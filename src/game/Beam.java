package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Beam {
  private Point position;
  private double rotation;
  private double speed = 5.0;
  private boolean active = true;


  public Beam(Point startPosition, double startRotation){
    position = new Point (startPosition.x, startPosition.y);
    rotation = startRotation;
  }

  public void move(){
    position.x += speed * Math.cos(Math.toRadians(rotation));
    position.y += speed * Math.sin(Math.toRadians(rotation));

    if (position.x < 0 || position.x > 800 || position.y < 0|| position.y > 600){
      active = false;}
    }

  public void paint(Graphics brush){
    if(active){
        brush.setColor(Color.YELLOW);
        brush.fillRect((int) position.x, (int) position.y, 5, 5);}
      }

  public boolean isOnScreen(){
    return active;
  }

  public Point getPosition(){
    return position;
  }
  }
