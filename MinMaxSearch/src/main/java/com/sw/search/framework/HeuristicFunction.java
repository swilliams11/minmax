package com.sw.search.framework;

public interface HeuristicFunction<T> {
	//public double h(T state);
	public void updateHeuristic(T state);
}