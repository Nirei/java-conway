package conway.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import static conway.ui.ButtonActionCommands.*;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 6585154018416553491L;
	public final static short STATE_CLEAR = 0;
	public final static short STATE_PAUSED = 1;
	public final static short STATE_PLAYING = 2;
	public final static short STATE_ENABLED = 3;
	public final static short STATE_FINALIZED = 4;

	private GridPanel gridPanel;
	private JPanel menuPanel;
	private JButton playButton;
	private JButton pauseButton;
	private JButton restartButton;
	private JButton forwardButton;
	private JButton backwardButton;
	private JSlider speedSlider;
	private JLabel stepCounter;

	private JButton stopButton;
	private JCheckBox showGridBox;
	private JCheckBox saveStatesBox;
	
	public void setUp(int _columns, int _rows) {
		
		BoxLayout blayout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);

		setLayout(blayout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(720, 460));
		setTitle("Life of Conway");
		
	    // Center window
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dim.getWidth() - getWidth())/2);
	    int y = (int) ((dim.getHeight() - getHeight())/2);
	    setLocation(x, y);
	    
		setResizable(true);
		// setBackground(Color.WHITE);
		
		// Component setup
		add(gridPanel = gridSetUp(_rows, _columns));
		add(menuPanel = menuPanelSetUp());
		menuPanel.add(buttonSetUp());
		menuPanel.add(optionPanelSetUp());
		menuPanel.add(speedSliderSetUp());
		
		// Step counter
		stepCounter = new JLabel();
		add(stepCounter);
		
		setVisible(true);
	}
	
	private GridPanel gridSetUp(int _rows, int _columns) {
		GridPanel _gridPanel = new GridPanel(_rows, _columns);
		_gridPanel.setMinimumSize(new Dimension(720, 360));
		return _gridPanel;
	}
	
	private JPanel menuPanelSetUp() {
		JPanel _menuPanel = new JPanel();
		FlowLayout flayout = new FlowLayout();
		_menuPanel.setLayout(flayout);
		_menuPanel.setMaximumSize(new Dimension(720, 100));
		return _menuPanel;
	}
	
	private JPanel buttonSetUp() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// For eclipse
