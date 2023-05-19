package XXLChess.Pieces;

import XXLChess.*;

public class Bishop extends Piece {
    public Bishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite, Type.BISHOP, 3.625);
    }

    public int[][] getMove(Piece[][] boardStatus) {
		int[][] directions = { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
		int[][] toReturn = new int[boardStatus.length][boardStatus[0].length];

		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];

            int locationX = (int) Math.round(super.getX() / 48);
            int locationY = (int) Math.round(super.getY() / 48);
            toReturn[locationY][locationX] = 3;

            int x = locationX + dx;
            int y = locationY + dy;
			
			while (BoardUtils.isCoord(x, y)) {
				if (boardStatus[y][x] == null) {
					toReturn[y][x] = 1;
				} else if (boardStatus[y][x].getPieceColor() != super.getPieceColor()) {
					toReturn[y][x] = 2;
					break;
				} else {
					break;
				}

				x += dx;
				y += dy;
			}
		}

		return toReturn;
	}

}
