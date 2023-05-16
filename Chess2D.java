// jChess - 2D mode
// By Ibrahim Chehab and Fardeen Kasmani

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;
import uibooster.model.options.DarkUiBoosterOptions;
import processing.core.PConstants;
import java.util.ArrayList;

import java.util.HashMap;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.io.File;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;



  if ((currentChosenTile == null && lastChosenPiece.getPieceColor() == currentPlayer && lastChosenPiece != null)) {
	int[][] coords = lastChosenPiece.getMove(chessboard);
	if (coords[clickedTileY][clickedTileX] == 1) {
	  board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
	  lastChosenPiece.movePiece(clickedTileX, clickedTileY);
	}
	
	// Capturing a piece
  } else if (currentChosenTile != null && currentPlayer && lastChosenPiece != null) {
	int[][] coords = lastChosenPiece.getMove(chessboard);
	initTiles(coords);
	int[][] prevCoords = lastChosenPiece.getMove(chessboard);
	if (currentChosenTile.getPieceColor() != currentPlayer && currentPlayer) {
	  resetGridColor();
	  if (prevCoords[clickedTileY][clickedTileX] == 2) {
		board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
		System.out.println(Arrays.deepToString(chessboard));
		removePieces(currentChosenTile.getPieceColor(), currentChosenTile);
		lastChosenPiece.movePiece(clickedTileX, clickedTileY);
		currentChosenTile = lastChosenPiece;
	  }
	}
  }
  
  lastChosenPiece = chessboard[clickedTileY][clickedTileX];
  }


public class Chess2D extends PApplet {
	int screenNumber = 0; // Current screen number
	Piece lastChosenPiece; // Last chosen piece
	static int squareSize = 50; // Size for chess squares
	int currentPlayer = 0; // Current player whos turn it is

	// Loading all the textures and files required

	Rect[][] rects; // All the board squares
	PImage[] images; // Images for pieces
	String[] names = { "king.png", "queen.png", "knight.png", "bishop.png", "rook.png", "pawn.png" }; // Names for
																										// images to
																										// load
	Board board;
	Player player1, player2;
	int[] mainBoardColor;
	int[] secondaryBoardColor;
	String[] prefFileData; // Data inside preferences file

	/**
	 * Setup function required for PApplet
	 * 
	 * @author Fardeen Kasmani
	 */
	public void setup() {
		surface.setTitle("jChess2D - Release V1.0"); // Setting Title Text
		player1 = new Player(0, this);
		player2 = new Player(1, this);
		board = new Board(this);
		surface.setResizable(true);
		loadPreferences();
		initBoard2D();
		images = new PImage[6];
		for (int i = 0; i < 6; i++) {
			images[i] = loadImage("data/" + names[i]);
		}
	}

