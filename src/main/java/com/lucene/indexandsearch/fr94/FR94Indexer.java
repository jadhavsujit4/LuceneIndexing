package com.lucene.indexandsearch.fr94;

import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

public class FR94Indexer extends DocumentIndexer {
    private Document doc;
    private static List<Document> fr94Docs = new ArrayList<>();

    public FR94Indexer(String indexPath) {
        super(indexPath);
        doc = new Document();
    }
    public void indexDocumentsFromFile(String fileName) {
        try {
            System.out.println("Indexing FR94 Documents");
            fr94Docs = FR94Reader.loadFR94Docs(Constants.FR94FILESPATH);
            for (Document aDoc : fr94Docs) {
                addDocToIndex(aDoc);
            }
            System.out.println("Indexed FR94 Documents");
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }
}
