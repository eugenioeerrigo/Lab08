package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private List<String> parole;
	private Graph<String, DefaultEdge> graph;
	private String vertMax;
	
	
	public void createGraph(int numeroLettere) {

		//Leggo lista di oggetti dal DB (da aggiungere ai vertici)
		WordDAO dao = new WordDAO();
		parole = dao.getAllWordsFixedLength(numeroLettere);
		
		//Creo grafo
		graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		//Aggiungo i vertici
		Graphs.addAllVertices(graph, parole);
		
		//Aggiungo gli archi tramite metodo
		this.addEdges(graph);
		
		System.out.format("Grafo creato: %d vertici, %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());
		
	}

	/**
	 * Aggiunge gli archi tramite doppio ciclo (coppie possibili) 
	 */
	private void addEdges(Graph<String, DefaultEdge> graph) {
			for (String s1 : this.parole) {
				for (String s2 : this.parole) {
					if (!s1.equals(s2)) {                // escludo coppie uguali
						if(letteraDiversa(s1, s2))                       //Scelgo le coppie che hanno 1 sola lettera diversa
								graph.addEdge(s1, s2);                  //addEdge aggiunge null per la seconda coppia (a,b) e (b,a)
					}
				}
			}
		}
		

	private boolean letteraDiversa(String s1, String s2) {
		int cont = 0;
		
		for(int i=0; i<s1.length(); i++) {
			if(cont>1)
				break;
			if(s1.charAt(i)!=s2.charAt(i))
				cont++;
		}
			
		if(cont==1)
			return true;
		else
			return false;
	}

	public List<String> displayNeighbours(String parolaInserita) {
		if(graph == null)
			return new ArrayList<>();
		
		List<String> vicine = new ArrayList<>();
		if(graph.containsVertex(parolaInserita)) {
			for(String p : parole) {
				if(graph.containsEdge(parolaInserita, p))
					vicine.add(p);
			}
		}
		return vicine;
	}

	public int findMaxDegree() {
		int max = 0;
		
		if(graph == null)
			return -1;
		
		vertMax = "";
		
		Set<String> vertici = graph.vertexSet();
		for(String s : vertici)
			if(graph.degreeOf(s) > max) {
				max = graph.degreeOf(s);
				vertMax = s;
			}
		return max; 
	}

	public String getVertMax() {
		return vertMax;
	}

}
