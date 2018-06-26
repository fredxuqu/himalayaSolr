package com.himalaya.lucene.index;

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
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.himalaya.lucene.LuceneApplicationTest;


/**
 * @author: xuqu
 * @E-mail: fredxuqu@163.com
 * @version 2018年6月4日 下午2:29:04 Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneIndexTest{

	protected static final Logger LOGGER = LoggerFactory.getLogger(LuceneApplicationTest.class);
	
	@Autowired
	LuceneIndex luceneIndex;
	
	protected String[] ids = {"1", "2"};
	protected String[] countries = {"Netherlands", "Italy"};
	protected String[] contents = {"Amsterdam has lots of bridges", "Venice has lots of canals"};
	protected String[] citys = {"Amsterdam", "Venice"};
	private Directory directory;
	
	@Before
	public void setUp() throws Exception{
		
		directory = new RAMDirectory();
		IndexWriter writer = getWriter();
		for (int i = 0; i < ids.length; i++) {
			Document doc = new Document();
			doc.add(new StringField("id", ids[i], Field.Store.YES));
			doc.add(new StringField("country", countries[i], Field.Store.YES));
			doc.add(new TextField("contents", contents[i], Field.Store.YES));
			doc.add(new StringField("city", citys[i], Field.Store.YES));
			
			writer.addDocument(doc);
		}
		LOGGER.info("Doc Nums " + writer.numDocs()); 
		writer.close();
	}
	
	@After
	public void tearDown(){
		
	}
	
	private IndexWriter getWriter() throws Exception{
		IndexWriterConfig iwc = new IndexWriterConfig(new WhitespaceAnalyzer());
		//iwc.setOpenMode(OpenMode.CREATE);
		return new IndexWriter(directory, iwc);
	}
	
	@Test
	public void testCreateIndex() throws Exception {
		LOGGER.info("Total Num: " + luceneIndex.createIndex());
	}
	
	@Test
	public void testIndexWriter() throws Exception {
		
		IndexWriter writer = getWriter();
		Assert.assertEquals(ids.length, writer.numDocs());
		writer.close();
	}
	
	@Test
	public void testDeleteIndex() throws Exception {
		
		IndexWriter writer = getWriter();
		Assert.assertEquals(ids.length, writer.numDocs());
		writer.deleteDocuments(new Term("id","1"));
		writer.commit();
		Assert.assertTrue(writer.hasDeletions());
		Assert.assertEquals(ids.length, writer.maxDoc());
		Assert.assertEquals(1, writer.numDocs());
		writer.close();
	}
	
	@Test
	public void testDeleteIndexAfterOptimize() throws Exception {
		
		IndexWriter writer = getWriter();
		Assert.assertEquals(ids.length, writer.numDocs());
		writer.deleteDocuments(new Term("id","1"));
		writer.commit();
		Assert.assertTrue(writer.hasDeletions());
		Assert.assertEquals(ids.length, writer.maxDoc());
		Assert.assertEquals(1, writer.numDocs());
		writer.close();
	}
	
	@Test
	public void testUpdateIndex() throws Exception {
		
		IndexWriter writer = getWriter();
		Document doc = new Document();
		doc.add(new StringField("id", "1", Field.Store.YES));
		doc.add(new StringField("country", "USA", Field.Store.YES));
		doc.add(new TextField("contents", "Facebook will open a Distributed System build on Lucene", Field.Store.YES));
		doc.add(new StringField("city", "SFO", Field.Store.YES));
		writer.updateDocument(new Term("id", "1"), doc);
		writer.commit();
		Assert.assertEquals(ids.length, writer.numDocs());
		Assert.assertEquals(3, writer.maxDoc());
		writer.close();
	}
	
	@Test
	public void testIndexReader() throws Exception {
		
		IndexReader reader = DirectoryReader.open(directory);
		Assert.assertEquals(ids.length, reader.maxDoc());
		Assert.assertEquals(ids.length, reader.numDocs());
		reader.close();
	}	
	
	@Test
	public void testTermQuery() throws Exception {
		
		String fieldName = "contents";
		String keywords = "bridges";
		Term term = new Term(fieldName, keywords);
		Query query = new TermQuery(term);
		
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		TopDocs results = searcher.search(query, 10);
		Assert.assertEquals(1, results.totalHits);
//		ScoreDoc[] hitDocs = results.scoreDocs;
//		for (ScoreDoc scoreDoc : hitDocs) {
//			Document document = searcher.doc(scoreDoc.doc);
//			LOGGER.info("Contents " + document.get("contents"));
//		}
//		Assert.assertEquals(1, results.scoreDocs.length);
	}	
	
	@Test
	public void testQueryParser() {
		
		IndexReader reader = null;
		
		try {
			String fieldName = "contents";
			String keywords = "Amsterdam";
			QueryParser queryParser = new QueryParser(fieldName, new WhitespaceAnalyzer());
			Query query = queryParser.parse(keywords);
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs results = searcher.search(query, 10);
			ScoreDoc[] hitDocs = results.scoreDocs;
			for (ScoreDoc scoreDoc : hitDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				LOGGER.info("Contents " + document.get("contents"));
			}
			Assert.assertEquals(1, results.scoreDocs.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testQueryParser1() {
		
		IndexReader reader = null;
		
		try {
			String fieldName = "contents";
			String keywords = "+Amsterdam +canals";
			QueryParser queryParser = new QueryParser(fieldName, new WhitespaceAnalyzer());
			Query query = queryParser.parse(keywords);
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs results = searcher.search(query, 10);
			ScoreDoc[] hitDocs = results.scoreDocs;
			for (ScoreDoc scoreDoc : hitDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				LOGGER.info("Contents " + document.get("contents"));
			}
			Assert.assertEquals(0, results.scoreDocs.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testQueryParser2() {
		
		IndexReader reader = null;
		
		try {
			String fieldName = "contents";
			String keywords = "+Amsterdam +bridges";
			QueryParser queryParser = new QueryParser(fieldName, new WhitespaceAnalyzer());
			Query query = queryParser.parse(keywords);
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs results = searcher.search(query, 10);
			ScoreDoc[] hitDocs = results.scoreDocs;
			for (ScoreDoc scoreDoc : hitDocs) {
				Document document = searcher.doc(scoreDoc.doc);
				LOGGER.info("Contents " + document.get("contents"));
			}
			Assert.assertEquals(1, results.scoreDocs.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}