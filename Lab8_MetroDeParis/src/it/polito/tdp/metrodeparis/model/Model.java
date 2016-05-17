package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.*;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.FermataDAO;

public class Model {

	private FermataDAO fdao = new FermataDAO();
	private SimpleWeightedGraph<Fermata, DefaultWeightedEdge> metro = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge> (DefaultWeightedEdge.class);
	private List<Fermata> fermate;
	private FloydWarshallShortestPaths<Fermata, DefaultWeightedEdge> floyd;
	
	public List<String> caricaFermate(){
		fermate = fdao.getFermate();
		List<String> nomiFermate = new ArrayList<>();
		for(Fermata f:fermate)
			nomiFermate.add(f.getNome());
		return nomiFermate;
	}
	
	public void createGraph(){
		Graphs.addAllVertices(metro, fermate);
		for(Fermata f:fermate){
			fdao.getVicini(f);
			for(Fermata f2:f.getArchi()){
				if(!metro.containsEdge(f, f2)){
					double distanza = LatLngTool.distance(new LatLng(f.getCoordX(), f.getCoordY()), new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
					double velocita = f.getVelocita(f2);
					if(velocita>0){
						double time = (distanza/velocita)*3600;
						Graphs.addEdgeWithVertices(metro, f, f2, time);
					}
				}
			}
		}
	}
	
	public Fermata getFermata(String nome){
		for(Fermata f:fermate){
			if(f.getNome().compareTo(nome)==0)
				return f;
		}
		return null;
	}
	
//	public FloydWarshallShortestPaths<Fermata, DefaultWeightedEdge> calcolaCammini(){
//		floyd = new FloydWarshallShortestPaths<Fermata, DefaultWeightedEdge>(metro);
//		return floyd;
//	}
	
	public List<Fermata> calcolaPercorso(Fermata a, Fermata b){
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(metro, a, b);
		GraphPath<Fermata, DefaultWeightedEdge> cammino = dijkstra.getPath();
		if(cammino==null)
			return null;
		return Graphs.getPathVertexList(cammino);
	}
	
//	public List<Fermata> calcolaPercorso(Fermata a, Fermata b){
//		return Graphs.getPathVertexList(floyd.getShortestPath(a, b)); 
//	}
	
	public int getTime(List<Fermata> cammino){
		int time=0;
		int index=0;
		for(Fermata f:cammino){
			Fermata source = f;
			index++; 
			if(index==cammino.size())
				break;
			Fermata target = cammino.get(index);
			time += metro.getEdgeWeight(metro.getEdge(source, target));
		}
		time += (cammino.size()-2)*30;
		return time;
	}

	public SimpleWeightedGraph<Fermata, DefaultWeightedEdge> getGraph(){
		return metro;
	}
	
	public List<Fermata> getFermate(){
		return fermate;
	}
	
//	public static void main(String[] args){
//		Model m = new Model();
//		m.caricaFermate();
//		m.createGraph();	
//		m.calcolaCammini();
//		System.out.println(m.calcolaPercorso(m.getFermata("Ablon"), m.getFermata("Auber")));		
//	}
	
}

