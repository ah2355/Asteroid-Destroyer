package game;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PowerUp extends Element {
	private String power;
	protected PowerUpEff powerE;
	
	public PowerUp(Point inPosition) {
		super(new Point [] { new Point(0,0),new Point(20,0),
				new Point(0,20),new Point(20,20), }, inPosition, 0);
		power = "Shield";
		powerE = new PowerUpEff();
	}
	
	public void paint(Graphics brush) {
		brush.setColor(Color.BLUE);
		brush.fillOval((int)position.x, (int)position.y, 20, 20);
	}
	
	public void activateShield(Ship sh) {
		sh.activeShield();
		powerE.start();
		
	}
	
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
    
    
	 class PowerUpEff{
		private long time;
		private boolean active;
		private Timer timer;
		
		public PowerUpEff() {
			active = false;
		}
		
		public void start() {
			if(!active) {
				time = System.currentTimeMillis();
				active = true;
				System.out.println("Shield Power-Up activated!");
			}
			timer = new Timer(5000, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(active && (System.currentTimeMillis() - time) >  5000) {
						active = false;
						System.out.println("Shield Expired");
						timer.stop();
					}
				}
			
			});
			timer.start();
			
		}
		
		
		public boolean isActive() {
			return active;
		}
	}

}
