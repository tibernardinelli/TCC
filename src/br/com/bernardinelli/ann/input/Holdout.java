package br.com.bernardinelli.ann.input;

import java.util.List;

import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.model.Tuple;

public class Holdout implements ModelValidationTechnique {

	@Override
	public void run(List<Tuple> tuples, Trainable trainable) {
		int size = tuples.size() / 3;
		List<Tuple> subListTest = tuples.subList(0, size - 1);
		List<Tuple> subListExercise = tuples.subList(size, tuples.size() - 1);

		int ok;
		do {
			ok = 0;
			trainable.exercise(subListExercise);
			for (Tuple tupla : subListTest) {
				if (trainable.test(tupla))
					ok++;
			}
		} while ((ok * 100) / subListTest.size() > 90);
	}

}
