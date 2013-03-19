package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.UndirectedGraph;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerFactory;
import org.gephi.io.importer.api.ImportController;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

public class GraphUtils {
	
// Init a project - and therefore a workspace
	ProjectController projectController;

	Workspace workspace;
	GraphModel graphModel;
	Container container;
	ImportController importController;

	public GraphUtils() {
		this.projectController = Lookup.getDefault().lookup(
				ProjectController.class);
		this.projectController.newProject();

		this.workspace = projectController.getCurrentWorkspace();
		
		graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		container  = Lookup.getDefault().lookup(ContainerFactory.class).newContainer();
		importController = Lookup.getDefault().lookup(ImportController.class);
	}

	public ArrayList<Node> readNodes(String path, boolean label) throws IOException {
		InputStream is = new FileInputStream(path);
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		String line = br.readLine();
		ArrayList<Node> nodes = new ArrayList<Node>();
		while(line!=null) {
			String[] nodeItem = line.split("\\t");
			Node node = graphModel.factory().newNode(nodeItem[0]);
			String labelNode = "";
			if(label) {
				labelNode = nodeItem[(nodeItem.length-1)];
			}
			node.getNodeData().setLabel(labelNode);
			nodes.add(node);
			line = br.readLine();
		}
		br.close();
		ir.close();
		is.close();
		
		return nodes;
	}
	
	public ArrayList<Edge> readEdges(String path, ArrayList<Node> nodes) throws IOException {
		InputStream is = new FileInputStream(path);
		InputStreamReader ir = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(ir);
		ArrayList<Edge> edges = new ArrayList<Edge>();
		String line = br.readLine();
		while(line!=null) {
			String[] nodeItem = line.split("\\t");
			Node node1 = null;
			Node node2 = null;
			UndirectedGraph und = graphModel.getUndirectedGraph();
			und.clear();
			for(Node item : nodes) {
				if(String.valueOf(item.getNodeData().getId()).equals(nodeItem[1])) {
					node1 = item; 
				} else if(String.valueOf(item.getNodeData().getId()).equals(nodeItem[0])) {
					node2 = item;
				}
//				und.addNode(item);
			}
//			Edge edge = graphModel.factory().newEdge(und.getNode(nodeItem[1]), und.getNode(nodeItem[0]));
			Edge edge = graphModel.factory().newEdge(node1, node2);
			edges.add(edge);
			
			line = br.readLine();
		}
		is.close();
		ir.close();
		br.close();
		
		return edges;
	}
	
	public UndirectedGraph undirectGraph(ArrayList<Node> nodes, ArrayList<Edge> edges) {
		UndirectedGraph und = graphModel.getUndirectedGraph();
		und.clear();
		for(int i =0;i<nodes.size();i++) {
			und.addNode(nodes.get(i));
		}
		
		for(int i =0;i<edges.size();i++) {
			und.addEdge(edges.get(i));
		}
		
		return und;
	}
	
	public UndirectedGraph undirectGraph(Node[] nodes, Edge[] edges) {
		UndirectedGraph und = graphModel.getUndirectedGraph();
		und.clear();
		for(int i =0;i<nodes.length;i++) {
			und.addNode(nodes[i]);
		}
		
		for(int i =0;i<edges.length;i++) {
			und.addEdge(edges[i]);
		}
		
		return und;
	}
}
