package com.lucene.indexandsearch;


import com.lucene.indexandsearch.indexer.CRANIndexer;
import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.xml.bind.JAXB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class RunIndexer {

    public IndexParams p;

    public DocumentIndexer di;


    private enum DocumentModel {
        CRAN
    }

    private DocumentModel docModel;

    public RunIndexer() {
        System.out.println("Indexer");
    }

    private void setDocParser(String val) {
        try {
            docModel = DocumentModel.valueOf(p.indexType.toUpperCase());
        } catch (Exception e) {
            System.out.println("Document Parser Not Recognized - Setting to Default");
            System.out.println("Possible Document Parsers are:");
            for (DocumentModel value : DocumentModel.values()) {
                System.out.println("<indexType>" + value.name() + "</indexType>");
            }
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void selectDocumentParser(DocumentModel dm) {
        docModel = dm;
        di = null;
        if (dm == DocumentModel.CRAN) {
            di = new CRANIndexer(p.indexName, p.tokenFilterFile, p.recordPositions);
        } else {
            System.out.println("Default Document Parser");
        }
    }


    public ArrayList<String> readFileListFromFile() {
        /*
            Takes the name of a file (filename), which contains a list of files.
            Returns an array of the filenames (to be indexed)
         */

        String filename = p.fileList;

        ArrayList<String> files = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line = br.readLine();
                while (line != null) {
                    files.add(line);
                    line = br.readLine();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return files;
    }

    public void readIndexParamsFromFile(String indexParamFile) {
        try {
            p = JAXB.unmarshal(new File(indexParamFile), IndexParams.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (p.recordPositions == null)
            p.recordPositions = false;
//
//        System.out.println("Index type: " + p.indexType);
//        System.out.println("Path to index: " + p.indexName);
//        System.out.println("List of files to index: " + p.fileList);
//        System.out.println("Record positions in index: " + p.recordPositions);

    }

    public RunIndexer(String indexParamFile) {
        System.out.println("Indexer");
        readIndexParamsFromFile(indexParamFile);
        setDocParser(p.indexType);
        selectDocumentParser(docModel);
    }

    public void indexDocumentsFromFile(String filename) {
        di.indexDocumentsFromFile(filename);
    }

    public void finished() {
        di.finished();

        try {
            IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(p.indexName)));
            long numDocs = reader.numDocs();
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Number of docs indexed: " + numDocs + Constants.ANSI_RESET);


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }


    public static void main(String[] args) {

        RunIndexer indexer = new RunIndexer(Constants.indexParamFile);

        try {
            ArrayList<String> files = indexer.readFileListFromFile();
            for (String f : files) {
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "About to Index Files from: " + f + Constants.ANSI_RESET);
                indexer.indexDocumentsFromFile(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        indexer.finished();
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Done building Index" + Constants.ANSI_RESET);


    }

}


class IndexParams {
    public String indexName;
    public String fileList;
    public String indexType;

    //public Boolean compressed;
    public String tokenFilterFile;
    public Boolean recordPositions;

}
