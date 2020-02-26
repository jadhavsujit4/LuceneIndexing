package com.lucene.indexandsearch;

import com.lucene.indexandsearch.searcher.RunSearcher;
import com.lucene.indexandsearch.utils.Constants;

import java.util.ArrayList;

public class RunApplication {

    public static void main(String[] args) throws Exception {

        RunIndexer indexer = new RunIndexer(Constants.indexParamFile);

        try {
            ArrayList<String> files = indexer.readFileListFromFile();
            for (String f : files) {
                System.out.println("About to Index Files from: " + f);
                indexer.indexDocumentsFromFile(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        indexer.finished();
        System.out.println("Done building Index");

        String sim;

        sim = Constants.MODELBM25;
        RunSearcher searcher = new RunSearcher(sim);
        searcher.processQueryFile();

        PlotGraph.main(null);
        System.out.println( Constants.CYAN_BOLD_BRIGHT + "FINISHED!" + Constants.ANSI_RESET);
    }


}
