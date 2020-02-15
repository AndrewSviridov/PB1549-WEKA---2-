package wekalearning.classifiers;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.OptionHandler;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AddClassification;

import java.util.Random;

/**
 * 執行單次交叉驗證，並將預測結果儲存為檔案
 */
public class CVPrediction {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances data = DataSource.read("C:/Weka-3-7/data/ionosphere.arff");
		// 設定類別別索引
		data.setClassIndex(data.numAttributes() - 1);

		// 分類別器
		String[] tmpOptions = new String[2];
		String classname = "weka.classifiers.trees.J48";
		tmpOptions[0] = "-C"; // 預設參數
		tmpOptions[1] = "0.25";
		Classifier classifier = (Classifier) Utils.forName(Classifier.class,
				classname, tmpOptions);

		// 其他選項
		int seed = 1234; // 隨機種子
		int folds = 10; // 折數

		// 隨機化資料
		Random rand = new Random(seed);
		Instances newData = new Instances(data);
		newData.randomize(rand);
		// 若果類別別為標稱型，則根據其類別別值進行分層
		if (newData.classAttribute().isNominal())
			newData.stratify(folds);

		// 執行交叉驗證，並加入預測
		Instances predictedData = null; // 預測資料
		Evaluation eval = new Evaluation(newData);
		for (int i = 0; i < folds; i++) {
			// 訓練集
			Instances train = newData.trainCV(folds, i);
			// 測試集
			Instances test = newData.testCV(folds, i);

			// 建構並評估分類別器
			Classifier clsCopy = AbstractClassifier.makeCopy(classifier);
			clsCopy.buildClassifier(train);
			eval.evaluateModel(clsCopy, test);

			// 加入預測
			AddClassification filter = new AddClassification();
			filter.setClassifier(classifier);
			filter.setOutputClassification(true);
			filter.setOutputDistribution(true);
			filter.setOutputErrorFlag(true);
			filter.setInputFormat(train);
			// 訓練分類別器
			Filter.useFilter(train, filter);
			// 在測試集上預測
			Instances pred = Filter.useFilter(test, filter);

			if (predictedData == null)
				predictedData = new Instances(pred, 0); // 防止預測資料集為空
			for (int j = 0; j < pred.numInstances(); j++)
				predictedData.add(pred.instance(j));
		}

		// 評估結果輸出
		System.out.println();
		System.out.println("=== 分類別器設定 ===");
		// 分類別器是否實現OptionHandler接口？
		if (classifier instanceof OptionHandler)
			System.out.println("分類別器："
					+ classifier.getClass().getName()
					+ " "
					+ Utils.joinOptions(((OptionHandler) classifier)
							.getOptions()));
		else
			System.out.println("分類別器：" + classifier.getClass().getName());
		System.out.println("資料集：" + data.relationName());
		System.out.println("折數：" + folds);
		System.out.println("隨機種子：" + seed);
		System.out.println();
		System.out.println(eval.toSummaryString("=== " + folds + "折交叉驗證 ===",
				false));

		// 寫入資料檔
		DataSink.write("d:/predictions.arff", predictedData);
	}
}
