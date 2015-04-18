

import java.util.PriorityQueue;
import java.util.Queue;

import br.com.bernardinelli.ann.settings.Random;

public class FalseRandomizer implements Random {


	private Double[] doublesArray;

	private Queue<Double> doubles = new PriorityQueue<Double>();

	public FalseRandomizer(Double ... params) {
		doublesArray = params;
		init();
	}

	private void init() {
		for (Double d : doublesArray)
			doubles.offer(d);
	}

	@Override
	public double nextDouble() {
		// TODO Auto-generated method stub
		if (doubles.size() == 0)
			init();
		return doubles.peek();
	}

}
