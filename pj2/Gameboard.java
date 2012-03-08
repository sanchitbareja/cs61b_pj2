/* Gameboard */

public class Gameboard {

    public static final int INVALID = -1; //represents an invalid square
    public static final int EMPTY = 0; //represents empty square
    public static final int WHITE = 1; //represents a square containing white piece
    public static final int BLACK = 2; //represents a square containing black piece 

    protected int[][] board;

    /**
     * getNeighbors() takes 2 parameters - namely, the coordinate - and returns a 2 x 2 2-D square array
     * of representing squares around the given square.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a 2 x 2 int[][] containing squares located orthogonally
     * and diagonally adjacent to the square at (x,y).
     */

    public int[][] getNeighbors(int x, int y) {
    }

    /**
     * getType() takes 2 parameters, and returns an int representing the type
     * of the square.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return type of the square, a 0 represents EMPTY, a 1 represents WHITE,
     * a 2 represents BLACK.
     */

    public int getType(int x, int y) {
    }

    /**
     * setType() takes 3 parameters, the coordinates, and an integer representing the new type, and 
     * changes the type of the square to the new type.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param goal represents the type which the square will be changed to. If
     * newType is the same as the type of the square, nothing is changed.
     */

    public void setType(int x, int y, int goal) {
    }

    /**
     * isValid() takes 2 parameters, the coordinates, and checks whether the type
     * of square is INVALID.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if type of the square is INVALID, false otherwise.
     */

    public boolean isValid(int x, int y) {
    }

    /**
     * isEmpty() takes 2 parameters, the coordinates, and checks whether the type 
     * of square is EMPTY.
     * 
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if type of the square is EMPTY, false otherwise.
     */

    public boolean isEmpty(int x, int y) {
    }

    /**
     * isWhite() takes 2 parameters, the coordinate, and checks whether the type
     * of square is WHITE.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if type of the square is WHITE, false otherwise.
     */

    public boolean isWhite(int x, int y) {
    }

    /**
     * isBlack() takes 2 parameters, the coordinates, and checks whether the type
     * of square is BLACK.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if type of square is BLACK, false otherwise.
     */

    public boolean isBlack(int x, int y) {
    }

    /**
     * getRow() takes 1 parameters, the x-coordainte, and returns an array of square ints
     * in the same row as x.
     *
     * @param x the x-coordinate of the row
     *
     * @return a int[] containing squares in the same row as int x.
     */

    public int[] getRow(int x) {
    }

    /**
     * getColumn takes 1 parameters, the y-coordinate, and returns an array of square ints
     * in the same column as the y.
     *
     * @param y the y-coordinate of the column
     *
     * @return a int[] containing squares in the same column as int y.
     */

    public int[] getColumn(int y) {
    }

    /**
     * getDiagonalLength() takes 2 parameters, the coordinates, and returns an integer
     * representing the number of valid square ints in the same diagonal
     * as "this" regardless of direction.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return an integer representing the length of the diagonal
     */

    public getDiagonalLength(int x, int y) {
    }

    /**
     * getDiagonal() takes 3 parameters, the coordiantes, a direction, and returns
     * an array of square objects in the same diagonal as the square
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param direction is an integer representing the direction of the diagonal to
     * return. More specifically, a -1 represents a northwest-southeast diagonal, 
     * and a 1 represents a northeast-southwest diagonal.
     *
     * @return a int[] containing squares in the same diagonal as the square.
     */

    public int[] getDiagonal(int x, int y, int direction) {
    }


    /**
     * findConnectingChips() takes 2 parameters, the coordinates, and returns a list of square ints 
     * that the square is "connected" to.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a int[] containing chips that is connected to given square
     */

    public int[] findConnectingChips(int x, int y) {
    }

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

    private void removePiece(int x, int y) {
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
     * isValidMove() takes one parameter, and checks if "move" 
     * can be legally made based on the game rules.
     * 
     * @param m is the move in question.
     *
     * @return true if the move can be legally made, false otherwise.
     */

    public boolean isvalidMove(Move m) {
    }
    
    /**
     * listMoves() takes one parameter, the type of the current player, and 
     * returns an array of all possible moves.
     *
     * @param player is the type of the player in consideration
     *
     * @return a Move[] containing all the possible moves possible for "player"
     * "this" Gameboard.
     */

    public Move[] listMoves(int player) {
    }

    /**
     * containsNetwork() takes one parameter, the type of the current "player", and
     * returns whether or not a network could be established with the "player"'s
     * pieces on "this" Gameboard
     *
     * @param player is the type of the player in consideration
     *
     * @return true if the current set of pieces contains a Network, false otherwise
     */

    public boolean containsNetwork(int player) {
    }

    /**
     * toString() returns a String representation of the board.
     */
    public String toString() {
    }

        



