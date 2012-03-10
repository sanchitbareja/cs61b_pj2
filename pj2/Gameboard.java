/* Gameboard */

import java.util.*;
import player.*;

public class Gameboard {

    protected int[][] board;

    public static final int INVALID = -1; //represents an invalid square
    public static final int EMPTY = 0; //represents empty square
    public static final int WHITE = 1; //represents a square containing white piece
    public static final int BLACK = 2; //represents a square containing black piece 

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
        this.whiteCount = 10;
        this.blackCount = 10;

        this.board = new int[width][height];
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
        this.whiteCount = 10;
        this.blackCount = 10;
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
        int toReturn[][] = new int[3][3];
    	if(x >= 0 && x<= 7 && y >= 0 && y <= 7){
    	    //int toReturn[][] = new int[3][3];
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
    	    //int toReturn[][] = new int[3][3];
    	    for(int i = 0; i < 3; i++){
        		for(int j = 0; j < 3; j++){
        		    toReturn[i][j] = INVALID;
        		}
    	    }
    	}
        return toReturn; 
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
    	    return false;
    	} else {
    	    return true;
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
    	    columnChips[j] = this.board[x][j];
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
    	for(int i = 0; i < this.width; i++){
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
	//this method uses a formula to compute the number of diagonal chips
	int len = x + y + 1;
    	if(direction == -1){
    	    len = this.height - y + x;
    	    if(len > this.height){
    		  len = this.height - len%this.height;
    	    }
    	} else if(direction == 1){
    	    if(len > this.height){
    		  len = this.height - len % this.height;
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
    	int diagonalLength = getDiagonalLength(x,y,direction);
    	int diagonalChips[] = new int[diagonalLength];
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
    	    while((startX < this.height && startY < this.height) && xy < diagonalLength){
        		diagonalChips[xy] = this.board[x][y];
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
    	    while((startX > 0 && startY < this.height) && xy2 < diagonalLength){
        		diagonalChips[xy2] = this.board[x][y];
        		startX -= 1;
        		startY += 1;
        		xy2 += 1;
    	    }
    	}
    	return diagonalChips;
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
        int[] connectedChips = new int[1];
        return connectedChips;
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

    public void addPiece(int x, int y, int goal) throws AgainstRulesException {
        if (check(x,y) && (goal == WHITE || goal == BLACK)) { //checks if (x,y) are valid, goal WHITE || BLACK
                }
        }

    /*
    ****************************************************
    *            addPiece() Helper Methods             *
    ****************************************************
    */

    /**
     * checkDimensions(), takes two parameters, the x-coordinate, and y-coordinate, and verifies
     * that they are within the dimensions of this.Gameboard.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if the coordinate satisfies the dimensions of the board, false otherwise.
     */

    private boolean checkDimensions(int x, int y) {
        if ((x < this.width && x >= 0) && (y < this.height && y >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkSquare(), takes three parameters, the x-coordinate,  y-coordinate, and a piece, and verifies
     * that the square is not paired with a coordinate where the piece is not allowed. If the goal is INVALID, automatically
     * returns false. An INVALID is not allowed anywhere! If the coordinates is at an INVALID spot, also returns false.
     * 
     * (RULE #1 and #2)
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param goal the piece to be inspected at (x,y)
     *
     * @return true if the piece can legitimately be placed on (x,y), false otherwise.
     */

    private boolean checkSquare(int x, int y, int type) {
        if (isValid(x,y) && getType(type) != INVALID) { //if (x,y) is valid, and the type of goal is valid
            if (getType(goal) == EMPTY) { //an EMPTY can be put anywhere, except the invalid squares, which have been checked
                return true;
            }
            if (getType(type) == BLACK) { //a BLACK can not be placed on the left/right edges
                if (x != 0 && x != (this.width - 1)) { //if x-coordinate is not 0 and its not width - 1
                    return true;
                }
            }
            if (getType(type) == WHITE) { //a WHITE can not be placed on the top/bottom edges
                if (y != 0 && y != (this.height - 1)) { //if x-coordinate is not 0 and its not width - 1
                    return true; 
                }
            }
        } else {
            return false;
        }
    }

    /**
     * pieceCheck(), takes one parameter, a type of a piece, returns whether or not the type is {BLACK, WHITE}
     * 
     * (RULE #1 and #2)
     * @param goal the piece to be inspected
     *
     * @return true if the piece is WHITE or BLACK, false otherwise.
     */

    private boolean pieceCheck(int goal) {
        if (goal == WHITE || goal == BLACK) {
            return true;
        }
        return false;
    }

    /**
     * countNeighbor() takes in three parameters, an x-coordinate and y-coordinate, and a type and return the number of neighbors
     * around it, excluding itself.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param goal the type of the piece to be inspected
     *
     * @return the number of neighbors surrounding the specified piece, excluding itself,
     */

    private int countNeighbor(int x, int y, int type) {
        int[][] neighbors = getNeighbors(x, y, type);
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (getType(i, j) == type) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * locateNeighbor() takes in three parameters, an x-coordinate, y-coordinate on the board and a type, adjusts 
     * the coordinates of 2-D array returned from getNeighbor() to overlay the coordinates of the piece on this.Gameboard.
     *
     * @param x1 the x-coordinate of the square on the gameboard
     * @param y1 the y-coordinate of the square on the gameboard
     * @param type the type of the square we are inspecting
     *
     * @return an array of length 2, containing the coordinates of the neighbor on the this.Gameboard. The first index is the x-coordinate,
     * and the second index is the y-coordinates.
     */

    private int locateNeighbor(int x, int y, int type) {
        int[] neighbors = getNeighbors(x, y, type);
        int[] location = new int[2];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i != 1 && j != 1) && getType(i, j) == type) {
                    count[0] = x + i - 1;
                    count[1] = y + j - 1; 
                }
            }
        }
        return count;
    }


    /**
     * neighborCheck() takes in three parameters, an x-coordinate and y-coordinate, and a piece and checks whether placing the piece there
     * would violate the rule that a player may not have more than two chips in a connected group, whether connected
     * orthogonally or diagonally.
     * (RULE #4)
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param goal the piece to be inspected
     *
     * @return true if the piece is can be placed according to the rules, false otherwise.
     */

    private boolean neighborCheck(int x, int y, int type) {
        if squareCheck() {
            if (neighborCount(x,y,type) == 0) {
                return true;
            }
            if (neighborCount(x,y,type) == 1) {
                int[] location = oneNeighborLocator(x,y,type);
                if (neighborCount(location[0], location[1]) <= 0) {
                    return true;
                }
            }
            return false;
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
        this.setType(x, y, EMPTY);
    }

    /**
     * swapPiecess() "moves" a piece from one coordinate to another.
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

    public void swapPieces(int x1, int y1, int x2, int y2) {
        this.setType(x2, y2, this.getType(x1,y1));
        this.setType(x1, y1, this.getType(x2, y2));
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
        return true;
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
        Move[] list = new Move[1];
        return list;
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
        return true;
    }

    /**
     * toString() returns a String representation of the board.
     */
    public String toString() {
        String current = "      0    1    2    3    4    5    6    7  " + "\n" + "   -----------------------------------------";
        for(int j = 0; j < this.height; j++) {
            String[] printColumn = new String[this.width];
            for(int i = 0; i < this.width; i++) {
                int typeAt = getType(i,j);
                //System.out.println(typeAt + " at coordinates (" + i + ", " + j + ")");
                switch(typeAt) {
                    case INVALID:
                        printColumn[i] = "XXXX";
                        break;
                    case EMPTY:
                        printColumn[i] = "    ";
                        break;
                    case WHITE:
                        printColumn[i] = " WW ";
                        break;
                    case BLACK:
                        printColumn[i] = " BB ";
                        break;
                    default:
                        printColumn[i] = "EROR";
                }
            }
            current += "\n" + j + "  |";
            for(int k = 0; k < printColumn.length; k++) {
                current += printColumn[k] + '|';
            }
            current += "\n" + "   -----------------------------------------";
        }
        return current;
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

            System.out.println(testGame);

            assert testGame.getType(6,0) == BLACK: "ERROR (Y): square should be BLACK";
            assert testGame.getType(6,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert testGame.getType(5,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert testGame.getType(3,3) == BLACK: "ERROR (Y): square should be BLACK";
            assert testGame.getType(3,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert testGame.getType(5,7) == BLACK: "ERROR (Y): square should be BLACK";

            assert testGame.blackCount == 4: "ERROR: blackCount is incorrect";



            System.out.println("All tests so far have passed!");
        } catch(Exception e) {
            System.out.println(e);
        }

    }
}


        



