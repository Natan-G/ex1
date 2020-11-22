package ex1.tests;

import ex1.ex1.src.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {


    @Test
    void copy() {
        WGraph_DS g0 = new WGraph_DS();
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(g0);

        for(int i=0 ; i<100 ; i++){
            g0.addNode(i);
        }
        for(int i=0 ; i<100 ; i++){
            for(int j=i+1 ; j<100 ; j++){
                g0.connect(i,j,Math.random());
            }
        }
        WGraph_DS g2 = (WGraph_DS) g1.copy();
        assertEquals(g0.nodeSize() , g2.nodeSize());
        assertEquals(g0.edgeSize() , g2.edgeSize());
        g2.removeNode(101);
        assertEquals(g0.nodeSize() , g2.nodeSize());
        g0.removeNode(1);
        assertNotEquals(g0.nodeSize() , g2.nodeSize());
    }

    @Test
    void isConnected() {
        WGraph_DS g0 = new WGraph_DS();
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(g0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.addNode(7);
        g0.connect(1,2,1);
        g0.connect(2,4,1);
        g0.connect(2,3,1);
        g0.connect(3,4,1);
        g0.connect(4,5,1);
        g0.connect(4,6,1);
        g0.connect(4,7,1);

        g0.removeNode(1);
        assertTrue(g1.isConnected());
        g0.removeNode(7);
        assertTrue(g1.isConnected());
        g0.removeNode(4);
        assertFalse(g1.isConnected());
        g0.addNode(4);
        g0.connect(4,2,1);
        g0.connect(4,6,1);
        g0.connect(4,5,1);
        assertTrue(g1.isConnected());
    }

    @Test
    void shortestPathDist() {
        WGraph_DS g0 = new WGraph_DS();
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(g0);
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.addNode(7);
        g0.connect(1,2,1);
        g0.connect(1,4,2);
        g0.connect(2,5,4);
        g0.connect(5,7,2);
        g0.connect(4,3,3);
        g0.connect(4,6,2);
        g0.connect(4,7,10);
        g1.shortestPath(1,8);
        assertEquals( -1,g1.shortestPathDist(1,8) );
        g1.shortestPath(1,7);
        assertEquals(7 , g1.shortestPathDist(1,7));
        g0.removeNode(2);
        assertEquals(12 , g1.shortestPathDist(1,7));
    }

    @Test
    void shortestPath() {WGraph_DS g0 = new WGraph_DS();
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(g0);
        assertNull(g1.shortestPath(1,7));
        g0.addNode(1);
        g0.addNode(2);
        g0.addNode(3);
        g0.addNode(4);
        g0.addNode(5);
        g0.addNode(6);
        g0.addNode(7);
        g0.connect(1,2,1);
        g0.connect(1,4,2);
        g0.connect(2,5,4);
        g0.connect(5,7,2);
        g0.connect(4,3,3);
        g0.connect(4,6,2);
        g0.connect(4,7,10);
        List<node_info> list = g1.shortestPath(1,7);
        assertEquals(2 , list.get(1).getKey());
        list.remove(3);
        assertEquals(5,list.get(2).getKey() );

    }

    @Test
    void save_load() {
        weighted_graph g0 = new WGraph_DS();
        for(int i=0 ; i<50 ; i++){
            g0.addNode(i);
        }

        weighted_graph_algorithms g1 = new WGraph_Algo();
        g1.init(g0);
        String str = "g0.obj";
        g1.save(str);
        weighted_graph g2 = new WGraph_DS();
        for(int i=0 ; i<50 ; i++){
            g2.addNode(i);
        }

        g1.load(str);
        assertEquals(g0,g2);
        g0.removeNode(0);
        assertNotEquals(g0,g2);
    }




}