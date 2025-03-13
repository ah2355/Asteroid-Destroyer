package game;

/**
 * INTERFACE: Move 
 DESCRIPTION: Represents an object that can move within the
 * game. Any class implementing this interface must define the move() method
 */
public interface Move {
	/**
	 * Moves the implementing object based on its defined movement behavior.
	 */
	void move();
}
