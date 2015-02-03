package com.sw.search.framework;

import com.sw.search.Node;
import com.sw.search.framework.ActionsFunction;
import com.sw.search.framework.ResultFunction;
import com.sw.search.framework.DefaultStepCostFunction;
import com.sw.search.framework.GoalTest;
import com.sw.search.framework.StepCostFunction;
import com.sw.game.Puzzle;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 66.<br>
 * <br>
 * A problem can be defined formally by five components: <br>
 * <ul>
 * <li>The <b>initial state</b> that the agent starts in.</li>
 * <li>A description of the possible <b>actions</b> available to the agent.
 * Given a particular state s, ACTIONS(s) returns the set of actions that can be
 * executed in s.</li>
 * <li>A description of what each action does; the formal name for this is the
 * <b>transition model, specified by a function RESULT(s, a) that returns the
 * state that results from doing action a in state s.</b></li>
 * <li>The <b>goal test</b>, which determines whether a given state is a goal
 * state.</li>
 * <li>A <b>path cost</b> function that assigns a numeric cost to each path. The
 * problem-solving agent chooses a cost function that reflects its own
 * performance measure. The <b>step cost</b> of taking action a in state s to
 * reach state s' is denoted by c(s,a,s')</li>
 * </ul>
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 * @author Mike Stampone
 */
public class Problem<T> {

	protected T initialState;

	protected ActionsFunction<T> actionsFunction;

	protected ResultFunction<T> resultFunction;

	protected GoalTest<T> goalTest;

	protected StepCostFunction stepCostFunction;
	
	protected HeuristicFunction hueristicFunction;

	/**
	 * Constructs a problem with the specified components, and a default step
	 * cost function (i.e. 1 per step).
	 * 
	 * @param initialState
	 *            the initial state that the agent starts in.
	 * @param actionsFunction
	 *            a description of the possible actions available to the agent.
	 * @param resultFunction
	 *            a description of what each action does; the formal name for
	 *            this is the transition model, specified by a function
	 *            RESULT(s, a) that returns the state that results from doing
	 *            action a in state s.
	 * @param goalTest
	 *            test determines whether a given state is a goal state.
	 */
	public Problem(T initialState, ActionsFunction<T> actionsFunction,
			ResultFunction<T> resultFunction, GoalTest<T> goalTest) {
		this(initialState, actionsFunction, resultFunction, goalTest,
				new DefaultStepCostFunction());
	}
	
	/**
	 * Constructs a problem with the specified components, and a heuristic
	 * function.
	 * 
	 * @param initialState
	 *            the initial state that the agent starts in.
	 * @param actionsFunction
	 *            a description of the possible actions available to the agent.
	 * @param resultFunction
	 *            a description of what each action does; the formal name for
	 *            this is the transition model, specified by a function
	 *            RESULT(s, a) that returns the state that results from doing
	 *            action a in state s.
	 * @param goalTest
	 *            test determines whether a given state is a goal state.
	 * @param heuristic
	 * 			  test that determines the hueristic amount.
	 */
	public Problem(T initialState, ActionsFunction<T> actionsFunction,
			ResultFunction<T> resultFunction, GoalTest<T> goalTest, HeuristicFunction h) {
		
		this(initialState, actionsFunction, resultFunction, goalTest,
				new DefaultStepCostFunction());
		this.hueristicFunction = h;
	}

	/**
	 * Constructs a problem with the specified components, which includes a step
	 * cost function.
	 * 
	 * @param initialState
	 *            the initial state of the agent.
	 * @param actionsFunction
	 *            a description of the possible actions available to the agent.
	 * @param resultFunction
	 *            a description of what each action does; the formal name for
	 *            this is the transition model, specified by a function
	 *            RESULT(s, a) that returns the state that results from doing
	 *            action a in state s.
	 * @param goalTest
	 *            test determines whether a given state is a goal state.
	 * @param stepCostFunction
	 *            a path cost function that assigns a numeric cost to each path.
	 *            The problem-solving-agent chooses a cost function that
	 *            reflects its own performance measure.
	 */
	public Problem(T initialState, ActionsFunction<T> actionsFunction,
			ResultFunction<T> resultFunction, GoalTest<T> goalTest,
			StepCostFunction stepCostFunction) {
		this.initialState = initialState;
		this.actionsFunction = actionsFunction;
		this.resultFunction = resultFunction;
		this.goalTest = goalTest;
		this.stepCostFunction = stepCostFunction;
	}

	/**
	 * Returns the initial state of the agent.
	 * 
	 * @return the initial state of the agent.
	 */
	public T getInitialState() {
		return initialState;
	}

	/**
	 * Returns <code>true</code> if the given state is a goal state.
	 * 
	 * @return <code>true</code> if the given state is a goal state.
	 */
	public boolean isGoalState(T state) {
		return goalTest.isGoalState(state);
	}
	
	public void updateHeuristic(T state){
		hueristicFunction.updateHeuristic(state);
	}

	/**
	 * Returns the goal test.
	 * 
	 * @return the goal test.
	 */
	public GoalTest<T> getGoalTest() {
		return goalTest;
	}

	/**
	 * Returns the description of the possible actions available to the agent.
	 * 
	 * @return the description of the possible actions available to the agent.
	 */
	public ActionsFunction<T> getActionsFunction() {
		return actionsFunction;
	}

	/**
	 * Returns the description of what each action does.
	 * 
	 * @return the description of what each action does.
	 */
	public ResultFunction<T> getResultFunction() {
		return resultFunction;
	}

	/**
	 * Returns the path cost function.
	 * 
	 * @return the path cost function.
	 */
	public StepCostFunction<Node> getStepCostFunction() {
		return stepCostFunction;
	}
	
	public HeuristicFunction<Node> getHeuristicFunction(){
		return this.hueristicFunction;
	}

	//
	// PROTECTED METHODS
	//
	protected Problem() {
	}
}