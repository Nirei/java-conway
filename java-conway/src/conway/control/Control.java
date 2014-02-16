package conway.control;

import java.util.ArrayList;
import java.util.List;

import conway.board.Board;
import conway.board.BoardSet;
import conway.board.BoardSetList;
import conway.board.Cell;
import conway.board.Rules;
import conway.ui.GridPanel;
import conway.ui.MainWindow;

public class Control {

	private List<Thread> threads = new ArrayList<Thread>();
	private Board board;
	private GridPanel grid;
	private MainWindow window;
	private long msecondsPerFrame = 200;
	private int frame = 0;
	private int lastFrame = -1;
	private boolean playing;
	private boolean savingStates;
	
	private void refresh() {
		if(lastFrame != frame) {
			grid.setCells(board.getCells(frame));
			window.getStepCounter().setText(String.valueOf(frame));
			lastFrame = frame;
		}
		window.repaint();
	}
	
	private void animate() {
		playing = true;
		while(playing) {
			++frame;
			refresh();
			if(board.getCells(frame).size() == 0) {
				playing = false;
				window.setState(MainWindow.STATE_FINALIZED);
			}
			try {
				Thread.sleep(msecondsPerFrame);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	void play() {
		window.setState(MainWindow.STATE_PLAYING);
		Runnable r = new Runnable() {
			@Override public void run() { animate(); }
		};
		
		Thread thread = new Thread(r);
		threads.add(thread);
		thread.start();
	}
	
	void pause() {
		window.setState(MainWindow.STATE_PAUSED);
		playing = false;
		refresh();
	}
	
	void forward() {
		window.setState(MainWindow.STATE_ENABLED);
		++frame;
		playing = false;
		refresh();
	}
	
	void backward() {
		if(frame == 0) return;
		if(frame == 1) window.setState(MainWindow.STATE_CLEAR);
		--frame;
		playing = false;
		refresh();
	}
	
	void reset() {
		window.setState(MainWindow.STATE_CLEAR);
		frame = 0;
		playing = false;
		refresh();
	}
	
	void clear() {
		window.setState(MainWindow.STATE_CLEAR);
		reset();
		board.clear();
		lastFrame = -1;
		refresh();
	}
	
	void editGrid(int _x, int _y) {
		window.setState(MainWindow.STATE_CLEAR);
		board.toggleCell(frame, grid.getCellOnCoords(_x, _y));
		frame = 0;
		lastFrame = -1;
		playing = false;
		refresh();
	}
	
	private void init() {
		for(int i=0; i<grid.getColumns(); i++) {
			board.setCell(0, new Cell(i, 20), true);
		}
	}
	
	void optionChange() {
		//// Haiku I
		// Some option changes, 
		// this is called.
		// Speed and screen are refreshed.
		
		if(window.getSpeed() == 0) {
			pause();
		}
		else { 
			float speed = 100f - window.getSpeed().floatValue();
			msecondsPerFrame = ((long) (speed) * 5 + 1);
		}

		if(!playing) { refresh(); }
		
		if(savingStates != window.getSaveStates()) {
			initBoard();
			savingStates = window.getSaveStates();
		}
	}
	
	private void initBoard() {
		Board temp = board;
		if(window.getSaveStates()) {
			board = new BoardSetList();
		} else {
			board = new BoardSet();
		}
		
		if(temp != null) { board.setCells(temp.getCells(frame)); }
		else { init(); }
		
		board.setRules(new Rules());
		reset();
	}

	public Control() {
		window = new MainWindow();
		window.setUp(137, 70);
		grid = window.getGridPanel();
		ControlListener clis = new ControlListener(this);
		window.addUIListener(clis);
		initBoard();
		init();
		refresh();
	}
}
