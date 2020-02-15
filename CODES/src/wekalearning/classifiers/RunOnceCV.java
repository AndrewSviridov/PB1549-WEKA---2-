package wekalearning.classifiers;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

/**
 * 執行單次執行的10折交叉驗證
 */
public class RunOnceCV {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances data = DataSource.read("C:/Weka-3-7/data/ionosphere.arff");
		// 設定類別別索引
		data.setClassIndex(data.numAttributes() - 1);

		// 分類別器
		String[] options = new String[2];
		String classname = "weka.classifiers.trees.J48";
		options[0] = "-C"; // 預設參數
		options[1] = "0.25";
		Classifier classifier = (Classifier) Utils.forName(Classifier.class,
				classname, options);

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

		// 執行交叉驗證
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
		}

		// 輸出評估
		System.out.println();
		System.out.println("=== 分類別器設定  ===");
		System.out.println("分類別器：" + Utils.toCommandLine(classifier));
		System.out.println("資料集：" + data.relationName());
		System.out.println("折數：" + folds);
		System.out.println("隨機種子：" + seed);
		System.out.println();
		System.out.println(eval.toSummaryString("=== " + folds + "折交叉驗證 ===",
				false));
	}
}
