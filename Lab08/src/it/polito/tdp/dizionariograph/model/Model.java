package it.polito.tdp.dizionariograph.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionariograph.bean.Word;
import it.polito.tdp.dizionariograph.db.WordDAO;

public class Model {
	
	private List<String> parole;
	private Graph<String, DefaultEdge> graph;
	private String vertMax;
	
	
	public void createGraph(int numeroLettere) {

		WordDAO dao = new WordDAO();
		parole = dao.getAllWordsFixedLength(numeroLettere);
		
		graph = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		Graphs.addAllVertices(graph, parole);
		
		this.addEdges(graph);
		System.out.format("Grafo creato: %d vertici, %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());
		
	}

	private void addEdges(Graph<String, DefaultEdge> graph) {
			for (String s1 : this.parole) {
				for (String s2 : this.parole) {
					if (!s1.equals(s2)) {                                    // escludo coppie 
						if(letteraDiversa(s1, s2))
								graph.addEdge(s1, s2);
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
