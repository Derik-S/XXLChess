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