package com.lucene.indexandsearch;

import com.lucene.indexandsearch.fbis.FBISIndexer;
import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.latimes.LATIMESIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import static com.lucene.indexandsearch.utils.Constants.*;


public class Main {

    private DocumentModel docModel;
    public DocumentIndexer diFbis;
    public DocumentIndexer diLatimes;
    //TODO add your indexer for your data

    public Main(String docType) {
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Indexer" + Constants.ANSI_RESET);
        setDocParser(docType);
        selectDocumentParser(docModel);
    }

    private enum DocumentModel {
        //TODO add your Docmodel
        CRAN, FBIS, LATTIMES
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
        diFbis = null;
        diLatimes = null;
        //TODO: add your DocumentModel in if and else
        if (dm == DocumentModel.FBIS) {
            diFbis = new FBISIndexer(Constants.INDEXPATH);
        } else if(dm == DocumentModel.LATTIMES)
        {
            diLatimes = new LATIMESIndexer(Constants.INDEXPATH);
        }
        else
            {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Default Document Parser" + Constants.ANSI_RESET);
        }
    }

    public void indexDocumentsFromFile(String filename) {
        if(filename.equals(LATIMES_FILESPATH)){
            diLatimes.indexDocumentsFromFile(filename);
            diLatimes.finished();
        }
        else if(filename.equals(Constants.FBISFILESPATH)){
            diFbis.indexDocumentsFromFile(filename);
            diFbis.finished();
        }
        //TODO: add your folders
    }

    public void finished() {
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

        createIndex(FBISFILESPATH,FBISINDEXTYPE);
        createIndex(LATIMES_FILESPATH, LATTIMESINDEXTYPE);
        //TODO: add your indexer
    }

    public static void createIndex(String indexData, String indexType){
        Instant startTime = Instant.now();
        Main indexer = new Main(indexType);
        try {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "About to Index Files from data: " + indexType + Constants.ANSI_RESET);
            indexer.indexDocumentsFromFile(indexData);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            indexer.finished();
        }
        Instant finishTime = Instant.now();
        long timeElapsed = Duration.between(startTime, finishTime).toMillis();
        System.out.println(Constants.EXECUTION_TIME + timeElapsed/60000.0);
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Done building Index" + Constants.ANSI_RESET);
    }
}