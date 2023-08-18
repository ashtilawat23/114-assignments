package edu.njit.cs114;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Author: Ashalesh Tilawat
 * Date created: 4/25/2023
 */
public class AdjListGraph extends AbstractGraph {

    private List<Edge> [] adjLists;

    public AdjListGraph(int n, boolean directed) {
        super(n, directed);
        init(n);
    }

    @Override
    public void init(int n) {
        adjLists = (List<Edge> []) Array.newInstance(List.class,n);
        for (int i=0; i < n; i++) {
            adjLists[i] = new LinkedList<>();
        }
    }


    @Override
    public void addGraphEdge(int u, int v, int weight) throws GraphException {
        if (v < 0 || v >= adjLists.length) {
            throw new GraphException("Invalid vertex "+v);
        }
        // throw GraphException if the edge already exists
        /**
         * Complete code here
         */
        adjLists[u].add(new Edge(u, v, weight));
        if (!directed) {
            adjLists[v].add(new Edge(v, u, weight));
        }
    }

    @Override
    public Iterator<Edge> getOutgoingEdges(int v) {
        if (v < 0 || v >= adjLists.length) {
            throw new IllegalArgumentException("Invalid vertex "+v);
        }
        return adjLists[v].iterator();
    }

    @Override
    public Edge delGraphEdge(int u, int v) {
        /**
         * Complete code here
         */
        ListIterator<Edge> it = adjLists[u].listIterator();
        while (it.hasNext()) {
            Edge edge = it.next();
            if (edge.to == v) {
                it.remove();
                return edge;
            }
        }
        return null;
    }

    /**
     * Get edge from u to v if it exists else null
     * @param u
     * @param v
     * @return
     */
    public Edge getEdge(int u, int v) {
        /**
         * Complete code here
         */
        if (v < 0 || v >= adjLists.length) {
            throw new IllegalArgumentException("Invalid vertex "+v);
        }
        Iterator<Edge> it = adjLists[u].iterator();
        while (it.hasNext()) {
            Edge edge = it.next();
            if (edge.to == v) {
                return edge;
            }
        }
        return null;
    }

    @Override
    public boolean isEdge(int u, int v) {
        return (getEdge(u, v) != null);
    }

    @Override
    public int weight(int u, int v) throws GraphException {
        Edge edge = getEdge(u,v);
        if (edge == null) {
            throw new GraphException("No edge from " +u+ " to "+v + " exists");
        }
        return edge.weight;
    }

}
