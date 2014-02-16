package conway.board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardSetList implements Board {

	private List<Set<Cell>> statesList = new ArrayList<Set<Cell>>();
	private Rules rules;
	private Integer calculatedSteps = 0;

	public BoardSetList() {
		statesList.add(new HashSet<Cell>());
	}
	
	@Override
	synchronized public void setCell(int step, Cell c, boolean b) {
		
		setCells(statesList.get(step));

		if(b) {
			statesList.get(0).add(c);
		} else {
			statesList.get(0).remove(c);
		}
	}

	@Override
	public boolean getCell(int step, Cell c) {
		return getCells(step).contains(c);
	}
	
	@Override
	public Set<Cell> getCells(int step) {
		
		while(step > calculatedSteps) {
			stepForward();
		}
		
		synchronized(statesList) {
			return new HashSet<Cell>(statesList.get(step));
		}
	}

	synchronized private void stepForward() {
		Set<Cell> nextCells;

		nextCells = rules.next(getCells(calculatedSteps));

		synchronized(statesList) {
			statesList.add(nextCells);
		}
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
		return "BoardSet [(init state)=" + statesList.get(0) + "]";
	}

	@Override
	public void toggleCell(int step, Cell cell) {
		setCell(step, cell, !getCell(step, cell));
	}
	
	@Override
	public void clear() {
		calculatedSteps = 0;
		statesList.clear();
		statesList.add(new HashSet<Cell>());
	}
	
	@Override
	public void setCells(Set<Cell> _cells) {
		// Clear states list
		statesList.clear();
		statesList.add(_cells);
		calculatedSteps = 0;
	}
}