package ods;

import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

public class AdjacencyLists implements Graph {
	int n;
	
	List<Integer>[] adj;
	HashMap<Integer,String> labels;

	@SuppressWarnings("unchecked")
	public AdjacencyLists(int n0) {
		n = n0;
		adj = (List<Integer>[])new List[n];
		for (int i = 0; i < n; i++) 
			adj[i] = new ArrayStack<Integer>();
	}

	public String toString() {
		if( labels == null ) return toStringPlain();
		return toStringPretty(labels);
	}
	public String toStringPlain() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(i + ": ");
			for (int j : adj[i]) {
				sb.append(j + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public String toStringPretty(HashMap<Integer,String> labels ) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(labels.get(i) + ": ");
			for (int j : adj[i]) {
				sb.append(labels.get(j) + " ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public void setLabels(HashMap<Integer,String> labels ) {
		this.labels = labels; // Does zero error-checking
	}

	public String getLabel(int i) {
		if( labels == null ) return "" + i;
		return labels.get(i);
	}
	public void addEdge(int i, int j) {
		adj[i].add(j);
	}

	public boolean hasEdge(int i, int j) {
		return adj[i].contains(j);
	}

	public int inDegree(int i) {
		int deg = 0;
		for (int j = 0; j < n; j++)
			if (adj[j].contains(i)) deg++;
		return deg;
	}

	public List<Integer> inEdges(int i) {
		List<Integer> edges = new ArrayStack<Integer>();
		for (int j = 0; j < n; j++)
			if (adj[j].contains(i))	edges.add(j);
		return edges;
	}

	public int nVertices() {
		return n;
	}

	public int outDegree(int i) {
		return adj[i].size();
	}

	public List<Integer> outEdges(int i) {
		return adj[i];
	}

	public void removeEdge(int i, int j) {
		Iterator<Integer> it = adj[i].iterator();
		while (it.hasNext()) {
			if (it.next() == j) {
				it.remove();
				return;
			}
		}	
	}
	
	public static AdjacencyLists mesh(int n) {
		AdjacencyLists g = new AdjacencyLists(n*n);
		for (int k = 0; k < n*n; k++) {
			if (k % n > 0) 
				g.addEdge(k, k-1);
			if (k >= n)
				g.addEdge(k, k-n);
			if (k % n != n-1)
				g.addEdge(k, k+1);
			if (k < n*(n-1))
				g.addEdge(k, k+n);
		}
		return g;
	}
	
	public static void main(String[] args) {
		AdjacencyLists g = mesh(4);
		Algorithms.bfsZ(g,0);
		Algorithms.dfsZ(g,0);
		Algorithms.dfs2Z(g,0);

		g = new AdjacencyLists(5);
		System.out.println( "empty graph:\n" + g );

		g.addEdge(0,1);
		g.addEdge(0,2);
		g.addEdge(0,3);
		g.addEdge(0,4);
		g.addEdge(1,0);

		System.out.println( "graph with 5 vertices and 5 edges:\n" + g );
		HashMap<Integer,String> labels = new HashMap<>();
		labels.put(0,"zero");
		labels.put(1,"one");
		labels.put(2,"two");
		labels.put(3,"three");
		labels.put(4,"four");
		g.setLabels(labels);
		System.out.println( "graph with 5 vertices and 5 edges:\n" + g );

		for( int i=0; i < 5; i++ ) {
			System.out.println( "label of " + i + " is: " + g.getLabel(i));
		}
	}

}
