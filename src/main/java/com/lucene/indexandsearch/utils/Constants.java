package com.lucene.indexandsearch.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class Constants {

    // Common analyzer
    public static final Analyzer ANALYZER = new StandardAnalyzer();

    public static final String indexParamFile = "params/index/index_params.xml";

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
    public static final String MODELBM25 = "bm25";
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

    public static final String MODELLMJ = "LMJ";
    public static final String MODELLMD = "LMD";


//    public static Analyzer makeAnalyzer() throws IOException {
//        List<String> stopWordList = Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves");
////        //stop filer needs to have a charArraySet argument of the stopwords
//        CharArraySet stopWordSet = new CharArraySet( stopWordList, true);
//
//        Map<String,String> stopConfig1 = new HashMap<>();
//        stopConfig1.put("ignoreCase", "true");
//        stopConfig1.put("words", "data/stopwordsFinal.txt");
//        stopConfig1.put("format", "wordset");
//
//        Map<String,String> stopConfig2 = new HashMap<>(stopConfig1);
//        Map<String,String> stopConfigImmutable = Collections.unmodifiableMap(new HashMap<>(stopConfig1));
//
//
//        return CustomAnalyzer.builder()
//                .withTokenizer(WhitespaceTokenizerFactory.class).addTokenFilter(PorterStemFilterFactory.class)
//                .addTokenFilter(LowerCaseFilterFactory.class)
//                .addTokenFilter(ASCIIFoldingFilterFactory.class)
//                .addTokenFilter("stop",
//                stopConfig2)
//                .build();
//    }

//    public static final String fieldsFile;
//    public static final String qeFile;


}

