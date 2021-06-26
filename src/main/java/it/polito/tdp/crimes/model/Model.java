package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {

	private SimpleWeightedGraph <String, DefaultWeightedEdge> grafo;
	private EventsDao dao;
	private List <String> percorsoMigliore;
			
	
	public Model() {
		dao = new EventsDao();
		dao.listAllEvents();
		
	
	}
	
	public void creaGrafo(String categoria, int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(grafo, dao.getVertici(categoria,mese));
		
		
		//aggiungo archi
		for(Adiacenza a : dao.getAdiacenze(categoria, mese)) {
			Graphs.addEdgeWithVertices(grafo, a.getReato1(), a.getReato2(), a.getPeso());
		}
		
		System.out.println("GRAFO CREATO con "+grafo.vertexSet().size()+" vertici e "+grafo.edgeSet().size()+" archi");
		
	}
	
	
	/*
	 Si visualizzi l’elenco di tutti gli archi il cui peso sia superiore al peso medio
	 presente nel grafo. Per ogni arco si visualizzino i due tipi di reato (i due vertici)
	 ed il peso stesso
	 */
	public List <Adiacenza> getArchiFiltrati (String categoria, int mese){
		List <Adiacenza> result = new ArrayList<>();
		double media = 0.0;
		for(Adiacenza a : dao.getAdiacenze(categoria,mese)) {
			media+= a.getPeso();
		}
		
		media = media/dao.getAdiacenze(categoria, mese).size();
		
		for(Adiacenza aa : dao.getAdiacenze(categoria, mese)) {
			if(aa.getPeso()> media) {
				result.add(aa);
			}
		}
		
		return result;
		
	
		
	}
	
	public List <String> getCategorie(){
		return dao.getCategorie();
	}
	
	
	public int nArchi () {
		return this.grafo.edgeSet().size();
	}
	
	public int nVertici () {
		return this.grafo.vertexSet().size();
	}
	
	public List <Adiacenza> getAdiacenze(String categoria, int mese){
		return this.dao.getAdiacenze(categoria,mese);
	}
	
	public List <String> trovaPercorso(String sorgente, String destinazione){
		this.percorsoMigliore = new LinkedList <>();
		List <String> parziale = new LinkedList<>();
		parziale.add(sorgente);
		cerca(destinazione, parziale);
		return this.percorsoMigliore;
	}
	
	private void cerca(String destinazione, List <String> parziale) {
		
		//caso terminale
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(parziale.size()> this.percorsoMigliore.size()) {
				this.percorsoMigliore = new LinkedList<>(parziale);
			}
			return ;
		}
		
		//altrimenti
		for(String vicino : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				
			}
		}
		
		
		
	}
	
	
}
