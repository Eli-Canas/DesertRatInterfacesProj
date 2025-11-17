import java.util.*;
import interfacePackage.*;

class Cell implements CellInterface {
    protected final int row, col;

    // Keep a single occupant so retrieveRat(...) can hand it back out
    protected RatInterface occupant = null;

    protected final List<RatInterface> graveyard = new ArrayList<>();
    protected final Random rng = new Random();

    public Cell(int r, int c) {
        this.row = r;
        this.col = c;
    }

    @Override
    public int[] receiveRat(RatInterface pRat) {
        // House the rat, then apply terrain effect
        occupant = pRat;

        // 70% chance to wear down, 30% to refresh
        if (rng.nextDouble() < 0.70) {
            occupant.wearDown();
        } else {
            occupant.refresh();
        }

        // If cell effects killed the rat, move to graveyard and clear occupant
        if (occupant.getAliveState() == 1) {
            graveyard.add(occupant);
            occupant = null;
        }

        // Always report this cell's coordinates as the landing location
        return new int[]{row, col};
    }

    @Override
    public RatInterface retrieveRat(String pRatId) {
        if (occupant != null && occupant.getId().equals(pRatId)) {
            RatInterface r = occupant;
            occupant = null;
            return r;
        }
        return null; // no such rat here (or escaped earlier)
    }

    @Override
    public char getCellType() {
        return '.'; // plain cell
    }

    @Override
    public ArrayList<RatInterface> returnTheDead() {
        return new ArrayList<>(graveyard);
    }

    @Override
    public void storeTheDead(RatInterface pRat) {
        graveyard.add(pRat);
    }
}
