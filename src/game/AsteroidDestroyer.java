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
	private Ship element;
	private ArrayList<Asteroid> ast;
	private PowerUp[] power;

	public AsteroidDestroyer() {
		super("AsteroidDestroyer!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		ast = new ArrayList<Asteroid>();
		power = new PowerUp[5];

		Point[] p = { new Point(0, 0), new Point(20, 0), 
				new Point(20, 20), new Point(0, 20) };

		element = new Ship(p, new Point(400, 300), 0);

		Point[] astr = { new Point(0, 0), new Point(20, -10),
				new Point(40, 0), new Point(30, 20), new Point(10, 20) };

		for (int i = 0; i < 4; i++) {
			int x = (int) (Math.random() * 800);
			int y = (int) (Math.random() * 600);
			double rotation = Math.random() * 360;

			ast.add(new Asteroid(astr, new Point(x, y), rotation));
		}

		this.addKeyListener(element);

		spawnPower();
	}

	public void spawnPower() {
		for (int i = 0; i < power.length; i++) {
			Point randomPosition = new Point((int) (Math.random() * 800),
					(int) (Math.random() * 600));
			power[i] = new PowerUp(randomPosition);
		}
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);

		if (element != null) {
			element.move();
			element.paint(brush);

			for (PowerUp pw : power) {
				pw.paint(brush);
			}

			for (PowerUp pw : power) {
				if (element.collide(pw)) {
					System.out.println("Power-up collected");
					pw.activateShield(element);
					element.collectPower(pw);
				}
			}

			for (Asteroid at : ast) {
				at.move();
				at.paint(brush);

				if (element.collide(at)) {
					System.out.println("System collides");
					if (element.statShield()) {
						System.out.println("Shield blocked the collision with asteroid!");
					} else {
						System.out.println("Ship collided with asteroid and took damage!");
					}
				}
			}

			checkCollisions();

		}

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter, 10, 10);

	}

	private void checkCollisions() {
		ArrayList<Beam> beams = element.getBeams();
		beams.removeIf(beam -> {
			for (Asteroid at : ast) {
				if (at.contains(beam.getPosition())) {
					ast.remove(at);
					return true;
				}
			}
			return false;
		});

		if (ast.size() == 0) {
			addMore();
		}
	}

	private void addMore() {
		Point[] astr = { new Point(0, 0), new Point(20, -10), 
				new Point(40, 0), new Point(30, 20), new Point(10, 20) };

		for (int i = 0; i < 4; i++) {
			int x = (int) (Math.random() * 800);
			int y = (int) (Math.random() * 600);
			double rotation = Math.random() * 360;

			ast.add(new Asteroid(astr, new Point(x, y), rotation));
		}

	}

	public static void main(String[] args) {
		AsteroidDestroyer a = new AsteroidDestroyer();
		a.repaint();
	}
}
