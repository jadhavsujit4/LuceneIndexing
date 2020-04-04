package com.lucene.indexandsearch;

import com.lucene.indexandsearch.fbis.FBISIndexer;
import com.lucene.indexandsearch.fr94.FR94Indexer;
import com.lucene.indexandsearch.ft.FTIndexer;
import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.latimes.LATIMESIndexer;
import com.lucene.indexandsearch.searcher.RunSearcher2;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.codehaus.plexus.util.FileUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

import static com.lucene.indexandsearch.utils.Constants.*;


public class Main {

    private DocumentModel docModel;
    public DocumentIndexer diFbis;
    public DocumentIndexer diLatimes;
    public DocumentIndexer diFr94;
    public DocumentIndexer diFt;

    public Main(String docType) throws IOException {
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Indexer" + Constants.ANSI_RESET);
        setDocParser(docType);
        selectDocumentParser(docModel);
    }

    private enum DocumentModel {
        CRAN, FBIS, LATTIMES, FR94, FT
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

    public void selectDocumentParser(DocumentModel dm) throws IOException {
        docModel = dm;
        diFbis = null;
        diLatimes = null;
        diFr94 = null;
        diFt = null;

        if (dm == DocumentModel.FBIS) {
            new FBISIndexer(Constants.INDEXPATH);
        } else if (dm == DocumentModel.LATTIMES) {
            new LATIMESIndexer(Constants.INDEXPATH);
        } else if (dm == DocumentModel.FR94) {
            new FR94Indexer(INDEXPATH);
        } else if (dm == DocumentModel.FT) {
            new FTIndexer(Constants.INDEXPATH);
        } else {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "Default Document Parser" + Constants.ANSI_RESET);
        }
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

    public static void createIndex(String indexData, String indexType) throws IOException {
        Instant startTime = Instant.now();
        Main indexer = new Main(indexType);
        try {
            System.out.println(Constants.CYAN_BOLD_BRIGHT + "About to Index Files from data: " + indexType + Constants.ANSI_RESET);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            indexer.finished();
        }
        Instant finishTime = Instant.now();
        long timeElapsed = Duration.between(startTime, finishTime).toMillis();
        System.out.println(Constants.EXECUTION_TIME + timeElapsed / 60000.0);
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Done building Index" + Constants.ANSI_RESET);
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 0 && args[0].equals("1")) {
            FileUtils.cleanDirectory(INDEXPATH);
            createIndex(FBISFILESPATH, FBISINDEXTYPE);
            createIndex(LATIMES_FILESPATH, LATTIMESINDEXTYPE);
            createIndex(FR94FILESPATH, FR94INDEXTYPE);
            createIndex(FT_FILESPATH, FTINDEXTYPE);
        }
        String sim = Constants.MODELMULTI;
        Constants.MODELUSED = Constants.MODELMULTI;
        String[] argToPassToSearcher = {sim};
        RunSearcher2.main(argToPassToSearcher);
        RunTrecEval.main(null);
    }
}