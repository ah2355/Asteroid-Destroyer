package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * CLASS: Ship
 * DESCRIPTION: Represents the player's spaceship in the game, inheriting from 
 * Element class and implementing Move interface. It handles 
 * movement, shooting, powerups, and collision detection.
 */
public class Ship extends Element implements Move {
	private boolean forward = false;
	private boolean left = false;
	private boolean right = false;
	private boolean backward = false;
	private boolean shield = false;
	private PowerUp power;
	private ArrayList<Beam> beams;
	private boolean shooting;
	private Life lives;

	/**
     * Constructs a Ship with a given shape, position, and rotation. Also
     * initializing the lives variable
     * 
     * @param inShape an array of Points representing the shape of the ship
     * @param position The initial position of the ship.
     * @param rotation The initial rotation angle of the ship.
     */
	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
		beams = new ArrayList<>();
		lives = new Life(3); // the initial life is set to 3
	}

	/**
	 * Paints the ship and it beams on the screen, overrides the method from
	 * Element class
	 * @param brush the Graphics object for rendering
	 */
	@Override
	public void paint(Graphics brush) {
		if (shield) {
			brush.setColor(Color.CYAN);
		} else {
			brush.setColor(Color.RED);
		}
		super.paint(brush);

		if (power != null && !power.powerE.isActive()) {
			deactiveShield();
			brush.setColor(Color.RED);
			power = null;
		}

		for (Beam beam : beams) {
			beam.paint(brush);
		}
	}

	/**
     * Checks if the ship collides with another game object.
     * 
     * @param obj The other game object.
     * @return True if a collision is detected, false otherwise.
     */
	@Override
	public boolean collide(Polygon p) {
		if (this.statShield() && p instanceof PowerUp) {
			return false;
		}

		if (p instanceof PowerUp) {
			PowerUp powerUp = (PowerUp) p;
			double dx = this.position.x - powerUp.position.x;
			double dy = this.position.y - powerUp.position.y;
			double distance = Math.sqrt(dx * dx + dy * dy);
			if (distance < 20) {
				return true;
			}
		} else {
			for (Point ps : this.getPoints()) {
				if (p.contains(ps)) {
					return true;
				}
			}
		}
		
		return false;
	}

	/**
     * Collects a powerup and applies its effect.
     * 
     * @param powerUp The power-up to collect.
     */
	public void collectPower(PowerUp pw) {
		power = pw;
		pw.activateShield(this);
	}

	 /**
     * Activate the shield if its not active
     * 
     */
	public void activeShield() {
		if (!shield) {
			shield = true;
		}
		System.out.println("Shield activated!");
	}

	/**
     * Deactivate the shield if is active
     * 
     */
	public void deactiveShield() {
		if (shield) {
			shield = false;
		}
		System.out.println("Shield deactivated!");
	}

	 /**
     * Checks if the shield is active.
     * 
     * @return True if the shield is active, false otherwise.
     */
	public boolean statShield() {
		return shield;
	}
	
	/**
	 * INNER CLASS(1): Life
     * DESCRIPTION: The Life class is an inner class that manages 
     * the player's lives. It tracks the number of remaining lives and 
     * implements a cooldown system to prevent rapid life loss in case of 
     * multiple collisions.
     */
	public class Life{
		private int lifes;
		private long lastTimeHit;
		private final long coolDown = 1000;
		
		 /**
         * Constructs a Life object with the given number of lives.
         * 
         * @param l The initial number of lives.
         */
		public Life(int l) {
			lifes = l;
		}
		
		/**
         * Decreases the player's life count if enough time has passed since 
         * the last hit. Prevents rapid loss of multiple lives.
         */
		public void loseLife() {
			long currentTime = System.currentTimeMillis();
			
			if(lifes > 0) {
				if (currentTime - lastTimeHit > coolDown) {
		            lifes--;
		            lastTimeHit = currentTime;
		        }
			}
		}
		
		/**
         * Returns the number of remaining lives.
         * 
         * @return The number of lives left.
         */
		public int getLife() {
			return lifes;
		}
		
		/**
         * Checks if the player is still alive.
         * 
         * @return true if the player has at least one life remaining, 
         * false otherwise.
         */
		public boolean isAlive() {
			if(lifes > 0) {
				return true;
			}
			
			return false;
		}
	}
	
	/**
     * Get the live object from the outer class
     * 
     * @return the object of Life class
     */
	public Life getLifeAll() {
		return lives;
	}

	 /**
     * Moves the ship based on key inputs.
     * Ensures the ship wraps around the screen when moving out of bounds.
     */
	public void move() {
		double stepSize = 2.0;
		
		if(lives.isAlive()) {
			if (forward) {
				double x = stepSize * Math.cos(Math.toRadians(rotation));
				double y = stepSize * Math.sin(Math.toRadians(rotation));

				position.x += x;
				position.y += y;
			}

			if (backward) {
				double x = stepSize * Math.cos(Math.toRadians(rotation));
				double y = stepSize * Math.sin(Math.toRadians(rotation));

				position.x -= x;
				position.y -= y;
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

			if (shooting) {
				beams.add(new Beam(new Point(position.x, position.y), rotation));
				shooting = false;
			}

			//Lambda expression for removing the beam that are off screen
			beams.removeIf(beam -> !beam.isOnScreen());
			for (Beam beam : beams) {
				beam.move();
			}
			
			// Screen wrap around login
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

	 /**
     * Handles key press events for controlling the ship.
     * 
     * @param e The KeyEvent object.
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

		if (key == KeyEvent.VK_DOWN) {
			backward = true;
		}

		if (key == KeyEvent.VK_SPACE) {
			shooting = true;
		}

	}

	 /**
     * Handles key release events to stop movement.
     * 
     * @param e The KeyEvent object.
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

		if (key == KeyEvent.VK_DOWN) {
			backward = false;
		}
	}

	 /**
     * Returns an ArrayList of Beam to get all the beam position
     * 
     * @return the ArrayList of Beam
     */
	public ArrayList<Beam> getBeams() {
		return this.beams;
	}

}
