package wekalearning.clusterers;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * 本例展示如何執行“classes-to-clusters”評估
 */
public class ClassesToClusters {
	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances data = DataSource
				.read("C:/Weka-3-7/data/contact-lenses.arff");
		data.setClassIndex(data.numAttributes() - 1);

		// 產生聚類別器資料,過濾以去除類別別屬性
		Remove filter = new Remove();
		filter.setAttributeIndices("" + (data.classIndex() + 1));
		filter.setInputFormat(data);
		Instances dataClusterer = Filter.useFilter(data, filter);

		// 訓練聚類別器
		EM clusterer = new EM();
		// 若果有必要,可在這裡設定更多選項
		// 建構聚類別器
		clusterer.buildClusterer(dataClusterer);

		// 評估聚類別器
		ClusterEvaluation eval = new ClusterEvaluation();
		eval.setClusterer(clusterer);
		eval.evaluateClusterer(data);

		// 輸出結果
		System.out.println(eval.clusterResultsToString());
	}
}
