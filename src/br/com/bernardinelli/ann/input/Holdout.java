package br.com.bernardinelli.ann.input;

import java.util.List;

import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.model.Example;

public class Holdout implements ModelValidationTechnique {

	@Override
	public void run(List<Example> tuples, Trainable trainable) {
		int size = tuples.size() / 3;
		List<Example> subListTest = tuples.subList(0, size - 1);
		List<Example> subListExercise = tuples.subList(size, tuples.size() - 1);

		int ok;
		do {
			ok = 0;
			trainable.exercise(subListExercise);
			for (Example tupla : subListTest) {
				if (trainable.test(tupla))
					ok++;
			}
			//gravar os valores de erro ;
		} while ((ok * 100) / subListTest.size() > 90);
	}

}
