package edu.njit.cs114;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Author: Ashalesh Tilawat
 * Date created: 4/25/2023
 */
public class GraphSearch {

    public static final int VISITED = 1;
    public static final int UNVISITED = 0;


    public static void preVisit(Graph g, int v) {
        System.out.print(v+"(" + g.getMark(v)+ "),");
    }

    public static void postVisit(Graph g, int v) {}

    public static void graphTraverseBFS(Graph g) {
        System.out.println("breadth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++) {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++) {
            if (g.getMark(v) == UNVISITED) {
                System.out.println("Start vertex : "+v);
                bfs(g, v);
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public static void graphTraverseDFS(Graph g) {
        System.out.println("Depth-first search of graph..");
        for (int v = 0; v < g.numVertices(); v++) {
            g.setMark(v, UNVISITED);
        }
        for (int v = 0; v < g.numVertices(); v++) {
            if (g.getMark(v) == UNVISITED) {
                System.out.println("Start vertex : "+v);
                dfs(g, v);
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public static void dfs(Graph g, int v) {
        preVisit(g, v);
        g.setMark(v,VISITED);
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        while (outEdgeIter.hasNext()) {
            Graph.Edge edge = outEdgeIter.next();
            int w = edge.to;
            if (g.getMark(w) == UNVISITED) {
                dfs(g, w);
            }
        }
        postVisit(g, v);
    }

    public static void bfs(Graph g, int start) {
        Queue <Integer> q = new LinkedList<>();
        q.add(start);
        g.setMark(start, 1);
        while (!q.isEmpty()) {
            int v = q.remove();
            preVisit(g, v);
            Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
            while (outEdgeIter.hasNext()) {
                Graph.Edge edge = outEdgeIter.next();
                int w = edge.to;
                if (g.getMark(w) == 0) {
                    g.setMark(w, 1);
                    q.add(w);
                }
            }
        }
        postVisit(g, start);
    }

    //COMPLETE THE FOLLOWING METHODS FOR HOMEWORK ASSIGNMENT

    /**
     * (complete it for homework assignment)
     * Returns true if a cycle exists in undirected graph
     * @param g undirected graph
     * @return
     */
    public static boolean cycleExists(Graph g, int v, int parent, boolean[] visited) {
        visited[v] = true;
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        while (outEdgeIter.hasNext()) {
            Graph.Edge edge = outEdgeIter.next();
            int w = edge.to;
            if (!visited[w]) {
                if (cycleExists(g, w, v, visited)) {
                    return true;
                }
            } else if (w != parent) {
                return true;
            }
        }
        return false;
    }

    public static boolean cycleExists(Graph g) {
        boolean[] visited = new boolean[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++) {
            if (!visited[i]) {
                if (cycleExists(g, i, -1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * (complete it for homework assignment)
     * Returns true if a simple cycle with odd number of edges exists in undirected graph
     * @param g undirected graph
     * @return
     */
    public static boolean oddCycleExists(Graph g, int v, int parent, boolean[] visited, int depth) {
        visited[v] = true;
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        while (outEdgeIter.hasNext()) {
            Graph.Edge edge = outEdgeIter.next();
            int w = edge.to;
            if (!visited[w]) {
                if (oddCycleExists(g, w, v, visited, depth + 1)) {
                    return true;
                }
            } else if (w != parent && (depth % 2 == 1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean oddCycleExists(Graph g) {
        boolean[] visited = new boolean[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++) {
            if (!visited[i]) {
                if (oddCycleExists(g, i, -1, visited, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Does the directed graph have a cycle of directed edges ? (Extra credit)
     * @param g
     * @return
     */
    public enum VertexStatus { UNVISITED, VISITING, VISITED }

    public static boolean cycleExistsDirect(Graph g, int v, VertexStatus[] status) {
        status[v] = VertexStatus.VISITING;
        Iterator<Graph.Edge> outEdgeIter = g.getOutgoingEdges(v);
        while (outEdgeIter.hasNext()) {
            Graph.Edge edge = outEdgeIter.next();
            int w = edge.to;
            if (status[w] == VertexStatus.UNVISITED) {
                if (cycleExistsDirect(g, w, status)) {
                    return true;
                }
            } else if (status[w] == VertexStatus.VISITING) {
                return true;
            }
        }
        status[v] = VertexStatus.VISITED;
        return false;
    }

    public static boolean cycleExistsDirect(Graph g) {
        VertexStatus[] status = new VertexStatus[g.numVertices()];
        for (int i = 0; i < g.numVertices(); i++) {
            status[i] = VertexStatus.UNVISITED;
        }
        for (int i = 0; i < g.numVertices(); i++) {
            if (status[i] == VertexStatus.UNVISITED) {
                if (cycleExistsDirect(g, i, status)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String [] args) throws Exception {
        Graph g = new AdjListGraph(8, true);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(0,3);
        g.addEdge(1,2);
        g.addEdge(1,3);
        g.addEdge(2,0);
        g.addEdge(3,2);
        g.addEdge(4,3);
        g.addEdge(4,6);
        g.addEdge(5,7);
        g.addEdge(6,5);
        g.addEdge(7,5);
        g.addEdge(7,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
        System.out.println("Directed cycle exists in g ? " + cycleExistsDirect(g));
        g = new AdjListGraph(8, false);
        g.addEdge(0,1);
        g.addEdge(1,3);
        g.addEdge(3,2);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(5,7);
        g.addEdge(4,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
       System.out.println("Cycle exists in g ? " + cycleExists(g));
        g = new AdjListGraph(8, false);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,3);
        g.addEdge(1,4);
        g.addEdge(3,2);
        g.addEdge(3,4);
        g.addEdge(4,5);
        g.addEdge(6,5);
        g.addEdge(5,7);
        g.addEdge(7,6);
        System.out.println(g);
        graphTraverseBFS(g);
        graphTraverseDFS(g);
       System.out.println("Cycle exists in g ? " + cycleExists(g));
       System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
       g = new AdjListGraph(7, false);
       g.addEdge(0,1);
       g.addEdge(0,2);
       g.addEdge(0,3);
       g.addEdge(2,4);
       g.addEdge(2,5);
       g.addEdge(3,6);
       System.out.println(g);
       System.out.println("Cycle exists in g ? " + cycleExists(g));
       g = new AdjListGraph(7, false);
       g.addEdge(0,1);
       g.addEdge(1,2);
       g.addEdge(2,3);
       g.addEdge(3,0);
       g.addEdge(3,4);
       g.addEdge(4,5);
       g.addEdge(5,6);
       g.addEdge(6,3);
       System.out.println(g);
       System.out.println("Cycle exists in g ? " + cycleExists(g));
       System.out.println("Odd cycle exists in g ? " + oddCycleExists(g));
    }


}
