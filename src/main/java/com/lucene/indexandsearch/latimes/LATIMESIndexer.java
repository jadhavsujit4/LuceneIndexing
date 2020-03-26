package com.lucene.indexandsearch.latimes;

import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

public class LATIMESIndexer extends DocumentIndexer {

    private Document doc;
    private static List<Document> latimesDocs = new ArrayList<>();

    public LATIMESIndexer(String indexPath) {
        super(indexPath);
        doc = new Document();
    }
    public void indexDocumentsFromFile(String fileName) {
        try {
            System.out.println("Indexing LATIMES Documents");
            latimesDocs = LATIMESReader.loadLATIMESDocs(Constants.LATIMES_FILESPATH);
            for (Document aDoc : latimesDocs) {
                addDocToIndex(aDoc);
            }
            System.out.println("Indexed LATIMES Documents");
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }
}
