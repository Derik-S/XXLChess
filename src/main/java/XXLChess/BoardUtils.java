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
		ArrayList<int[]> checkTile = new ArrayList<>();
		Piece kingLocation = board[kingY][kingX];
		int[][] kingCoords = kingLocation.getMove(board);

		// Getting all the possible moves of the king's location
		for (int chessCol = 0; chessCol < 14; chessCol++) {
			for (int chessRow = 0; chessRow < 14; chessRow++) {
				int currTile = kingCoords[chessCol][chessRow];
				if (currTile != 0) {
					// Create an array to hold the coordinates and add it to the ArrayList
					int[] coordinates = { chessCol, chessRow };
					checkTile.add(coordinates);
				}
			}
		}

		// Looping through each enemy piece
		Iterator<Piece> enemyIterator = enemyPieces.iterator();
		while (enemyIterator.hasNext()) {
			Piece p = enemyIterator.next();
			int[][] coords = p.getMove(board);
			
			// Checks if the current piece move matches with a king placement
			Iterator<int[]> checkIterator = checkTile.iterator();
			while (checkIterator.hasNext()) {
				int[] validTile = checkIterator.next();
				int x = validTile[0];
				int y = validTile[1];
				if (coords[x][y] == 1 || coords[x][y] == 2) {
					checkIterator.remove();
				}
			}
		}
		return checkTile.isEmpty();
	}
}