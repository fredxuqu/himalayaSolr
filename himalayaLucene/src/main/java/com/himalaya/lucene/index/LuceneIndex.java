package com.himalaya.lucene.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

/**
* @author: xuqu
* @E-mail: fredxuqu@163.com
* @version 
* 2018年6月5日 上午9:43:07
* Description
*/
@Component
public class LuceneIndex {

	public int createIndex() throws Exception {
		String filePath = "D:/luceneTestFiles/file";
		String indexPath = "D:/luceneTestFiles/index";
		
		IndexWriter indexWriter = null;
		int totalNums = 0;
		try {
			File fileDir = new File(filePath);
			
			// open and create index folder
			Directory dir = FSDirectory.open(Paths.get(indexPath));

			// create Analyzer
			Analyzer luceneAnalyzer = new StandardAnalyzer();
			
			// create IndexWriterConfig
			IndexWriterConfig iwc = new IndexWriterConfig(luceneAnalyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			
			// create IndexWriter
			indexWriter = new IndexWriter(dir, iwc);
			File[] textFiles = fileDir.listFiles();

			// write document to index
			for (int i = 0; i < textFiles.length; i++) {
				if (textFiles[i].isFile()) {
					// read raw contents
					String temp = FileReaderAll(textFiles[i].getCanonicalPath(), "GBK");
					Document document = new Document();
					Field FieldPath = new StringField("path", textFiles[i].getPath(), Field.Store.YES);
					Field FieldBody = new TextField("body", temp, Field.Store.YES);
					document.add(FieldPath);
					document.add(FieldBody);
					indexWriter.addDocument(document);
				}
			}
			totalNums = indexWriter.numDocs();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(indexWriter!=null){
				indexWriter.close();
			}
		}
		return totalNums;
	}
	
	public String FileReaderAll(String file, String charType) {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
