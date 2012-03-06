/* Gameboard */

public class Gameboard {

    protected Square[][] board;
    
    public static final int TOTAL = 10; //represents total allotted pieces per player
    public static final int WHITEPLAYER = 1;
    public static final int BLACKPLAYER = 2;

    protected int whiteCount;
    protected int blackCount;
    
    /**
     * Gameboard constructor takes no parameters, and constructs
     * an 8 x 8 2-D array of integers.
     */
    public Gameboard() {
    }

    /**
     * getWhiteCount() takes no parameters returns the number of white 
     * pieces remaining for "this" Gameboard.
     *
     * @return number of white pieces
     */

    public int getWhiteCount() {
        return whitePieces;
    }

    /**
     * getBlackCount() takes no parameters and returns the number of black 
     * pieces remaining for "this" Gameboard.
     *
     * @return number of black pieces
     */

    public int getBlackCount() {
        return blackPieces;
    }

    /**
     * addPiece(), takes three parameters, and changes the type of the
     * indicated square on "this" Gameboard at the coordinates (x,y) to
     * the specified type.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param goal the new type of the square
     */

    public void addPiece(int x, int y, int goal) {
    }

    /**
     * removePiece(), takes two parameters, and changes the type of the 
     * indicated square on "this" Gameboard at the coordinates (x,y) to EMPTY.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */

    public void removePiece(int x, int y) {
    }

    /**
     * switchPieces() "moves" a piece from one coordinate to another.
     * More specifically, it takes parameters (x1, y1, x2, y2) and
     * moves a piece on "this" Gameboard from (x1, y1) to (x2, y2),
     * and moves the piece at (x2, y2) to (x1, y1).
     *
     * Unusual conditions: 
     * if the piece specified at coordinate (x1, y1) does not exist,
     * nothing is done.
     *
     * @param x1 the x-coordinate of the square containing the piece
     * @param y1 the y-coordinate of the square containing the piece
     * @param x2 the x-coordinate of the second square to swap with
     * @param y2 the y-coordinate of the second square to swap with
     */

    public void switchPiece(int x1, int y1, int x2, int y2) {
    }

    /**
     * toString() returns a String representation of the board.
     */
    public String toString() {
    }

        



