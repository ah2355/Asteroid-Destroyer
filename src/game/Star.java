package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * CLASS: Star
 * DESCRIPTION: represents a small star in the game's background. Stars are 
 * randomly positioned and rendered as small white dots.
 */
public class Star {
	private Point position;
	
	/**
     * Constructs a Star object with a random position within the given 
     * screen dimensions.
     *
     * @param width  the width of the screen
     * @param height the height of the screen
     */
	public Star(int width, int height) {
		Random rand = new Random();
		position = new Point(rand.nextDouble(width),rand.nextDouble(height));
	}
	
	/**
     * Paints the star as a small white dot at its current position.
     *
     * @param brush the Graphics object used to draw the star
     */
	public void paint(Graphics brush) {
		brush.setColor(Color.WHITE);
		brush.fillOval((int) position.x, (int) position.y, 2, 2);
	}
	
	/**
     * Paints the star as a small white dot at its current position.
     *
     * @param brush the Graphics object used to draw the star
     */
	public Point getPosition() {
		return position;
	}
}
