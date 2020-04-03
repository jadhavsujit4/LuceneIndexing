package com.lucene.indexandsearch.utils;

import com.lucene.indexandsearch.indexer.SMJAnalyzer;
import org.apache.lucene.analysis.Analyzer;

public class Constants {

    // Common analyzer
    public static final Analyzer ANALYZER = new SMJAnalyzer();
//    static List<String> stopWordList = Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "about", "above", "across", "after", "again", "against", "all", "almost", "alone", "along", "already", "also", "although", "always", "among", "an", "and", "another", "any", "anybody", "anyone", "anything", "anywhere", "are", "area", "areas", "around", "as", "ask", "asked", "asking", "asks", "at", "away", "b", "back", "backed", "backing", "backs", "be", "became", "because", "become", "becomes", "been", "before", "began", "behind", "being", "beings", "best", "better", "between", "big", "both", "but", "by", "c", "came", "can", "cannot", "case", "cases", "certain", "certainly", "clear", "clearly", "come", "could", "d", "did", "differ", "different", "differently", "do", "does", "done", "down", "down", "downed", "downing", "downs", "during", "e", "each", "early", "either", "end", "ended", "ending", "ends", "enough", "even", "evenly", "ever", "every", "everybody", "everyone", "everything", "everywhere", "f", "face", "faces", "fact", "facts", "far", "felt", "few", "find", "finds", "first", "for", "four", "from", "full", "fully", "further", "furthered", "furthering", "furthers", "g", "gave", "general", "generally", "get", "gets", "give", "given", "gives", "go", "going", "good", "goods", "got", "great", "greater", "greatest", "group", "grouped", "grouping", "groups", "h", "had", "has", "have", "having", "he", "her", "here", "herself", "high", "high", "high", "higher", "highest", "him", "himself", "his", "how", "however", "i", "if", "important", "in", "interest", "interested", "interesting", "interests", "into", "is", "it", "its", "itself", "j", "just", "k", "keep", "keeps", "kind", "knew", "know", "known", "knows", "l", "large", "largely", "last", "later", "latest", "least", "less", "let", "lets", "like", "likely", "long", "longer", "longest", "m", "made", "make", "making", "man", "many", "may", "me", "member", "members", "men", "might", "more", "most", "mostly", "mr", "mrs", "much", "must", "my", "myself", "n", "necessary", "need", "needed", "needing", "needs", "never", "new", "new", "newer", "newest", "next", "no", "nobody", "non", "noone", "not", "nothing", "now", "nowhere", "number", "numbers", "o", "of", "off", "often", "old", "older", "oldest", "on", "once", "one", "only", "open", "opened", "opening", "opens", "or", "order", "ordered", "ordering", "orders", "other", "others", "our", "out", "over", "p", "part", "parted", "parting", "parts", "per", "perhaps", "place", "places", "point", "pointed", "pointing", "points", "possible", "present", "presented", "presenting", "presents", "problem", "problems", "put", "puts", "q", "quite", "r", "rather", "really", "right", "right", "room", "rooms", "s", "said", "same", "saw", "say", "says", "second", "seconds", "see", "seem", "seemed", "seeming", "seems", "sees", "several", "shall", "she", "should", "show", "showed", "showing", "shows", "side", "sides", "since", "small", "smaller", "smallest", "so", "some", "somebody", "someone", "something", "somewhere", "state", "states", "still", "still", "such", "sure", "t", "take", "taken", "than", "that", "the", "their", "them", "then", "there", "therefore", "these", "they", "thing", "things", "think", "thinks", "this", "those", "though", "thought", "thoughts", "three", "through", "thus", "to", "today", "together", "too", "took", "toward", "turn", "turned", "turning", "turns", "two", "u", "under", "until", "up", "upon", "us", "use", "used", "uses", "v", "very", "w", "want", "wanted", "wanting", "wants", "was", "way", "ways", "we", "well", "wells", "went", "were", "what", "when", "where", "whether", "which", "while", "who", "whole", "whose", "why", "will", "with", "within", "without", "work", "worked", "working", "works", "would", "x", "y", "year", "years", "yet", "you", "young", "younger", "youngest", "your", "yours", "z", "a's", "able", "about", "above", "according", "accordingly", "across", "actually", "after", "afterwards", "again", "against", "ain't", "all", "allow", "allows", "almost", "alone", "along", "already", "also", "although", "always", "am", "among", "amongst", "an", "and", "another", "any", "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere", "apart", "appear", "appreciate", "appropriate", "are", "aren't", "around", "as", "aside", "ask", "asking", "associated", "at", "available", "away", "awfully", "be", "became", "because", "become", "becomes", "becoming", "been", "before", "beforehand", "behind", "being", "believe", "below", "beside", "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by", "c'mon", "c's", "came", "can", "can't", "cannot", "cant", "cause", "causes", "certain", "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning", "consequently", "consider", "considering", "contain", "containing", "contains", "corresponding", "could", "couldn't", "course", "currently", "definitely", "described", "despite", "did", "didn't", "different", "do", "does", "doesn't", "doing", "don't", "done", "down", "downwards", "during", "each", "edu", "eg", "eight", "either", "else", "elsewhere", "enough", "entirely", "especially", "et", "etc", "even", "ever", "every", "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except", "far", "few", "fifth", "first", "five", "followed", "following", "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings", "had", "hadn't", "happens", "hardly", "has", "hasn't", "have", "haven't", "having", "he", "he's", "hello", "help", "hence", "her", "here", "here's", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his", "hither", "hopefully", "how", "howbeit", "however", "i'd", "i'll", "i'm", "i've", "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate", "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "isn't", "it", "it'd", "it'll", "it's", "its", "itself", "just", "keep", "keeps", "kept", "know", "knows", "known", "last", "lately", "later", "latter", "latterly", "least", "less", "lest", "let", "let's", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd", "mainly", "many", "may", "maybe", "me", "mean", "meanwhile", "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself", "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never", "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor", "normally", "not", "nothing", "novel", "now", "nowhere", "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones", "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves", "out", "outside", "over", "overall", "own", "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably", "probably", "provides", "que", "quite", "qv", "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively", "respectively", "right", "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing", "seem", "seemed", "seeming", "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously", "seven", "several", "shall", "she", "should", "shouldn't", "since", "six", "so", "some", "somebody", "somehow", "someone", "something", "sometime", "sometimes", "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying", "still", "sub", "such", "sup", "sure", "t's", "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that", "that's", "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "there's", "thereafter", "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "they'd", "they'll", "they're", "they've", "think", "third", "this", "thorough", "thoroughly", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward", "towards", "tried", "tries", "truly", "try", "trying", "twice", "two", "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up", "upon", "us", "use", "used", "useful", "uses", "using", "usually", "value", "various", "very", "via", "viz", "vs", "want", "wants", "was", "wasn't", "way", "we", "we'd", "we'll", "we're", "we've", "welcome", "well", "went", "were", "weren't", "what", "what's", "whatever", "when", "whence", "whenever", "where", "where's", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "who's", "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with", "within", "without", "won't", "wonder", "would", "would", "wouldn't", "yes", "yet", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves", "zero");
//    //stop filer needs to have a charArraySet argument of the stopwords
//    static CharArraySet stopWordSet = new CharArraySet(stopWordList, true);
//    public static final Analyzer ANALYZER = new EnglishAnalyzer(stopWordSet);

//    public static final String indexParamFile = "params/index/index_params.xml";

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
    public static String resultsDirectoryPath = "data/results";
    public static final String indexName = "index";
    public static String queryFile = "data/cran/cran.qry";
    public static String precisionRecallGraphImagePath = "data/results2/PRGraph";
    public static final String searchResultFile = "data/results2/query_results";
    public static final String trecEvalPath = "data/trec_eval-9.0.7/trec_eval ";
    public static final String trecEvalRelevancePath = "data/qrels.assignment2.part1";
    public static final String trecEvalCommand = trecEvalPath + trecEvalRelevancePath + " " + searchResultFile;
    //    public static final String trecEvalCommand = "data/trec_eval-9.0.7/trec_eval data/cran/cranqrel data/results/bm25_results_BM25";
    public static final String trecEvalResultFile = "data/results2/TrecEval_Result";
    public static final String TXTExtension = ".txt";
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

