package com.lucene.indexandsearch.ft;

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

public class FTReader {

    private static BufferedReader br;
    private static List<Document> ftDocList = new ArrayList<>();
    private static final String[] IGNORE_FILES = {"readchg.txt", "readmefb.txt"};

    public static List<Document> loadFTDocs(String ftDirectory) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(ftDirectory));
        for (String ftFile : dir.listAll()) {
            if (!ftFile.equals(IGNORE_FILES[0]) && !ftFile.equals(IGNORE_FILES[1])) {
                br = new BufferedReader(new FileReader(ftDirectory + "/" + ftFile));
                FTRead();
            }
        }
        return ftDocList;
    }

    private static void FTRead() throws IOException {

        String file = readAFile();
        org.jsoup.nodes.Document document = Jsoup.parse(file);
        List<Element> list = document.getElementsByTag("doc");
        for (Element doc : list) {
            FTData ftData = new FTData();
            if (doc.getElementsByTag(FTTags.DOCNO.name()) != null)
                ftData.setDocNum(removeUnnecessaryTags(doc, FTTags.DOCNO));
            if (doc.getElementsByTag(FTTags.HEADLINE.name()) != null)
                ftData.setHeadLine(removeUnnecessaryTags(doc, FTTags.HEADLINE));
            if (doc.getElementsByTag(FTTags.TEXT.name()) != null)
                ftData.setText(removeUnnecessaryTags(doc, FTTags.TEXT));
            if (doc.getElementsByTag(FTTags.BYLINE.name()) != null)
                ftData.setByLine(removeUnnecessaryTags(doc, FTTags.BYLINE));
            if (doc.getElementsByTag(FTTags.PROFILE.name()) != null)
                ftData.setProfile(removeUnnecessaryTags(doc, FTTags.PROFILE));
            //ftData.setAll(latimesData.getDocNum() + " " + latimesData.getText() + " " + latimesData.getTi());
            ftDocList.add(createFTDocument(ftData));
        }
    }

    private static String removeUnnecessaryTags(Element doc, FTTags tag) {

        Elements element = doc.getElementsByTag(tag.name());
        Elements tempElement = element.clone();
        //remove any nested
        deleteNestedTags(tempElement, tag);
        String data = tempElement.toString();

        //remove any instance of "\n"
        if (data.contains("\n"))
            data = data.replaceAll("\n", "").trim();
        //remove any instance of "<p>"
        if (data.contains("<p>"))
            data = data.replaceAll("<p>", "").trim();
        if (data.contains("</p>"))
            data = data.replaceAll("</p>", "").trim();
        //remove start and end tags
        if (data.contains(("<" + tag.name() + ">").toLowerCase()))
            data = data.replaceAll("<" + tag.name().toLowerCase() + ">", "").trim();
        if (data.contains(("</" + tag.name() + ">").toLowerCase()))
            data = data.replaceAll("</" + tag.name().toLowerCase() + ">", "").trim();
        data = data.trim().replaceAll(" +", " ");
        return data;
    }

    private static void deleteNestedTags(Elements element, FTTags currTag) {

        for (FTTags tag : FTTags.values()) {
            if (element.toString().contains("<" + tag.name().toLowerCase() + ">") &&
                    element.toString().contains("</" + tag.name().toLowerCase() + ">") && !tag.equals(currTag)) {
                element.select(tag.toString()).remove();
            }
        }
    }

    private static Document createFTDocument(FTData FTData) {
        Document document = new Document();
        document.add(new StringField(Constants.DOCNO_TEXT, FTData.getDocNum(), Field.Store.YES));
        document.add(new TextField(Constants.FIELD_TEXT, FTData.getText(), Field.Store.YES));
        document.add(new TextField(Constants.HEADLINE_TEXT, FTData.getHeadLine(), Field.Store.YES));
        if(!"".equals(FTData.getByLine()))
        document.add(new TextField(Constants.BYLINE_TEXT, FTData.getByLine(), Field.Store.YES));
        document.add(new TextField(Constants.PROFILE_TEXT, FTData.getProfile(), Field.Store.YES));
        //document.add(new TextField(Constants.PAGE_TEXT, FTData.getPage(), Field.Store.YES));
        //document.add(new TextField(Constants.PUB_TEXT, FTData.getPub(), Field.Store.YES));
        //document.add(new TextField(Constants.FIELD_ALL, latimesData.getAll(), Field.Store.YES));
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