package XXLChess;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import XXLChess.Pieces.*;

public class Board {
    
    private Piece board[][];

    public Board() {
        board = new Piece[14][14];
    }

	/**
	 * Getter and setter for chessboard
	 * @return board
	 */
    public Piece[][] getChessboard() {
        return this.board;
    }

    public void setChessBoard() {
        this.board = transpose(board);
    }

	/**
	 * Sets the chess piece to it's location on the board
	 * @param x x coordinate of the board
	 * @param y y coordinate of the board
	 * @param piece The selected piece
	 */
    public void setChessPiece(int x, int y, Piece piece) {
        board[x][y] = piece;
    }

	/**
	 * Transposes the board
	 * @param board
	 * @return Transposed board
	 */
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

	/**
	 * Moves the piece to a location on the 2D array board
	 * @param x1 Original x coordinate of the piece
	 * @param y1 Original y coordinate of the piece
	 * @param x2 New y coordinate of the piece
	 * @param y2 New x coordinate of the piece
	 * @return Transposed board
	 */
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

		if (y2 == 7 || y2 == 6) { //Pawn promotion check
			if (board[y2][x2].getType() == Type.PAWN) {
				pawnPromotion(x2, y2);
			}
		}
	}

    /**
	 * Castles player pieces
	 * @param x1 King's x-pos
	 * @param y1 King's y-pos
	 * @param x2 Rook's x-pos
	 * @param y2 Rook's y-pos
	 */
	public void castle(int x1, int y1, int x2, int y2) {
		// Determining
		int deltaX = Math.abs(x2 - x1);
		// Far Side Castle
		if (deltaX == 7) {
			// Moving the King
			board[y1][x1 - 2] = board[y1][x1];
			board[y1][x1 - 2].setX(x1 - 2);
			board[y1][x1] = null;
			board[y1][x1 - 2].setMove(true);
			// Moving the Rook
			board[y2][x2 + 3] = board[y2][x2];
			board[y2][x2 + 3].setX(x2 + 3);
			board[y2][x2] = null;
			board[y2][x2 + 3].setMove(true);
		}
		// Close Side Castle
		else {
			// Moving the King
			board[y1][x1 + 2] = board[y1][x1];
			board[y1][x1 + 2].setX(x1 + 2);
			board[y1][x1] = null;
			board[y1][x1 + 2].setMove(true);
			// Moving the Rook
			board[y2][x2 - 2] = board[y2][x2];
			board[y2][x2 - 2].setX(x2 - 2);
			board[y2][x2] = null;
			board[y2][x2 - 2].setMove(true);
		}
	}

    /**
	 * Promotes Pawns when they reach the end of the board
	 * 
	 * @param x2 X- coordinate to change
	 * @param y2 Y- coordinate to change
	 */
	public void pawnPromotion(int x2, int y2) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Sets Windows UI
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		String[] options = { "Queen", "Rook", "Bishop", "Knight" };

		String entry;

		do {
			entry = (String) JOptionPane.showInputDialog(null, "What would you like to promote this pawn to?",
					"Pawn Promotion", JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/main/resources/XXLChess/w-pawn.png"), options,
					options[0]);
		} while (entry == null);
		boolean isWhite = board[y2][x2].getPieceColor();

		if (entry.equals("Queen")) {
			board[y2][x2] = null;
			board[y2][x2] = new Queen(x2, y2, isWhite);
		}

		if (entry.equals("Rook")) {
			board[y2][x2] = null;
			board[y2][x2] = new Rook(x2, y2, isWhite);
			board[y2][x2].setMove(true); // Disables Castling with the New Rook
		}

		if (entry.equals("Bishop")) {
			board[y2][x2] = null;
			board[y2][x2] = new Bishop(x2, y2, isWhite);
		}

		if (entry.equals("Knight")) {
			board[y2][x2] = null;
			board[y2][x2] = new Knight(x2, y2, isWhite);
		}
		System.gc();
	}
    
 }