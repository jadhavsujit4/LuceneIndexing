package com.lucene.indexandsearch.FBIS;

import com.lucene.indexandsearch.utils.Constants;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FBISReader {

    private static BufferedReader br;
    private static List<Document> fbisDocList = new ArrayList<>();
    private static final String[] IGNORE_FILES = {"readchg.txt", "readmefb.txt"};

    public static List<Document> loadFBISDocs(String fbisDirectory) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(fbisDirectory));
        for (String fbisFile : dir.listAll()) {
            if (!fbisFile.equals(IGNORE_FILES[0]) && !fbisFile.equals(IGNORE_FILES[1])) {
                br = new BufferedReader(new FileReader(fbisDirectory + "/" + fbisFile));
                FBISRead();
            }
        }
        return fbisDocList;
    }

    private static void FBISRead() throws IOException {

        String file = readAFile();
        org.jsoup.nodes.Document document = Jsoup.parse(file);

        List<Element> list = document.getElementsByTag("doc");

//        String date = "",day = "",month ="",year ="";

        for (Element doc : list) {

            FBISData fbisData = new FBISData();
            if (doc.getElementsByTag(FBISTags.DOCNO.name()) != null)
                fbisData.setDocNum(removeUnnecessaryTags(doc, FBISTags.DOCNO));
            if (doc.getElementsByTag(FBISTags.TEXT.name()) != null)
                fbisData.setText(removeUnnecessaryTags(doc, FBISTags.TEXT));
            if (doc.getElementsByTag(FBISTags.TI.name()) != null)
                fbisData.setTi(removeUnnecessaryTags(doc, FBISTags.TI));

            fbisData.setAll(fbisData.getDocNum() + " " + fbisData.getText() + " " + fbisData.getTi());

            fbisDocList.add(createFBISDocument(fbisData));
        }
    }

    private static String removeUnnecessaryTags(Element doc, FBISTags tag) {

        Elements element = doc.getElementsByTag(tag.name());
        Elements tempElement = element.clone();
        //remove any nested
        deleteNestedTags(tempElement, tag);
        String data = tempElement.toString();

        //remove any instance of "\n"
        if (data.contains("\n"))
            data = data.replaceAll("\n", "").trim();
        //remove start and end tags
        if (data.contains(("<" + tag.name() + ">").toLowerCase()))
            data = data.replaceAll("<" + tag.name().toLowerCase() + ">", "").trim();
        if (data.contains(("</" + tag.name() + ">").toLowerCase()))
            data = data.replaceAll("</" + tag.name().toLowerCase() + ">", "").trim();

        return data;
    }

    private static void deleteNestedTags(Elements element, FBISTags currTag) {

        for (FBISTags tag : FBISTags.values()) {
            if (element.toString().contains("<" + tag.name().toLowerCase() + ">") &&
                    element.toString().contains("</" + tag.name().toLowerCase() + ">") && !tag.equals(currTag)) {
                element.select(tag.toString()).remove();
            }
        }
    }

    private static Document createFBISDocument(FBISData fbisData) {
        Document document = new Document();
        document.add(new StringField(Constants.DOCNO_TEXT, fbisData.getDocNum(), Field.Store.YES));
        document.add(new TextField(Constants.HEADLINE_TEXT, fbisData.getTi(), Field.Store.YES));
        document.add(new TextField(Constants.FIELD_TEXT, fbisData.getText(), Field.Store.YES));
        document.add(new TextField(Constants.FIELD_ALL, fbisData.getAll(), Field.Store.YES));
        return document;
    }

    private static String readAFile() throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

}
