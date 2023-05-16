package XXLChess;

import java.util.Arrays;
import XXLChess.Pieces.*;

public class Board {
    
    private Piece board[][];

    public Board() {
        board = new Piece[14][14];
    }

    public Piece[][] getChessboard() {
        // System.out.println(Arrays.deepToString(this.board));
        return this.board;
    }

    public void setChessBoard() {
        this.board = transpose(board);
    }

    public void setChessPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

    public static Piece[][] transpose(Piece[][] board) {
        int rows = board.length;
        int columns = board[0].length;
    
        Piece[][] result = new Piece[columns][rows];
    
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[j][i] = board[i][j];
            }
        }
    
        return result;
    }

    public Board(String[][] board) {
        this.board = new Piece[14][14];

    }

    public static String[][] makeBoardString(Piece[][] board) {
		String[][] toReturn = new String[14][14]; //Initializing string to return
		for (int i = 0; i < board.length; i++) { //Looping through all the 
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != null) { //Null check
					//Creating string representation of that pawn
					toReturn[i][j] = String.valueOf(board[i][j].getType()) + ":"
							+ String.valueOf(board[i][j].getPieceColor()) + ":" + String.valueOf(board[i][j].getMove(board));
				}
			}
		}
		return toReturn; //Returning array
	}

    // THIS IS FOR CHECKMATE
    public void movePiece(Player p1, Player p2, int x1, int y1, int x2, int y2) {

        boolean playerCheck = board[y2][x2].getPieceColor();

        // Checking and moving to an empty spot
        if (board[y2][x2] == null) {
            board[y2][x2] = board[y1][x1]; // Setting the piece to the new spot
            board[y2][x2].setX(x2); // Setting the coordinates of the new piece
            board[y2][x2].setY(y2); // Setting the coordinate of the new piece spot
            board[y1][x1] = null; // Making the old spot nothing
            board[y2][x2].setMove(true);
        }
        // Killing an Enemy and Moving the Piece to an Empty Spot
        else {
            if (playerCheck == false) {
                p2.addToPile(board[x2][y2]);
            } else {
                p1.addToPile(board[x2][y2]);
            }
			board[y2][x2] = board[y1][x1];
			board[y2][x2].setX(x2);
			board[y2][x2].setY(y2);
			board[y1][x1] = null;
			board[y2][x2].setMove(true);
        }

        // Pawn Promotion
        if (playerCheck == true && board[y2][x2].getType() == Type.PAWN && y2 == 6) {
            pawnPromotion(x2,y2, true);
        }
        else if (playerCheck == false && board[y2][x2].getType() == Type.PAWN && y2 == 7) {
            pawnPromotion(x2,y2, false);
        }
    }


    public void movePiece(int x1, int y1, int x2, int y2) {
        if (board[y2][x2] == null) {
            board[y2][x2] = board[y1][x1];
			board[y2][x2].setX(x2);
			board[y2][x2].setY(y2);
			board[y1][x1] = null;
			board[y2][x2].setMove(true);


            if (board[y2][x2].getType() == Type.PAWN) {
                if (board[y2][x2].getPieceColor() == true) {
                    if (board[y2 + 1][x2] != null) {
                        board[y2 + 1][x2] = null;
                    }
                    else {
                        if (board[y2-1][x2] != null) {
                            board[y2-1][x2] = null;
                        }
                    }
                }
            }
        }
    }

    public void pawnPromotion(int x2, int y2, boolean isWhite) {
        board[y2][x2] = null;
        if (isWhite) {
            board[y2][x2] = new Queen(x2, y2, true);
        } else {
            board[y2][x2] = new Queen(x2, y2, false);
        }
        
    }
    
 }