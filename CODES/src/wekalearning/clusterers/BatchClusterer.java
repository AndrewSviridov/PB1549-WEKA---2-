package wekalearning.clusterers;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.clusterers.EM;

import java.io.File;

/**
 * 批次訓練EM算法
 */
public class BatchClusterer {

	public static void main(String[] args) throws Exception {
		// 載入資料
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File("C:/Weka-3-7/data/contact-lenses.arff"));
		Instances data = loader.getDataSet();

		// 建構聚類別器
		String[] options = new String[2];
		options[0] = "-I"; // 最大迭代次數
		options[1] = "100";
		EM clusterer = new EM(); // 聚類別器的新案例
		clusterer.setOptions(options); // 設定選項
		clusterer.buildClusterer(data);

		// 輸出產生模型
		System.out.println(clusterer);
	}
}
