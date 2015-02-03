package com.sw.game;


import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;
import aima.core.search.framework.Metrics;
import com.sw.game.Puzzle;
import com.sw.search.Node;
import com.sw.search.Search;
import com.sw.search.framework.ActionFunctionImpl;
import com.sw.search.framework.GoalTest;
import com.sw.search.framework.HeuristicManhattanDistance;
import com.sw.search.framework.HeuristicTilesCorrect;
import com.sw.search.framework.Problem;
import com.sw.search.framework.ResultFunctionImpl;

public class Run {
	static Metrics metrics;
	static Logger log = Logger.getLogger("Log"); 
	static Puzzle puzzle = new EightPuzzle("1,2,3,4,0,5,6,7,8");
	static Puzzle puzzle2 = new EightPuzzle("1,2,0,3,4,5,6,7,8");
	static Puzzle easy = new EightPuzzle("1,3,4,8,6,2,7,0,5");// prof. easy
	static Puzzle medium = new EightPuzzle("2,8,1,0,4,3,7,6,5");
	static Puzzle hard = new EightPuzzle("5,6,7,4,0,8,3,2,1");
	static GoalTest<Puzzle> goal = new EightPuzzle("1,2,3,4,5,6,7,8,0");
	static GoalTest<Puzzle> goal2 = new EightPuzzle("1,2,3,8,0,4,7,6,5");// prof.														// goal
	static List<Node> solution = null;
	static final String EASY = "Easy";
	static final String MEDIUM = "Medium";
	static final String HARD = "Hard";
	static final String CUSTOM = "Custom";
	
	
	public static void main(String[] args) throws Exception {
		boolean result = true;
		while(result){
			result = Run.run();
		}
	}
	
	public static boolean run(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the number you would like to run.");
		System.out.println("1 - easy problem\n2 - medium problem\n3 - hard problem\n4 - enter new problem");
		String response = in.nextLine();
		int problem = Integer.parseInt(response);
		String selection = null;
		
		switch(problem){
		case 1:
			puzzle = easy;
			selection = EASY;
			break;
		case 2:
			puzzle = medium;
			selection = MEDIUM;
			break;
		case 3: 
			puzzle = hard;
			selection = HARD;
			break;
		case 4:
			selection = CUSTOM;
			System.out.println("Please enter puzzle separated by spaces (e.g. 1 2 3 5 4 6 7 8 0");
			String puzzleString = in.nextLine();
			puzzle = new EightPuzzle(puzzleString);
		}
		
		System.out.println("Select the search method.");
		System.out.println("1 - breath first search\n2 - depth firsth search"
				+"\n3 - iterative deepening\n4 - Greedy Best First Search"
				+"\n5 - A* Number of tiles incorrect\n6 - A* Manhattan Distance");
		int searchMethod  = Integer.parseInt(in.nextLine());
		int depthLimit = -1;
		String searchSelection = null;
		//comparator that uses f(n) = g(n) + h(n) to order the priority queue
		Comparator<Node> cmp = new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				int fo1 = (int)(o1.getPathCost() + o1.getHueristic());
				int fo2 = (int) (o2.getPathCost() + o2.getHueristic());
				int f = fo1 - fo2; 
				return f;
			}			
		};
		
		switch(searchMethod){
		case 1:
			searchSelection = "BFS";
			solution = Run.searchBFS(puzzle, goal2);
			 break;
		case 2:
			searchSelection = "DFS";
			solution = Run.searchDFS(puzzle, goal2);
			 break;		
		}
		
		Run.printResults(searchSelection + "-" + selection
				+ ((depthLimit >= 0)? " - Depth Limit is " + depthLimit : " - Depth Limit is not applicable")
				, puzzle, (Puzzle)goal2, solution);
		
		System.out.println("\n\nWould you like to run another puzzle? Enter y or n:");
		boolean continue1 = Run.checkValue(in.nextLine());
		//in.close();
		return continue1;
		
	}
	
	public static boolean checkValue(String result){
		if(result.equals("y")) return true;
		else return false;
	}

	/*
	 * Searches the problem for a solution based using Breadth First Search.
	 * 
	 * @param puzzle initial state
	 * 
	 * @param goal goal state
	 * 
	 * @return solution
	 */
	// @SuppressWarnings("unchecked")
	public static List<Node> searchBFS(Puzzle puzzle, GoalTest<Puzzle> goal) {
		Problem<Puzzle> problem = new Problem<>(puzzle,
				new ActionFunctionImpl(), new ResultFunctionImpl(), goal);
		Search<Node, Puzzle> search = new BFS();
		List<Node> solution = search.search(problem);
		metrics = search.getMetrics();
		if (solution == null) {
			System.out.println("Solution cannot be found!");
			log.debug("Solution cannot be found.");
		}
		return solution;
	}

	
	
	
	
	/*
	 * Prints the header, sequence of moves, statistics and states.
	 * 
	 * @param puzzle the initial state
	 * 
	 * @param goal the goal state
	 * 
	 * @param solution the solution
	 */
	public static void printResults(String searchType, Puzzle puzzle,
			Puzzle goal, List<Node> solution) {
		System.out.println("\n*****************************");
		log.debug("\n*****************************");
		System.out.println(searchType);
		log.debug(searchType);
		Run.printHeader(puzzle, goal);
		Run.printStats();
		Run.printMoveSequence(solution);
		//Run.printStates(solution);
	}

	/*
	 * Prints the states in the solution.
	 * 
	 * @param solution the solution
	 */
	public static void printStates(List<Node> solution) {
		System.out.println("\nPATH:");
		log.debug("\nPATH:");
		if (solution != null) {
			Node<Puzzle> node = solution.get(0);
			Puzzle p = node.getState();
			for (Node<Puzzle> myp : solution) {
				System.out.println(myp.getState());
				log.debug(myp.getState());
			}
		} else {
			System.out.println("No paths!");
			log.debug("No Paths!");
		}
	}

	/*
	 * Prints the initial and goal states.
	 * 
	 * @param init the initial state
	 * 
	 * @param goal the goal state
	 */
	public static void printHeader(Puzzle init, Puzzle goal) {
		System.out.println("\nInitial State: " + init.getState());
		System.out.println("Goal State: " + goal.getState());
		log.debug("\nInitial State: " + init.getState());
		log.debug("Goal State: " + goal.getState());
	}

	/*
	 * Print the sequence of moves to arrive at the solution.
	 * 
	 * @param solution the solution
	 */
	public static void printMoveSequence(List<Node> solution) {
		System.out.println("\nSequence of moves to solution:");
		log.debug("\nSequence of moves to solution:");
		if (solution == null) {
			System.out.println("No Solution");
			log.debug("No Solution");
		} else {
			StringBuilder out = new StringBuilder();
			for (Node<Puzzle> n : solution) {
				if (n.getAction() != null) {
					out.append(n.getAction());
					out.append(",");
				}
			}
			System.out.print(out);
			log.debug(out);
		}
	}

	/*
	 * Print the statistics generated by the search.
	 */
	public static void printStats() {
		System.out.println("\n\nMetrics:");
		if (metrics != null) {
			for (String s : metrics.keySet()) {
				System.out.println(s + ": " + metrics.get(s));
				log.debug(s + ": " + metrics.get(s));
			}
		} else {
			System.out.println("No metrics!");
			log.debug("No metrics");
		}

	}
}
