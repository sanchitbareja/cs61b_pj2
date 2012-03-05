/* Gameboard */

public class Gameboard {

    protected int[][] board;

    public static final int WHITE = 1; //represents a white piece
    public static final int BLACK = 2; //represents a black piece 
    public static final int TOTAL = 10; //represents total allotted pieces per player

    protected int whiteCount;
    protected int blackCount;
    
    /**
     * Gameboard constructor takes no parameters, and constructs
     * an 8 x 8 2-D array of integers.
     */
    public Gameboard() {
    }

    /**
     * getWhiteCount() takes no parameters returns the number of white pieces remaining.
     *
     * @return number of white pieces
     */

    public int getWhiteCount() {
        return whitePieces;
    }

    /**
     * getBlackCount() returns the number of black pieces remaining.
     *
     * @return number of black pieces
     */

    public int getBlackCount() {
        return blackPieces;
    }

    /**
     * neighbors() takes in a coordinate (int x, int y), as parameters
     * and returns a 3 x 3 2-D array of integers representing pieces
     * around a specified square on "this" Gameboard.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a 3 x 3 two-dimensional array of integers representing pieces
     * orthogonally and diagonally adjacent to the square (x,y)
     */

    public int[][] neighbors(int x, int y) {
    }

    /**
     * verify() takes in a coordinate (int x, int y), as parameters
     * and returns whether or not the coordinate is a valid square on "this"
     * Gameboard.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if the square is a valid square on the network gameboard described
     * in the README file; false otherwise.
     */

    public boolean verify(int x, int y) {
       return true;
    }

    /**
     * cellContent() returns the an integer that indicates the piece
     * at the coordinate specified by parameters (int X, int Y).
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * 
     * @return an integer representing the blac
     */

    public int cellContent(int x, int y) {
    }

    /**
     * isEmpty() checks whether or not the indicated coordinate
     * specified by parameters (int x, int y) is empty.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if the square on "this" Gameboard is empty, false otherwise.
     */

    public boolean isEmpty(int x, int y) {
        return true;
    }

    /**
     * addPiece() puts a piece at the indicated square on "this" Gameboard specified
     * by parameters (int x, int y).
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */

    public void addPiece(int x, int y) {
    }

    /**
     * removePiece() removes a piece at the indicated square on "this" Gameboard
     * specified by parameters (int x, int y).
     * 
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */

    private void removePiece(int x, int y) {
    }

    /**
     * switchPiece() moves a piece from one coordinate to another.
     * More specifically, it takes parameters (x1, y1, x2, y2) and
     * moves a piece on "this" Gameboard from (x1, y1) to (x2, y2).
     *
     * Unusual conditions: 
     * if the piece specified at coordinate (x1, y1) does not exist,
     * nothing is done.
     *
     * @param x1 the x-coordinate of the square containing the piece
     * @param y1 the y-coordinate of the square containing the piece
     * @param x2 the x-coordinate of the square the piece is to be moved to
     * @param y2 the y-coordinate of the square the piece is to be moved to 
     */

    public void switchPiece(int x1, int y1, int x2, int y2) {
    }

    /**
     * getRow() returns an integer array of square contents  in the same row
     * as the square, inclusive  at the indicated coordinate specified by parameters
     * (int x, int y).
     *
     * @param x1 the x-coordinate of the square
     * @param y1 the y-coordinate of the square
     *
     * @return an int[], containing  
     */

    public int[] getRow(int x, int y) {
    }

    /**
     * getColumn() returns an integer array of pieces in the same column
     * as the piece at the indicated coordinate specified by parameters
     * (int x, int y).
     */

    public int[] getColumn(int x, int y) {
    }

    /**
     * getDiagonal() returns an integer array of pieces in the same
     * diagonal as the piece and cardinality at the indicated
     * coordinate and direction specified by parameters (int X, int Y,
     * int Z). A 0 for int Z represents a diagonal from the northwestern
     * part of the board. A 1 for int Z represents a diagonal from the
     * northeastern part of the board.
     */

    public int[] diagonal(int x, int y, int z) {
    }

    /**
     * toString() returns a String representation of the board.
     */
    public String toString() {
    }

        



