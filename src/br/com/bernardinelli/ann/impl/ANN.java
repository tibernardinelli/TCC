package br.com.bernardinelli.ann.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Random;

import br.com.bernardinelli.ann.model.Tuple;

public class ANN {

	private Random random;

	private Map<Integer, Double[]> hiddenLayerWeight;
	private Map<Integer, Double[]> outputLayerWeight;

	private int numeroEntradas;
	private int numeroSaidas;
	private Map<Integer, String> possibleResults;

	public ANN(int neuronios, int entradas, String[] possibleResults) {
		random = new Random();
		this.numeroEntradas = entradas;
		this.numeroSaidas = possibleResults.length;

		this.hiddenLayerWeight = initialize(numeroEntradas, neuronios);
		this.outputLayerWeight = initialize(neuronios, numeroSaidas);
		
		normalizeWeight(this.hiddenLayerWeight);
		normalizeWeight(this.outputLayerWeight);

		this.possibleResults = new HashMap<Integer, String>();
		for (int i = 0; i < possibleResults.length; i++) {
			this.possibleResults.put(i, possibleResults[i]);
		}
	}
	
	private Map<Integer, Double[]> initialize(int input, int output){
		Map<Integer, Double[]> layer = new HashMap<Integer, Double[]>();
		for (int i = 0; i < output; i++) {
			Double[] d = new Double[input];
			for (int j = 0; j < input; j++) {
				if (random.nextBoolean())
					d[j] = -1d;
				else
					d[j] = 1d;
			}
			layer.put(i, d);
		}
		return layer;
	}

	private void normalizeWeight(Map<Integer, Double[]> layer) {
		for (Integer i : layer.keySet()) {
			Double[] wi = layer.get(i);
			OptionalDouble temp = Arrays.stream(wi).mapToDouble(x-> Math.pow(x, 2)).reduce(Double::sum);
			Double nwi = Math.sqrt(temp.getAsDouble());
			for (int j = 0; j < wi.length; j++) {
				wi[j] = wi[j] / nwi;
			}
		}
	}

	public void trainCollection(Collection<Tuple> tuplas) {
		int acertos = 0;
		do {
			acertos = 0;
			for (Tuple tupla : tuplas) {

				double[] desiredOutput = getDesiredOutput(tupla);
				double[] output = execute(tupla);

				double [] termosErro = new double[possibleResults.size()];
				for (int i = 0; i<output.length; i++){
					termosErro[i] = output[i] * (1 - output[i]) * (desiredOutput[i] - output[i]);
				}
				int higher = 0;
				for (int i = higher; i < output.length; i++) {
					if (output[i] > output[higher])
						higher = i;
				}
				
				//atualizaPesos;
				if (tupla.getLabel().equals(possibleResults.get(higher)))
					acertos++;
			}
		} while (acertos / tuplas.size() < 90);
	}

	private double[] getDesiredOutput(Tuple tupla) {
		double []saidaEsperada = new double[possibleResults.size()];
		
		for (int i = 0; i<saidaEsperada.length; i++){
			if (tupla.getLabel().equals(possibleResults.get(i))){
				saidaEsperada[i] = 1;
				break;
			}
		}
		return saidaEsperada;
	}

	private double[] execute(Tuple tupla) {
		Double[] somaPonderadaCamadaOculta = new Double[hiddenLayerWeight
				.keySet().size()];
		Double[] outputCamadaOculta = new Double[somaPonderadaCamadaOculta.length];
		double[] saida = new double[somaPonderadaCamadaOculta.length];
		for (Integer neuronio : hiddenLayerWeight.keySet()) {
			Double[] weights = hiddenLayerWeight.get(neuronio);
			Double somaPonderada = 0.0d;
			for (int i = 0; i < weights.length; i++) {
				somaPonderada = tupla.getValue(i) * weights[i];
			}
			somaPonderadaCamadaOculta[neuronio] = somaPonderada;
			outputCamadaOculta[neuronio] = 1 / (1 + Math
					.exp(-somaPonderada));
		}

		for (Integer neuronio : outputLayerWeight.keySet()) {
			Double somaPonderada = 0.0d;
			Double[] weights = outputLayerWeight.get(neuronio);
			for (int i = 0; i < outputCamadaOculta.length; i++) {
				somaPonderada = weights[i] * outputCamadaOculta[i];
			}
			saida[neuronio] = 1 / (1 + Math.exp(-somaPonderada));
		}
		return saida;
	}
	
	public String executa(Tuple tupla){
		double[] output = execute(tupla);
		int higher = 0;
		for (int i = higher; i < output.length; i++) {
			if (output[i] > output[higher])
				higher = i;
		}
		return possibleResults.get(higher);
		
	}

}
