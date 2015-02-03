package com.sw.search.framework;

import com.sw.game.Puzzle;
import com.sw.search.Node;

public class HeuristicTilesCorrect implements HeuristicFunction<Node<Puzzle>> {
	private Puzzle goalPuzzle;
	private String goal;
	
	public HeuristicTilesCorrect(Puzzle goal){
		this.goalPuzzle = goal;;
		this.goal = goal.getState();		
	}
	
	/*
	 * Returns the Hueristic value for two nodes.
	 * 
	 * @see com.sw.search.framework.HeuristicFunction#h(java.lang.Object, java.lang.Object)
	 */
	//@Override
	//@SuppressWarnings("unchecked")
	public void updateHeuristic(Node<Puzzle> state){
		int tilesIncorrect = 0;
		String [] currentState = state.getState().toString().split(",");
		String [] goalState = goal.split(",");
		for(int i = 0; i < goalState.length; i++){
			if(!goalState[i].equals(currentState[i]))
				tilesIncorrect += 1;			
		}
		state.setHueristic(tilesIncorrect);		
	}
}
