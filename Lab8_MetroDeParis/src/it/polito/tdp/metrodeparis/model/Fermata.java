package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fermata {

	private String nome;
	private double coordX, coordY;
	private Map<Fermata, Double> archi;
	private String linea;
	
	public Fermata(String nome, double coordX, double coordY) {
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
		archi = new HashMap<>();
		linea = null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getCoordX() {
		return coordX;
	}

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}
	
	public void addArco(Fermata f, double d){
		archi.put(f, d);
	}
	
	public double getVelocita(Fermata f){
		if(archi.containsKey(f))
			return archi.get(f);
		return -1;
	}
	
	public boolean isVicino(Fermata f){
		return archi.containsKey(f);
	}
	
	public List<Fermata> getArchi(){
		List<Fermata> elenco = new ArrayList<>(archi.keySet());
		return elenco;
	}
	
	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((linea == null) ? 0 : linea.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (linea == null) {
			if (other.linea != null)
				return false;
		} else if (!linea.equals(other.linea))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}
	
}
