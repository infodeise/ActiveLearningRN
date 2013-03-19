package activelearning;

import java.io.IOException;
import java.util.ArrayList;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;

import util.GraphUtils;

import classifier.Categorie;
import classifier.RelationalNeighborClassifier;

public class ActiveLearning {
	public static void Uncertainty (TechinicalLabeled graph, UndirectedGraph undirectGraphUnlabeled, UndirectedGraph undirectGraphLabeled) throws IOException{
		UndirectedGraph undirect = graph.etiquetRadom(undirectGraphUnlabeled, undirectGraphLabeled);
		int labeled = 100;
		RelationalNeighborClassifier rnc = new RelationalNeighborClassifier(undirect, labeled);
		double precision = ((double)rnc.getLabeled()+(double)rnc.getClassified())/(double)undirectGraphUnlabeled.getNodes().toArray().length;
		System.out.println("Precision: "+precision+" Labeled:"+rnc.getLabeled());
		UndirectedGraph classifierUndirect = rnc.coletiveClassifier();
		
		Node[] nodesUncertainity = classifierUndirect.getNodes().toArray();
		Node nodeEntropy = null;
		double maior = 0;
		for(int i=0;i<nodesUncertainity.length;i++) {
			if(nodesUncertainity[i].getNodeData().getLabel().isEmpty()) {
				Node[] neighbors = classifierUndirect.getNeighbors(nodesUncertainity[i]).toArray();
				ArrayList<Categorie> c = graph.countCategorie(neighbors);
				double result=0;
				if(c!=null && c.size()>0) {
					result = graph.entropy(c.size(), c);
					if(result > maior) {
						maior = result;
						nodeEntropy = nodesUncertainity[i];
					}
				} //else {
//					result = graph.entropy(nodesUncertainity.length, graph.countCategorie(nodesUncertainity));
//					if(result > maior) {
//						maior = result;
//						nodeEntropy = nodesUncertainity[i];
//					}
//				}
			}
		}
		double precision2 = 0;
		
		while(precision2 < 1) {
			Node nodeLabeled = findNodeId(undirectGraphLabeled.getNodes().toArray(),nodeEntropy.getNodeData().getId());
			String label = nodeLabeled.getNodeData().getLabel();
			Node unlabeled = findNodeId(classifierUndirect.getNodes().toArray(), nodeEntropy.getNodeData().getId());
			Node[] nodes2 = classifierUndirect.getNodes().toArray();
			for(Node item : nodes2) {
				if(item.getId() == unlabeled.getId()) {
					item.getNodeData().setLabel(label);
				}
			}
			Edge[] edges = classifierUndirect.getEdges().toArray();
			classifierUndirect.clear();
			GraphUtils g = new GraphUtils();
			classifierUndirect = g.undirectGraph(nodes2, edges);
			labeled++;
			RelationalNeighborClassifier rnc2 = new RelationalNeighborClassifier(classifierUndirect, labeled);
			rnc2.coletiveClassifier();
			precision2 = ((double)(rnc2.getLabeled())+(double)rnc2.getClassified())/(double)nodes2.length;
			System.out.println("Precision: "+precision2+" Labeled:"+rnc2.getLabeled());
			
		}
	}
	
	public static void Random (TechinicalLabeled graph, UndirectedGraph undirectGraphUnlabeled, UndirectedGraph undirectGraphLabeled) throws IOException {
		UndirectedGraph undirect = graph.etiquetRadom(undirectGraphUnlabeled, undirectGraphLabeled);
		int labeled = 100;
		RelationalNeighborClassifier rnc = new RelationalNeighborClassifier(undirect, labeled);
		double precision = ((double)rnc.getLabeled()+(double)rnc.getClassified())/(double)undirectGraphUnlabeled.getNodeCount();
		System.out.println("Precision: "+precision+" Labeled:"+rnc.getLabeled());
		UndirectedGraph classifierUndirect = rnc.coletiveClassifier();
		
		Node[] nodesUncertainity = classifierUndirect.getNodes().toArray();
		Node nodeRadom = graph.randomLabel(nodesUncertainity);
		double precision2 =0;
		while(precision2 < 1) {
			Node nodeLabeled = findNodeId(undirectGraphLabeled.getNodes().toArray(),nodeRadom.getNodeData().getId());
			String label = nodeLabeled.getNodeData().getLabel();
			Node unlabeled = findNodeId(classifierUndirect.getNodes().toArray(), nodeRadom.getNodeData().getId());
			Node[] nodes2 = classifierUndirect.getNodes().toArray();
			for(Node item : nodes2) {
				if(item.getId() == unlabeled.getId()) {
					item.getNodeData().setLabel(label);
				}
			}
			Edge[] edges = classifierUndirect.getEdges().toArray();
			classifierUndirect.clear();
			GraphUtils g = new GraphUtils();
			classifierUndirect = g.undirectGraph(nodes2, edges);
			
//			String label = undirectGraphLabeled.getNode(nodeRadom.getId()).getNodeData().getLabel();
//			classifierUndirect.getNode(nodeRadom.getId()).getNodeData().setLabel(label);
			labeled++;
			RelationalNeighborClassifier rnc2 = new RelationalNeighborClassifier(classifierUndirect, labeled);
			rnc2.coletiveClassifier();
			precision2 = ((double)(rnc2.getLabeled())+(double)rnc2.getClassified())/(double)nodes2.length;
			System.out.println("Precision: "+precision2+" Labeled:"+rnc2.getLabeled());
			
		}
	}
	
	private static Node findNodeId(Node[] nodes, String id) {
		for(Node item : nodes) {
			if(item.getNodeData().getId().equals(id)) {
				return item;
			}
		}
		return null;
	}

}
