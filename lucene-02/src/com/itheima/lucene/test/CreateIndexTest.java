package com.itheima.lucene.test;

import java.io.File;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.itheima.lucene.dao.BookDao;
import com.itheima.lucene.dao.BookDaoImpl;
import com.itheima.lucene.domain.Book;

public class CreateIndexTest {

	//创建luence索引
	@Test
	public void testAdd() throws Exception{
		//1、读取文档mysql数据
		//2、获取mysql数据
		BookDao bookDao = new BookDaoImpl();
		List<Book> books = bookDao.queryBookList();
		
		//4、分析文档
		Analyzer analyzer = new IKAnalyzer();
		
		//5、创建索引到索引库中
		Directory directory = FSDirectory.open(new File("F:\\temp\\dd"));
		IndexWriterConfig config = new IndexWriterConfig(Version.LATEST, analyzer);
		IndexWriter indexWrite = new IndexWriter(directory, config);
		for (Book book : books) {
			//3、创建文档对象Document
			Document doc = new Document();
			
			//将book对象中的五个属性放到document五个域中
			Field idField = new StoredField("id",String.valueOf(book.getId()));
			Field nameField = new TextField("name",String.valueOf(book.getName()),Store.YES);
			Field picField = new StoredField("pic",String.valueOf(book.getPic()));
			Field priceField = new FloatField("pic", book.getPrice(),Store.YES);
			Field descField = new TextField("desc",String.valueOf(book.getDesc()),Store.YES);
			
			doc.add(idField);
			doc.add(nameField);
			doc.add(picField);
			doc.add(priceField);
			doc.add(descField);
			
			//把全域放到文档对象中
			indexWrite.addDocument(doc);
			
		}
			
		
		indexWrite.close();
			
			
			
			
		
		
		
	}
	
	 
}
