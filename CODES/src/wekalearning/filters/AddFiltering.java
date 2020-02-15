package wekalearning.filters;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;

import java.util.Random;

/**
 * 加入一個數值屬性和一個標稱屬性到資料集中，並用隨機值填充後輸出
 */
public class AddFiltering {

	public static void main(String[] args) throws Exception {
		// 載入資料集
		Instances data = DataSource
				.read("C:/Weka-3-7/data/weather.numeric.arff");
		Instances result = null;

		Add filter;
		result = new Instances(data);

		// 新增數值屬性
		filter = new Add();
		filter.setAttributeIndex("last");
		filter.setAttributeName("NumericAttribute");
		filter.setInputFormat(result);
		result = Filter.useFilter(result, filter);
		// 新增標稱屬性
		filter = new Add();
		filter.setAttributeIndex("last");
		filter.setNominalLabels("A,B,C"); // 設定標簽
		filter.setAttributeName("NominalAttribute");
		filter.setInputFormat(result);
		result = Filter.useFilter(result, filter);

		// 用隨機值填充新增的兩個屬性
		Random rand = new Random(1234);
		for (int i = 0; i < result.numInstances(); i++) {
			// 填充數值屬性
			result.instance(i).setValue(result.numAttributes() - 2,
					rand.nextDouble());
			// 填充標稱屬性
			result.instance(i).setValue(result.numAttributes() - 1,
					rand.nextInt(3)); // 標簽索引： A:0、B:1、C:2
		}

		// 輸出資料
		System.out.println("過濾後的資料集：");
		System.out.println(result);
	}
}
