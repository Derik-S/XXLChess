package XXLChess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class BoardUtils {
    static int boardSize = 14;

	public static boolean isCoord(int x, int y) {
		return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
	}

	/**
	 * Given the player, this function returns the king's coordinates
	 * @param board
	 * @param player
	 * @return
	 */
	public static int[] getKingCoords(Piece[][] board, boolean pieceColor) {
		for (Piece[] p : board) {
			for (Piece pa : p) {
				if (pa != null) {
					if (pa.getType() == Type.KING && pa.getPieceColor() == pieceColor) {
						return new int[] { pa.getX(), pa.getY() };
					}
				}
			}
		}
		return null;
	}

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
	 * @return
	 */
	public static boolean checkforCheck(ArrayList<Piece> enemyPieces, Board b, int kingX, int kingY) {
		// Gets the board
		Piece[][] board = b.getChessboard();
		
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
	 * @param b     A reference to the board object
	 * @param id    The player to check
	 * @param kingX The players KingX
	 * @param kingY The players KingY
	 * @return Whether the player is checkmated
	 */
	public static boolean checkforCheckMate(ArrayList<Piece> enemyPieces, Board b, int kingX, int kingY) {
		Piece[][] board = b.getChessboard();
		Piece kingLocation = board[kingY][kingX];
		int[][] kingCoords = kingLocation.getMove(board);
		int kingCounter = 0; // Checks if the king has any movable tiles or not. If it does then it's not checkmate

		// Getting all the possible moves of the king's location
		ArrayList<int[]> checkTile = getcheckTiles(kingCoords);

		// Case 1: Checking if the king can move.

		int[][] checktilePlaces = getCheckKingMovement(enemyPieces, checkTile, board, kingX, kingY);

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
	 * Method that checks for any checkPlaces in 0's and 1's. This player method shows where the king can move
	 * 
	 * @param enemyPieces     A reference to the board object
	 * @param checkTile    Grabs all the 
	 * @param board		Grabs the current state of board
	 * @return 1's for available moves and 2's for capture moves
	 */
	public static int[][] getCheckKingMovement(ArrayList<Piece> enemyPieces, ArrayList<int[]> checkTile, Piece[][] board, int kingX, int kingY) {
		int[][] toReturn = new int[14][14];

		// Perhaps figure out pawn's attack piece since it's diagonal
	
		// Looping through each enemy piece
		for (Piece p : enemyPieces) {
			int[][] coords = p.getMove(board);
	
			// Checks if the current piece move matches with a king placement
			for (int[] validTile : checkTile) {
				int x = validTile[0];
				int y = validTile[1];
				if (coords[x][y] == 0) {
					toReturn[x][y] = 1;
				}
				else if (coords[x][y] == 2) {
					toReturn[x][y] = 2;
				}
			}
		}

		toReturn[kingY][kingX] = 3;

		return toReturn;
	}	

	// This player method shows where the player's pieces can move whilst in check
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
}