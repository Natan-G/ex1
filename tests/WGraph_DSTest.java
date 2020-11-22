package ex1.tests;


import ex1.ex1.src.WGraph_DS;
import ex1.ex1.src.node_info;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


class WGraph_DSTest {

    @Test
    void getNode() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 10; i++) {
            graph.addNode(i);
        }
        assertTrue(graph.getNode(1) != null);
        graph.removeNode(3);
        assertTrue(graph.getNode(3) == null);

    }

    @Test
    void hasEdge() {
        WGraph_DS graph = new WGraph_DS();
        for (int i = 0; i < 100; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i < 300; i++) {
            int a = (int) Math.random() * 100;
            int b = (int) Math.random() * 100;
            double w = Math.random();
            if (a != b) {
                graph.connect(a, b, w);
            }
        }
        for (int i = 0; i < 100; i++) {
            int a = (int) Math.random() * 100;
            int b = (int) Math.random() * 100;
            if (a != b) {
                assertTrue(graph.hasEdge(a, b));
                assertTrue(graph.hasEdge(b, a));
            }
        }
        graph.removeEdge(1, 2);
        assertFalse(graph.hasEdge(1, 2));

    }

    @Test
    void getEdge() {
        WGraph_DS graph = new WGraph_DS();

        graph.connect(1, 2, 0.1);
        double w = graph.getEdge(1, 2);
        assertNotEquals(graph.getEdge(1, 2), 0.1, 0.0);
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1, 2, 0.1);
        assertEquals(graph.getEdge(1, 2), 0.1);
        graph.connect(1, 2, 3.4);
        assertEquals(graph.getEdge(1, 2), 3.4);
    }

    @Test
    void connect() {
        WGraph_DS graph = new WGraph_DS();

        graph.connect(0, 1, 3.1);
        double w = graph.getEdge(1, 2);
        assertNotEquals(graph.getEdge(1, 2), 3.1, 0.0);
        graph.addNode(0);
        graph.addNode(1);
        graph.connect(0, 1, 3.1);
        assertEquals(graph.getEdge(1, 0), 3.1);
        graph.connect(0, 1, 3.4);
        assertEquals(graph.getEdge(0, 1), 3.4);

    }

    @Test
    void getV() {
        WGraph_DS graph = new WGraph_DS();

        for (int i = 0; i < 100; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                graph.connect(i, j, Math.random());
            }
        }
        for(node_info vertex: graph.getV()){
            for(node_info v : graph.getV(vertex.getKey())){
                assertNotNull(v);
            }
        }
    }


    @Test
    void removeNode() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        assertNull(graph.removeNode(3));
        assertEquals(1,graph.removeNode(1).getKey());
    }

    @Test
    void removeEdge() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.connect(1,2,1);
        assertTrue(graph.hasEdge(1,2));
        graph.removeEdge(2,1);
        assertFalse(graph.hasEdge(1,2));
    }

    @Test
    void nodeSize() {
        WGraph_DS graph = new WGraph_DS();
        for(int i=0 ; i<100 ; i++){
            graph.addNode(i);
        }
        assertEquals(100 , graph.nodeSize());
        int x = (int)Math.random()*100;
        graph.removeNode(x);
        assertEquals(99 , graph.nodeSize());
        graph.removeNode(x);
        assertEquals(99,graph.nodeSize());
        graph.removeNode(101);
        assertEquals(99,graph.nodeSize());
    }

    @Test
    void edgeSize() {
        WGraph_DS graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.connect(1,2,1);
        assertEquals(1 , graph.edgeSize());
        graph.connect(2,1,1);
        assertEquals(1 , graph.edgeSize());
        graph.connect(1,4,2);
        assertEquals(1 , graph.edgeSize());

    }

    @Test
    public void time(){
        long start = new Date().getTime();
        WGraph_DS graph = new WGraph_DS();
        for(int i=0 ; i<1000000 ; i++){
            for(int j=0 ; j<10 ; j++){
                graph.connect(i,(int)Math.random()*999980,Math.random());
            }
        }
        long end=new Date().getTime();
        if(end-start>10000){
            fail("its make up to 10 sec");
        }
        System.out.println("its took :" + (double)(end-start)/100 + "sec");
    }


}