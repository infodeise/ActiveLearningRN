import java.io.IOException;
import java.util.ArrayList;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;

import util.GraphUtils;
import activelearning.ActiveLearning;
import activelearning.TechinicalLabeled;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String pathNode = System.getProperty("user.dir")+"\\data\\cora.content";
		String pathEdge = System.getProperty("user.dir")+"\\data\\cora.cites";
		try {
			GraphUtils nodes  = new GraphUtils();
			GraphUtils nodesLabeled = new GraphUtils();
			ArrayList<Node> undUnlabeled = nodes.readNodes(pathNode, false);
			ArrayList<Node> undLabeled = nodesLabeled.readNodes(pathNode, true);
			
			ArrayList<Edge> edges = nodes.readEdges(pathEdge, undUnlabeled);
			ArrayList<Edge> edgesLabeled = nodesLabeled.readEdges(pathEdge, undLabeled);
			
			UndirectedGraph undirectGraphUnlabeled = nodes.undirectGraph(undUnlabeled, edges);
			UndirectedGraph undirectGraphLabeled = nodesLabeled.undirectGraph(undLabeled, edgesLabeled);
			
			TechinicalLabeled graph = new TechinicalLabeled();
			
			ActiveLearning.Uncertainty(graph, undirectGraphUnlabeled, undirectGraphLabeled);
			System.out.println("=============================================RANDOMICO==========================");
			
			GraphUtils graphUtils  = new GraphUtils();
			GraphUtils graphUtilsLabeled = new GraphUtils();
			ArrayList<Node> nodesUnlabeled = graphUtils.readNodes(pathNode, false);
			ArrayList<Node> nodesLabeled2 = graphUtilsLabeled.readNodes(pathNode, true);
			
			ArrayList<Edge> edges2 = graphUtils.readEdges(pathEdge, nodesUnlabeled);
			ArrayList<Edge> edgesLabeled2 = graphUtilsLabeled.readEdges(pathEdge, nodesLabeled2);
			
			UndirectedGraph graphUnlabeled = graphUtils.undirectGraph(nodesUnlabeled, edges2);
			UndirectedGraph graphLabeled = nodesLabeled.undirectGraph(nodesLabeled2, edgesLabeled2);
			ActiveLearning.Random(graph, graphUnlabeled, graphLabeled);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
