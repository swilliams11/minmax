package com.sw.game;

import java.util.List;
import java.util.Set;

import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.ResultFunction;
import com.sw.search.framework.GoalTest;

public interface Puzzle extends GoalTest {

	public boolean [][] getState();
	public Puzzle successor(Action a);
	public Set<Action> actions();
	public Player getPlayer();
	public List<Action> moveSequence();
	public GameStatus getGameStatus();
	public void setGameStatus(int value);
}
