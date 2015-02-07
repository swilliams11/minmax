package com.sw.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DanceBattle implements Puzzle {
	private int numOfMoves;
	private int movesTaken;
	private boolean [][] danceMoves;
	private final Player player;
	
	public DanceBattle(int numOfMoves, int movesTaken, Player player){
		danceMoves = new boolean [numOfMoves][numOfMoves];
		this.player = player;
	}
	
	public DanceBattle(int numOfMoves, int movesTaken, Player player, List<String> list){
		this(numOfMoves, movesTaken, player);
		for(String s : list){
			String [] nums = s.split(" ");
			danceMoves[Integer.parseInt(nums[0])][Integer.parseInt(nums[1])] = true;
			danceMoves[Integer.parseInt(nums[1])][Integer.parseInt(nums[0])] = true;
		}
	}
	
	
	public DanceBattle(int numOfMoves, int movesTaken, Player player, boolean [][] state){
		this(numOfMoves, movesTaken, player);
		for(int i = 0; i < state.length; i++){
			for( int z = 0; z < state.length; z++){
				danceMoves[i][z] = state[i][z];
			}
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

	/*
	 * Returns a new object with the state of the action.
	 * 
	 * @param Action the next move to be made
	 * 
	 * (non-Javadoc)
	 * @see com.sw.game.Puzzle#successor(com.sw.game.Action)
	 */
	public List<Puzzle> successor(Action a) {
		String move = a.action();
		Player p = whoseTurn(player);
		List<Puzzle> l = createSuccessors(move, p);
		return null;
	}
	
	/*
	 * Determines who is the next player of the successor state.
	 * 
	 * @param player the current players turn.
	 */
	private Player whoseTurn(Player player){
		switch(player){
		case MIN:
			return Player.MAX;
		case MAX:
			return Player.MIN;
		}
		return player;
	}
	
	
	/*
	 * This function sets the state of next player.
	 * 
	 * @param move the move the current completes.  
	 */
	private List<Puzzle> createSuccessors(String move, Player p){
		List<Puzzle> list = new ArrayList<Puzzle>();
		legalMoves
		Puzzle successor = new DanceBattle(this.numOfMoves, this.movesTaken + 1, p, getState());
		
	}
	
	private boolean
	

	public Set<Action> actions() {
		
		return null;
	}	
	
	public String getPlayer(){
		return player;
	}
}
