/* MachinePlayer.java */

package player;

import list.*;

/**
 *  An implementation of an automatic Network player.  Keeps track of moves
 *  made by both players.  Can select a move for itself.
 */
public class MachinePlayer extends Player {

  private int color;
  private int addSearchDepth;
  private int stepSearchDepth;
  private Gameboard board;

  // Creates a machine player with the given color.  Color is either 0 (black)
  // or 1 (white).  (White has the first move.)
  public MachinePlayer(int color) {
    this.color = color;
    this.stepSearchDepth = 2;
    this.addSearchDepth = 4;
    this.board = new Gameboard();
  }

  // Creates a machine player with the given color and search depth.  Color is
  // either 0 (black) or 1 (white).  (White has the first move.)
  public MachinePlayer(int color, int searchDepth) {
    this.color = color;
    this.stepSearchDepth = searchDepth;
    this.addSearchDepth = searchDepth;
    this.board = new Gameboard();
  }

  // Returns a new move by "this" player.  Internally records the move (updates
  // the internal game board) as a move by "this" player.
  public Move chooseMove() {
    try {
      if(colorToSide(this.color) == Gameboard.WHITEPLAYER){
        if(this.board.getWhiteCount() == 10){
          Move wf = new Move(0,3);
          this.board.performMove(wf, sideToGameboardColor(colorToSide(this.color)));
          return wf;
        } /*else if(this.board.getWhiteCount() == 9){
            Move ws = new Move(7,3);
            this.board.performMove(ws, sideToGameboardColor(colorToSide(this.color)));
            return ws;
        }*/
      } else {
        if(this.board.getBlackCount() == 10){
          Move bf = new Move(3,0);
          this.board.performMove(bf, sideToGameboardColor(colorToSide(this.color)));
          return bf;
        } /*else if(this.board.getBlackCount() == 9){
            Move bs = new Move(3,7);
            this.board.performMove(bs, sideToGameboardColor(colorToSide(this.color)));
            return bs;
        }*/
      }
      Move m = chooseMoveIntermediary(this.addSearchDepth, this.stepSearchDepth).move;
      this.board.performMove(m, sideToGameboardColor(colorToSide(this.color)));
      return m;
    } catch (Exception e) {
      //System.out.println("catch: chooseMove()");
      System.out.println(e);
      e.printStackTrace();
      return null;
    }
  }

  /**
  * chooseMoveIntermediary is a helper function of chooseMove which takes in a maximum depth for 
  * step and add operations and performs the game tree search implemented in chooseMoveHelper for
  * each depth, up to five levels. The benefit of this is that the game tree is able to determine
  * whether a move is available in one turn rather than, say, four turns, and select the sooner
  * win.
  * 
  * @param maxAddDepth is the maximum number of levels to search to while adding pieces
  * @paran maxStepDepth is the maximum number of levels to search to while stepping pieces
  * @return a Best object that is returned from the game three data structure.
  */
  public Best chooseMoveIntermediary(int maxAddDepth, int maxStepDepth) throws InvalidNodeException, AgainstRulesException {
    //System.out.println("try: chooseMove()");
      Best best1 = chooseMoveHelper(colorToSide(this.color), -2000000000, 2000000000, 0, 0, 0);

      if (colorToSide(this.color) == Gameboard.BLACKPLAYER && best1.score == 1000000000) {
        // System.out.println("Depth searched to: "+0);
        return best1;
      } else if (colorToSide(this.color) == Gameboard.WHITEPLAYER && best1.score == -1000000000) {
        // System.out.println("Depth searched to: "+0);
        return best1;
      }

      Best best2 = chooseMoveHelper(colorToSide(this.color), -2000000000, 2000000000, 0, 1, 1);
      if (colorToSide(this.color) == Gameboard.BLACKPLAYER && best2.score == 1000000000) {
        // System.out.println("Depth searched to: 1,1");
        return best2;
      } else if (colorToSide(this.color) == Gameboard.WHITEPLAYER && best2.score == -1000000000) {
        // System.out.println("Depth searched to: 1,1");
        return best2;
      }
      Best best3 = chooseMoveHelper(colorToSide(this.color), -2000000000, 2000000000, 0, 2, 2);
      if (colorToSide(this.color) == Gameboard.BLACKPLAYER && best3.score == 1000000000) {
        // System.out.println("Depth searched to: 2,2");
        return best3;
      } else if (colorToSide(this.color) == Gameboard.WHITEPLAYER && best3.score == -1000000000) {
        // System.out.println("Depth searched to: 2,2");
        return best3;
      }
      Best best4 = chooseMoveHelper(colorToSide(this.color), -2000000000, 2000000000, 0, 3, 2);
      if (colorToSide(this.color) == Gameboard.BLACKPLAYER && best4.score == 1000000000) {
        // System.out.println("Depth searched to: 3,2");
        return best4;
      } else if (colorToSide(this.color) == Gameboard.WHITEPLAYER && best4.score == -1000000000) {
        // System.out.println("Depth searched to: 3,2");
        return best4;
      }
      Best best5 = chooseMoveHelper(colorToSide(this.color), -2000000000, 2000000000, 0, 4, 2);
      // System.out.println("Depth searched to: 4,2");
      return best5;
    
  }

