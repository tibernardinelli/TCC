package br.com.bernardinelli.ann.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.bernardinelli.ann.impl.RNA;
import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Tuple;


public class Runner implements Trainable {

	private RNA rna;
	private Data data;
	private Map<String, Integer> resultadoEsperado;

	public Runner(RNA rna, Data data) {
		super();
		this.rna = rna;
		this.data = data;
		resultadoEsperado = new HashMap<String, Integer>();
		int i = 0;
		for(String s: data.getPossibleResults()){
			resultadoEsperado.put(s,  i);
			i++;
		}
	}

	@Override
	public void exercise(List<Tuple> tuples) {
		List<double[]> params = tuples.stream().map(t -> {
			 double[] temp = Arrays.copyOf(t.getValues(),t.getValues().length + 1);
			 temp[t.getValues().length] = resultadoEsperado.get(t.getLabel());
			return t.getValues();
		}).collect(Collectors.toList());
		rna.exercise(params);
	}

	@Override
	public boolean test(Tuple tupla) {
		double[] outputs = rna.execute(tupla.getValues());
		double higherValue = outputs[0];
		int higherIndex = 0;
		for (int i = 1; i < outputs.length; i++) {
			if (outputs[i] > higherValue) {
				higherIndex = i;
				higherValue = outputs[i];
			}
		}

		return tupla.getLabel() == data.getPossibleResults()[higherIndex];

	}

}
