package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Ship extends Element implements Move {
	private boolean forward = false;
	private boolean left = false;
	private boolean right = false;
	private boolean backward = false;
	private boolean shield = false;
	private PowerUp power;

	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	public void paint(Graphics brush) {
		if (shield) {
			brush.setColor(Color.CYAN);
		} else {
			brush.setColor(Color.RED);
		}
		super.paint(brush);
		
		if(power != null) {
			power.powerE.check();
		}
		if(!power.powerE.isActive()) {
			deactiveShield();
			power = null;
		}
	}
	
	public void collectPower(PowerUp pw) {
		power = pw;
		power.activateShield(this);
	}

	public void activeShield() {
		if (!shield) {
			shield = true;
		}
	}

	public void deactiveShield() {
		if (shield) {
			shield = false;
		}
	}

	public void move() {
		double stepSize = 2.0;

		if (forward) {
			double x = stepSize * Math.cos(Math.toRadians(rotation));
			double y = stepSize * Math.sin(Math.toRadians(rotation));

			position.x += x;
			position.y += y;
		}

		if (backward) {
			double x = stepSize * Math.cos(Math.toRadians(rotation));
			double y = stepSize * Math.sin(Math.toRadians(rotation));

			position.x -= x;
			position.y -= y;
		}

		if (left) {
			rotation -= 5;
			if (rotation < 0) {
				rotation += 360;
			}
		}

		if (right) {
			rotation += 5;
			if (rotation >= 360) {
				rotation -= 360;
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			forward = true;
		}

		if (key == KeyEvent.VK_LEFT) {
			left = true;
		}

		if (key == KeyEvent.VK_RIGHT) {
			right = true;
		}

		if (key == KeyEvent.VK_DOWN) {
			backward = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_UP) {
			forward = false;
		}

		if (key == KeyEvent.VK_LEFT) {
			left = false;
		}

		if (key == KeyEvent.VK_RIGHT) {
			right = false;
		}

		if (key == KeyEvent.VK_DOWN) {
			backward = false;
		}
	}

}
