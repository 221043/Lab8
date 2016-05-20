package it.polito.tdp.metrodeparis.model;

import java.util.Comparator;

public class ComparatorePerFermata implements Comparator<Fermata>{

	@Override
	public int compare(Fermata a, Fermata b) {
		return a.getNome().compareTo(b.getNome());
	}

}
