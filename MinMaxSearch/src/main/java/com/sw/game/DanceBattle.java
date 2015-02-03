package com.sw.game;

import java.util.List;
import java.util.Set;

public class DanceBattle implements Puzzle{
	private int numOfMoves;
	private int movesTaken;
	private boolean [][] danceMoves;
	
	public DanceBattle(int numOfMoves, int movesTaken){
		danceMoves = new boolean [numOfMoves][numOfMoves];
	}
	
	public DanceBattle(int numOfMoves, int movesTaken, List<String> list){
		this(numOfMoves, movesTaken);
		for(String s : list){
			String [] nums = s.split(" ");
			danceMoves[Integer.parseInt(nums[0])][Integer.parseInt(nums[1])] = true;
			danceMoves[Integer.parseInt(nums[1])][Integer.parseInt(nums[0])] = true;
		}
	}

	public boolean isGoalState(Puzzle state) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean [][] getState() {
		boolean [][] temp = new boolean [numOfMoves][numOfMoves];
		for(int i = 0; i < danceMoves.length; i++){
			for( int z = 0; z < danceMoves.length; z++){
				temp[i][z] = danceMoves[i][z];
			}
		}
		return temp;
	}

	public Puzzle successor(Action a) {
		
		return null;
	}

	public Set<Action> actions() {
		
		return null;
	}
	
	
	
}
