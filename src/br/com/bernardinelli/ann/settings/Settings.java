package br.com.bernardinelli.ann.settings;


public class Settings {

	private static Settings	instance;

	public static Settings getInstance() {
		if (instance == null)
			instance = new Settings();
		return instance;
	}

	private Settings() {
		
	}

	private Random random;
	private boolean normalizeWeight;

	public Random getRandom() {
		if (random == null)
			random = new DefaultRandom();
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	public boolean isNormalizeWeight() {
		return normalizeWeight;
	}

	public void setNormalizeWeight(boolean normalizeWeight) {
		this.normalizeWeight = normalizeWeight;
	}
	
	
}
