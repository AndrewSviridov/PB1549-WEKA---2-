package wekalearning.serialization;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 分類別器及標頭資訊序列化和反序列化範例
 */
public class HeaderSerialization {

	public static void main(String[] args) throws Exception {
		// 載入訓練集
		Instances train = DataSource
				.read("C:/Weka-3-7/data/segment-challenge.arff");
		train.setClassIndex(train.numAttributes() - 1);
		// 訓練J48
		Classifier cls = new J48();
		cls.buildClassifier(train);
		// 序列化分類別器及標頭資訊
		Instances header = new Instances(train, 0);
		SerializationHelper.writeAll("C:/Weka-3-7/data/j48.model",
				new Object[] { cls, header });
		System.out.println("序列化分類別器及標頭資訊成功！\n");

		// 載入測試集
		Instances test = DataSource.read("C:/Weka-3-7/data/segment-test.arff");
		test.setClassIndex(test.numAttributes() - 1);
		// 反序列化模型
		Object o[] = SerializationHelper.readAll("C:/Weka-3-7/data/j48.model");
		Classifier cls2 = (Classifier) o[0];
		Instances data = (Instances) o[1];
		// 模型與測試集是否相容？
		if (!data.equalHeaders(test))
			throw new Exception("資料不相容！");
		System.out.println("反序列化分類別器及標頭資訊成功！");
		System.out.println("反序列化模型如下：");
		System.out.println(cls2);
	}

}
