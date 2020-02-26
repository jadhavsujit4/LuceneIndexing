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
        final XYSeries trecEvalMatrixValues = new XYSeries("");
        for (int i = 0; i < trecXYValues.size(); i++) {
            trecEvalMatrixValues.add(i, trecXYValues.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(trecEvalMatrixValues);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Precision VS Recall Curve",
                "Recall",
                "Precision",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 640;   /* Width of the image */
        int height = 480;  /* Height of the image */
        File XYChart = new File(Constants.precisionRecallGraphImagePath);
        ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
        System.out.println(Constants.CYAN_BOLD_BRIGHT + "Precision VS Recall graph image saved at: " + Constants.precisionRecallGraphImagePath + Constants.ANSI_RESET);
    }
}