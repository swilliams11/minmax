package com.sw.search.framework;

import com.sw.game.Puzzle;
import com.sw.search.framework.GoalTest;

public class GoalTestImpl implements GoalTest<Puzzle> {

	//@Override
	public boolean isGoalState(Puzzle state) {
		return false;
	}
}
