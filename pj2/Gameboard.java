/* Gameboard */

import java.util.*;
import player.*;
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

    public static final int MIN_DEPTH = 6;

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
            toReturn[0][0] = get00(c.x,c.y);
            toReturn[0][1] = get01(c.x,c.y);
            toReturn[0][2] = get02(c.x,c.y);
            toReturn[1][0] = get10(c.x,c.y);
            toReturn[1][1] = INVALID;
            toReturn[1][2] = get12(c.x,c.y);
            toReturn[2][0] = get20(c.x,c.y);
            toReturn[2][1] = get21(c.x,c.y);
            toReturn[2][2] = get22(c.x,c.y);
        } else {
            System.out.println("Invalid x,y given in getNeighbors x: "+c.x+" y: "+c.y+" .");
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
        if(y+1 <= this.width-1){
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
        if(getType(c.x,c.y) != INVALID){
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
        if(getType(c.x,c.y) == EMPTY){
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
        if(getType(c.x, c.y) == WHITE){
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
        if(getType(c.x, c.y) == BLACK){
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
     * @return a Coordinate[] containing squares in the same diagonal as the square.
     */

    private Coordinate[] getDiagonal(Coordinate c, int direction) {
        int diagonalLength = getDiagonalLength(c, direction);
        int diagonalChips[] = new int[diagonalLength];
        int startX = c.x;
        int startY = c.y;
        if(direction == -1){
            if(c.x > c.y){
                startY = 0;
                startX = c.x-c.y;
            } else if(y > x){
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
                connectedChips[1] = new Coordinate(c.x - i, c.y - i);
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

        int[] row = getRow(c);
        int length = row.length;
        int location = c.x;

        for (int i = 1; location - i >= 0; i++) {
            if (row[location - i] == type) {

                connectedChips[0] = new Coordinate(x-i, y);
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

                connectedChips[1] = new Coordinate(x+i, y);
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
     * findConnectedColumn() takes 2 parameters, the coordinates, and returns a list of connected squares of the same type in the same column.
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

        int[] column = getColumn(c);
        int length = column.length;
        int location = c.y;

        for (int i = 1; location - i >= 0; i++) {
            if (column[location - i] == type) {
                connectedChips[0] = new Coordinate(x,y-i);
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
                connectedColumn[1] = new Coordinate(x, y+i);
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
     * findConnectingChips() takes 2 parameters, the coordinates, and returns a list of square ints 
     * that the square is "connected" to.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a int[] containing chips that is connected to given square
     */

    private int[] findConnectingChips(int x, int y) {
        int temp[] = new int[2];
        return temp;
    }

    /**
     * addPiece(), takes three parameters, and changes the type of the
     * indicated square on "this" Gameboard at the coordinates (x,y) to
     * the specified type.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the new type of the square
     */

    private void addPiece(int x, int y, int type) throws AgainstRulesException {
        //if (checkRules(x,y,type)) { //YOU NEED TO TURN THIS ON!
        if (checkRulesExceptCount(x,y,type)) {
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
     * checkRules(), takes two parameters, the x-coordinate,  y-coordinate, and a type, and verifies
     * all the check methods. Namely, it verifies that checkDimensions(), checkPiece(), checkCount(), checkSquare(), and checkNeighbors()
     * returns true.
     * 
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type of the piece being considered
     *
     * @return true if all of the above tests return true, false otherwise.
     */

    private boolean checkRules(int x, int y, int type) {
        if (checkCount(x,y,type) && ((checkDimensions(x,y) && checkPiece(type)) && (checkSquare(x,y,type) && checkNeighbors(x,y,type)))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkRulesExcept Count(), takes two parameters, the x-coordinate,  y-coordinate, and a type, and verifies
     * all the check methods. Namely, it verifies that checkDimensions(), checkPiece(), checkSquare(), and checkNeighbors()
     * returns true. Important: This method is for testing only, it does not check checkCount().
     * 
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type of the piece being considered
     *
     * @return true if all of the above tests return true, false otherwise.
     */

    private boolean checkRulesExceptCount(int x, int y, int type) {
        if ((checkDimensions(x,y) && checkPiece(type)) && (checkSquare(x,y,type) && checkNeighbors(x,y,type))) {
            return true;
        } else {
            return false;
        }
    }

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
     * that the square is not paired with a coordinate where the piece is not allowed. If the type is INVALID, automatically
     * returns false. An INVALID is not allowed anywhere! If the coordinates is at an INVALID spot, also returns false.
     * 
     * (RULE #1 and #2)
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the piece to be inspected at (x,y)
     *
     * @return true if the piece can legitimately be placed on (x,y), false otherwise.
     */

    private boolean checkSquare(int x, int y, int type) {
        if (isEmpty(x,y)) { //if (x,y) is valid, and the type of square is valid
            if (type == BLACK) { //a BLACK can not be placed on the left/right edges
                if (x != 0 && x != (this.width - 1)) { //if x-coordinate is not 0 and its not width - 1
                    return true;
                } else {
                    return false;
                }
            }
            if (type == WHITE) { //a WHITE can not be placed on the top/bottom edges
                if (y != 0 && y != (this.height - 1)) { //if x-coordinate is not 0 and its not width - 1
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
     * checkCount(), takes two parameters, the x-coordinate,  y-coordinate, and a type, and verifies
     * number of pieces remaining is greater than zero.
     * 
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type of the piece being considered
     *
     * @return true if the number of pieces remaining of type is greater than zero, false otherwise
     */

    private boolean checkCount(int x, int y, int type) {
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
     * pieceCheck(), takes one parameter, a type of a piece, returns whether or not the type is {BLACK, WHITE}
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
     * countNeighbor() takes in three parameters, an x-coordinate and y-coordinate, and a type and return the number of neighbors
     * around it, excluding itself.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the type of the piece to be inspected
     *
     * @return the number of neighbors surrounding the specified piece, excluding itself,
     */

    private int countNeighbors(int x, int y, int type) {
        int count = 0;
        int[][] neighbors = getNeighbors(x, y);
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
     * locateNeighbor() takes in three parameters, an x-coordinate, y-coordinate on the board and a type, adjusts 
     * the coordinates of 2-D array returned from getNeighbor() to overlay the coordinates of the piece on this.Gameboard. Only
     * returns the first neighbor it finds.
     *
     * @param x1 the x-coordinate of the square on the gameboard
     * @param y1 the y-coordinate of the square on the gameboard
     * @param type the type of the square we are inspecting
     *
     * @return an array of length 2, containing the coordinates of first neighbor on the this.Gameboard. The first index is the x-coordinate,
     * and the second index is the y-coordinates.
     */

    private int[] locateNeighbors(int x, int y, int type) {
        int[] location = new int[2];
        int[][] neighbors = getNeighbors(x, y);
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
                    location[0] = x + i - 1; //true location
                    location[1] = y + j - 1; //true location
                }
            }
        }

        //System.out.println(location[0] + ", " + location[1]);
        return location;
    }


    /**
     * checkNeighbors() takes in three parameters, an x-coordinate and y-coordinate, and a piece and checks whether placing the piece there
     * would violate the rule that a player may not have more than two chips in a connected group, whether connected
     * orthogonally or diagonally.
     * (RULE #4)
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     * @param type the type of the square
     *
     * @return true if the piece is can be placed according to the rules, false otherwise.
     */

    private boolean checkNeighbors(int x, int y, int type) {
        if (countNeighbors(x,y,type) == 0) {
            return true;
        }
        //System.out.println("countNeighbors " + countNeighbors(x,y,type) + " at (" + x + ", " + y + ") with " + type);
        if (countNeighbors(x,y,type) == 1) {
            int[] location = locateNeighbors(x,y,type);
            if (countNeighbors(location[0], location[1], type) <= 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * removePiece(), takes two parameters, and changes the type of the 
     * indicated square on "this" Gameboard at the coordinates (x,y) to EMPTY.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     */

    private void removePiece(int x, int y) {
        if (checkPiece(getType(x,y)) && checkDimensions(x,y)) {
            int temp = this.getType(x,y);
            if (temp == BLACK) {
                blackCount++;
            }
            if (temp == WHITE) {
                whiteCount++;
            }
            this.setType(x, y, EMPTY);
        }   
    }

    /**
     * movePiece() "moves" a piece from one coordinate to another.
     * More specifically, it takes parameters (x1, y1, x2, y2) and
     * moves a piece on "this" Gameboard from (x1, y1) to (x2, y2).
     *
     *
     * @param x1 the x-coordinate of the square containing the piece
     * @param y1 the y-coordinate of the square containing the piece
     * @param x2 the x-coordinate of destination
     * @param y2 the y-coordinate of destination
     */

    private void movePieces(int x1, int y1, int x2, int y2) throws AgainstRulesException {
        if (x1 == x2 && y1 == y2) {
            throw new AgainstRulesException("attempt to move to same coordinates (" + x1 + ", " + y1 + ") = (" + x2 + ", " + y2 + ")");
        } else {
            int type = getType(x1,y1);
            try {
                removePiece(x1, y1);
                addPiece(x2, y2, type);
            }
            catch (AgainstRulesException e) {
                addPiece(x1, y1, type);
                throw new AgainstRulesException("attempt to move " + getType(x1, y1) + " from  (" + x1 + ", " + y1 + ") fails game rules.");
            }
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
        if(m.moveKind == Move.ADD) {
            try{
                addPiece(m.x1, m.y1, type);
                removePiece(m.x1, m.y1);
                return true;
            } catch (AgainstRulesException e){
                return false;
            }

        }
        if(m.moveKind == Move.STEP) {
            try{
                movePieces(m.x1, m.y1, m.x2, m.y2);
                movePieces(m.x2, m.y2, m.x1, m.y2);
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
     * @return a two dimensional array containing the location of all black pieces, the first
     * index in the inner-array is the x-coordinate, the second is the y-coordinate.
     */

    private int[][] listBlacks() {
        int[][] black = new int[TOTAL - this.getBlackCount()][2];
        int count = 0;
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (isBlack(i, j)) {
                    black[count][0] = i;
                    black[count][1] = j;
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
     * @return a two dimensional array containing the location of all whites pieces, the first
     * index in the inner-array is the x-coordinate, the second is the y-coordinate.
     */

    private int[][] listWhites() {
        int[][] white = new int[TOTAL - this.getWhiteCount()][2];
        int count = 0;
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (isWhite(i, j)) {
                    white[count][0] = i;
                    white[count][1] = j;
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
     * @return a two dimensional array containing the location of all pieces of type, the first
     * index in the inner-array is the x-coordinate, the second is the y-coordinate.
     */

    public int[][] listPieces(int type) throws AgainstRulesException {
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
     * returns an array of all possible moves.
     *
     * @param player is the type of the player in consideration
     *
     * @return a Move[] containing all the possible moves possible for "player"
     * "this" Gameboard.
     */

    public SList listMoves(int player) throws AgainstRulesException {
        SList validMoves = new SList();


        //addPiece()
        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (isValidMove(new Move(i, j), player)) {
                    validMoves.insertBack(new Move(i,j));
                }
            }
        }
        int[][] piece = listPieces(player);
        for (int k = 0; k < piece.length; k++) {
            for (int j = 0; j < this.height; j++) {
                for (int i = 0; i < this.width; i++) {
                    if (isValidMove(new Move(piece[k][0], piece[k][1], i, j), player)) {
                        validMoves.insertBack(new Move(piece[k][0], piece[k][1], i, j));
                    }
                }
            }
        }
        return validMoves;
    }

    /**
     * makeGrid() takes two parameters, an x-coordinate and y-coordinate, and returns a 3-D array containing the pieces
     * it has connections to in every direction.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a three dimension array containing the pieces of the same type the specified square is connected to. More specifically,
     * the first two dimensions specify a 3 x 3 two dimension array, where each direction specifies the direction of the connected chip.
     * For example, the top middle index of the 3 x 3 array would point to the first connected piece in same column, the top left index
     * of the 3 x 3 array would point to the first connected piece in the upper-left diagonal direction. The third dimension of the array
     * is of length two, and specifies the coordinates of the connected piece. The center index of the 3 x 3 array contains the coordinates
     * of the given square.
     */

    private int[][][] makeGrid(int x, int y) {
        int[][][] grid = new int[3][3][2];
        int[][] row = findConnectedRow(x,y);
        int[][] column = findConnectedColumn(x,y);
        int[][] ldiagonal = findConnectedLDiagonal(x,y);
        int[][] rdiagonal = findConnectedRDiagonal(x,y);

        grid[0][0] = ldiagonal[0];
        grid[0][1] = column[0];
        grid[0][2] = rdiagonal[0];
        grid[1][0] = row[0];
        grid[1][1] = new int[2];
        grid[1][1][0] = x; 
        grid[1][1][1] = y;
        grid[1][2] = row[1];
        grid[2][0] = rdiagonal[1];
        grid[2][1] = column[1];
        grid[2][2] = ldiagonal[1];

        return grid;
    }

    /**
     * makeHGrid() takes two parameters, an x-coordinate and y-coordinate, and returns a 2-D array containing the pieces
     * it has connections to in every direction.
     *
     * @param x the x-coordinate of the square
     * @param y the y-coordinate of the square
     *
     * @return a two dimension array containing the pieces of the same type the specified square is connected to. More specifically, it returns
     * a horizontal version of makeGrid(), where the indices of the first two dimensional array progresses horizontally. 
     */

    private int[][] makeHGrid(int x, int y) {
        int[][] grid = new int[9][2];
        int[][] row = findConnectedRow(x,y);
        int[][] column = findConnectedColumn(x,y);
        int[][] ldiagonal = findConnectedLDiagonal(x,y);
        int[][] rdiagonal = findConnectedRDiagonal(x,y);

        grid[0] = ldiagonal[0];
        grid[1] = column[0];
        grid[2] = rdiagonal[0];
        grid[3] = row[0];
        grid[4] = new int[2];
        grid[4][0] = x; 
        grid[4][1] = y;
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
    /*
    private boolean containsNetwork(int player) {
        if (player == BLACKPLAYER) {
            int[] topRow = getRow(0);
            for (int i = 0; i < topRow.length; i++) {
                if(topRow[i] == BLACK) {
                    if(containsNetworkHelper({0,i}, new SList(), 1)) {
                        return true; 
                    }               
                }
            }
        } else {
            return true;
        }
    }
    */

    //private boolean containsNetworkHelper(int[] coord, SList noahsark, int depth) {
        /*
        if(noahsark.contains(coord)) {
            return false;
        } else {
            return true;
        }
        */
        //return false;
    //}

        /*
        if(chip in network){
            return false;
        }

        if(depth < MIN_DEPTH && each getConnections(chip) is in network){ //redundant
            return false;
        }
        if(more than 2 chips in network belong to topRow/leftCol || bottomRow/rightCol){
            return false;
        }
        if(last chip in network is in last row/column){
            return true;
        }
        for each chip in getConnections(chip){
            cotainsNetworkHelper(chip,network+chip,depth+1);
        }
        */

    /**
     * isEmptyBoard() takes no parameters, and verifies that there are no pieces on the board. For internal testing only.
     *
     * @return empty is true if the board is empty, false otherwise.
     */

    private boolean isEmptyBoard() {
        boolean empty = true;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (isBlack(i,j) || isWhite(i,j)) {
                    empty = false;
                }
            }
        }
        return empty;
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

    private double evaluator(int player) {
        return (Math.random() * 2.0) - 1.0;
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
        Gameboard yuxinGame = new Gameboard();
        //System.out.println(yuxinGame);
        //System.out.println(yuxinGame.evaluator(BLACKPLAYER));

        try {

            //verifying board dimensions
            assert yuxinGame.width == 8: "ERROR (Y): width of gameboard incorrect";
            assert yuxinGame.height == 8: "ERROR (Y): height of gameboard incorrect";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying corner squares are indeed INVALID
            assert yuxinGame.isValid(0,0) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(0,7) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(7,0) == false: "ERROR (S): invalid squares are valid";
            assert yuxinGame.isValid(7,7) == false: "ERROR (S): invalid squares are valid";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying pieces can not be added to INVALID squares
            System.out.println("WARNING: AgainstRulesException in addPiece() is commented out");
            yuxinGame.addPiece(0,0,BLACK);
            yuxinGame.addPiece(0,7,BLACK);
            yuxinGame.addPiece(7,0,BLACK);
            yuxinGame.addPiece(7,7,BLACK);
            yuxinGame.addPiece(0,0,WHITE);
            yuxinGame.addPiece(0,7,WHITE);
            yuxinGame.addPiece(7,0,WHITE);
            yuxinGame.addPiece(7,7,WHITE);

            assert yuxinGame.isValid(0,0) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(0,7) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(7,0) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isValid(7,7) == false: "ERROR (Y): a piece has been added to an invalid square";
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying pieces of opposing color can not be added to a player's home rows
            //WHITE's side
            System.out.println("WARNING: checkCount in addPiece() replaced with checkRulesExceptCount()");
            yuxinGame.addPiece(0,1,BLACK);
            yuxinGame.addPiece(0,2,BLACK);
            yuxinGame.addPiece(0,3,BLACK);
            yuxinGame.addPiece(0,4,BLACK);
            yuxinGame.addPiece(0,5,BLACK);
            yuxinGame.addPiece(0,6,BLACK);

            yuxinGame.addPiece(7,1,BLACK);
            yuxinGame.addPiece(7,2,BLACK);
            yuxinGame.addPiece(7,2,BLACK);
            yuxinGame.addPiece(7,3,BLACK);
            yuxinGame.addPiece(7,4,BLACK);
            yuxinGame.addPiece(7,5,BLACK);
            yuxinGame.addPiece(7,6,BLACK);
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //BLACK's side
            yuxinGame.addPiece(1,0,WHITE);
            yuxinGame.addPiece(2,0,WHITE);
            yuxinGame.addPiece(3,0,WHITE);
            yuxinGame.addPiece(4,0,WHITE);
            yuxinGame.addPiece(5,0,WHITE);
            yuxinGame.addPiece(6,0,WHITE);

            yuxinGame.addPiece(1,7,WHITE);
            yuxinGame.addPiece(2,7,WHITE);
            yuxinGame.addPiece(3,7,WHITE);
            yuxinGame.addPiece(4,7,WHITE);
            yuxinGame.addPiece(5,7,WHITE);
            yuxinGame.addPiece(6,7,WHITE);
            assert yuxinGame.isEmptyBoard() == true: "ERROR (Y): the board should be empty";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying addPiece() without neighbor conflicts
            yuxinGame.addPiece(6,0,BLACK);
            yuxinGame.addPiece(6,5,BLACK);
            yuxinGame.addPiece(5,5,BLACK);
            yuxinGame.addPiece(3,3,BLACK);
            yuxinGame.addPiece(3,5,BLACK);
            yuxinGame.addPiece(5,7,BLACK);

            assert yuxinGame.getType(6,0) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(6,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(5,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(3,3) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(3,5) == BLACK: "ERROR (Y): square should be BLACK";
            assert yuxinGame.getType(5,7) == BLACK: "ERROR (Y): square should be BLACK";

            assert yuxinGame.blackCount == 4: "ERROR: blackCount is incorrect";
            assert yuxinGame.isEmptyBoard() == false: "ERROR (Y): the board should have pieces on it";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //verifying addPiece() with neighbor conflicts
            //BLACK piece initially at (6,0);

            //Test 1: exhaustive attempt to cause neighbor conflicts

            //Setup
            yuxinGame.addPiece(6,1,BLACK);
            assert yuxinGame.getType(6,1) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(1,7,BLACK);
            assert yuxinGame.getType(1,7) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(6,7,BLACK);
            assert yuxinGame.getType(6,7) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(2,1, BLACK);
            assert yuxinGame.getType(2,1) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(1,0, BLACK);
            assert yuxinGame.getType(1,0) == BLACK: "ERROR (Y): square should be BLACK";

            yuxinGame.addPiece(4,2, BLACK);
            assert yuxinGame.getType(4,2) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(3,7, BLACK);
            assert yuxinGame.getType(3,7) == BLACK: "ERROR (Y): square should be BLACK";

            //System.out.println(yuxinGame);

            //Attempt to cause errors
            yuxinGame.addPiece(2,0, BLACK);
            assert yuxinGame.getType(2,0) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,0, BLACK);
            assert yuxinGame.getType(5,0) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(1,1, BLACK);
            assert yuxinGame.getType(1,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,1, BLACK);
            assert yuxinGame.getType(3,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,1, BLACK);
            assert yuxinGame.getType(5,1) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(2,2, BLACK);
            assert yuxinGame.getType(2,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,2, BLACK);
            assert yuxinGame.getType(3,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,2, BLACK);
            assert yuxinGame.getType(5,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(6,2, BLACK);
            assert yuxinGame.getType(6,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(4,3, BLACK);
            assert yuxinGame.getType(4,3) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(2,4, BLACK);
            assert yuxinGame.getType(2,4) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,4, BLACK);
            assert yuxinGame.getType(3,4) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(4,4, BLACK);
            assert yuxinGame.getType(4,4) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,4, BLACK);
            assert yuxinGame.getType(5,4) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(6,4, BLACK);
            assert yuxinGame.getType(6,4) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(4,5, BLACK);
            assert yuxinGame.getType(4,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(2,6, BLACK);
            assert yuxinGame.getType(2,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,6, BLACK);
            assert yuxinGame.getType(3,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(4,6, BLACK);
            assert yuxinGame.getType(4,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,6, BLACK);
            assert yuxinGame.getType(5,6) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(6,6, BLACK);
            assert yuxinGame.getType(6,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(2,7, BLACK);
            assert yuxinGame.getType(2,7) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(4,7, BLACK);
            assert yuxinGame.getType(4,7) == EMPTY: "ERROR (Y): square should be EMPTY";

            //Test 2: randomized attempt to insert WHITE around BLACK pieces

            yuxinGame.addPiece(3,1, WHITE);
            assert yuxinGame.getType(3,1) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(3,2, WHITE);
            assert yuxinGame.getType(3,2) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(4,5, WHITE);
            assert yuxinGame.getType(4,5) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(5,6, WHITE);
            assert yuxinGame.getType(5,6) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(7,1, WHITE);
            assert yuxinGame.getType(7,1) == WHITE: "ERROR (Y): square should be WHITE";

            yuxinGame.addPiece(6,2, WHITE);
            assert yuxinGame.getType(6,2) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(7,5, WHITE);
            assert yuxinGame.getType(7,5) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(7,6, WHITE);
            assert yuxinGame.getType(7,6) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(0,2, WHITE);
            assert yuxinGame.getType(0,2) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(0,6, WHITE);
            assert yuxinGame.getType(0,6) == WHITE: "ERROR (Y): square should be WHITE";

            yuxinGame.addPiece(1,5, WHITE);
            assert yuxinGame.getType(1,5) == WHITE: "ERROR (Y): square should be WHITE";

            //Attempt to cause errors
            yuxinGame.addPiece(2,2, WHITE);
            assert yuxinGame.getType(2,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(7,2, WHITE);
            assert yuxinGame.getType(7,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(6,6, WHITE);
            assert yuxinGame.getType(6,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(0,5, WHITE);
            assert yuxinGame.getType(0,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(1,6, WHITE);
            assert yuxinGame.getType(1,6) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.addPiece(4,3, WHITE);
            assert yuxinGame.getType(4,3) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(2,6, WHITE);
            assert yuxinGame.getType(2,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,2, WHITE);
            assert yuxinGame.getType(5,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(5,1, WHITE);
            assert yuxinGame.getType(5,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,6, WHITE);
            assert yuxinGame.getType(3,6) == EMPTY: "ERROR (Y): square should be EMPTY";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//

            //attempting to add an INVALID using addPiece()
            yuxinGame.addPiece(1,1, INVALID);
            assert yuxinGame.getType(1,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(3,0, INVALID);
            assert yuxinGame.getType(3,0) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(0,5, INVALID);
            assert yuxinGame.getType(0,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(0,1, INVALID);
            assert yuxinGame.getType(0,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(6,6, INVALID);
            assert yuxinGame.getType(6,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.addPiece(4,1, INVALID);
            assert yuxinGame.getType(4,1) == EMPTY: "ERROR (Y): square should be EMPTY";

            //attempting to replace piece with an INVALID using addPiece()
            yuxinGame.addPiece(1,0, INVALID);
            assert yuxinGame.getType(1,0) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(3,1, INVALID);
            assert yuxinGame.getType(3,1) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(6,2, INVALID);
            assert yuxinGame.getType(6,2) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(6,5, INVALID);
            assert yuxinGame.getType(6,5) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(0,6, INVALID);
            assert yuxinGame.getType(0,6) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(3,3, INVALID);
            assert yuxinGame.getType(3,3) == BLACK: "ERROR (Y): square should be BLACK";

            //attempting to replace piece with an EMPTY using addPiece()
            yuxinGame.addPiece(1,0, EMPTY);
            assert yuxinGame.getType(1,0) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(3,1, EMPTY);
            assert yuxinGame.getType(3,1) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(6,2, EMPTY);
            assert yuxinGame.getType(6,2) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(6,5, EMPTY);
            assert yuxinGame.getType(6,5) == BLACK: "ERROR (Y): square should be BLACK";
            yuxinGame.addPiece(0,6, EMPTY);
            assert yuxinGame.getType(0,6) == WHITE: "ERROR (Y): square should be WHITE";
            yuxinGame.addPiece(3,3, EMPTY);
            assert yuxinGame.getType(3,3) == BLACK: "ERROR (Y): square should be BLACK";

            //attempting to add piece to out-of-bound coordinates using addPiece()
            yuxinGame.addPiece(100,100, BLACK);
            yuxinGame.addPiece(-20,45, WHITE);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //checking removePiece()

            yuxinGame.removePiece(6,0);
            assert yuxinGame.getType(6,0) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(6,5);
            assert yuxinGame.getType(6,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(5,5);
            assert yuxinGame.getType(5,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(3,3);
            assert yuxinGame.getType(3,3) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(3,5);
            assert yuxinGame.getType(3,5) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(5,7);
            assert yuxinGame.getType(5,7) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(6,1);
            assert yuxinGame.getType(6,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(1,7);
            assert yuxinGame.getType(1,7) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(6,7);
            assert yuxinGame.getType(6,7) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(2,1);
            assert yuxinGame.getType(2,1) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(1,0);
            assert yuxinGame.getType(1,0) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(4,2);
            assert yuxinGame.getType(4,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(3,7);
            assert yuxinGame.getType(3,7) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(3,1);
            assert yuxinGame.getType(3,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(3,2);
            assert yuxinGame.getType(3,2) == EMPTY: "ERROR (Y): square should be EMPTY";

            yuxinGame.removePiece(4,5);
            assert yuxinGame.getType(4,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(5,6);
            assert yuxinGame.getType(5,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(7,1);
            assert yuxinGame.getType(7,1) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(6,2);
            assert yuxinGame.getType(6,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(7,5);
            assert yuxinGame.getType(7,5) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(7,6);

            assert yuxinGame.getType(7,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(0,2);
            assert yuxinGame.getType(0,2) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(0,6);
            assert yuxinGame.getType(0,6) == EMPTY: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(1,5);
            assert yuxinGame.getType(1,5) == EMPTY: "ERROR (Y): square should be EMPTY";

            //attempting to remove INVALID squares
            yuxinGame.removePiece(0,0);
            assert yuxinGame.getType(0,0) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(0,7);
            assert yuxinGame.getType(0,7) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(7,0);
            assert yuxinGame.getType(7,0) == INVALID: "ERROR (Y): square should be EMPTY";
            yuxinGame.removePiece(7,7);
            assert yuxinGame.getType(7,7) == INVALID: "ERROR (Y): square should be EMPTY";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //Setup 
            yuxinGame.addPiece(3,1, WHITE);
            yuxinGame.addPiece(3,2, WHITE);
            yuxinGame.addPiece(4,5, WHITE);
            yuxinGame.addPiece(5,6, WHITE);
            yuxinGame.addPiece(7,1, WHITE);

            yuxinGame.addPiece(6,2, WHITE);
            yuxinGame.addPiece(7,5, WHITE);
            yuxinGame.addPiece(7,6, WHITE);
            yuxinGame.addPiece(0,2, WHITE);
            yuxinGame.addPiece(0,6, WHITE);

            yuxinGame.addPiece(1,5, WHITE);
            yuxinGame.addPiece(6,1, BLACK);
            yuxinGame.addPiece(1,7, BLACK);
            yuxinGame.addPiece(6,7, BLACK);
            yuxinGame.addPiece(2,1, BLACK);

            yuxinGame.addPiece(2,3, BLACK);
            yuxinGame.addPiece(2,5, BLACK);
            yuxinGame.addPiece(2,7, BLACK);
            yuxinGame.addPiece(1,0, BLACK);
            yuxinGame.addPiece(4,2, BLACK);

            yuxinGame.addPiece(3,7, BLACK);
            yuxinGame.addPiece(6,0, BLACK);
            yuxinGame.addPiece(6,5, BLACK);
            yuxinGame.addPiece(5,5, BLACK);
            yuxinGame.addPiece(3,3, BLACK);

            yuxinGame.addPiece(3,5, BLACK);
            yuxinGame.addPiece(5,7, BLACK);

            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- Row/Column");
            //System.out.println(yuxinGame);

            //testing Connected Columns
            int[][] c02 = {{0,0}, {0,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(0,2), c02): "Connected Column Error";
            int[][] c06 = {{0,2}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(0,6), c06): "Connected Column Error";
            int[][] c10 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(1,0), c10): "Connected Column Error";
            int[][] c15 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(1,5), c15): "Connected Column Error";
            int[][] c17 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(1,7), c17): "Connected Column Error";
            
            int[][] c21 = {{0,0}, {2,3}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(2,1), c21): "Connected Column Error";
            int[][] c23 = {{2,1}, {2,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(2,3), c23): "Connected Column Error";
            int[][] c25 = {{2,3}, {2,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(2,5), c25): "Connected Column Error";
            int[][] c27 = {{2,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(2,7), c27): "Connected Column Error";
            int[][] c31 = {{0,0}, {3,2}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(3,1), c31): "Connected Column Error";
            int[][] c32 = {{3,1}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(3,2), c32): "Connected Column Error";

            int[][] c35 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(3,5), c35): "Connected Column Error";
            int[][] c42 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(4,2), c42): "Connected Column Error";
            int[][] c45 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(4,5), c45): "Connected Column Error";
            int[][] c55 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(5,5), c55): "Connected Column Error";
            int[][] c56 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(5,6), c56): "Connected Column Error";

            int[][] c57 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(5,7), c57): "Connected Column Error";
            int[][] c60 = {{0,0}, {6,1}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(6,0), c60): "Connected Column Error";
            int[][] c61 = {{6,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(6,1), c61): "Connected Column Error";
            int[][] c62 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(6,2), c62): "Connected Column Error";
            int[][] c65 = {{0,0}, {6,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(6,5), c65): "Connected Column Error";

            int[][] c67 = {{6,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(6,7), c67): "Connected Column Error";
            int[][] c71 = {{0,0}, {7,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(7,1), c71): "Connected Column Error";
            int[][] c75 = {{7,1}, {7,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(7,5), c75): "Connected Column Error";
            int[][] c76 = {{7,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedColumn(7,6), c76): "Connected Column Error";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //testing Connected Rows
            int[][] r02 = {{0,0}, {3,2}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(0,2), r02): "Connected Row Error";
            int[][] r06 = {{0,0}, {5,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(0,6), r06): "Connected Row Error";
            int[][] r10 = {{0,0}, {6,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(1,0), r10): "Connected Row Error";
            int[][] r15 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(1,5), r15): "Connected Row Error";
            int[][] r17 = {{0,0}, {2,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(1,7), r17): "Connected Row Error";
            
            int[][] r21 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(2,1), r21): "Connected Row Error";
            int[][] r23 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(2,3), r23): "Connected Row Error";
            int[][] r25 = {{0,0}, {3,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(2,3), r23): "Connected Row Error";
            int[][] r27 = {{1,7}, {5,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(2,7), r27): "Connected Row Error";
            int[][] r31 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(3,1), r31): "Connected Row Error";
            int[][] r32 = {{0,2}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(3,2), r32): "Connected Row Error";

            int[][] r35 = {{2,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(3,5), r35): "Connected Row Error";
            int[][] r42 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(4,2), r42): "Connected Row Error";
            int[][] r45 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(4,5), r45): "Connected Row Error";
            int[][] r55 = {{0,0}, {6,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(5,5), r55): "Connected Row Error";
            int[][] r56 = {{0,6}, {7,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(5,6), r56): "Connected Row Error";

            int[][] r57 = {{2,7}, {6,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(5,7), r57): "Connected Row Error";
            int[][] r60 = {{1,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(6,0), r60): "Connected Row Error";
            int[][] r61 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(6,1), r61): "Connected Row Error";
            int[][] r62 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(6,2), r62): "Connected Row Error";
            int[][] r65 = {{5,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(6,5), r65): "Connected Row Error";

            int[][] r67 = {{5,7}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(6,7), r67): "Connected Row Error";
            int[][] r71 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(7,1), r71): "Connected Row Error";
            int[][] r75 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(7,5), r75): "Connected Row Error";
            int[][] r76 = {{5,6}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRow(7,6), r76): "Connected Row Error";

            //System.out.println(yuxinGame);

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
    
            //testing Connected LDiagonal
            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- LDiagonal");
            //System.out.println(yuxinGame);

            int[][] ld02 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(0,2), ld02): "Connected LDiagonal Error";
            int[][] ld06 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(0,6), ld06): "Connected LDiagonal Error";
            int[][] ld10 = {{0,0}, {2,1}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(1,0), ld10): "Connected LDiagonal Error";
            int[][] ld15 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(1,5), ld15): "Connected LDiagonal Error";
            int[][] ld17 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(1,7), ld17): "Connected LDiagonal Error";
            
            int[][] ld21 = {{1,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(2,1), ld21): "Connected LDiagonal Error";
            int[][] ld23 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(2,3), ld23): "Connected LDiagonal Error";
            int[][] ld25 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(2,3), ld23): "Connected LDiagonal Error";
            int[][] ld27 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(2,7), ld27): "Connected LDiagonal Error";
            int[][] ld31 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(3,1), ld31): "Connected LDiagonal Error";
            int[][] ld32 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(3,2), ld32): "Connected LDiagonal Error";

            int[][] ld35 = {{0,0}, {5,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(3,5), ld35): "Connected LDiagonal Error";
            int[][] ld42 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(4,2), ld42): "Connected LDiagonal Error";
            int[][] ld45 = {{0,0}, {5,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(4,5), ld45): "Connected LDiagonal Error";
            int[][] ld55 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(5,5), ld55): "Connected LDiagonal Error";
            int[][] ld56 = {{4,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(5,6), ld56): "Connected LDiagonal Error";

            int[][] ld57 = {{3,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(5,7), ld57): "Connected LDiagonal Error";
            int[][] ld60 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(6,0), ld60): "Connected LDiagonal Error";
            int[][] ld61 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(6,1), ld61): "Connected LDiagonal Error";
            int[][] ld62 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(6,2), ld62): "Connected LDiagonal Error";
            int[][] ld65 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(6,5), ld65): "Connected LDiagonal Error";

            int[][] ld67 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(6,7), ld67): "Connected LDiagonal Error";
            int[][] ld71 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(7,1), ld71): "Connected LDiagonal Error";
            int[][] ld75 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(7,5), ld75): "Connected LDiagonal Error";
            int[][] ld76 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedLDiagonal(7,6), ld76): "Connected LDiagonal Error";


            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//

            //testing Connected RDiagonal
            yuxinGame.addPiece(5,3, BLACK);
            //System.out.println("************************************************************");
            //System.out.println("yuxinGame -- RDiagonal");
            //System.out.println(yuxinGame);

            int[][] rd02 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(0,2), rd02): "Connected RDiagonal Error";
            int[][] rd06 = {{1,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(0,6), rd06): "Connected RDiagonal Error";
            int[][] rd10 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(1,0), rd10): "Connected RDiagonal Error";
            //System.out.println(yuxinGame.getType(0,6));
            //for (int i = 0; i < 7; i++) {
                //System.out.print(yuxinGame.getDiagonal(1,5,1)[i] + " |");
            //}
            int[][] rd15 = {{0,0}, {0,6}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(1,5), rd15): "Connected RDiagonal Error";
            int[][] rd17 = {{3,5}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(1,7), rd17): "Connected RDiagonal Error";
            
            int[][] rd21 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(2,1), rd21): "Connected RDiagonal Error";
            int[][] rd23 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(2,3), rd23): "Connected RDiagonal Error";
            int[][] rd25 = {{6,1}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(2,3), rd23): "Connected RDiagonal Error";
            int[][] rd27 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(2,7), rd27): "Connected RDiagonal Error";
            int[][] rd31 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(3,1), rd31): "Connected RDiagonal Error";
            int[][] rd32 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(3,2), rd32): "Connected RDiagonal Error";

            int[][] rd35 = {{5,3},{1,7}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(3,5), rd35): "Connected RDiagonal Error";
            int[][] rd42 = {{6,0},{0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(4,2), rd42): "Connected RDiagonal Error";
            int[][] rd45 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(4,5), rd45): "Connected RDiagonal Error";
            int[][] rd53 = {{0,0},{3,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(5,3), rd53): "Connected RDiagonal Error";
            int[][] rd55 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(5,5), rd55): "Connected RDiagonal Error";
            int[][] rd56 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(5,6), rd56): "Connected RDiagonal Error";

            int[][] rd57 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(5,7), rd57): "Connected RDiagonal Error";
            int[][] rd60 = {{0,0},{4,2}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(6,0), rd60): "Connected RDiagonal Error";
            int[][] rd61 = {{0,0},{2,5}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(6,1), rd61): "Connected RDiagonal Error";
            int[][] rd62 = {{7,1},{0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(6,2), rd62): "Connected RDiagonal Error";
            int[][] rd65 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(6,5), rd65): "Connected RDiagonal Error";

            int[][] rd67 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(6,7), rd67): "Connected RDiagonal Error";
            int[][] rd71 = {{0,0},{6,2}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(7,1), rd71): "Connected RDiagonal Error";
            int[][] rd75 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(7,5), rd75): "Connected RDiagonal Error";
            int[][] rd76 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(yuxinGame.findConnectedRDiagonal(7,6), rd76): "Connected RDiagonal Error";

            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //---------------------------------------------------------------------//
            //Diagonal Doublecheck

            Gameboard zhuGame = new Gameboard();
            zhuGame.addPiece(5,5,BLACK);
            zhuGame.addPiece(3,7,BLACK);
            zhuGame.addPiece(6,4,BLACK);
            zhuGame.addPiece(2,0,BLACK);
            zhuGame.addPiece(1,5,BLACK);
            zhuGame.addPiece(6,6,WHITE);
            zhuGame.addPiece(4,4,WHITE);
            zhuGame.addPiece(1,1, WHITE);
            zhuGame.addPiece(0,2, WHITE);
            zhuGame.addPiece(7,3, WHITE);
            //System.out.println(zhuGame);

            int[][] ldd02 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(0,2), ldd02): "Connected LDiagonal Error";
            int[][] ldd11 = {{0,0}, {4,4}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(1,1), ldd11): "Connected LDiagonal Error";
            int[][] ldd15 = {{0,0}, {3,7}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(1,5), ldd15): "Connected LDiagonal Error";
            int[][] ldd20 = {{0,0}, {6,4}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(2,0), ldd20): "Connected LDiagonal Error";
            int[][] ldd37 = {{1,5}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(3,7), ldd37): "Connected LDiagonal Error";

            int[][] ldd44 = {{1,1}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(4,4), ldd44): "Connected LDiagonal Error";
            int[][] ldd55 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(5,5), ldd55): "Connected LDiagonal Error";
            int[][] ldd64 = {{2,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(6,4), ldd64): "Connected LDiagonal Error";
            int[][] ldd66 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(6,6), ldd66): "Connected LDiagonal Error";
            int[][] ldd73 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedLDiagonal(7,3), ldd73): "Connected LDiagonal Error";   

            int[][] rdd02 = {{1,1}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(0,2), rdd02): "Connected RDiagonal Error";
            int[][] rdd11 = {{0,0}, {0,2}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(1,1), rdd11): "Connected RDiagonal Error";
            int[][] rdd15 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(1,5), rdd15): "Connected RDiagonal Error";
            int[][] rdd20 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(2,0), rdd20): "Connected RDiagonal Error";
            int[][] rdd37 = {{5,5}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(3,7), rdd37): "Connected RDiagonal Error";

            int[][] rdd44 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(4,4), rdd44): "Connected RDiagonal Error";
            int[][] rdd55 = {{6,4}, {3,7}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(5,5), rdd55): "Connected RDiagonal Error";
            int[][] rdd64 = {{0,0},{5,5}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(6,4), rdd64): "Connected RDiagonal Error";
            int[][] rdd66 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(6,6), rdd66): "Connected RDiagonal Error";
            int[][] rdd73 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(zhuGame.findConnectedRDiagonal(7,3), rdd73): "Connected RDiagonal Error"; 

            Gameboard cGame = new Gameboard();
            cGame.addPiece(0,6,WHITE);
            cGame.addPiece(1,5,WHITE);
            cGame.addPiece(4,2,WHITE);
            cGame.addPiece(5,1,WHITE);
            cGame.addPiece(6,1,WHITE);
            cGame.addPiece(1,7,WHITE);
            cGame.addPiece(1,1,WHITE);
            cGame.addPiece(6,6, WHITE);
            cGame.addPiece(6,5, WHITE);
            cGame.addPiece(2,1, WHITE);
            //System.out.println("************************************************************");
            //System.out.println("cGame");
            //System.out.println(cGame);

            int[][] lddd06 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(0,6), lddd06): "Connected LDiagonal Error";
            int[][] lddd11 = {{0,0}, {6,6}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(1,1), lddd11): "Connected LDiagonal Error";
            int[][] lddd15 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(1,5), lddd15): "Connected LDiagonal Error";
            int[][] lddd21 = {{0,0}, {6,5}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(2,1), lddd21): "Connected LDiagonal Error";
            int[][] lddd42 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(4,2), lddd42): "Connected LDiagonal Error";
            int[][] lddd51 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(5,1), lddd51): "Connected LDiagonal Error";

            int[][] lddd65 = {{2,1}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(6,5), lddd65): "Connected LDiagonal Error";
            int[][] lddd66 = {{1,1}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedLDiagonal(6,6), lddd66): "Connected LDiagonal Error";

            int[][] rddd06 = {{1,5}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(0,6), rddd06): "Connected RDiagonal Error";
            int[][] rddd11 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(1,1), rddd11): "Connected RDiagonal Error";
            int[][] rddd15 = {{4,2}, {0,6}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(1,5), rddd15): "Connected RDiagonal Error";
            int[][] rddd21 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(2,1), rddd21): "Connected RDiagonal Error";
            int[][] rddd42 = {{5,1}, {1,5}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(4,2), rddd42): "Connected RDiagonal Error";
            int[][] rddd51 = {{0,0}, {4,2}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(5,1), rddd51): "Connected RDiagonal Error";

            int[][] rddd65 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(6,5), rddd65): "Connected RDiagonal Error";
            int[][] rddd66 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(cGame.findConnectedRDiagonal(6,6), rddd66): "Connected RDiagonal Error";

            Gameboard dGame = new Gameboard();
            dGame.addPiece(1,7,BLACK);
            dGame.addPiece(2,6,BLACK);
            dGame.addPiece(6,7,BLACK);
            dGame.addPiece(5,6,BLACK);
            dGame.addPiece(7,6,WHITE);
            dGame.addPiece(6,5,WHITE);
            //System.out.println("************************************************************");
            //System.out.println("dGame");
            //System.out.println(dGame);

            int[][] ldddd17 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(1,7), ldddd17): "Connected LDiagonal Error";
            int[][] ldddd26 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(2,6), ldddd26): "Connected LDiagonal Error";
            int[][] ldddd56 = {{0,0}, {6,7}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(5,6), ldddd56): "Connected LDiagonal Error";
            int[][] ldddd65 = {{0,0}, {7,6}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(6,5), ldddd65): "Connected LDiagonal Error";
            int[][] ldddd67 = {{5,6}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(6,7), ldddd67): "Connected LDiagonal Error";
            int[][] ldddd76 = {{6,5}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedLDiagonal(7,6), ldddd76): "Connected LDiagonal Error";

            int[][] rdddd17 = {{2,6}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(1,7), rdddd17): "Connected RDiagonal Error";
            int[][] rdddd26 = {{0,0}, {1,7}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(2,6), rdddd26): "Connected RDiagonal Error";
            int[][] rdddd56 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(5,6), rdddd56): "Connected RDiagonal Error";
            int[][] rdddd65 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(6,5), rdddd65): "Connected RDiagonal Error";
            int[][] rdddd67 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(6,7), rdddd67): "Connected RDiagonal Error";
            int[][] rdddd76 = {{0,0}, {0,0}};
            assert Arrays.deepEquals(dGame.findConnectedRDiagonal(7,6), rdddd76): "Connected RDiagonal Error";
            /*
            Gameboard sanchitGame = new Gameboard();
            sanchitGame.addPiece(0,1,WHITE);
            sanchitGame.addPiece(0,2,WHITE);
            sanchitGame.addPiece(0,3,WHITE);
            sanchitGame.addPiece(0,4,WHITE);
            sanchitGame.addPiece(0,5,WHITE);
            sanchitGame.addPiece(0,6,WHITE);
            
            sanchitGame.addPiece(1,1,WHITE);
            sanchitGame.addPiece(2,2,WHITE);
            sanchitGame.addPiece(3,3,WHITE);
            sanchitGame.addPiece(4,4,WHITE);
            sanchitGame.addPiece(5,5,WHITE);
            sanchitGame.addPiece(6,6,WHITE);

            sanchitGame.addPiece(7,1,WHITE);
            sanchitGame.addPiece(7,2,WHITE);
            sanchitGame.addPiece(7,3,WHITE);
            sanchitGame.addPiece(7,4,WHITE);
            sanchitGame.addPiece(7,5,WHITE);
            sanchitGame.addPiece(7,6,WHITE);
            sanchitGame.addPiece(5,7,WHITE);

            System.out.println(sanchitGame);
            
            assert sanchitGame.getDiagonalLength(0,0,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,1,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,2,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,3,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,4,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,5,-1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,6,-1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,7,-1) == 1: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(1,0,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,1,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,2,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,3,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,4,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,5,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,6,-1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,7,-1) == 2: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(2,0,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,1,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,2,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,3,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,4,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,5,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,6,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,7,-1) == 3: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(3,0,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,1,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,2,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,3,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,4,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,5,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,6,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,7,-1) == 4: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(4,0,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,1,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,2,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,3,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,4,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,5,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,6,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,7,-1) == 5: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(5,0,-1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,1,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,2,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,3,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,4,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,5,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,6,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,7,-1) == 6: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(6,0,-1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,1,-1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,2,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,3,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,4,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,5,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,6,-1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,7,-1) == 7: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(7,0,-1) == 1: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,1,-1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,2,-1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,3,-1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,4,-1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,5,-1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,6,-1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,7,-1) == 8: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(0,0,1) == 1: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,1,1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,2,1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,3,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,4,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,5,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,6,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(0,7,1) == 8: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(1,0,1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,1,1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,2,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,3,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,4,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,5,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,6,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(1,7,1) == 7: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(2,0,1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,1,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,2,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,3,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,4,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,5,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,6,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(2,7,1) == 6: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(3,0,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,1,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,2,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,3,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,4,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,5,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,6,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(3,7,1) == 5: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(4,0,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,1,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,2,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,3,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,4,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,5,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,6,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(4,7,1) == 4: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(5,0,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,1,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,2,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,3,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,4,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,5,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,6,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(5,7,1) == 3: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(6,0,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,1,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,2,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,3,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,4,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,5,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,6,1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(6,7,1) == 2: "ERROR (S): getDiagonalLength is wrong";

            assert sanchitGame.getDiagonalLength(7,0,1) == 8: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,1,1) == 7: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,2,1) == 6: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,3,1) == 5: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,4,1) == 4: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,5,1) == 3: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,6,1) == 2: "ERROR (S): getDiagonalLength is wrong";
            assert sanchitGame.getDiagonalLength(7,7,1) == 1: "ERROR (S): getDiagonalLength is wrong";   
            
            int[] a = {-1,0,0,0,0,0,0,-1};
            int[] b = {-1,2,1,2,1,2,1,-1};
            int[] c = {-1,0,0,0,0,0,0,-1};
            int[] d = {-1,2,2,2,2,2,2,-1};
            int[] e = {-1,2,1,2,1,2,1,-1};
            int[] f = {1,0,2,0,0};
            
            assert Arrays.equals(sanchitGame.getRow(0),a): "ERROR (S): getRow is wrong";
            assert Arrays.equals(sanchitGame.getDiagonal(0,0,-1),b): "ERROR (S): getDiagonal is wrong";
            assert Arrays.equals(sanchitGame.getRow(7),c): "ERROR (S): getRow is wrong";
            assert Arrays.equals(sanchitGame.getColumn(0),d): "ERROR (S): getColumn is wrong";
            assert Arrays.equals(sanchitGame.getDiagonal(2,2,-1),e): "ERROR (S): getDiagonal is wrong";
            assert Arrays.equals(sanchitGame.getDiagonal(5,5,1),f): "ERROR (S): getDiagonal is wrong";

            System.out.println(sanchitGame);
            */
	    System.out.println("All tests passed!");
        } catch(Exception e) {
            System.out.println(e);
        }

    }
}


        


