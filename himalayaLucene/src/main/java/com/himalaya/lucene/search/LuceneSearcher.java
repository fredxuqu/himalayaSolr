package com.himalaya.lucene.search;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月5日 上午9:53:23
* Description
*/
@Component
public class LuceneSearcher {
	public void search() throws Exception {
		String indexPath = "D:/luceneTestFiles/index";
		try {
			
			// open index file
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
			
			// create search
			IndexSearcher searcher = new IndexSearcher(reader);
			ScoreDoc[] hits = null;
			String queryString = "lucene";
			Query query = null;
			
			// create Analyzer
			Analyzer analyzer = new StandardAnalyzer();
	
			// create QueryParser and parse query
			QueryParser qp = new QueryParser("body", analyzer);
			query = qp.parse(queryString);

			if (searcher != null) {
				// search documents
				TopDocs results = searcher.search(query, 10);
				hits = results.scoreDocs;
				Document document = null;
				for (int i = 0; i < hits.length; i++) {
					document = searcher.doc(hits[i].doc);
					String body = document.get("body");
					String path = document.get("path");
					String modifiedtime = document.get("modifiField");
					System.out.println(path + " " + body + " " + modifiedtime);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
