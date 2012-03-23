/* Gameboard */

package player;

import java.util.*;
//import player.*;
import dict.*;
import list.*; 

public class Gameboard {

    protected int[][] board;

    public static final int INVALID = -1; //represents an invalid square
    public static final int EMPTY = 0; //represents empty square
    public static final int WHITE = 1; //represents a square containing white piece
    public static final int BLACK = 2; //represents a square containing black piece 

    public static final int TOTAL = 10; //represents total allotted pieces per player
    public static final int WHITEPLAYER = 1;
    public static final int BLACKPLAYER = 2;

    public static final int BOARD_WIDTH = 8;
    public static final int BOARD_HEIGHT = 8;

    public static final int MIN_DEPTH = 6; //minimum chips to form a network

    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST = 2;
    public static final int WEST = 3;
    public static final int NW = 4;
    public static final int NE = 5;
    public static final int SW = 6;
    public static final int SE = 7;
    public static final int NULLDIRECTION = -1;

    public static final int[] DIRECTIONS = {WEST, EAST, NORTH, SOUTH, NW, SE, NE, SW, NULLDIRECTION}; //this is the order in which connecting chips are checked.
    public static final int[] OPPOSITES = {EAST, WEST, SOUTH, NORTH, SE, NW, SW, NE, NULLDIRECTION};

    protected int whiteCount;
    protected int blackCount;

    protected int width;
    protected int height;

    public HashTableChained table = new HashTableChained(1000);
    
