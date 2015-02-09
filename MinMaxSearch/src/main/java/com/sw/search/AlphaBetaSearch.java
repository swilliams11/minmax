package com.sw.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.sw.search.framework.Metrics;

import com.sw.game.Action;
import com.sw.game.GameStatus;
import com.sw.game.Player;
import com.sw.game.Puzzle;
import com.sw.search.framework.HeuristicFunction;
import com.sw.search.framework.MinMaxHeuristic;

public class AlphaBetaSearch implements Search<Puzzle> {

	private Node<Puzzle> finalMaxNode;
	private GameStatus status;
	private HeuristicFunction<Node<Puzzle>> hf;
	private Comparator<Node> maxCmp, minCmp;
	private final int ply;
	private int previousDepth;
	private int currentDepth;
	private boolean goalStateReached = false;
	//value of the best (highest value) choice we found along path for max
	private int alpha = Integer.MIN_VALUE;
	//value of the best (lowest value) choice we found along path for min
	private int beta = Integer.MAX_VALUE;
	
	public AlphaBetaSearch(Comparator<Node> maxCmp, Comparator<Node> minCmp
			, int ply, HeuristicFunction<Node<Puzzle>> hf){
		this(maxCmp, minCmp, ply);
		this.hf = hf;
	}
	
	public AlphaBetaSearch(Comparator<Node> maxCmp, Comparator<Node> minCmp, int ply){
		//this(maxCmp, minCmp);
		this.maxCmp = maxCmp;
		this.minCmp = minCmp;
		this.ply = ply;
		hf = new MinMaxHeuristic();
	}
	
	public AlphaBetaSearch(Comparator<Node> maxCmp, Comparator<Node> minCmp){
		this();
		this.maxCmp = maxCmp;
		this.minCmp = minCmp;
	}
	
	public AlphaBetaSearch(){
		ply = 2;
		hf = new MinMaxHeuristic();
	}
	
	public Node<Puzzle> search(Node<Puzzle> n){
		Node<Puzzle> selection = n;
		
		while(!goalStateReached){
			selection = search2(selection);
		}
		return selection;
	}
	
	/*
	 * @param Node the initial board state
	 * 
	 * @return the Action that player should take.
	 * 
	 * (non-Javadoc)
	 * @see com.sw.search.Search#search(com.sw.search.Node)
	 */
	public Node<Puzzle> search2(Node<Puzzle> n) {
		Puzzle puzzle = n.getState();
		Set<Action> actions = puzzle.actions();
		int max = Integer.MIN_VALUE;
		Node<Puzzle> maxNode = null;
		Queue<Node<Puzzle>> frontier = new PriorityQueue<>(20, maxCmp);
		previousDepth = n.getDepth();
		
		//add to the priority queue
		for(Action a : actions){
			Puzzle puzzleSuccessor = puzzle.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			nSuccessor.setHueristic(hf.heuristic(nSuccessor));
			frontier.add(nSuccessor);
		}		
		
		while(!frontier.isEmpty()){
			Node<Puzzle> nSuccessor = frontier.poll(); //removes higher priority node
			//int utilityValue = minValue(nSuccessor);
			//int utilityValue = maxValue(nSuccessor, Integer.MIN_VALUE, Integer.MAX_VALUE);
			int utilityValue = maxValue(nSuccessor, alpha, beta);
			
			if(utilityValue > max){
				max = utilityValue;
				maxNode =  nSuccessor;			
			}			
		}

		//return finalMaxNode;
		if(goalStateReached)
			return finalMaxNode;
		else
			return maxNode;
	}
	
	/*
	 * return the utilty value for the current game state for MIN.
	 * 
	 * @param Node the node the contains the current game state
	 * 
	 * @return utility value
	 */
	public int minValue(Node<Puzzle> n, int alpha, int beta){
		Puzzle p = n.getState();
		if(p.isGoalState()){
			goalStateReached = true;
			return runGoalProcess(n); 
		}
		
		int min = Integer.MAX_VALUE;
		Set<Action> actions = p.actions();
		Queue<Node<Puzzle>> frontier = new PriorityQueue<>(20, maxCmp);
		
		//add to the priority queue
		for(Action a : actions){
			Puzzle puzzleSuccessor = p.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			nSuccessor.setHueristic(hf.heuristic(nSuccessor));
			frontier.add(nSuccessor);
		}
		
		while(!frontier.isEmpty()){
			Node<Puzzle> nSuccessor = frontier.poll(); //removes higher priority node
			if(isPlyLimit(previousDepth, nSuccessor.getDepth())){
				min = Math.min(min, hf.heuristic(nSuccessor));
			} else {
				min = Math.min(min, maxValue(nSuccessor, alpha, beta));
				if(min <= alpha)	return min;
				beta = Math.min(beta, min);
			}
		}		
		return min;
	}
	
	/*
	 * return the utilty value for the current game state for MAX.
	 * 
	 * @param Node the node the contains the current game state
	 * 
	 * @return utility value
	 */
	public int maxValue(Node<Puzzle> n, int alpha, int beta){
		Puzzle p = n.getState();
		if(p.isGoalState()){
			goalStateReached = true;
			return runGoalProcess(n);
		}
		
		int max = Integer.MIN_VALUE;
		Set<Action> actions = p.actions();
		Queue<Node<Puzzle>> frontier = new PriorityQueue<>(20, minCmp);
		
		//add to the priority queue
		for(Action a : actions){
			Puzzle puzzleSuccessor = p.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			nSuccessor.setHueristic(hf.heuristic(nSuccessor));
			frontier.add(nSuccessor);
		}
		
		//search the frontier
		while(!frontier.isEmpty()){
			Node<Puzzle> nSuccessor = frontier.poll(); //removes higher priority node
			if(isPlyLimit(previousDepth, nSuccessor.getDepth())){
				max = Math.max(max, hf.heuristic(nSuccessor));
			} else {
				max = Math.max(max, minValue(nSuccessor, alpha, beta));
				if(max >= beta)	return max;
				alpha = Math.max(alpha, max);
			}
		}
		return max;
	}
	
	/*
	 * Calculates the utility value and sets the game state
	 * for the node.
	 * @param node terminal state node.
	 * 
	 * @return utility value
	 */
	private int runGoalProcess(Node<Puzzle> n){
		finalMaxNode = n;
		int value = utilityValue(n);
		n.getState().setGameStatus(value);
		return value;
	}
	
	/*
	 * Returns the utilty value of the current goal state.
	 * This is only called when a terminal state is reached.
	 * 
	 *  @param Node the node for which we need to calculate the 
	 *  utility value
	 *  
	 *  @return utility value
	 */
	public int utilityValue(Node<Puzzle> n){
		Puzzle p = n.getState();
		//boolean goalState = p.isGoalState();
		Player player = p.getPlayer();
		if(player.equals(Player.MIN)){
			return -1;
		//} else if(goalState && player.equals(Player.MAX)){
		} else {
			return 1;
		}
	}
	
	public boolean isPlyLimit(int previous, int current){
		if(current - previous == ply)
			return true;
		else 
			return false;
	}
	
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}

}
