import java.util.*;
import interfacePackage.*;

public class ControlDriver {
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Rat Crossing Desert ===");

        boolean again = true;
        while (again) {
            DesertInterface desert = ObjectCreator.createNewDesert();

            int ratsUsed = 0;
            boolean finished = false;

            // Start the first rat
            RatStatusInterface status = desert.startRat();
            ratsUsed++;

            // Keep trying new rats until one finishes
            while (!finished) {
                // Drive the current rat until it either finishes (-1) or is gone (1)
                while (status.getRatState() == 0) {
                    status = desert.moveRat(status.getRatID());
                }

                if (status.getRatState() == -1) {
                    // Success!
                    finished = true;
                } else {
                    // Dead or escaped: start a new rat at (0,0)
                    status = desert.startRat();
                    ratsUsed++;
                }
            }

            System.out.println("Rats used to cross: " + ratsUsed);
            desert.displayStatistics();
            desert.printMap();

            System.out.print("Run again with a new desert? (y/n): ");
            String ans = SC.nextLine().trim().toLowerCase(Locale.ROOT);
            again = ans.startsWith("y");
        }

        System.out.println("Goodbye!");
    }
}
