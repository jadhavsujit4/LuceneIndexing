package com.lucene.indexandsearch;

import com.lucene.indexandsearch.utils.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RunTrecEval {

    public static Map<Integer, Float> trecEvalXYValues = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        runTrec();
    }

    public static void runTrec() throws IOException {
        File file = new File(Constants.trecEvalResultFile);
        FileWriter fw = new FileWriter(file);
        OutputStream out = null;


        System.out.println(System.getProperty("user.dir"));
        String[] args1 = new String[]{"/bin/bash", "-c", "cd " + System.getProperty("user.dir") + "/data/trec_eval-9.0.7"};
        Process procss = new ProcessBuilder(args1).start();
        args1 = new String[]{"/bin/bash", "-c", Constants.trecEvalCommand + "_" + Constants.MODELBM25};
        procss = new ProcessBuilder(args1).start();
        String s = "";
        BufferedReader br = new BufferedReader(
                new InputStreamReader(procss.getInputStream()));
        try {
            Integer xStart = 0;
            while ((s = br.readLine()) != null) {
                String precision = "";
                if (s.contains("iprec")) {
                    precision = s.substring(s.length() - 6, s.length());
                    trecEvalXYValues.put(xStart, Float.parseFloat(precision));
                    xStart = xStart + 1;
                }
                System.out.println(s);
                fw.write(s);
                fw.write(System.lineSeparator());
            }
            System.out.println(trecEvalXYValues);
        } finally {
            br.close();
            fw.close();
        }
        System.out.println("Trec Eval results are stored at: " + Constants.trecEvalResultFile);
    }
}