package com.sw.search.framework;

import com.sw.game.Action;

public interface HeuristicFunction<T> {
	//public double h(T state);
	public int heuristic(T state, Action action);
}