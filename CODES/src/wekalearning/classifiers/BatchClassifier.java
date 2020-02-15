package wekalearning.classifiers;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * 批次模式建構J48分類別器，並輸出決策樹模型
 */
public class BatchClassifier {

	public static void main(String[] args) throws Exception {
		// 載入資料
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File("C:/Weka-3-7/data/weather.nominal.arff"));
		Instances data = loader.getDataSet();
		data.setClassIndex(data.numAttributes() - 1);

		// 訓練J48分類別器
		String[] options = new String[1];
		options[0] = "-U"; // 未裁剪樹選項
		J48 tree = new J48(); // J48分類別器物件
		tree.setOptions(options); // 設定選項
		tree.buildClassifier(data); // 建構分類別器

		// 輸出產生模型
		System.out.println(tree);
	}
}
