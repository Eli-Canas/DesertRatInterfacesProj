import java.util.*;
import interfacePackage.*;

class Hole extends Cell implements HoleInterface {
    // Track all holes so we can teleport between them
    private static final List<Hole> ALL_HOLES = new ArrayList<>();

    // Stats for escapes
    private int disappeared = 0;

    public Hole(int r, int c) {
        super(r, c);
        ALL_HOLES.add(this);
    }

    @Override
    public int[] receiveRat(RatInterface pRat) {
        // 10% escape, 70% teleport to another hole (if exists), 20% stay (apply terrain)
        double x = rng.nextDouble();

        if (x < 0.10) {
            // ESCAPE: as per interface docs, still return this cell's location,
            // but a later retrieveRat(id) will return null
            occupant = null;
            disappeared++;
            return new int[]{row, col};
        }

        if (x < 0.80 && ALL_HOLES.size() > 1) {
            // TELEPORT to a different hole; that hole will apply its terrain logic
            Hole dest = this;
            while (dest == this && ALL_HOLES.size() > 1) {
                dest = ALL_HOLES.get(rng.nextInt(ALL_HOLES.size()));
            }
            return dest.receiveRat(pRat);
        }

       
        return super.receiveRat(pRat);
    }

    @Override
    public char getCellType() {
        return 'H';
    }

    @Override
    public int countLostSouls() {
        return disappeared;
    }
}
