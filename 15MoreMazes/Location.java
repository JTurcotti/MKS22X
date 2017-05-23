public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public boolean within(Maze m) {
	return x>=0 && y>=0 &&
	    x<m.maxRows && y<m.maxCols;
    }
	    
    @Override
    public boolean equals(Object objOther) {
	if (!(objOther instanceof Location)) return false;
	Location other = (Location) objOther;
	return (other.x == this.x && other.y == this.y);
    }

    @Override
    public int hashCode(){
	return x<<8 + y;
    }
    
    @Override
    public String toString() {
	return "(" + x + ", " + y + ")";
    }
    
    public int distanceTo(Location other) {
	return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}

