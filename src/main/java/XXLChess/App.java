package XXLChess;

//import org.reflections.Reflections;
//import org.reflections.scanners.Scanners;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.core.PFont;
import processing.event.MouseEvent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import XXLChess.Pieces.*;

import java.lang.Math;

import java.awt.Font;
import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int SPRITESIZE = 480;
    public static final int CELLSIZE = 48;
    public static final int SIDEBAR = 120;
    public static final int BOARD_WIDTH = 14;

    public static int WIDTH = CELLSIZE*BOARD_WIDTH+SIDEBAR;
    public static int HEIGHT = BOARD_WIDTH*CELLSIZE;

    public static final int FPS = 60;

    //Class Attributes
    protected String configPath;
    protected JSONObject conf;
    protected JSONObject time_controls;
    protected JSONObject player;
    protected JSONObject cpu;
    protected int seconds;
    protected int increment;
    protected int max_movement_time;
    protected String player_colour;
    protected double piece_movement_speed;
    protected int playerSeconds;
    protected int cpuSeconds;
    protected int playerIncrements;
    protected int cpuIncrements;

    protected Board board;
    protected Tile tile;
    protected Clock playerClock;
    protected Clock cpuClock;

    protected Piece lastChosenPiece;
    protected Piece currentChosenTile;

    protected boolean currentPlayer = true;

    protected boolean inCheckMate;
    protected boolean inCheck;

    protected boolean firstMove = true;
    protected boolean playerFirst = true;

    protected ArrayList<Tile> greenTile;
    protected ArrayList<Tile> blueTile;
    protected ArrayList<Tile> lightredTile;
    protected ArrayList<Tile> darkredTile;
    protected ArrayList<Tile> yellowTile;


    protected ArrayList<Piece> blackPieces;
    protected ArrayList<Piece> whitePieces;

    // relative file paths for all the images
    private static String whiteRookFileStr = "src/main/resources/XXLChess/w-rook.png";
    private static String blackRookFileStr = "src/main/resources/XXLChess/b-rook.png";
    private static String whiteKnightFileStr = "src/main/resources/XXLChess/w-knight.png";
    private static String blackKnightFileStr = "src/main/resources/XXLChess/b-knight.png";
    private static String whiteBishopFileStr = "src/main/resources/XXLChess/w-bishop.png";
    private static String blackBishopFileStr = "src/main/resources/XXLChess/b-bishop.png";
    private static String whiteQueenFileStr = "src/main/resources/XXLChess/w-queen.png";
    private static String blackQueenFileStr = "src/main/resources/XXLChess/b-queen.png";
    private static String whiteKingFileStr = "src/main/resources/XXLChess/w-king.png";
    private static String blackKingFileStr = "src/main/resources/XXLChess/b-king.png";
    private static String whitePawnFileStr = "src/main/resources/XXLChess/w-pawn.png";
    private static String blackPawnFileStr = "src/main/resources/XXLChess/b-pawn.png";
    private static String whiteArchbishopFileStr = "src/main/resources/XXLChess/w-archbishop.png";
    private static String blackArchbishopFileStr = "src/main/resources/XXLChess/b-archbishop.png";
    private static String whiteCamelFileStr = "src/main/resources/XXLChess/w-camel.png";
    private static String blackCamelFileStr = "src/main/resources/XXLChess/b-camel.png";
    private static String whiteGeneralFileStr = "src/main/resources/XXLChess/w-knight-king.png";
    private static String blackGeneralFileStr = "src/main/resources/XXLChess/b-knight-king.png";
    private static String whiteAmazonFileStr = "src/main/resources/XXLChess/w-amazon.png";
    private static String blackAmazonFileStr = "src/main/resources/XXLChess/b-amazon.png";
    private static String whiteChancellorFileStr = "src/main/resources/XXLChess/w-chancellor.png";
    private static String blackChancellorFileStr = "src/main/resources/XXLChess/b-chancellor.png";

    /**
     * Sets up all the configuration
     */
    public App() {
      this.configPath = "config.json";
      this.conf = loadJSONObject(new File(this.configPath));
      this.time_controls = this.conf.getJSONObject("time_controls");
      this.player = this.time_controls.getJSONObject("player");
      this.cpu = this.time_controls.getJSONObject("cpu");

      this.player_colour = this.conf.getString("player_colour");
      this.piece_movement_speed = this.conf.getDouble("piece_movement_speed");
      this.max_movement_time = this.conf.getInt("max_movement_time");

      this.playerSeconds = this.player.getInt("seconds");
      this.cpuSeconds = this.cpu.getInt("seconds");

      this.playerIncrements = this.player.getInt("increment");
      this.cpuIncrements = this.cpu.getInt("increment");



      this.whitePieces = new ArrayList<Piece>();
      this.blackPieces = new ArrayList<Piece>();

      this.greenTile = new ArrayList<Tile>();
      this.blueTile = new ArrayList<Tile>();
      this.lightredTile = new ArrayList<Tile>();
      this.darkredTile = new ArrayList<Tile>();
      this.yellowTile = new ArrayList<Tile>();


  }

    /**
     * Initialise the setting of the window size.
    */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player, enemies and map elements.
    */
    public void setup() {
      frameRate(FPS);
  
      String layout = "level1.txt";
      textSize(20);
  
      // Load images during setup
      try {
        Scanner sc = new Scanner(new File(layout));
        int y = 0;
        board = new Board();
        playerClock = new Clock(this.playerSeconds, this);
        cpuClock = new Clock(this.cpuSeconds, this);
        while (sc.hasNextLine()) {
          String line = sc.nextLine();
  
          for (int x = 0; x < line.length(); x++) {
            if (line.charAt(x) == " ".charAt(0)) {
              //checks for empty spaces
            }
            else if (line.charAt(x) == "R".charAt(0)) {
              // Black Rook
              Rook blackRook = new Rook(x * CELLSIZE, y * CELLSIZE, false);
              blackRook.setSprite(this.loadImage(blackRookFileStr));
              this.blackPieces.add(blackRook);
              board.setChessPiece(x, y, blackRook);
            }
            else if (line.charAt(x) == "r".charAt(0)) {
              // White Rook
              Rook whiteRook = new Rook(x * CELLSIZE, y * CELLSIZE, true);
              whiteRook.setSprite(this.loadImage(whiteRookFileStr));
              this.whitePieces.add(whiteRook);
              board.setChessPiece(x, y, whiteRook);
            }
            else if (line.charAt(x) == "P".charAt(0)) {
              // Black Pawn
              Pawn blackPawn = new Pawn(x * CELLSIZE, y * CELLSIZE, false);
              blackPawn.setSprite(this.loadImage(blackPawnFileStr));
              this.blackPieces.add(blackPawn);
              board.setChessPiece(x, y, blackPawn);
            }
            else if (line.charAt(x) == "p".charAt(0)) {
              // White Pawn
              Pawn whitePawn = new Pawn(x * CELLSIZE, y * CELLSIZE, true);
              whitePawn.setSprite(this.loadImage(whitePawnFileStr));
              this.whitePieces.add(whitePawn);
              board.setChessPiece(x, y, whitePawn);
            }
            else if (line.charAt(x) == "N".charAt(0)) {
              // Black Knight
              Knight blackKnight = new Knight(x * CELLSIZE, y * CELLSIZE, false);
              blackKnight.setSprite(this.loadImage(blackKnightFileStr));
              this.blackPieces.add(blackKnight);
              board.setChessPiece(x, y, blackKnight);
            }
            else if (line.charAt(x) == "n".charAt(0)) {
              // White Knight
              Knight whiteKnight = new Knight(x * CELLSIZE, y * CELLSIZE, true);
              whiteKnight.setSprite(this.loadImage(whiteKnightFileStr));
              this.whitePieces.add(whiteKnight);
              board.setChessPiece(x, y, whiteKnight);
            }
            else if (line.charAt(x) == "B".charAt(0)) {
              // Black Bishop
              Bishop blackBishop = new Bishop(x * CELLSIZE, y * CELLSIZE, false);
              blackBishop.setSprite(this.loadImage(blackBishopFileStr));
              this.blackPieces.add(blackBishop);
              board.setChessPiece(x, y, blackBishop);
            }
            else if (line.charAt(x) == "b".charAt(0)) {
              // White Bishop
              Bishop whiteBishop = new Bishop(x * CELLSIZE, y * CELLSIZE, true);
              whiteBishop.setSprite(this.loadImage(whiteBishopFileStr));
              this.whitePieces.add(whiteBishop);
              board.setChessPiece(x, y, whiteBishop);
            }
            else if (line.charAt(x) == "H".charAt(0)) {
              // Black Archbishop
              Archbishop blackArchbishop = new Archbishop(x * CELLSIZE, y * CELLSIZE, false);
              blackArchbishop.setSprite(this.loadImage(blackArchbishopFileStr));
              this.blackPieces.add(blackArchbishop);
              board.setChessPiece(x, y, blackArchbishop);
            }
            else if (line.charAt(x) == "h".charAt(0)) {
              // White Archbishop
              Archbishop whiteArchbishop = new Archbishop(x * CELLSIZE, y * CELLSIZE, true);
              whiteArchbishop.setSprite(this.loadImage(whiteArchbishopFileStr));
              this.whitePieces.add(whiteArchbishop);
              board.setChessPiece(x, y, whiteArchbishop);
            }
            else if (line.charAt(x) == "C".charAt(0)) {
              // Black Camel
              Camel blackCamel = new Camel(x * CELLSIZE, y * CELLSIZE, false);
              blackCamel.setSprite(this.loadImage(blackCamelFileStr));
              this.blackPieces.add(blackCamel);
              board.setChessPiece(x, y, blackCamel);
            }
            else if (line.charAt(x) == "c".charAt(0)) {
              // White Camel
              Camel whiteCamel = new Camel(x * CELLSIZE, y * CELLSIZE, true);
              whiteCamel.setSprite(this.loadImage(whiteCamelFileStr));
              this.whitePieces.add(whiteCamel);
              board.setChessPiece(x, y, whiteCamel);
            }
            else if (line.charAt(x) == "G".charAt(0)) {
              // Black General
              General blackGeneral = new General(x * CELLSIZE, y * CELLSIZE, false);
              blackGeneral.setSprite(this.loadImage(blackGeneralFileStr));
              this.blackPieces.add(blackGeneral);
              board.setChessPiece(x, y, blackGeneral);
            }
            else if (line.charAt(x) == "g".charAt(0)) {
              // White General
              General whiteGeneral = new General(x * CELLSIZE, y * CELLSIZE, true);
              whiteGeneral.setSprite(this.loadImage(whiteGeneralFileStr));
              this.whitePieces.add(whiteGeneral);
              board.setChessPiece(x, y, whiteGeneral);
            }
            else if (line.charAt(x) == "A".charAt(0)) {
              // Black Amazon
              Amazon blackAmazon = new Amazon(x * CELLSIZE, y * CELLSIZE, false);
              blackAmazon.setSprite(this.loadImage(blackAmazonFileStr));
              this.blackPieces.add(blackAmazon);
              board.setChessPiece(x, y, blackAmazon);
            }
            else if (line.charAt(x) == "a".charAt(0)) {
              // White Amazon
              Amazon whiteAmazon = new Amazon(x * CELLSIZE, y * CELLSIZE, true);
              whiteAmazon.setSprite(this.loadImage(whiteAmazonFileStr));
              this.whitePieces.add(whiteAmazon);
              board.setChessPiece(x, y, whiteAmazon);
            }
            else if (line.charAt(x) == "K".charAt(0)) {
              // Black King
              King blackKing = new King(x * CELLSIZE, y * CELLSIZE, false);
              blackKing.setSprite(this.loadImage(blackKingFileStr));
              this.blackPieces.add(blackKing);
              board.setChessPiece(x, y, blackKing);
            }
            else if (line.charAt(x) == "k".charAt(0)) {
              // White King
              King whiteKing = new King(x * CELLSIZE, y * CELLSIZE, true);
              whiteKing.setSprite(this.loadImage(whiteKingFileStr));
              this.whitePieces.add(whiteKing);
              board.setChessPiece(x, y, whiteKing);
            }
            else if (line.charAt(x) == "E".charAt(0)) {
              // Black Chancellor
              Chancellor blackChancellor = new Chancellor(x * CELLSIZE, y * CELLSIZE, false);
              blackChancellor.setSprite(this.loadImage(blackChancellorFileStr));
              this.blackPieces.add(blackChancellor);
              board.setChessPiece(x, y, blackChancellor);
            }
            else if (line.charAt(x) == "e".charAt(0)) {
              // White Chancellor
              Chancellor whiteChancellor = new Chancellor(x * CELLSIZE, y * CELLSIZE, true);
              whiteChancellor.setSprite(this.loadImage(whiteChancellorFileStr));
              this.whitePieces.add(whiteChancellor);
              board.setChessPiece(x, y, whiteChancellor);
            }
            else if (line.charAt(x) == "Q".charAt(0)) {
              // Black Queen
              Queen blackQueen = new Queen(x * CELLSIZE, y * CELLSIZE, false);
              blackQueen.setSprite(this.loadImage(blackQueenFileStr));
              this.blackPieces.add(blackQueen);
              board.setChessPiece(x, y, blackQueen);
            }
            else if (line.charAt(x) == "q".charAt(0)) {
              // White Queen
              Queen whiteQueen = new Queen(x * CELLSIZE, y * CELLSIZE, true);
              whiteQueen.setSprite(this.loadImage(whiteQueenFileStr));
              this.whitePieces.add(whiteQueen);
              board.setChessPiece(x, y, whiteQueen);
            }
          }
          y++;
      }
      sc.close();
      board.setChessBoard();
      clockTurn(false);
  } catch (FileNotFoundException e) {
      System.out.println("Could not find layout file.");
      System.exit(0);
  }
      
  }

  /**
   * Receive key pressed signal from the keyboard.
  */
  public void keyPressed(){
    if (this.keyCode == 69) {
      textSize(10);
      this.text("You resigned", 680, 400);
      this.textSize(1);
    }

  }

  /**
   * Sets up when a mouse is clicked
   */
  @Override
  public void mouseClicked(MouseEvent e) {
      int mouseX = e.getX();
      int mouseY = e.getY();
  
      int clickedTileX = (int) Math.round(mouseX / CELLSIZE);
      int clickedTileY = (int) Math.round(mouseY / CELLSIZE);

      resetGridColor();
  
      Piece[][] chessboard = board.getChessboard();
      currentChosenTile = chessboard[clickedTileY][clickedTileX];

      int kingX = (BoardUtils.getKingCoords(this.whitePieces, currentPlayer)[0] / CELLSIZE);
      int kingY = (BoardUtils.getKingCoords(this.whitePieces, currentPlayer)[1] / CELLSIZE);


        //Edge Case not to pick other player's colour
        if(currentChosenTile != null) {
          if (currentChosenTile.getPieceColor() != currentPlayer && lastChosenPiece == null) {
            currentChosenTile = null;
            lastChosenPiece = null;
            return;
          }
        }

        // Case 1: Both lastChosenPiece and currentChosenTile are null
        if(currentChosenTile == null && lastChosenPiece == null) {
          return;
        }

        // Case 2: lastChosenPiece is null and currentChosenTile is not
        if (currentChosenTile != null && lastChosenPiece == null) {
          resetGridColor();
          int[][] coords = currentChosenTile.getMove(chessboard);
          initTiles(coords);
          lastChosenPiece = currentChosenTile;
          return;
        }

        // Case 3: currentChosenTile is null and lastChosenPiece is not (In this case, a piece movement to an empty square)
        if (currentChosenTile == null && lastChosenPiece != null) {

          if (lastChosenPiece.getMove(chessboard) != null) { // Add this null check
              int[][] coords = lastChosenPiece.getMove(chessboard);
              initTiles(coords);
              // Moves the piece legally
              if (coords[clickedTileY][clickedTileX] == 1) {
                  resetGridColor();
                  this.yellowTile.clear();
                  initlastTiles(lastChosenPiece.getX(), lastChosenPiece.getY(), clickedTileX, clickedTileY);
                  board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
                  lastChosenPiece.movePiece(clickedTileX, clickedTileY);
                  makeRandomMove(blackPieces);
                  lastChosenPiece = null;
                  clockTurn(false);
                  inCheck = BoardUtils.checkforCheck(this.blackPieces, chessboard, kingX, kingY);
              } else {
                  resetGridColor();
              }
          }
        }


      // Case 4: Both currentChosenTile and lastChosenPiece are not null (In this case, a piece capture)
      if (currentChosenTile != null && lastChosenPiece != null) {

        int[][] coords = currentChosenTile.getMove(chessboard);
        initTiles(coords);
        if (currentChosenTile.getPieceColor() != lastChosenPiece.getPieceColor()) {
          resetGridColor();
          this.yellowTile.clear();
          initlastTiles(lastChosenPiece.getX(), lastChosenPiece.getY(), clickedTileX, clickedTileY);
          board.movePiece(lastChosenPiece.getX() / CELLSIZE, lastChosenPiece.getY() / CELLSIZE, clickedTileX, clickedTileY);
          removePieces(currentChosenTile.getPieceColor(), currentChosenTile);
          lastChosenPiece.movePiece(clickedTileX, clickedTileY);
          lastChosenPiece = null;
          makeRandomMove(blackPieces);
          clockTurn(false);
          inCheck = BoardUtils.checkforCheck(this.blackPieces, chessboard, kingX, kingY);
        }
      }
    }

  
    /**
     * Draw all elements in the game by current frame. 
    */
    public void draw() {

      initBoard();
      drawlastTiles();
      drawTiles();
      drawPieces();

      playerClock.display(680, 600);
      cpuClock.display(680, 40);

      playerClock.update();
      cpuClock.update();

      keyPressed();

    }

	
	// Add any additional methods or attributes you want. Please put classes in different files.

  /**
   * Initialises the chessboard
   */
  public void initBoard() {
    // draw the cells of the chessboard
    for (int i = 0; i < 14; i++) {
      for (int j = 0; j < 14; j++) {
        // determine the color of the cell based on its position
        if ((i + j) % 2 == 0) {
          fill(	244, 217, 176); // light color
        } else {
          fill(	189, 134, 93); // dark color
        }
        // draw the cell
        noStroke();
        rect(j * CELLSIZE + 1, i * CELLSIZE + 1, CELLSIZE, CELLSIZE);
      }
    }
  }

  /**
   * Initialises the tiles
   * @param coords The coords is recieved from the piece's legal moves and sets it according to the 2D array
   * of it.
   * 1's are null space moves
   * 2's are capture moves
   * 3 is the current Piece's location
   */
  public void initTiles(int[][] coords) {
      for (int i = 0; i < coords.length; i++) {
          for (int j = 0; j < coords[i].length; j++) {
              int x = coords[i][j];
              if (x == 1) {
                  Tile blueTile = new Tile(j * CELLSIZE + 1, i * CELLSIZE + 1, "blue");
                  this.blueTile.add(blueTile);
              } else if (x == 2) {
                  Tile lightredTile = new Tile(j * CELLSIZE + 1, i * CELLSIZE + 1, "light red");
                  this.lightredTile.add(lightredTile);
              } else if (x == 3) {
                  Tile greenTile = new Tile(j * CELLSIZE + 1, i * CELLSIZE + 1, "green");
                  this.greenTile.add(greenTile);
              }
          }
      }
  }

  /**
   * Initialises the yellow tiles
   * @param x1 Grabs x coordinate of the original spot's piece
   * @param y1 Grabs y coordinate of the original spot's piece
   * @param x2 Grabs the x coordinate of the new spot's piece
   * @param y2 Grabs the y coordinate of the new spot's piece
   */
  public void initlastTiles(int x1, int y1, int x2, int y2) {
    Tile yellowOne = new Tile(x1 + 1, y1 + 1, "yellow");
    Tile yellowTwo = new Tile(x2 * CELLSIZE + 1, y2  * CELLSIZE + 1, "yellow");
    this.yellowTile.add(yellowOne);
    this.yellowTile.add(yellowTwo);
  }

  /**
   * Draws the tiles
   */
  public void drawTiles() {
    for (Tile greenTile : this.greenTile) {
      greenTile.draw(this);
    }
    for (Tile blueTile : this.blueTile) {
      blueTile.draw(this);
    }
    for (Tile lightredTile : this.lightredTile) {
      lightredTile.draw(this);
    }
  }

  /**
   * Draws the last tiles
   */
  public void drawlastTiles() {
    for(Tile yellowTile : this.yellowTile) {
      yellowTile.draw(this);
    }
  }

  /**
   * Draws the pieces
   */
  public void drawPieces() {
      //Draws the Tiles
      for (Piece blackPieces : this.blackPieces) {
        blackPieces.draw(this);
      }
      for (Piece whitePieces : this.whitePieces) {
        whitePieces.draw(this);
      }
  }

  /**
   * Removes the pieces
   * @param color Is the colour of the piece type
   * @param piece Selected piece type that is going to be removed
   */
  public void removePieces(boolean color, Piece piece) {
    if (color == true) {
      this.whitePieces.remove(piece);
    }
    else if (color == false) {
      this.blackPieces.remove(piece);
    }
  }

  /**
   * Resets the tile colours
   */
  public void resetGridColor() {
    this.greenTile.clear();
    this.blueTile.clear();
    this.lightredTile.clear();
  }

  /**
   * This is the chessAI in which it picks a random piece and moves it somewhere
   * @param pieces Arraylist of the pieces needed to move
   */
  public void makeRandomMove(ArrayList<Piece> pieces) {
    // Get a random piece from the pieces ArrayList
    Random random = new Random();

    // Get current board
    Piece[][] chessboard = board.getChessboard();

    int kingX = (BoardUtils.getKingCoords(this.blackPieces, !currentPlayer)[0] / CELLSIZE);
    int kingY = (BoardUtils.getKingCoords(this.blackPieces, !currentPlayer)[1] / CELLSIZE);

    inCheck = BoardUtils.checkforCheck(this.whitePieces, chessboard, kingX, kingY);
    // inCheckMate = BoardUtils.checkforCheckMate(this.whitePieces, chessboard, kingX, kingY);
    

    while (true) {
        int randomPieceIndex = random.nextInt(pieces.size());
        Piece selectedPiece = pieces.get(randomPieceIndex);

        // Resets the parameters after Player has chosen
        cpuClock.start();
        this.yellowTile.clear();

        // Get the valid moves for the selected piece
        int[][] validMoves = selectedPiece.getMove(chessboard);

        if (inCheck) {
          // Selected Piece in this case is the king
          selectedPiece = chessboard[kingY][kingX];
          int[][] kingCoords = selectedPiece.getMove(chessboard);
          ArrayList<int[]> checkTiles = BoardUtils.getcheckTiles(kingCoords);
          validMoves = BoardUtils.getCheckKingMovement(this.whitePieces, checkTiles, board, kingX, kingY);
          System.out.println(Arrays.deepToString(validMoves));
        }

        //Find the indices of all the legal moves (1s and 2s) in the 'validMoves' array
        ArrayList<int[]> legalMoveIndices = new ArrayList<>();
        for (int i = 0; i < validMoves.length; i++) {
            for (int j = 0; j < validMoves[i].length; j++) {
                if (validMoves[i][j] == 1 || validMoves[i][j] == 2) {
                  int[] coordinates = {i , j};
                  legalMoveIndices.add(coordinates);
                }
            }
        }

        if(!legalMoveIndices.isEmpty()) {
            int randomMoveIndex = random.nextInt(legalMoveIndices.size());
            int[] selectedMoveIndex = legalMoveIndices.get(randomMoveIndex);

            int moveRow = selectedMoveIndex[0];
            int moveCol = selectedMoveIndex[1];

            if(validMoves[moveRow][moveCol] == 2) {
              Piece eliminatedPiece = chessboard[moveRow][moveCol];
              removePieces(eliminatedPiece.getPieceColor(), eliminatedPiece);
            }

            initlastTiles(selectedPiece.getX(), selectedPiece.getY(), moveCol, moveRow);

            board.movePiece(selectedPiece.getX() / CELLSIZE, selectedPiece.getY() / CELLSIZE, moveCol, moveRow);
            selectedPiece.movePiece(moveCol, moveRow);

            clockTurn(true);
            break;

        } else {
            pieces.remove(randomPieceIndex);
        }
    }
}

  /**
   * Class method in which it sets the clock timer after a move is made
   * @param turn To know which turn is which
   */
    public void clockTurn(boolean turn) {
      if (turn) {
        playerClock.stop();
        cpuClock.increment(this.cpuIncrements, false);
        playerFirst = false;
      }
      if (!turn) {
        cpuClock.stop();
        playerClock.start();
        playerClock.increment(this.playerIncrements, playerFirst);
      }
    }


    public static void main(String[] args) {
        PApplet.main("XXLChess.App");
    }

  }

