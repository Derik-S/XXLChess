package XXLChess.Pieces;

import XXLChess.*;

public class King extends Piece {
    public King(int x, int y, boolean isWhite) {
        super(x, y, isWhite, Type.KING, Double.POSITIVE_INFINITY);
    }

    public int[][] getMove(Piece[][] boardStatus) {
		int toReturn[][] = new int[boardStatus.length][boardStatus[0].length];
		int coords[][] = { { 0, -1 }, { 0, 1 }, { -1, 0 }, { 1, 0 }, { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

		for (int[] dir : coords) {
			int dx = dir[0];
            int dy = dir[1];

            int locationX = (int) Math.round(super.getX() / 48);
            int locationY = (int) Math.round(super.getY() / 48);
            toReturn[locationY][locationX] = 3;

            int x = locationX + dx;
            int y = locationY + dy;

			if (BoardUtils.isCoord(x, y)) {
				if (boardStatus[y][x] == null) {
					toReturn[y][x] = 1; // Valid Move
				} else if (boardStatus[y][x].getPieceColor() != super.getPieceColor()) {
					toReturn[y][x] = 2; // Capture Move
				}
			}
		}
		return toReturn;
	}

}
