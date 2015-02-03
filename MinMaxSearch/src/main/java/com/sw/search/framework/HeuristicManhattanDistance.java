package com.sw.search.framework;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sw.game.Puzzle;
import com.sw.search.Node;

public class HeuristicManhattanDistance implements HeuristicFunction<Node<Puzzle>> {
	private Puzzle goalPuzzle;
	private String goal;
	private int [] distances = new int[9];
	/*
	 * Create a new instance passing in the goal state.
	 */
	public HeuristicManhattanDistance(Puzzle goal){
		this.goalPuzzle = goal;;
		this.goal = goal.getState();
		this.goal = this.goal.replace(",","");
	}
	
	/*
	 * Updates the heuristic value for the current node
	 * 
	 * @param state the node that contains the state for which we need to calculate the 
	 * heuristic.
	 */
	//@Override
	//@SuppressWarnings("unchecked")
	public void updateHeuristic(Node<Puzzle> state){
		String stateTemp = state.getState().toString();
		stateTemp = stateTemp.replace(",","");
		
		for(int i = 0; i < stateTemp.length(); i++){
			String s = Integer.toString(i);
			int x1 = getRow(stateTemp.indexOf(s));
			int x2 = getRow(goal.indexOf(s));
			int y1 = getColumn(stateTemp.indexOf(s));
			int y2 = getColumn(goal.indexOf(s)); 
			distances[i] = calcDistance(x1,x2,y1,y2);
		}		
		state.setHueristic(sumDistances());		
	}
	
	private int getRow(int index){
		int x = 0;
		switch(index){
		case 0: case 1: case 2:
			x = 0;
			break;
		case 3: case 4: case 5:
			x = 1;
			break;
		case 6: case 7: case 8:
			x =  2;
			break;
		}
		return x;
	}
	
	private int getColumn(int index){
		int x = 0;
		switch(index){
		case 0: case 1: case 2:
			x = index;
			break;
		case 3: case 4: case 5:
			x = index - 3;
			break;
		case 6: case 7: case 8:
			x =  index - 6;
			break;
		}
		return x;
	}
	
	private int calcDistance(int x1, int y1, int x2, int y2){
		int a = (int)Math.pow((x2 - x1),2);
		int b = (int)Math.pow((y2 - y1),2);
		int c = a + b;
		return (int)Math.sqrt(c);
	}
	
	private int sumDistances(){
		int sum = 0;
		for(int i = 0; i < distances.length; i++)
			sum += distances[i];
		return sum;
	}
}
