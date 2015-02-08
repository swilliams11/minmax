package com.sw.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import aima.core.search.framework.Metrics;

import com.sw.game.Action;
import com.sw.game.GameStatus;
import com.sw.game.Player;
import com.sw.game.Puzzle;

public class MinMaxSearch implements Search<Puzzle> {

	private Node<Puzzle> finalMaxNode;
	private GameStatus status;
	
	public MinMaxSearch(){	
	}		
	
	/*
	 * @param Node the initial board state
	 * 
	 * @return the Action that player should take.
	 * 
	 * (non-Javadoc)
	 * @see com.sw.search.Search#search(com.sw.search.Node)
	 */
	public Node<Puzzle> search(Node<Puzzle> n) {
		Puzzle puzzle = n.getState();
		Set<Action> actions = puzzle.actions();
		int max = Integer.MIN_VALUE;
		Node<Puzzle> maxNode = null;
		Queue<Node<Puzzle>> frontier = new PriorityQueue<>(20, new Comparator<Node>(){
			@Override
			public int compare(Node o1, Node o2) {
				return (int)(o1.getHueristic() - o2.getHueristic());
			}			
		});
		
		
		for(Action a : actions){
			Puzzle puzzleSuccessor = puzzle.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			int utilityValue = minValue(nSuccessor);
			if(utilityValue > max){
				max = utilityValue;
				maxNode =  nSuccessor;			
			}
			//max = (r > max)? r : max;			
		}
		return finalMaxNode;
		//return maxNode;
	}
	
	/*
	 * return the utilty value for the current game state for MIN.
	 * 
	 * @param Node the node the contains the current game state
	 * 
	 * @return utility value
	 */
	public int minValue(Node<Puzzle> n){
		Puzzle p = n.getState();
		if(p.isGoalState()){
			return runGoalProcess(n); 
		}
		
		int max = Integer.MIN_VALUE;
		Set<Action> actions = p.actions();
		for (Action a : actions){
			Puzzle puzzleSuccessor = p.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			max = Math.max(max, maxValue(nSuccessor));
		}
		return max;
	}
	
	/*
	 * return the utilty value for the current game state for MAX.
	 * 
	 * @param Node the node the contains the current game state
	 * 
	 * @return utility value
	 */
	public int maxValue(Node<Puzzle> n){
		Puzzle p = n.getState();
		if(p.isGoalState()){
			return runGoalProcess(n);
		}
		
		int min = Integer.MAX_VALUE;
		Set<Action> actions = p.actions();
		for (Action a : actions){
			Puzzle puzzleSuccessor = p.successor(a);
			Node<Puzzle> nSuccessor = new Node<Puzzle>(puzzleSuccessor, n, a, 1.0);
			min = Math.min(min, minValue(nSuccessor));
		}
		return min;
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
	
	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}

}
