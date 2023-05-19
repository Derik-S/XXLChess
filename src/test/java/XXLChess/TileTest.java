package XXLChess;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TileTest {
    
    @Test
    public void constructor() {
        Tile tile = new Tile(20, 20, "green");
        assertEquals(20, tile.x);
        assertEquals(20, tile.y);
        assertEquals("green", tile.colour);
    }

    // @Test
    // public void drawGreenTest() {
    //     Tile tile = new Tile(20, 20, "green");
    //     PApplet rect = null;

    //     tile.draw(rect);

    //     assertEquals(112, rect.fillColor[0]);
    // }
}
