package XXLChess;

import XXLChess.Pieces.*;

public class Board {
    
    private Piece board[][];

    public Board() {
        board = new Piece[14][14];
    }

    public Piece[][] getChessboard() {
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

	public void movePiece(int x1, int y1, int x2, int y2) {
		// Checking and Moving the Piece to an Empty Spot
		if (board[y2][x2] == null) {
			board[y2][x2] = board[y1][x1];
			board[y2][x2].setX(x2 * 48);
			board[y2][x2].setY(y2 * 48);
			board[y1][x1] = null;
			board[y2][x2].setMove(true);

			if (board[y2][x2].getType() == Type.PAWN) {
				if (board[y2][x2].getPieceColor() == true) {
					if (board[y2 + 1][x2] != null) {
						board[y2 + 1][x2] = null;
					}
				} else {
					if (board[y2 - 1][x2] != null) {
						board[y2 - 1][x2] = null;
					}
				}
			}
		}
		// Killing an Enemy and Moving the Piece to an Empty Spot
		else {
			board[y2][x2] = board[y1][x1];
			board[y2][x2].setX(x2 * 48);
			board[y2][x2].setY(y2 * 48);
			board[y1][x1] = null;
			board[y2][x2].setMove(true);
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