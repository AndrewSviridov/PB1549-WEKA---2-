package wekalearning.visualize;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;

import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;

/**
 * 由資料集產生並顯示ROC曲線，使用NaiveBayes的預設設定來產生ROC資料
 */
public class VisualizeROC {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances data = DataSource
				.read("C:/Weka-3-7/data/weather.nominal.arff");
		data.setClassIndex(data.numAttributes() - 1);

		// 評估分類別器
		Classifier classifier = new NaiveBayes();
		Evaluation eval = new Evaluation(data);
		eval.crossValidateModel(classifier, data, 10, new Random(1234));

		// 第一步，產生可繪制的資料
		ThresholdCurve tc = new ThresholdCurve();
		int classIndex = 0;
		Instances curve = tc.getCurve(eval.predictions(), classIndex);

		// 第二步，將可繪制資料放入繪圖容器
		PlotData2D plotdata = new PlotData2D(curve);
		plotdata.setPlotName(curve.relationName());
		plotdata.addInstanceNumberAttribute();

		// 第三步，將繪圖容器加入至可視化面板
		ThresholdVisualizePanel tvp = new ThresholdVisualizePanel();
		tvp.setROCString("(Area under ROC = "
				+ Utils.doubleToString(ThresholdCurve.getROCArea(curve), 4)
				+ ")");
		tvp.setName(curve.relationName());
		// 指定連線哪些點
		boolean[] cp = new boolean[curve.numInstances()];
		for (int i = 1; i < cp.length; i++)
			cp[i] = true;
		plotdata.setConnectPoints(cp);
		// 加入繪圖
		tvp.addPlot(plotdata);

		// 第四步，將可視化面板新增到JFrame
		final JFrame jf = new JFrame("WEKA ROC: " + tvp.getName());
		// 設定窗體的大小為500*400像素
		jf.setSize(500, 400);
		// 設定佈局管理器
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(tvp, BorderLayout.CENTER);
		// 自動隱藏並釋放該窗體
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 設定窗體可見
		jf.setVisible(true);
	}
}
