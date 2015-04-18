package br.com.bernardinelli.ann.input;

import java.util.List;

import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.model.Example;

public interface ModelValidationTechnique {
	
	void run(List<Example> tuples, Trainable trainable);

}
