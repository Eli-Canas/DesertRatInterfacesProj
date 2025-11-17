/**
 * 
 */
package interfacePackage;

/**
 * @author Larry Shannon
 *
 */
public interface RatStatusInterface
{
	/**
	 *
	 * @return String representation of Rat ID
	 */
	public String getRatID();
	/**
	 * 
	 * @return integer of Rat's state
	 * 1 Rat lost or dead
	 * 0 Rat alive
	 * -1 Rat finished 
	 */
	public int getRatState();
	/**
	 * 
	 * @param pRateState
	 * 1 Rat lost or dead
	 * 0 Rat alive
	 * -1 Rat finished 
	 */
	public void setRatState(int pRateState);
	
}
