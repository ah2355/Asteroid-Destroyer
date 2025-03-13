//MADE BY: Afif Haque & Aahaan Seth
package game;

/*
CLASS: AsteroidDestroyer 
DESCRIPTION: The main game class that has control for the game logic, 
and user input. It manages spaceship, asteroids, powerups, and background stuff.
*/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.Timer;

class AsteroidDestroyer extends Game {
	static int counter = 0;
	private Ship element;
	private ArrayList<Asteroid> ast;
	private ArrayList<Star> stars;
	private PowerUp[] power;

	/**
	 * Constructor initializes the game window and different elements of the
	 * game like spaceship, background, and other elements
	 */
	public AsteroidDestroyer() {
		super("AsteroidDestroyer!", 800, 600);
		this.setFocusable(true);
		this.requestFocus();
		ast = new ArrayList<Asteroid>();
		stars = new ArrayList<Star>();
		power = new PowerUp[5];

		Point[] p = { new Point(0, 0), new Point(20, 0), 
				new Point(20, 20), new Point(0, 20) };

		element = new Ship(p, new Point(400, 300), 0);


		this.addKeyListener(element);
		
		spawAsteroids();
		spawnPower();
		spawnStars();
	}

	/**
	 * Spawns powerup at random locations
	 */
	public void spawnPower() {
		for (int i = 0; i < power.length; i++) {
			Point randomPosition = new Point(20 + (int) 
					(Math.random() * (800 - 2 * 20)),
					20 +(int) (Math.random() * (600 - 2 * 20)));
			power[i] = new PowerUp(randomPosition);
		}
	}
	
	/**
	 * Spawns asteroids at random locations
	 */
	public void spawAsteroids() {
		Point[] astr = { new Point(0, 0), new Point(20, -10),
				new Point(40, 0), new Point(30, 20), new Point(10, 20) };

		for (int i = 0; i < 4; i++) {
			int x = (int) (Math.random() * 800);
			int y = (int) (Math.random() * 600);
			double rotation = Math.random() * 360;

			ast.add(new Asteroid(astr, new Point(x, y), rotation));
		}
	}
	
	/**
	 * Spawns stars at random locations (for background)
	 */
	public void spawnStars() {
		for(int i =0 ; i < 100; i++) {
			stars.add(new Star(800, 600));
		}
	}

	/**
	 * Paint method helps the game to put in the window
	 * @param brush the graphics content used for drawing
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0, 0, width, height);
		
		Font font = new Font("San-serif", Font.BOLD, 30);
		brush.setFont(font);
		brush.setColor(new Color(102, 0 ,153));
		brush.drawString("Asteroid Destroyer", 250, 75);
		
		Font live = new Font("San-serif", Font.BOLD, 10);
		brush.setFont(live);
		brush.setColor(Color.WHITE);
		brush.drawString("Lives: " + element.getLifeAll().getLife(), 10, 30);
		
		Font inst = new Font("San-serif", Font.BOLD, 10);
		brush.setFont(inst);
		brush.setColor(Color.white);
		brush.drawString("Press \"space\" to shoot", 330, 95);
		
		for(Star star: stars) {
			star.paint(brush);
		}
		
		
		if (element != null) {
			element.move();
			element.paint(brush);
			
			if(!element.getLifeAll().isAlive()) {
				Font gg = new Font("San-serif", Font.BOLD, 40);
				brush.setFont(gg);
		        brush.setColor(Color.RED);
		        brush.drawString("Game Over", 300, 300);
		        
		        Font count = new Font("San-serif", Font.BOLD, 15);
				brush.setFont(count);
				brush.setColor(Color.WHITE);
				brush.drawString("Asteroid Hit: " + counter, 355, 330);
		        return;
			}

			for (PowerUp pw : power) {
				if(pw!= null) {
					pw.paint(brush);
				}	
			}

			for(int i = 0; i < power.length; i++) {
				if(power[i] != null && element.collide(power[i])) {
					System.out.println("Power-up collected");
					element.collectPower(power[i]);
					power[i] = null;
				}
			}

			for (Asteroid at : ast) {
				at.move();
				at.paint(brush);

				if (element.collide(at)) {
					if (element.statShield()) {
						System.out.println("Shield blocked the asteroid!");
					} else {
						System.out.println("Ship collided with asteroid took damage!");
						element.getLifeAll().loseLife();
					}
				}
			}

			checkCollisions();

		}

		Font counterFont = new Font("San-serif", Font.BOLD, 10);
		brush.setFont(counterFont);
		brush.setColor(Color.white);
		brush.drawString("Hit: " + counter, 10, 10);
	}

	/**
	 * Check for collisions between beams and asteroids removing them 
	 * when the beam hits asteroid
	 */
	private void checkCollisions() {
		ArrayList<Beam> beams = element.getBeams();
		beams.removeIf(beam -> {
			for (Asteroid at : ast) {
				if (at.contains(beam.getPosition())) {
					ast.remove(at); 
					counter++;
					return true;
				}
			}
			return false;
		});

		if (ast.size() == 0) {
			addMoreAst();
		}
	}

	/**
	 * Add more Asteroids when they are all destroyed
	 */
	private void addMoreAst() {
		Point[] astr = { new Point(0, 0), new Point(20, -10), 
				new Point(40, 0), new Point(30, 20), new Point(10, 20) };

		for (int i = 0; i < 4; i++) {
			int x = (int) (Math.random() * 800);
			int y = (int) (Math.random() * 600);
			double rotation = Math.random() * 360;

			ast.add(new Asteroid(astr, new Point(x, y), rotation));
		}
		
		if(power.length == 0) {
			spawnPower();
		}

	}

	/**
	 * Main method to start the game
	 * @param args
	 */
	public static void main(String[] args) {
		AsteroidDestroyer a = new AsteroidDestroyer();
		a.repaint();
	}
}
