package XXLChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BoardUtils {
    static int boardSize = 14;

	/**
	 * Checks if the piece can be move to a legitimate spot on the 14x14 board
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean isCoord(int x, int y) {
		return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
	}

	/**
	 * Given the player, this method returns the king's coordinates
	 * @param playerPieces
	 * @param player
	 * @return
	 */
	public static int[] getKingCoords(ArrayList<Piece> playerPieces, boolean pieceColor) {
		for (Piece p : playerPieces) {
			if (p.getType() == Type.KING && p.getPieceColor() == pieceColor) {
				return new int[] { p.getX(), p.getY()};
			}
		}
		return null;
	}

	/**
	 * Goes through a loop and if there isn't any legitimate moves from anypieces from both sides
	 * the method will return true
	 * @param playerPieces
	 * @param enemyPieces
	 * @param b
	 * @return Whether there is a draw or not
	 */
	public static boolean checkforDraw(ArrayList<Piece> playerPieces, ArrayList<Piece> enemyPieces, Board b) {
		Piece[][] board = b.getChessboard();
		ArrayList<Piece> allPieces = new ArrayList<>();
		ArrayList<Integer> possibleMoves = new ArrayList<>();

		allPieces.addAll(playerPieces);
		allPieces.addAll(enemyPieces);

		for (Piece p : allPieces) {
			int[][] coords = p.getMove(board);
			// Getting all the possible moves of the king's location
			for (int chessCol = 0; chessCol < 14; chessCol++) {
				for (int chessRow = 0; chessRow < 14; chessRow++) {
					int currTile = coords[chessCol][chessRow];
					if (currTile != 0) {
						possibleMoves.add(currTile);
					}
				}
			}
		}

		return possibleMoves.isEmpty();
		
	}

	/**
	 * Method which checks to see if a player is checked
	 * @param board
	 * @param kingX
	 * @param kingY
	 * @return Whether the player is checked
	 */
	public static boolean checkforCheck(ArrayList<Piece> enemyPieces, Piece[][] board, int kingX, int kingY) {
		
		// Loops through every enemypiece
		for (Piece p : enemyPieces) {
			int[][] coords = p.getMove(board);
			if (coords[kingY][kingX] != 0) {
				return true;
			}
		}	
		return false;
	}

	/**
	 * Method that checks for a checkmate, given a player
	 * 
	 * @param enemyPieces Used to loop all the possible moves of the enemy's Pieces
	 * @param kingX The players KingX
	 * @param kingY The players KingY
	 * @return Whether the player is checkmated
	 */
	public static boolean checkforCheckMate(ArrayList<Piece> enemyPieces, Board b, int kingX, int kingY) {
		Piece[][] board = b.getChessboard();
		Piece kingLocation = board[kingY][kingX];
		int[][] kingCoords = kingLocation.getMove(board);
		int kingCounter = 0; // Checks if the king has any movable tiles or not. If it does then it's not checkmate

		ArrayList<int[]> checkTile = getcheckTiles(kingCoords);

		// Case 1: Checking if the king can move.

		int[][] checktilePlaces = getCheckKingMovement(enemyPieces, checkTile, b, kingX, kingY);

		for (int chessCol = 0; board.length < 14; chessCol++) {
			for (int chessRow = 0; board[chessCol].length < 14; chessRow++) {
				int currTile = checktilePlaces[chessCol][chessRow];
				if(currTile == 1 || currTile == 2) {
					kingCounter = 1;
				}
			}
		}

		return kingCounter == checkTile.size();
	}

	/**
	 * Method that gets all the possible moves of the king's location
	 * @param kingCoords	The location of the king
	 */
	public static ArrayList<int[]> getcheckTiles(int[][] kingCoords) {
		ArrayList<int[]> toReturn = new ArrayList<>();
		
		for (int chessCol = 0; chessCol < 14; chessCol++) {
			for (int chessRow = 0; chessRow < 14; chessRow++) {
				int currTile = kingCoords[chessCol][chessRow];
				if (currTile != 0) {
					// Create an array to hold the coordinates and add it to the ArrayList
					int[] coordinates = { chessCol, chessRow };
					toReturn.add(coordinates);
				}
			}
		}
		return toReturn;
		
	}

	/**
	 * Method that checks for any checkPlaces in 0's and 1's. This player method shows where the king can move legally with enemyPieces
	 * 
	 * @param enemyPieces Checks for all possible moves for the enemyPieces
	 * @param checkTile Checks for the checkTiles of the king (where it can move)
	 * @param b
	 * @param kingX
	 * @param kingY
	 */
	public static int[][] getCheckKingMovement(ArrayList<Piece> enemyPieces, ArrayList<int[]> checkTile, Board b, int kingX, int kingY) {
		int[][] toReturn = new int[14][14];
		ArrayList<int[]> kingMoves = new ArrayList<>();
		
		// Grabbing the board
		Piece[][] board = b.getChessboard();
		
		// King's moves without the attacking pieces
		int[][] kingPieceMoves = board[kingY][kingX].getMove(board);
		
		// Making a board where the king is removed so that the pinned works
		Piece[][] pinBoard = new Piece[14][14];
		for (int row = 0; row < 14; row++) {
			for (int col = 0; col < 14; col++) {
				pinBoard[row][col] = board[row][col];
			}
		}
		pinBoard[kingY][kingX] = null;

	
		// Looping through each enemy piece
		for (Piece p : enemyPieces) {
			int[][] coords = p.getMove(pinBoard);
	
			// Checks if the current piece move matches with a king placement
			for (int[] validTile : checkTile) {
				int x = validTile[0];
				int y = validTile[1];
				if (coords[x][y] == 0 || coords[x][y] == 2) {
					int[] coordinate = {x, y};
					kingMoves.add(coordinate);
				}
			}
		}

		System.out.println(kingMoves);

		// Using the king's moves we check if there's any available moves
		for(int[] kingMove : kingMoves) {
			int x = kingMove[0];
			int y = kingMove[1];
			if(kingPieceMoves[x][y] == 0) {
				toReturn[x][y] = 1;
			}
			if(kingPieceMoves[x][y] == 2) {
				toReturn[x][y] = 2;
			}
		}

		toReturn[kingY][kingX] = 3;
		return toReturn;
	}	

	/**
	 * Method shows where the player's pieces can move whilst in check
	 * 
	 * @param playerPiece Checks for all possible moves for the playerPieces (used for blocking)
	 * @param enemyPieces Checks for all possible moves on the enemyPieces side
	 * @param b
	 * @param kingX
	 * @param kingY
	 */
	public static int[][] getCheckPieceMovement(Piece playerPiece, ArrayList<Piece> enemyPieces, ArrayList<int[]> checkTile, Piece[][] board) {
		int[][] toReturn = new int[14][14];
		int[][] coords = playerPiece.getMove(board);


		for(int[] validTile : checkTile) {
			int x = validTile[0];
			int y = validTile[1];
			if (coords[x][y] == 1) {
				toReturn[x][y] = 1;
			}
			if (coords[x][y] == 2) {
				toReturn[x][y] = 2;
			}
		}

		toReturn[playerPiece.getX() / 48][playerPiece.getY() / 48] = 3;

		return toReturn;
	}

	/**
	 * Method shows if castling is possible. The two x,y coordinates can either be the king or castle
	 * @param board Grabs the current state of the board
	 * @param x1 Grabs the x coordinate of one piece
	 * @param y1 Grabs the y coordinate of one piece
	 * @param x2 Grabs the x coordinate of another piece
	 * @param y2 Grabs the y coordinate of another piece
	 */
	public static boolean isCastlePossible(Piece[][] board, int x1, int y1, int x2, int y2) {
		if (board[y1][x1].getisMoved() || board[y2][x2].getisMoved()) {
			return false;
		}

		if (x1 > x2) {
			for (int i = x1 - 1; i > x2; i--) {
				if (board[y1][i] != null) {
					return false;
				}
			}
		}

		else {
			for (int i = x1 + 1; i < x2; i++) {
				if (board[y1][i] != null) {
					return false;
				}
			}
		}
		return true;
	}
}