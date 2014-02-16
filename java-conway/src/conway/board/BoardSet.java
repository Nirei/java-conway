package conway.board;

import java.util.HashSet;
import java.util.Set;

public class BoardSet implements Board {

	private Set<Cell> initialState = new HashSet<Cell>();
	private Set<Cell> currentState = new HashSet<Cell>();
	private Rules rules;
	private Integer calculatedSteps = 0;

	public BoardSet() {}
	
	@Override
	synchronized public void setCell(int step, Cell c, boolean b) {
		
		// Clear states list
		initialState = getCells(step);
		currentState = initialState;
		calculatedSteps = 0;

		if(b) {
			initialState.add(c);
		} else {
			initialState.remove(c);
		}
	}

	@Override
	public boolean getCell(int step, Cell c) {
		return getCells(step).contains(c);
	}
	
	@Override
	public Set<Cell> getCells(int step) {
		
		if(step == 0) return new HashSet<Cell>(initialState);
		
		while(step > calculatedSteps) {
			stepForward();
		}
		
		return new HashSet<Cell>(currentState);
	}

	synchronized private void stepForward() {
		Set<Cell> nextCells;

		nextCells = rules.next(getCells(calculatedSteps));

		currentState = nextCells;
		calculatedSteps++;
	}

	@Override
	public void setRules(Rules _rules) {
		rules = _rules;
	}
	
	@Override
	public Rules getRules() {
		return rules;
	}
	
	@Override
	public String toString() {
		return "BoardSet [(init state)=" + initialState + "]";
	}

	@Override
	public void toggleCell(int step, Cell cell) {
		setCell(step, cell, !getCell(step, cell));
	}
	
	@Override
	public void clear() {
		calculatedSteps = 0;
		initialState.clear();
		currentState = initialState;
	}

	@Override
	public void setCells(Set<Cell> _cells) {
		initialState = _cells;
		currentState = _cells;
		calculatedSteps = 0;
	}
}