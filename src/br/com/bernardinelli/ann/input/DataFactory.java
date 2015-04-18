package br.com.bernardinelli.ann.input;
import java.util.List;

import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Example;

public abstract class DataFactory {

	public static Data transform(List<Example> tuplas){
		
		int propertyNum = tuplas.get(0).getPropertiesNumber();
		
		final Data data = new Data(propertyNum);
		tuplas.forEach(tupla -> {
			data.add(tupla.clone());
		});
		data.standardize();
		return data;
	}
	
//	public Data[] holdout() {
//		if (data.size() == 0)
//			return null;
//
//		int propertyNum = data.get(0).getPropertiesNumber();
//
//		final Data trainningData = new Data(propertyNum);
//		final Data testData = new Data(propertyNum);
//
//		Collections.shuffle(data, new Random());
//
//		int partSize = data.size() / 3;
//		
//		data.subList(0, partSize).stream().forEach(tupla -> {
//			testData.add(tupla);
//		});
//		data.subList(partSize , data.size() ).stream().forEach(tupla -> {
//			trainningData.add(tupla);
//		});
//
//		return new Data[] { trainningData, testData };
//	}
}
