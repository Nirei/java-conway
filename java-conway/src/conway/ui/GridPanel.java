package conway.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import conway.board.Cell;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 420139992208411019L;
	private Set<Cell> cells;
	private int rows, columns;
	private Float widocol, heiorow;
	private JCheckBox showGrid;

	public GridPanel(int _rows, int _columns) {
		rows = _rows;
		columns = _columns;
		setDoubleBuffered(true);
		setBackground(Color.DARK_GRAY);
	}
	
	public float getRows() {
		return rows;
	}

	public float getColumns() {
		return columns;
	}
	
	public void setShowGrid(JCheckBox _showGrid) {
		showGrid = _showGrid;
	}
	
	public Cell getCellOnCoords(Integer _x, Integer _y) {
		int x = (int) (_x.floatValue()/widocol);
		int y = (int) (_y.floatValue()/heiorow);
		return new Cell(x,y);
	}
	
	
	public void setCells(Set<Cell> _cells) {
		widocol = ((float) getWidth()-1)/columns;
		heiorow = ((float) getHeight()-1)/rows;
		cells = _cells;
	}
	
	public Set<Cell> getCells() {
		return cells;
	}
	
	private void paintCells(Graphics2D g2, Set<Cell> cells) {			
		for(Cell c : cells) {
			Integer cellX = c.getX(), cellY = c.getY();
			if(cellX >= 0 && cellX < columns && cellY >= 0 && cellY < rows) {
				float	x = cellX.floatValue()*widocol,
						y = cellY.floatValue()*heiorow;
				g2.fill(new Ellipse2D.Float(x+1, y+1, widocol-2, heiorow-2));
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2;
		Line2D hLine;
		Line2D vLine;

		// width over cols and height over rows values for i -> x,y coord transforming
		widocol = ((float) getWidth()-1)/columns;
		heiorow = ((float) getHeight()-1)/rows;

		
		g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
		// Color living cells
		g2.setPaint(Color.WHITE);
		paintCells(g2, cells);
		
		g2.setPaint(new Color(0));
		
		// Draw grid
		if(showGrid.isSelected()) {
			for(float i=0; i<=rows; i++) {
				float y = i*heiorow;
				hLine = new Line2D.Float(0f, y, getWidth(), y);
				g2.draw(hLine);
			}
	
			for(float i=0; i<=columns; i++) {
				float x = i*widocol;
				vLine = new Line2D.Float(x, 0f, x, getHeight());
				g2.draw(vLine);
			}
		}
	}
	
}
