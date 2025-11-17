import java.util.*;
import interfacePackage.*;

class Rat implements RatInterface {
    private static int NEXT = 1;

    private final String id = String.format("R%04d", NEXT++);
    private final Random rng = new Random();

    // 0 = alive, 1 = lost/dead, -1 = finished
    private int aliveState = 0;

    // simple stamina model; drop below 0 => dead
    private int stamina = 5;

    @Override
    public String move() {
        // Return a compass direction string per RatInterface
        String[] dirs = {"N","NE","E","SE","S","SW","W","NW"};
        return dirs[rng.nextInt(dirs.length)];
    }

    @Override
    public void refresh() {
        if (aliveState == 0) stamina = Math.min(stamina + 1, 8);
    }

    @Override
    public void wearDown() {
        if (aliveState != 0) return;
        stamina--;
        if (stamina < 0) aliveState = 1; // mark dead/lost
    }

    @Override
    public int getAliveState() {
        return aliveState;
    }

    @Override
    public String getId() {
        return id;
    }

   
}
