package wekalearning.dataset.savedata;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.DatabaseSaver;

/**
 * 將資料集儲存到資料庫
 */
public class Save2DB {

	public static void main(String[] args) {

		try {
			// 載入資料
			Instances data = new Instances(
					DataSource.read("C:/Weka-3-7/data/iris.arff"));
			System.out.println("完成載入資料");

			// 批次模式儲存資料到資料庫
			DatabaseSaver saver = new DatabaseSaver();
			saver.setDestination("jdbc:mysql://localhost:3306/weka", "weka",
					"weka");
			// 在這裡明確指定表名：
			saver.setTableName("iris");
			saver.setRelationForTableName(false);
			saver.setInstances(data);
			saver.writeBatch();
			System.out.println("完批次量模式儲存資料");

			// 增量模式儲存資料到資料庫
			DatabaseSaver saver2 = new DatabaseSaver();
			saver2.setDestination("jdbc:mysql://localhost:3306/weka", "weka",
					"weka");
			// 在這裡明確指定表名：
			saver2.setTableName("iris2");
			saver2.setRelationForTableName(false);
			saver2.setRetrieval(DatabaseSaver.INCREMENTAL);
			saver2.setStructure(data);
			for (int i = 0; i < data.numInstances(); i++) {
				saver2.writeIncremental(data.instance(i));
			}
			// 知會儲存器已經完成
			saver2.writeIncremental(null);
			System.out.println("完成增量模式儲存資料");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
