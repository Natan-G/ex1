package ex1.ex1.src;

import java.io.Serializable;
import java.util.*;

/**
 * this class implements ex1.ex1.src.node_info and ex1.ex1.src.weighted_graph interfaces.
 * represents an undirectional weighted graph.
 * this implementation based on efficient compact representation with HashMap
 */

public class WGraph_DS implements weighted_graph, Serializable {


    private  int MC=0;
    private  int edgeSize=0;
    private HashMap <Integer , node_info> graph;




    //constructor
    public WGraph_DS(){
        this.graph=new HashMap<Integer, node_info>();
    }

    /**
     * return the node from the graph by the node_id
     * @param key - the node_id
     * @return node - the node, null if none
     */
    @Override
    public node_info getNode(int key) {
        if (this.graph.containsKey(key)) {
            node_info node = this.graph.get(key);
            if (node == null) {
                return null;
            }
            ++MC;
            return node;
        }
        return null;
    }

    /**
     * hasNi function return true if between 2 vertices have edge
     * @param node1 first node_id
     * @param node2 second node_id
     * @return true if node1 <-> node2 has Edge
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(this.graph.containsKey(node1) && this.graph.containsKey(node2)) {
            NodeInfo vertex1 = (NodeInfo) getNode(node1);//O(1)
            NodeInfo vertex2 = (NodeInfo) getNode(node2);//O(1)
            if(vertex1!=null && vertex2!=null)  return vertex1.hasNi(vertex2.getKey()); //O(1)
        }
        return false;
    }

    /**
     * return the weight(>=0) of the edge between 2 vertices,
     * return -1 if edge doesn't exist.
     * @param node1
     * @param node2
     * @return - the weight
     */

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2)) {
            NodeInfo vertex1 = (NodeInfo) this.graph.get(node1);
            NodeInfo vertex2 = (NodeInfo) this.graph.get(node2);
            return vertex1.edgeweighted.get(vertex2.getKey());
        }
        return -1;
    }

    /**
     * add the give node_id to the graph
     * Note: if the node already exists in the graph the method does simply nothing
     * @param key - the node_id
     */
    @Override
    public void addNode(int key) {
        if(!this.graph.containsKey(key)) {
            node_info node = new NodeInfo(key);
            this.graph.put(key, node);
            ++MC;
        }
    }

    /**
     * coonect 2 vertices by weight edge(weight >=0).
     * if the 2 vertices already exists, the method update the weight of the edge.
     * Note: the addNi function add neighbor to the vertex.
     * @param node1
     * @param node2
     * @param w - the weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (this.graph.containsKey(node1) && this.graph.containsKey(node2) && node1!=node2 && w>=0) {
            NodeInfo vertex1 = (NodeInfo) this.graph.get(node1);
            NodeInfo vertex2 = (NodeInfo) this.graph.get(node2);
            if (!hasEdge(node1, node2) && vertex1!=null && vertex2!=null) {
                vertex1.addNi(vertex2);
                vertex2.addNi(vertex1);
                ++edgeSize;
            }
            vertex1.edgeweighted.put(vertex2.getKey(), w);
            vertex2.edgeweighted.put(vertex1.getKey(), w);
            ++MC;
        }
    }

    /**
     * this method return pointer for the collection
     * representing all the nodes in the graph.
     * @return Collection
     */
    @Override
    public Collection<node_info> getV() {
        return this.graph.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id.
     * @param node_id - key of the node
     * @return Collection list, return null if none
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(this.graph.containsKey(node_id)) {
            List<node_info> list = new ArrayList<>();
            NodeInfo node = (NodeInfo) getNode(node_id);
            if (node.getNi() != null) {
                for (node_info vertex : node.getNi()) {
                    list.add(vertex);
                }
                return list;
            }
        }
        return null;
    }

    /**
     * this method delete the vertex by his key from the graph,
     * and removes all edges that connect to this vertex
     * @param key the node_id that need remove
     * @return the vertex that removed (null if none)
     */
    @Override
    public node_info removeNode(int key) {
        if (this.graph.containsKey(key)){
            NodeInfo node= (NodeInfo) getNode(key);
            List<node_info> list = new ArrayList<>();
            for (node_info vertex : node.ni.values()){
                list.add(vertex);
            }
            for(int i=0 ; i<list.size() ; i++){
                removeEdge(key,list.get(i).getKey());
            }
            this.graph.remove(key);
            ++MC;
            return node;
        }
        return null;

    }

    /**
     * remove the edge in the graph between 2 vertex
     * @param node1 first node key
     * @param node2 second node key
     */
    @Override
    public void removeEdge(int node1, int node2) {
        NodeInfo vertex1 = (NodeInfo) this.graph.get(node1);
        NodeInfo vertex2 = (NodeInfo) this.graph.get(node2);

        if(vertex1.hasNi(vertex2.getKey())){
            vertex1.ni.remove(vertex2.getKey());
            vertex2.ni.remove(vertex1.getKey());
            ++MC;
            --edgeSize;
        }
    }

    /**
     * this method return the number of vertices in the graph
     * @return graph.size
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * @return the number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }

    /**
     * @return the Mode Count - any change in the graph should cause an increment in the ModeCount.
     */
    @Override
    public int getMC() {
        return MC;
    }


    public void setMC(int MC) {
        this.MC = MC;

    }

    @Override
    public boolean equals(Object o) {
        if (this.graph == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return MC == wGraph_ds.MC &&
                edgeSize == wGraph_ds.edgeSize &&
                Objects.equals(graph.getClass() , wGraph_ds.graph.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(MC, edgeSize, graph);
    }

    private class NodeInfo implements node_info , Comparable<node_info> , Serializable {

        private int key;
        private double tag;
        private String info;

        private HashMap<Integer, node_info> ni;
        private HashMap<Integer, Double> edgeweighted;



        //constructor
        public NodeInfo(int key) {
            this.key = key;
            this.tag = Double.POSITIVE_INFINITY;
            this.info = "WHITE";
            this.ni = new HashMap<Integer, node_info>();
            this.edgeweighted=new HashMap<Integer , Double>();// key=key of node2 , double weighted

        }


        @Override
        public int getKey() {
            return this.key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info =s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public void setTag(double t) {
            this.tag = t;
        }

        public void addNi(node_info node) {
            if (this.key != node.getKey() && !hasNi(node.getKey())) {
                this.ni.put(node.getKey(), node);
            }
        }

        public boolean hasNi(int key) {
            return this.ni.containsKey(key);
        }

        public Collection<node_info> getNi() {
            if (this.ni.size()>0) {
                return ni.values();
            }
            return null;
        }


        @Override
        public int compareTo(node_info o) {
            if(this.tag-o.getTag()>0) return 1;
            else if(this.tag-o.getTag()<0) return -1;
            return 0;
        }
    }

}

