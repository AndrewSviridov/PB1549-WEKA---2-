package wekalearning.dataset.loaddata;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.converters.XRFFLoader;

import java.io.File;

public class LoadXrffFile {

	public static void main(String[] args) {
		// 使用DataSource類別的read方法來載入 XRFF 檔案
		System.out.println("使用DataSource類別的read方法來載入 XRFF檔案");
		System.out.println("由於檔案副檔名與資料集格式不符合，肯定載入失敗");
		// 同樣也要捕捉程式例外，這裡已拋出
		try {
			Instances data = DataSource
					.read("C:/Weka-3-7/data/weather.nominal.xml");
			System.out.println("\n資料集內容：");
			System.out.println(data);
		} catch (Exception e) {
			System.out.println("載入檔案失敗!");
		}

		System.out.println("\n\n使用直接指定載入器的方法來載入ARFF檔案");
		System.out.println("由於直接指定符合資料集格式的載入器，肯定載入成功");
		try {
			XRFFLoader loader = new XRFFLoader();
			loader.setSource(new File("C:/Weka-3-7/data/weather.nominal.xml"));
			Instances data = loader.getDataSet();
			System.out.println("\n資料集內容：");
			System.out.println(data);
		} catch (Exception e) {
			System.out.println("載入檔案失敗!");
		}

	}

}
