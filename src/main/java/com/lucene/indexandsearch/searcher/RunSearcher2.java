package com.lucene.indexandsearch.searcher;


import com.lucene.indexandsearch.query.QueryData;
import com.lucene.indexandsearch.query.QueryReader;
import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lucene.indexandsearch.searcher.RunSearcher.SimModel.BM25;


public class RunSearcher2 {


    protected Similarity simfn;
    protected IndexReader reader;
    protected IndexSearcher searcher;
    protected static Analyzer analyzer;
    protected QueryParser parser;
    protected LMSimilarity.CollectionModel colModel;
    protected String fieldsFile;
    protected String qeFile;

    protected enum SimModel {
        CLASSIC, BM25, LMD, LMJ, MULTI
    }

    protected RunSearcher.SimModel sim;

    private void setSim(String val) {
        try {
            sim = RunSearcher.SimModel.valueOf(val);
        } catch (Exception e) {
            System.out.println("Similarity Function Not Recognized - Setting to Default");
            System.out.println("Possible Similarity Functions are:");
            for (RunSearcher.SimModel value : RunSearcher.SimModel.values()) {
                System.out.println("<MODELBM25>" + value.name() + "</MODELBM25>");
            }
            sim = BM25;
        }
    }

    public void selectSimilarityFunction(RunSearcher.SimModel sim) {
        colModel = null;
        switch (sim) {

            case CLASSIC:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "Classic Similarity Function" + Constants.ANSI_RESET);
                simfn = new ClassicSimilarity();
                break;
            case BM25:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "BM25 Similarity Function" + Constants.ANSI_RESET);
                simfn = new BM25Similarity(Constants.k, Constants.b);
                break;
            case LMD:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "LM Dirichlet Similarity Function" + Constants.ANSI_RESET);
                colModel = new LMSimilarity.DefaultCollectionModel();
                simfn = new LMDirichletSimilarity(colModel, Constants.mu);
                break;

            case LMJ:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "LM Jelinek Mercer Similarity Function" + Constants.ANSI_RESET);
                colModel = new LMSimilarity.DefaultCollectionModel();
                simfn = new LMJelinekMercerSimilarity(colModel, Constants.lam);
                break;
            case MULTI:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "BM25 Similarity function With Classic Similarity Function" + Constants.ANSI_RESET);
                simfn = new LMJelinekMercerSimilarity(colModel, Constants.lam);
                break;

            default:
                System.out.println(Constants.CYAN_BOLD_BRIGHT + "Default Similarity Function" + Constants.ANSI_RESET);
                simfn = new BM25Similarity();

                break;
        }
    }

    public void setParams(String similarityToUse) {


//        setSim(Constants.MODELBM25.toUpperCase());
        setSim(similarityToUse.toUpperCase());

//        System.out.println("Path to index: " + Constants.indexName);
//        System.out.println("Query File: " + Constants.queryFile);
//        System.out.println("Result File: " + Constants.searchResultFile);
//        System.out.println("Model: " + Constants.MODELBM25);
//        System.out.println("Max Results: " + Constants.maxResults);
//        if (sim == BM25) {
//            System.out.println("b value: " + Constants.b);
//            System.out.println("k value: " + Constants.k);
//        }


        analyzer = Constants.ANALYZER;
    }


    private static void executeQueries(Directory directory) throws ParseException {
        try {
            IndexReader indexReader = DirectoryReader.open(directory);
            Similarity[] sims = {new BM25Similarity(), new ClassicSimilarity()};
            Similarity similarityModel = new MultiSimilarity(sims);
            IndexSearcher indexSearcher = createIndexSearcher(indexReader, similarityModel);
            analyzer = Constants.ANALYZER;

            Map<String, Float> boost = createBoostMap();
            QueryParser queryParser = new MultiFieldQueryParser(new String[]{"headline", "text"}, analyzer, boost);

            PrintWriter writer = new PrintWriter(Constants.searchResultFile2, "UTF-8");
            List<QueryData> loadedQueries = QueryReader.loadQueriesFromFile();

            for (QueryData queryData : loadedQueries) {

                List<String> splitNarrative = splitNarrIntoRelNotRel(queryData.getNarrative());
                String relevantNarr = splitNarrative.get(0).trim();

                BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();

                if (queryData.getTitle().length() > 0) {

                    Query titleQuery = queryParser.parse(QueryParser.escape(queryData.getTitle()));
                    Query descriptionQuery = queryParser.parse(QueryParser.escape(queryData.getDescription()));
                    Query narrativeQuery = null;
                    if (relevantNarr.length() > 0) {
                        narrativeQuery = queryParser.parse(QueryParser.escape(relevantNarr));
                    }

                    booleanQuery.add(new BoostQuery(titleQuery, (float) 4), BooleanClause.Occur.SHOULD);
                    booleanQuery.add(new BoostQuery(descriptionQuery, (float) 1.7), BooleanClause.Occur.SHOULD);

                    if (narrativeQuery != null) {
                        booleanQuery.add(new BoostQuery(narrativeQuery, (float) 1.2), BooleanClause.Occur.SHOULD);
                    }
                    ScoreDoc[] hits = indexSearcher.search(booleanQuery.build(), Constants.MAX_RETURN_RESULTS).scoreDocs;

                    for (int hitIndex = 0; hitIndex < hits.length; hitIndex++) {
                        ScoreDoc hit = hits[hitIndex];
                        writer.println(queryData.getQueryNum() + Constants.ITER_NUM + indexSearcher.doc(hit.doc).get("docno") +
                                " " + hitIndex + " " + hit.score + Constants.ITER_NUM);
                    }
                }
            }

//            closeIndexReader(indexReader);
//            closePrintWriter(writer);
            System.out.println("queries executed");

        } catch (IOException e) {
            System.out.println("ERROR: an error occurred when instantiating the printWriter!");
            System.out.println(String.format("ERROR MESSAGE: %s", e.getMessage()));
        }
    }

    private static List<String> splitNarrIntoRelNotRel(String narrative) {
        StringBuilder relevantNarr = new StringBuilder();
        StringBuilder irrelevantNarr = new StringBuilder();
        List<String> splitNarrative = new ArrayList<>();

        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(narrative);
        int index = 0;
        while (bi.next() != BreakIterator.DONE) {
            String sentence = narrative.substring(index, bi.current());

            if (!sentence.contains("not relevant") && !sentence.contains("irrelevant")) {
                relevantNarr.append(sentence.replaceAll(
                        "a relevant document identifies|a relevant document could|a relevant document may|a relevant document must|a relevant document will|a document will|to be relevant|relevant documents|a document must|relevant|will contain|will discuss|will provide|must cite",
                        ""));
            } else {
                irrelevantNarr.append(sentence.replaceAll("are also not relevant|are not relevant|are irrelevant|is not relevant|not|NOT", ""));
            }
            index = bi.current();
        }
        splitNarrative.add(relevantNarr.toString());
        splitNarrative.add(irrelevantNarr.toString());
        return splitNarrative;
    }

    static Map<String, Float> createBoostMap() {
        Map<String, Float> boost = new HashMap<>();
        boost.put("headline", (float) 0.1);
        boost.put("text", (float) 0.9);
        return boost;
    }

    static IndexSearcher createIndexSearcher(IndexReader indexReader, Similarity similarityModel) {
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        indexSearcher.setSimilarity(similarityModel);
        return indexSearcher;
    }


    public static void main(String[] args) {
        System.out.println("loading and executing queries");
        try {
            executeQueries(null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
