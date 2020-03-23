package com.lucene.indexandsearch.indexer;


import com.lucene.indexandsearch.utils.Constants;
import com.lucene.indexandsearch.utils.TokenAnalyzerMaker;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

//import org.apache.commons.compress.compressors.z.ZCompressorInputStream;


public class DocumentIndexer {

    protected boolean indexPositions;
    public IndexWriter writer;
    public Analyzer analyzer;

    public DocumentIndexer() {
    }

    public DocumentIndexer(String indexPath, String tokenFilterFile, boolean positional) {
        writer = null;
        analyzer = Constants.ANALYZER;
        indexPositions = positional;

        if (tokenFilterFile != null) {
            TokenAnalyzerMaker tam = new TokenAnalyzerMaker();
//            List<String> stopWordList = Arrays.asList("a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves");
//            CharArraySet stopWordSet = new CharArraySet(stopWordList, true);
//            analyzer = new StandardAnalyzer(stopWordSet);
//            analyzer = new StandardAnalyzer();
            analyzer = Constants.ANALYZER;

        }
        createWriter(indexPath);
    }


    public void createWriter(String indexPath) {

        try {
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            System.out.println("Indexing to directory '" + indexPath + "'...");

            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            Similarity[] sims =  {new BM25Similarity(), new ClassicSimilarity()} ;
//            MultiSimilarity();
            iwc.setSimilarity(new MultiSimilarity(sims));
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            writer = new IndexWriter(dir, iwc);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addDocToIndex(Document doc) {
        try {
            writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void indexDocumentsFromFile(String filename) {
        /* to be implemented in sub classess*/
    }

    protected BufferedReader openDocumentFile(String filename) {
        BufferedReader br = null;
        try {
            if (filename.endsWith(".gz")) {
                InputStream fileStream = new FileInputStream(filename);
                InputStream gzipStream = new GZIPInputStream(fileStream);
                Reader decoder = new InputStreamReader(gzipStream, StandardCharsets.UTF_8);
                br = new BufferedReader(decoder);
            } else {
                // For the weirdness that is TREC collections.
//                if (filename.endsWith(".Z") || filename.endsWith(".0Z") || filename.endsWith(".1Z") || filename.endsWith(".2Z")) {
//                    InputStream fileStream = new FileInputStream(filename);
//                    //InputStream zipStream = new ZCompressorInputStream(fileStream);
//                    ZCompressorInputStream zipStream = new ZCompressorInputStream(fileStream);
//                    Reader decoder = new InputStreamReader(zipStream, "UTF-8");
//                    br = new BufferedReader(decoder);
//                }
//                else
                br = new BufferedReader(new FileReader(filename));
            }


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return br;
    }


    public void finished() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}