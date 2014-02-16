package conway.control;

import static conway.ui.ButtonActionCommands.BACKWARD_AC;
import static conway.ui.ButtonActionCommands.CLEAR_AC;
import static conway.ui.ButtonActionCommands.FORWARD_AC;
import static conway.ui.ButtonActionCommands.PAUSE_AC;
import static conway.ui.ButtonActionCommands.PLAY_AC;
import static conway.ui.ButtonActionCommands.RESTART_AC;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.event.ChangeEvent;

import conway.ui.UIListenerInterface;

public class ControlListener implements UIListenerInterface {
	
	Control ctrl;
	
	ControlListener(Control _ctrl) {
		ctrl = _ctrl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PLAY_AC:
			ctrl.play();
			break;
		case PAUSE_AC:
			ctrl.pause();
			break;
		case RESTART_AC:
			ctrl.reset();
			break;
		case FORWARD_AC:
			ctrl.forward();
			break;
		case BACKWARD_AC:
			ctrl.backward();
			break;
		case CLEAR_AC:
			ctrl.clear();
			break;
		}
	}

	@Override public void mouseClicked(MouseEvent arg0) {}
	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		ctrl.editGrid(arg0.getX(), arg0.getY());
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		ctrl.optionChange();
	}
}
