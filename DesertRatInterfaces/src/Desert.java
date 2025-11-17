import java.util.*;
import interfacePackage.*;


class Desert implements DesertInterface {
    private final Random RNG = new Random();
    private CellInterface[][] grid;
    private int rows, cols;

    // Track rats and their locations
    private final Map<String, RatInterface> rats = new HashMap<>();
    private final Map<String, int[]> locations = new HashMap<>();

    // Track each rat's path, and remember the successful one
    private final Map<String, List<int[]>> paths = new HashMap<>();
    private List<int[]> lastSuccessfulPath = null;

    // Simple tallies
    private int lostOrDead = 0;
    private int finishedCount = 0;

    public Desert() {
        int size = askForSize();
        this.rows = size;
        this.cols = size;
        buildGrid();
    }

    // --- helpers ---
    private int askForSize() {
        // robust parse (avoids String->int issues)
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter desert size N (>=3): ");
        int n;
        try {
            n = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            n = 5;
        }
        if (n < 3) n = 3;
        return n;
    }

    private void buildGrid() {
        grid = new CellInterface[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (RNG.nextDouble() < 0.15 && !(r == 0 && c == 0)) {
                    grid[r][c] = ObjectCreator.createNewHole(r, c);
                } else {
                    grid[r][c] = ObjectCreator.createNewCell(r, c);
                }
            }
        }
    }

    // Convert String direction to (dr,dc)
    private int[] stepFrom(String dir) {
        if (dir == null) return new int[]{0,0};
        switch (dir.trim().toUpperCase(Locale.ROOT)) {
            case "N":  return new int[]{-1,  0};
            case "NE": return new int[]{-1,  1};
            case "E":  return new int[]{ 0,  1};
            case "SE": return new int[]{ 1,  1};
            case "S":  return new int[]{ 1,  0};
            case "SW": return new int[]{ 1, -1};
            case "W":  return new int[]{ 0, -1};
            case "NW": return new int[]{-1, -1};
            default:   return new int[]{0, 0};
        }
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }



    @Override
    public RatStatusInterface startRat() {
        RatInterface rat = ObjectCreator.createNewRat();
        String id = rat.getId();

        rats.put(id, rat);
        locations.put(id, new int[]{0, 0});

        // Start cell receives the rat (applies terrain effect)
        grid[0][0].receiveRat(rat);

        // Begin path log at (0,0)
        List<int[]> path = new ArrayList<>();
        path.add(new int[]{0,0});
        paths.put(id, path);

        return ObjectCreator.createNewRatStatus(id, 0);
    }

    @Override
    public RatStatusInterface moveRat(String pRatID) {
        RatInterface rat = rats.get(pRatID);
        int[] pos = locations.get(pRatID);

        if (rat == null || pos == null) {
            // Unknown id—treat as gone
            return ObjectCreator.createNewRatStatus(pRatID, 1);
        }

        // Take rat out of its current cell. If this returns null, it escaped (prior hole) or is gone.
        RatInterface withdrawn = grid[pos[0]][pos[1]].retrieveRat(pRatID);
        if (withdrawn == null) {
            rats.remove(pRatID);
            locations.remove(pRatID);
            lostOrDead++;
            return ObjectCreator.createNewRatStatus(pRatID, 1);
        }

        // Ask rat for a direction; compute target; clamp to grid
        int[] d = stepFrom(rat.move());
        int nr = pos[0] + d[0];
        int nc = pos[1] + d[1];
        if (!inBounds(nr, nc)) { nr = pos[0]; nc = pos[1]; }

        // Step into the next cell
        int[] landing = grid[nr][nc].receiveRat(rat);

        // Update path with landing
        List<int[]> path = paths.get(pRatID);
        if (path != null) path.add(new int[]{landing[0], landing[1]});

        // Check rat's state
        int state = rat.getAliveState();
        if (state == 1) { // dead/lost
            rats.remove(pRatID);
            locations.remove(pRatID);
            lostOrDead++;
            return ObjectCreator.createNewRatStatus(pRatID, 1);
        }
        if (state == -1) { // self-declared finished
            rats.remove(pRatID);
            locations.remove(pRatID);
            finishedCount++;
            lastSuccessfulPath = path;
            return ObjectCreator.createNewRatStatus(pRatID, -1);
        }

        // Finish when it reaches bottom-right
        if (landing[0] == rows - 1 && landing[1] == cols - 1) {
            rats.remove(pRatID);
            locations.remove(pRatID);
            finishedCount++;
            lastSuccessfulPath = path;
            return ObjectCreator.createNewRatStatus(pRatID, -1);
        }

        // Otherwise keep going next tick
        locations.put(pRatID, new int[]{landing[0], landing[1]});
        return ObjectCreator.createNewRatStatus(pRatID, 0);
    }

    @Override
    public void displayStatistics() {
        System.out.println("\n=== Results ===");
        System.out.println("Lost or dead: " + lostOrDead);
        System.out.println("Finished: " + finishedCount);

        if (lastSuccessfulPath != null) {
            System.out.print("Successful path: ");
            for (int i = 0; i < lastSuccessfulPath.size(); i++) {
                int[] p = lastSuccessfulPath.get(i);
                System.out.print("[" + p[0] + "," + p[1] + "]");
                if (i < lastSuccessfulPath.size() - 1) System.out.print(" -> ");
            }
            System.out.println();
        }

        // Optional: also aggregate from cells/holes for transparency
        int graveCount = 0, holeEscapes = 0;
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                graveCount += grid[r][c].returnTheDead().size();
                if (grid[r][c] instanceof HoleInterface) {
                    holeEscapes += ((HoleInterface)grid[r][c]).countLostSouls();
                }
            }
        }
        System.out.println("Bodies in cell graveyards: " + graveCount);
        System.out.println("Escapes recorded by holes: " + holeEscapes);
    }

    @Override
    public void printMap() {
        System.out.println("\nDesert map (H=hole, .=cell):");
        for (int r = 0; r < rows; r++) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < cols; c++) {
                char t = grid[r][c].getCellType();
                line.append((t == 'H' || t == 'h') ? 'H' : '.');
            }
            System.out.println(line);
        }
        System.out.println();
    }
}
