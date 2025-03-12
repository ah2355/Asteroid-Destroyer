package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Star {
	private Point position;
	
	public Star(int width, int height) {
		Random rand = new Random();
		position = new Point(rand.nextDouble(width),rand.nextDouble(height));
	}
	
	public void paint(Graphics brush) {
		brush.setColor(Color.WHITE);
		brush.fillOval((int) position.x, (int) position.y, 2, 2);
	}
	
	public Point getPosition() {
		return position;
	}
}

