package conway.board;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Rules {
	
	Map<Cell, Integer> table = new HashMap<Cell, Integer>();
	
	private void incrementCell(Cell c) {
		table.put(c, table.containsKey(c)?table.get(c)+1:1);
	}
	
	private void fillTable(Cell c) {
		int x = c.getX(), y = c.getY();
		
		// Upper row
		incrementCell(new Cell(x, y-1));
		incrementCell(new Cell(x-1, y-1));
		incrementCell(new Cell(x+1, y-1));
		
		// Middle row
		incrementCell(new Cell(x-1, y));
		incrementCell(new Cell(x+1, y));
		
		// Bottom row
		incrementCell(new Cell(x, y+1));
		incrementCell(new Cell(x-1, y+1));
		incrementCell(new Cell(x+1, y+1));

	}

	public Set<Cell> next(Set<Cell> current) {
		table.clear();

		for(Cell c : current) {
			fillTable(c);
		}
		
		Set<Cell> result = new HashSet<Cell>();
		for(Cell c : table.keySet()) {
			int neighbours = table.get(c);
			if(neighbours == 3) result.add(c);
			else if(neighbours == 2 && current.contains(c)) result.add(c);
		}
		return result;
	}
	
	/*
	public boolean next(int step, Board board, Cell c) {
		int sum = 0, x = c.getX(), y = c.getY();

		// Upper row
		sum += board.getCell(step, new Cell(x, y-1))?1:0;
		sum += board.getCell(step, new Cell(x-1, y-1))?1:0;
		sum += board.getCell(step, new Cell(x+1, y-1))?1:0;
		
		// Middle row
		sum += board.getCell(step, new Cell(x-1, y))?1:0;
		sum += board.getCell(step, new Cell(x+1, y))?1:0;
		
		// Bottom row
		sum += board.getCell(step, new Cell(x, y+1))?1:0;
		sum += board.getCell(step, new Cell(x-1, y+1))?1:0;
		sum += board.getCell(step, new Cell(x+1, y+1))?1:0;

		if(sum<2) return false;
		if(sum>3) return false;
		if(sum==3) return true;
		
		return board.getCell(step, c);
	}
	*/
}
