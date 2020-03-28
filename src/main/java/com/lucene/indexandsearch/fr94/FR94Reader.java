package com.lucene.indexandsearch.fr94;

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

public class FR94Reader {
    private static BufferedReader br;
    private static List<Document> fr94DocList = new ArrayList<>();
//    private static final String[] IGNORE_FILES = {"readchg", "readmefr"}; //Not present within the folders

    public static List<Document> loadFR94Docs(String fr94Directory) throws IOException {
        for (int i = 1; i<=12; i++){
            if (i<=9) {
                Directory dir = FSDirectory.open(Paths.get(fr94Directory + "/0" + i));
                for (String fr94File : dir.listAll()) {
                    br = new BufferedReader(new FileReader(fr94Directory + "/0" + i + "/" + fr94File));
                    FR94Read();
                }
            }
            else {
                Directory dir = FSDirectory.open(Paths.get(fr94Directory + "/" + i));
                for (String fr94File : dir.listAll()) {
                    br = new BufferedReader(new FileReader(fr94Directory + "/" + i + "/" + fr94File));
                    FR94Read();
                }
            }
        }
        return fr94DocList;
    }

    private static void FR94Read() throws IOException {

        String file = readAFile();
        org.jsoup.nodes.Document document = Jsoup.parse(file);
        List<Element> list = document.getElementsByTag("doc");
        for (Element doc : list) {
            FR94Data fr94Data = new FR94Data();
            if (doc.getElementsByTag(FR94Tags.DOCNO.name()) != null)
                fr94Data.setDocno(removeUnnecessaryTags(doc, FR94Tags.DOCNO));
            if (doc.getElementsByTag(FR94Tags.PARENT.name()) != null)
                fr94Data.setParent(removeUnnecessaryTags(doc, FR94Tags.PARENT));
            if (doc.getElementsByTag(FR94Tags.TEXT.name()) != null)
                fr94Data.setText(removeUnnecessaryTags(doc, FR94Tags.TEXT));
            if (doc.getElementsByTag(FR94Tags.DOCTITLE.name()) != null)
                fr94Data.setDoctitle(removeUnnecessaryTags(doc, FR94Tags.DOCTITLE));
            if (doc.getElementsByTag(FR94Tags.USDEPT.name()) != null)
                fr94Data.setUsdept(removeUnnecessaryTags(doc, FR94Tags.USDEPT));
            if (doc.getElementsByTag(FR94Tags.AGENCY.name()) != null)
                fr94Data.setAgency(removeUnnecessaryTags(doc, FR94Tags.AGENCY));
            if (doc.getElementsByTag(FR94Tags.USBUREAU.name()) != null)
                fr94Data.setUsbureau(removeUnnecessaryTags(doc, FR94Tags.USBUREAU));
            if (doc.getElementsByTag(FR94Tags.SUMMARY.name()) != null)
                fr94Data.setSummary(removeUnnecessaryTags(doc, FR94Tags.SUMMARY));
            if (doc.getElementsByTag(FR94Tags.ACTION.name()) != null)
                fr94Data.setAction(removeUnnecessaryTags(doc, FR94Tags.ACTION));
            if (doc.getElementsByTag(FR94Tags.SUPPLEM.name()) != null)
                fr94Data.setSupplem(removeUnnecessaryTags(doc, FR94Tags.SUPPLEM));
            if (doc.getElementsByTag(FR94Tags.BILLING.name()) != null)
                fr94Data.setBilling(removeUnnecessaryTags(doc, FR94Tags.BILLING));
            if (doc.getElementsByTag(FR94Tags.FRFILING.name()) != null)
                fr94Data.setFrfiling(removeUnnecessaryTags(doc, FR94Tags.FRFILING));
            if (doc.getElementsByTag(FR94Tags.CFRNO.name()) != null)
                fr94Data.setCfrno(removeUnnecessaryTags(doc, FR94Tags.CFRNO));
            if (doc.getElementsByTag(FR94Tags.FOOTNOTE.name()) != null)
                fr94Data.setFootnote(removeUnnecessaryTags(doc, FR94Tags.FOOTNOTE));
            if (doc.getElementsByTag(FR94Tags.FOOTCITE.name()) != null)
                fr94Data.setFootcite(removeUnnecessaryTags(doc, FR94Tags.FOOTCITE));
            if (doc.getElementsByTag(FR94Tags.FOOTNAME.name()) != null)
                fr94Data.setFootname(removeUnnecessaryTags(doc, FR94Tags.FOOTNAME));
            /* Future if any of the following tags are required
            if (doc.getElementsByTag(FR94Tags.ADDRESS.name()) != null)
                fr94Data.setAddress(removeUnnecessaryTags(doc, FR94Tags.ADDRESS));
            if (doc.getElementsByTag(FR94Tags.FURTHER.name()) != null)
                fr94Data.setFurther(removeUnnecessaryTags(doc, FR94Tags.FURTHER));
            if (doc.getElementsByTag(FR94Tags.SIGNER.name()) != null)
                fr94Data.setSigner(removeUnnecessaryTags(doc, FR94Tags.SIGNER));
            if (doc.getElementsByTag(FR94Tags.SIGNJOB.name()) != null)
                fr94Data.setSignjob(removeUnnecessaryTags(doc, FR94Tags.SIGNJOB));
            if (doc.getElementsByTag(FR94Tags.DATE.name()) != null)
                fr94Data.setDate(removeUnnecessaryTags(doc, FR94Tags.DATE));
            if (doc.getElementsByTag(FR94Tags.RINDOCK.name()) != null)
                fr94Data.setRindock(removeUnnecessaryTags(doc, FR94Tags.RINDOCK));
            if (doc.getElementsByTag(FR94Tags.TABLE.name()) != null)
                fr94Data.setTable(removeUnnecessaryTags(doc, FR94Tags.TABLE));*/
            //fr94Data.setAll(fr94Data.getDocno() + " " + fr94Data.getText() + " " + fr94Data.getTi());
            fr94DocList.add(createFR94Document(fr94Data));
        }
    }

