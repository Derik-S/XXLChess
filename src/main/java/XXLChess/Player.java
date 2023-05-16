package XXLChess;

import java.util.ArrayList;

public class Player {
	private ArrayList<Piece> takenPieces; // ArrayList of all the pieces the player took
	private boolean isWhite;

	public Player(boolean isWhite) {
		this.isWhite = isWhite;
		takenPieces = new ArrayList<Piece>();
	}

	public void addToPile(Piece p) {
		takenPieces.add(p);
	}

	/**
	 * Function which returns player ID
	 * @return id
	 */
	public boolean getisWhite() {
		return this.isWhite;
	}

}