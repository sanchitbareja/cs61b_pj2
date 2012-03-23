package player;

public class Coordinate {
	
	public final int x;
	public final int y; 

    /**
    * the Coordinate constructor takes in an x and a y value and makes the into a coordinate.
    */
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

    //implementation of .equals for Coordinate
	public boolean equals(Object other) {
        Coordinate coord = (Coordinate)other;
		return this.x == coord.x && this.y == coord.y;
	}

    //returns true if and only if every coordinate in array a is equal to the corresponding coordinate in array b.
    public static boolean deepEquals(Coordinate[] a, Coordinate[] b) {
        //IMPORTANT! the arrays must be the same length.
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    //returns a string representation of the Coordinate.
    public String toString() {
    	return "(" + this.x + ", " + this.y + ")"; 
    }
}
