import interfacePackage.*;

class RatStatus implements RatStatusInterface {
    private final String id;
    private int state; // 1 lost/dead, 0 alive, -1 finished

    public RatStatus(String pId, int pState) { this.id = pId; this.state = pState; }

    @Override public String getRatID() { return id; }
    @Override public int getRatState() { return state; }
    @Override public void setRatState(int p) { this.state = p; }
}
