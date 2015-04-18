package br.com.bernardinelli.ann.settings;

public class DefaultRandom implements Random{

	private java.util.Random random;
	private double low;
	private double high;

	public DefaultRandom(){
		random = new java.util.Random();
		low = -1;
		high = 1;
	}
	
	public DefaultRandom(double innerBound, double outterBound) {
		this();
		low = innerBound;
		high = outterBound;
	}
	
	@Override
	public double nextDouble() {
		return low + (high - low) * random.nextDouble();
	}
}
