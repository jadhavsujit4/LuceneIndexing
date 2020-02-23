package com.lucene.indexandsearch.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class Constants {

    // Common analyzer
    public static final Analyzer ANALYZER = new StandardAnalyzer();

    // Field names
    public static final String FIELD_CONTENT = "content";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_AUTHOR = "author";
    public static final String FIELD_URL = "url";
    public static final String FIELD_DOCHDR = "dochdr";
    public static final String FIELD_DOCNUM = "docnum";
    public static final String FIELD_PUBDATE = "pubdate";
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_HDR = "hdr";
    public static final String FIELD_ANCHOR = "anchor";
    public static final String FIELD_ALL = "all";


    public static final String baseProjectPath = System.getProperty("user.dir");

    //Parameters For Searching
    public static final String indexName = "index";
    public static String queryFile = "data/cran/cran.qry";
    public static final String searchResultFile = "data/results/bm25_results";
    public static final String trecEvalPath = baseProjectPath + "/data/trec_eval-9.0.7/trec_eval";
    public static final String trecEvalRelevancePath = baseProjectPath + "/data/cran/cranqrel";
    public static final String trecEvalCommand = trecEvalPath + " -m official " + trecEvalRelevancePath + " " + searchResultFile;
    public static final String trecEvalResultFile = "data/results/Trec Eval.res";
    public static final String model = "bm25";
    public static final int maxResults = 50;
    public static final float k = 1.2f;
    public static final float b = 0.75f;
    public static final float lam = 0.5f;
    public static final float beta = 500f;
    public static final float mu = 500f;
    public static final float c = 10f;
    public static final float delta = 1.0f;
    public static final String runTag = "bm25";
    public static final String tokenFilterFile = "params/index/example_01.xml";


//    public static final String fieldsFile;
//    public static final String qeFile;


}

