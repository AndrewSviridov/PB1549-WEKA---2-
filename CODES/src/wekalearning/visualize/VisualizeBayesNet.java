package wekalearning.visualize;

import weka.classifiers.bayes.BayesNet;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.graphvisualizer.GraphVisualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * 顯示訓練好的貝葉斯網路圖形
 */
public class VisualizeBayesNet {

	public static void main(String args[]) throws Exception {
		// 貝葉斯網路分類別器
		BayesNet cls = new BayesNet();
		// 資料集
		Instances data = DataSource
				.read("C:/Weka-3-7/data/weather.nominal.arff");
		// 設定類別別屬性
		data.setClassIndex(data.numAttributes() - 1);
		// 建構分類別器
		cls.buildClassifier(data);

		// 顯示圖形
		// 圖可視化器
		GraphVisualizer gv = new GraphVisualizer();
		gv.readBIF(cls.graph());
		// 定義一個窗體物件jframe，窗體名稱為"貝葉斯網路圖形"
		JFrame jframe = new JFrame("貝葉斯網路圖形");
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// 設定窗體的大小為500*300像素
		jframe.setSize(500, 300);
		jframe.getContentPane().setLayout(new BorderLayout());
		jframe.getContentPane().add(gv, BorderLayout.CENTER);
		// 設定窗體可見
		jframe.setVisible(true);

		// 佈局圖形
		gv.layoutGraph();
	}
}
