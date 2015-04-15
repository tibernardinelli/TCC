package br.com.bernardinelli.ann.impl;

import java.util.List;

import br.com.bernardinelli.ann.model.Tuple;

public interface Trainable {
	void exercise(List<Tuple> tuples);
	boolean test(Tuple tupla);
}
