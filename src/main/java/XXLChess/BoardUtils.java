package XXLChess;

public class BoardUtils {
    static int boardSize = 14;

	public static boolean isCoord(int x, int y) {
		return (x >= 0 && x < boardSize && y >= 0 && y < boardSize);
	}

	// public static int[][] getCheckPlaces(Piece[][] pieces, Piece p, int id) {
	// 	int toReturn[][] = new int[14][14];
	// 	int[][] movements = p.getMove(pieces);
	// 	for (int i = 0; i < movements.length; i++) { //Looping through all possible moves
	// 		for (int j = 0; j < movements[i].length; j++) {
	// 			if (movements[j][i] != 0) {
	// 				String[][] bs = Board.makeBoardString(pieces);
	// 				Board newBoard = new Board(bs);
	// 				newBoard.movePiece(p.getX(), p.getY(), i, j);
	// 				//If this move no longer puts us in check
	// 				if (!checkforCheck(newBoard.getChessboard(), id, getKingCoords(newBoard.getChessboard(), id)[0],
	// 						getKingCoords(newBoard.getChessboard(), id)[1])) {
	// 					toReturn[j][i] = 1; //Add it to the array
	// 				}
	// 			}
	// 		}

	// 	}
	// 	return toReturn; //Return the array
	// }

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
}
