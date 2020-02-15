package wekalearning.filters;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Standardize;

/**
 * 批次過濾。訓練集用於起始化過濾器，並使用過濾器來過濾測試集
 */
public class BatchFiltering {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances train = DataSource
				.read("C:/Weka-3-7/data/segment-challenge.arff");
		Instances test = DataSource.read("C:/Weka-3-7/data/segment-test.arff");

		// 過濾資料
		// 使用標准化過濾器
		Standardize filter = new Standardize();
		// 使用訓練集一次起始化過濾器
		filter.setInputFormat(train);
		// 基於訓練集組態過濾器，並傳回過濾後的案例
		Instances newTrain = Filter.useFilter(train, filter);
		// 過濾並建立新測試集
		Instances newTest = Filter.useFilter(test, filter);

		// 輸出資料集
		System.out.println("新訓練集：");
		System.out.println(newTrain);
		System.out.println("新測試集：");
		System.out.println(newTest);
	}
}
