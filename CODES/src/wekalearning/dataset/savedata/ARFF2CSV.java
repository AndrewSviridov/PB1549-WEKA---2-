package wekalearning.dataset.savedata;

import java.io.File;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * ARFF檔案轉為CSV檔案
 */
public class ARFF2CSV {

	public static void main(String[] args) {

		try {
			// 載入資料
			Instances data = new Instances(
					DataSource.read("C:/Weka-3-7/data/iris.arff"));
			System.out.println("完成載入資料");

			// 使用DataSink類別，儲存為CSV
			DataSink.write("C:/Weka-3-7/data/iris.csv", data);
			System.out.println("完成使用DataSink類別儲存資料");

			// 明確指定轉換器，儲存為CSV
			CSVSaver saver = new CSVSaver();
			saver.setInstances(data);
			saver.setFile(new File("C:/Weka-3-7/data/iris2.csv"));
			saver.writeBatch();
			System.out.println("完成指定CSVSaver轉換器儲存資料");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
