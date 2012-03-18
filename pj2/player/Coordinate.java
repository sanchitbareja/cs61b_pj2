package player;

public class Coordinate {
	
	public final int x;
	public final int y; 

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Object other) {
        Coordinate coord = (Coordinate)other;
		return this.x == coord.x && this.y == coord.y;
	}

    public static boolean deepEquals(Coordinate[] a, Coordinate[] b) {
        //IMPORTANT! the arrays must be the same length.
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
    	return "(" + this.x + ", " + this.y + ")"; 
    }
}
