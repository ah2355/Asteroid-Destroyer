package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Asteroid extends Element implements Move {

	private double xPos;
	private double yPos;
	private double rotate;
	
	public Asteroid(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		
		Random rand = new Random();
		xPos = (rand.nextDouble() * 1) * 5;
		yPos = (rand.nextDouble() * 1) * 5;
		rotate = (rand.nextDouble() * 1) * 1;
		
	}
	
	@Override
	public void paint(Graphics brush) {
		brush.setColor(Color.GRAY);
		super.paint(brush);
	}
	
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
