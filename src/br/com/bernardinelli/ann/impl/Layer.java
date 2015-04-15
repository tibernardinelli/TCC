package br.com.bernardinelli.ann.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Random;

class Layer {

	;

	private Map<Integer, Double[]> layer;

	Layer(int input, int output) {
		initializeMap(new Random(), input, output);
	
	}

	Layer(Random rnd, int input, int output) {
		initializeMap(rnd, input, output);
		normalizeWeight();
	}


	private void initializeMap(Random rnd, int input, int output) {
		layer = new HashMap<Integer, Double[]>();
		for (int i = 0; i < output; i++) {
			Double[] d = new Double[input];
			for (int j = 0; j < input; j++) {
				if (rnd.nextBoolean())
					d[j] = -1d;
				else
					d[j] = 1d;
			}
			layer.put(i, d);
		}
	}
	
	public void normalizeWeight() {
		for (Integer i : layer.keySet()) {
			Double[] wi = layer.get(i);
			OptionalDouble temp = Arrays.stream(wi).mapToDouble(x-> Math.pow(x, 2)).reduce(Double::sum);
			Double nwi = Math.sqrt(temp.getAsDouble());
			for (int j = 0; j < wi.length; j++) {
				wi[j] = wi[j] / nwi;
			}
		}
	}

	public double[] execute(double[] inputs) {
		double[] outputs = new double[layer.size()];
		for (Integer neuronio : layer.keySet()) {
			Double[] weights = layer.get(neuronio);
			Double somaPonderada = 0.0d;
			for (int i = 0; i < weights.length; i++) {
				somaPonderada = inputs[i] * weights[i];
			}
			outputs[neuronio] = 1 / (1 + Math
					.exp(-somaPonderada));
		}
		return outputs;
	}
}
