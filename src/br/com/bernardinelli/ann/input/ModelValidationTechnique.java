package br.com.bernardinelli.ann.input;

import java.util.List;

import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.model.Tuple;

public interface ModelValidationTechnique {
	
	void run(List<Tuple> tuples, Trainable trainable);

}
