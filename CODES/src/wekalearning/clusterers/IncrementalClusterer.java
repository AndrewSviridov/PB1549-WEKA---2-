package wekalearning.clusterers;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.clusterers.Cobweb;

import java.io.File;

/**
 * 增量訓練Cobweb算法
 */
public class IncrementalClusterer {

	public static void main(String[] args) throws Exception {
		// 載入資料
		ArffLoader loader = new ArffLoader();
		loader.setFile(new File("C:/Weka-3-7/data/contact-lenses.arff"));
		Instances structure = loader.getStructure();

		// 訓練Cobweb
		Cobweb cw = new Cobweb();
		cw.buildClusterer(structure);
		Instance current;
		while ((current = loader.getNextInstance(structure)) != null)
			cw.updateClusterer(current);
		cw.updateFinished();

		// 輸出產生模型
		System.out.println(cw);
	}
}
