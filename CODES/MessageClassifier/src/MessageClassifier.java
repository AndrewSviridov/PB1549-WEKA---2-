/**
 * 將簡短的文字訊息分類別為兩個類別別的Java程式
 */

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MessageClassifier implements Serializable {

	private static final long serialVersionUID = -6705084686587638940L;

	/* 迄今收集訓練資料 */
	private Instances m_Data = null;

	/* 用於產生單字計數的過濾器 */
	private StringToWordVector m_Filter = new StringToWordVector();

	/* 實際的分類別器 */
	private Classifier m_Classifier = new J48();

	/* 模型是否為最新 */
	private boolean m_UpToDate;

	/**
	 * 建構空訓練集
	 */
	public MessageClassifier() throws Exception {

		String nameOfDataset = "MessageClassificationProblem";

		// 建立的屬性清單
		List<Attribute> attributes = new ArrayList<Attribute>();

		// 加入屬性以儲存文字訊息
		attributes.add(new Attribute("Message", (List<String>) null));

		// 加入類別別屬性
		List<String> classValues = new ArrayList<String>();
		classValues.add("miss");
		classValues.add("hit");
		attributes.add(new Attribute("Class", classValues));

		// 建立起始容量為100的資料集
		m_Data = new Instances(nameOfDataset, (ArrayList<Attribute>) attributes, 100);
		// 設定類別別索引
		m_Data.setClassIndex(m_Data.numAttributes() - 1);
	}

	/**
	 * 使用指定的訓練文字訊息更新模型
	 */
	public void updateData(String message, String classValue) throws Exception {

		// 把文字訊息轉為案例
		Instance instance = makeInstance(message, m_Data);

		// 為案例設定類別別值
		instance.setClassValue(classValue);

		// 加入案例到訓練資料
		m_Data.add(instance);
		m_UpToDate = false;
		
		// 輸出提示訊息
		System.err.println("更新模型成功！");
	}

	/**
	 * 分類別指定的文字訊息
	 */
	public void classifyMessage(String message) throws Exception {

		// 檢查是否已建構分類別器
		if (m_Data.numInstances() == 0) {
			throw new Exception("沒有分類別器可用。");
		}

		// 檢查是否分類別器和過濾器為最新
		if (!m_UpToDate) {

			// 起始化過濾器，並告知輸入格式
			m_Filter.setInputFormat(m_Data);

			// 從訓練資料產生單字計數
			Instances filteredData = Filter.useFilter(m_Data, m_Filter);

			// 重建分類別器
			m_Classifier.buildClassifier(filteredData);
			m_UpToDate = true;
		}

		// 形成單獨的小測試集，所以該文字訊息不會新增到m_Data的字串屬性中
		Instances testset = m_Data.stringFreeStructure();

		// 使文字訊息成為測試案例
		Instance instance = makeInstance(message, testset);

		// 過濾案例
		m_Filter.input(instance);
		Instance filteredInstance = m_Filter.output();

		// 取得預測類別別值的索引
		double predicted = m_Classifier.classifyInstance(filteredInstance);

		// 輸出類別別值
		System.err.println("文字訊息分類別為 ："
				+ m_Data.classAttribute().value((int) predicted));
	}

	/**
	 * 將文字訊息轉為案例的方法
	 */
	private Instance makeInstance(String text, Instances data) {

		// 建立一個屬性數量為2，權重為1，全部值都為缺失的案例
		Instance instance = new DenseInstance(2);

		// 設定文字訊息屬性的值
		Attribute messageAtt = data.attribute("Message");
		instance.setValue(messageAtt, messageAtt.addStringValue(text));

		// 讓案例能夠存取資料集中的屬性訊息
		instance.setDataset(data);
		return instance;
	}

	/**
	 * 主方法
	 * 可以識別下列參數：
	 * -E
	 *   文字是否為英文。預設為中文，省略該參數
	 * -m 文字訊息檔案
	 *  指向一個檔案，其中包括待分類別的文字訊息，或用於更新模型的文字訊息。
	 * -c 類別別標簽
	 *  若果要更新模型，文字訊息的類別別標簽。省略表示需要對文字訊息進行分類別。
	 * -t 模型檔案
	 *  包括模型的檔案。若果不存在該檔案，就會自動建立。
	 *  
	 *  @param args 指令行選項
	 */
	public static void main(String[] options) {

		try {

			// 讀入文字訊息檔案，儲存為字串
			String messageName = Utils.getOption('m', options);
			if (messageName.length() == 0) {
				throw new Exception("必須提供文字訊息檔案的名稱。");
			}
			FileReader m = new FileReader(messageName);
			StringBuffer message = new StringBuffer();
			int l;
			while ((l = m.read()) != -1) {
				message.append((char) l);
			}
			m.close();
			
			// 檢查是否文字為英文
			boolean isEnglish = Utils.getFlag('E', options);
			if(! isEnglish) {
				// 只有中文字需要進行中文分詞
		        Analyzer ikAnalyzer = new IKAnalyzer();  
		        Reader reader = new StringReader(message.toString());  
		        TokenStream stream = (TokenStream)ikAnalyzer.tokenStream("", reader);  
		        CharTermAttribute termAtt  = (CharTermAttribute)stream.addAttribute(CharTermAttribute.class); 
		        message = new StringBuffer();
		        while(stream.incrementToken()){  
		            message.append(termAtt.toString() + " ");   
		        }  
			}

			// 檢查是否已指定類別別值
			String classValue = Utils.getOption('c', options);

			// 若果模型檔案存在，則讀入，否則建立新的模型檔案
			String modelName = Utils.getOption('t', options);
			if (modelName.length() == 0) {
				throw new Exception("必須提供模型檔案的名稱。");
			}
			MessageClassifier messageCl;
			try {
				ObjectInputStream modelInObjectFile = new ObjectInputStream(
						new FileInputStream(modelName));
				messageCl = (MessageClassifier) modelInObjectFile.readObject();
				modelInObjectFile.close();
			} catch (FileNotFoundException e) {
				messageCl = new MessageClassifier();
			}

			// 處理文字訊息
			if (classValue.length() != 0) {
				messageCl.updateData(message.toString(), classValue);
			} else {
				messageCl.classifyMessage(message.toString());
			}

			// 儲存文字訊息分類別器物件
			ObjectOutputStream modelOutObjectFile = new ObjectOutputStream(
					new FileOutputStream(modelName));
			modelOutObjectFile.writeObject(messageCl);
			modelOutObjectFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
