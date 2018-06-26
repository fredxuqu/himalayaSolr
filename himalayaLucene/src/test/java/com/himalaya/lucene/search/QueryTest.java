package com.himalaya.lucene.search;

import java.io.IOException;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BooleanQuery.Builder;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月5日 上午11:02:06
* Description
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryTest{
	
protected static final Logger LOGGER = LoggerFactory.getLogger(QueryTest.class);
	
	private Directory directory = null;
	
	protected String[] ids = {"1", "2", "3", "4"};
	protected String[] countries = {"China", "USA", "England", "Italy"};
	protected String[] contents = {"Great wall is located in north of Beijing, badaling is a part of it",
								   "Most Headquarter of famous financial company all over the world are located in Wall Street",
								   "Oxford and Cambridge are located in London, their are famous University.",
								   "Roma is a famous historic old town, there is a word - Roma is not built in a day."};
	protected String[] citys = {"Beijing", "NewYork", "London", "Roma"};
	protected String[] population = {"25000000", "20000000", "13000000", "8000000"};
	
	@Before
	public void setUp() {
		
		IndexWriter writer = null;
		try {
			directory = new RAMDirectory();
			writer = getWriter();
			for (int i = 0; i < ids.length; i++) {
				Document doc = new Document();
				doc.add(new StringField("id", ids[i], Field.Store.YES));
				doc.add(new StringField("country", countries[i], Field.Store.YES));
				doc.add(new TextField("contents", contents[i], Field.Store.YES));
				doc.add(new StringField("city", citys[i], Field.Store.YES));
				doc.add(new StringField("population", population[i], Field.Store.YES));
				writer.addDocument(doc);
			}
			writer.commit();
			LOGGER.info("Doc Nums " + writer.numDocs());			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	private IndexWriter getWriter() throws Exception{
		IndexWriterConfig iwc = new IndexWriterConfig(new WhitespaceAnalyzer());
		iwc.setOpenMode(OpenMode.CREATE);
		return new IndexWriter(directory, iwc);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void testTermQuery() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Term term = new Term("contents","wall");
		Query query = new TermQuery(term);
		TopDocs docs = searcher.search(query, 1);
		Assert.assertEquals(1, docs.totalHits);
		reader.close();
	}
	
	@Test
	public void testTermRangeQuery() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		BytesRef a = new BytesRef("USA");
		BytesRef b = new BytesRef("USC");
		Query query = new TermRangeQuery("country", a, b, true, true);
		TopDocs docs = searcher.search(query, 1);
		Assert.assertEquals(1, docs.totalHits);
		reader.close();
	}
	
	@Test
	public void testNumericRangeQuery() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		BytesRef a = new BytesRef("5000000");
		BytesRef b = new BytesRef("10000000");
		Query query = new TermRangeQuery("population", a, b, true, true);
		TopDocs docs = searcher.search(query, 1);
		Assert.assertEquals(1, docs.totalHits);
		reader.close();
	}
	
	@Test
	public void testBooleanQuery() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		BytesRef a = new BytesRef("China");
		BytesRef b = new BytesRef("Chinb");
		Query rangeQuery = new TermRangeQuery("country", a, b, true, true);
		
		Term term = new Term("contents","wall");
		Query termQuery = new TermQuery(term);
		
		Builder builder = new BooleanQuery.Builder();
		builder.add(rangeQuery, BooleanClause.Occur.MUST);
		builder.add(termQuery, BooleanClause.Occur.MUST);
		
		TopDocs docs = searcher.search(builder.build(), 1);
		Assert.assertEquals(1, docs.totalHits);
		reader.close();
	}
	
	@Test
	public void testPhraseQuery() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		String[] phrase = {"famous","company"};
		PhraseQuery phraseQuery = new PhraseQuery(1, "contents", phrase);
		
		TopDocs docs = searcher.search(phraseQuery, 1);
		Assert.assertEquals(1, docs.totalHits);
		ScoreDoc[] hitDocs = docs.scoreDocs;
		for (ScoreDoc scoreDoc : hitDocs) {
			Document document = searcher.doc(scoreDoc.doc);
			LOGGER.info("Contents " + document.get("contents"));
		}
		reader.close();
	}
	
	@Test
	public void testPhraseQuery1() throws Exception{
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		String[] phrase = {"company","famous"};
		PhraseQuery phraseQuery = new PhraseQuery(3, "contents", phrase);
		
		TopDocs docs = searcher.search(phraseQuery, 1);
		Assert.assertEquals(1, docs.totalHits);
		reader.close();
	}
}