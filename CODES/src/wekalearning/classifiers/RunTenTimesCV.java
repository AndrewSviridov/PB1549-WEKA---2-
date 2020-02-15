package wekalearning.classifiers;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Random;

/**
 * 執行10次執行的10折交叉驗證
 */
public class RunTenTimesCV {

	public static void main(String[] args) throws Exception {
		// 載入資料
		Instances data = DataSource.read("C:/Weka-3-7/data/labor.arff");
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
		int runs = 10; // 執行次數
		int folds = 10; // 折數

		// 執行交叉驗證
		for (int i = 0; i < runs; i++) {
			// 隨機化資料
			int seed = i + 1234; // 隨機種子
			Random rand = new Random(seed);
			Instances newData = new Instances(data);
			newData.randomize(rand);
			// 若果類別別為標稱型，則根據其類別別值進行分層
			if (newData.classAttribute().isNominal())
				newData.stratify(folds);

			Evaluation eval = new Evaluation(newData);
			for (int j = 0; j < folds; j++) {
				// 訓練集
				Instances train = newData.trainCV(folds, j);
				// 測試集
				Instances test = newData.testCV(folds, j);

				// 建構並評估分類別器
				Classifier clsCopy = AbstractClassifier.makeCopy(classifier);
				clsCopy.buildClassifier(train);
				eval.evaluateModel(clsCopy, test);
			}

			// 評估結果輸出
			System.out.println();
			System.out.println("=== 執行第 " + (i + 1) + "次的分類別器設定 ===");
			System.out.println("分類別器：" + Utils.toCommandLine(classifier));
			System.out.println("資料集：" + data.relationName());
			System.out.println("折數：" + folds);
			System.out.println("隨機種子：" + seed);
			System.out.println();
			System.out.println(eval.toSummaryString("=== " + folds
					+ "折交叉驗證 執行第" + (i + 1) + "次 ===", false));
		}
	}
}
