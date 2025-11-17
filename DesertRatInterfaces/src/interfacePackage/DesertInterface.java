/**
 * 
 */
package interfacePackage;

/**
 * @author Larry Shannon
 *
 */
public interface DesertInterface
{
	/**
	 * Obtains a new rat and introduces it at the start of the desert.
	 * @return
	 * Id of Rat and its status as a tuple object.
	 */
	public RatStatusInterface startRat();
	
	/**
	 * Finds the current rat and instructs the rat to move.
	 * @param currentMouse
	 * @return Rat ID and status
	 * 1 if rat is no longer exists
	 * 0 if mouse moves to a new cell but not the exit
	 * -1 if rat finishes
	 */
	public RatStatusInterface moveRat(String pRatID);
	
	/**
	 * Prints all required information on dead rats.
	 */
	void displayStatistics();
	void printMap();

}
