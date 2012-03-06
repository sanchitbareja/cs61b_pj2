/*Square class*/

public class Square {

    public static final int INVALID = -1; //represents an invalid square
    public static final int EMPTY = 0; //represents empty square
    public static final int WHITE = 1; //represents a square containing white piece
    public static final int BLACK = 2; //represents a square containing black piece 

    public int type;

    public Gameboard board;

    /**
     * Square constructor() takes in three parameters and creates a valid 
     * Square object of type EMPTY.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param g the Gameboard object which contains the square
     */

    public Square(int x, int y, Gameboard g) {
    }
   
    /**
     * Square constructor() takes in five parameters and creates a Square object.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the type of the square when constructed, a -1 represents
     * INVALID, a 0 represents EMPTY, a 1 represents WHITE, a 2 represents BLACK
     * @param g the Gameboard object which contains the square
     */

    public Square(int x, int y, int type, Gameboard g) {
    }

    /**
     * getNeighbors() takes no parameters, and returns a Square array
     * of length 8 representing squares around a "this" Square.
     *
     * @return a Square[] of length 8 containing squares located orthogonally
     * and diagonally adjacent to "this" Square.
     */

    public Square[] getNeighbors() {
    }

    /**
     * getType() takes no parameters, and returns an int representing the type
     * of "this" Square.
     *
     * @return type of "this" Square, a 0 represents EMPTY, a 1 represents WHITE,
     * a 2 represents BLACK.
     */

    public int getType() {
    }

    /**
     * getX() takes no parameters, and returns an int representing the x-coordinate
     * of "this" Square.
     *
     * @return x-coordinate of "this" Square on the Gameboard.
     */

    public int getX() {
    }

    /**
     * getY() takes no parameters, and returns an int representing the y-coordinate
     * of "this" Square.
     *
     * @return y-coordinate of "this" Square on the Gameboard.
     */

    public int getY() {
    }

    /**
     * setType() takes one parameter, an integer representing the new type, and 
     * changes the type field of "this" Square to the new type.
     *
     * @param goal represents the type which "this" Square will be changed to. If
     * newType is the same as the type field as "this" Square, nothing is changed.
     */

    public void setType(int goal) {
    }

    /**
     * isValid() takes no parameters, and checks whether the type
     * field of "this" Square is INVALID.
     *
     * @return true if type of "this" Square is INVALID, false otherwise.
     */

    public boolean isValid() {
    }

    /**
     * isEmpty() takes no parameters, and checks whether the type 
     * field "this" Square is EMPTY.
     * 
     * @return true if type of "this" Square is EMPTY, false otherwise.
     */

    public boolean isEmpty() {
    }

    /**
     * isWhite() takes no parameters, and checks whether the type
     * field "this" Square is WHITE.
     *
     * @return true if type of "this" Square is WHITE, false otherwise.
     */

    public boolean isWhite() {
    }

    /**
     * isBlack() takes no parameters, and checks whether the type
     * field "this" Square is BLACK.
     *
     * @return true if type of "this" Square is BLACK, false otherwise.
     */

    public boolean isBlack() {
    }

    /**
     * getRow() takes no parameters, and returns an array of square objects
     * in the same row as "this" Square.
     *
     * @return a Square[] containing squares in the same row as "this" Square.
     */

    public Square[] getRow() {
    }

    /**
     * getColumn takes no parameters, and returns an array of square objects
     * in the same column as "this" Square.
     *
     * @return a Square[] containing squares in the same row as "this" Square.
     */

    public Square[] getColumn() {
    }

    /**
     * getDiagonalLength() takes no parameters, and returns an integer
     * representing the number of valid square objects in the same diagonal
     * as "this" regardless of direction.
     *
     * @return an integer representing the length of the diagonal
     */

    public getDiagonalLength() {
    }

    /**
     * getDiagonal() takes one parameter, a direction, and returns
     * an array of square objects in the same diagonal as "this"
     * Square.
     *
     * @param direction is an integer representing the direction of the diagonal to
     * return. More specifically, a -1 represents a northwest-southeast diagonal, 
     * and a 1 represents a northeast-southwest diagonal.
     *
     * @return a Square[] containing squares in the same diagonal as "this" Square.
     */

    public Square[] getDiagonal(int direction) {
    }


    /**
     * findConnectingChips() returns a list of square objects 
     * that "this" Square is "connected" to.
     *
     * @return a Square[] containing chips that "this" Square is connected to
     */

    public Square[] findConnectingChips() {
    }

    /**
     * toString() returns a string representation of "this" Square.
     *
     * @return a string representing "this" Square
     */

    public String toString() {
    }
}
