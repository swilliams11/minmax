package com.sw.game;

public class ActionImpl implements Action {
	private int move1, move2;
	private String action;
	
	public ActionImpl(int move1, int move2){
		this.move1 = move1;
		this.move2 = move2;
		action = Integer.toString(move1) + " " + Integer.toString(move2);
	}
	
	public ActionImpl(String moves){
		String [] temp = moves.split(" ");
		move1 = Integer.parseInt(temp[0]);
		move2 = Integer.parseInt(temp[1]);
		action = moves;
	}

	public String action(){
		return action;
		
	}
	
	public String toString(){
		return action;
	}
}
