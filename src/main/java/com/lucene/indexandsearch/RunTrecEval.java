package com.lucene.indexandsearch;

import com.lucene.indexandsearch.utils.Constants;

import java.io.*;

public class RunTrecEval {


    public static void main(String[] args) throws IOException {
        File file = new File(Constants.trecEvalResultFile);
        FileWriter fw = new FileWriter(file);
        OutputStream out = null;
//        Process proc = new ProcessBuilder("Terminal").start();
//        out = proc.getOutputStream();
//        out.write("any command".getBytes());
//        out.flush();

        System.out.println(System.getProperty("user.dir"));
        String[] args1 = new String[]{"/bin/bash", "-c", "cd " + System.getProperty("user.dir") + "/data/trec_eval-9.0.7"};
        Process procss = new ProcessBuilder(args1).start();
        args1 = new String[]{"/bin/bash", "-c", "pwd"};
        procss = new ProcessBuilder(args1).start();
        String s = "";
        BufferedReader br = new BufferedReader(
                new InputStreamReader(procss.getInputStream()));
        while ((s = br.readLine()) != null) {
            System.out.println("line: " + s);
            fw.write(s);
            fw.write(System.lineSeparator());
        }


        args1 = new String[]{"/bin/bash", "-c", "./trec_eval " + System.getProperty("user.dir") + "/data/cran/cranqrel " + System.getProperty("user.dir") + "/data/results/bm25_results.res"};
//        String[] args1 = new String[]{"/bin/bash", "-c", "ls"};
        procss = new ProcessBuilder(args1).start();
//        out = procss.getOutputStream();
//        out.write("any command".getBytes());
//        out.flush();
        s = "";
        br = new BufferedReader(
                new InputStreamReader(procss.getInputStream()));
        while ((s = br.readLine()) != null) {
            System.out.println("line: " + s);
            fw.write(s);
            fw.write(System.lineSeparator());
        }
//        String s;
//        Process p;
//        try {
//            p = Runtime.getRuntime().exec("./trec_eval -m official /home/sujit/Downloads/cranqrel /home/sujit/IdeaProjects/LuceneIndexing/data/cran/bm25_results.res");
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(p.getInputStream()));
//            while ((s = br.readLine()) != null)
//                System.out.println("line: " + s);
//            p.waitFor();
//            System.out.println ("exit: " + p.exitValue());
//            p.destroy();
//        } catch (Exception e) {}
    }
}