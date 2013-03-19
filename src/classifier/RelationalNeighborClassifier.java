package classifier;

import java.util.ArrayList;
import java.util.Collections;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeIterable;
import org.gephi.graph.api.UndirectedGraph;

import util.CategorieComparator;
import activelearning.TechinicalLabeled;

public class RelationalNeighborClassifier {

	public static final String CASE_BASED = "Case_Based";
	public static final String GENETIC_ALGORITHMS = "Genetic_Algorithms";
	public static final String NEURAL_NETWORKS = "Neural_Networks";
	public static final String PROBABILISTIC_METHODS = "Probabilistic_Methods";
	public static final String REINFORCEMENT_LEARNING = "Reinforcement_Learning";
	public static final String RULE_LEARNING = "Rule_Learning";
	public static final String THEORY = "Theory";

	private UndirectedGraph undirectGraph;
	private int labeled;
	private int classified;

	public RelationalNeighborClassifier(UndirectedGraph undirectGraph, int labeled) {
		this.undirectGraph = undirectGraph;
		this.labeled = labeled;
		this.classified = 0;
	}

	public UndirectedGraph coletiveClassifier() {
		Node[] listNodes = this.undirectGraph.getNodes().toArray();
		int cont = 0;
		for (int i = 0; i < listNodes.length && cont<100; i++) {
			if (listNodes[i].getNodeData().getLabel().isEmpty()) {
				NodeIterable nodeNeighbor = this.undirectGraph.getNeighbors(listNodes[i]);
				Node[] listNeighbor = nodeNeighbor.toArray();

				TechinicalLabeled uncertainty = new TechinicalLabeled();
				ArrayList<Categorie> categories = uncertainty
						.countCategorie(listNeighbor);
				if(categories != null && categories.size()>0) {
					Collections.sort(categories, new CategorieComparator());
					listNodes[i].getNodeData().setLabel(categories.get(categories.size()-1).getCategorie());
					cont++;
				}
				
			}
		}
		Edge[] listEdges = this.undirectGraph.getEdges().toArray();
		this.undirectGraph.clear();
		for(int i =0;i<listNodes.length;i++) {
			this.undirectGraph.addNode(listNodes[i]);
		}
		
		for(int i =0;i<listEdges.length;i++) {
			this.undirectGraph.addEdge(listEdges[i]);
		}
		return this.undirectGraph;

	}

	public UndirectedGraph getUndirectGraph() {
		return undirectGraph;
	}

	public void setUndirectGraph(UndirectedGraph undirectGraph) {
		this.undirectGraph = undirectGraph;
	}

	public int getLabeled() {
		return labeled;
	}

	public void setLabeled(int labeled) {
		this.labeled = labeled;
	}

	public int getClassified() {
		Node[] node = undirectGraph.getNodes().toArray();
		for(Node item : node){
			if(!item.getNodeData().getLabel().isEmpty()) {
				this.classified++;
			}
		}
		return classified;
	}

	public void setClassified(int classified) {
		this.classified = classified;
	}
	
	
	
	
}
