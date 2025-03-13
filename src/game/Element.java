//MADE BY: Afif Haque & Aahaan Seth
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * CLASS: Element DESCRIPTION: Represents a moving and rotating object in the
 * game. It extends the Polygon class and implements the KeyListener and Move
 * interfaces. This class provides functionality for movement, rotation,
 * collision detection, and user input handling.
 */
public class Element extends Polygon implements KeyListener, Move {

	private boolean forward = false;
	private boolean left = false;
	private boolean right = false;

	/**
	 * Constructs an Element object with a specified shape, position, and rotation.
	 * 
	 * @param inShape    an array of Points representing the shape of the element
	 * @param inPosition the starting position of the element
	 * @param inRotation the starting rotation of the element
	 */
	public Element(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	/**
	 * Paints the element on the screen by filling a polygon shape.
	 * 
	 * @param brush the Graphics object used to draw the element
	 */
	public void paint(Graphics brush) {
		Point[] p = this.getPoints();

		int[] xPoints = new int[p.length];
		int[] yPoints = new int[p.length];

		for (int i = 0; i < p.length; i++) {
			xPoints[i] = (int) p[i].getX();
			yPoints[i] = (int) p[i].getY();
		}

		brush.fillPolygon(xPoints, yPoints, p.length);
	}

	/**
	 * Moves the element based on user input. The element moves forward when the up
	 * arrow key is pressed and rotates left or right when the respective arrow keys
	 * are pressed. The element also wraps around the screen if it moves out of
	 * bounds.
	 */
	public void move() {
		double stepSize = 2.0;

		if (forward) {
			double x = stepSize * Math.cos(Math.toRadians(rotation));
			double y = stepSize * Math.sin(Math.toRadians(rotation));

			position.x += x;
			position.y += y;
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

		if (position.x < 0) {
			position.x = 800;
		}

		if (position.x > 800) {
			position.x = 0;
		}

		if (position.y < 0) {
			position.y = 600;
		}

		if (position.y > 600) {
			position.y = 0;
		}

	}

	/**
	 * Checks if this element collides with another polygon.
	 * 
	 * @param p the Polygon to check for collision with
	 * @return true if there is a collision, false otherwise
	 */
	public boolean collide(Polygon p) {
		for (Point ps : this.getPoints()) {
			if (p.contains(ps)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Handles key press events. Sets movement variables based on the keys pressed.
	 * 
	 * @param e the KeyEvent representing the key press
	 */
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
	}

	/**
	 * Handles key release events. Resets movement variables when the 
	 *  keys are released.
	 * 
	 * @param e the KeyEvent representing the key release
	 */
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
	}

	  /**
     * Handles key typed events.
     * Have to implement this method because of implementing the interface,
     * but not really needed for the project.
     * 
     * @param e the KeyEvent representing the key typed
     */
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
