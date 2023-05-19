package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.Test;

import XXLChess.Pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    @Test
    public void getChessboardTest() {
        Piece piece = new Pawn(1, 1, false);
        PImage sprite = null;
        piece.setSprite(sprite);
        assertNull(piece.sprite);
    }

    @Test
    public void getXTest() {
        Piece piece = new Pawn(48, 48, false);
        assertEquals(48, piece.getX());
    }

    // Fix?
    public void setXTest() {
        Piece piece = new Pawn(48, 48, false);
        int x = piece.getX();
        int newX = 96;
        piece.setX(newX);

        int updatedX = piece.getX();
        assertEquals(newX, updatedX);
    }

    // Fix?
    @Test
    public void getYTest() {
        Piece piece = new Pawn(48, 48, false);
        assertEquals(48, piece.getY());
    }

    public void setYTest() {
        Piece piece = new Pawn(48, 48, false);
        piece.setY(96);
        assertEquals(piece.y, 96);
    }

    @Test
    public void getValueTest() {
        Piece piece = new Pawn(48, 48, false);
        assertEquals(1, piece.getValue());
    }

    @Test
    public void getTypeTest() {
        Piece piece = new Pawn(48, 48, false);
        assertEquals(Type.PAWN, piece.getType());
    }

    @Test
    public void getPieceColorTest() {
        Piece piece = new Pawn(48, 48, false);
        assertEquals(false, piece.getPieceColor());
    }

    @Test
    public void getisMovedTest() {
        Piece piece = new Pawn(48, 48, false);

        assertEquals(false, piece.getisMoved());
    }

    @Test
    public void setMoveTest() {
        Piece piece = new Pawn(48, 48, false);

        piece.setMove(true);
        assertEquals(true, piece.isMoved);
    }

    @Test
    public void movePieceTestOne() {
        Piece piece = new Pawn(0, 0, false);
        int clickedTileX = 2;
        int clickedTileY = 3;

        piece.movePiece(clickedTileX, clickedTileY);

        int expectedX = 96;  // 2 * 48
        int expectedY = 144; // 3 * 48

        assertEquals(expectedX, piece.getX());
        assertEquals(expectedY, piece.getY());
    }

    @Test
    public void movePieceTestTwo() {
        Piece piece = new Pawn(48, 528, false);
        int clickedTileX = 0;
        int clickedTileY = 12;

        piece.movePiece(clickedTileX, clickedTileY);
        assertEquals(0, piece.x);
        assertEquals(576, piece.y);
    }
}