  /**
  * colorToside takes in a color, white or black, given by the MachinePlayer class and converts it to
  * the analogous Gameboard.BLACKPLAYER or Gameboard.WHITEPLAYER for consistency.
  * 
  * @param color the color, an int
  * @return the side as definied in Gameboard.java
  */
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

  /**
  * oppositeSide takes a side as defined in Gameboard.java and returns the opposing side.
  * 
  * @param side is the side, an int
  * @return the opposing side, also an int
  */
  private int oppositeSide(int side) {
    if (side == Gameboard.WHITEPLAYER) {
      return Gameboard.BLACKPLAYER;
    } else {
      return Gameboard.WHITEPLAYER;
    }
  }

  /**
  * sideToGameboardColor takes in a side of play as defined in Gameboard and returns the
  * color as defined in Gameboard that corresponds to it. This is a helper function for
  * chooseMoveHelper.
  *
  * @param side the side
  * @return the corresponding color, and int.
  */
  private int sideToGameboardColor(int side) {
    if (side == Gameboard.BLACKPLAYER) {
      return Gameboard.BLACK;
    } else {
      return Gameboard.WHITE;
    }
  }

  /**
  * chooseMoveHelper takes in a current side, values of alpha and beta for alpha-beta pruning
  * analysis, the depth that the game tree search that it defines is currently searching, and
  * the maximum depths that it should search for each kind of move. It returns a Best object
  * which represents the best score and move that the side initially passed to it can take.
  * 
  * @param side the side currently being evaluated
  * @param alpha the alpha value for alpha-beta pruning
  * @param beta the beta value for alpha-beta pruning
  * @param currDepth the level of depth in the game tree that is currently being searched
  * @param maddAddDepth the maximum depth to search with add moves
  * @param maxStepDepth the maximum depth to search with step moves
  * @return the Best representing the machine's best move
  */
  public Best chooseMoveHelper(int side, double alpha, double beta, int currDepth, int maxAddDepth, int maxStepDepth) throws AgainstRulesException, InvalidNodeException{

    Best myBest = new Best();
    Best reply;

    int pieceCount;
    if (sideToGameboardColor(side) == Gameboard.BLACK) {
      pieceCount = board.getBlackCount();
    } else {
      pieceCount = board.getWhiteCount();
    }

    if ((pieceCount > 0 && currDepth >= maxAddDepth) || (pieceCount == 0 && currDepth >= maxStepDepth)) {
      myBest.score = board.evaluator(side);
      myBest.depth = currDepth;
      // System.out.println("if (currDepth >= this.searchDepth)");
      //System.out.println("Alpha: " + alpha + " Beta: " + beta);
      return myBest;
    }

    // if (board.containsNetwork(side)) {
    //   //System.out.println("Search Depth: " + currDepth);
    //   if (side == Gameboard.BLACKPLAYER) {
    //     //System.out.println("myBest = 1");
    //     myBest.score = 1000000000;
    //   } else {
    //     myBest.score = -1000000000;
    //     //System.out.println("myBest = -1");
    //   }
    //   // System.out.println("!!Search Depth: " + currDepth);
    //   // System.out.println("if (board.containsNetwork(side)) FUCK YOU YOU'RE BECOMING SELF AWARE!!!");
    //   //System.out.println("The Final Move: ");
    //   //System.out.println(myBest.move);
    //   //System.out.println("Alpha: " + alpha + " Beta: " + beta);
    //   myBest.depth = currDepth;
    //   return myBest;
    // }
    try {
          if((board.containsNetwork(Gameboard.WHITE) && side == Gameboard.BLACKPLAYER) || (board.containsNetwork(Gameboard.BLACK) && side == Gameboard.WHITEPLAYER)) {
                  // System.out.println("contains network triggered in aplha beta. with a negative value");
                  myBest.score = 1000000000; //switched
              }
          if((board.containsNetwork(Gameboard.BLACK) && side == Gameboard.BLACKPLAYER) || (board.containsNetwork(Gameboard.WHITE) && side == Gameboard.WHITEPLAYER)) {
                  // System.out.println("contains network triggered in aplha beta. with a positive value");
              myBest.score = -1000000000; //switched
          }
      } catch (InvalidNodeException e) {
          System.out.println(e);
      }

    if (colorToSide(this.color) == side) { //its the opponent's turn, i think
      myBest.score = alpha;
    } else {
      myBest.score = beta;
    }

    SList moves = this.board.listMoves(side);
    SListNode node = (SListNode)moves.front();
    //System.out.println(moves);
    try {
      while (node.isValidNode()) {
        Move currMove = (Move)node.item();
        // System.out.println("-------------------------------------------");
        // System.out.println(currMove);
        // System.out.println("-------------------------------------------");
        board.performMove(currMove, sideToGameboardColor(side));

        reply = chooseMoveHelper(oppositeSide(side), alpha, beta, currDepth + 1, maxAddDepth, maxStepDepth);

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
          //System.out.println("if (alpha >= beta)");
          Move m = myBest.move;
          //System.out.println("myBest: Movekind: " + m.moveKind + " x1: " + m.x1 + " y1: " + m.y1 + " x2: " + m.x2 + " y2: " + m.y2);
          //System.out.println("Alpha: " + alpha + " Beta: " + beta);
          myBest.depth = currDepth;
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
    //System.out.println("Alpha: " + alpha + " Beta: " + beta);
    myBest.depth = currDepth;
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