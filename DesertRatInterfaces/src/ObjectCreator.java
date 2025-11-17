public class ObjectCreator {
    public static interfacePackage.CellInterface createNewCell(int r,int c){ return new Cell(r,c); }
    public static interfacePackage.HoleInterface createNewHole(int r,int c){ return new Hole(r,c); }
    public static interfacePackage.RatInterface createNewRat(){ return new Rat(); }
    public static interfacePackage.RatStatusInterface createNewRatStatus(String id,int state){ return new RatStatus(id,state); }
    public static interfacePackage.DesertInterface createNewDesert(){ return new Desert(); }
}
