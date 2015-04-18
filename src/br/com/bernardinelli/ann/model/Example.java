package br.com.bernardinelli.ann.model;

import java.io.Serializable;
import java.util.Arrays;

public class Example implements Serializable, Cloneable{

	private static final long serialVersionUID = 8335526777433322683L;

	private double[] properties;
	private String label;

	private Example(){};
	
	public Example(String label, double... params) {
		properties = params;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public Double getValue(int i) {
		return properties[i];
	}
	
	public double[] getValues() {
		return properties.clone();
	}

	public void adjustValue(int i, double adjust) {
		properties[i] = adjust;
	}
	
	public int getPropertiesNumber(){
		return properties.length;
	}
	
	public static Example parseTuple(String label, double... params){
		return new Example(label, params);
	}

	@Override
	public Example clone() {
		Example tuple = new Example();
		tuple.properties = Arrays.copyOf(properties, properties.length);
		tuple.label = String.format("%s", label);
		return tuple;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + Arrays.hashCode(properties);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Example other = (Example) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (!Arrays.equals(properties, other.properties))
			return false;
		return true;
	}
	

}