//		ImageIcon playIcon = new ImageIcon("src/conway/images/play-button.png");
//		ImageIcon pauseIcon = new ImageIcon("src/conway/images/pause-button.png");
//		ImageIcon restartIcon = new ImageIcon("src/conway/images/restart-button.png");
//		ImageIcon forwardIcon = new ImageIcon("src/conway/images/forward-button.png");
//		ImageIcon backwardIcon = new ImageIcon("src/conway/images/back-button.png");
//		ImageIcon stopIcon = new ImageIcon("src/conway/images/stop-button.png");
	
		// For compilation
		ImageIcon playIcon = new ImageIcon(getClass().getResource("/conway/images/play-button.png"));
		ImageIcon pauseIcon = new ImageIcon(getClass().getResource("/conway/images/pause-button.png"));
		ImageIcon restartIcon = new ImageIcon(getClass().getResource("/conway/images/restart-button.png"));
		ImageIcon forwardIcon = new ImageIcon(getClass().getResource("/conway/images/forward-button.png"));
		ImageIcon backwardIcon = new ImageIcon(getClass().getResource("/conway/images/back-button.png"));
		ImageIcon stopIcon = new ImageIcon(getClass().getResource("/conway/images/stop-button.png"));

		
		playButton = new JButton(playIcon);
		pauseButton = new JButton(pauseIcon);
		restartButton = new JButton(restartIcon);
		forwardButton = new JButton(forwardIcon);
		backwardButton = new JButton(backwardIcon);
		stopButton = new JButton(stopIcon);

		playButton.setActionCommand(PLAY_AC);
		pauseButton.setActionCommand(PAUSE_AC);
		restartButton.setActionCommand(RESTART_AC);
		forwardButton.setActionCommand(FORWARD_AC);
		backwardButton.setActionCommand(BACKWARD_AC);
		stopButton.setActionCommand(CLEAR_AC);
		
		pauseButton.setVisible(false);
		
		playButton.setToolTipText("Start");
		pauseButton.setToolTipText("Pause");
		restartButton.setToolTipText("Reset");
		forwardButton.setToolTipText("Advance one step");
		backwardButton.setToolTipText("Go back one step");
		stopButton.setToolTipText("Clear grid");
		
		restartButton.setEnabled(false);
		backwardButton.setEnabled(false);

		buttonPanel.add(backwardButton);
		buttonPanel.add(playButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(forwardButton);
		buttonPanel.add(restartButton);
		buttonPanel.add(stopButton);
		
		return buttonPanel;
	}
	
	private JPanel optionPanelSetUp() {
		JPanel optionPanel = new JPanel();
		BoxLayout blayout = new BoxLayout(optionPanel, BoxLayout.Y_AXIS);
		optionPanel.setLayout(blayout);
		
		showGridBox = new JCheckBox();
		showGridBox.setText("Show grid");
		showGridBox.setToolTipText("Toggle grid");
		showGridBox.setSelected(true);
		gridPanel.setShowGrid(showGridBox);
		optionPanel.add(showGridBox);
		
		saveStatesBox = new JCheckBox();
		saveStatesBox.setText("Save states");
		saveStatesBox.setToolTipText("Keep track of intermediate states");
		saveStatesBox.setSelected(true);
		optionPanel.add(saveStatesBox);
		
		return optionPanel;
	}
	
	private JPanel speedSliderSetUp() {
		JPanel speedSliderPanel = new JPanel();
		BorderLayout blayout = new BorderLayout();
		speedSliderPanel.setLayout(blayout);

		JLabel speedLabel = new JLabel();
		speedLabel.setText("Speed:");
		
		speedSlider = new JSlider();
		speedSlider.setToolTipText("Controls simulation speed");
		speedSliderPanel.add(speedLabel, BorderLayout.NORTH);
		speedSliderPanel.add(speedSlider, BorderLayout.SOUTH);
		return speedSliderPanel;
	}
	
	public void addUIListener(UIListenerInterface al) {
		playButton.addActionListener(al);
		pauseButton.addActionListener(al);
		restartButton.addActionListener(al);
		forwardButton.addActionListener(al);
		backwardButton.addActionListener(al);
		stopButton.addActionListener(al);
		gridPanel.addMouseListener(al);
		speedSlider.addChangeListener(al);
		showGridBox.addChangeListener(al);
		saveStatesBox.addChangeListener(al);
	}

	public GridPanel getGridPanel() {
		return gridPanel;
	}
	
	public JLabel getStepCounter() {
		return stepCounter;
	}
	
	public Integer getSpeed() {
		return speedSlider.getValue();
	}
	
	public void setState(short state) {
		switch (state) {
		case STATE_FINALIZED:
			playButton.setVisible(true);
			pauseButton.setVisible(false);
			forwardButton.setEnabled(true);
			break;
		case STATE_CLEAR:
			playButton.setVisible(true);
			pauseButton.setVisible(false);
			restartButton.setEnabled(false);
			backwardButton.setEnabled(false);
			forwardButton.setEnabled(true);
			break;
		case STATE_ENABLED:
			restartButton.setEnabled(true);
			backwardButton.setEnabled(true);
			break;
		case STATE_PAUSED:
			playButton.setVisible(true);
			pauseButton.setVisible(false);
			backwardButton.setEnabled(true);
			forwardButton.setEnabled(true);
			break;
		case STATE_PLAYING:
			playButton.setVisible(false);
			pauseButton.setVisible(true);
			restartButton.setEnabled(true);
			backwardButton.setEnabled(false);
			forwardButton.setEnabled(false);
			break;
		}
		
		// Quick shit
		if(!saveStatesBox.isSelected()) {
			backwardButton.setEnabled(false);
		}
	}

	public boolean getSaveStates() {
		return saveStatesBox.isSelected();
	}
}
