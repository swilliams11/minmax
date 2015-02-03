package com.sw.search.framework;

import com.sw.game.Action;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 67.<br>
 * <br>
 * A description of what each action does; the formal name for this is the
 * transition model, specified by a function RESULT(s, a) that returns the state
 * that results from doing action a in state s. We also use the term successor
 * to refer to any state reachable from a given state by a single action.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public interface ResultFunction<T> {
	/**
	 * Returns the state that results from doing action a in state s
	 * 
	 * @param s
	 *            a particular state.
	 * @param a
	 *            an action to be performed in state s.
	 * @return the state that results from doing action a in state s.
	 */
	T result(T s, Action a);
}