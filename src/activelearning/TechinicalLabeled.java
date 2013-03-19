package activelearning;

import java.util.ArrayList;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;

import classifier.Categorie;
import classifier.RelationalNeighborClassifier;

public class TechinicalLabeled {

	public Node randomLabel(Node[] nodes) {
		boolean labeled = false;
		Node node = null;
		for (int i = 0; i < nodes.length; i++) {
			if (Math.random() > 0.6 && nodes[i].getNodeData().getLabel().isEmpty()) {
				node = nodes[i];
			}
		}
		return node;
	}
	
	public double entropy(int qtdTotal, ArrayList<Categorie> categories) {
		double acum = 0;
		for (int i = 0; i < categories.size(); i++) {
			double result = 0;
			result = -((double)categories.get(i).getQuantidade()/(double)qtdTotal) * (Math
					.log10((double)categories.get(i).getQuantidade()/(double)qtdTotal));
			acum += result;
		}
		return acum;
	}
	
	public UndirectedGraph etiquetRadom(UndirectedGraph ugUnlabeled,UndirectedGraph ugLabeled) {
		Node[] nodesUnlabeled = ugUnlabeled.getNodes().toArray();
		Node[] nodesLabeled = ugLabeled.getNodes().toArray();
		int cont = 0;
		for (int i = 0; i < nodesUnlabeled.length && cont < 100; i++) {
			if (Math.random() > 0.6) {
				nodesUnlabeled[i].getNodeData().setLabel(
						nodesLabeled[i].getNodeData().getLabel());
				cont++;
			}
			
		}
		System.out.println(cont);
		UndirectedGraph ud = ugUnlabeled;
		Edge[] listEdges = ugUnlabeled.getEdges().toArray();
		ud.clear();
		for (int i = 0; i < nodesUnlabeled.length; i++) {
			ud.addNode(nodesUnlabeled[i]);
		}

		for (int i = 0; i < listEdges.length; i++) {
			ud.addEdge(listEdges[i]);
		}

		return ud;
	}

	public ArrayList<Categorie> countCategorie(Node[] neighbors) {
		int contCase = 0;
		int contGenetic = 0;
		int contNeural = 0;
		int contProbabilistic = 0;
		int contReinforcement = 0;
		int contRule = 0;
		int contTheory = 0;

		ArrayList<Categorie> categories = null;
		if (neighbors.length > 0) {
			categories = new ArrayList<Categorie>();
			for (int i = 0; i < neighbors.length; i++) {
				if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.CASE_BASED)) {
					contCase++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.GENETIC_ALGORITHMS)) {
					contGenetic++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.NEURAL_NETWORKS)) {
					contNeural++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.PROBABILISTIC_METHODS)) {
					contProbabilistic++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.REINFORCEMENT_LEARNING)) {
					contReinforcement++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.RULE_LEARNING)) {
					contRule++;
				} else if (neighbors[i].getNodeData().getLabel().equals(RelationalNeighborClassifier.THEORY)) {
					contTheory++;
				}
			}

			if (contCase > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.CASE_BASED, contCase));
			}
			if (contNeural > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.NEURAL_NETWORKS,contNeural));
			}
			if (contGenetic > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.GENETIC_ALGORITHMS,contGenetic));
			}
			if (contProbabilistic > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.PROBABILISTIC_METHODS,contProbabilistic));
			}
			if (contReinforcement > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.REINFORCEMENT_LEARNING,contReinforcement));
			}
			if (contRule > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.RULE_LEARNING, contRule));
			}

			if (contTheory > 0) {
				categories.add(new Categorie(RelationalNeighborClassifier.THEORY, contTheory));
			}
		}

		return categories;
	}
	
	public int probabilityCategorie(Node[] nodes, String categorie) {
		int cont = 0;
		for(Node item : nodes) {
			if(!item.getNodeData().getLabel().isEmpty() && item.getNodeData().getLabel().equals(categorie)) {
				cont++;
			}
		}
		return cont;
	}
}
