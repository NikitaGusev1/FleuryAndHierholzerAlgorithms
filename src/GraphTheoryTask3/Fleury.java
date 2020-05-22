package GraphTheoryTask3; 
import static GraphTheoryTask3.Hierholzer.getPath;
import java.util.ArrayList; 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Fleury { 

	private static int vertices; // No. of vertices 
	private static ArrayList<Integer>[] adj; // adjacency list 

	// Constructor 
	Fleury(int numOfVertices) 
	{ 
		// initialise vertex count 
		this.vertices = numOfVertices; 

		// initialise adjacency list 
		initGraph(); 
	} 

	// utility method to initialise adjacency list  
	private void initGraph() 
	{ 
		adj = new ArrayList[vertices]; 
		for (int i = 0; i < vertices; i++) 
		{ 
			adj[i] = new ArrayList<>(); 
		} 
	} 

	// add edge u-v 
	private static void addEdge(Integer u, Integer v) 
	{ 
		adj[u].add(v); 
		adj[v].add(u); 
	} 

	// This function removes edge u-v from graph. 
	private void removeEdge(Integer u, Integer v) 
	{ 
		adj[u].remove(v); 
		adj[v].remove(u); 
	} 

	/* The main function that print Eulerian Trail. 
	It first finds an odd degree vertex (if there 
	is any) and then calls printEulerUtil() to 
	print the path */
	private void printEulerTour() 
	{ 
		// Find a vertex with odd degree 
		Integer u = 0; 
		for (int i = 0; i < vertices; i++) 
		{ 
			if (adj[i].size() % 2 == 1) 
			{ 
				u = i; 
				break; 
			} 
		} 
		
		// Print tour starting from oddv 
		printEulerUtil(u); 
		System.out.println(); 
	} 

	// Print Euler tour starting from vertex u 
	private void printEulerUtil(Integer u) 
	{ 
		// Recur for all the vertices adjacent to this vertex 
		for (int i = 0; i < adj[u].size(); i++) 
		{ 
			Integer v = adj[u].get(i); 
			// If edge u-v is a valid next edge 
			if (isValidNextEdge(u, v)) 
			{ 
				System.out.print(u + "-" + v + " "); 
				
				// This edge is used so remove it now 
				removeEdge(u, v); 
				printEulerUtil(v); 
			} 
		} 
	} 

	// The function to check if edge u-v can be 
	// considered as next edge in Euler Tout 
	private boolean isValidNextEdge(Integer u, Integer v) 
	{ 
		// The edge u-v is valid in one of the 
		// following two cases: 

		// 1) If v is the only adjacent vertex of u 
		// ie size of adjacent vertex list is 1 
		if (adj[u].size() == 1) { 
			return true; 
		} 

		// 2) If there are multiple adjacents, then 
		// u-v is not a bridge Do following steps 
		// to check if u-v is a bridge 
		// 2.a) count of vertices reachable from u 
		boolean[] Visited = new boolean[this.vertices]; 
		int count1 = BFS(u, Visited); 

		// 2.b) Remove edge (u, v) and after removing 
		// the edge, count vertices reachable from u 
		removeEdge(u, v); 
		Visited = new boolean[this.vertices]; 
		int count2 = BFS(u, Visited); 

		// 2.c) Add the edge back to the graph 
		addEdge(u, v); 
		return (count1 > count2) ? false : true; 
	} 

	// A BFS based function to count reachable 
	// vertices from v
	private int BFS(Integer v, boolean[] Visited) 
	{ 
		Visited[v] = false;
                LinkedList<Integer> queue = new LinkedList<Integer>();
                Visited[v] = true;
                queue.add(v);
                while(queue.size() != 0)
                {
                    v = queue.poll();
                    Iterator<Integer> i = adj[v].listIterator(); 
                    while (i.hasNext())
                    {
                        int n = i.next();
                        if(!Visited[n]) 
                        {
                            Visited[n] = true;
                            queue.add(n);
                        }
                    }
                }
		int count = 1; 
		// Recur for all vertices adjacent to this vertex 
		for (int adj : adj[v]) 
		{ 
			if (!Visited[adj]) 
			{ 
				count = count + BFS(adj, Visited); 
			} 
		} 
		return count; 
	} 
  
	public static void main(String[] args) 
	{
            Scanner sc = new Scanner(System.in);
            // Fleury Test
                System.out.println("Enter a number of vertices");
                int V = sc.nextInt(); 
		Fleury graph = new Fleury(V);
                for(int i = 0; i<V-3; ++i) {
                    addEdge(i,i+1);
                    addEdge(i,i+2);
                }
                addEdge(V-2,V-1);
                addEdge(V-2,0);
                addEdge(V-1,0);
                //addEdge(V-1,1);
                long FStart = System.nanoTime();
		graph.printEulerTour();
                long FEnd = System.nanoTime();
                System.out.println("Fleury Duration");
                System.out.println("Time taken in nano seconds: "
                           + (FEnd - FStart)); 
                //Hierholzer Test
                Hierholzer.Graph<Integer> g = new Hierholzer.Graph<>();
                for(int i = 0; i<V-3; ++i) {
                    g.addEdge(i,i+1);
                    g.addEdge(i,i+2);
                }
                g.addEdge(V-2,V-1);
                g.addEdge(V-2,0);
                g.addEdge(V-1,0);
                long HStart = System.nanoTime();
                System.out.println(getPath(g));
                long HEnd = System.nanoTime();
                System.out.println("Hierholzer Duration");
                System.out.println("Time taken in nano seconds: "
                           + (HEnd - HStart)); 
                
	} 
} 