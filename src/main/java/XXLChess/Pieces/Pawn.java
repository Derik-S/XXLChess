package XXLChess.Pieces;

import XXLChess.*;

public class Pawn extends Piece {
    public Pawn(int x, int y, boolean isWhite) {
        super(x, y, isWhite, Type.PAWN, 1);
    }

    public int[][] getMove(Piece[][] boardStatus) {
		int[][] toReturn = new int[boardStatus.length][boardStatus[0].length];
		int x = (int) Math.round((super.getX()/ 48));
		int y = (int) Math.round((super.getY()/ 48));
		toReturn[y][x] = 3;

		int[] direction = (super.getPieceColor() == false) ? new int[] { 1, 1 } : new int[] { -1, -1 };
		int[][] pawnMoves = { { 0, direction[0] }, { 0, direction[0] * 2 } };
		int[][] pawnKills = { { 1, direction[0] }, { -1, direction[0] } };

		for (int[] move : pawnMoves) {
			int newX = x + move[0];
			int newY = y + move[1];
			if (BoardUtils.isCoord(newX, newY) && boardStatus[newY][newX] == null) {
				toReturn[newY][newX] = 1;
			}
		}

		for (int[] kill : pawnKills) {
			int newX = x + kill[0];
			int newY = y + kill[1];
			if (BoardUtils.isCoord(newX, newY) && boardStatus[newY][newX] != null && boardStatus[newY][newX].getPieceColor() != super.getPieceColor()) {
				toReturn[newY][newX] = 2;
			}
		}
		return toReturn;
	}

}
