package djikstra;

import java.io.*;
import java.util.Scanner;
public class DijkstraDemo {


	public static void main(String[] args) throws IOException {
		Scanner kb = new Scanner(System.in);
		
		File myFile1 = new File("city.txt");
		File myFile2 = new File("road.txt");
		
		if (! myFile1.exists() || ! myFile2.exists()) {
			System.out.println("Input file not found.");
			System.exit(0);
		}
		
		Scanner inputFile1 = new Scanner(myFile1);
		Scanner inputFile2 = new Scanner(myFile2);
		
		Dijkstra graph1 = new Dijkstra(20); //create an object using class Graph
		/*
		 * this loop takes the data from file 2 which are edges and edge weights from u to v
		 * and adds edges to the graph using u, v, and the weight as arguments.
		 */
		while (inputFile2.hasNext()) {
			int a, b, c;
			a = inputFile2.nextInt();
			b = inputFile2.nextInt();
			c = inputFile2.nextInt();
			graph1.addEdge((a - 1), (b - 1), c);
		}
		/*
		 * this loop takes the data from file 1, which is all the information for each city
		 * and puts them into their according arrays in the Graph class. 
		 */
		while (inputFile1.hasNext()) {
			int a, d, e;
			String b, c;
			a = inputFile1.nextInt();
			b = inputFile1.next();
			/*
			 * each a value has a space in their name, so when using the .next() method, 
			 * which reads until the next space, you need
			 * to compensate with an extra space to add to c. 
			 */
			if (a == 4 || a == 5 || a == 6 || a == 7 || a == 9 || a == 11 || a == 12 || a == 13 || a == 14 || a == 16 || a == 17)
				c = (inputFile1.next()) + " " + (inputFile1.next());
			else
				c = inputFile1.next();
			d = inputFile1.nextInt();
			e = inputFile1.nextInt();
			graph1.addData((a - 1), b, c, d, e);

		}
		inputFile1.close();
		inputFile2.close();
		
		
		String choice = null;
		while (choice != "E") {
			System.out.print("Command? ");
			choice = kb.next();
			/*
			 * When Q is entered, find city number of entered abbreviation. Then 
			 * get the String and int data given the city number and add them to an array. 
			 * Print the information from the array. 
			 */
			if (choice.charAt(0) == 'Q') {
				System.out.print("City code: ");
				String input = kb.next();
				int vector = graph1.getNum(input);
				String[] infoWord = graph1.showDataString(vector);
				int[] infoInt = graph1.showDataInt(vector);
				System.out.println((vector + 1) + " " + infoWord[0] + " " + infoWord[1] + " " 
								+ infoInt[0] + " "  + infoInt[1]);
				
			}
			/*
			 * When D is entered, it takes the two abbreviations and finds the city number of each.
			 * Call upon the dijkstra method using the city numbers and you are returned with
			 * the shortest distance from source to target. The using the previous array and starting
			 * with the target, make your way back to the source, revealing the path taken. 
			 */
			else if (choice.charAt(0) == 'D') {
				System.out.print("City codes: ");
				String first = kb.next();
				String second = kb.next();
				
				int vector1 = graph1.getNum(first);
				int vector2 = graph1.getNum(second);
				
				
				int weight = graph1.dijkstra(vector1, vector2); // shortest distance
				int[] prev = graph1.getPrev(); // previous array
				int[] temp; 
				System.out.print("The minimum distance between " + graph1.getName(vector1) + " and "
						+ graph1.getName(vector2) + " is " + weight + " through the route: ");
				/*
				 * finding how many cities it takes to get from source to target = count
				 */
				int count = 1;
				int mid;
				int target = prev[vector2];
				count++;
				if (target != vector1) {
					mid = prev[target];
					count++;
					while (mid != vector1) {
						mid = prev[mid];
						count++;
					}
				} 	
				/*
				 * adding the cities from source to target into an array of size count
				 * starting from the target city. 
				 */
				temp = new int[count];
				count = 0;
				temp[count++] = vector2;
				mid = prev[vector2];
				temp[count++] = mid;
				while (mid != vector1) {
					mid = prev[mid];
					if (count == temp.length - 1)
						temp[count] = mid;
					else
						temp[count++] = mid;
				}
				/*
				 * print out the array in reverse order since the array
				 * starts with the target.
				 */
				for (int i = temp.length - 1; i >= 0; i--) {
					if (i == 0) {
						System.out.print(graph1.getInn(temp[i]));
					}
					else
						System.out.print(graph1.getInn(temp[i]) + ", ");
				}
				System.out.println(".");
			}
			/*
			 * When I is entered, take the two abbreviations and find the city numbers of each.
			 * Then add an edge to the adjacency matrix using the source, target, and weight as 
			 * arguments. If the edge already exists, do not add. If either of abbreviations
			 * that were entered is incorrect, do not add. 
			 */
			else if (choice.charAt(0) == 'I') {
				System.out.print("City codes and distance: ");
				String code1 = kb.next();
				String code2 = kb.next();
				int dist = kb.nextInt();
				if (graph1.getNum(code1) == -1) {
					System.out.println("The city code " + code1 + " does not exist.");
				}
				else if (graph1.getNum(code2) == -1) {
					System.out.println("The city code " + code2 + " does not exist.");
				}
				else if (graph1.isEdge(graph1.getNum(code1), graph1.getNum(code2)) == true) {
					System.out.println("The road from " + graph1.getName(graph1.getNum(code1)) + " to "
								+ graph1.getName(graph1.getNum(code2)) + " already exists.");
				}
				else {
					graph1.addEdge(graph1.getNum(code1), graph1.getNum(code2), dist);
					System.out.println("You have inserted a road from " + graph1.getName(graph1.getNum(code1)) + " to " 
							+ graph1.getName(graph1.getNum(code2)) + " with a distance of " + dist + ".");
				}
			}
			/*
			 * When R is entered, take the two abbreviations and find the city numbers of each.
			 * Then remove an edge from the adjacency matrix using the source and target as 
			 * arguments. If the edge does not exist do not remove. If either of abbreviations
			 * that were entered is incorrect, do not remove. 
			 */
			else if (choice.charAt(0) == 'R') {
				System.out.print("City codes: ");
				String code1 = kb.next();
				String code2 = kb.next();
				if (graph1.getNum(code1) == -1) {
					System.out.println("The city code " + code1 + " does not exist.");
				}
				else if (graph1.getNum(code2) == -1) {
					System.out.println("The city code " + code2 + " does not exist.");
				}
				else if (graph1.isEdge(graph1.getNum(code1), graph1.getNum(code2)) == false) {
					System.out.println("The road from " + graph1.getName(graph1.getNum(code1)) + " to "
								+ graph1.getName(graph1.getNum(code2)) + " does not exist.");
				}
				else {
					graph1.removeEdge(graph1.getNum(code1), graph1.getNum(code2));
					System.out.println("You have removed a road from " + graph1.getName(graph1.getNum(code1)) + " to " 
							+ graph1.getName(graph1.getNum(code2)) + " successfully.");
				}
			}
			else if (choice.charAt(0) == 'H') {
				System.out.println(" Q   Query the city information by entering the city code. \n D   Find the minimum distance between two cities. "
							+ "\n I   Insert a road by entering two city codes and distance.  \n R   Remove an existing road by entering two city codes."
							+ " \n H   Display this message. \n E   Exit the program");
			
			}
			else if (choice.charAt(0) == 'E') {
				System.out.println("Thank you for using!");
				System.exit(0);
			}
		}	
		
	}
}

