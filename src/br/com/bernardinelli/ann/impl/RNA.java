package br.com.bernardinelli.ann.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.com.bernardinelli.ann.settings.Random;
import br.com.bernardinelli.ann.settings.Settings;

public class RNA {

	private LinkedList<Layer> layers;
	private Map<Layer, double[]> outputs;

	public RNA(int inputNumber, int... layerNodeDefinition) {
		this.layers = new LinkedList<Layer>();
		this.outputs = new HashMap<Layer, double[]>();
		Random rnd = Settings.getInstance().getRandom();

		this.layers.add(new Layer(rnd, inputNumber, layerNodeDefinition[0]));
		for (int i = 1; i < layerNodeDefinition.length; i++) {
			this.layers.add(new Layer(rnd, layerNodeDefinition[i - 1],
					layerNodeDefinition[i]));
		}
	}

	//TODO renomear
	public double[] apresentacao(double[] inputs) {
		for (Layer layer : layers) {
			inputs = layer.execute(inputs);
			outputs.put(layer, inputs);
		}
		return inputs;
	}

	// ultimo registro contem o resultado esperado
	public void exercise(List<double[]> inputs) {
		for (double[] input : inputs) {

			double expectedResult = input[input.length - 1];
			double[] expectedResults = new double[input.length - 1];
			Arrays.fill(expectedResults, 0d);
			expectedResults[new Double(expectedResult).intValue()] = 1d;

			double[] output = apresentacao(Arrays.copyOfRange(input, 0,
					input.length - 2));

			double[] termosErro = new double[output.length];
			for (int i = 0; i < output.length; i++) {
				termosErro[i] = output[i] * (1 - output[i])
						* (expectedResults[i] - output[i]);
			}

			double learnRate = 0.1;
			Iterator<Layer> descendingIterator = layers.descendingIterator();
			while(descendingIterator.hasNext()){
				Layer layer = descendingIterator.next();
				termosErro = layer.adjustWeight(learnRate, termosErro);
			}
		}
	}
}
