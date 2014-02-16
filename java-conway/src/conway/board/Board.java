package conway.board;

import java.util.Set;

public interface Board {

	public abstract void setCell(int step, Cell c, boolean b);

	public abstract boolean getCell(int step, Cell c);
	
	public abstract void setCells(Set<Cell> cells);
	
	public abstract Set<Cell> getCells(int step);

	public Rules getRules();

	public void setRules(Rules _rules);

	public abstract void toggleCell(int step, Cell cell);

	public void clear();

}