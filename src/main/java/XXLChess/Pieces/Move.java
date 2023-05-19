package XXLChess.Pieces;

import XXLChess.*;

public class Move {

    // Move type combines if it's 0 then it should give knight movements only, if it's 1 then it should give continual movements. If its both then it's 3
    
    private void calculateCoordsMoves(int[][] toReturn, Piece[][] boardStatus, int[][] coords, int x, int y, boolean colour) {
        for (int[] dir : coords) {
            int dx = dir[0];
            int dy = dir[1];

            int locationX = (int) Math.round(x/ 48);
            int locationY = (int) Math.round(y / 48);
            toReturn[locationY][locationX] = 3;

            int continueX = locationX + dx;
            int continueY = locationY + dy;

            if (BoardUtils.isCoord(continueX, continueY)) {
                if (boardStatus[continueY][continueX] == null) {
                    toReturn[continueY][continueX] = 1; // Valid Move
                } else if (boardStatus[y][x].getPieceColor() != colour) {
                    toReturn[continueY][continueX] = 2; // Capture Move
                }
            }
        }
    }

    private void calculateDirectionMoves(int[][] toReturn, Piece[][] boardStatus, int[][] coords, int x, int y, boolean colour) {
        int[][] directions = { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];

            int locationX = (int) Math.round(x/ 48);
            int locationY = (int) Math.round(y / 48);

            int continueX = locationX + dx;
            int continueY = locationY + dy;

            while (BoardUtils.isCoord(continueX, continueY)) {
                if (boardStatus[continueY][continueX] == null) {
                    toReturn[continueY][continueX] = 1;
                } else if (boardStatus[continueY][continueX].getPieceColor() != colour) {
                    toReturn[continueY][continueX] = 2;
                    break;
                } else {
                    break;
                }

                x += dx;
                y += dy;
            }
        }
    }
}

