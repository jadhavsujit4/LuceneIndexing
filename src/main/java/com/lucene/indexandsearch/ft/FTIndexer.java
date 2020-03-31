package com.lucene.indexandsearch.ft;

import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

public class FTIndexer extends DocumentIndexer {

    private Document doc;
    private static List<Document> ftDocs = new ArrayList<>();

    public FTIndexer(String indexPath) {
        super(indexPath);
        doc = new Document();
    }
    public void indexDocumentsFromFile(String fileName) {
        try {
            System.out.println("Indexing FT Documents");
            ftDocs = FTReader.loadFTDocs(Constants.FT_FILESPATH);
            for (Document aDoc : ftDocs) {
                addDocToIndex(aDoc);
            }
            System.out.println("Indexed FT Documents");
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
        }
    }
}
