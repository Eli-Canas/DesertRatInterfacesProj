/**
 * 
 */
package interfacePackage;

import java.util.ArrayList;

/**
 * @author Larry Shannon
 *
 */
public interface CellInterface 
{
	int[] location = new int[2];

	/**
	 * The getCellType() returns the cell type
	 * @Precondition The cell type must be initialized as a final variable during the construction of a cell object.<br>
	 * It should indicate one of the two cell types Cell, Hole
	 * @Postcondition no change
	 * @return a character for the cell type
	 */
	public char getCellType();

	/**
	 * The receiveRat() method receives a rat and stores the rat
	 * @param RatInterface holds reference to Rat object
	 * @return 
	 * Returns {row,col}<br>
	 * cell location <br>
	 */
	public int[] receiveRat(RatInterface pRat);

	/**
	 * The retrieveRat(String pRatID) method receives a rat ID and returns the reference to that rat if the rat is stored in the cell
	 * @param RatID holds String ID of Rat object
	 * @return Returns the reference to a Rat object if there is one in the cell
	 * postcondition: the reference to the Rat object is removed from the cell
	 */
	public RatInterface retrieveRat(String pRatId);

	/**
	 * The storeTheDead(Rat pRat) method stores the argument Rat in a list of dead rats.
	 * precondition: pRat must reference a Rat object, a list structure of some kind that stores rats, must exist.
	 * postcondition: pRat's reference is added to a list of dead rats.
	 * @param pRat
	 */
	public void storeTheDead(RatInterface pRat);
	
	/**
	 * The returnTheDead() method retrieves a list of the rats that died in the cell.
	 * @precondition The dead rat ArrayList must exist though it need not have any rats in it.
	 * @postcondition none
	 * @return
	 * ArrayList<> of Rat objects.
	 */
	public ArrayList<RatInterface> returnTheDead();
}
