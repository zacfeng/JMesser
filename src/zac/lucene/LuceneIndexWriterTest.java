package zac.lucene;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndexWriterTest {
	
	private static void addDoc(IndexWriter w, int i, String q)
			throws IOException {
		Document doc = new Document();
		doc.add(new TextField("nid", String.valueOf(i), Field.Store.YES));
		doc.add(new TextField("q", q, Field.Store.YES));
		w.addDocument(doc);
	}
	
	public static void main(String[] args) throws IOException, ParseException,
			ClassNotFoundException, SQLException {
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_46);

		FSDirectory index = FSDirectory.open(new File("./luceneIndex"));

		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_46,
				analyzer);

		IndexWriter w = new IndexWriter(index, config);

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager
				.getConnection("jdbc:sqlite:Searchlog.db");
		Statement stat = conn.createStatement();

		ResultSet rs = stat.executeQuery("SELECT q FROM SearchlogRaw;");
		int i = 0;

		while (rs.next()) {
			addDoc(w, i, rs.getString("q"));
			i++;
		}

		w.close();

		System.out.println("Index " + i + " documents.");
	}

}