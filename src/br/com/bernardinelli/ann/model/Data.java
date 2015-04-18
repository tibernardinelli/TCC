package br.com.bernardinelli.ann.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Data {


	private Map<Integer, Collection<Double>> dataByProperties;
	private Map<Integer, Double> dataSumarized;
	private List<Example> tuples;
	private Set<String> possibleResults;

	public Data(int argumentNumber) {
		possibleResults = new TreeSet<String>();
		dataByProperties = new HashMap<Integer, Collection<Double>>();
		dataSumarized = new HashMap<Integer, Double>();
		for (int i = 0; i < argumentNumber; i++) {
			dataByProperties.put(i, new ArrayList<Double>());
			dataSumarized.put(i, 0d);
		}
		tuples = new ArrayList<Example>();
	}

	public void add(String label, double... params) {
		add(new Example(label, params));
	}

	public void add(Example tupla) {
		if (tupla == null) {
			return;
		}
		tuples.add(tupla);
		possibleResults.add(tupla.getLabel());
		for (int i = 0; i < dataByProperties.size(); i++) {
			dataByProperties.get(i).add(tupla.getValue(i));
			dataSumarized.put(i, dataSumarized.get(i) + tupla.getValue(i));
		}
	}
	

	public void standardize() {
		//Deixar os dados na mesma dimensão, admissional.
		for (int i = 0; i < dataByProperties.size(); i++) {
			double average = average(i);
			double standardDeviation = standardDeviation(i);
			if (standardDeviation != 0d && standardDeviation != Double.NaN)
				for (Example t : tuples) {
					double value = (t.getValue(i) - average)
							/ standardDeviation;
					t.adjustValue(i, value);
				}
		}
	}

	public String[] getPossibleResults() {
		return possibleResults.toArray(new String[possibleResults
				.size()]);
	}
	
	public List<Example> getTuples() {
		return tuples;
	}

	private Double average(int paramNum) {
		return dataSumarized.get(paramNum) / tuples.size();
	}

	private Double standardDeviation(int paramNum) {
		Double average = average(paramNum);
		Double var = 0d;
		for (Double d : dataByProperties.get(paramNum)) {
			var += Math.pow(d - average, 2);
		}
		double n = tuples.size();
		return Math.sqrt(var / (n - 1));
	}

	@Override
	public String toString() {
		return String.format(
				"Número de propriedades: %d; número de registros: %d",
				dataByProperties.size(), tuples.size());
	}

}
