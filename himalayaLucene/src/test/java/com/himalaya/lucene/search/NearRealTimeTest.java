package com.himalaya.lucene.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Assert;
import org.junit.Test;

import com.himalaya.lucene.LuceneApplicationTest;


/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月5日 上午11:02:06
* Description
*/
public class NearRealTimeTest extends LuceneApplicationTest{
	
	@Test
	public void testNearRealTimeSearch() throws Exception {
		Directory directory = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, iwc);
		for (int i = 0; i < 10; i++) {
			Document doc = new Document();
			doc.add(new StringField("id", "" + i, Field.Store.NO));
			doc.add(new StringField("text", "aaa", Field.Store.NO));
			writer.addDocument(doc);
		}
		writer.commit();
		
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term("text", "aaa"));
		TopDocs docs = searcher.search(query, 1);
		Assert.assertEquals(10, docs.totalHits);
		
		writer.deleteDocuments(new Term("id", "7"));
		writer.commit();
		
		reader = DirectoryReader.open(directory);
		searcher = new IndexSearcher(reader);
		docs = searcher.search(query, 10);
		Assert.assertEquals(9, docs.totalHits);
		
		reader.close();
		writer.close();
	}
	
	
	@Test
	public void testExplain() throws Exception {
		Directory directory = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, iwc);
		for (int i = 0; i < 10; i++) {
			Document doc = new Document();
			doc.add(new StringField("id", "" + i, Field.Store.NO));
			doc.add(new StringField("text", "aaa", Field.Store.NO));
			writer.addDocument(doc);
		}
		writer.commit();
		
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term("text", "aaa"));
		TopDocs docs = searcher.search(query, 10);
		for (ScoreDoc match : docs.scoreDocs) {
			System.out.println(match.doc);
			Explanation explain = searcher.explain(query, match.doc);
			System.out.println(explain.toString());
		}
		Assert.assertEquals(10, docs.totalHits);		
		reader.close();
		writer.close();
	}
}