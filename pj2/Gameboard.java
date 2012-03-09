/* Gameboard */

import java.util.*;
import player.*;

public class Gameboard {

    public static final int INVALID = -1; //represents an invalid square
    public static final int EMPTY = 0; //represents empty square
    public static final int WHITE = 1; //represents a square containing white piece
    public static final int BLACK = 2; //represents a square containing black piece 

    protected int[][] board;

    /**
     * getNeighbors() takes 2 parameters - namely, the coordinate - and returns a 3 x 3 2-D square array
     * of representing squares around the given square.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a 3 x 3 int[][] containing squares located orthogonally
     * and diagonally adjacent to the square at (x,y). If square is at the side/corner, it fills 
     * remainig 3X3 array with -1s
     */

    public int[][] getNeighbors(int x, int y) {
	if(x >= 0 && x<= 7 && y >= 0 && y <= 7){
	    int toReturn[][] = new int[3][3];
	    toReturn[0][0] = get00(x,y);
	    toReturn[0][1] = get01(x,y);
	    toReturn[0][2] = get02(x,y);
	    toReturn[1][0] = get10(x,y);
	    toReturn[1][1] = this.board[x][y];
	    toReturn[1][2] = get12(x,y);
	    toReturn[2][0] = get20(x,y);
	    toReturn[2][1] = get21(x,y);
	    toReturn[2][2] = get22(x,y);
	} else {
	    System.out.println("Invalid x,y given in getNeighbors x: "+x+" y: "+y+" .");
	    int toReturn[][] = new int[3][3];
	    for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
		    toReturn[i][j] = INVALID;
		}
	    }
	    return toReturn;
	}
    }
    
    //helper methods for getNeighbors
    private int get00(int x, int y){
	if(x - 1 >= 0 && y - 1 >= 0){
	    return this.board[x-1][y-1];
	} else {
	    return INVALID;
	}
    }

    private int get10(int x, int y){
	if(y-1 >= 0){
	    return this.board[x][y-1];
	} else {
	    return INVALID;
	}
    }

    private int get20(int x, int y){
	if(x + 1 <= 7 && y-1 >= 0){
	    return this.board[x+1][y-1];
	} else {
	    return INVALID;
	}
    }

    private int get01(int x, int y){
	if(x - 1 >= 0){
	    return this.board[x-1][y];
	} else {
	    return INVALID;
	}
    }

    private int get21(int x, int y){
	if(x+1 <= 7){
	    return this.board[x+1][y];
	} else {
	    return INVALID;
	}
    }

    private int get02(int x, int y){
	if(x-1 >= 0 && y+1 <= 7){
	    return this.board[x-1][y+1];
	} else {
	    return INVALID;
	}
    }

    private int get12(int x, int y){
	if(y+1 <= 7){
	    return this.board[x][y+1];
	} else {
	    return INVALID;
	}
    }

    private int get22(int x, int y){
	if(x+1 <= 7 && y+1<=7){
	    return this.board[x+1][y+1];
	} else {
	    return INVALID;
	}
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
	return this.board[x][y];
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

    private void setType(int x, int y, int goal) {
	this.board[x][y] = goal;
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
	if(getType(x,y) == INVALID){
	    return true;
	} else {
	    return false;
	}
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
	if(getType(x,y) == EMPTY){
	    return true;
	} else {
	    return false;
	}
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
	if(getType(x, y) == WHITE){
	    return true;
	} else {
	    return false;
	}
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
	if(getType(x, y) == BLACK){
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * getColumn() takes 1 parameters, the x-coordainte, and returns an array of square ints
     * in the same row as x.
     *
     * @param x the x-coordinate of the row
     *
     * @return a int[] containing squares in the same row as int x.
     */

    public int[] getColumn(int x) {
	int columnChips[] = new int[8];
	for(int j = 0; j < this.height; j++){
	    columnChips[i] = this.board[x][j];
	}
	return columnChips;
    }

    /**
     * getRow() takes 1 parameters, the y-coordinate, and returns an array of square ints
     * in the same column as the y.
     *
     * @param y the y-coordinate of the column
     *
     * @return a int[] containing squares in the same column as int y.
     */

    public int[] getRow(int y) {
	int rowChips[] = new int[8];
	for(int i = 0; i < this.row; i++){
	    rowChips[i] = this.board[i][y];
	}
	return rowChips;
    }

    /**
     * getDiagonalLength() takes 3 parameters, the coordinates, the direction , and returns
     * and integer representing the number of squares in the same diagonal
     * as the square regardless of direction.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param direction is an integer representing the direction of the diagonal to
     * return. More specifically, a -1 represents a northwest-southeast diagonal, 
     * and a 1 represents a northeast-southwest diagonal
     *
     * @return an integer representing the length of the diagonal
     */

    private int getDiagonalLength(int x, int y, int direction) {
	//this method uses a formula to compute the number of diagnol chips
	int len = x + y + 1;
	if(direction == -1){
	    len = this.height - y + x;
	    if(len > this.height){
		len = this.height - len%this.height;
	    }
	} else if(direction == 1){
	    if(len > this.height){
		len = this.height - len%this.height;
	    }
	}
	return len;
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
	int diagnolLength = getDiagnoalLength(x,y,direction);
	int diagnolChips[] = new int[diagnolLength];
	int startX = x;
	int startY = y;
	if(direction == -1){
	    if(x > y){
		startY = 0;
		startX = x-y;
	    } else if(y > x){
		startX = 0;
		startY = y-x;
	    } else {
		startX = 0;
		startY = 0;
	    }
	    int xy = 0;
	    while(startX < this.height && startY < this.height && xy < diagnolLength){
		diagnolChips[xy] = this.board[x][y];
		startX += 1;
		startY += 1;
		xy += 1;
	    }
	} else if(direction == 1){
	    if(x + y > 7){
		startX = 7;
		startY = x + y - this.height + 1;
	    } else {
		startY = 0;
		startX = x + y;
	    }
	    int xy2 = 0;
	    while(startX > 0 && startY < this.height && xy2 M diagnolLength){
		diagnolChips[xy2] = this.board[x][y];
		startX -= 1;
		startY += 1;
		xy2 += 1;
	    }
	}
	return diagnolChips;
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

    protected int width;
    protected int height;
    
    /**
     * Gameboard constructor takes no parameters, and constructs
     * a default 8 x 8 2-D array of integers.
     */
    public Gameboard() {
        //fills all squares with EMPTY
        this.width = 8;
        this.height = 8;
        int[][] board = new int[width][height];
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                board[i][j] = EMPTY;
            }
        }
        //sets corner squares (0,0), (0,7), (7,0), (7,7) to INVALID
        board[0][0] = INVALID;
        board[0][7] = INVALID;
        board[7][0] = INVALID;
        board[7][7] = INVALID;
    }

    /**
     * Gameboard constructor takes two parameters, and constructs
     * a width * height 2-D array of integers.
     */
    public Gameboard(int width, int height) {
        //fills all squares with EMPTY
        this.width = width;
        this.height = height;
        int[][] board = new int[width][height];
        for (int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                board[i][j] = EMPTY;
            }
        }
        //sets corner squares (0,0), (0,7), (7,0), (7,7) to INVALID
        board[0][0] = INVALID;
        board[0][height - 1] = INVALID;
        board[width - 1][0] = INVALID;
        board[width - 1][width - 1] = INVALID;
    }

    /**
     * getWhiteCount() takes no parameters returns the number of white 
     * pieces remaining for "this" Gameboard.
     *
     * @return number of white pieces
     */

    public int getWhiteCount() {
        return whiteCount;
    }

    /**
     * getBlackCount() takes no parameters and returns the number of black 
     * pieces remaining for "this" Gameboard.
     *
     * @return number of black pieces
     */

    public int getBlackCount() {
        return blackCount;
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
        if (condition) throw new AgainstRulesException("")
        if (goal == WHITE && this.whiteCount > 0) {
            this.setType(x, y, WHITE);
            this.whiteCount--;
        }
        if (goal == BLACK && this.blackCount > 0) {
            this.setType(x, y, BLACK);
            this.blackCount--;
        }
    }

    /**
     * removePiece(), takes two parameters, and changes the type of the 
     * indicated square on "this" Gameboard at the coordinates (x,y) to EMPTY.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */

    private void removePiece(int x, int y) {
        this.setType(x,y,EMPTY);
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
        this.setType(x2, y2, this.getType(x1,y1));
        this.setType(x1, y1, this.getType(x2, y2));
    }

    /**
     * clearBoard() changes all squares with the exception of corner squares
     * to EMPTY for testing purposes.
     */

    private void clearBoard() {
        this = new Gameboard();
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
        System.out.println("   |  0 |  1 |  2 |  3 |  4 |  5 |  6 |  7 |");
        System.out.println("   -----------------------------------------");
        for(int i = 0; i < width; i++) {
            String[] printColumn = new String[height];
            for(int j = 0; j < height; j++) {
                switch(getType(i,j)) {
                    case INVALID:
                        printColumn[j] = "----";
                        break;
                    case EMPTY:
                        printColumn[j] = "    ";
                        break;
                    case WHITE:
                        printColumn[j] = " WW ";
                        break;
                    case BLACK:
                        printColumn[j] = " BB ";
                        break;
                    default:
                        printColumn[j] = " ERR";
                }
            }
            for(int k = 0; k < printColumn.length; k++) {
                System.out.println(k + " |");
                System.out.print(printColumn[k] + '|');
                System.out.println("   -----------------------------------------");
            }

        }
    }

    public static void main(String args[]) {
        Gameboard testGame = new Gameboard();
        System.out.println(testGame);

        //testing board dimensions
        try {
            assert testGame.width == 8: "ERROR (Y): width of gameboard incorrect";
            assert testGame.height == 8: "ERROR (Y): height of gameboard incorrect";

            //testing board corner square invariants (Rule #1)
            assert testGame.isValid(0,0) == false: "ERROR (S): invalid squares are valid";
            assert testGame.isValid(0,7) == false: "ERROR (S): invalid squares are valid";
            assert testGame.isValid(7,0) == false: "ERROR (S): invalid squares are valid";
            assert testGame.isValid(7,7) == false: "ERROR (S): invalid squares are valid";

            //testing addPiece() without neighbor conflict
            testGame.addPiece(6,0,BLACK);
            testGame.addPiece(6,5,BLACK);
            testGame.addPiece(5,5,BLACK);
            testGame.addPiece(3,3,BLACK);
            testGame.addPiece(3,5,BLACK);
            testGame.addPiece(5,7,BLACK);

            assert testGame.getType(6,0) == BLACK: "ERROR (Y): square should be BLACK"
            assert testGame.getType(6,5) == BLACK: "ERROR (Y): square should be BLACK"
            assert testGame.getType(5,5) == BLACK: "ERROR (Y): square should be BLACK"
            assert testGame.getType(3,3) == BLACK: "ERROR (Y): square should be BLACK"
            assert testGame.getType(3,5) == BLACK: "ERROR (Y): square should be BLACK"
            assert testGame.getType(5,7) == BLACK: "ERROR (Y): square should be BLACK"

            assert testGame.blackCount == 4: "ERROR: blackCount is incorrect";
        } catch(Exception e) {
            System.out.println(e);
        }

    }
}


        



