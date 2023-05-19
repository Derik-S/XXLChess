package XXLChess;

import java.util.ArrayList;

public class Player {
	protected ArrayList<Piece> takenPieces; // ArrayList of all the pieces the player took
	protected boolean isWhite;

	public Player(boolean isWhite) {
		this.isWhite = isWhite;
		this.takenPieces = new ArrayList<Piece>();
	}

	/**
	 * Class method which adds the taken pieces to a pile
	 * @param p Captured piece
	 */
	public void addToPile(Piece p) {
		this.takenPieces.add(p);
	}

	/**
	 * Function which returns player's piece colour
	 * @return Piece colour
	 */
	public boolean getisWhite() {
		return this.isWhite;
	}

}