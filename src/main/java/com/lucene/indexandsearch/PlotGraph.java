package com.lucene.indexandsearch;


import com.lucene.indexandsearch.utils.Constants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.util.Map;

public class PlotGraph {

    public static void main(String[] args) throws Exception {

        RunTrecEval.runTrec();
        Map<Integer, Float> trecXYValues =  RunTrecEval.trecEvalXYValues;
        final XYSeries trecEvalMatrixValues = new XYSeries("My Search Engine Graph :(");
        for (int i = 0; i < trecXYValues.size(); i++) {
            trecEvalMatrixValues.add(i, trecXYValues.get(i));
        }
        final XYSeries idealPrecisionRecall = new XYSeries("Ideal Search Engine Graph");
        idealPrecisionRecall.add(0, 0.88);
        idealPrecisionRecall.add(1, 0.86);
        idealPrecisionRecall.add(2, 0.84);
        idealPrecisionRecall.add(3, 0.84);
        idealPrecisionRecall.add(4, 0.83);
        idealPrecisionRecall.add(5, 0.82);
        idealPrecisionRecall.add(6, 0.81);
        idealPrecisionRecall.add(7, 0.79);
        idealPrecisionRecall.add(8, 0.75);
        idealPrecisionRecall.add(9, 0.69);
        idealPrecisionRecall.add(9.3,0.64);
        idealPrecisionRecall.add(9.6,0.45);
        idealPrecisionRecall.add(9.8,0.25);
        idealPrecisionRecall.add(10,0.1);

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(trecEvalMatrixValues);
        dataset.addSeries(idealPrecisionRecall);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Recall VS Precision Curve",
                "Recall",
                "Precision",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */
        File XYChart = new File(Constants.precisionRecallGraphImagePath + "_" +Constants.MODELUSED + ".jpeg");
        ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Precision VS Recall graph image saved at: " + Constants.precisionRecallGraphImagePath + "_" +Constants.MODELUSED + ".jpeg" + Constants.ANSI_RESET);
    }
}