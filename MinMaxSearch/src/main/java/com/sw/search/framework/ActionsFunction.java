package com.sw.search.framework;

import java.util.Set;
import com.sw.game.Action;

public interface ActionsFunction<T> {
	/**
	 * Given a particular state s, returns the set of actions that can be
	 * executed in s.
	 * 
	 * @param s
	 *            a particular state.
	 * @return the set of actions that can be executed in s.
	 */
	Set<Action> actions(T s);
}