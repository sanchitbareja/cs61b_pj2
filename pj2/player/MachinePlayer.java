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
    this.searchDepth = 5;
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
      return chooseMoveHelper(colorToSide(this.color) , 2.0, -2.0, Gameboard.MIN_DEPTH).move;
    } catch (Exception e) {
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
      return Gameboard.WHITE;
    } else {
      return Gameboard.BLACK;
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
      myBest.score = board.evaluator();
      return myBest;
    }

    if (board.containsNetwork(side)) {
      if (side == Gameboard.BLACKPLAYER) {
        myBest.score = 1;
      } else {
        myBest.score = -1;
      }
      return myBest;
    }

    if (colorToSide(this.color) == side) { //its the opponent's turn, i think
      myBest.score = alpha;
    } else {
      myBest.score = beta;
    }

    SList moves = this.board.listMoves(side);
    SListNode node = (SListNode)moves.front();
    try {
      while (node.isValidNode()) {
        Move currMove = (Move)node.item();

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
          return myBest;
        }

        node = (SListNode)node.next();
      }
    } catch (AgainstRulesException e) {
      System.out.println(e);
    } catch (InvalidNodeException e) {
      System.out.println(e);
    }

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
