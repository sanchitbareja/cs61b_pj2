/* MachinePlayer.java */

package player;

import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int color;
  private int searchDepth;
  private Gameboard board;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color = color;
    this.searchDepth = 3;
    this.board = new Gameboard();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this.color = color;
    this.searchDepth = searchDepth;
    this.board = new Gameboard();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    try {
      //System.out.println("try: chooseMove()");

      Move m = chooseMoveHelper(colorToSide(this.color) , -2000000000, 2000000000, 0).move;
      this.board.performMove(m, sideToGameboardColor(colorToSide(this.color)));
      //System.out.println(this.board);
      //System.out.println(m);
      return m;
    } catch (Exception e) {
      //System.out.println("catch: chooseMove()");
      System.out.println(e);
      e.printStackTrace();
      return null;
    }
  } 

  //TODO: comment this
  private int colorToSide(int color) {
    int player;
    if (color == 1) {
      player = Gameboard.WHITEPLAYER;
    }
    else {
      player = Gameboard.BLACKPLAYER;
    }
    return player;
  }

  //TODO: comment this!
  private int oppositeSide(int side) {
    if (side == Gameboard.WHITEPLAYER) {
      return Gameboard.BLACKPLAYER;
    } else {
      return Gameboard.WHITEPLAYER;
    }
  }

  //TODO: comment this!
  private int sideToGameboardColor(int side) {
    if (side == Gameboard.BLACKPLAYER) {
      return Gameboard.BLACK;
    } else {
      return Gameboard.WHITE;
    }
  }

  //TODO: FIX THIS SO IT WORKS.
  //implement Best class
  //also write comments lololol
  //side is WHITEPLAYER or BLACKPLAYER
  //and a shit ton of test code because this shit will break in 40 different places if you try to run it
  public Best chooseMoveHelper(int side, double alpha, double beta, int currDepth) throws AgainstRulesException, InvalidNodeException{

    Best myBest = new Best();
    Best reply;



    if (currDepth >= this.searchDepth) {
      myBest.score = board.evaluator(currDepth);
      // System.out.println("if (currDepth >= this.searchDepth)");
      System.out.println("Alpha: " + alpha + " Beta: " + beta);
      return myBest;
    }

    if (board.containsNetwork(side)) {
      //System.out.println("Search Depth: " + currDepth);
      if (side == Gameboard.BLACKPLAYER) {
        //System.out.println("myBest = 1");
        myBest.score = 1000000000;
      } else {
        myBest.score = -1000000000;
        //System.out.println("myBest = -1");
      }
      // System.out.println("!!Search Depth: " + currDepth);
      // System.out.println("if (board.containsNetwork(side)) FUCK YOU YOU'RE BECOMING SELF AWARE!!!");
      //System.out.println("The Final Move: ");
      //System.out.println(myBest.move);
      System.out.println("Alpha: " + alpha + " Beta: " + beta);
      return myBest;
    }

    if (colorToSide(this.color) == side) { //its the opponent's turn, i think
      myBest.score = alpha;
    } else {
      myBest.score = beta;
    }

    SList moves = this.board.listMoves(side);
    SListNode node = (SListNode)moves.front();
    // System.out.println(moves);
    try {
      while (node.isValidNode()) {
        Move currMove = (Move)node.item();
        // System.out.println("-------------------------------------------");
        // System.out.println(currMove);
        // System.out.println("-------------------------------------------");
        board.performMove(currMove, sideToGameboardColor(side));

        reply = chooseMoveHelper(oppositeSide(side), alpha, beta, currDepth + 1);

        board.undoMove(currMove);

        if (colorToSide(this.color) == side && (reply.score > myBest.score)) {
          myBest.move = currMove;
          myBest.score = reply.score;
          alpha = reply.score;
        } else if (colorToSide(this.color) != side && reply.score < myBest.score) {
          myBest.move = currMove;
          myBest.score = reply.score;
          beta = reply.score;
        }
        if (alpha >= beta) {
          System.out.println("if (alpha >= beta)");
          Move m = myBest.move;
          //System.out.println("myBest: Movekind: " + m.moveKind + " x1: " + m.x1 + " y1: " + m.y1 + " x2: " + m.x2 + " y2: " + m.y2);
          System.out.println("Alpha: " + alpha + " Beta: " + beta);
          return myBest;
        }

        node = (SListNode)node.next();
      }
    } catch (AgainstRulesException e) {
      System.out.println(e);
    } catch (InvalidNodeException e) {
      System.out.println(e);
    }

    // System.out.println("return myBest (last)");
    System.out.println("Alpha: " + alpha + " Beta: " + beta);
    return myBest;
  }

  // If the Move m is legal, records the move as a move by the opponent
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method allows your opponents to inform you of their moves.
  public boolean opponentMove(Move m) {
    if(this.board.isValidMove(m,sideToGameboardColor(oppositeSide(colorToSide(this.color))))){
      try{
        int type = sideToGameboardColor(oppositeSide(colorToSide(this.color)));
        this.board.performMove(m,type);
        //System.out.println("Hello!");
        return true;
      } catch (AgainstRulesException e){
        return false;
      }
    } else {
      return false;
    }
  }

  // If the Move m is legal, records the move as a move by "this" player
  // (updates the internal game board) and returns true.  If the move is
  // illegal, returns false without modifying the internal state of "this"
  // player.  This method is used to help set up "Network problems" for your
  // player to solve.
  public boolean forceMove(Move m) {
    //System.out.println("Movekind: " + m.moveKind + " x1: " + m.x1 + " y1: " + m.y1 + " x2: " + m.x2 + " y2: " + m.y2);
    if(this.board.isValidMove(m, sideToGameboardColor(colorToSide(this.color)))) {
      try{
        int type = sideToGameboardColor(colorToSide(this.color));
        board.performMove(m,type);

        return true;
      } catch (AgainstRulesException e){
        return false;
      }
    } else {
      return false;
    }
  }

}
