package br.com.bernardinelli.ann.impl;

import java.util.List;

import br.com.bernardinelli.ann.model.Example;

public interface Trainable {
	void exercise(List<Example> tuples);
	boolean test(Example tupla);
}
