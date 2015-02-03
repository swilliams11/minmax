package com.sw.search.framework;

import com.sw.search.Node;
import com.sw.search.framework.StepCostFunction;
import com.sw.game.Action;
import com.sw.game.Puzzle;

public class DefaultStepCostFunction implements StepCostFunction<Node<Puzzle>> {

	public double c(Node<Puzzle> stateFrom, Action action, Node<Puzzle> stateTo) {
		return 1;
	}
	public double c(){
		return 1;
	}
}