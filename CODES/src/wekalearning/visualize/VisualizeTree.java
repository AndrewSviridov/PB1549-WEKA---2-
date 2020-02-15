package wekalearning.visualize;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.gui.treevisualizer.PlaceNode2;
import weka.gui.treevisualizer.TreeVisualizer;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * 訓練J48並可視化決策樹
 */
public class VisualizeTree {

	public static void main(String args[]) throws Exception {
		// 建構J48分類別器
		J48 cls = new J48();
		Instances data = DataSource
				.read("C:/Weka-3-7/data/weather.nominal.arff");
		data.setClassIndex(data.numAttributes() - 1);
		cls.buildClassifier(data);

		// 顯示樹
		TreeVisualizer tv = new TreeVisualizer(null, cls.graph(),
				new PlaceNode2());
		JFrame jf = new JFrame("J48分類別器樹可視化器");
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setSize(600, 400);
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(tv, BorderLayout.CENTER);
		jf.setVisible(true);

		// 調整樹
		tv.fitToScreen();
	}
}
