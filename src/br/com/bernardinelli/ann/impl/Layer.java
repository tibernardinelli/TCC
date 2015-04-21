package br.com.bernardinelli.ann.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.OptionalDouble;

import br.com.bernardinelli.ann.settings.Random;
import br.com.bernardinelli.ann.settings.Settings;

class Layer {

	private Map<Integer, double[]> input;
	private Map<Integer, Double> output;
	private Map<Integer, Double[]> layer;

	Layer(int input, int output) {
		initializeMap(Settings.getInstance().getRandom(), input, output);
		initializeAuxMap();
	}

	Layer(Random rnd, int input, int output) {
		initializeMap(rnd, input, output);
		normalizeWeight();
		initializeAuxMap();
	}

	private void initializeAuxMap() {
		this.input = new HashMap<Integer, double[]>();
		this.output = new HashMap<Integer, Double>();
	}

	private void initializeMap(Random rnd, int input, int output) {
		layer = new HashMap<Integer, Double[]>();
		for (int i = 0; i < output; i++) {
			Double[] d = new Double[input];
			for (int j = 0; j < input; j++) {
				d[j] = rnd.nextDouble();
			}
			layer.put(i, d);
		}
	}

	public void normalizeWeight() {
		if (Settings.getInstance().isNormalizeWeight())
			for (Integer i : layer.keySet()) {
				Double[] wi = layer.get(i);
				OptionalDouble temp = Arrays.stream(wi).mapToDouble(x -> Math.pow(x, 2)).reduce(Double::sum);
				Double norma = Math.sqrt(temp.getAsDouble());
				//System.out.print(String.format("norma: %s ", norma.toString()));
				for (int j = 0; j < wi.length; j++) {
					wi[j] = wi[j] / norma;
				}
			}
	}

	public double[] execute(double[] inputs) {
		double[] outputs = new double[layer.size()];
		for (Integer neuronio : layer.keySet()) {
			input.put(neuronio, inputs);
			Double[] weights = layer.get(neuronio);
			Double net = 0.0d;
			for (int i = 0; i < weights.length; i++) {
				net += inputs[i] * weights[i];
			}
			outputs[neuronio] = 1 / (1 + Math.exp(-net));
			output.put(neuronio, outputs[neuronio]);
		}
		return outputs;
	}

	public double[] adjustWeight(double learnRate, double[] errorTerms) {
		for (Entry<Integer, Double[]> entry: layer.entrySet()){
			double inputs[] = input.get(entry.getKey());
			double errorTerm = errorTerms[entry.getKey()];
			Double[] pesos = entry.getValue();
			for (int i = 0; i < pesos.length; i++) {
				pesos[i] += (learnRate * errorTerm * inputs[i]);
			}
			layer.put(entry.getKey(), pesos);
		}
		
		normalizeWeight();
		
		double[] inputs = input.get(0);
		int inputNumber = input.get(0).length;
		double[] retorno = new double[inputNumber];
		for (int i = 0; i < inputNumber; i++){
			double sum = 0.0;
			for (int j = 0; j < errorTerms.length; j++){
				sum += layer.get(j)[i] * errorTerms[j];
			}
			retorno[i] = sum * (inputs[i] * (1 - inputs[i]));
		}
		
		return retorno;
	}
}
