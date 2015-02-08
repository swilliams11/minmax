package com.sw.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DanceBattle implements Puzzle {
	private int numOfMoves;
	private int movesTaken;
	private boolean[][] danceMoves;
	private final Player player;
	private List<Action> priorMoves;
	//private Set<Action> priorMoves;
	private GameStatus status;
	
	public DanceBattle(int numOfMoves, int movesTaken, Player player) {
		this.numOfMoves = numOfMoves;
		this.movesTaken = movesTaken;
		danceMoves = new boolean[numOfMoves][numOfMoves];
		priorMoves = new LinkedList<Action>();
		//priorMoves = new HashSet<>();
		this.player = player;
	}

	public DanceBattle(int numOfMoves, int movesTaken, Player player,
			List<String> list) {
		this(numOfMoves, movesTaken, player);
		
		for (String move : list) {
			this.priorMoves.add(new ActionImpl(move));
			String[] nums = move.split(" ");
			danceMoves[Integer.parseInt(nums[0])][Integer.parseInt(nums[1])] = true;
			danceMoves[Integer.parseInt(nums[1])][Integer.parseInt(nums[0])] = true;
		}		
	}

	public DanceBattle(int numOfMoves, int movesTaken, Player player,
			boolean[][] state) {
		this(numOfMoves, movesTaken, player);
		for (int i = 0; i < state.length; i++) {
			for (int z = 0; z < state.length; z++) {
				danceMoves[i][z] = state[i][z];
			}
		}
	}

	public DanceBattle(int numOfMoves, int movesTaken, Player player,
			boolean[][] state, List<Action> moves) {
		this(numOfMoves, movesTaken, player, state);
		this.priorMoves = moves;
	}
	
	/*
	 * Check if the current state is the goal state.
	 * 
	 * (non-Javadoc)
	 * @see com.sw.search.framework.GoalTest#isGoalState(java.lang.Object)
	 */
	public boolean isGoalState() {
		Action lastMove = getLastMove();
		int secMove = getPlayersSecondMove(lastMove);
		for(int i = 0; i < numOfMoves; i++){
			if(!danceMoves[secMove][i])
				return false;
		}
		return true;
	}

	/*
	 * Return a copy of the game state.
	 * 
	 * @return Game state
	 * (non-Javadoc)
	 * @see com.sw.game.Puzzle#getState()
	 */
	public boolean[][] getState() {
		boolean[][] temp = new boolean[numOfMoves][numOfMoves];
		for (int i = 0; i < danceMoves.length; i++) {
			for (int z = 0; z < danceMoves.length; z++) {
				temp[i][z] = danceMoves[i][z];
			}
		}
		return temp;
	}

	/*
	 * Returns a new object with the state of the action.
	 * 
	 * @param Action the next move to be made
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.sw.game.Puzzle#successor(com.sw.game.Action)
	 */
	public Puzzle successor(Action a) {
		Player p = whoseTurn(player);
		Puzzle puzzle = new DanceBattle(this.numOfMoves, this.movesTaken + 1,
				p, getState(), new LinkedList<>(this.priorMoves));
		((DanceBattle) puzzle).bustAmove(a);
		return puzzle;
	}

	/*
	 * Determines who is the next player of the successor state.
	 * 
	 * @param player the current players turn.
	 */
	private Player whoseTurn(Player player) {
		switch (player) {
		case MIN:
			return Player.MAX;
		case MAX:
			return Player.MIN;
		}
		return player;
	}

	/*
	 * Adds the move to the game state.
	 * 
	 * @param Action the move the player made.
	 */
	private void bustAmove(Action a) {
		String s = a.action();
		this.priorMoves.add(a);
		String[] nums = s.split(" ");
		danceMoves[Integer.parseInt(nums[0])][Integer.parseInt(nums[1])] = true;
		danceMoves[Integer.parseInt(nums[1])][Integer.parseInt(nums[0])] = true;
	}

	/*
	 * This function sets the state of next player.
	 * 
	 * @param move the move the current completes.
	 */
	/*private List<Puzzle> createSuccessors(String move, Player p){
		List<Puzzle> list = new ArrayList<Puzzle>();
		legalMoves
		Puzzle successor = new DanceBattle(this.numOfMoves, this.movesTaken + 1, p, getState());
		
	}*/

	/*
	 * 
	 * 
	 * @return Actions the set of actions that can be taken from the current
	 * game state.
	 * (non-Javadoc)
	 * @see com.sw.game.Puzzle#actions()
	 */
	public Set<Action> actions() {
		Set<Action> actions = new HashSet<Action>();
		Action lastMove = getLastMove();
		int move = getPlayersSecondMove(lastMove);
		
		for(int i = 0; i < this.numOfMoves; i++){
			if(!this.danceMoves[move][i])
				actions.add(new ActionImpl(move, i));
		}
		return actions;		
	}
	
	/*
	 * Returns the last move that was taken. 
	 * 
	 * @return Action the last move that was taken
	 */
	private Action getLastMove(){
		int size = this.priorMoves.size();
		return this.priorMoves.get(size - 1);
	}
	
	private int getPlayersFirstMove(Action a){
		String [] moves = a.action().split(" ");
		return Integer.parseInt(moves[0]);
	}
	
	private int getPlayersSecondMove(Action a){
		String [] moves = a.action().split(" ");
		return Integer.parseInt(moves[1]);
	}
	

	/*
	 * @return Player the players move for the current game state.
	 */
	public Player getPlayer() {
		return player;
	}
	
	public List<Action> moveSequence(){
		return new LinkedList<>(priorMoves);
	}
	
	public GameStatus getGameStatus(){
		return status;
	}
	
	public void setGameStatus(int value){
		switch(value){
		case -1:
			this.status = GameStatus.LOSE;
			break;
		case 1:
			this.status = GameStatus.WIN;
			break;
		}
	}
	
	public int getMovesTaken(){
		return this.movesTaken;
	}
}
