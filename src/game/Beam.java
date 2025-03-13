//MADE BY: Afif Haque & Aahaan Seth
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * CLASS: Beam
 * DESCRIPTION: represents a object fired by a ship in the game.
 * It moves in a straight line based on its initial rotation and disappears 
 * when it goes off screen
 */
public class Beam {
	private Point position;
	private double rotation;
	private double speed = 5.0;
	private boolean active = true;

	/**
     * Constructs a Beam object with a given starting position and rotation angle.
     *
     * @param startPosition the initial position of the beam
     * @param startRotation the initial rotation angle of the beam in degrees
     */
	public Beam(Point startPosition, double startRotation) {
		position = new Point(startPosition.x, startPosition.y);
		rotation = startRotation;
	}
	
	/**
     * Moves the beam forward in the direction of its rotation.
     * If the beam moves outside the screen boundaries, it is deactivated.
     */
	public void move() {
		position.x += speed * Math.cos(Math.toRadians(rotation));
		position.y += speed * Math.sin(Math.toRadians(rotation));

		if (position.x < 0 || position.x > 800 || position.y < 0 || 
				position.y > 600) {
			active = false;
		}
	}
	
	/**
     * Paints the beam as a small yellow rectangle if it is still active.
     *
     * @param brush the Graphics object used to draw the beam
     */
	public void paint(Graphics brush) {
		if (active) {
			brush.setColor(Color.YELLOW);
			brush.fillRect((int) position.x, (int) position.y, 5, 5);
		}
	}

	/**
     * Checks if the beam is still active and on the screen.
     *
     * @return true if the beam is active, false otherwise
     */
	public boolean isOnScreen() {
		return active;
	}

	/**
     * Gets the current position of the beam.
     *
     * @return the position of the beam as a Point object
     */
	public Point getPosition() {
		return position;
	}
}
