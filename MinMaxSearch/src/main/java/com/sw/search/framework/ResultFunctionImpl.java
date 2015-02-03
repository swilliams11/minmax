package com.sw.search.framework;

import com.sw.game.Action;
import com.sw.game.Puzzle;

public class ResultFunctionImpl implements ResultFunction<Puzzle> {

	//@Override
	public Puzzle result(Puzzle s, Action a) {
		return s.successor(a);
	}

}
