package com.sw.search;

import java.util.List;

import aima.core.search.framework.Metrics;

import com.sw.game.Action;

public class MinMaxSearch implements Search {

	public MinMaxSearch(){	
	}
		
	public Action search(Node n) {
		List<Node> nodes;
		int max = Integer.MIN_VALUE;
		for(Node node : nodes){
			int r = n.getRank();
			max = (r > max)? r : max;
			return node.getAction();
		}
		return node.getAction();
	}

	public Metrics getMetrics() {
		// TODO Auto-generated method stub
		return null;
	}

}
