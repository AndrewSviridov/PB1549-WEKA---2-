package wekalearning.clusterers;

import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 本例展示在訓練集上建構EM聚類別器，然後在測試集上預測簇並輸出簇的隸屬度
 */
public class OutputClusterDistribution {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances train = DataSource
				.read("C:/Weka-3-7/data/segment-challenge.arff");
		Instances test = DataSource.read("C:/Weka-3-7/data/segment-test.arff");
		if (!train.equalHeaders(test))
			throw new Exception("訓練集和測試集不相容：" + train.equalHeadersMsg(test));

		// 建構聚類別器
		EM clusterer = new EM();
		clusterer.buildClusterer(train);

		// 輸出預測
		System.out.println("編號 - 簇  \t-\t 分佈");
		for (int i = 0; i < test.numInstances(); i++) {
			int cluster = clusterer.clusterInstance(test.instance(i));
			double[] dist = clusterer.distributionForInstance(test.instance(i));
			System.out.print((i + 1));
			System.out.print(" - ");
			System.out.print(cluster);
			System.out.print(" - ");
			System.out.print(Utils.arrayToString(dist));
			System.out.println();
		}
	}
}