    /**
     * Gameboard constructor takes no parameters, and constructs
     * a default 8 x 8 2-D array of integers.
     */
    public Gameboard() {
        //fills all squares with EMPTY
        this.width = BOARD_WIDTH;
        this.height = BOARD_HEIGHT;
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

    public int getTypeCount(int type) {
        if (type == BLACK) {
            return getBlackCount();
        }
        if (type == WHITE) {
            return getWhiteCount();
        } else {
            return -1;
        }
    }

    /**
     * getNeighbors() takes one parameter, a Coordinate, and returns a 3 x 3 2-D square array
     * of representing squares around the given square.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return a 3 x 3 int[][] containing squares located orthogonally
     * and diagonally adjacent to the square at (x,y). If square is at the side/corner, it fills 
     * remainig 3X3 array with -1s
     */

    private int[][] getNeighbors(Coordinate c) {
        int toReturn[][] = new int[3][3];
        if(c.x >= 0 && c.x<= this.width-1 && c.y >= 0 && c.y <= this.width-1){
            //int toReturn[][] = new int[3][3];
            toReturn[0][0] = get00(c);
            toReturn[0][1] = get01(c);
            toReturn[0][2] = get02(c);
            toReturn[1][0] = get10(c);
            toReturn[1][1] = INVALID;
            toReturn[1][2] = get12(c);
            toReturn[2][0] = get20(c);
            toReturn[2][1] = get21(c);
            toReturn[2][2] = get22(c);
        } else {
            System.out.println("Invalid Coordinate given in getNeighbors x: "+ c.x + " y: " + c.y + " .");
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
    private int get00(Coordinate c){
        if(c.x - 1 >= 0 && c.y - 1 >= 0){
            return this.board[c.x-1][c.y-1];
        } else {
            return INVALID;
        }
    }

    private int get10(Coordinate c){
        if(c.y-1 >= 0){
            return this.board[c.x][c.y-1];
        } else {
            return INVALID;
        }
    }

    private int get20(Coordinate c){
        if(c.x + 1 <= this.width-1 && c.y-1 >= 0){
            return this.board[c.x+1][c.y-1];
        } else {
            return INVALID;
        }
    }

    private int get01(Coordinate c){
        if(c.x - 1 >= 0){
            return this.board[c.x-1][c.y];
        } else {
            return INVALID;
        }
    }

    private int get21(Coordinate c){
        if(c.x+1 <= this.width-1){
            return this.board[c.x+1][c.y];
        } else {
            return INVALID;
        }
    }

    private int get02(Coordinate c){
        if(c.x-1 >= 0 && c.y+1 <= this.width-1){
            return this.board[c.x-1][c.y+1];
        } else {
            return INVALID;
        }
    }

    private int get12(Coordinate c){
        if(c.y+1 <= this.width-1){
            return this.board[c.x][c.y+1];
        } else {
            return INVALID;
        }
    }

    private int get22(Coordinate c){
        if(c.x+1 <= this.width-1 && c.y+1<=this.width-1){
            return this.board[c.x+1][c.y+1];
        } else {
            return INVALID;
        }
    }

    /**
     * getType() takes one parameter, a Coordinate, and returns an int representing the type
     * of the square.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return type of the square, a 0 represents EMPTY, a 1 represents WHITE,
     * a 2 represents BLACK.
     */

    public int getType(Coordinate c) {
       return this.board[c.x][c.y];
    }

    /**
     * setType() takes two parameter, a Coordinate, and an integer representing the new type, and 
     * changes the type of the square to the new type.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @param type represents the type which the square will be changed to. If
     * newType is the same as the type of the square, nothing is changed.
     */

    private void setType(Coordinate c, int type) {
       this.board[c.x][c.y] = type;
    }

    /**
     * isValid() takes one parameter, a Coordinate, and checks whether the type
     * of square is INVALID.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return true if type of the square is INVALID, false otherwise.
     */

    public boolean isValid(Coordinate c) {
        if(getType(c) != INVALID){
            return true;
        } else {
            return false;
        }
    }

    /**
     * isEmpty() takes one parameter, a Coordinate, and checks whether the type 
     * of square is EMPTY.
     * 
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return true if type of the square is EMPTY, false otherwise.
     */

    public boolean isEmpty(Coordinate c) {
        if(getType(c) == EMPTY){
            return true;
        } else {
            return false;
        }
    }

    /**
     * isWhite() takes one parameter, a Coordinate, and checks whether the type
     * of square is WHITE.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return true if type of the square is WHITE, false otherwise.
     */

    public boolean isWhite(Coordinate c) {
        if(getType(c) == WHITE){
            return true;
        } else {
            return false;
        }
    }

    /**
     * isBlack() takes one parameter, a Coordinate, and checks whether the type
     * of square is BLACK.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return true if type of square is BLACK, false otherwise.
     */

    public boolean isBlack(Coordinate c) {
        if(getType(c) == BLACK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * getColumn() takes one parameter, a Coordinate, and returns an array of square ints
     * in the same row as the coordinate.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return an int[] containing squares in the same row as int x.
     */

    private int[] getColumn(Coordinate c) {
        int columnChips[] = new int[8];
        for(int j = 0; j < this.height; j++){
            columnChips[j] = this.board[c.x][j];
        }

        return columnChips;
    }

    /**
     * getRow() takes 1 parameter, a Coordinate, and returns an array of square ints
     * in the same column as the y.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return an int[] containing squares in the same column as int y.
     */

    private int[] getRow(Coordinate c) {
        int rowChips[] = new int[8];
        for(int i = 0; i < this.width; i++){
            rowChips[i] = this.board[i][c.y];
        }

        return rowChips;
    }

    /**
     * getDiagonalLength() takes two parameters, a Coordinate, the direction , and returns
     * and integer representing the number of squares in the same diagonal
     * as the square regardless of direction.
     *
     * @param Coordinate c, representing the coordinate of the square
     * @param direction is an integer representing the direction of the diagonal to
     * return. More specifically, a -1 represents a northwest-southeast diagonal, 
     * and a 1 represents a northeast-southwest diagonal
     *
     * @return an integer representing the length of the diagonal
     */

    private int getDiagonalLength(Coordinate c, int direction) {
    //this method uses a formula to compute the number of diagonal chips
        int len = c.x + c.y + 1;
        if(direction == -1){
            len = this.height - c.y + c.x;
            if(len > this.height){
              len = this.height - len % this.height;
            }
        } else if(direction == 1){
            if(len > this.height){
              len = this.height - len % this.height;
            }
        }
        return len;
    }

    /**
     * getDiagonal() takes two parameters, the Coordinate object, a direction, and returns
     * an array of square objects in the same diagonal as the square
     *
     * @param Coordinate c, representing the coordinate of the square
     * @param direction is an integer representing the direction of the diagonal to
     * return. More specifically, a -1 represents a northwest-southeast diagonal, 
     * and a 1 represents a northeast-southwest diagonal.
     *
     * @return an int[] containing squares in the same diagonal as the square.
     */

    private int[] getDiagonal(Coordinate c, int direction) {
        int diagonalLength = getDiagonalLength(c, direction);
        int diagonalChips[] = new int[diagonalLength];
        int startX = c.x;
        int startY = c.y;
        if(direction == -1){
            if(c.x > c.y){
                startY = 0;
                startX = c.x-c.y;
            } else if(c.y > c.x){
                startX = 0;
                startY = c.y-c.x;
            } else {
                startX = 0;
                startY = 0;
            }
            int xy = 0;
            while((startX < this.width && startY < this.height) && xy < diagonalLength){
                diagonalChips[xy] = this.board[startX][startY];
                startX += 1;
                startY += 1;
                xy += 1;
            }
        } else if(direction == 1){
            if(c.x + c.y > this.width - 1){
                startX = this.width - 1;
                startY = c.x + c.y - this.width + 1;
            } else {
                startY = 0;
                startX = c.x + c.y;
            }
            int xy2 = 0;
            while((startX >= 0 && startY < this.height) && xy2 < diagonalLength){
                diagonalChips[xy2] = this.board[startX][startY];
                startX -= 1;
                startY += 1;
                xy2 += 1;
            }
        }
        return diagonalChips;
    }

    /**
     * findConnectedLDiagonal() takes one parameter, a Coordinate, and returns a list of connected squares of the same type in the left diagonal.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the left diagonal
     */

    private Coordinate[] findConnectedLDiagonal(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);
        //int[][] connectedChips = new int[2][2];
        int[] diagonal = getDiagonal(c, -1);
        //int[] diagonal = getDiagonal(c.x, c.y, -1);
        int length = diagonal.length;
        int location = -1;
        if (c.y >= c.x) {
            location = c.x;
        } else {
            location = c.y;
        }

        for (int i = 1; location - i >= 0; i++) {
            if (diagonal[location - i] == type) {
                connectedChips[0] = new Coordinate(c.x - i, c.y - i);
                //connectedChips[0][0] = c.x - i;
                //connectedChips[0][1] = c.y - i;
                break;
            }
            if (diagonal[location - i] != type && checkPiece(diagonal[location - i])) {
                break;
            }
        }
        for (int i = 1; i + location < length; i++) {
            if (diagonal[i + location] == type) {
                connectedChips[1] = new Coordinate(c.x + i, c.y + i);
                //connectedChips[1][0] = c.x + i;
                //connectedChips[1][1] = c.y + i;
                break;
            }
            if (diagonal[location + i] != type && checkPiece(diagonal[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedRDiagonal() takes one parameter, a Coordinate, and returns a list of connected squares of the same type in the right diagonal.
     *
     * @param Coordinate c representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the right diagonal
     */

    private Coordinate[] findConnectedRDiagonal(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] diagonal = getDiagonal(c, 1);
        int length = diagonal.length;
        int location = -1;
        if (c.y > (this.width - c.x - 1)) {
            location = this.width - c.x - 1;
        } else {
            location = c.y;
        }

        for (int i = 1; location - i >= 0; i++) {
            if (diagonal[location - i] == type) {
                connectedChips[0] = new Coordinate(c.x + i, c.y - i);
                //connectedChips[0][0] = c.x + i;
                //connectedChips[0][1] = c.y - i;
                break;
            }
            if (diagonal[location - i] != type && checkPiece(diagonal[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (diagonal[i + location] == type) {
                connectedChips[1] = new Coordinate(c.x - i, c.y + i);
                //connectedChips[1][0] = c.x - i;
                //System.out.println(connectedChips[1][0]);
                //connectedChips[1][1] = c.y + i;
                //System.out.println(connectedChips[1][1]);
                break;
            }
            if (diagonal[location + i] != type && checkPiece(diagonal[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedRow() takes one parameter, the Coordinate, and returns a list of connected squares of the same type in the same row.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the same row
     */

    private Coordinate[] findConnectedRow(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] row = getRow(c);
        int length = row.length;
        int location = c.x;

        for (int i = 1; location - i >= 0; i++) {
            if (row[location - i] == type) {

                connectedChips[0] = new Coordinate(c.x-i, c.y);
                //connectedChips[0][0] = x - i;
                //connectedChips[0][1] = y;
                break;
            }
            if (row[location - i] != type && checkPiece(row[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (row[i + location] == type) {

                connectedChips[1] = new Coordinate(c.x+i, c.y);
                //connectedChips[1][0] = x + i;
                //connectedChips[1][1] = y;
                break;
            }
            if (row[location + i] != type && checkPiece(row[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedColumn(new Coordinate() takes 2 parameters, the coordinates, and returns a list of connected squares of the same type in the same column.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a int[] containing "connected" squares of the same type in the same column.
     */

    private Coordinate[] findConnectedColumn(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] column = getColumn(c);
        int length = column.length;
        int location = c.y;

        for (int i = 1; location - i >= 0; i++) {
            if (column[location - i] == type) {
                connectedChips[0] = new Coordinate(c.x,c.y-i);
                //connectedChips[0][0] = x;
                //connectedChips[0][1] = y - i;
                break;
            }
            if (column[location - i] != type && checkPiece(column[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (column[i + location] == type) {
                connectedChips[1] = new Coordinate(c.x, c.y+i);
                //connectedChips[1][0] = x;
                //connectedChips[1][1] = y + i;
                break;
            }
            if (column[location + i] != type && checkPiece(column[location + i])) {
                break;
            }
        }
        return connectedChips;
    }


    /**
     * findConnectingChips() takes 1 parameter, the coordinate, and returns a list of Coordinates
     * that the square is "connected" to.
     *
     * @param Coordinate coord takes in the coordinates of the square
     *
     * @return a Coordinate containing chips that is connected to given square
     */

    private Coordinate[] findConnectingChips(Coordinate coord) {
        Coordinate[] row = findConnectedRow(coord);
        Coordinate[] col = findConnectedColumn(coord);
        Coordinate[] ld = findConnectedLDiagonal(coord);
        Coordinate[] rd = findConnectedRDiagonal(coord);
        Coordinate[] result = Arrays.copyOf(row, row.length + col.length + ld.length + rd.length);
        System.arraycopy(col, 0, result, row.length, col.length);
        System.arraycopy(ld, 0, result, row.length + col.length, ld.length);
        System.arraycopy(rd, 0, result, row.length + col.length + ld.length, rd.length);

        // System.out.print("FindConnectiongChips has returned with: [");
        // for (int i=0; i<result.length; i++) {
            // System.out.print(" "+result[i]);
        // }
        // System.out.print(" ]\n");

        return result;
    }

    /**
     * addPiece(), takes 2 parameters, and changes the type of the
     * indicated square on "this" Gameboard at the coordinates (x,y) to
     * the specified type.
     *
     * @param Coordinate coord takes in the coordinates of the square
     * @param type the new type of the square
     */

    private void addPiece(Coordinate coord, int type) throws AgainstRulesException {
        if (checkRules(coord,type)) { //YOU NEED TO TURN THIS ON!
        //if (checkRulesExceptCount(coord,type)) { //TURN ON WHEN TESTING
            setType(coord, type);
            if (type == BLACK) {
                blackCount--;
            }
            if (type == WHITE) {
                whiteCount--;
            }
        } else {
            throw new AgainstRulesException("attempt to add " + type + " fails at (" + coord.x + ", " + coord.y + ")");
        }
        /*
        if ((checkDimensions(x,y) && checkPiece(type)) && (checkSquare(x,y,type) && checkNeighbors(x,y,type))) {
            setType(x, y, type);
            if (type == BLACK) {
                blackCount--;
            }
            if (type == WHITE) {
                whiteCount--;
            }
        } else {
            //throw new AgainstRulesException("attempt to add " + type + " fails at  (" + x + ", " + y + ")");
        }
        */
    }

    /**
     * checkRules(), takes 2 parameters, the coordinates, and a type, and verifies
     * all the check methods. Namely, it verifies that checkDimensions(), checkPiece(), checkCount(), checkSquare(), and checkNeighbors()
     * returns true.
     * 
     * @param Coordinate coord takes in the coordinates of the square
     * @param type of the piece being considered
     *
     * @return true if all of the above tests return true, false otherwise.
     */

    private boolean checkRules(Coordinate coord, int type) {
        /*
        System.out.println("checkDimensions: " + checkDimensions(coord));
        System.out.println("checkNeighbor: " + checkNeighbors(coord,type));
        System.out.println("checkSquare: " + checkSquare(coord,type));
        System.out.println("checkPiece: " + checkPiece(type));
        System.out.println("checkCount: " + checkCount(type));
        */
        if (checkCount(type) && ((checkDimensions(coord) && checkPiece(type)) && (checkSquare(coord,type) && checkNeighbors(coord,type)))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkRulesExcept Count(), takes 2 parameters, the coordinates, and a type, and verifies
     * all the check methods. Namely, it verifies that checkDimensions(), checkPiece(), checkSquare(), and checkNeighbors()
     * returns true. Important: This method is for testing only, it does not check checkCount().
     * 
     * @param Coordinate coord takes in the coordinates of the square
     * @param type of the piece being considered
     *
     * @return true if all of the above tests return true, false otherwise.
     */

    private boolean checkRulesExceptCount(Coordinate coord, int type) {
        if ((checkDimensions(coord) && checkPiece(type)) && (checkSquare(coord,type) && checkNeighbors(coord,type))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkDimensions(), takes 1 parameters, the coordinates, and verifies
     * that they are within the dimensions of this.Gameboard.
     *
     * @param Coordinate coord takes in the coordinates of the square
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return true if the coordinate satisfies the dimensions of the board, false otherwise.
     */

    private boolean checkDimensions(Coordinate coord) {
        if ((coord.x < this.width && coord.x >= 0) && (coord.y < this.height && coord.y >= 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkSquare(), takes 2 parameters, the coordinate and a piece, and verifies
     * that the square is not paired with a coordinate where the piece is not allowed. If the type is INVALID, automatically
     * returns false. An INVALID is not allowed anywhere! If the coordinates is at an INVALID spot, also returns false.
     * 
     * (RULE #1 and #2)
     * @param Coordinate coord takes in the coordinates of the square
     * @param type the piece to be inspected at (x,y)
     *
     * @return true if the piece can legitimately be placed on (x,y), false otherwise.
     */

    private boolean checkSquare(Coordinate coord, int type) {
        if (isEmpty(coord)) { //if (x,y) is valid, and the type of square is valid
            if (type == BLACK) { //a BLACK can not be placed on the left/right edges
                if (coord.x != 0 && coord.x != (this.width - 1)) { //if x-coordinate is not 0 and its not width - 1
                    return true;
                } else {
                    return false;
                }
            }
            if (type == WHITE) { //a WHITE can not be placed on the top/bottom edges
                if (coord.y != 0 && coord.y != (this.height - 1)) { //if x-coordinate is not 0 and its not width - 1
                    return true; 
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * checkCount(), takes one parameters, a type, and verifies
     * number of pieces remaining is greater than zero.
     * 
     * @param type of the piece being considered
     *
     * @return true if the number of pieces remaining of type is greater than zero, false otherwise
     */

    private boolean checkCount(int type) {
        if (type == BLACK) {
            if (this.getBlackCount() > 0) {
                return true;
            } else {
                return false;
            }
        }
        if (type == WHITE) {
            if (this.getWhiteCount() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * pieceCheck(), takes one parameter, the type of a piece, returns whether or not the type is {BLACK, WHITE}
     * 
     * (RULE #1 and #2)
     * @param type the piece to be inspected
     *
     * @return true if the piece is WHITE or BLACK, false otherwise.
     */

    private boolean checkPiece(int type) {
        if (type == WHITE || type == BLACK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * countNeighbor() takes in 2 parameters, a Coordinate, and a type and return the number of neighbors
     * around it, excluding itself.
     *
     * @param Coordinate coord takes in the coordinates of a square
     * @param type the type of the piece to be inspected
     *
     * @return the number of neighbors surrounding the specified piece, excluding itself,
     */

    private int countNeighbors(Coordinate coord, int type) {
        int count = 0;
        int[][] neighbors = getNeighbors(coord);
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (neighbors[i][j] == type) {
                    //System.out.print(i + ", " + j + " --> " + count);
                    count++;
                }
            }
        }
        //System.out.println("count is " + count + " at (" + x + ", " + y + ") with " + type);
        if (neighbors[1][1] == type) {
            count--;
        }
        return count;
    }

    /**
     * locateNeighbor() takes in 2 parameters, a coordinate on the board and a type, adjusts 
     * the coordinates of 2-D array returned from getNeighbor() to overlay the coordinates of the piece on this.Gameboard. Only
     * returns the first neighbor it finds.
     *
     * @param Coordinate coord takes the coordinates of a square
     * @param type the type of the square we are inspecting
     *
     * @return a coordiante, containing the coordinates of first neighbor on the this.Gameboard.
     */

    private Coordinate locateNeighbors(Coordinate coord, int type) {
        Coordinate location = null;
        int[][] neighbors = getNeighbors(coord);
        /*
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                System.out.print(neighbors[i][j] + "|");
            }
            System.out.println();
        }
        */

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (neighbors[i][j] == type) {
                    location = new Coordinate(coord.x + i - 1, coord.y + j - 1); //true location
                }
            }
        }

        //System.out.println(location[0] + ", " + location[1]);
        return location;
    }


    /**
     * checkNeighbors() takes in 2 parameters, a coordinate and a piece and checks whether placing the piece there
     * would violate the rule that a player may not have more than two chips in a connected group, whether connected
     * orthogonally or diagonally.
     * (RULE #4)
     *
     * @param Coordinate coord takes in the coordinate of a square
     * @param type the type of the square
     *
     * @return true if the piece is can be placed according to the rules, false otherwise.
     */

    private boolean checkNeighbors(Coordinate coord, int type) {
        if (countNeighbors(coord,type) == 0) {
            return true;
        }
        //System.out.println("countNeighbors " + countNeighbors(x,y,type) + " at (" + x + ", " + y + ") with " + type);
        if (countNeighbors(coord,type) == 1) {
            Coordinate location = locateNeighbors(coord,type);
            if (countNeighbors(location, type) <= 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * removePiece(), takes 1 parameter, the coordinate, and changes the type of the 
     * indicated square on "this" Gameboard at the coordinates (x,y) to EMPTY.
     *
     * @param Coordinate coord takes the coordinates of a square
     */

    private void removePiece(Coordinate coord) {
        if (checkPiece(getType(coord)) && checkDimensions(coord)) {
            int temp = this.getType(coord);
            if (temp == BLACK) {
                blackCount++;
            }
            if (temp == WHITE) {
                whiteCount++;
            }
            this.setType(coord, EMPTY);
        }   
    }

    /**
     * movePieces() "moves" a piece from one coordinate to another.
     * More specifically, it takes parameters (Coordinate coord1, Coordinate coord2) and
     * moves a piece on "this" Gameboard from coord1 to coord2.
     *
     *
     * @param Coordinate coord1 takes the coordinates of the first square
     * @param Coordinate coord2 takes the coordinates of the second square
     */

    private void movePieces(Coordinate coord1, Coordinate coord2) throws AgainstRulesException {
        if (coord1.x == coord2.x && coord1.y == coord2.y) {
            throw new AgainstRulesException("attempt to move to same coordinates (" + coord1.x + ", " + coord1.y + ") = (" + coord2.x + ", " + coord2.y + ")");
        } else {
            int type = getType(coord1);
            try {
                removePiece(coord1);
                addPiece(coord2, type);
            }
            catch (AgainstRulesException e) {
                addPiece(coord1, type);
                throw new AgainstRulesException("attempt to move " + getType(coord1) + " from  (" + coord1.x + ", " + coord1.y + ") fails game rules.");
            }
        }
    }

    //TODO: comment this, bitch!!!
    public void performMove(Move m, int type) throws AgainstRulesException{
        if (m.moveKind == Move.STEP) {
            movePieces(new Coordinate(m.x2, m.y2), new Coordinate(m.x1, m.y1));
        }
        if (m.moveKind == Move.ADD) {
            addPiece(new Coordinate(m.x1, m.y1), type);
        }
    }

    //TODO: comment this!!!!!!!
    public void undoMove(Move m) throws AgainstRulesException {
        if (m.moveKind == Move.STEP) {
            movePieces(new Coordinate(m.x1, m.y1), new Coordinate(m.x2, m.y2));
        }
        if (m.moveKind == Move.ADD) {
            removePiece(new Coordinate(m.x1, m.y1));
        } 
    }

    /**
     * isValidMove() takes two parameter, and checks if "move" 
     * can be legally made based on the game rules.
     * 
     * @param m is the move in question.
     * @param type is the type of the piece involved in the move
     *
     * @return true if the move can be legally made, false otherwise.
     */

    public boolean isValidMove(Move m, int type) {
        //does not test if moving away the piece allows other
        //player to win WARNING!
        if(m.moveKind == Move.ADD && getTypeCount(type) > 0) {
            try{
                addPiece(new Coordinate(m.x1,m.y1), type);
                removePiece(new Coordinate(m.x1, m.y1));
                return true;
            } catch (AgainstRulesException e){
                return false;
            }

        }
        if(m.moveKind == Move.STEP && getTypeCount(type) == 0) {
            try{
                movePieces(new Coordinate(m.x2, m.y2), new Coordinate(m.x1, m.y1));
                movePieces(new Coordinate(m.x1, m.y1), new Coordinate(m.x2, m.y2));
                return true;
            } catch (AgainstRulesException e){
                return false;
            }
        }
        if(m.moveKind == Move.QUIT) {
            return true; 
        } else {
            return false;
        }
    }

    /**
     * listBlacks() takes no parameters, and returns the coordinates of all squares
     * with a black piece.
     *
     *
     * @return an array containing the coordinate of all black pieces
     */

    private Coordinate[] listBlacks() {
        Coordinate[] black = new Coordinate[TOTAL - this.getBlackCount()];
        int count = 0;
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (isBlack(new Coordinate(i, j))) {
                    black[count] = new Coordinate(i,j);
                    count++;
                } 
            }
        }
        return black;
    }

    /**
     * listWhites() takes no parameters, and returns the coordinates of all squares
     * with a whites piece.
     *
     *
     * @return an array containing the coordinate of all white pieces
     */

    private Coordinate[] listWhites() {
        Coordinate[] white = new Coordinate[TOTAL - this.getWhiteCount()];
        int count = 0;
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (isWhite(new Coordinate(i,j))) {
                    white[count] = new Coordinate(i,j);
                    count++;
                } 
            }
        }
        return white;
    }

    /**
     * listPieces takes one parameter, and returns the coordinates of all the pieces on the board
     * of the given type.
     * 
     * @param a type, either WHITE or BLACK 
     *
     * @return an array containing the coordinate of the specified type
     */

    public Coordinate[] listPieces(int type) throws AgainstRulesException {
        if (type == WHITE) {
            return this.listWhites();
        }
        if (type == BLACK) {
            return this.listBlacks();
        } else {
            throw new AgainstRulesException("attempting to list pieces of type neither black nor white");
        }
    }

    
    /**
     * listMoves() takes one parameter, the type of the current player, and 
     * returns an array of all possible valid moves.
     *
     * @param player is the type of the player in consideration
     *
     * @return a Move[] containing all the possible moves possible for "player"
     * "this" Gameboard.
     */

    public SList listMoves(int player) throws AgainstRulesException {
        //System.out.println(this);
        SList validMoves = new SList();
        if(player == WHITE){
            if(getTypeCount(player) > 0) {
                for (int i = 0; i < this.height; i++) {
                    for (int j = 0; j < this.width; j++) {
                        if (isValidMove(new Move(i, j), player)) {
                            validMoves.insertBack(new Move(i,j));
                        }
                    }
                }
            }
            if(getTypeCount(player) == 0) {
                Coordinate[] piece = listPieces(player);
                //System.out.println("hey fuck you");
                for (int k = 0; k < piece.length; k++) {
                    for (int i = 0; i < this.height; i++) {
                        for (int j = 0; j < this.width; j++) {
                            //System.out.println("Outside If!!");
                            if (isValidMove(new Move(i, j, piece[k].x, piece[k].y), player)) {
                                //System.out.println("GOING IN!!");
                                validMoves.insertBack(new Move(i, j, piece[k].x, piece[k].y));
                            }
                        }
                    }
                }
            }
        } else {
            if(getTypeCount(player) > 0) {
                for (int j = 0; j < this.height; j++) {
                    for (int i = 0; i < this.width; i++) {
                        if (isValidMove(new Move(i, j), player)) {
                            validMoves.insertBack(new Move(i,j));
                        }
                    }
                }
            }
            if(getTypeCount(player) == 0) {
                Coordinate[] piece = listPieces(player);
                //System.out.println("hey fuck you");
                for (int k = 0; k < piece.length; k++) {
                    for (int j = 0; j < this.height; j++) {
                        for (int i = 0; i < this.width; i++) {
                            //System.out.println("Outside If!!");
                            if (isValidMove(new Move(i, j, piece[k].x, piece[k].y), player)) {
                                //System.out.println("GOING IN!!");
                                validMoves.insertBack(new Move(i, j, piece[k].x, piece[k].y));
                            }
                        }
                    }
                }
            }
        }
        return validMoves;
    }

    /**
     * makeGrid() takes 1 parameter, a coordinate, and returns a 3-D array containing the pieces
     * it has connections to in every direction.
     *
     * @param Coordinate coord takes the coordinates of the square
     *
     * @return a coordiante containing the pieces of the same type the specified square is connected to.
     */
    
    private Coordinate[][] makeGrid(Coordinate coord) {
        Coordinate[][] grid = new Coordinate[3][3];
        Coordinate[] row = findConnectedRow(coord);
        Coordinate[] column = findConnectedColumn(coord);
        Coordinate[] ldiagonal = findConnectedLDiagonal(coord);
        Coordinate[] rdiagonal = findConnectedRDiagonal(coord);

        grid[0][0] = ldiagonal[0];
        grid[0][1] = column[0];
        grid[0][2] = rdiagonal[0];
        grid[1][0] = row[0];
        grid[1][1] = coord; 
        grid[1][2] = row[1];
        grid[2][0] = rdiagonal[1];
        grid[2][1] = column[1];
        grid[2][2] = ldiagonal[1];

        return grid;
    }

    /**
     * makeHGrid() takes 1 parameter, a coordinate, and returns a 2-D array containing the pieces
     * it has connections to in every direction.
     *
     * @param Coordinate coord takes the coordinates of the square
     *
     * @return an array containing the pieces of the same type the specified square is connected to. More specifically, it returns
     * a horizontal version of makeGrid().
     */

    private Coordinate[] makeHGrid(Coordinate coord) {
        Coordinate[] grid = new Coordinate[9];
        Coordinate[] row = findConnectedRow(coord);
        Coordinate[] column = findConnectedColumn(coord);
        Coordinate[] ldiagonal = findConnectedLDiagonal(coord);
        Coordinate[] rdiagonal = findConnectedRDiagonal(coord);

        grid[0] = ldiagonal[0];
        grid[1] = column[0];
        grid[2] = rdiagonal[0];
        grid[3] = row[0];
        grid[4] = coord;
        grid[5] = row[1];
        grid[6] = rdiagonal[1];
        grid[7] = column[1];
        grid[8] = ldiagonal[1];

        return grid;
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

    public boolean containsNetwork(int player) throws InvalidNodeException{

        // System.out.println("\nContainsNetwork called!");
        int[] firstRow = null;
        if (player == BLACKPLAYER) {
            firstRow = getRow(new Coordinate(0,0));
        } else if (player == WHITEPLAYER) {
            firstRow = getColumn(new Coordinate(0,0));
        } else {return false;} //return false if the player parameter is something crazy
        
        for (int i = 0; i < firstRow.length; i++) {

            if((firstRow[i] == BLACK && player == BLACKPLAYER) || (firstRow[i] == WHITE && player == WHITEPLAYER)) {
                
                // System.out.println("Chip encountered in the home row!");
                SList parsedCoords = new SList();

                Coordinate eachCoord;
                if (player == BLACKPLAYER) {
                    eachCoord = new Coordinate(i,0);
                } else if (player == WHITEPLAYER) {
                    eachCoord = new Coordinate(0,i);  
                } else {return false;} //return false if the player parameter is something crazy

                // System.out.println("The chip encountered was at: "+eachCoord);
                // System.out.println();

                parsedCoords.insertFront(eachCoord);
                if(containsNetworkHelper(eachCoord, parsedCoords, player, NULLDIRECTION)) {
                    return true; 
                }
                parsedCoords.front().remove();

            }

        }
            // System.out.println("\nAll chips in the home row were exhausted.");
            return false;
    }
     /**
     * containsNetworkHelper() takes four parameters, the current coordinate in the
     * line of connected chips, the list of coordinates that have been parsed through already,
     * the player that will win if there is a network, the direction that the last connection made was in, and
     * returns a recursive call that iterates through every combination of connected chips
     * and checks to see if there is a network.
     *
     * @param coord is the current coordinate being parsed
     * @param parsedCoords is the list of Coordinate objects that have already been parsed
     * @param player is the player that will win if a network is found
     * @param direction is the direction that the last parsed connection was made in
     * @return true if a network has been found, false otherwise.
     */

    private boolean containsNetworkHelper(Coordinate coord, SList parsedCoords, int player, int direction) throws InvalidNodeException{
        /*
        first of all, if the coord were checking is already in the network, there is no
        legal network that will result from having this node again, so return false
        right off the bat.

        if the coordinate is in a the starting row and 

        if the MIN_DEPTH has been exceeded and the current coordinate is in the home row,
        then return true

        otherwise, go through all of the coordinates returned by getConnections. call recursively
        on each one. and return true if at least one of the recursive calls returns true.
        */

        // System.out.println("\ncontainsNetworkHelper called! The coordinate is: "+coord+" and parsedCoords.length() is: "+parsedCoords.length());

        int blackWinYCoord = this.height - 1;
        int whiteWinXCoord = this.width - 1;

        if(parsedCoords.contains(coord)) { //this would be faster if implemented with sets
            // System.out.println("Case 1 triggered: parsedCoords.contains(coord) was true!\n");
            //parsedCoords.back().remove();
            return false;
        }
        if (player == WHITE) {
            if ((coord.x == 0 && !(parsedCoords.length() == 1))  || (coord.x == whiteWinXCoord && !(parsedCoords.length() >= MIN_DEPTH))) {
                // System.out.println("Case 2.1 triggered: either two in the home/end row or in the end row with not enough chips!\n");
                //parsedCoords.back().remove();
                return false; //can't have chips in the home rows
            }
        }
        if (player == BLACK) {
            if ((coord.y == 0 && !(parsedCoords.length() == 1))  || (coord.y == blackWinYCoord && !(parsedCoords.length() >= MIN_DEPTH))) {
                // System.out.println("Case 2.2 triggered: either two in the home/end row or in the end row with not enough chips!\n");
                //parsedCoords.back().remove();
                return false; //can't have chips in the home rows
            }
        }
        if (parsedCoords.length() >= MIN_DEPTH - 1 && ((player == WHITEPLAYER && coord.x == whiteWinXCoord) ||
                                                                (player == BLACKPLAYER && coord.y == blackWinYCoord))) {
            // System.out.println("Case 3 triggered: looks like its a win!\n");
            return true;
        }

        Coordinate[] connections = findConnectingChips(coord);

        // System.out.println("Attempting to parse through remaining connections.");
        for (int i=0; i<connections.length; i++) {
            // System.out.println("Parsing. DIRECTIONS[i] is: "+DIRECTIONS[i]+" and direction is: "+direction+" and connections[i] is: "+connections[i]);
            if (DIRECTIONS[i] == direction) { //this should work, maybe. if its the first time and is NULLDIRECTION, it never skips. i think.
                // System.out.println("Same direction detected! Ignoring.");
                continue;
            }
            if (OPPOSITES[i] == direction) {
                // System.out.println("Opposite direction detected! Ignoring.");
                continue;
            }
            if (!connections[i].equals(new Coordinate(0,0))) {
                // System.out.println("Another connection found!");
                parsedCoords.insertBack(coord);
                if (containsNetworkHelper(connections[i], parsedCoords, player, DIRECTIONS[i])) {
                    return true;
                }
                parsedCoords.back().remove();
            }
        }
    
        return false;
    }

    private boolean containsNetworkOfLength(Coordinate c,int max_depth){
        try{
            return containsNetworkOfLengthHelper(c,new SList(), getType(c),NULLDIRECTION,max_depth);
        } catch (InvalidNodeException e){
            //System.out.println(e);
            return false;
        }
    }

    private boolean containsNetworkOfLengthHelper(Coordinate coord, SList parsedCoords, int player, int direction, int max_depth) throws InvalidNodeException{
        /*
        first of all, if the coord were checking is already in the network, there is no
        legal network that will result from having this node again, so return false
        right off the bat.

        if the coordinate is in a the starting row and 

        if the MIN_DEPTH has been exceeded and the current coordinate is in the home row,
        then return true

        otherwise, go through all of the coordinates returned by getConnections. call recursively
        on each one. and return true if at least one of the recursive calls returns true.
        */

        // System.out.println("\ncontainsNetworkHelper called! The coordinate is: "+coord+" and parsedCoords.length() is: "+parsedCoords.length());
        int blackWinYCoord = this.height - 1;
        int whiteWinXCoord = this.width - 1;

        try{
            if(parsedCoords.contains(coord)) { //this would be faster if implemented with sets
                // System.out.println("Case 1 triggered: parsedCoords.contains(coord) was true!\n");
                parsedCoords.back().remove();
                return false;
            }
        } catch (Exception e){
            //dun do anything
        }

        if (player == WHITE) {
            if ((coord.x == 0 && !(parsedCoords.length() == 1))) {
                // System.out.println("Case 2.1 triggered: either two in the home/end row or in the end row with not enough chips!\n");
                //parsedCoords.back().remove();
                return false; //can't have chips in the home rows
            }
        }
        if (player == BLACK) {
            if ((coord.y == 0 && !(parsedCoords.length() == 1))) {
                // System.out.println("Case 2.2 triggered: either two in the home/end row or in the end row with not enough chips!\n");
                //parsedCoords.back().remove();
                return false; //can't have chips in the home rows
            }
        }

        if (parsedCoords.length() >= max_depth) {
            // System.out.println("Case 3 triggered: looks like its a win!\n");
            return true;
        }

        Coordinate[] connections = findConnectingChips(coord);

        // System.out.println("Attempting to parse through remaining connections.");
        for (int i=0; i<connections.length; i++) {
            // System.out.println("Parsing. DIRECTIONS[i] is: "+DIRECTIONS[i]+" and direction is: "+direction+" and connections[i] is: "+connections[i]);
            if (DIRECTIONS[i] == direction) { //this should work, maybe. if its the first time and is NULLDIRECTION, it never skips. i think.
                // System.out.println("Same direction detected! Ignoring.");
                continue;
            }
            if (OPPOSITES[i] == direction) {
                // System.out.println("Opposite direction detected! Ignoring.");
                continue;
            }
            if (!connections[i].equals(new Coordinate(0,0))) {
                // System.out.println("Another connection found!");
                parsedCoords.insertBack(coord);
                if (containsNetworkHelper(connections[i], parsedCoords, player, DIRECTIONS[i])) {
                    return true;
                }
            }
        }    
        return false;
    }

    /**
     * isEmptyBoard() takes no parameters, and verifies that there are no pieces on the board. For internal testing only.
     *
     * @return empty is true if the board is empty, false otherwise.
     */

    private boolean isEmptyBoard() {
        boolean empty = true;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (isBlack(new Coordinate(i,j)) || isWhite(new Coordinate(i,j))) {
                    empty = false;
                }
            }
        }
        return empty;
    }

/* ============================== EVALUATOR MODULE ===================================*/


    //********************************************************************/
    //                        scoreBlocks() Module                       //
    //********************************************************************/

    public int opponentPiece(Coordinate c) {
        if(getType(c) == BLACK) {
            return WHITE;
        }
        if(getType(c) == WHITE) {
            return BLACK;
        } else {
            return -1;
        }
    }

    public int opponentType(int type) {
        if(type == BLACK) {
            return WHITE;
        }
        if(type == WHITE) {
            return BLACK;
        } else {
            return -1;
        }
    }

    /**
     * findConnectedLDiagonal() takes one parameter, a Coordinate, and returns a list of connected squares of the same type in the left diagonal.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the left diagonal
     */

    private Coordinate[] spyFindConnectedLDiagonal(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);
        //int[][] connectedChips = new int[2][2];
        int[] diagonal = getDiagonal(c, -1);
        //int[] diagonal = getDiagonal(c.x, c.y, -1);
        int length = diagonal.length;
        int location = -1;
        if (c.y >= c.x) {
            location = c.x;
        } else {
            location = c.y;
        }

        for (int i = 1; location - i >= 0; i++) {
            if (diagonal[location - i] == opponentType(type)) {
                connectedChips[0] = new Coordinate(c.x - i, c.y - i);
                //connectedChips[0][0] = c.x - i;
                //connectedChips[0][1] = c.y - i;
                break;
            }
            if (diagonal[location - i] != opponentType(type) && checkPiece(diagonal[location - i])) {
                break;
            }
        }
        for (int i = 1; i + location < length; i++) {
            if (diagonal[i + location] == opponentType(type)) {
                connectedChips[1] = new Coordinate(c.x + i, c.y + i);
                //connectedChips[1][0] = c.x + i;
                //connectedChips[1][1] = c.y + i;
                break;
            }
            if (diagonal[location + i] != opponentType(type) && checkPiece(diagonal[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedRDiagonal() takes one parameter, a Coordinate, and returns a list of connected squares of the same type in the right diagonal.
     *
     * @param Coordinate c representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the right diagonal
     */

    private Coordinate[] spyFindConnectedRDiagonal(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] diagonal = getDiagonal(c, 1);
        int length = diagonal.length;
        int location = -1;
        if (c.y > (this.width - c.x - 1)) {
            location = this.width - c.x - 1;
        } else {
            location = c.y;
        }

        for (int i = 1; location - i >= 0; i++) {
            if (diagonal[location - i] == opponentType(type)) {
                connectedChips[0] = new Coordinate(c.x + i, c.y - i);
                //connectedChips[0][0] = c.x + i;
                //connectedChips[0][1] = c.y - i;
                break;
            }
            if (diagonal[location - i] != opponentType(type) && checkPiece(diagonal[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (diagonal[i + location] == opponentType(type)) {
                connectedChips[1] = new Coordinate(c.x - i, c.y + i);
                //connectedChips[1][0] = c.x - i;
                //System.out.println(connectedChips[1][0]);
                //connectedChips[1][1] = c.y + i;
                //System.out.println(connectedChips[1][1]);
                break;
            }
            if (diagonal[location + i] != opponentType(type) && checkPiece(diagonal[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedRow() takes one parameter, the Coordinate, and returns a list of connected squares of the same type in the same row.
     *
     * @param Coordinate c, representing the coordinate of the square
     *
     * @return a Coordinate[] containing "connected" squares of the same type in the same row
     */

    private Coordinate[] spyFindConnectedRow(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] row = getRow(c);
        int length = row.length;
        int location = c.x;

        for (int i = 1; location - i >= 0; i++) {
            if (row[location - i] == opponentType(type)) {

                connectedChips[0] = new Coordinate(c.x-i, c.y);
                //connectedChips[0][0] = x - i;
                //connectedChips[0][1] = y;
                break;
            }
            if (row[location - i] != opponentType(type) && checkPiece(row[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (row[i + location] == opponentType(type)) {

                connectedChips[1] = new Coordinate(c.x+i, c.y);
                //connectedChips[1][0] = x + i;
                //connectedChips[1][1] = y;
                break;
            }
            if (row[location + i] != opponentType(type) && checkPiece(row[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    /**
     * findConnectedColumn(new Coordinate() takes 2 parameters, the coordinates, and returns a list of connected squares of the same type in the same column.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a int[] containing "connected" squares of the same type in the same column.
     */

    private Coordinate[] spyFindConnectedColumn(Coordinate c) {
        int type = getType(c);

        Coordinate[] connectedChips = new Coordinate[2];
        //int[][] connectedChips = new int[2][2];
        connectedChips[0] = new Coordinate(0,0);
        connectedChips[1] = new Coordinate(0,0);

        int[] column = getColumn(c);
        int length = column.length;
        int location = c.y;

        for (int i = 1; location - i >= 0; i++) {
            if (column[location - i] == opponentType(type)) {
                connectedChips[0] = new Coordinate(c.x,c.y-i);
                //connectedChips[0][0] = x;
                //connectedChips[0][1] = y - i;
                break;
            }
            if (column[location - i] != opponentType(type) && checkPiece(column[location - i])) {
                break;
            }
        }

        for (int i = 1; i + location < length; i++) {
            if (column[i + location] == opponentType(type)) {
                connectedChips[1] = new Coordinate(c.x, c.y+i);
                //connectedChips[1][0] = x;
                //connectedChips[1][1] = y + i;
                break;
            }
            if (column[location + i] != opponentType(type) && checkPiece(column[location + i])) {
                break;
            }
        }
        return connectedChips;
    }

    private Coordinate[] spyMakeHGrid(Coordinate c) {
        Coordinate[] grid = new Coordinate[9];
        Coordinate[] row = spyFindConnectedRow(c);
        Coordinate[] column = spyFindConnectedColumn(c);
        Coordinate[] ldiagonal = spyFindConnectedLDiagonal(c);
        Coordinate[] rdiagonal = spyFindConnectedRDiagonal(c);

        grid[0] = ldiagonal[0];
        grid[1] = column[0];
        grid[2] = rdiagonal[0];
        grid[3] = row[0];
        grid[4] = c;
        grid[5] = row[1];
        grid[6] = rdiagonal[1];
        grid[7] = column[1];
        grid[8] = ldiagonal[1];

        return grid;
    }

    /*=============================== END OF YUXIN ZHU STUFF ===============================*/


    public int scoreBlocks(Coordinate[] list, int bonus) {
        Coordinate[][] friends = new Coordinate[list.length][9];
        for(int i = 0; i < list.length; i++) {
            friends[i] = spyMakeHGrid(list[i]);
        }
        int score = 0;
        for(int i = 0; i < list.length; i++) {
            for(int k = 0; k < 4; k++) {
                if(getType(friends[i][k]) == opponentPiece(list[i])) {
                    if(getType(friends[i][k]) == getType(friends[i][8-k])) {
                        score+=bonus; 
                    }
                } 
            }
        }
        return score;
    }


    private int scorePiece(Coordinate c) {
        if(getType(c) == BLACK) {
            if(c.y == 0 || c.y == this.height - 1) {
                return 2;
            }
        }
        if(getType(c) == WHITE) {
            if(c.x == 0 || c.x == this.width - 1) {
                return 2;
            }
        }
        return 0; 
    }

    private boolean inCorner(Coordinate c){
        int chip = getType(c);
        if(chip == WHITE){
            if((c.x == 0 && c.y == 1) || (c.x == 0 && c.y == 6) || (c.x == 7 && c.y == 1) || (c.x == 7 && c.y == 6)){
                return true;
            }
        }
        if(chip == BLACK){
            if((c.x == 1 && c.y == 0) || (c.x == 6 && c.y == 0) || (c.x == 1 && c.y == 7) || (c.x == 6 && c.y == 7)){
                return true;
            }
        }
        return false;
    }

    private int awardForMaximizingConnections(Coordinate[] list,Coordinate[][] friends){
        // award for maximising connections      
        int sumOfConnections = 0;      
        for(int j = 0; j < list.length; j++) { 
            sumOfConnections+=scorePiece(list[j]);
            for(int k = 0; k < 9; k++) {
                if(!friends[j][k].equals(new Coordinate(0,0))){
                    sumOfConnections+=5;  
                }
            }
            sumOfConnections--; //remove connection to itself
        }
        return sumOfConnections;
    }

    private int awardHomeRow(Coordinate[] list){
        int homeRow1 = 0;
        int homeRow2 = 0;
        int sumOfConnections = 0;

        //reward for having in the homerow
        for(int i = 0; i < list.length; i++) {
            if(list[i].y == 0) {
                homeRow1++;
            }
            if(list[i].y == this.height - 1) {
                homeRow2++;
            }
        }

        //punish if too there is nothing in one of the homerows
        if(!(homeRow1 == 0 && homeRow2 == 0) || !((homeRow1 != 0 && homeRow2 == 0) || (homeRow2 != 0 && homeRow1 == 0))) {
            sumOfConnections+=20;
        }

        //award if ratio of piece in the home rows is larger than .5
        if(homeRow1 > homeRow2) {
            if(((double) homeRow2)/(homeRow1) > .5) {
                sumOfConnections+=20;
            }
            if(homeRow1 <= 2 && homeRow2 <= 2) {
                sumOfConnections+=20;
            } 
        }
        if(homeRow1 < homeRow2) {
            if(((double) homeRow1)/(homeRow2) > .5) {
                sumOfConnections+=20;
            }
            if(homeRow1 <= 2 && homeRow2 <= 2) {
                sumOfConnections+=20;
            } 
        }
        return sumOfConnections;
    }

    private int awardInCorner(Coordinate[] list){
        //award for not being in corner
        int sumOfConnections = 0;
        for(int i = 0; i < list.length; i++){
            if(!inCorner(list[i])){
                sumOfConnections += 1;
            }
        }
        return sumOfConnections;
    }

    private int awardPiecesNotNeighbors(Coordinate[] list){
        int sumOfConnections = 0;
        //award if pieces are not together
        for(int i = 0; i < list.length; i++) {
            int numNeighbors = countNeighbors(list[i], getType(list[i]));
            if(numNeighbors == 0) {
                sumOfConnections+=10;
            }
        }
        return sumOfConnections;
    }

    private int awardIfNotTooManyInRow(Coordinate[] list){
        int sumOfConnections = 0;
        //award if not too many pieces in a row
        for(int i = 0; i < this.height; i++){
            int localSum = 0;
            for(int piece = 0; piece < list.length; piece++){
                if(list[piece].y == i){
                    localSum += 1;
                }
            }
            if(localSum <= 3){
                sumOfConnections += 20;
            }
        }
        return sumOfConnections;
    }

    private int awardIfNotTooManyInCol(Coordinate[] list){
        int sumOfConnections = 0;
        //award if not too many pieces in a col
        for(int i = 0; i < this.width; i++){
            int localSum = 0;
            for(int piece = 0; piece < list.length; piece++){
                if(list[piece].x== i){
                    localSum += 1;
                }
            }
            if(localSum <= 3){
                sumOfConnections += 20;
            }
        }
        return sumOfConnections;
    }

    private int scoreConnectionsBlacks(Coordinate[] list) {
        /* initialization */
        int sumOfConnections = 0;

        Coordinate[][] friends = new Coordinate[list.length][9];

        for(int i = 0; i < list.length; i++) {
            friends[i] = makeHGrid(list[i]);
        }
        /* end of initialization */

        /* Add up scores from different strategies */
        sumOfConnections += awardForMaximizingConnections(list,friends);
        sumOfConnections += awardHomeRow(list);
        sumOfConnections += awardInCorner(list);
        sumOfConnections += awardPiecesNotNeighbors(list);
        sumOfConnections += awardIfNotTooManyInRow(list);
        sumOfConnections += awardIfNotTooManyInCol(list);
        /* end of strategizing */

        return sumOfConnections;
    }

    private int scoreConnectionsWhites(Coordinate[] list) {
        /* initialization */
        int sumOfConnections = 0;
        Coordinate[][] friends = new Coordinate[list.length][9];

        for(int i = 0; i < list.length; i++) {
            friends[i] = makeHGrid(list[i]);
        }
        /* end of initialization */

        /* Add up scores from different strategies */
        sumOfConnections += awardForMaximizingConnections(list,friends);
        sumOfConnections += awardHomeRow(list);
        sumOfConnections += awardInCorner(list);
        sumOfConnections += awardPiecesNotNeighbors(list);
        sumOfConnections += awardIfNotTooManyInRow(list);
        sumOfConnections += awardIfNotTooManyInCol(list);
        /* end of strategizing */

        return sumOfConnections;
    }

    /**
     * evaluator takes in two parameters, a player, and evaluates the this.Gameboard based
     * on the chances of winning for the player.
     *
     * @param player the player on the board to be evaluated
     *
     * @return an double representing the chances of winning for the player, 1 for guaranteed win, -1
     * for guaranteed loss, and a number in between for boards that are not either.
     */

    public double evaluator(int currDepth) {
        /*
            2. ratio of connections for all our pieces vs opponent pieces
            3. average distance between pieces 
                -> lower the average distance, lower the probability of being blocked and vice-versa
            4. 
        */
        // if(table.find(this) == null) {
        //     double blackSum = 0.0;
        //     double whiteSum = 0.0;
        //     Coordinate[] blacks = this.listBlacks();
        //     Coordinate[] whites = this.listWhites();
        //     for(int b = 0; b < blacks.length; b++) {
        //         blackSum += getScore(blacks[b]);
        //     }
        //     for(int w = 0; w < whites.length; w++) {
        //         whiteSum += getScore(whites[w]);
        //     }
        //     double blackAverage = blackSum/blacks.length;
        //     double whiteAverage = whiteSum/whites.length;

        //     table.insert(this, new Double(blackAverage - whiteAverage));
        //     return blackAverage - whiteAverage;
        // } else {
        //     return ((Double) table.find(this).value()).doubleValue();
        // }            
        // return (Math.random() * 2.0) - 1.0;
        int white_score = 0;
        int black_score = 0;
        Coordinate[] whites = listWhites();
        Coordinate[] blacks = listBlacks();
        int networkB = 0;
        int networkW = 0; 
        for(int i = 0; i < whites.length; i++){
            if(containsNetworkOfLength(whites[i],5)){
                //System.out.println("White Board with 5 connected chips");
                //System.out.println(this);
                //networkW+=20;
                white_score += 20;
                //continue;
            }

            if(containsNetworkOfLength(whites[i],4)){
                //System.out.println("White Board with 4 connected chips");
                //System.out.println(this);
                //networkW+=20;
                white_score += 10;
                //continue;
            }
            if(containsNetworkOfLength(whites[i],3)){
                //System.out.println("White Board with 3 connected chips");
                //System.out.println(this);
                //networkW+=20;
                white_score += 5;
                //continue;
            }
        }
        for(int i = 0; i < blacks.length; i++){
            if(containsNetworkOfLength(blacks[i],5)){
                //networkB+=20;
                black_score += 20;
            }

            if(containsNetworkOfLength(blacks[i],4)){
                //networkB+=20;
                black_score += 10;
            }
            if(containsNetworkOfLength(blacks[i],3)){
                //networkB+=20;
                black_score += 5;
            }
        }
        
        white_score += scoreConnectionsWhites(whites);
        black_score += scoreConnectionsBlacks(blacks);

        white_score += scoreBlocks(whites, 50);
        black_score += scoreBlocks(blacks, 50);

        try {
            if(containsNetwork(WHITE)){
                return -1000000000;
            }
            if(containsNetwork(BLACK)){
                return 1000000000;
            }
            //System.out.println("white: " + whites + "||" + "black: " + blacks);
            return (double)black_score - white_score;
        }
        catch (Exception e) {
            System.out.println(e);
            return 0.0;
        }
    }

/* ==============================  END OF EVALUATOR MODULE =============================*/

    public int hashCode() {
    // Replace the following line with your solution.
        int hash = 0;
        int multiple = 1;
        for(int j = 0; j < this.width; j++){
            for(int i = 0; i < this.height; i++){
                hash += this.board[i][j] * multiple;
                multiple = multiple * 3; 
            }
        }
        return hash;
    }


    /**
     * toString() returns a String representation of the board.
     */
    public String toString() {
        String current = "      0    1    2    3    4    5    6    7  " + "\n" + "   -----------------------------------------";
        for(int j = 0; j < this.height; j++) {
            String[] printColumn = new String[this.width];
            for(int i = 0; i < this.width; i++) {
                int typeAt = getType(new Coordinate(i,j));
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
        Gameboard yuxinGame = new Gameboard();
        //System.out.println(yuxinGame);
        //System.out.println(yuxinGame.evaluator(BLACKPLAYER));

        try {
            /*

            //verifying board dimensions
            assert yuxinGame.width == 8: "ERROR (Y): width of gameboard incorrect";
            assert yuxinGame.height == 8: "ERROR (Y): height of gameboard incorrect";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying corner squares are indeed INVALID
            assert yuxinGame.isValid(new Coordinate(0,0)) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(new Coordinate(0,7)) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(new Coordinate(7,0)) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(new Coordinate(7,7)) == false: "ERROR (S): invalid squares are valid";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying pieces can not be added to INVALID squares
            System.out.println("WARNING: AgainstRulesException in addPiece() is commented out");
            yuxinGame.addPiece(new Coordinate(0,0),BLACK);
            yuxinGame.addPiece(new Coordinate(0,7),BLACK);
            yuxinGame.addPiece(new Coordinate(7,0),BLACK);
            yuxinGame.addPiece(new Coordinate(7,7),BLACK);
            yuxinGame.addPiece(new Coordinate(0,0),WHITE);
            yuxinGame.addPiece(new Coordinate(0,7),WHITE);
            yuxinGame.addPiece(new Coordinate(7,0),WHITE);
            yuxinGame.addPiece(new Coordinate(7,7),WHITE);

            assert yuxinGame.isValid(new Coordinate(0,0)) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(new Coordinate(0,7)) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(new Coordinate(7,0)) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(new Coordinate(7,7)) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying pieces of opposing color can not be added to a player's home rows
            //WHITE's side
            System.out.println("WARNING: checkCount in addPiece() replaced with checkRulesExceptCount()");
            yuxinGame.addPiece(new Coordinate(0,1),BLACK);
            yuxinGame.addPiece(new Coordinate(0,2),BLACK);
            yuxinGame.addPiece(new Coordinate(0,3),BLACK);
            yuxinGame.addPiece(new Coordinate(0,4),BLACK);
            yuxinGame.addPiece(new Coordinate(0,5),BLACK);
            yuxinGame.addPiece(new Coordinate(0,6),BLACK);

            yuxinGame.addPiece(new Coordinate(7,1),BLACK);
            yuxinGame.addPiece(new Coordinate(7,2),BLACK);
            yuxinGame.addPiece(new Coordinate(7,2),BLACK);
            yuxinGame.addPiece(new Coordinate(7,3),BLACK);
            yuxinGame.addPiece(new Coordinate(7,4),BLACK);
            yuxinGame.addPiece(new Coordinate(7,5),BLACK);
            yuxinGame.addPiece(new Coordinate(7,6),BLACK);
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //BLACK's side
            yuxinGame.addPiece(new Coordinate(1,0),WHITE);
            yuxinGame.addPiece(new Coordinate(2,0),WHITE);
            yuxinGame.addPiece(new Coordinate(3,0),WHITE);
            yuxinGame.addPiece(new Coordinate(4,0),WHITE);
            yuxinGame.addPiece(new Coordinate(5,0),WHITE);
            yuxinGame.addPiece(new Coordinate(6,0),WHITE);

            yuxinGame.addPiece(new Coordinate(1,7),WHITE);
            yuxinGame.addPiece(new Coordinate(2,7),WHITE);
            yuxinGame.addPiece(new Coordinate(3,7),WHITE);
            yuxinGame.addPiece(new Coordinate(4,7),WHITE);
            yuxinGame.addPiece(new Coordinate(5,7),WHITE);
            yuxinGame.addPiece(new Coordinate(6,7),WHITE);
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying addPiece() without neighbor conflicts
            yuxinGame.addPiece(new Coordinate(6,0),BLACK);
            yuxinGame.addPiece(new Coordinate(6,5),BLACK);
            yuxinGame.addPiece(new Coordinate(5,5),BLACK);
            yuxinGame.addPiece(new Coordinate(3,3),BLACK);
            yuxinGame.addPiece(new Coordinate(3,5),BLACK);
            yuxinGame.addPiece(new Coordinate(5,7),BLACK);

            assert yuxinGame.getType(new Coordinate(6,0)) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(new Coordinate(6,5)) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(new Coordinate(5,5)) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(new Coordinate(3,3)) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(new Coordinate(3,5)) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(new Coordinate(5,7)) == BLACK: "ERROR (Y): square should be BLACK";

            assert yuxinGame.blackCount == 4: "ERROR: blackCount is incorrect";
            assert yuxinGame.isEmptyBoard() == false: "ERROR (Y): the board should have pieces on it";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying addPiece() with neighbor conflicts
            //BLACK piece initially at (6,0);

            //Test 1: exhaustive attempt to cause neighbor conflicts

            //Setup
            yuxinGame.addPiece(new Coordinate(6,1),BLACK);
            assert yuxinGame.getType(new Coordinate(6,1)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(1,7),BLACK);
            assert yuxinGame.getType(new Coordinate(1,7)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(6,7),BLACK);
            assert yuxinGame.getType(new Coordinate(6,7)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(2,1), BLACK);
            assert yuxinGame.getType(new Coordinate(2,1)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(1,0), BLACK);
            assert yuxinGame.getType(new Coordinate(1,0)) == BLACK: "ERROR (Y): square should be BLACK";

            yuxinGame.addPiece(new Coordinate(4,2), BLACK);
            assert yuxinGame.getType(new Coordinate(4,2)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(3,7), BLACK);
            assert yuxinGame.getType(new Coordinate(3,7)) == BLACK: "ERROR (Y): square should be BLACK";

            //System.out.println(yuxinGame);

            //Attempt to cause errors
            yuxinGame.addPiece(new Coordinate(2,0), BLACK);
            assert yuxinGame.getType(new Coordinate(2,0)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,0), BLACK);
            assert yuxinGame.getType(new Coordinate(5,0)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(1,1), BLACK);
            assert yuxinGame.getType(new Coordinate(1,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,1), BLACK);
            assert yuxinGame.getType(new Coordinate(3,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,1), BLACK);
            assert yuxinGame.getType(new Coordinate(5,1)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(new Coordinate(2,2), BLACK);
            assert yuxinGame.getType(new Coordinate(2,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,2), BLACK);
            assert yuxinGame.getType(new Coordinate(3,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,2), BLACK);
            assert yuxinGame.getType(new Coordinate(5,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(6,2), BLACK);
            assert yuxinGame.getType(new Coordinate(6,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(4,3), BLACK);
            assert yuxinGame.getType(new Coordinate(4,3)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(new Coordinate(2,4), BLACK);
            assert yuxinGame.getType(new Coordinate(2,4)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,4), BLACK);
            assert yuxinGame.getType(new Coordinate(3,4)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(4,4), BLACK);
            assert yuxinGame.getType(new Coordinate(4,4)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,4), BLACK);
            assert yuxinGame.getType(new Coordinate(5,4)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(6,4), BLACK);
            assert yuxinGame.getType(new Coordinate(6,4)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(new Coordinate(4,5), BLACK);
            assert yuxinGame.getType(new Coordinate(4,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(2,6), BLACK);
            assert yuxinGame.getType(new Coordinate(2,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,6), BLACK);
            assert yuxinGame.getType(new Coordinate(3,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(4,6), BLACK);
            assert yuxinGame.getType(new Coordinate(4,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,6), BLACK);
            assert yuxinGame.getType(new Coordinate(5,6)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(new Coordinate(6,6), BLACK);
            assert yuxinGame.getType(new Coordinate(6,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(2,7), BLACK);
            assert yuxinGame.getType(new Coordinate(2,7)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(4,7), BLACK);
            assert yuxinGame.getType(new Coordinate(4,7)) == EMPTY: "ERROR (Y): square should be EMPTY";

            //Test 2: randomized attempt to insert WHITE around BLACK pieces

            yuxinGame.addPiece(new Coordinate(3,1), WHITE);
            assert yuxinGame.getType(new Coordinate(3,1)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(3,2), WHITE);
            assert yuxinGame.getType(new Coordinate(3,2)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(4,5), WHITE);
            assert yuxinGame.getType(new Coordinate(4,5)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(5,6), WHITE);
            assert yuxinGame.getType(new Coordinate(5,6)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(7,1), WHITE);
            assert yuxinGame.getType(new Coordinate(7,1)) == WHITE: "ERROR (Y): square should be WHITE";

            yuxinGame.addPiece(new Coordinate(6,2), WHITE);
            assert yuxinGame.getType(new Coordinate(6,2)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(7,5), WHITE);
            assert yuxinGame.getType(new Coordinate(7,5)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(7,6), WHITE);
            assert yuxinGame.getType(new Coordinate(7,6)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(0,2), WHITE);
            assert yuxinGame.getType(new Coordinate(0,2)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(0,6), WHITE);
            assert yuxinGame.getType(new Coordinate(0,6)) == WHITE: "ERROR (Y): square should be WHITE";

            yuxinGame.addPiece(new Coordinate(1,5), WHITE);
            assert yuxinGame.getType(new Coordinate(1,5)) == WHITE: "ERROR (Y): square should be WHITE";

            //Attempt to cause errors
            yuxinGame.addPiece(new Coordinate(2,2), WHITE);
            assert yuxinGame.getType(new Coordinate(2,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(7,2), WHITE);
            assert yuxinGame.getType(new Coordinate(7,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(6,6), WHITE);
            assert yuxinGame.getType(new Coordinate(6,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(0,5), WHITE);
            assert yuxinGame.getType(new Coordinate(0,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(1,6), WHITE);
            assert yuxinGame.getType(new Coordinate(1,6)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(new Coordinate(4,3), WHITE);
            assert yuxinGame.getType(new Coordinate(4,3)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(2,6), WHITE);
            assert yuxinGame.getType(new Coordinate(2,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,2), WHITE);
            assert yuxinGame.getType(new Coordinate(5,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(5,1), WHITE);
            assert yuxinGame.getType(new Coordinate(5,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,6), WHITE);
            assert yuxinGame.getType(new Coordinate(3,6)) == EMPTY: "ERROR (Y): square should be EMPTY";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//

            //attempting to add an INVALID using addPiece(new Coordinate()
            yuxinGame.addPiece(new Coordinate(1,1), INVALID);
            assert yuxinGame.getType(new Coordinate(1,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(3,0), INVALID);
            assert yuxinGame.getType(new Coordinate(3,0)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(0,5), INVALID);
            assert yuxinGame.getType(new Coordinate(0,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(0,1), INVALID);
            assert yuxinGame.getType(new Coordinate(0,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(6,6), INVALID);
            assert yuxinGame.getType(new Coordinate(6,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(new Coordinate(4,1), INVALID);
            assert yuxinGame.getType(new Coordinate(4,1)) == EMPTY: "ERROR (Y): square should be EMPTY";

            //attempting to replace piece with an INVALID using addPiece(new Coordinate()
            yuxinGame.addPiece(new Coordinate(1,0), INVALID);
            assert yuxinGame.getType(new Coordinate(1,0)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(3,1), INVALID);
            assert yuxinGame.getType(new Coordinate(3,1)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(6,2), INVALID);
            assert yuxinGame.getType(new Coordinate(6,2)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(6,5), INVALID);
            assert yuxinGame.getType(new Coordinate(6,5)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(0,6), INVALID);
            assert yuxinGame.getType(new Coordinate(0,6)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(3,3), INVALID);
            assert yuxinGame.getType(new Coordinate(3,3)) == BLACK: "ERROR (Y): square should be BLACK";

            //attempting to replace piece with an EMPTY using addPiece(new Coordinate()
            yuxinGame.addPiece(new Coordinate(1,0), EMPTY);
            assert yuxinGame.getType(new Coordinate(1,0)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(3,1), EMPTY);
            assert yuxinGame.getType(new Coordinate(3,1)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(6,2), EMPTY);
            assert yuxinGame.getType(new Coordinate(6,2)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(6,5), EMPTY);
            assert yuxinGame.getType(new Coordinate(6,5)) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(new Coordinate(0,6), EMPTY);
            assert yuxinGame.getType(new Coordinate(0,6)) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(new Coordinate(3,3), EMPTY);
            assert yuxinGame.getType(new Coordinate(3,3)) == BLACK: "ERROR (Y): square should be BLACK";

            //attempting to add piece to out-of-bound coordinates using addPiece(new Coordinate()
            yuxinGame.addPiece(new Coordinate(100,100), BLACK);
            yuxinGame.addPiece(new Coordinate(-20,45), WHITE);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //checking removePiece(new Coordinate(()

            yuxinGame.removePiece(new Coordinate(6,0));
            assert yuxinGame.getType(new Coordinate(6,0)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(6,5));
            assert yuxinGame.getType(new Coordinate(6,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(5,5));
            assert yuxinGame.getType(new Coordinate(5,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(3,3));
            assert yuxinGame.getType(new Coordinate(3,3)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(3,5));
            assert yuxinGame.getType(new Coordinate(3,5)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(new Coordinate(5,7));
            assert yuxinGame.getType(new Coordinate(5,7)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(6,1));
            assert yuxinGame.getType(new Coordinate(6,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(1,7));
            assert yuxinGame.getType(new Coordinate(1,7)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(6,7));
            assert yuxinGame.getType(new Coordinate(6,7)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(2,1));
            assert yuxinGame.getType(new Coordinate(2,1)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(new Coordinate(1,0));
            assert yuxinGame.getType(new Coordinate(1,0)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(4,2));
            assert yuxinGame.getType(new Coordinate(4,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(3,7));
            assert yuxinGame.getType(new Coordinate(3,7)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(3,1));
            assert yuxinGame.getType(new Coordinate(3,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(3,2));
            assert yuxinGame.getType(new Coordinate(3,2)) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(new Coordinate(4,5));
            assert yuxinGame.getType(new Coordinate(4,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(5,6));
            assert yuxinGame.getType(new Coordinate(5,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(7,1));
            assert yuxinGame.getType(new Coordinate(7,1)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(6,2));
            assert yuxinGame.getType(new Coordinate(6,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(7,5));
            assert yuxinGame.getType(new Coordinate(7,5)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(7,6));

            assert yuxinGame.getType(new Coordinate(7,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(0,2));
            assert yuxinGame.getType(new Coordinate(0,2)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(0,6));
            assert yuxinGame.getType(new Coordinate(0,6)) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(1,5));
            assert yuxinGame.getType(new Coordinate(1,5)) == EMPTY: "ERROR (Y): square should be EMPTY";

            //attempting to remove INVALID squares
            yuxinGame.removePiece(new Coordinate(0,0));
            assert yuxinGame.getType(new Coordinate(0,0)) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(0,7));
            assert yuxinGame.getType(new Coordinate(0,7)) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(7,0));
            assert yuxinGame.getType(new Coordinate(7,0)) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(new Coordinate(7,7));
            assert yuxinGame.getType(new Coordinate(7,7)) == INVALID: "ERROR (Y): square should be EMPTY";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //Setup 
            yuxinGame.addPiece(new Coordinate(3,1), WHITE);
            yuxinGame.addPiece(new Coordinate(3,2), WHITE);
            yuxinGame.addPiece(new Coordinate(4,5), WHITE);
            yuxinGame.addPiece(new Coordinate(5,6), WHITE);
            yuxinGame.addPiece(new Coordinate(7,1), WHITE);

            yuxinGame.addPiece(new Coordinate(6,2), WHITE);
            yuxinGame.addPiece(new Coordinate(7,5), WHITE);
            yuxinGame.addPiece(new Coordinate(7,6), WHITE);
            yuxinGame.addPiece(new Coordinate(0,2), WHITE);
            yuxinGame.addPiece(new Coordinate(0,6), WHITE);

            yuxinGame.addPiece(new Coordinate(1,5), WHITE);
            yuxinGame.addPiece(new Coordinate(6,1), BLACK);
            yuxinGame.addPiece(new Coordinate(1,7), BLACK);
            yuxinGame.addPiece(new Coordinate(6,7), BLACK);
            yuxinGame.addPiece(new Coordinate(2,1), BLACK);

            yuxinGame.addPiece(new Coordinate(2,3), BLACK);
            yuxinGame.addPiece(new Coordinate(2,5), BLACK);
            yuxinGame.addPiece(new Coordinate(2,7), BLACK);
            yuxinGame.addPiece(new Coordinate(1,0), BLACK);
            yuxinGame.addPiece(new Coordinate(4,2), BLACK);

            yuxinGame.addPiece(new Coordinate(3,7), BLACK);
            yuxinGame.addPiece(new Coordinate(6,0), BLACK);
            yuxinGame.addPiece(new Coordinate(6,5), BLACK);
            yuxinGame.addPiece(new Coordinate(5,5), BLACK);
            yuxinGame.addPiece(new Coordinate(3,3), BLACK);

            yuxinGame.addPiece(new Coordinate(3,5), BLACK);
            yuxinGame.addPiece(new Coordinate(5,7), BLACK);

            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- Row/Column");
            //System.out.println(yuxinGame);

            //testing Connected Columns
            Coordinate[] c02 = {new Coordinate(0,0), new Coordinate(0,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(0,2)), c02): "Connected Column Error";
            Coordinate[] c06 = {new Coordinate(0,2), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(0,6)), c06): "Connected Column Error";
            Coordinate[] c10 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(1,0)), c10): "Connected Column Error";
            Coordinate[] c15 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(1,5)), c15): "Connected Column Error";
            Coordinate[] c17 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(1,7)), c17): "Connected Column Error";
            
            Coordinate[] c21 = {new Coordinate(0,0), new Coordinate(2,3)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(2,1)), c21): "Connected Column Error";
            Coordinate[] c23 = {new Coordinate(2,1), new Coordinate(2,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(2,3)), c23): "Connected Column Error";
            Coordinate[] c25 = {new Coordinate(2,3), new Coordinate(2,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(2,5)), c25): "Connected Column Error";
            Coordinate[] c27 = {new Coordinate(2,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(2,7)), c27): "Connected Column Error";
            Coordinate[] c31 = {new Coordinate(0,0), new Coordinate(3,2)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(3,1)), c31): "Connected Column Error";
            Coordinate[] c32 = {new Coordinate(3,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(3,2)), c32): "Connected Column Error";

            Coordinate[] c35 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(3,5)), c35): "Connected Column Error";
            Coordinate[] c42 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(4,2)), c42): "Connected Column Error";
            Coordinate[] c45 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(4,5)), c45): "Connected Column Error";
            Coordinate[] c55 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(5,5)), c55): "Connected Column Error";
            Coordinate[] c56 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(5,6)), c56): "Connected Column Error";

            Coordinate[] c57 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(5,7)), c57): "Connected Column Error";
            Coordinate[] c60 = {new Coordinate(0,0), new Coordinate(6,1)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(6,0)), c60): "Connected Column Error";
            Coordinate[] c61 = {new Coordinate(6,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(6,1)), c61): "Connected Column Error";
            Coordinate[] c62 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(6,2)), c62): "Connected Column Error";
            Coordinate[] c65 = {new Coordinate(0,0), new Coordinate(6,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(6,5)), c65): "Connected Column Error";

            Coordinate[] c67 = {new Coordinate(6,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(6,7)), c67): "Connected Column Error";
            Coordinate[] c71 = {new Coordinate(0,0), new Coordinate(7,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(7,1)), c71): "Connected Column Error";
            Coordinate[] c75 = {new Coordinate(7,1), new Coordinate(7,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(7,5)), c75): "Connected Column Error";
            Coordinate[] c76 = {new Coordinate(7,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedColumn(new Coordinate(7,6)), c76): "Connected Column Error";

            //---------------------------------------------------------------------/)/
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //testing Connected Rows
            Coordinate[] r02 = {new Coordinate(0,0), new Coordinate(3,2)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(0,2)), r02): "Connected Row Error";
            Coordinate[] r06 = {new Coordinate(0,0), new Coordinate(5,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(0,6)), r06): "Connected Row Error";
            Coordinate[] r10 = {new Coordinate(0,0), new Coordinate(6,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(1,0)), r10): "Connected Row Error";
            Coordinate[] r15 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(1,5)), r15): "Connected Row Error";
            Coordinate[] r17 = {new Coordinate(0,0), new Coordinate(2,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(1,7)), r17): "Connected Row Error";
            
            Coordinate[] r21 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(2,1)), r21): "Connected Row Error";
            Coordinate[] r23 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(2,3)), r23): "Connected Row Error";
            Coordinate[] r25 = {new Coordinate(0,0), new Coordinate(3,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(2,3)), r23): "Connected Row Error";
            Coordinate[] r27 = {new Coordinate(1,7), new Coordinate(5,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(2,7)), r27): "Connected Row Error";
            Coordinate[] r31 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(3,1)), r31): "Connected Row Error";
            Coordinate[] r32 = {new Coordinate(0,2), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(3,2)), r32): "Connected Row Error";

            Coordinate[] r35 = {new Coordinate(2,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(3,5)), r35): "Connected Row Error";
            Coordinate[] r42 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(4,2)), r42): "Connected Row Error";
            Coordinate[] r45 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(4,5)), r45): "Connected Row Error";
            Coordinate[] r55 = {new Coordinate(0,0), new Coordinate(6,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(5,5)), r55): "Connected Row Error";
            Coordinate[] r56 = {new Coordinate(0,6), new Coordinate(7,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(5,6)), r56): "Connected Row Error";

            Coordinate[] r57 = {new Coordinate(2,7), new Coordinate(6,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(5,7)), r57): "Connected Row Error";
            Coordinate[] r60 = {new Coordinate(1,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(6,0)), r60): "Connected Row Error";
            Coordinate[] r61 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(6,1)), r61): "Connected Row Error";
            Coordinate[] r62 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(6,2)), r62): "Connected Row Error";
            Coordinate[] r65 = {new Coordinate(5,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(6,5)), r65): "Connected Row Error";

            Coordinate[] r67 = {new Coordinate(5,7), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(6,7)), r67): "Connected Row Error";
            Coordinate[] r71 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(7,1)), r71): "Connected Row Error";
            Coordinate[] r75 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(7,5)), r75): "Connected Row Error";
            Coordinate[] r76 = {new Coordinate(5,6), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRow(new Coordinate(7,6)), r76): "Connected Row Error";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
    
            //testing Connected LDiagonal
            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- LDiagonal");
            //System.out.println(yuxinGame);

            Coordinate[] ld02 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(0,2)), ld02): "Connected LDiagonal Error";
            Coordinate[] ld06 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(0,6)), ld06): "Connected LDiagonal Error";
            Coordinate[] ld10 = {new Coordinate(0,0), new Coordinate(2,1)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(1,0)), ld10): "Connected LDiagonal Error";
            Coordinate[] ld15 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(1,5)), ld15): "Connected LDiagonal Error";
            Coordinate[] ld17 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(1,7)), ld17): "Connected LDiagonal Error";
            
            Coordinate[] ld21 = {new Coordinate(1,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(2,1)), ld21): "Connected LDiagonal Error";
            Coordinate[] ld23 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(2,3)), ld23): "Connected LDiagonal Error";
            Coordinate[] ld25 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(2,3)), ld23): "Connected LDiagonal Error";
            Coordinate[] ld27 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(2,7)), ld27): "Connected LDiagonal Error";
            Coordinate[] ld31 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(3,1)), ld31): "Connected LDiagonal Error";
            Coordinate[] ld32 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(3,2)), ld32): "Connected LDiagonal Error";

            Coordinate[] ld35 = {new Coordinate(0,0), new Coordinate(5,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(3,5)), ld35): "Connected LDiagonal Error";
            Coordinate[] ld42 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(4,2)), ld42): "Connected LDiagonal Error";
            Coordinate[] ld45 = {new Coordinate(0,0), new Coordinate(5,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(4,5)), ld45): "Connected LDiagonal Error";
            Coordinate[] ld55 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(5,5)), ld55): "Connected LDiagonal Error";
            Coordinate[] ld56 = {new Coordinate(4,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(5,6)), ld56): "Connected LDiagonal Error";

            Coordinate[] ld57 = {new Coordinate(3,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(5,7)), ld57): "Connected LDiagonal Error";
            Coordinate[] ld60 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(6,0)), ld60): "Connected LDiagonal Error";
            Coordinate[] ld61 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(6,1)), ld61): "Connected LDiagonal Error";
            Coordinate[] ld62 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(6,2)), ld62): "Connected LDiagonal Error";
            Coordinate[] ld65 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(6,5)), ld65): "Connected LDiagonal Error";

            Coordinate[] ld67 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(6,7)), ld67): "Connected LDiagonal Error";
            Coordinate[] ld71 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(7,1)), ld71): "Connected LDiagonal Error";
            Coordinate[] ld75 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(7,5)), ld75): "Connected LDiagonal Error";
            Coordinate[] ld76 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedLDiagonal(new Coordinate(7,6)), ld76): "Connected LDiagonal Error";


            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //testing Connected RDiagonal
            yuxinGame.addPiece(new Coordinate(5,3), BLACK);
            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- RDiagonal");
            //System.out.println(yuxinGame);

            Coordinate[] rd02 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(0,2)), rd02): "Connected RDiagonal Error";
            Coordinate[] rd06 = {new Coordinate(1,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(0,6)), rd06): "Connected RDiagonal Error";
            Coordinate[] rd10 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(1,0)), rd10): "Connected RDiagonal Error";
            //System.out.println(yuxinGame.getType(new Coordinate(0,6));
            //for (int i = 0; i < 7; i++) {
                //System.out.print(yuxinGame.getDiagonal(1,5,1)[i] + " |");
            //}
            Coordinate[] rd15 = {new Coordinate(0,0), new Coordinate(0,6)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(1,5)), rd15): "Connected RDiagonal Error";
            Coordinate[] rd17 = {new Coordinate(3,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(1,7)), rd17): "Connected RDiagonal Error";
            
            Coordinate[] rd21 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(2,1)), rd21): "Connected RDiagonal Error";
            Coordinate[] rd23 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(2,3)), rd23): "Connected RDiagonal Error";
            Coordinate[] rd25 = {new Coordinate(6,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(2,3)), rd23): "Connected RDiagonal Error";
            Coordinate[] rd27 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(2,7)), rd27): "Connected RDiagonal Error";
            Coordinate[] rd31 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(3,1)), rd31): "Connected RDiagonal Error";
            Coordinate[] rd32 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(3,2)), rd32): "Connected RDiagonal Error";

            Coordinate[] rd35 = {new Coordinate(5,3),new Coordinate(1,7)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(3,5)), rd35): "Connected RDiagonal Error";
            Coordinate[] rd42 = {new Coordinate(6,0),new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(4,2)), rd42): "Connected RDiagonal Error";
            Coordinate[] rd45 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(4,5)), rd45): "Connected RDiagonal Error";
            Coordinate[] rd53 = {new Coordinate(0,0), new Coordinate(3,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(5,3)), rd53): "Connected RDiagonal Error";
            Coordinate[] rd55 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(5,5)), rd55): "Connected RDiagonal Error";
            Coordinate[] rd56 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(5,6)), rd56): "Connected RDiagonal Error";

            Coordinate[] rd57 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(5,7)), rd57): "Connected RDiagonal Error";
            Coordinate[] rd60 = {new Coordinate(0,0), new Coordinate(4,2)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(6,0)), rd60): "Connected RDiagonal Error";
            Coordinate[] rd61 = {new Coordinate(0,0), new Coordinate(2,5)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(6,1)), rd61): "Connected RDiagonal Error";
            Coordinate[] rd62 = {new Coordinate(7,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(6,2)), rd62): "Connected RDiagonal Error";
            Coordinate[] rd65 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(6,5)), rd65): "Connected RDiagonal Error";

            Coordinate[] rd67 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(6,7)), rd67): "Connected RDiagonal Error";
            Coordinate[] rd71 = {new Coordinate(0,0), new Coordinate(6,2)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(7,1)), rd71): "Connected RDiagonal Error";
            Coordinate[] rd75 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(7,5)), rd75): "Connected RDiagonal Error";
            Coordinate[] rd76 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(yuxinGame.findConnectedRDiagonal(new Coordinate(7,6)), rd76): "Connected RDiagonal Error";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //Diagonal Doublecheck

            Gameboard zhuGame = new Gameboard();
            zhuGame.addPiece(new Coordinate(5,5),BLACK);
            zhuGame.addPiece(new Coordinate(3,7),BLACK);
            zhuGame.addPiece(new Coordinate(6,4),BLACK);
            zhuGame.addPiece(new Coordinate(2,0),BLACK);
            zhuGame.addPiece(new Coordinate(1,5),BLACK);
            zhuGame.addPiece(new Coordinate(6,6),WHITE);
            zhuGame.addPiece(new Coordinate(4,4),WHITE);
            zhuGame.addPiece(new Coordinate(1,1), WHITE);
            zhuGame.addPiece(new Coordinate(0,2), WHITE);
            zhuGame.addPiece(new Coordinate(7,3), WHITE);
            //System.out.println(zhuGame);

            Coordinate[] ldd02 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(0,2)), ldd02): "Connected LDiagonal Error";
            Coordinate[] ldd11 = {new Coordinate(0,0), new Coordinate(4,4)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(1,1)), ldd11): "Connected LDiagonal Error";
            Coordinate[] ldd15 = {new Coordinate(0,0), new Coordinate(3,7)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(1,5)), ldd15): "Connected LDiagonal Error";
            Coordinate[] ldd20 = {new Coordinate(0,0), new Coordinate(6,4)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(2,0)), ldd20): "Connected LDiagonal Error";
            Coordinate[] ldd37 = {new Coordinate(1,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(3,7)), ldd37): "Connected LDiagonal Error";

            Coordinate[] ldd44 = {new Coordinate(1,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(4,4)), ldd44): "Connected LDiagonal Error";
            Coordinate[] ldd55 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(5,5)), ldd55): "Connected LDiagonal Error";
            Coordinate[] ldd64 = {new Coordinate(2,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(6,4)), ldd64): "Connected LDiagonal Error";
            Coordinate[] ldd66 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(6,6)), ldd66): "Connected LDiagonal Error";
            Coordinate[] ldd73 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedLDiagonal(new Coordinate(7,3)), ldd73): "Connected LDiagonal Error";   

            Coordinate[] rdd02 = {new Coordinate(1,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(0,2)), rdd02): "Connected RDiagonal Error";
            Coordinate[] rdd11 = {new Coordinate(0,0), new Coordinate(0,2)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(1,1)), rdd11): "Connected RDiagonal Error";
            Coordinate[] rdd15 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(1,5)), rdd15): "Connected RDiagonal Error";
            Coordinate[] rdd20 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(2,0)), rdd20): "Connected RDiagonal Error";
            Coordinate[] rdd37 = {new Coordinate(5,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(3,7)), rdd37): "Connected RDiagonal Error";

            Coordinate[] rdd44 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(4,4)), rdd44): "Connected RDiagonal Error";
            Coordinate[] rdd55 = {new Coordinate(6,4), new Coordinate(3,7)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(5,5)), rdd55): "Connected RDiagonal Error";
            Coordinate[] rdd64 = {new Coordinate(0,0), new Coordinate(5,5)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(6,4)), rdd64): "Connected RDiagonal Error";
            Coordinate[] rdd66 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(6,6)), rdd66): "Connected RDiagonal Error";
            Coordinate[] rdd73 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(zhuGame.findConnectedRDiagonal(new Coordinate(7,3)), rdd73): "Connected RDiagonal Error"; 

            Gameboard cGame = new Gameboard();
            cGame.addPiece(new Coordinate(0,6),WHITE);
            cGame.addPiece(new Coordinate(1,5),WHITE);
            cGame.addPiece(new Coordinate(4,2),WHITE);
            cGame.addPiece(new Coordinate(5,1),WHITE);
            cGame.addPiece(new Coordinate(6,1),WHITE);
            cGame.addPiece(new Coordinate(1,7),WHITE);
            cGame.addPiece(new Coordinate(1,1),WHITE);
            cGame.addPiece(new Coordinate(6,6), WHITE);
            cGame.addPiece(new Coordinate(6,5), WHITE);
            cGame.addPiece(new Coordinate(2,1), WHITE);
            //System.out.println("************************************************************");
            //System.out.println("cGame");
            //System.out.println(cGame);

            Coordinate[] lddd06 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(0,6)), lddd06): "Connected LDiagonal Error";
            Coordinate[] lddd11 = {new Coordinate(0,0), new Coordinate(6,6)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(1,1)), lddd11): "Connected LDiagonal Error";
            Coordinate[] lddd15 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(1,5)), lddd15): "Connected LDiagonal Error";
            Coordinate[] lddd21 = {new Coordinate(0,0), new Coordinate(6,5)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(2,1)), lddd21): "Connected LDiagonal Error";
            Coordinate[] lddd42 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(4,2)), lddd42): "Connected LDiagonal Error";
            Coordinate[] lddd51 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(5,1)), lddd51): "Connected LDiagonal Error";

            Coordinate[] lddd65 = {new Coordinate(2,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(6,5)), lddd65): "Connected LDiagonal Error";
            Coordinate[] lddd66 = {new Coordinate(1,1), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedLDiagonal(new Coordinate(6,6)), lddd66): "Connected LDiagonal Error";

            Coordinate[] rddd06 = {new Coordinate(1,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(0,6)), rddd06): "Connected RDiagonal Error";
            Coordinate[] rddd11 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(1,1)), rddd11): "Connected RDiagonal Error";
            Coordinate[] rddd15 = {new Coordinate(4,2), new Coordinate(0,6)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(1,5)), rddd15): "Connected RDiagonal Error";
            Coordinate[] rddd21 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(2,1)), rddd21): "Connected RDiagonal Error";
            Coordinate[] rddd42 = {new Coordinate(5,1), new Coordinate(1,5)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(4,2)), rddd42): "Connected RDiagonal Error";
            Coordinate[] rddd51 = {new Coordinate(0,0), new Coordinate(4,2)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(5,1)), rddd51): "Connected RDiagonal Error";

            Coordinate[] rddd65 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(6,5)), rddd65): "Connected RDiagonal Error";
            Coordinate[] rddd66 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(cGame.findConnectedRDiagonal(new Coordinate(6,6)), rddd66): "Connected RDiagonal Error";

            Gameboard dGame = new Gameboard();
            dGame.addPiece(new Coordinate(1,7),BLACK);
            dGame.addPiece(new Coordinate(2,6),BLACK);
            dGame.addPiece(new Coordinate(6,7),BLACK);
            dGame.addPiece(new Coordinate(5,6),BLACK);
            dGame.addPiece(new Coordinate(7,6),WHITE);
            dGame.addPiece(new Coordinate(6,5),WHITE);
            //System.out.println("************************************************************");
            //System.out.println("dGame");
            //System.out.println(dGame);

            Coordinate[] ldddd17 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(1,7)), ldddd17): "Connected LDiagonal Error";
            Coordinate[] ldddd26 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(2,6)), ldddd26): "Connected LDiagonal Error";
            Coordinate[] ldddd56 = {new Coordinate(0,0), new Coordinate(6,7)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(5,6)), ldddd56): "Connected LDiagonal Error";
            Coordinate[] ldddd65 = {new Coordinate(0,0), new Coordinate(7,6)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(6,5)), ldddd65): "Connected LDiagonal Error";
            Coordinate[] ldddd67 = {new Coordinate(5,6), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(6,7)), ldddd67): "Connected LDiagonal Error";
            Coordinate[] ldddd76 = {new Coordinate(6,5), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedLDiagonal(new Coordinate(7,6)), ldddd76): "Connected LDiagonal Error";

            Coordinate[] rdddd17 = {new Coordinate(2,6), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(1,7)), rdddd17): "Connected RDiagonal Error";
            Coordinate[] rdddd26 = {new Coordinate(0,0), new Coordinate(1,7)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(2,6)), rdddd26): "Connected RDiagonal Error";
            Coordinate[] rdddd56 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(5,6)), rdddd56): "Connected RDiagonal Error";
            Coordinate[] rdddd65 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(6,5)), rdddd65): "Connected RDiagonal Error";
            Coordinate[] rdddd67 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(6,7)), rdddd67): "Connected RDiagonal Error";
            Coordinate[] rdddd76 = {new Coordinate(0,0), new Coordinate(0,0)};
            assert Coordinate.deepEquals(dGame.findConnectedRDiagonal(new Coordinate(7,6)), rdddd76): "Connected RDiagonal Error";

            Gameboard sanchitGame = new Gameboard();
            sanchitGame.addPiece(new Coordinate(0,1), WHITE);
            sanchitGame.addPiece(new Coordinate(0,2), WHITE);
            sanchitGame.addPiece(new Coordinate(0,3), WHITE);
            sanchitGame.addPiece(new Coordinate(0,4), WHITE);
            sanchitGame.addPiece(new Coordinate(0,5), WHITE);
            sanchitGame.addPiece(new Coordinate(0,6), WHITE);
            
            sanchitGame.addPiece(new Coordinate(1,1), WHITE);
            sanchitGame.addPiece(new Coordinate(2,2), WHITE);
            sanchitGame.addPiece(new Coordinate(3,3), WHITE);
            sanchitGame.addPiece(new Coordinate(4,4), WHITE);
            sanchitGame.addPiece(new Coordinate(5,5), WHITE);
            sanchitGame.addPiece(new Coordinate(6,6), WHITE);

            sanchitGame.addPiece(new Coordinate(7,1), WHITE);
            sanchitGame.addPiece(new Coordinate(7,2), WHITE);
            sanchitGame.addPiece(new Coordinate(7,3), WHITE);
            sanchitGame.addPiece(new Coordinate(7,4), WHITE);
            sanchitGame.addPiece(new Coordinate(7,5), WHITE);
            sanchitGame.addPiece(new Coordinate(7,6), WHITE);
            sanchitGame.addPiece(new Coordinate(5,7), WHITE);

            //System.out.println(sanchitGame);
            
            assert sanchitGame.getDiagonalLength(new Coordinate(0,0), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,1), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,2), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,3), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,4), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,5), -1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,6), -1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,7), -1) == 1: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(1,0), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,1), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,2), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,3), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,4), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,5), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,6), -1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,7), -1) == 2: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(2,0), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,1), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,2), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,3), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,4), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,5), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,6), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,7), -1) == 3: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(3,0), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,1), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,2), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,3), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,4), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,5), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,6), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,7), -1) == 4: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(4,0), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,1), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,2), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,3), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,4), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,5), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,6), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,7), -1) == 5: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(5,0), -1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,1), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,2), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,3), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,4), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,5), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,6), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,7), -1) == 6: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(6,0), -1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,1), -1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,2), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,3), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,4), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,5), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,6), -1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,7), -1) == 7: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(7,0), -1) == 1: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,1), -1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,2), -1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,3), -1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,4), -1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,5), -1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,6), -1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,7), -1) == 8: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(0,0), 1) == 1: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,1), 1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,2), 1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,3), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,4), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,5), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,6), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(0,7), 1) == 8: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(1,0), 1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,1), 1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,2), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,3), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,4), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,5), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,6), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(1,7), 1) == 7: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(2,0), 1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,1), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,2), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,3), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,4), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,5), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,6), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(2,7), 1) == 6: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(3,0), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,1), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,2), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,3), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,4), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,5), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,6), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(3,7), 1) == 5: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(4,0), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,1), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,2), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,3), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,4), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,5), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,6), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(4,7), 1) == 4: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(5,0), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,1), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,2), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,3), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,4), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,5), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,6), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(5,7), 1) == 3: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(6,0), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,1), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,2), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,3), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,4), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,5), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,6), 1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(6,7), 1) == 2: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(new Coordinate(7,0), 1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,1), 1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,2), 1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,3), 1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,4), 1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,5), 1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,6), 1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(new Coordinate(7,7), 1) == 1: "ERROR (S): getDiagonalLength is wrong";   
            
            System.out.println(sanchitGame);
            */

            //Noah's tests for containsNetwork and associated functions
            System.out.println("Testing SList.contains with Integers and Coordinates.");

            SList noah = new SList();
            noah.insertFront(new Integer(3));
            noah.insertFront(new Integer(7));
            noah.insertFront(new Integer(10));
            noah.insertFront(new Integer(1));

            assert noah.contains(new Integer(3)): "ERROR: contains doesnt work right.";
            assert noah.contains(new Integer(10)): "ERROR: contains doesnt work right.";
            assert !noah.contains(new Integer(67)): "ERROR: contains doesnt work right.";

            noah.front().remove();

            assert !noah.contains(new Integer(1)): "ERROR: contains doesnt work right.";

            assert (new Coordinate(0,0)).equals(new Coordinate(0,0)): "Problem with equals!";

            noah = new SList();
            noah.insertFront(new Coordinate(0, 2));
            noah.insertFront(new Coordinate(0, 0));
            noah.insertFront(new Coordinate(3, 7));
            noah.insertFront(new Coordinate(8, 10));

            assert noah.contains(new Coordinate(0, 0)): "Error: contains es no bueno";
            assert !noah.contains(new Coordinate(1, 1)): "Error: contains es no bueno";

            System.out.println("Testing findConnectingChips in a test Gameboard.");

            Gameboard noahgame = new Gameboard();

            noahgame.addPiece(new Coordinate(6,5), BLACK);
            noahgame.addPiece(new Coordinate(6,0), BLACK);
            noahgame.addPiece(new Coordinate(5,5), BLACK);
            noahgame.addPiece(new Coordinate(3,3), BLACK);
            noahgame.addPiece(new Coordinate(3,5), BLACK);
            noahgame.addPiece(new Coordinate(5,7), BLACK);

            //System.out.println("The gameboard looks like this:");
            //System.out.println(noahgame);
            //System.out.println();

            Coordinate[] neighbors = noahgame.findConnectingChips(new Coordinate(5, 5));

            // System.out.println("Neighbors of 5,5: "+neighbors);
            
            assert neighbors.length == 8:"Error: fCC is no bueno";
            // System.out.println("neighbors[0] is: "+neighbors[0]);
            assert neighbors[0].equals(new Coordinate(3,5)): "Error: fCC no bueno";
            assert neighbors[1].equals(new Coordinate(6,5)): "Error: fCC no bueno";
            assert neighbors[2].equals(new Coordinate(0,0)): "Error: fCC no bueno";
            assert neighbors[3].equals(new Coordinate(5,7)): "Error: fCC no bueno";
            assert neighbors[4].equals(new Coordinate(3,3)): "Error: fCC no bueno";
            assert neighbors[5].equals(new Coordinate(0,0)): "Error: fCC no bueno";
            assert neighbors[6].equals(new Coordinate(0,0)): "Error: fCC no bueno";
            assert neighbors[7].equals(new Coordinate(0,0)): "Error: fCC no bueno";

            noahgame.addPiece(new Coordinate(4,5), Gameboard.WHITE); 
            neighbors = noahgame.findConnectingChips(new Coordinate(5, 5));

            // System.out.println("Now the gameboard looks like this:");
            // System.out.println(noahgame);
            // System.out.println();
            // System.out.println("Neighbors of 5,5: "+neighbors);

            assert neighbors.length == 8:"Error: fCC is no bueno";
            assert neighbors[0].equals(new Coordinate(0,0)): "Error: fCC no bueno";
            assert neighbors[1].equals(new Coordinate(6,5)): "Error: fCC no bueno";

            System.out.println("Testing containsNetwork.");
            // System.out.println("The gameboard: ");
            // System.out.println(noahgame);

            assert !noahgame.containsNetwork(WHITEPLAYER): "Error with containsNetwork.";
            assert noahgame.containsNetwork(BLACKPLAYER): "Error with containsNetwork.";

            noahgame.addPiece(new Coordinate(7,1), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(4,1), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(6,3), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(5,3), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(3,1), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(1,3), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(0,4), Gameboard.WHITE); //not yet, but will break if im right about implementing the turns rule

            // System.out.println("The board has changed! It is now:");
            // System.out.println(noahgame);

            assert !noahgame.containsNetwork(WHITEPLAYER): "Error with containsNetwork.";

            noahgame.addPiece(new Coordinate(1,6), Gameboard.WHITE);
            noahgame.addPiece(new Coordinate(0,6), Gameboard.WHITE);

            // System.out.println("The board has changed again!! It is now:");
            // System.out.println(noahgame);

            assert noahgame.containsNetwork(WHITEPLAYER): "Error with containsNetwork.";

            //readme tests

            noahgame = new Gameboard();
            //60 - 65 - 55 - 33 - 35 - 57, should be legal
            noahgame.addPiece(new Coordinate(6,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(6,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,7), Gameboard.BLACK);

            assert noahgame.containsNetwork(BLACKPLAYER);

            noahgame = new Gameboard();
            //20 - 25 - 35 - 13 - 33 - 55 - 57, should be legal
            noahgame.addPiece(new Coordinate(2,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(2,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(1,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,7), Gameboard.BLACK);

            assert noahgame.containsNetwork(BLACKPLAYER);

            //60 - 20 - 42 - 33 - 35 - 57, should be illegal
            noahgame = new Gameboard();
            noahgame.addPiece(new Coordinate(6,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(2,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(4,2), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,7), Gameboard.BLACK);

            // System.out.println("The board has changed! It is now:");
            // System.out.println(noahgame);

            assert !noahgame.containsNetwork(BLACKPLAYER);

            //20 - 42 - 60 - 65 - 55 - 57, should be illegal
            noahgame = new Gameboard();
            noahgame.addPiece(new Coordinate(2,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(4,2), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(6,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(6,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,7), Gameboard.BLACK);

            assert !noahgame.containsNetwork(BLACKPLAYER);

            //20 - 25 - 35 - 33 - 55 - 35 - 57, should be illegal
            noahgame = new Gameboard();
            noahgame.addPiece(new Coordinate(2,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(2,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(5,7), Gameboard.BLACK);

            // System.out.println("The board has changed! It is now:");
            // System.out.println(noahgame);

            assert noahgame.containsNetwork(BLACKPLAYER);

            //60 - 42 - 33 - 35 - 25 - 27, should be illegal
            noahgame = new Gameboard();
            noahgame.addPiece(new Coordinate(6,0), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(4,2), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,3), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(3,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(2,5), Gameboard.BLACK);
            noahgame.addPiece(new Coordinate(2,7), Gameboard.BLACK);

            assert !noahgame.containsNetwork(BLACKPLAYER);

            //end noah tests

            //System.out.println(new Coordinate(2,1));
	       System.out.println("All tests passed!");
        } catch(AgainstRulesException e) {
            System.out.println(e);
        } catch(InvalidNodeException e) {
            System.out.println(e);
        }

    }
}


        


