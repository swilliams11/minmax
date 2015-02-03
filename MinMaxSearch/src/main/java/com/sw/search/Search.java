package com.sw.search;

import java.util.List;
import aima.core.search.framework.Metrics;
import com.sw.game.Action;

public interface Search {
	public Action search(Node n);	
	public Metrics getMetrics();
}
