package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;

import XXLChess.Pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    
    @Test
    public void constructor() {
        Player player = new Player(true);
        assertTrue(player.isWhite);
    }

    @Test
    public void addToPileTest() {
        Player player = new Player(true);
        Piece piece = new Pawn(48, 48, false);

        player.addToPile(piece);
        assertFalse(player.takenPieces.isEmpty());
    }

    @Test
    public void getisWhiteTest() {
        Player player = new Player(true);
        assertTrue(player.getisWhite());
    }
}
