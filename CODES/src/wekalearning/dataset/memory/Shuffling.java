package wekalearning.dataset.memory;

import java.util.Random;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Shuffling {

	public static void main(String[] args) throws Exception {
		Instances data = DataSource
				.read("C:/Weka-3-7/data/weather.nominal.arff");
		System.out.println("\n原資料集內容：");
		System.out.println(data);

		// 以下使用Random預設建構函數。若果要得到可重復的偽隨機序列，這種模式不可取
		Instances data1 = new Instances(data);
		data1.randomize(new Random());
		System.out.println("\n使用預設建構函數後第一次的資料集內容：");
		System.out.println(data1);

		Instances data2 = new Instances(data);
		data1.randomize(new Random());
		System.out.println("\n使用預設建構函數後第二次的資料集內容：");
		System.out.println(data2);

		// 以下使用Random提供隨機數種子的建構函數。推薦采用這種模式
		long seed = 1234l;
		Instances data3 = new Instances(data);
		data1.randomize(new Random(seed));
		System.out.println("\n使用提供隨機數種子的建構函數後第一次的資料集內容：");
		System.out.println(data3);

		Instances data4 = new Instances(data);
		data1.randomize(new Random(seed));
		System.out.println("\n使用提供隨機數種子的建構函數後第二次的資料集內容：");
		System.out.println(data4);

	}

}
