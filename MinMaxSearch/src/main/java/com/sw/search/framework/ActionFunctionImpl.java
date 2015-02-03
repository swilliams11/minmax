package com.sw.search.framework;

import java.util.Set;

import com.sw.game.Action;
import com.sw.game.Puzzle;

public class ActionFunctionImpl implements ActionsFunction<Puzzle> {
	public Set<Action> actions(Puzzle s){
		return s.actions();
	}
}
