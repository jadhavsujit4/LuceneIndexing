package com.lucene.indexandsearch.indexer;


import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import java.io.BufferedReader;
import java.util.Arrays;

public class CRANIndexer extends DocumentIndexer {

    private Field docnumField;
    private Field titleField;
    private Field authorField;
    private Field pubdateField;
    private Field textField;
    private Field allField;
    private Document doc;

    public CRANIndexer(String indexPath, String tokenFilterFile, boolean positional) {
        super(indexPath, tokenFilterFile, positional);

        // Reusable document object to reduce GC overhead
        doc = new Document();

        initFields();
        initCranDoc();
    }

    private void initFields() {
        docnumField = new StringField(Constants.FIELD_DOCNUM, "", Field.Store.YES);
        pubdateField = new StringField(Constants.FIELD_PUBDATE, "", Field.Store.YES);
        if (indexPositions) {
            titleField = new TermVectorField(Constants.FIELD_TITLE, "", Field.Store.YES);
            textField = new TermVectorField(Constants.FIELD_CONTENT, "", Field.Store.YES);
            authorField = new TermVectorField(Constants.FIELD_AUTHOR, "", Field.Store.YES);
            allField = new TermVectorField(Constants.FIELD_ALL, "", Field.Store.YES);
        } else {
            titleField = new TextField(Constants.FIELD_TITLE, "", Field.Store.YES);
            textField = new TextField(Constants.FIELD_CONTENT, "", Field.Store.YES);
            authorField = new TextField(Constants.FIELD_AUTHOR, "", Field.Store.YES);
            allField = new TextField(Constants.FIELD_ALL, "", Field.Store.YES);
        }
    }

    private void initCranDoc() {
        doc.add(docnumField);
        doc.add(titleField);
        doc.add(textField);
        doc.add(authorField);
        doc.add(pubdateField);
        doc.add(allField);
    }

    public Document createCacmDocument(String docid, String title, String author, String pubdate, String content) {

        docnumField.setStringValue(docid);
        titleField.setStringValue(title);
        authorField.setStringValue(author);
        pubdateField.setStringValue(pubdate);
        textField.setStringValue(content);
        allField.setStringValue(title + " " + author + " " + content);

        doc.add(docnumField);
        doc.add(titleField);
        doc.add(textField);
        doc.add(authorField);
        doc.add(pubdateField);
        doc.add(allField);

        return doc;
    }


    public void indexDocumentsFromFile(String filename) {
        try {
            BufferedReader br = openDocumentFile(filename);
            try {

                String[] fields = new String[5];
                for (int i = 0; i < fields.length; i++) {
                    fields[i] = "";
                }
                // 0 - id, 1 - title, 2-authors, 3-about publication, 4-main content
                int fieldno = 0;


                String line = br.readLine();
                while (line != null) {

                    if (line.startsWith(".I")) {
                        // if there is an existing document, create doc, and add to index
                        if (!fields[0].equals("")) {
                            doc.clear();
                            doc = createCacmDocument(fields[0], fields[1], fields[2], fields[3], fields[4]);
                            addDocToIndex(doc);
                        }

                        // reset fields
                        Arrays.fill(fields, "");
                        String[] parts = line.split(" ");
                        // set field 0 to docid
                        fields[0] = parts[1];
                        System.out.println("Indexing document: " + parts[1]);
                        fieldno = 0;
                    }

                    if (line.startsWith(".T")) {
                        // set field to title, capture title text
                        fieldno = 1;
                    }

                    if (line.startsWith(".A")) {
                        // set field to author
                        fieldno = 2;
                    }

                    if (line.startsWith(".B")) {
                        // set field to pub date
                        fieldno = 3;
                    }

                    if (line.startsWith(".W")) {
                        // set field to content
                        fieldno = 4;
                    }


                    if ((fieldno > 0) && (fieldno < 5)) {
                        if (line.length() > 2) {
                            fields[fieldno] += " " + line;
                        }
                    }
                    line = br.readLine();
                }
                if (!fields[0].equals("")) {
                    doc = createCacmDocument(fields[0], fields[1], fields[2], fields[3], fields[4]);
                    addDocToIndex(doc);
                }

            } finally {
                br.close();
            }
        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }
}