	/**
	 * Draw function required for PApplet
	 * 
	 * @author Fardeen Kasmani
	 */
	public void draw() {
			background(255);
			updateRectSize();
			stroke(255);
			fill(0);
			pushMatrix();
			translate((width - (squareSize * 8)) / 2, 0);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					rects[i][j].drawRect();
				}
			}
			drawPieces(board.getBoard());
			popMatrix();
			player1.drawPile(images);
			player2.drawPile(images);
		}
	}

	/**
	 * Method which initializes the rect objects on screen
	 * 
	 * @author Fardeen Kasmani
	 */
	public void initBoard2D() {
		rects = new Rect[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) { // Looping through all the rects
				rects[i][j] = new Rect(i * squareSize, j * squareSize);

				if (i % 2 == 0 && j % 2 != 0) { // Setting colors accordingly
					rects[i][j].setFillR(mainBoardColor[0]);
					rects[i][j].setFillG(mainBoardColor[1]);
					rects[i][j].setFillB(mainBoardColor[2]);
				} else if (i % 2 != 0 && j % 2 == 0) {
					rects[i][j].setFillR(mainBoardColor[0]);
					rects[i][j].setFillG(mainBoardColor[1]);
					rects[i][j].setFillB(mainBoardColor[2]);
				} else {
					rects[i][j].setFillR(secondaryBoardColor[0]);
					rects[i][j].setFillG(secondaryBoardColor[1]);
					rects[i][j].setFillB(secondaryBoardColor[2]);
				}
			}
		}
	}

	/**
	 * Mouse pressed function required for PApplet Depending on screen, method will
	 * either interact with GUI or move pieces
	 * 
	 * @author Ibrahim Chehab
	 */
	public void mousePressed() {
						Piece[][] p = board.getBoard();
						if (p[j][i] != null) { // If the piece at that location is not null
							if (p[j][i].getPlayer() != currentPlayer && lastChosenPiece != null) {
								// Update board with the places that piece can go
								int[][] coords = lastChosenPiece.getMove(p);
								coords = updateArray(coords,
										BoardUtils.getCheckPlaces(board.getBoard(), lastChosenPiece, currentPlayer));

								if (coords[j][i] == 2) { // If the rect pressed is somewhere the piece can capture a
															// pawn
									board.movePiece(player1, player2, lastChosenPiece.getPosX(),
											lastChosenPiece.getPosY(), i, j); // Capture it
									resetGridColor(); // Reset grid color
									lastChosenPiece = null; // Reset last chosen piece
									if (currentPlayer == 1) { // Change player cycle
										currentPlayer = 0;
									} else {
										currentPlayer = 1;
									}
								}
						} else {
							if (lastChosenPiece != null) {
								int[][] pos = lastChosenPiece.getMove(board.getBoard());

								pos = updateArray(pos,
										BoardUtils.getCheckPlaces(board.getBoard(), lastChosenPiece, currentPlayer));

								if (pos[j][i] != 0) { // Moving pieces
									board.movePiece(player1, player2, lastChosenPiece.getPosX(),
											lastChosenPiece.getPosY(), i, j);
									resetGridColor();
									lastChosenPiece = null;
									if (currentPlayer == 1) { // Player change cycle
										currentPlayer = 0;
									} else {
										currentPlayer = 1;
									}
								}
							}
						}
					}
				}
			}
			// Check and checkmate detection

			if (BoardUtils.checkforCheck(board.getBoard(), currentPlayer,
					BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[0],
					BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[1])) {

				int kingX = BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[0];
				int kingY = BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[1];

				rects[kingX][kingY].setFillR(255);
				rects[kingX][kingY].setFillG(120);
				rects[kingX][kingY].setFillB(120);

				if (BoardUtils.checkforCheckMate(board, currentPlayer,
						BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[0],
						BoardUtils.getKingCoords(board.getBoard(), currentPlayer)[1])) {
					System.out.println("Here1");
					delay(5000);
					screenNumber = 2;
				}

			}

		}
	}

	/**
	 * Updates the Board's colours based on it's movement capabilities when a Piece
	 * is selected
	 * 
	 * @author Fardeen Kasmani
	 * @param pos Positions to update grid with
	 */
	public void updateGrid(int[][] pos) {

		for (int i = 0; i < pos.length; i++) {
			for (int j = 0; j < pos[0].length; j++) { // Looping through all pieces
				if (pos[i][j] == 1) {
					rects[j][i].setFillR(125);
					rects[j][i].setFillG(125);
					rects[j][i].setFillB(125);
					rects[j][i].setStrokeR(0);
					rects[j][i].setStrokeG(0);
					rects[j][i].setStrokeB(0);
				} else if (pos[i][j] == 2) { // Setting their colors accordingly
					rects[j][i].setFillR(255);
					rects[j][i].setFillG(120);
					rects[j][i].setFillB(120);

					rects[j][i].setStrokeR(0);
					rects[j][i].setStrokeG(0);
					rects[j][i].setStrokeB(0);

				} else {
					rects[j][i].setStrokeR(125);
					rects[j][i].setStrokeG(125);
					rects[j][i].setStrokeB(125);

					rects[j][i].setFillR(secondaryBoardColor[0]);
					rects[j][i].setFillG(secondaryBoardColor[1]);
					rects[j][i].setFillB(secondaryBoardColor[2]);

					if (j % 2 == 0 && i % 2 != 0) {
						rects[j][i].setFillR(mainBoardColor[0]);
						rects[j][i].setFillG(mainBoardColor[1]);
						rects[j][i].setFillB(mainBoardColor[2]);
					} else if (j % 2 != 0 && i % 2 == 0) {
						rects[j][i].setFillR(mainBoardColor[0]);
						rects[j][i].setFillG(mainBoardColor[1]);
						rects[j][i].setFillB(mainBoardColor[2]);
					}

				}
			}
		}
	}

	/**
	 * Updates array based on a given map
	 * 
	 * @author Fardeen Kasmani
	 * @param source Source array to update
	 * @param matte  Matte to apply to array
	 * @return The merged array
	 */
	int[][] updateArray(int[][] source, int[][] matte) {
		for (int i = 0; i < matte.length; i++) {
			for (int j = 0; j < matte[i].length; j++) { // Looping through all pieces
				if (matte[i][j] == 0) { // If hte matte is 0
					source[i][j] = 0; // Make the source 0 (because it's not included in hte matte)
				}
			}
		}
		return source;
	}

	/**
	 * Class with utilities to aid with mouse detection and square drawing
	 * 
	 * @author Ibrahim Chehab
	 *
	 */
	class Rect {
		public float rectx, recty; // X and Y coords
		private int[] fill = { 255, 255, 255 }; // Fill
		private int[] stroke = { 0, 0, 0 }; // Stroke

		/**
		 * @author Fardeen Kasmani
		 * @param x x coord
		 * @param y y coord
		 */
		public Rect(int x, int y) {
			rectx = x;
			recty = y;
		}

		/**
		 * Updates rect size
		 * 
		 * @author Fardeen Kasmani
		 * @param x x coord
		 * @param y y coord
		 */
		public void update(int x, int y) {
			rectx = x;
			recty = y;
		}

		/**
		 * Draws rectangle to screen
		 * 
		 * @author Ibrahim Chehab
		 */
		public void drawRect() {
			pushStyle();
			if (this.isHovered()) {
				stroke(stroke[0], stroke[1], stroke[2]);
			} else {
				noStroke();
			}
			fill(fill[0], fill[1], fill[2]);
			rect(rectx - 1, recty - 1, squareSize - 1, squareSize - 1);
			popStyle();
		}

		/**
		 * Returns whether the rect is pressed
		 * 
		 * @author Ibrahim Chehab
		 * @return isPressed
		 */
		public boolean isPressed() {
			return (mousePressed && mouseX >= rectx + (width - (squareSize * 8)) / 2
					&& mouseX <= rectx + squareSize + (width - (squareSize * 8)) / 2 && mouseY >= recty
					&& mouseY <= recty + squareSize);
		}

		/**
		 * Sets the Stroke or Fill Colour of a rect
		 * 
		 * @author Fardeen Kasmani
		 * @param R red value
		 * @param G green value
		 * @param B blue value
		 */
		public void setFillR(int R) {
			fill[0] = R;
		}

		public void setFillG(int G) {
			fill[1] = G;
		}

		public void setFillB(int B) {
			fill[2] = B;
		}

		public void setStrokeR(int R) {
			stroke[0] = R;
		}

		public void setStrokeG(int G) {
			stroke[1] = G;
		}

		public void setStrokeB(int B) {
			stroke[2] = B;
		}

	}

	/**
	 * Sets screen size Required for PApplet
	 * 
	 * @author Fardeen Kasmani
	 */
	public void settings() {
		size(1280, 720);
	}

	/**
	 * Function which starts the PApplet class
	 * 
	 * @author Ibrahim Chehab
	 * @param passedArgs
	 */
	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "Chess2D" };
		if (passedArgs != null) {
			PApplet.main(concat(appletArgs, passedArgs));
		} else {
			PApplet.main(appletArgs);
		}
	}

	/**
	 * Converts a string to int array
	 * 
	 * @author Fardeen Kasmani
	 * @param firstArray Source array
	 * @return Converted int array
	 */
	public int[] stringToIntArray(String[] firstArray) {
		int[] toReturn = new int[firstArray.length];
		for (int i = 0; i < firstArray.length; i++) {
			toReturn[i] = Integer.parseInt(firstArray[i]);
		}

		return toReturn;
	}
}






