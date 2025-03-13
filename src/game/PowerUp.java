package game;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * CLASS: PowerUp DESCRIPTION: Represents a collectible item in the game that
 * grants a temporary effect to the player upon collision. It extends the
 * Element class and provides functionality for activating power-ups,
 * specifically a shield power-up.
 */
public class PowerUp extends Element {
	private String power;
	protected PowerUpEff powerE;

	/**
	 * Constructs a PowerUp object at the given position. The default powerup type
	 * is a shield.
	 *
	 * @param inPosition the position where the power-up appears in the game
	 */
	public PowerUp(Point inPosition) {
		super(new Point[] { new Point(0, 0), new Point(20, 0), new Point(0, 20), new Point(20, 20), }, inPosition, 0);
		power = "Shield";
		powerE = new PowerUpEff();
	}

	/**
	 * Paints the powerup as a blue oval at its current position.
	 *
	 * @param brush the Graphics object used to draw the power-up
	 */
	public void paint(Graphics brush) {
		brush.setColor(Color.BLUE);
		brush.fillOval((int) position.x, (int) position.y, 20, 20);
	}

	/**
	 * Activates the shield effect for the ship and starts the powerup effect 
	 * timer.
	 *
	 * @param sh the Ship object that collects the power-up and activates the shield
	 */
	public void activateShield(Ship sh) {
		sh.activeShield();
		powerE.start();

	}

	/**
	 * Checks if this powerup collides with another polygon. If the collision is
	 * with a Ship, the power-up is collected, and an effect is applied.
	 *
	 * @param p the Polygon to check for collision with
	 * @return true if the power-up is collected by the ship, false otherwise
	 */
	@Override
	public boolean collide(Polygon p) {
		if (p instanceof Ship) {
			Ship ship = (Ship) p;
			ship.collectPower(this);
			System.out.println("Power-up collected!");
			return true;
		}
		return super.collide(p);
	}

	/**
	 * INNER CLASS(2): PowerUpEff DESCRIPTION: manages the duration and expiration of
	 * the powerup effect. It uses a timer to deactivate the effect after a set
	 * duration.
	 */
	class PowerUpEff {
		private long time;
		private boolean active;
		private Timer timer;

		/**
		 * Constructs a PowerUpEff object with an inactive state.
		 */
		public PowerUpEff() {
			active = false;
		}

		/**
		 * Starts the powerup effect. If not already active, it begins a timer 
		 * that expires after 5 seconds, deactivating the effect.
		 */
		public void start() {
			if (!active) {
				time = System.currentTimeMillis();
				active = true;
				System.out.println("Shield Power-Up activated!");
			}
			// Anonymous class
			timer = new Timer(5000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (active && (System.currentTimeMillis() - time) > 5000) {
						active = false;
						System.out.println("Shield Expired");
						timer.stop();
					}
				}

			});
			timer.start();

		}

		/**
		 * Checks if the powerup effect is currently active.
		 *
		 * @return true if the powerup effect is active, false otherwise
		 */
		public boolean isActive() {
			return active;
		}
	}

}
