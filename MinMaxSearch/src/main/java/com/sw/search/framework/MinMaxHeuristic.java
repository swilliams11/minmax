package com.sw.search.framework;

import com.sw.game.Action;
import com.sw.game.DanceBattle;
import com.sw.game.Player;
import com.sw.game.Puzzle;
import com.sw.search.Node;

public class MinMaxHeuristic implements HeuristicFunction<Node<Puzzle>> {
	
	public int heuristic(Node<Puzzle> node){
		Puzzle puzzle = node.getState();
		//Player player = puzzle.getPlayer();
		int movesTaken = ((DanceBattle) puzzle).getMovesTaken();
		boolean [][] state = puzzle.getState();
		//move(state, action);
		int countRemainingMoves = countRemainingMoves(state);
		int value = calcHeuristic(countRemainingMoves, movesTaken);
		return value;
	}
	
	private int countRemainingMoves(boolean [][] state){
		int count = 0;
		for(int r = 0; r < state.length; r++){
			for (int c = 0; c < state[0].length; c++){
				if(!state[r][c])
					count++;
			}
		}
		return count;
	}
	
	/*private void move(boolean [][] state, Action action){
		String [] actions = action.action().split(" ");
		state[Integer.parseInt(actions[0])][Integer.parseInt(actions[1])] = true;
		state[Integer.parseInt(actions[1])][Integer.parseInt(actions[0])] = true;
	}*/
	
	private int calcHeuristic(int remainingMoves, int movesTaken){
		return remainingMoves - movesTaken;
	}
}
