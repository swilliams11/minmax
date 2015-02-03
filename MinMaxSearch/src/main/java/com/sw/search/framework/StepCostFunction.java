package com.sw.search.framework;

import com.sw.game.Action;

public interface StepCostFunction<T> {
	/**
	 * Calculate the step cost of taking action a in state s to reach state s'.
	 * 
	 * @param s
	 *            the state from which action a is to be performed.
	 * @param a
	 *            the action to be taken.
	 * 
	 * @param sDelta
	 *            the state reached by taking the action.
	 * @return the cost of taking action a in state s to reach state s'.
	 */
	double c(T s, Action a, T sDelta);
	double c();
}