    public static final String MODELBM25 = "BM25";
    public static final String MODELCLASSIC = "CLASSIC";
    public static final String MODELLMJ = "LMJ";
    public static final String MODELLMD = "LMD";
    public static final String MODELMULTI = "MULTI";

    public static String MODELUSED = "";

    public static final int lengthFilterMinimumLength = 3;
    public static final int lengthFilterMaximumLength = 25;

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

    public static final String ANSI_RESET = "\u001B[0m";  // Text Reset

    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";
    ; // GREEN

    public static final String ANSI_RED_BACKGROUND = "\u001B[41m"; // RED BACKGROUND


    // Assignment 2
    //Dataset file path
    public static final String FBISFILESPATH = "rawdata/fbis";
    public static final String LATIMES_FILESPATH = "rawdata/latimes";
    public static final String FR94FILESPATH = "rawdata/fr94";
    public static final String FT_FILESPATH = "rawdata/ft";

    //Index types
    public static final String FBISINDEXTYPE = "fbis";
    public static final String LATTIMESINDEXTYPE = "lattimes";
    public static final String FR94INDEXTYPE = "fr94";
    public static final String FTINDEXTYPE = "ft";

    //indexing tags
    public static final String FIELD_TEXT = "text";
    public static final String DOCNO_TEXT = "docno";
    public static final String HEADLINE_TEXT = "headline";
    public static final String BYLINE_TEXT = "byline";
    public static final String GRAPHIC_TEXT = "graphic";
    public static final String SECTION_TEXT = "section";
    public static final String PARENT_TEXT = "parent";
    public static final String DOCTITLE_TEXT = "doctitle";
    public static final String USDEPT_TEXT = "usdept";
    public static final String USBUREAU_TEXT = "usbureau";
    public static final String AGENCY_TEXT = "agency";
    public static final String ADDRESS_TEXT = "address";
    public static final String FURTHER_TEXT = "further";
    public static final String SUMMARY_TEXT = "summary";
    public static final String ACTION_TEXT = "action";
    public static final String SIGNER_TEXT = "signer";
    public static final String SIGNJOB_TEXT = "signjob";
    public static final String SUPPLEM_TEXT = "supplem";
    public static final String BILLING_TEXT = "billing";
    public static final String FRFILING_TEXT = "frfiling";
    public static final String CFRNO_TEXT = "cfrno";
    public static final String RINDOCK_TEXT = "rindock";
    public static final String TABLE_TEXT = "table";
    public static final String FOOTNOTE_TEXT = "footnote";
    public static final String FOOTCITE_TEXT = "footcite";
    public static final String FOOTNAME_TEXT = "footname";
    public static final String PROFILE_TEXT = "profile";
    public static final String PUB_TEXT = "pub";
    public static final String PAGE_TEXT = "page";



    public static final int MAX_RETURN_RESULTS = 1000;
    public static final String ITER_NUM = " 0 ";
    public static final String searchResultFile2 = "data/results2/query_results";

    public static final String CRANINDEXTYPE = "cran";
    public static final String EXECUTION_TIME = " >> Execution Time in minutes: ";


    public static final String INDEXPATH = "/media/sujit/New Volume/Masters/Semester 2/IR/Assignment/Assignment/index";
    public static final String QUERYFILEPATH = "rawdata/topics";


}

