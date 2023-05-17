package XXLChess;

import processing.core.PApplet;

// import java.util.Arrays;

public class Tile {
    protected int x;
    protected int y;
    protected String colour;
    protected int[] greenFill = { 112, 136, 83}; // Fill
    protected int[] blueFill = {0, 0, 255};
    protected int[] redFill = {255, 0, 0};
    protected int[] yellowFill = {255, 0, 255};
    protected float alphaFloat = 128; // sets the transparency of the tile


    public Tile(int x, int y, String colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public void draw(PApplet app) {
        if (this.colour == "green") {
            app.fill(greenFill[0], greenFill[1], greenFill[2]);
            app.noStroke();
            app.rect(this.x, this.y, 48, 48);
        }
        else if (this.colour == "blue") {
            app.fill(blueFill[0], blueFill[1], blueFill[2], alphaFloat);
            app.noStroke();
            app.rect(this.x, this.y, 48, 48);
        }
        else if (this.colour == "light red") {
            app.fill(redFill[0], redFill[1], redFill[2], alphaFloat);
            app.noStroke();
            app.rect(this.x, this.y, 48, 48);
        }
        else if (this.colour == "yellow") {
            app.fill(yellowFill[0], yellowFill[1], yellowFill[2]);
            app.noStroke();
            app.rect(this.x, this.y, 48, 48);
        }
    }
}

