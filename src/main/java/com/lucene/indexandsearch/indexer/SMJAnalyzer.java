package com.lucene.indexandsearch.indexer;

import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter;
import org.apache.lucene.analysis.miscellaneous.LengthFilter;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilter;
import org.apache.lucene.analysis.standard.ClassicFilter;
import org.apache.lucene.analysis.standard.ClassicTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.util.Arrays;
import java.util.List;


public class SMJAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer stdTokenizer = new ClassicTokenizer();
//        Tokenizer stdTokenizer = new StandardTokenizer();
        TokenStream tokenStream = new ClassicFilter(stdTokenizer);
        tokenStream = new ASCIIFoldingFilter(tokenStream);
        //from https://github.com/Yoast/YoastSEO.js/blob/develop/src/config/stopwords.js
        List<String> stopWordList = Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves");
        //stop filer needs to have a charArraySet argument of the stopwords
        CharArraySet stopWordSet = new CharArraySet(stopWordList, true);
        //from https://stackoverflow.com/questions/31957986/how-to-combine-analyzer-instances-for-stop-word-removal-and-stemming-in-lucene
//        tokenStream = new WordDelimiterFilter(tokenStream, WordDelimiterFilter.ALPHA, null);
        tokenStream = new LengthFilter(tokenStream, Constants.lengthFilterMinimumLength, Constants.lengthFilterMaximumLength);
        tokenStream = new LowerCaseFilter(tokenStream);
        tokenStream = new StopFilter(tokenStream, stopWordSet);
//        tokenStream = new EnglishPossessiveFilter(tokenStream);
        tokenStream = new KStemFilter(tokenStream);
        tokenStream = new PorterStemFilter(tokenStream);
        return new TokenStreamComponents(stdTokenizer, tokenStream);
    }
}
