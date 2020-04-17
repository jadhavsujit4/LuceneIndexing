package com.lucene.indexandsearch;

import com.lucene.indexandsearch.fbis.FBISIndexer;
import com.lucene.indexandsearch.fr94.FR94Indexer;
import com.lucene.indexandsearch.ft.FTIndexer;
import com.lucene.indexandsearch.indexer.DocumentIndexer;
import com.lucene.indexandsearch.latimes.LATIMESIndexer;
import com.lucene.indexandsearch.searcher.Searcher;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;
import org.codehaus.plexus.util.FileUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

import static com.lucene.indexandsearch.utils.Constants.*;


public class Main {

    private DocumentModel docModel;
    public DocumentIndexer diFbis;
    public DocumentIndexer diLatimes;
    public DocumentIndexer diFr94;
    public DocumentIndexer diFt;

    public Main(String docType) throws IOException {
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "About to Index Files for Document type: " + docType + Constants.ANSI_RESET);
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
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            indexer.finished();
        }
        Instant finishTime = Instant.now();
        long timeElapsed = Duration.between(startTime, finishTime).toMillis();
        System.out.println(Constants.CYAN_BOLD_BRIGHT + Constants.EXECUTION_TIME + timeElapsed / 60000.0 + Constants.ANSI_RESET);
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Done building Index for " + indexType + Constants.ANSI_RESET);
    }

    public static void main(String[] args) throws Exception {

        System.out.println(Constants.FORMATTER);
        System.out.println(Constants.CYELW_START + Constants.GRPUPNAME + Constants.CYELW_END);
        System.out.println(Constants.CYELW_START + Constants.COURSE + Constants.ASMNT + Constants.NEW_LINE + Constants.NAME3 + Constants.NEW_LINE + Constants.TCDID3 + Constants.NEW_LINE + Constants.NEW_LINE + Constants.NAME4 + Constants.NEW_LINE + Constants.TCDID4 + Constants.NEW_LINE + Constants.NEW_LINE + Constants.NAME1 + Constants.NEW_LINE + Constants.TCDID1 + Constants.NEW_LINE + Constants.NEW_LINE + Constants.NAME2 + Constants.NEW_LINE + Constants.TCDID2 + Constants.NEW_LINE  + Constants.CYELW_END);

        while (true) {
            System.out.println(Constants.FORMATTER);
            System.out.println(Constants.ENTER_YOUR_CHOICE);
            System.out.print(Constants.NEW_LINE);
            System.out.println(Constants.TAB + Constants.CHOICE_1);
            System.out.println(Constants.TAB + Constants.CHOICE_2);
            System.out.println(Constants.TAB + Constants.CHOICE_3);

            System.out.print(Constants.NEW_LINE);

            System.out.print(Constants.ENTER_YOUR_CHOICE);
            Scanner in = new Scanner(System.in);

            while (!in.hasNextInt()) {
                System.out.println(Constants.RED_START + Constants.INVALID_CHOICE + Constants.RED_END);
                System.out.print(Constants.ENTER_YOUR_CHOICE);
                in.next();
            }

            int choiceInput = in.nextInt();
            String[] argToPassToSearcher;
            switch (choiceInput) {
                case 1:
                    System.out.println(Constants.CYAN_BOLD_BRIGHT + "Running Indexer" + Constants.ANSI_RESET);
                    FileUtils.cleanDirectory(INDEXPATH);
                    createIndex(FBISFILESPATH, FBISINDEXTYPE);
                    createIndex(LATIMES_FILESPATH, LATTIMESINDEXTYPE);
                    createIndex(FR94FILESPATH, FR94INDEXTYPE);
                    createIndex(FT_FILESPATH, FTINDEXTYPE);
                    System.out.println(Constants.CYAN_BOLD_BRIGHT + "Indexing Completed" + Constants.ANSI_RESET);
                    String sim = MODELBM25;
                    Constants.MODELUSED = MODELBM25;
                    argToPassToSearcher = new String[]{Constants.MODELUSED};
                    Searcher.main(argToPassToSearcher);
                    break;
                case 2:
                    System.out.print(Constants.CHOOSE_SIMILARITY + Constants.NEW_LINE);
                    System.out.println(Constants.TAB + Constants.CHOICE_BM25);
                    System.out.println(Constants.TAB + Constants.CHOICE_MULTI);
                    System.out.println(Constants.TAB + Constants.CHOICE_LMJ);
                    System.out.println(Constants.TAB + Constants.CHOICE_LMD);
                    System.out.println(Constants.TAB + Constants.CHOICE_CLASSIC);
                    while (!in.hasNextInt()) {
                        System.out.println(Constants.RED_START + Constants.INVALID_CHOICE + Constants.RED_END);
                        System.out.print(Constants.ENTER_YOUR_CHOICE);
                        in.next();
                    }
                    int similarityInput = in.nextInt();
                    switch (similarityInput) {
                        case 1:
                            Constants.MODELUSED = MODELBM25;
                            break;
                        case 2:
                            Constants.MODELUSED = Constants.MODELMULTI;
                            break;
                        case 3:
                            Constants.MODELUSED = Constants.MODELLMJ;
                            break;
                        case 4:
                            Constants.MODELUSED = Constants.MODELLMD;
                            break;
                        case 5:
                            Constants.MODELUSED = Constants.MODELCLASSIC;
                            break;
                        default:
                            System.out.println(Constants.RED_START + Constants.OUT_OF_RANGE + Constants.RED_END);
                            System.out.println(Constants.CGRN_START + Constants.DEFAULT_SIMILARITY + Constants.CGRN_END);
                            Constants.MODELUSED = MODELBM25;
                            break;
                    }
                    break;
                case 3:
                    System.exit(1);
                default: {
                    System.out.println(Constants.RED_START + Constants.OUT_OF_RANGE + Constants.RED_END);
                    System.out.println(Constants.CGRN_START + Constants.DEFAULT_CHOICE + Constants.CGRN_END);
                    Constants.MODELUSED = MODELBM25;
                    break;
                }
            }
            argToPassToSearcher = new String[]{Constants.MODELUSED};
            Searcher.main(argToPassToSearcher);
            System.out.println(Constants.NEW_LINE);

            PlotGraph.main(null);
        }
    }
}