//   @Override
//   public void mouseClicked(MouseEvent e) {
//       int mouseX = e.getX();
//       int mouseY = e.getY();
  
//       int clickedTileX = (int) Math.round(mouseX / CELLSIZE);
//       int clickedTileY = (int) Math.round(mouseY / CELLSIZE);

//       resetGridColor();
  
//       Piece[][] chessboard = board.getChessboard();
//       currentChosenTile = chessboard[clickedTileY][clickedTileX];

//       if ((currentChosenTile == null && lastChosenPiece.getPieceColor() == currentPlayer && lastChosenPiece != null)) {
//         int[][] coords = lastChosenPiece.getMove(chessboard);
//         if (coords[clickedTileY][clickedTileX] == 1) {
//           board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
//           lastChosenPiece.movePiece(clickedTileX, clickedTileY);
//         }
        
//         // Capturing a piece
//       } else if (currentChosenTile != null && currentPlayer && lastChosenPiece != null) {
//         int[][] coords = currentChosenTile.getMove(chessboard);
//         initTiles(coords);
//         if (currentChosenTile.getPieceColor() != currentPlayer && currentPlayer) {
//           resetGridColor();
//           board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
//           removePieces(currentChosenTile.getPieceColor(), currentChosenTile);
//           lastChosenPiece.movePiece(clickedTileX, clickedTileY);

//         }
//       }
      
//       lastChosenPiece = chessboard[clickedTileY][clickedTileX];      

//       }