    private static String removeUnnecessaryTags(Element doc, FR94Tags tag) {

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

    private static void deleteNestedTags(Elements element, FR94Tags currTag) {

        for (FR94Tags tag : FR94Tags.values()) {
            if (element.toString().contains("<" + tag.name().toLowerCase() + ">") &&
                    element.toString().contains("</" + tag.name().toLowerCase() + ">") && !tag.equals(currTag)) {
                element.select(tag.toString()).remove();
            }
        }
    }

    private static Document createFR94Document(FR94Data fr94Data) {
        Document document = new Document();
        document.add(new StringField(Constants.DOCNO_TEXT, fr94Data.getDocno(), Field.Store.YES));
        document.add(new TextField(Constants.FIELD_TEXT, fr94Data.getText(), Field.Store.YES));
        document.add(new TextField(Constants.PARENT_TEXT, fr94Data.getParent(), Field.Store.YES));
        document.add(new StringField(Constants.DOCTITLE_TEXT, fr94Data.getDoctitle(), Field.Store.YES));
        document.add(new StringField(Constants.USDEPT_TEXT, fr94Data.getUsdept(), Field.Store.YES));
        document.add(new TextField(Constants.USBUREAU_TEXT, fr94Data.getUsbureau(), Field.Store.YES));
        document.add(new TextField(Constants.AGENCY_TEXT, fr94Data.getAgency(), Field.Store.YES));
        document.add(new TextField(Constants.SUMMARY_TEXT, fr94Data.getSummary(), Field.Store.YES));
        document.add(new TextField(Constants.SUPPLEM_TEXT, fr94Data.getSupplem(), Field.Store.YES));
        document.add(new TextField(Constants.ACTION_TEXT, fr94Data.getAction(), Field.Store.YES));
        document.add(new TextField(Constants.BILLING_TEXT, fr94Data.getBilling(), Field.Store.YES));
        document.add(new TextField(Constants.FRFILING_TEXT, fr94Data.getFrfiling(), Field.Store.YES));
        document.add(new TextField(Constants.CFRNO_TEXT, fr94Data.getCfrno(), Field.Store.YES));
        document.add(new TextField(Constants.FOOTNOTE_TEXT, fr94Data.getFootnote(), Field.Store.YES));
        document.add(new TextField(Constants.FOOTCITE_TEXT, fr94Data.getFootcite(), Field.Store.YES));
        document.add(new TextField(Constants.FOOTNAME_TEXT, fr94Data.getFootname(), Field.Store.YES));
        //document.add(new TextField(Constants.FIELD_ALL, fr94Data.getAll(), Field.Store.YES));
        return document;
    }

    private static String readAFile() throws IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                if (line.startsWith("<!--")) {
                    line = br.readLine();
                    continue;
                }
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