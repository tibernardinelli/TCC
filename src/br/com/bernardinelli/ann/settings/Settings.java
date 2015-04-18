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

	public Random getRandom() {
		if (random == null)
			random = new DefaultRandom();
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}
}
