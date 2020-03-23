package com.lucene.indexandsearch.FBIS;

import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;

import java.util.ArrayList;
import java.util.List;

public class FBISIndexer extends DocumentIndexer {

    //    private Field docNoField;
//    private Field headerField;
//    private Field textField;
//    private Field allField;
    private Document doc;
    private static List<Document> fbisDocs = new ArrayList<>();


    public FBISIndexer(String indexPath, String tokenFilterFile, boolean positional) {
        super(indexPath, tokenFilterFile, positional);

        // Reusable document object to reduce GC overhead
        doc = new Document();

//        initFields();
//        initFBISDoc();
    }

//    private void initFields() {
//        docNoField = new StringField(Constants.FIELD_DOCNUM, "", Field.Store.YES);
//        if (indexPositions) {
//            headerField = new TermVectorField(Constants.FIELD_TITLE, "", Field.Store.YES);
//            textField = new TermVectorField(Constants.FIELD_TEXT, "", Field.Store.YES);
//            allField = new TermVectorField(Constants.FIELD_ALL, "", Field.Store.YES);
//        } else {
//            headerField = new TextField(Constants.FIELD_TITLE, "", Field.Store.YES);
//            textField = new TextField(Constants.FIELD_TEXT, "", Field.Store.YES);
//            allField = new TextField(Constants.FIELD_ALL, "", Field.Store.YES);
//        }
//    }
//
//    private void initFBISDoc() {
//        doc.add(docNoField);
//        doc.add(headerField);
//        doc.add(textField);
//        doc.add(allField);
//    }

//    public Document createFBISDocument(String docid, String title, String author, String pubdate, String content) {
//
//        docNoField.setStringValue(docid);
//        headerField.setStringValue(title);
//        textField.setStringValue(content);
//        allField.setStringValue(title + " " + author + " " + content);
//
//        doc.add(docNoField);
//        doc.add(headerField);
//        doc.add(textField);
//        doc.add(allField);
//
//        return doc;
//    }


    public void indexDocumentsFromFile(String fileName) {
        try {

            System.out.println("Indexing FBIS Documents");
            fbisDocs = FBISReader.loadFBISDocs(Constants.FBISFILESPATH);
            for (Document aDoc : fbisDocs
            ) {
                addDocToIndex(aDoc);
            }
            System.out.println("Indexed FBIS Documents");
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }
}