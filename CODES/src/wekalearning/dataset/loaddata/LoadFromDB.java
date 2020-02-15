package wekalearning.dataset.loaddata;

import weka.core.Instances;
import weka.core.converters.DatabaseLoader;
import weka.experiment.InstanceQuery;

public class LoadFromDB {

	public static void main(String[] args) throws Exception {
		InstanceQuery query = null;
		Instances data = null;

		// 使用InstanceQuery類別
		System.out.println("使用InstanceQuery類別從資料庫中檢索資料");
		query = new InstanceQuery();
		query.setDatabaseURL("jdbc:mysql://localhost:3306/weka");
		query.setUsername("weka");
		query.setPassword("weka");
		query.setQuery("select * from weather");
		data = query.retrieveInstances();
		// 使用最後一個屬性作為類別別屬性
		if (data.classIndex() == -1)
			data.setClassIndex(data.numAttributes() - 1);
		System.out.println("資料集內容：");
		System.out.println(data);

		// 使用DatabaseLoader類別進行“批次檢索”
		System.out.println("\n\n使用DatabaseLoader類別從資料庫中批次檢索資料");
		DatabaseLoader loader = null;
		loader = new DatabaseLoader();
		loader.setSource("jdbc:mysql://localhost:3306/weka", "weka", "weka");
		loader.setQuery("select * from weather");
		Instances data1 = loader.getDataSet();
		// 使用最後一個屬性作為類別別屬性
		if (data1.classIndex() == -1)
			data1.setClassIndex(data1.numAttributes() - 1);
		System.out.println("資料集內容：");
		System.out.println(data1);
	}

}
