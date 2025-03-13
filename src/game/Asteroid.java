package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * CLASS: Asteroid
 * DESCRIPTION: represents an asteroid in the game, inheriting from the Element 
 * class and implementing the Move interface. It defines the behavior of an 
 * asteroid, including its position, movement, and rotation. Asteroids are 
 * drawn as gray objects that move across the screen, wrapping around the edges.
 */

public class Asteroid extends Element implements Move {

	private double xPos;
	private double yPos;
	private double rotate;
	
	 /**
     * Constructs an Asteroid object with the given shape, position, and rotation. 
     * Random values for the asteroid's movement speed and rotation are generated.
     * 
     * @param inShape an array of Points representing the shape of the asteroid
     * @param inPosition the starting position of the asteroid
     * @param inRotation the starting rotation of the asteroid
     */
	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		
		Random rand = new Random();
		xPos = (rand.nextDouble() * 1) * 4;
		yPos = (rand.nextDouble() * 1) * 4;
		rotate = (rand.nextDouble() * 1) * 1;
		
	}
	
	/**
     * Paints the asteroid on the screen with a gray color.
     * This method overrides the paint method in the Element class.
     * 
     * @param brush the Graphics object used to draw the asteroid
     */
	@Override
	public void paint(Graphics brush) {
		brush.setColor(Color.GRAY);
		super.paint(brush);
	}
	
	 /**
     * Moves the asteroid across the screen. The asteroid moves at a random speed, 
     * rotates, and wraps around the edges of the screen.
     * This method overrides the move method from the Move interface.
     */
	@Override
	public void move() {
		position.x += xPos;
		position.y += yPos;
		rotation += rotate;
		
		if(position.x < 0) {
			position.x = 800;
		}
		
		if(position.x > 800) {
			position.x = 0;
		}
		
		if(position.y < 0) {
			position.y = 600;
		}
		
		if(position.y > 600) {
			position.y = 0;
		}
	}


}
