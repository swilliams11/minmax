package com.sw.game;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.sw.search.framework.Metrics;

import com.sw.game.Puzzle;
import com.sw.search.AlphaBetaSearch;
import com.sw.search.MinMaxSearch;
import com.sw.search.Node;
import com.sw.search.Search;

public class Run {
	static Metrics metrics;
	static Logger log = Logger.getLogger("Log");
	static List<Node> solution = null;
	static List<String> init = new ArrayList<>();
	static int numOfMoves;
	static int numOfMovesTaken;
	static Player player;
	
	public static void main(String[] args) throws Exception {
		boolean result = true;
		while(result){
			result = Run.run();
		}
	}
	
	
	/*
	 * Run run the process.
	 * 
	 * @return true if user wants to examine another game or false otherwise.
	 */
	public static boolean run(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the file name that contains the initial board state.");
		System.out.println("e.g. testcase1.txt\n");
		
		//System.out.println("1 - easy problem\n2 - medium problem\n3 - hard problem\n4 - enter new problem");
		String response = in.nextLine();
		init = readFile(response);
		player = determinePlayer();		
		
		Puzzle p = null;
			
		p = new DanceBattle(Run.numOfMoves, Run.numOfMovesTaken, player, init);
		
		Node<Puzzle> n = new Node<>(p);
		//Create the min and max comparators
		Comparator<Node> maxCmp = new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return (int)(o1.getHueristic() - o2.getHueristic());
			}			
		};
		
		Comparator<Node> minCmp = new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return (int)(o2.getHueristic() - o1.getHueristic());
			}			
		};
		
		System.out.println("Enter ply level [e.g. a number from 1 to 10:");
		response = in.nextLine();
		System.out.println("Enter search algorithm [1 = Min Max; 2 = Min Max Alpha Beta]:");
		String alg = in.nextLine();
		
		Search<Puzzle> search = null;
		//select the ply level
		int ply = 0;
		if(validResponse(response)){
			ply = Integer.parseInt(response);
		} else {
			ply = 2;
		}	
		
		//select the the algorithm 
		switch (Integer.parseInt(alg)){
		case 1:
			search = new MinMaxSearch(maxCmp,minCmp,ply);
			break;
		case 2:
			search = new AlphaBetaSearch(maxCmp,minCmp,ply);
		}
				
		
		Node<Puzzle> node = search.search(n);
		Puzzle finalState = node.getState();
		List<Action> danceSequence = finalState.moveSequence();
		printHeader();
		printMoves(danceSequence);
		System.out.println("You " + finalState.getGameStatus());
		//Run.printResults();		
		System.out.println("\n\nWould you like to run another puzzle? Enter y or n:");
		boolean continue1 = Run.checkValue(in.nextLine());
		//in.close();
		return continue1;		
	}
	
	/*
	 * Read the file.
	 * 
	 * @param fileName name of the file to read
	 */
	public static List<String> readFile(String fileName){
		Path file = Paths.get(fileName);
		//Path temp = Paths.get("");
		//System.out.println(temp.toAbsolutePath().toString());
		List<String> list = new ArrayList<>();
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line = null;
		    int count = 0;
		    while ((line = reader.readLine()) != null) {
		        count++;
		        switch(count){
		        case 1:
		        	Run.numOfMoves = Integer.parseInt(line);
		        	break;
		        case 2:
		        	Run.numOfMovesTaken = Integer.parseInt(line);
		        	break;
		        default:
		        	list.add(line);
		        }		    	
		    }		    
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		return list;
	}
	
	/*
	 * 
	 * @return Player the last player to move.
	 */
	public static Player determinePlayer(){
		if(init.size() % 2 == 0){
			return Player.MIN;
		} else {
			return Player.MAX;
		}
	}
	
	public static boolean validResponse(String response){
		int value = Integer.parseInt(response);
		if (value > 1 && value <= 10){
			return true;
		}
		return false;
	}
	
	/*
	 * Print the game header.
	 */
	public static void printHeader(){
		System.out.println("##############Dance Battle##############");
		System.out.println("Number of Moves: " + Run.numOfMoves);
		System.out.println("Number of Moves Taken: " + Run.numOfMovesTaken);
		System.out.println("Last Player to Move: " + player);
		System.out.println("Initial board state:");
		for(String move : init){
			System.out.println(move);
		}		
	}
	
	/*
	 * Print the dance sequence.
	 * 
	 * @param actions list of actions for the game.
	 */
	public static void printMoves(List<Action> actions){
		System.out.println("############Dance Sequence###########");
		for( Action a : actions){
			System.out.println(a);
		}
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
	/*public static List<Node> search(Puzzle puzzle, GoalTest<Puzzle> goal) {
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
	}*/

	
	
	
	
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
