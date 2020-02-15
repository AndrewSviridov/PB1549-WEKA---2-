package wekalearning.clusterers;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.DensityBasedClusterer;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 三種評估聚類別器的模式
 */
public class ClusteringEvaluation {

	public static void main(String[] args) throws Exception {
		String filename = "C:/Weka-3-7/data/contact-lenses.arff";
		ClusterEvaluation clusterEval;
		Instances data;
		String[] options;
		DensityBasedClusterer dbc; // 基於密度的聚類別器
		double logLikelyhood;

		// 載入資料
		data = DataSource.read(filename);

		// 一般方法
		System.out.println("\n****** 一般方法");
		options = new String[2];
		options[0] = "-t"; // 指定訓練檔案
		options[1] = filename;
		String output = ClusterEvaluation.evaluateClusterer(new EM(), options);
		System.out.println(output);

		// 手動呼叫
		System.out.println("\n****** 手動呼叫");
		dbc = new EM();
		dbc.buildClusterer(data);
		clusterEval = new ClusterEvaluation();
		clusterEval.setClusterer(dbc);
		clusterEval.evaluateClusterer(new Instances(data));
		System.out.println(clusterEval.clusterResultsToString());

		// 基於密度的聚類別器交叉驗證
		System.out.println("\n****** 交叉驗證");
		dbc = new EM();
		logLikelyhood = ClusterEvaluation.crossValidateModel(dbc, data, 10,
				data.getRandomNumberGenerator(1234));
		System.out.println("對數似然: " + logLikelyhood);
	}
}
