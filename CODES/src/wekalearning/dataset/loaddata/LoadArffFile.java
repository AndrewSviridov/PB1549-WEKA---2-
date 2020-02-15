package wekalearning.dataset.loaddata;

import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.File;

public class LoadArffFile {

	public static void main(String[] args) throws Exception {
		// 使用DataSource類別的read方法來載入ARFF檔案
		System.out.println("\n\n使用DataSource類別的read方法來載入 ARFF檔案");
		// 同樣也要捕捉程式例外，這裡已拋出
		Instances data1 = DataSource
				.read("C:/Weka-3-7/data/weather.nominal.arff");
		System.out.println("\n資料集內容：");
		System.out.println(data1);

		// 使用直接指定載入器的方法來載入ARFF檔案
		System.out.println("\n\n使用直接指定載入器的方法來載入ARFF檔案");
		// 建立一個ArffLoader 類別案例
		ArffLoader loader = new ArffLoader();
		// 載入ARFF檔案，
		// 此時從系統中讀檔案時要捕捉例外，這裡透過在main函數中拋出
		loader.setSource(new File("C:/Weka-3-7/data/weather.numeric.arff"));
		Instances data2 = loader.getDataSet();
		System.out.println("\n資料集內容：");
		System.out.println(data2);

	}

}
