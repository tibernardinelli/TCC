package br.com.bernardinelli.ann.input;

import java.time.Duration;
import java.time.Instant;
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
		int acertividade = 0; 
		int execucoes = 0;
		Instant inicio = Instant.now();
		do {
			execucoes ++;
			ok = 0;
			trainable.exercise(subListExercise);
			
			for (Example tupla : subListTest) {
				boolean test = trainable.test(tupla);
				if (test)
					ok++;
			}
			
			//gravar os valores de erro ;
			int temp = (ok * 100) / subListTest.size();
			if (temp != acertividade || execucoes % 1000 == 0)
				System.out.println(String.format("Acertividade de %d%% em %d apresentações", temp, execucoes));
			acertividade = temp;
		} while (acertividade < 90);
		System.out.println(Duration.between(inicio, Instant.now()));
	}

}
