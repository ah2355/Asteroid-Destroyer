package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Element extends Polygon implements KeyListener, Move{
	
	private boolean forward = false;
	private boolean left = false;
	private boolean right = false;

	public Element(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

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
	
	public void move() {
		double stepSize = 2.0;
		
		if(forward) {
			double x = stepSize * Math.cos(Math.toRadians(rotation));
			double y = stepSize * Math.sin(Math.toRadians(rotation));
			
			position.x += x;
			position.y += y;
		}
		
		if(left) {
			rotation -= 5;
			if(rotation < 0) {
				rotation += 360;
			}
		}
		
		if(right) {
			rotation += 5;
			if(rotation >= 360) {
				rotation -= 360;
			}
		}
		
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
	
	public boolean collide(Polygon p) {
		for(Point ps: this.getPoints()) {
			if(p.contains(ps)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) {
			forward = true;
		}
		
		if(key == KeyEvent.VK_LEFT) {
			left = true;
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			right = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_UP) {
			forward = false;
		}
		
		if(key == KeyEvent.VK_LEFT) {
			left = false;
		}
		
		if(key == KeyEvent.VK_RIGHT) {
			right = false;
		}		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
