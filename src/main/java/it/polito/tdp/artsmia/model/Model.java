package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private SimpleWeightedGraph<Integer, DefaultEdge> grafo;
	private List<Adiacenza> adiacenze;
	private List<Integer> percorsoBest;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
	}
	
	public List<String> getAllRoles(){
		return dao.getAllRoles();
	}

	public void creaGrafo(String ruolo) {
		this.grafo = new SimpleWeightedGraph<>(DefaultEdge.class);
		this.adiacenze = new ArrayList<>(dao.getAdiacenze(ruolo));
		List<Integer> artisti = new ArrayList<>(dao.getArtisti(ruolo));
		
		Graphs.addAllVertices(grafo, artisti);
		
		for(Adiacenza a: adiacenze) {
			if(grafo.getEdge(a.getIdArtista1(), a.getIdArtista2()) == null)
				Graphs.addEdgeWithVertices(grafo, a.getIdArtista1(), a.getIdArtista2(), a.getPeso());
		}
	}
	
	public boolean grafoContiene(Integer id) {
		if(grafo.containsVertex(id))
			return true;
		return false;
	}
	
	public List<Integer> trovaPercorso(Integer sorgente) {
		this.percorsoBest = new ArrayList<>();
		List<Integer> parziale = new ArrayList<>();
		parziale.add(sorgente);
		cerca(parziale, -1);
		return percorsoBest;
	}
	
	private void cerca(List<Integer> parziale, int peso) {	
		int ultimo = parziale.get(parziale.size()-1);
		// ottengo i vicini
		List<Integer> vicini = Graphs.neighborListOf(grafo, ultimo);
		for(Integer vicino: vicini) {
			if(!parziale.contains(vicino) && peso == -1) {
				parziale.add(vicino);
				cerca(parziale, (int)grafo.getEdgeWeight(grafo.getEdge(ultimo, vicino)));
				parziale.remove(vicino);
			}
			else {
				if(!parziale.contains(vicino) && this.grafo.getEdgeWeight(grafo.getEdge(ultimo, vicino)) == peso) {
					parziale.add(vicino);
					cerca(parziale, peso);
					parziale.remove(vicino);
				}
			}
		}
		
		if(parziale.size() > percorsoBest.size())
			percorsoBest = new ArrayList<>(parziale);
	}

	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}

	public List<Adiacenza> getAdiacenze() {
		return adiacenze;
	}
	
}
