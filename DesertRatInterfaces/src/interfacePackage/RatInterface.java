package interfacePackage;

/**
 * 
 * @author Larry Shannon
 *
 */

public interface RatInterface 
{
	
	/**
	 * The move() method has the rat randomly select one of the eight possible moves directions.<br>
	 * (N, NW, W, SW, S, SE, E, NE);<br>
	 * @Precondition: None
	 * @Postcondition: None
	 * @return
	 * the unbiased, randomly generated, direction to move the rat
	 */
	public String move();
	
	/**
	 * The getAliveState() method returns the alive status of the rat.
	 * 0 Rat is alive, 1 Rat is dead of exhaustion 
	 * @Precondition: the Rat's alive status has been initialized
	 * @Postcondition: no change in the mouse's state
	 * @return
	 * the integer value of the mouse's alive status
	 */
	public int getAliveState();
	
	/**
	 * Rat is refreshed
	 * Clears away exhaustion
	 */
	public void refresh();
	/**
	 * Rat gets a little more exhausted
	 * If exhaustion gets too low Rat dies.
	 */
	public void wearDown();
	/**
	 * Retrieve the Rat's ID
	 */
	public String getId();

}
