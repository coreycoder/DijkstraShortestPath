package djikstra;

public class Dijkstra {

	private int[][] edges; //two-dimensional int array adjacency matrix to hold edges and edge weights
	private int[] previous; //array that holds the index of the previous vertex to each of the indexes.
	private int[][] cityInt; // two-dimensional array for population and elevation
	private String[][] cityString; // two-dimensional array for the abbreviation and the name
	/*
	 * This is the Graph constructor. It takes parameters size n and initializes an empty adjacency matrix
	 * of size n x n. Then it initializes the previous array with size n. Then it initializes the 
	 * cityString and cityInt arrays of n x 2. 
	 */
	public Dijkstra(int n) {
		edges = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				edges[i][j] = 0;
			}
		}
		previous = new int[n];
		cityString = new String[n][2];
		cityInt = new int[n][2];
	}
	/*
	 * addEdge method takes the source, target, and weight as parameters and sets the weight
	 * of the edge source to target from 0 to the weight.
	 */
	public void addEdge(int source, int target, int weight){
		edges[source][target] = weight;
		
	}
	/*
	 * isEdge method takes the source and target as parameters. It checks if the weight at 
	 * index [source][target] is not equal to 0, meaning that there is a weight, and thus
	 * an edge. Returns true if there is an edge, false otherwise.
	 */
	public boolean isEdge(int source, int target) {
		if (edges[source][target] != 0) {
			return true;
		}
		else 
			return false;
	}
	/*
	 * removeEdge method takes the source and target as parameters and sets the weight
	 * of index [source][target] to 0, basically removing that edge.
	 */
	public void removeEdge(int source, int target) {
		edges[source][target] = 0;
	}
	/*
	 * getWeight method takes the source and target as parameters and returns 
	 * the weight of the edge from source to target.
	 */
	public int getWeight(int source, int target) {
		int weight;
		weight = edges[source][target];
		return weight;
	}
	
	/*
	 * neighbors method takes the given vertex as a parameter. The vertex is put into
	 * the row position in the matrix for it is the source and we are looking for 
	 * the targets. The first for loop just counts for how many other vertices the
	 * source vertex connects to. Then create an array of size count to hold the neighbors.
	 * Using a for loop in the same fashion, add the vertices the source connects to into 
	 * the neighbor array called answer. Return the answer array.
	 */
	public int[] neighbors(int vertex) {
		int count = 0;
		int[] answer;
		
		for (int i = 0; i < 20; i++) {
			if (edges[vertex][i] != 0) {
				count++;
			}
		}
		answer = new int[count];
		count = 0;
		for (int i = 0; i < 20; i++) {
			if (edges[vertex][i] != 0) {
				answer[count++] = i;
			}
		}
		return answer;
	}
	/*
	 * findMin method takes the distance array used in the Dijkstra method as a parameter.
	 * Using a for loop, go through the array to find the vertex with the smallest distance.
	 * Also make sure that the vertex has not already been gone through yet as shown by
	 * the != 9000000 (vertex set to 9000000 when it has been gone through).
	 */
	public int findMin(int[] dist) {
		int min = dist[0];
		int place = 0;
		for (int i = 1; i < dist.length; i++) {
			if (dist[i] < min && dist[i] != 9000000) {
				min = dist[i];
				place = i;
			}
		}
			return place;
	}

	/*
	 * dijkstra method takes the source and target as parameters. First creates a distance
	 * array of size n = 20. Then initially sets u to the source vertex. Using a for loop, 
	 * set the source vertex u to a distance of 0 while all other vertices have a distance
	 * of infinity (used 1000000 instead). Then, using a while loop until the target is found,
	 * find the vertex with the minimum distance by calling the findMin method and relax 
	 * its neighbors that are found using the neighbors method. This dijkstra method 
	 * differs from the conventional dijkstra method in that it stops and returns the 
	 * previous array when the target is reached, as opposed to going through relaxing
	 * all the vertices from a single source and returning the path with the shortest distance.
	 */
	public int dijkstra(int source, int target) {
		int[] distance = new int[20];
		int u = source;
		boolean found = false;
		for (int i = 0; i < distance.length; i++) {
			if (i == u) {
				distance[i] = 0;
			}
			else 
				distance[i] = 1000000; // counts as infinity
		}

		while (found == false) {
			int v = target;
			u = findMin(distance); // u gets updated to the new vertex with the smallest distance
			
			if (distance[u] == 1000000) {
				break;
			}
			int[] connected = neighbors(u); // array of neighbors in numerical order
			/*
			 * for loop creates a distance from u to each of its neighbors, then checks if this 
			 * distance is less than the already set distance and hasn't been gone through yet. 
			 * If both of these are true, it changes the distance of the neighbor to the new
			 * smaller distance, which is accumulated from the source. Then it sets u as 
			 * the previous vertex to the neighbor in the previous array. If the newly set neighbor
			 * is the target vertex, then the method closes and that distance is returned.
			 */
			for (int i = 0; i < connected.length ; i++) {
				int alternate = (distance[u] + getWeight(u, connected[i]));
				if (alternate < distance[connected[i]] && distance[connected[i]] != 9000000) {
					distance[connected[i]] = alternate;
					previous[connected[i]] = u;
					if (connected[i] == v) {
						distance[u] = 9000000;
						return distance[target];
					}
				}
			}
			distance[u] = 9000000; //distance of 9000000 means vertex has been gone through
		}
		return distance[target];
	}
	/*
	 * getPrev method simply returns the previous array.
	 */
	public int[] getPrev() {
		return previous;
	}

	/*
	 * addData method takes the city number, abbreviation, name, population, and elevation
	 * as parameters. Essentially all of the information for each city. Each variable 
	 * is placed into their respective arrays. 
	 */
	public void addData(int num, String inn, String name, int pop, int elev) {
		cityString[num][0] = inn;
		cityString[num][1] = name;
		cityInt[num][0] = pop;
		cityInt[num][1] = elev;
			
	}
	/*
	 * getNum method takes the abbreviation of the city as the parameter.
	 * Using for loops, it goes through the cityString array and uses
	 * the .equals method to find at which position the abbreviation lies. 
	 * This position is the city number and is returned.
	 */
	public int getNum(String label) {
		for(int i = 0; i < 20; i++) {
			if (label.equals(cityString[i][0])) {
					return i;
				}
		}
		return -1;
	}
	/*
	 * showDataString method takes the city number as a parameter. It creates a string
	 * array to hold the abbreviation and the name of the given city number. 
	 * Then returns the string array.
	 */
	public String[] showDataString(int row) {
		String[] dataArrayString = new String[2];
		dataArrayString[0] = cityString[row][0];
		dataArrayString[1] = cityString[row][1];
		return dataArrayString;
	}
	/*
	 * showDataSInt method takes the city number as a parameter. It creates a int
	 * array to hold the population and elevation of the given city number. 
	 * Then returns the int array.
	 */
	public int[] showDataInt(int row) {
		int[] dataArrayInt = new int[2];
		dataArrayInt[0] = cityInt[row][0];
		dataArrayInt[1] = cityInt[row][1];
		return dataArrayInt;
	}
	/*
	 * getInn method simply returns the abbreviation of the given city number
	 */
	public String getInn(int value) {
		return cityString[value][0];
	}
	/*
	 * getName method simply returns the name of the given city number.
	 */
	public String getName(int value) {
		return cityString[value][1];
	}
}



