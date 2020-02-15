package wekalearning.classifiers;

import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 本範例用訓練集建構J48分類別器，預測測試集的類別別，並輸出實際的和預測的類別別標簽以及分佈
 */
public class OutputClassDistribution {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances train = DataSource
				.read("C:/Weka-3-7/data/segment-challenge.arff");
		Instances test = DataSource.read("C:/Weka-3-7/data/segment-test.arff");
		// 設定類別別索引
		train.setClassIndex(train.numAttributes() - 1);
		test.setClassIndex(test.numAttributes() - 1);
		// 檢查訓練集和測試集是否相容
		if (!train.equalHeaders(test))
			throw new Exception("訓練集和測試集不相容：" + train.equalHeadersMsg(test));

		// 訓練分類別器
		J48 classifier = new J48();
		classifier.buildClassifier(train);

		// 輸出預測
		System.out.println("編號\t-\t實際\t-\t預測\t-\t錯誤\t-\t分佈");
		for (int i = 0; i < test.numInstances(); i++) {
			// 得到預測值
			double pred = classifier.classifyInstance(test.instance(i));
			// 得到分佈
			double[] dist = classifier
					.distributionForInstance(test.instance(i));
			System.out.print((i + 1));
			System.out.print(" - ");
			System.out.print(test.instance(i).toString(test.classIndex()));
			System.out.print(" - ");
			System.out.print(test.classAttribute().value((int) pred));
			System.out.print(" - ");
			// 判斷是否預測錯誤
			if (pred != test.instance(i).classValue())
				System.out.print("是");
			else
				System.out.print("否");
			System.out.print(" - ");
			System.out.print(Utils.arrayToString(dist));
			System.out.println();
		}
	}
}
