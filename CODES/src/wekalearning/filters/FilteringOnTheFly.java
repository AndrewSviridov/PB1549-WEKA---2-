package wekalearning.filters;

import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

/**
 * 即時過濾案例程式。示範如何使用FilteredClassifier元過濾器， 程式使用了Remove過濾器和J48分類別器。
 */
public class FilteringOnTheFly {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances train = DataSource
				.read("C:/Weka-3-7/data/segment-challenge.arff");
		Instances test = DataSource.read("C:/Weka-3-7/data/segment-test.arff");
		// 設定類別別屬性
		train.setClassIndex(train.numAttributes() - 1);
		test.setClassIndex(test.numAttributes() - 1);
		// 檢查訓練集和測試集是否相容
		if (!train.equalHeaders(test))
			throw new Exception("訓練集和測試集不相容：\n" + train.equalHeadersMsg(test));

		// 過濾器
		Remove rm = new Remove();
		rm.setAttributeIndices("1"); // 移除第1個屬性

		// 分類別器
		J48 j48 = new J48();
		j48.setUnpruned(true); // 使用未裁剪的J48

		// 元分類別器
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(rm);
		fc.setClassifier(j48);

		// 訓練並預測
		fc.buildClassifier(train);
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = fc.classifyInstance(test.instance(i));
			System.out.print("編號：" + (i + 1));
			System.out.print("，實際類別別："
					+ test.classAttribute().value(
							(int) test.instance(i).classValue()));
			System.out.println("，預測類別別："
					+ test.classAttribute().value((int) pred));
		}
	}
}
