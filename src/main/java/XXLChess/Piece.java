package XXLChess;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Piece {

    protected int x;
    protected int y;
    protected boolean isWhite;
    protected double value;
    protected PImage sprite;
    public Type piece;
    protected int color;
    protected boolean isMoved = false;
    protected int speed = 2;


    public Piece(int x, int y, boolean isWhite, Type piece) {
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
        this.piece = piece;
    }

    /**
	 * Abstract function implemented by all subclasses. Returns where the piece can
	 * move
	 * @param boardStatus Array containing all the
	 * @return int[][] Returns the places that the piece can move,
	 */
	public abstract int[][] getMove(Piece[][] board);

    /**
     * Sets the object's sprite
     * @param   sprite  The sprite that the object should have.
    */
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
     * Draws the object onto the screen.
     * @param   app The PApplet object.
    */
    public void draw(PApplet app) {
        this.sprite.resize(48,48);
        app.image(this.sprite, this.x, this.y);
    }

    /**
     * Gives and sets the object's x coordinate.
    */
    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        if (x < 48) {
            System.out.println("you've been usec");
        }
        this.x = x;
    }

    /**
     * Gives the object's y coordinate.  
    */
    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gives and sets the object's piece value.
    */
    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gets the piece Type.
    */
    public Type getType() {
        return piece;
    }

    public boolean getPieceColor() {
        return isWhite;
    }

    public boolean getMove() {
        return isMoved;
    }

    public void setMove(boolean a) {
        isMoved = a;
    }

    public void movePiece(int clickedTileX, int clickedTileY) {
        int resultX = (clickedTileX * 48) - this.x;
        int resultY = (clickedTileY * 48) - this.y;
    
        int desiredX = this.x + resultX;
        int desiredY = this.y + resultY;
    
        int speed = 1;

        if (this.x < desiredX) {
            this.x += speed;
          } else if (this.x > desiredX) {
            this.x -= speed;
          }
        
          if (this.y < desiredY) {
            this.y += speed;
          } else if (this.y > desiredY) {
            this.y -= speed;
          }
    }
}