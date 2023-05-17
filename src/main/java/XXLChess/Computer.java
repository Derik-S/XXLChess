package XXLChess;

import java.util.*;

public class Computer {
    protected Board board;
    protected boolean isWhite;

    public Computer(boolean isWhite, Board board) {
        this.isWhite = isWhite;
        this.board = board;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void makeRandomMove(ArrayList<Piece> pieces, Piece[][] chessboard2) {
        // Get a random piece from the pieces ArrayList
        Random random = new Random();

        while (true) {
            int randomPieceIndex = random.nextInt(pieces.size());
            Piece selectedPiece = pieces.get(randomPieceIndex);

            // Get current board
            Piece[][] chessboard = board.getChessboard();

            // Get the valid moves for the selected piece
            int[][] validMoves = selectedPiece.getMove(chessboard);

            //Find the indices of all the legal moves (1s and 2s) in the 'validMoves' array
            ArrayList<Integer> legalMoveIndices = new ArrayList<>();
            for (int i = 0; i < validMoves.length; i++) {
                for (int j = 0; j < validMoves[i].length; j++) {
                    if (validMoves[i][j] == 1 || validMoves[i][j] == 2) {
                        legalMoveIndices.add(i * 14 + j);
                    }
                }
            }

            if(!legalMoveIndices.isEmpty()) {
                int randomMoveIndex = random.nextInt(legalMoveIndices.size());
                int selectedMoveIndex = legalMoveIndices.get(randomMoveIndex);

                int moveRow = selectedMoveIndex / 14;
                int moveCol = selectedMoveIndex % 14;

                board.movePiece(selectedPiece.getX(), selectedPiece.getY(), moveRow, moveCol);
                selectedPiece.movePiece(moveRow, moveCol);
                
                break;

            } else {
                pieces.remove(randomPieceIndex);
            }
        }
    }
}
