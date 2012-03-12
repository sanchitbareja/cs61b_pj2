public class Coordinate {
	
	public final int x;
	public final int y; 

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean equals(Coordinate other) {
		return this.x == other.x && this.y == other.y;
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
