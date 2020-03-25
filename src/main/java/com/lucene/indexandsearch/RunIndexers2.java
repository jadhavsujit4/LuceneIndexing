package com.lucene.indexandsearch;

import com.lucene.indexandsearch.fbis.FBISIndexer;
import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;


public class RunIndexers2 {

//    public IndexParams2 p;

    public DocumentIndexer di;


    private enum DocumentModel {
        CRAN, FBIS, LATTIMES
    }

    private DocumentModel docModel;

    public RunIndexers2() {
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Indexer" + Constants.ANSI_RESET);
    }

    private void setDocParser(String val) {
        try {
            docModel = DocumentModel.valueOf(val.toUpperCase());
        } catch (Exception e) {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Document Parser Not Recognized - Setting to Default" + Constants.ANSI_RESET);
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Possible Document Parsers are:" + Constants.ANSI_RESET);
            for (DocumentModel value : DocumentModel.values()) {
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "<indexType>" + value.name() + "</indexType>" + Constants.ANSI_RESET);
            }
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void selectDocumentParser(DocumentModel dm) {
        docModel = dm;
        di = null;
        if (dm == DocumentModel.FBIS) {
            di = new FBISIndexer(Constants.INDEXPATH);
        } else {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Default Document Parser" + Constants.ANSI_RESET);
        }
    }

    public RunIndexers2(String docType) {
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Indexer" + Constants.ANSI_RESET);
        setDocParser(docType);
        selectDocumentParser(docModel);
    }

    public void indexDocumentsFromFile(String filename) {
        di.indexDocumentsFromFile(filename);
    }

    public void finished() {
        di.finished();

        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Constants.INDEXPATH)));
            long numDocs = reader.numDocs();
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Number of docs indexed: " + numDocs + Constants.ANSI_RESET);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }


    public static void main(String[] args) {

        RunIndexers2 fbisIndexer = new RunIndexers2(Constants.FBISINDEXTYPE);
        RunIndexers2 lattimesIndexer = new RunIndexers2(Constants.LATTIMESINDEXTYPE);

        try {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "About to Index Files from: " + Constants.FBISFILESPATH + Constants.ANSI_RESET);
            fbisIndexer.indexDocumentsFromFile(Constants.FBISFILESPATH);
            lattimesIndexer.indexDocumentsFromFile(Constants.LATTIMESINDEXTYPE);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            fbisIndexer.finished();
            lattimesIndexer.finished();
        }
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Done building Index" + Constants.ANSI_RESET);
    }
}