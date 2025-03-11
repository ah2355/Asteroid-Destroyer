package game;

/*
CLASS: YourGameNameoids
DESCRIPTION: Extending Game, YourGameName is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.

*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class AsteroidDestroyer extends Game {
	static int counter = 0;
	private Element element;
	private ArrayList<Element> sh;
	
	public AsteroidDestroyer() {
		super("AsteroidDestroyer!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		sh = new ArrayList<Element>();

		Point[] p = { new Point(0, 0), new Point(20, 0), 
				new Point(20, 20) };
		
		element = new Element(p, new Point(400, 300), 0);
		sh.add(element);
		
		this.addKeyListener(element);
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		

		if(sh!= null) {
			for(Element sh1: sh) {
				sh1.move();
				
				for(Element sh2 : sh) {
					if(sh1 != sh2 && sh1.collide(sh2)) {
						System.out.println("System collides");
					}
				}
				sh1.paint(brush);
			}
		}
		
		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);
		
		
	}

	public static void main(String[] args) {
		AsteroidDestroyer a = new AsteroidDestroyer();
		a.repaint();
	}
}