package wekalearning.attributeSelection;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * 使用底層API
 */
public class UseLowLevel {

	public static void main(String[] args) throws Exception {
		// 載入資料
		DataSource source = new DataSource(
				"C:/Weka-3-7/data/weather.numeric.arff");
		Instances data = source.getDataSet();
		// 設定類別別屬性索引
		if (data.classIndex() == -1)
			data.setClassIndex(data.numAttributes() - 1);

		System.out.println("\n 使用底層API");
		AttributeSelection attsel = new AttributeSelection();
		CfsSubsetEval eval = new CfsSubsetEval();
		GreedyStepwise search = new GreedyStepwise();
		search.setSearchBackwards(true);
		attsel.setEvaluator(eval);
		attsel.setSearch(search);
		attsel.SelectAttributes(data);
		int[] indices = attsel.selectedAttributes();
		System.out.println("選取屬性索引(從0開始):\n" + Utils.arrayToString(indices));
	}

}
