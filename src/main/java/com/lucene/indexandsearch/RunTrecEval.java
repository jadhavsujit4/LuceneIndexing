package com.lucene.indexandsearch;

import com.lucene.indexandsearch.utils.Constants;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class RunTrecEval {

    public static Map<Integer, Float> trecEvalXYValues = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        runTrec();
    }

    public static void runTrec() throws IOException {
        File file = new File(Constants.trecEvalResultFile + "_" +Constants.MODELUSED + Constants.TXTExtension);
        FileWriter fw = new FileWriter(file);
        OutputStream out = null;


        String[] args1 = new String[]{"/bin/bash", "-c", "cd " + System.getProperty("user.dir") + "/data/trec_eval-9.0.7"};
        Process procss = new ProcessBuilder(args1).start();
        args1 = new String[]{"/bin/bash", "-c", Constants.trecEvalCommand + "_" + Constants.MODELUSED};
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
                System.out.println(Constants.CYAN_BOLD_BRIGHT + s + Constants.ANSI_RESET);
                fw.write(s);
                fw.write(System.lineSeparator());
            }
//            System.out.println(trecEvalXYValues);
        } finally {
            br.close();
            fw.close();
        }
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Trec Eval results are stored at: " + Constants.trecEvalResultFile + "_" +Constants.MODELUSED + Constants.TXTExtension + Constants.ANSI_RESET);
    }
}