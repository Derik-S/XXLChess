package XXLChess;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import XXLChess.Pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void constructor() {
        App app = new App();
        assertNotNull(app);
        assertTrue(app.configPath == "config.json");
        assertEquals("white", app.player_colour);
        assertEquals(180, app.playerSeconds);
        assertEquals(180, app.cpuSeconds);
        assertEquals(2, app.playerIncrements);
        assertEquals(2, app.cpuIncrements);
        assertEquals(6.0, app.piece_movement_speed);
        assertEquals(1, app.max_movement_time);
    }

    @Test
    public void setupTest() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        assertNotNull(app.whitePieces);
        assertNotNull(app.blackPieces);
    }

    // Work on resign and checkmate

    @Test
    public void edgeCase() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        Piece currentChosenTile = new Pawn(1, 1, false);
        Piece lastChosenPiece = null;

        app.mouseClicked();
        assertNull(lastChosenPiece);
        assertNotNull(currentChosenTile);
    }

    @Test
    public void caseOne() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        Piece currentChosenTile = null;
        Piece lastChosenPiece = null;
        assertNull(lastChosenPiece);
        assertNull(currentChosenTile);
    }

    @Test
    public void caseTwo() {
        App app = new App();
        app.noLoop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

        Piece currentChosenTile = new Pawn(1, 1, true);
        Piece lastChosenPiece = null;
        
        assertEquals(currentChosenTile, lastChosenPiece);

    }
}
