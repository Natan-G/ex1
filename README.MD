Project:
	
	this project represents an undirectional and weighted graph that support a large number of nodes,
	based on afficient compact representation.

Data Struct :
	
	this project based on HashMap. HashMap allow i‎nsert search and remove in O(1) (most cases)
	every node has HashMap that represent his neighbor list.
	every graph  create an HashMap and every node has collection of his neighbor
	
Classes : 	
	
	ex1.ex1.src.WGraph_DS: implemants ex1.ex1.src.weighted_graph interface represent set of operations applicable
	on node in an undirectionaland weighted graph by using inner class NodeInfo represent
	ex1.ex1.src.node_info interface.
	
		methods:
			constructor
			getNode: return the vertex from the graph
			hasEdge: return if has edge between 2 vertex 
			getEdge: return the weighted of edge between 2 vertices
			addNode: add vertex to the graph
			connect: connect 2 vertices by edge with weight
			getV() : return collection of all vertex in the graph
			getV(int node_id) : return collection of node neighbors
			removNode : remove and return the vertex from the graph and disconnected all edegs
			removeEdge : remove edge between 2 vertex
			nodeSize
			edgeSize
			getMC : return the mode count (number of changes on the graph)


	ex1.ex1.src.WGraph_Algo: 	implemants ex1.ex1.src.weighted_graph_algorithms
		methods:
			init(graph g): init the graph which this set of algorithms operates on.
			copy : compute a deep copy of this graph
			isConnected : check if there is a valid path from every vertex to each other vertex,
			by usuing Dijkstra algorithm
			shortestPathDist: return the length of the shortest path between 2 vertex (source,desteny).
			shortestPath: return the collection of the shortest path(source , desteny)
			Dijkstra:  update the shortest path by weight from the source vertex to every vertex in the graph,
            by using the tag of vertex as their parent weight and the edge connect between them.
			save : Saves this weighted (undirected) graph to the given file name
			load : load a graph to this graph algorithm.