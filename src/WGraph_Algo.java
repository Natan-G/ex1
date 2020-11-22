package ex1.ex1.src;

import java.io.*;
import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    weighted_graph graph;
    HashMap<Integer, node_info> parent = new HashMap<Integer, node_info>();

    //constructor
    public WGraph_Algo(){
        this.graph=new WGraph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g-the graph
     */
    @Override
    public void init(weighted_graph g) {
        this.graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     * @return the graph.
     */
    @Override
    public weighted_graph getGraph() {
        if (this.graph != null) return this.graph;
        return null;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * @return the new graph
     */
    @Override
    public weighted_graph copy() {
        if (this.graph == null) return null;
        WGraph_DS cGraph = new WGraph_DS();

        //deep copy for all vertices
        for (node_info vertex : graph.getV()) {
            cGraph.addNode(vertex.getKey());
            cGraph.getNode(vertex.getKey()).setTag(vertex.getTag());
            cGraph.getNode(vertex.getKey()).setInfo(vertex.getInfo());
        }
        //connect all edges
        for (node_info vertex : graph.getV()) {
            if (this.graph.getV(vertex.getKey()) != null) {
                for (node_info v : this.graph.getV(vertex.getKey())) {
                    cGraph.connect(v.getKey(), vertex.getKey(), graph.getEdge(vertex.getKey(), v.getKey()));
                }
            }
        }


        return cGraph;
    }

    /**
     * return true if there is a valid path from every node to each other node.
     * Note: the graph is undirectional.
     * @return
     */
    @Override
    public boolean isConnected() {

        if (graph.nodeSize() == 1 || graph.nodeSize() == 0) return true;
        Collection<node_info> c = graph.getV();
        Iterator<node_info> it = c.iterator();
        if (it.hasNext()) Dijkstra(it.next().getKey());
        for (node_info vertex : graph.getV()) {
            if (!vertex.getInfo().equals("BLACK")) return false;
        }
        return true;
    }

    /**
     * return the length of the shortest path between 2 verticies.
     * if no such path return -1.
     * @param src - start node
     * @param dest - end (target) node
     * @return the length of path
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) return 0;
        List<node_info> list = shortestPath(src, dest);
        if (list == null) return -1;


        return list.get(list.size() - 1).getTag();
    }

    /**
     * return the shortest path between 2 vertices, as an ordered List of nodes.
     * if no such path return null.
     * @param src - start node
     * @param dest - end (target) node
     * @return the vertices list of the shortest path.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(graph.getNode(src)==null || graph.getNode(dest)==null) return null;
        List<node_info> list = new ArrayList<>();
        if (src == dest && graph.getNode(src) != null) {
            list.add(graph.getNode(src));
            return list;
        }
        Dijkstra(src);
        node_info vertex = graph.getNode(dest);
        list.add(vertex);
        while (vertex != graph.getNode(src)) {
            vertex = parent.get(vertex.getKey());
            list.add(vertex);
        }

        Collections.reverse(list);

        return list;
    }

    /**
     * Saves this weighted (undirected) graph to the given file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {

        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(f);
            obj.writeObject(graph);
            obj.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream sI = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(sI);
            graph = (weighted_graph) obj.readObject();
            obj.close();
            sI.close();
            return true;

        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /* update the shortest path by weight from the source vertex to every vertex in the graph,
           by using tag vertex as their parent weight and the edge connect between them.
           using vertex.info as visited("WHITE") or not visited("BLACK").
           this methode used in: shortestPath , shortestPathDist and isConnected.

         */
    public void Dijkstra(int src) {
        graph.getNode(src).setTag(0);
        PriorityQueue<node_info> pq = new PriorityQueue<node_info>();
        for (node_info vertex : graph.getV()) {
            if (vertex.getKey() != src) vertex.setTag(Double.POSITIVE_INFINITY);
            vertex.setInfo("WHITE");
            pq.add(vertex);
        }
        while (!pq.isEmpty()) {
            node_info curr = pq.remove();
            if (graph.getV(curr.getKey()) != null) {
                for (node_info vertex : graph.getV(curr.getKey())) {
                    if (vertex.getInfo().equals("WHITE")) {
                        vertex.setTag(curr.getTag());
                        double dist = curr.getTag() + graph.getEdge(curr.getKey(), vertex.getKey());
                        if (vertex.getTag() < dist) {
                            vertex.setTag(dist);
                            parent.put(vertex.getKey(), curr);
                            pq.remove(vertex);
                            pq.add(vertex);
                        }
                    }
                    curr.setInfo("BLACK");
                }
            }
        }

    }
}
