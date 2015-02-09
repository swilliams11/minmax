package com.sw.search;

import java.util.List;
import com.sw.search.framework.Metrics;
import com.sw.game.Action;

public interface Search<T> {
	public Node<T> search(Node<T> n);	
	public Metrics getMetrics();
}
