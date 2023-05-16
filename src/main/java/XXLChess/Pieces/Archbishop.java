package XXLChess.Pieces;

import XXLChess.*;

public class Archbishop extends Piece {
    public Archbishop(int x, int y, boolean isWhite) {
        super(x, y, isWhite, Type.ARCHBISHOP);
    }

    public int[][] getMove(Piece[][] boardStatus) {
		int[][] toReturn = new int[boardStatus.length][boardStatus[0].length];
		int[][] coords = { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 }, { 1, -2 }, { -1, -2 } };

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

        int[][] directions = { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };

		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];

            int locationX = (int) Math.round(super.getX() / 48);
            int locationY = (int) Math.round(super.getY() / 48);

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
