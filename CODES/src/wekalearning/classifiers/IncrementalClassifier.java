package wekalearning.classifiers;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;

/**
 * 增量模式建構NaiveBayes分類別器，並輸出產生模型
 */
public class IncrementalClassifier {

	public static void main(String[] args) throws Exception {
		// 載入資料
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File("C:/Weka-3-7/data/weather.nominal.arff"));
		Instances structure = loader.getStructure();
		structure.setClassIndex(structure.numAttributes() - 1);

		// 訓練NaiveBayes分類別器
		NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
		nb.buildClassifier(structure);
		Instance instance;
		while ((instance = loader.getNextInstance(structure)) != null)
			nb.updateClassifier(instance);

		// 輸出產生模型
		System.out.println(nb);
	}
}
