package com.lucene.indexandsearch.searcher;

import com.lucene.indexandsearch.utils.Constants;
import com.lucene.indexandsearch.utils.TokenAnalyzerMaker;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.LMSimilarity.CollectionModel;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;

import java.io.*;

import static com.lucene.indexandsearch.searcher.RunSearcher.SimModel.BM25;

public class RunSearcher {

    protected Similarity simfn;
    protected IndexReader reader;
    protected IndexSearcher searcher;
    protected Analyzer analyzer;
    protected QueryParser parser;
    protected CollectionModel colModel;
    protected String fieldsFile;
    protected String qeFile;

    protected enum SimModel {
        BM25, BM25L
    }

    protected SimModel sim;

    private void setSim(String val) {
        try {
            sim = SimModel.valueOf(Constants.model);
        } catch (Exception e) {
            System.out.println("Similarity Function Not Recognized - Setting to Default");
            System.out.println("Possible Similarity Functions are:");
            for (SimModel value : SimModel.values()) {
                System.out.println("<model>" + value.name() + "</model>");
            }
            sim = BM25;
        }
    }

    public void selectSimilarityFunction(SimModel sim) {
        colModel = null;
        switch (sim) {

            case BM25:
                System.out.println("BM25 Similarity Function");
                simfn = new BM25Similarity(Constants.k, Constants.b);
                break;


            default:
                System.out.println("Default Similarity Function");
                simfn = new BM25Similarity();

                break;
        }
    }

    public void readParams() {


        setSim(Constants.model.toUpperCase());

        System.out.println("Path to index: " + Constants.indexName);
        System.out.println("Query File: " + Constants.queryFile);
        System.out.println("Result File: " + Constants.searchResultFile);
        System.out.println("Model: " + Constants.model);
        System.out.println("Max Results: " + Constants.maxResults);
        if (sim == BM25) {
            System.out.println("b value: " + Constants.b);
            System.out.println("k value: " + Constants.k);
        }
        if (Constants.tokenFilterFile != null) {
            TokenAnalyzerMaker tam = new TokenAnalyzerMaker();
//            analyzer = tam.createAnalyzer(Constants.tokenFilterFile);
            analyzer = new StandardAnalyzer();
        } else {
            analyzer = Constants.ANALYZER;
        }
    }

    public void processQueryFile() {

        System.out.println("Query File...");
        try {
            BufferedReader br = new BufferedReader(new FileReader(Constants.queryFile));
            File file = new File(Constants.searchResultFile);
            FileWriter fw = new FileWriter(file);
            int iii = 0;
            try {
                String line = br.readLine();
                while (line != null) {
                    iii++;
                    String[] parts = line.split(" ");
                    String qno = parts[1];
                    String queryTerms = "";
                    if (br.readLine().equalsIgnoreCase(".W")) {
//                        String lineChecker = br.readLine();
                        line = br.readLine();
                        do {
                            String[] queryParts = line.split(" ");
                            for (int i = 0; i < queryParts.length; i++)
                                queryTerms = queryTerms + " " + queryParts[i];
                            line = br.readLine();
                        } while (line != null && !line.startsWith(".I"));
                    }
                    System.out.println("QNo:" + qno);
                    ScoreDoc[] scored = runQuery(qno, queryTerms.trim());

                    int n = Math.min(Constants.maxResults, scored.length);

                    for (int i = 0; i < n; i++) {
                        Document doc = searcher.doc(scored[i].doc);
                        String docno = doc.get("docnum");
                        fw.write(iii + "\tQ0\t" + docno + "\t" + (i + 1) + "\t" + scored[i].score + "\t" + Constants.runTag);
                        fw.write(System.lineSeparator());
                    }
                    //line = br.readLine();
                }
            } finally {
                br.close();
                fw.close();
            }
            System.out.println("Sujit i: " + iii);

        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    public ScoreDoc[] runQuery(String qno, String queryTerms) {
        ScoreDoc[] hits = null;

        System.out.println("Query No.: " + qno + " " + queryTerms);
        try {
            Query query = parser.parse(QueryParser.escape(queryTerms));

            try {
                TopDocs results = searcher.search(query, Constants.maxResults);
                hits = results.scoreDocs;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(1);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
            System.exit(1);
        }
        return hits;
    }

    public RunSearcher() {
        System.out.println("Retrieval App");
        readParams();
        try {
            reader = DirectoryReader.open(FSDirectory.open(new File(Constants.indexName).toPath()));
            searcher = new IndexSearcher(reader);

            // create similarity function and parameter
            selectSimilarityFunction(sim);
            searcher.setSimilarity(simfn);

            parser = new QueryParser(Constants.FIELD_ALL, analyzer);

        } catch (Exception e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        RunSearcher searcher = new RunSearcher();
        searcher.processQueryFile();
    }
}