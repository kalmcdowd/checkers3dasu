/*
* The GameScreen class provides a Graphical User Interface and handles user
* actions relating to piece placement and movement in the 3D checkers game.
*
* Author:  David Clark
*/

package checkers;

import java.util.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.text.SimpleDateFormat;

public class GameScreen extends javax.swing.JFrame
{
    private static final int NIL = 0;
    final int PIXELS = 40;
    private final int ALLOWABLE_ROWS = 3;
    private Square[] square;
    private Referee referee;
    private Game game;
    private Move move;
    private int visitorCheckers = 0;
    private int visitorKings = 1;
    private int visitorBlocked = 2;
    private int visitorSafe = 1;
    private int visitorMines = 2;
    private int homeCheckers = 0;
    private int homeKings = 1;
    private int homeBlocked = 2;
    private int homeSafe = 1;
    private int homeMines = 2;
    private int width = 0;
    private int boardSize = 0;
    private boolean boardSetup = false;
    private boolean randomSetup = false;
    private String homePlayer;
    private String visitorPlayer;
    Square firstSelectMove  = null;
    Square secondSelectMove = null;
    Square firstSelectSetup;
    Square secondSelectSetup;
    ArrayList<Square> availableMoves;
    ArrayList<Square> moreJumps;
    ArrayList requiredJumps = new ArrayList();
    Random random;

    //load fonts
    Font oldEnglish_14 = loadFont(Font.PLAIN, 14);
    Font oldEnglish_16b = loadFont(Font.BOLD, 16);
    Font oldEnglish_16 = loadFont(Font.PLAIN, 16);
    Font oldEnglish_20 = loadFont(Font.PLAIN, 20);

    //load images
    ImageIcon board8X8 = new ImageIcon(getClass().getResource("/checkers/images/Board8x8.png"));
    ImageIcon board10X10 = new ImageIcon(getClass().getResource("/checkers/images/Board10x10.png"));
    ImageIcon squareBlack = new ImageIcon(getClass().getResource("/checkers/images/SquareBlack.png"));
    ImageIcon hSquareBlack = new ImageIcon(getClass().getResource("/checkers/images/hSquareBlack.png"));
    ImageIcon checkerBlack = new ImageIcon(getClass().getResource("/checkers/images/CheckerBlack.png"));
    ImageIcon hCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/hCheckerBlack.png"));
    ImageIcon rhCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/rhCheckerBlack.png"));
    ImageIcon checkerRed = new ImageIcon(getClass().getResource("/checkers/images/CheckerRed.png"));
    ImageIcon hCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/hCheckerRed.png"));
    ImageIcon rhCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/rhCheckerRed.png"));
    ImageIcon kingBlack = new ImageIcon(getClass().getResource("/checkers/images/KingBlack.png"));
    ImageIcon hKingBlack = new ImageIcon(getClass().getResource("/checkers/images/hKingBlack.png"));
    ImageIcon rhKingBlack = new ImageIcon(getClass().getResource("/checkers/images/rhKingBlack.png"));
    ImageIcon kingRed = new ImageIcon(getClass().getResource("/checkers/images/KingRed.png"));
    ImageIcon hKingRed = new ImageIcon(getClass().getResource("/checkers/images/hKingRed.png"));
    ImageIcon rhKingRed = new ImageIcon(getClass().getResource("/checkers/images/rhKingRed.png"));
    ImageIcon squareSafe = new ImageIcon(getClass().getResource("/checkers/images/SquareSafe.png"));
    ImageIcon hSquareSafe = new ImageIcon(getClass().getResource("/checkers/images/hSquareSafe.png"));
    ImageIcon safeCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/SafeCheckerBlack.png"));
    ImageIcon hSafeCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/hSafeCheckerBlack.png"));
    ImageIcon rhSafeCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/rhSafeCheckerBlack.png"));
    ImageIcon safeCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/SafeCheckerRed.png"));
    ImageIcon hSafeCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/hSafeCheckerRed.png"));
    ImageIcon rhSafeCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/rhSafeCheckerRed.png"));
    ImageIcon safeKingBlack = new ImageIcon(getClass().getResource("/checkers/images/SafeKingBlack.png"));
    ImageIcon hSafeKingBlack = new ImageIcon(getClass().getResource("/checkers/images/hSafeKingBlack.png"));
    ImageIcon rhSafeKingBlack = new ImageIcon(getClass().getResource("/checkers/images/rhSafeKingBlack.png"));
    ImageIcon safeKingRed = new ImageIcon(getClass().getResource("/checkers/images/SafeKingRed.png"));
    ImageIcon hSafeKingRed = new ImageIcon(getClass().getResource("/checkers/images/hSafeKingRed.png"));
    ImageIcon rhSafeKingRed = new ImageIcon(getClass().getResource("/checkers/images/rhSafeKingRed.png"));
    ImageIcon squareBlocked = new ImageIcon(getClass().getResource("/checkers/images/SquareBlocked.png"));
    ImageIcon hSquareBlocked = new ImageIcon(getClass().getResource("/checkers/images/hSquareBlocked.png"));
    ImageIcon setupCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/PieceCheckerBlack.png"));
    ImageIcon hSetupCheckerBlack = new ImageIcon(getClass().getResource("/checkers/images/hPieceCheckerBlack.png"));
    ImageIcon setupKingBlack = new ImageIcon(getClass().getResource("/checkers/images/PieceKingBlack.png"));
    ImageIcon hSetupKingBlack = new ImageIcon(getClass().getResource("/checkers/images/hPieceKingBlack.png"));
    ImageIcon setupCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/PieceCheckerRed.png"));
    ImageIcon hSetupCheckerRed = new ImageIcon(getClass().getResource("/checkers/images/hPieceCheckerRed.png"));
    ImageIcon setupKingRed = new ImageIcon(getClass().getResource("/checkers/images/PieceKingRed.png"));
    ImageIcon hSetupKingRed = new ImageIcon(getClass().getResource("/checkers/images/hPieceKingRed.png"));
    ImageIcon setupMine = new ImageIcon(getClass().getResource("/checkers/images/Mine.png"));
    ImageIcon hSetupMine = new ImageIcon(getClass().getResource("/checkers/images/hMine.png"));
    ImageIcon background = new ImageIcon(getClass().getResource("/checkers/images/Background.png"));
    ImageIcon displayLabel = new ImageIcon(getClass().getResource("/checkers/images/DisplayLabel.png"));

    //accepts a Game object and sets up the board accordingly.
    //used for loading a  game
    public GameScreen(Game savedGame)
    {
        //populate variables with vales from the saved game
        game = savedGame;
        square = game.getBoard();
        width = game.getWidth();
        boardSize = width * width;
        visitorPlayer = game.getVisitor();
        homePlayer = game.getHome();
        referee = new Referee(square, game);
        move = new Move();

        initComponents();
        initBackground();

        //re-add the mouse listener to all the squares
        for (int i = 0; i < boardSize / 2; i++)
        {
            square[i].addMouseListener(new java.awt.event.MouseAdapter()
            {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    Square mySquare =  (Square) evt.getSource();
                    squareClicked(mySquare);
                }
            });
            updateIcon(square[i]);
            lBoardPane.add(square[i], JLayeredPane.DEFAULT_LAYER);
        }

        for (int i = boardSize / 2; i < boardSize; i++)
        {
            square[i].addMouseListener(new java.awt.event.MouseAdapter()
            {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    Square mySquare =  (Square) evt.getSource();
                    squareClicked(mySquare);
                }
            });
            updateIcon(square[i]);
            rBoardPane.add(square[i], JLayeredPane.DEFAULT_LAYER);
        }

        initBoardExtras();
        initGamePlay();

        //if it's home's turn, toggle player's turn
        if (!game.getVisitorTurn())
            togglePlayer();
    }

    //set up the board.
    //used for new games
    public GameScreen(int size, String player1, String player2)
    {
        width = size;
        boardSize = width * width;
        boardSetup = true;

        if (size == 8)
            visitorCheckers = homeCheckers = 9; 
        else if (size == 10)
            visitorCheckers = homeCheckers = 14;

        square = new Square[boardSize];

        initComponents();
        initBackground();
        initBoard();

        setVisible(true);

        //find out which player is home/visitor
        coinToss(player1, player2);

        initBoardExtras();

        //initialize the objects that are used in game play
        game = new Game(width, visitorPlayer, homePlayer);
        referee = new Referee(square, game);
        move = new Move();
        
        if (randomSetup)
        {
            boardSetup = false;
            randomPlace();
        }
        else
        {
            boardSetup = true;
            initBoardSetup();
        }

        storeMove();
    }

    //randomly place all pieces
    public void randomPlace ()
    {
        random = new Random();
        int tmp;
        int numSquares = (width/2)*6;

        game.setVisitorTurn(true);
        Square[] randomList = new Square[(width*width)/2];

        if(width == 8)
        {
           //populate an array with all of the possible squares
            //that a piece can be placed.

            for(int i = 0; i < 12; i++)
            {
               randomList[i] = square[i];
            }
            for(int j = 0; j < 12; j++)
            {
               randomList[j+12] = square[j+32];
            }
            for(int k = 0; k < 4; k++)
            {
                randomList[k+24] = square[k+12];
            }
            for(int l = 0; l < 4; l++)
            {
                randomList[l+28] = square[l+44];
            }
         }

         //handle the 10x10 case
        if(width == 10)
        {
            //populate an array with all of the possible squares
            //that a piece can be placed.
            for(int i = 0; i < 15; i++)
            {
               randomList[i] = square[i];
            }
               for(int j = 0; j < 15; j++)
            {
               randomList[j+15] = square[50+j];
            }
            for(int k = 0; k < 10; k++)
            {
                randomList[k+30] = square[k+15];
            }
            for(int l = 0; l < 10; l++)
            {
                randomList[l+40] = square[l+65];
            }
        }

        //randomly place all visitor checkers.
        while(visitorCheckers != 0)
        {

            tmp = random.nextInt(numSquares);

            if(randomList[tmp].getPiece() == null)
            {
                randomList[tmp].setPiece(new Checker(game.getVisitorTurn()));
                updateIcon(randomList[tmp]);
                visitorCheckers--;
            }
        }

        //randomly place all visitor kings.
        while(visitorKings != 0)
        {
            tmp = random.nextInt(numSquares);

            if(randomList[tmp].getPiece() == null)
            {
                randomList[tmp].setPiece(new King(game.getVisitorTurn()));
                updateIcon(randomList[tmp]);
                visitorKings--;
            }
        }

        //randomly place all visitor blocked squares.
        while(visitorBlocked != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getPiece() == null && !randomList[tmp].getBlocked())
            {
                randomList[tmp].setBlocked(true);
                randomList[tmp].setIcon(squareBlocked);
                visitorBlocked--;
            }
        }

        //randomly place all visitor safe zones.
        while(visitorSafe != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getBlocked() != true)
            {
                randomList[tmp].setSafe(true);
                updateIcon(randomList[tmp]);
                visitorSafe--;
            }
        }

        //randomly place all visitor mines.
        while(visitorMines != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getBlocked() == false && 
                    randomList[tmp].getSafe() == false &&
                    !randomList[tmp].getMine())
            {
                randomList[tmp].setMine(true);
                randomList[tmp].setVisitorMine(true);
                visitorMines--;
            }
        }

         //populate the array with the available squares for the Home player
       if(width == 8)
       {
            for(int i = 0; i < 12; i++)
            {
                randomList[i] = square[i+20];
            }
            for(int j = 0; j < 12; j++)
            {
                randomList[j+12] = square[52+j];
            }
            for(int k = 0; k < 4; k++)
            {
                randomList[k+24] = square[k+16];
            }
            for(int l = 0; l < 4; l++)
            {
                randomList[l+28] = square[l+48];
            }
       }

       if(width == 10)
       {
           //populate the array with the available squares for the Home player
            for(int i = 0; i < 15; i++)
            {
               randomList[i] = square[i+35];
            }
            for(int j = 0; j < 15; j++)
            {
                randomList[j+15] = square[85+j];
            }
            for(int k = 0; k < 10; k++)
            {
                randomList[k+30] = square[k+25];
            }
            for(int l = 0; l < 10; l++)
            {
                randomList[l+40] = square[l+75];
            }
       }

        game.setVisitorTurn(false);

        //randomly place all Home checkers.
        while(homeCheckers != 0)
        {
            tmp = random.nextInt(numSquares);

            if(randomList[tmp].getPiece() == null)
            {
                randomList[tmp].setPiece(new Checker(game.getVisitorTurn()));
                updateIcon(randomList[tmp]);
                homeCheckers--;

            }
        }

        //randomly place all Home kings.
        while(homeKings != 0)
        {
            tmp = random.nextInt(numSquares);

            if(randomList[tmp].getPiece() == null)
            {
                randomList[tmp].setPiece(new King(game.getVisitorTurn()));
                updateIcon(randomList[tmp]);
                homeKings--;
            }
        }

        //randomly place all Home blocked squares.
        while(homeBlocked != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getPiece() == null && !randomList[tmp].getMine())
            {
                randomList[tmp].setBlocked(true);
                randomList[tmp].setIcon(squareBlocked);
                homeBlocked--;
            }
        }

        //randomly place all Home safe zones.
        while(homeSafe != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getBlocked() != true)
            {
                randomList[tmp].setSafe(true);
                updateIcon(randomList[tmp]);
                homeSafe--;
            }
        }

        //randomly place all Home mines.
        while(homeMines != 0)
        {
            tmp = random.nextInt((width*width)/2);

            if(randomList[tmp].getBlocked() != true && 
                    randomList[tmp].getSafe() != true &&
                    !randomList[tmp].getMine())
            {
                randomList[tmp].setMine(true);
                randomList[tmp].setVisitorMine(false);
                homeMines--;
            }
        }

        //the visitor has the first turn
        game.setVisitorTurn(true);

        initGamePlay();
    }

    //determine the home and visitor player
    private void coinToss(String player1, String player2)
    {
        CoinToss toss = new CoinToss(null, true, player1, player2);
        toss.setLocationRelativeTo(this);
        toss.setVisible(true);

        //use the information from the coin toss to determine the home and
        //visitor player
        if (toss.player1Home())
        {
            homePlayer = player1;
            visitorPlayer = player2;
        }
        else
        {
            homePlayer = player2;
            visitorPlayer = player1;
        }

        //find out if the user would like to set up the board
        //randomly or manually
        if (toss.randomSetup())
            randomSetup = true;
        else
            randomSetup = false;
    }

    //setup the image for the background and the two boards.
    private void initBackground()
    {
        BackgroundPane = new JLayeredPane();
        BackgroundLabel = new JLabel();
        lBoardPane = new JLayeredPane();
        lBackgroundLabel = new JLabel();
        rBoardPane = new JLayeredPane();
        rBackgroundLabel = new JLabel();

        //set up the background
        getContentPane().add(BackgroundPane);
        BackgroundPane.setBounds(0, 0, 888, 640);
        BackgroundPane.add(BackgroundLabel, -1);
        BackgroundLabel.setIcon(background);
        BackgroundLabel.setBounds(0, 0, 888, 640);

        //set the size and location of the board background labels and
        //add them to the layered pane.
        lBackgroundLabel.setBounds(0, 0, PIXELS * width, PIXELS * width);
        lBoardPane.add(lBackgroundLabel, JLayeredPane.DEFAULT_LAYER);
        BackgroundPane.add(lBoardPane, 0);
        rBackgroundLabel.setBounds(0, 0, PIXELS * width, PIXELS * width);
        rBoardPane.add(rBackgroundLabel, JLayeredPane.DEFAULT_LAYER);
        BackgroundPane.add(rBoardPane, 0);

        if (width == 10)
        {
            //define the placement of the 10x10 board
            lBoardPane.setBounds(12, 10, PIXELS * width, PIXELS * width);
            rBoardPane.setBounds(470, 180, PIXELS * width, PIXELS * width);

            //add the background picture to the label
            lBackgroundLabel.setIcon(board10X10);
            rBackgroundLabel.setIcon(board10X10);
        }
        else if (width == 8)
        {
            //define the placement of the 8x8 board
            //the PIXELS offset is added in order to center the smaller board
            lBoardPane.setBounds(12 + PIXELS, 10 + PIXELS, PIXELS * width, PIXELS * width);
            rBoardPane.setBounds(470 + PIXELS, 180 + PIXELS, PIXELS * width, PIXELS * width);

            //add the background picture to the label
            lBackgroundLabel.setIcon(board8X8);
            rBackgroundLabel.setIcon(board8X8);
        }
    }

    //************************ initialize the board ***********************//
    // The board is initialized as an array of Square objects (a Square is //
    // an extension of the JLabel object).  The Square objects are         //
    // initialized with a black background and are placed in every other   //
    // spot on the game board.                                             //
    // Each Square is given a 3-Dimensional position in the format         //
    // (board, row, column), with board referring to the board number      //
    // (1 or 2), row referring to the row number (1 through the height of  //
    // board) and column referring to the column number (1 through the     //
    // width of the board).                                                //
    /////////////////////////////////////////////////////////////////////////
    private void initBoard()
    {
        int board = 1;
        int row = 1;
        int col = 2;
        boolean halfFlag = false;

        //the vert and horiz values are manipulated in the 'for' loop to place
        //the black squares between the red "dummy" squares on the board
        int vert = 0;
        int horiz = PIXELS;

        for (int i = 0; i < boardSize; i++)
        {
            //once half the squares have been placed, start adding squares to
            //the second board
            if (i == boardSize/2)
            {
                board = 2;
                row = 1;
                col = 2;
                vert = 0;
                horiz = PIXELS;
                halfFlag = true;
            }

            //initialize a 3D Position, give the position to the square, give
            //the square its location on the board and give it a mouse listener
            this.square[i] = new Square(new Position(board, row, col), i);
            this.square[i].setIcon(squareBlack);
            this.square[i].setBounds(horiz, vert, PIXELS, PIXELS);
            this.square[i].addMouseListener(new java.awt.event.MouseAdapter()
            {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt)
                {
                    Square mySquare =  (Square) evt.getSource();
                    squareClicked(mySquare);
                }
            });

            //if half the squares have been placed, add the square to board 2,
            //otherwise add the square to board 1
            if (halfFlag)
                rBoardPane.add(this.square[i], JLayeredPane.DEFAULT_LAYER);
            else
                lBoardPane.add(this.square[i], JLayeredPane.DEFAULT_LAYER);

            //There are two cases for reaching the end of a row:
            //case 1 - the square has been added to the last spot on the row
            //case 2 - the square has been added to the second to the last
            //spot on the row (a red square occupies the last spot)
            if (col % width == 0)  //case 1
            {
                row++;
                col = 1;
                vert = vert + PIXELS;
                horiz = 0;
            }
            else if (col % width == width - 1)  //case 2
            {
                row++;
                col = 2;
                vert = vert + PIXELS;
                horiz = PIXELS;
            }
            //if the end of the row has not been reached, increment the column
            //and the horizontal location
            else
            {
                col = col + 2;
                horiz = horiz + (PIXELS * 2);
            }
        }
    }

    //recieve and manage "clicks" from the user on the GUI
    private void squareClicked(Square sq)
    {
        //the board is in setup phase
        if (boardSetup)
            setupClicked(sq);
        //the board is not in setup phase
        else
        {
            //the user has not yet selected a piece to move
            if (firstSelectMove == null)
            {
                //validate that the correct user is attempting to move one of
                //his/her own pieces
                if (referee.validateTurn(sq.getPiece()))
                {
                    //keep track of the first square selected
                    firstSelectMove = sq;

                    //if the user is restricted by a required jump, make sure
                    //they can only move pieces with a jump available
                    if (move.requiredJump())
                    {
                        //validate that the selected square has a jump available
                        if (requiredJumps.contains(sq))
                        {
                            //get and highlight the available jumps for the
                            //selected square
                            availableMoves = referee.showJumps(sq, move);
                            highlightMoves(availableMoves);
                        }
                        //if the user selects a piece that does not have a jump
                        //available, clear thier first selection and make them
                        //start over
                        else
                            firstSelectMove = null;
                    }
                    //there are no required jumps
                    else
                    {
                        //get and highlight the available moves for the selected
                        //square
                        availableMoves = referee.showMoves(sq, move);
                        highlightMoves(availableMoves);
                    }
                }
            }
            //the user has already selected the piece they would like to move
            else
            {
                //keep track of the destination square
                secondSelectMove = sq;
                removeHighlights(availableMoves);

                //send the selections to the Referee and recieve a new move
                //object that will inform us of what decisions the referee
                //made
                move = referee.executeMove(firstSelectMove, secondSelectMove,
                        availableMoves, move);

                //valid move
                if (move.isValidMove())
                {
                    //remove message
                    lTextLabel.setText("");
                    
                    //if a valid move has occured remove the required jumps flag
                    if (move.requiredJump())
                    {
                        move.setRequiredJump(false);
                        removeRedHighlights(requiredJumps);
                    }

                    //if there are multiple pieces that can be captured by a
                    //jump, let the user select which piece they would like
                    //to capture
                    if (move.jumpSize() > 1)
                    {
                        setHighlight(move.getJump().get(0));
                        setHighlight(move.getJump().get(1));

                        //use a dialog box to ask the user
                        Object[] options = {"Left Board", "Right Board"};
                        int n = JOptionPane.showOptionDialog(this,
                            "There are two possibilities for this move."
                            + "\nWhich board do you want to remove the piece from?",
                            "Make a selection", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[1]);

                        //left board selected
                        if (n == 0) 
                        {
                            //find out which piece is on the left board and
                            //remove it
                            if (move.getJump().get(0).getIndex() <
                                    move.getJump().get(1).getIndex())
                                referee.removePiece(move.getJump().get(0));
                            else
                                referee.removePiece(move.getJump().get(1));
                        }
                        //right board selected
                        else 
                        {
                            //find out which piece is on the right board and
                            //remove it
                            if (move.getJump().get(0).getIndex() <
                                    move.getJump().get(1).getIndex())
                                referee.removePiece(move.getJump().get(1));
                            else
                                referee.removePiece(move.getJump().get(0));
                        }

                        //update the board to reflect the decisions made
                        updateIcon(move.getJump().get(0));
                        updateIcon(move.getJump().get(1));
                        updateIcon(firstSelectMove);
                        updateIcon(secondSelectMove);

                        //now that a jump has occured, the destination is the
                        //start location for any multiple jump scenarios
                        firstSelectMove = secondSelectMove;

                        //if the user landed on a mine or got kinged, the move
                        //is over
                        if (move.landedOnMine() || move.gotKinged())
                            availableMoves.clear();
                        //check for additional jumps
                        else
                            availableMoves = referee.showJumps(firstSelectMove, move);

                        //allow for multiple jumps, if there are any available
                        if (!availableMoves.isEmpty())
                            move.setMoreJumps(true);
                        else
                            move.setMoreJumps(false);
                    }
                    //normal jump scenario (only one available jump)
                    else if (move.jumpSize() == 1)
                    {
                        //update the board to reflect that the jump has occured
                        updateIcon(move.getJump().get(0));
                        updateIcon(firstSelectMove);
                        updateIcon(secondSelectMove);

                        //now that a jump has occured, the destination is the
                        //start location for any multiple jump scenarios
                        firstSelectMove = secondSelectMove;

                        //if the user landed on a mine or got kinged, the move
                        //is over
                        if (move.landedOnMine())
                            availableMoves.clear();
                        else if (move.gotKinged())                        
                            availableMoves.clear();

                        //check for additional jumps
                        else
                            availableMoves = referee.showJumps(firstSelectMove, move);

                        //allow for multiple jumps, if there are any available
                        if (!availableMoves.isEmpty())
                        {
                            move.setMoreJumps(true);
                            lTextLabel.setText("Additional jump(s) available!");
                        }
                        else
                            move.setMoreJumps(false);
                    }
                    //no jumps made
                    else
                    {
                        //update the board to reflect that a move has occured
                        updateIcon(firstSelectMove);
                        updateIcon(secondSelectMove);
                    }
                }

                //provide feedback to user
                if (move.landedOnMine())
                {
                    lTextLabel.setText("Smart Mine!");
                }
                else if (move.gotKinged())
                   lTextLabel.setText("Kinged!");


                //toggle the turn and clear the move if there was a valid move
                //made with no additional jumps or if user has additional
                //available jumps that they do not want to take
                if((move.isValidMove() && !move.moreJumps()) ||
                      (!move.isValidMove() && move.moreJumps()))
                {
                    togglePlayer();
                    storeMove();
                    referee.toggleTurn();
                    move.clearMove();

                    //check for required jumps
                    requiredJumps = referee.requiredJumps(move);
                    if (move.requiredJump())
                    {
                        redHighlightMoves(requiredJumps);
                        lTextLabel.setText("Required jump(s)!");
                    }
                }

                //don't reset the start location when there are multiple jumps
                if (!move.moreJumps())
                    firstSelectMove = null;

                //allow multiple jumps on a single turn
                if (move.moreJumps())
                    highlightMoves(availableMoves);

                move.clearJump();
            }

            //***** check if the game is over *****//
            //the home player has won the game
            if (move.getGameOver() == Main.HOME_WON)
            {
                //inform the users that the game is over
                JOptionPane.showMessageDialog(this, "GAME OVER!\n" +
                      homePlayer + " has won the game!", "Game Over",
                      JOptionPane.INFORMATION_MESSAGE, setupKingRed);

                // update statistics based on the home player winning the game,
                // and the visitor player losing the game
                User homeUser = null;
                User visitorUser = null;
                for (User user : Main.storage.getUsers())
                {
                    if (user.getUserName().equals(homePlayer)) 
                        homeUser = user;
                }
                for (User user : Main.storage.getUsers())
                {
                    if (user.getUserName().equals(visitorPlayer)) 
                        visitorUser = user;
                }

                // record the win for the home player, and the loss for the
                // visitor player
                homeUser.getStats().addWin();
                visitorUser.getStats().addLoss();

                // update the players' pvp records
                if (homeUser.getStats().pvpRecords.containsKey(visitorUser)) 
                   homeUser.getStats().pvpRecords.get(visitorUser).addWin();
                else
                     homeUser.getStats().pvpRecords.put(visitorUser, new Record(1,0,0));

                if (visitorUser.getStats().pvpRecords.containsKey(homeUser)) 
                    visitorUser.getStats().pvpRecords.get(homeUser).addLoss();
                else
                    visitorUser.getStats().pvpRecords.put(homeUser, new Record(0,1,0));
                
                Main.storage.saveStorage();
                Main.restart();
                dispose();
            }
            //the visitor player has won the game
            else if (move.getGameOver() == Main.VISITOR_WON)
            {
                //inform the users that the game is over
                JOptionPane.showMessageDialog(this, "GAME OVER!\n" +
                      visitorPlayer + " has won the game!", "Game Over",
                      JOptionPane.INFORMATION_MESSAGE, setupKingBlack);

                // update statistics based on the visitor player winning the
                // game, and the home player losing the game
                User homeUser = null;
                User visitorUser = null;
                for (User user : Main.storage.getUsers())
                {
                    if (user.getUserName().equals(homePlayer)) 
                        homeUser = user;
                }

                for (User user : Main.storage.getUsers())
                {
                    if (user.getUserName().equals(visitorPlayer)) 
                        visitorUser = user;
                }

                // record the win for the visitor player, and the loss for the
                // home player
                visitorUser.getStats().addWin();
                homeUser.getStats().addLoss();

                // update the players' pvp records
                if (homeUser.getStats().pvpRecords.containsKey(visitorUser)) 
                   homeUser.getStats().pvpRecords.get(visitorUser).addLoss();
                else 
                     homeUser.getStats().pvpRecords.put(visitorUser, new Record(0,1,0));
                
                if (visitorUser.getStats().pvpRecords.containsKey(homeUser)) 
                    visitorUser.getStats().pvpRecords.get(homeUser).addWin();
                 else 
                    visitorUser.getStats().pvpRecords.put(homeUser, new Record(1,0,0));
                
                Main.storage.saveStorage();
                Main.restart();
                dispose();
            }
        }
    }

    //store the current state of the board in an array of integers, each integer
    //corresponding to a specific square icon
    public void storeMove()
    {
        int[] boardState = new int[boardSize];

        for (int i = 0; i < square.length; i++)
        {
            if (square[i].getIcon() == squareBlack)
                boardState[i] = Main.SQ_BL;
            else if (square[i].getIcon() == checkerBlack)
                boardState[i] = Main.CK_BL;
            else if (square[i].getIcon() == checkerRed)
                boardState[i] = Main.CK_RD;
            else if (square[i].getIcon() == kingBlack)
                boardState[i] = Main.KG_BL;
            else if (square[i].getIcon() == kingRed)
                boardState[i] = Main.KG_RD;
            else if (square[i].getIcon() == squareSafe)
                boardState[i] = Main.SQ_SF;
            else if (square[i].getIcon() == safeCheckerBlack)
                boardState[i] = Main.SF_CK_BL;
            else if (square[i].getIcon() == safeCheckerRed)
                boardState[i] = Main.SF_CK_RD;
            else if (square[i].getIcon() == safeKingBlack)
                boardState[i] = Main.SF_KG_BL;
            else if (square[i].getIcon() == safeKingRed)
                boardState[i] = Main.SF_KG_RD;
            else if (square[i].getIcon() == squareBlocked)
                boardState[i] = Main.SQ_BO;
        }

        game.storeMove(boardState);
    }

    //toggle highlight of player whose turn it is
    public void togglePlayer()
    {
        //if the visitor player is not currently bold, make it bold and
        //unbold the home player
        if (visitorIconLabel.getBorder() == null)
        {
            visitorIconLabel.setBorder(BorderFactory.createEtchedBorder(
                javax.swing.border.EtchedBorder.RAISED,
                new java.awt.Color(255, 255, 0), null));
            visitorNameLabel.setFont(oldEnglish_16b);
            homeIconLabel.setBorder(null);
            homeNameLabel.setFont(oldEnglish_16);
        }
        //if the home player is not currently bold, make it bold and
        //unbold the visitor player
        else
        {
            visitorIconLabel.setBorder(null);
            visitorNameLabel.setFont(oldEnglish_16);
            homeIconLabel.setBorder(BorderFactory.createEtchedBorder(
                javax.swing.border.EtchedBorder.RAISED,
                new java.awt.Color(255, 255, 0), null));
            homeNameLabel.setFont(oldEnglish_16b);
        }
    }

    //update the icon on a square that has been manipulated by the referee
    private void updateIcon(Square sq)
    {
        //blocked square
        if (sq.getBlocked())
            sq.setIcon(squareBlocked);
        //safe zone
        else if (sq.getSafe())
        {
            //empty safe square
            if (sq.getPiece() == null)
                sq.setIcon(squareSafe);
            //safe checker
            else if (sq.getPiece() instanceof Checker)
            {
                //safe black checker
                if (sq.getPiece().getColor() == Main.BLACK)
                    sq.setIcon(safeCheckerBlack);
                //safe red checker
                else  
                    sq.setIcon(safeCheckerRed);
            }
            //safe king
            else if (sq.getPiece() instanceof King)
            {
                //safe black king
                if (sq.getPiece().getColor() == Main.BLACK)
                    sq.setIcon(safeKingBlack);
                //safe red king
                else
                    sq.setIcon(safeKingRed);
            }
        }
        //regular square
        else 
        {
            //empty square
            if (sq.getPiece() == null)
                sq.setIcon(squareBlack);
            //checker
            else if (sq.getPiece() instanceof Checker)
            {
                //black checker
                if (sq.getPiece().getColor() == Main.BLACK)
                    sq.setIcon(checkerBlack);
                //red checker
                else
                    sq.setIcon(checkerRed);
            }
            //king
            else if (sq.getPiece() instanceof King)
            {
                //black king
                if (sq.getPiece().getColor() == Main.BLACK)
                    sq.setIcon(kingBlack);
                //red king
                else
                    sq.setIcon(kingRed);
            }
        }
    }

    //highlight a list of moves
    private void highlightMoves(ArrayList<Square> moves)
    {
        if (moves != null)
            for (int i = 0; i < moves.size(); i++)
                setHighlight(moves.get(i));
    }

    //highlight in red a list of moves
    private void redHighlightMoves(ArrayList<Square> moves)
    {
        if (moves != null)
            for (int i = 0; i < moves.size(); i++)
                setRedHighlight(moves.get(i));
    }

    //remove the highlights from a list of moves
    private void removeHighlights(ArrayList<Square> moves)
    {
        if (moves != null)
            for (int i = 0; i < moves.size(); i++)
                removeHighlight(moves.get(i));
    }

    //remove the red highlights from a list of moves
    private void removeRedHighlights(ArrayList<Square> moves)
    {
        if (moves != null)
            for (int i = 0; i < moves.size(); i++)
                removeRedHighlight(moves.get(i));
    }

    //highlight a given square
    private void setHighlight(Square sq)
    {
        if(sq.getIcon() == squareBlack)
            sq.setIcon(hSquareBlack);
        else if (sq.getIcon() == checkerBlack)
            sq.setIcon(hCheckerBlack);
        else if (sq.getIcon() == kingBlack)
            sq.setIcon(hKingBlack);
        else if (sq.getIcon() == checkerRed)
            sq.setIcon(hCheckerRed);
        else if (sq.getIcon() == kingRed)
            sq.setIcon(hKingRed);
        else if (sq.getIcon() == squareSafe)
            sq.setIcon(hSquareSafe);
        else if (sq.getIcon() == safeCheckerBlack)
            sq.setIcon(hSafeCheckerBlack);
        else if (sq.getIcon() == safeKingBlack)
            sq.setIcon(hSafeKingBlack);
        else if (sq.getIcon() == safeCheckerRed)
            sq.setIcon(hSafeCheckerRed);
        else if (sq.getIcon() == safeKingRed)
            sq.setIcon(hSafeKingRed);
        else if (sq.getIcon() == squareBlocked)
            sq.setIcon(hSquareBlocked);
        else if (sq.getIcon() == setupCheckerBlack)
            sq.setIcon(hSetupCheckerBlack);
        else if (sq.getIcon() == setupKingBlack)
            sq.setIcon(hSetupKingBlack);
        else if (sq.getIcon() == setupCheckerRed)
            sq.setIcon(hSetupCheckerRed);
        else if (sq.getIcon() == setupKingRed)
            sq.setIcon(hSetupKingRed);
        else if (sq.getIcon() == setupMine)
            sq.setIcon(hSetupMine);

    }

    //highlight in red a given square
    private void setRedHighlight(Square sq)
    {
        if (sq.getIcon() == checkerBlack)
            sq.setIcon(rhCheckerBlack);
        else if (sq.getIcon() == kingBlack)
            sq.setIcon(rhKingBlack);
        else if (sq.getIcon() == checkerRed)
            sq.setIcon(rhCheckerRed);
        else if (sq.getIcon() == kingRed)
            sq.setIcon(rhKingRed);
        else if (sq.getIcon() == safeCheckerBlack)
            sq.setIcon(rhSafeCheckerBlack);
        else if (sq.getIcon() == safeKingBlack)
            sq.setIcon(rhSafeKingBlack);
        else if (sq.getIcon() == safeCheckerRed)
            sq.setIcon(rhSafeCheckerRed);
        else if (sq.getIcon() == safeKingRed)
            sq.setIcon(rhSafeKingRed);
    }

    //remove the highlight from a given square
    private void removeHighlight(Square sq)
    {
        if(sq.getIcon() == hSquareBlack)
            sq.setIcon(squareBlack);
        else if (sq.getIcon() == hCheckerBlack)
            sq.setIcon(checkerBlack);
        else if (sq.getIcon() == hKingBlack)
            sq.setIcon(kingBlack);
        else if (sq.getIcon() == hCheckerRed)
            sq.setIcon(checkerRed);
        else if (sq.getIcon() == hKingRed)
            sq.setIcon(kingRed);
        else if (sq.getIcon() == hSquareSafe)
            sq.setIcon(squareSafe);
        else if (sq.getIcon() == hSafeCheckerBlack)
            sq.setIcon(safeCheckerBlack);
        else if (sq.getIcon() == hSafeKingBlack)
            sq.setIcon(safeKingBlack);
        else if (sq.getIcon() == hSafeCheckerRed)
            sq.setIcon(safeCheckerRed);
        else if (sq.getIcon() == hSafeKingRed)
            sq.setIcon(safeKingRed);
        else if (sq.getIcon() == hSquareBlocked)
            sq.setIcon(squareBlocked);
        else if (sq.getIcon() == hSetupCheckerBlack)
            sq.setIcon(setupCheckerBlack);
        else if (sq.getIcon() == hSetupKingBlack)
            sq.setIcon(setupKingBlack);
        else if (sq.getIcon() == hSetupCheckerRed)
            sq.setIcon(setupCheckerRed);
        else if (sq.getIcon() == hSetupKingRed)
            sq.setIcon(setupKingRed);
        else if (sq.getIcon() == hSetupMine)
            sq.setIcon(setupMine);
    }

    //remove the red highlight from a given square
    private void removeRedHighlight(Square sq)
    {
        if (sq.getIcon() == rhCheckerBlack)
            sq.setIcon(checkerBlack);
        else if (sq.getIcon() == rhKingBlack)
            sq.setIcon(kingBlack);
        else if (sq.getIcon() == rhCheckerRed)
            sq.setIcon(checkerRed);
        else if (sq.getIcon() == rhKingRed)
            sq.setIcon(kingRed);
        else if (sq.getIcon() == rhSafeCheckerBlack)
            sq.setIcon(safeCheckerBlack);
        else if (sq.getIcon() == rhSafeKingBlack)
            sq.setIcon(safeKingBlack);
        else if (sq.getIcon() == rhSafeCheckerRed)
            sq.setIcon(safeCheckerRed);
        else if (sq.getIcon() == rhSafeKingRed)
            sq.setIcon(safeKingRed);
    }

    //initialize objects that will be used in board setup and
    //normal game play
    private void initBoardExtras()
    {
        lMessagePane = new JLayeredPane();
        lMessageLabel = new JLabel();
        lTextLabel = new JLabel();
        rMessagePane = new JLayeredPane();
        rMessageLabel = new JLabel();
        rPlayerLabel = new JLabel();
        lPlayerLabel = new JLabel();
        homeIconLabel = new JLabel(setupCheckerRed, JLabel.CENTER);
        homeNameLabel = new JLabel(homePlayer);
        visitorIconLabel = new JLabel(setupCheckerBlack, JLabel.CENTER);
        visitorNameLabel = new JLabel(visitorPlayer);

        //add the top-right message/button container
        BackgroundPane.add(lMessagePane, 0);
        lMessagePane.setBounds(40, 430, 340, 145);

        //add the bottom-left message/button container
        BackgroundPane.add(rMessagePane, 0);
        rMessagePane.setBounds(500, 20, 340, 145);

        //bottom-left image
        lMessageLabel.setIcon(displayLabel);
        lMessageLabel.setBounds(0, 0, 340, 145);
        lMessagePane.add(lMessageLabel, -1);

        //bottom-left text label
        lTextLabel.setBounds(0, 0, 340, 145);
        lTextLabel.setFont(oldEnglish_20);
        lTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lMessagePane.add(lTextLabel, 0);

        //top-right image
        rMessageLabel.setIcon(displayLabel);
        rMessageLabel.setBounds(0, 0, 340, 145);
        rMessagePane.add(rMessageLabel, -1);

        //set up the top-right container
        rPlayerLabel.setBounds(5,5,330,20);
        rPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rPlayerLabel.setFont(oldEnglish_16);
        rPlayerLabel.setText(visitorPlayer);
        rMessagePane.add(rPlayerLabel,0);

        //add visitor icon and label to top-right container
        visitorIconLabel.setBounds(20, 20, 40, 40);
        rMessagePane.add(visitorIconLabel, 0);
        visitorIconLabel.setVisible(false);
        visitorNameLabel.setBounds(65, 20, 275, 40);
        rMessagePane.add(visitorNameLabel, 0);
        visitorNameLabel.setVisible(false);
        
        //add home icon and label to top-right container
        homeIconLabel.setBounds(20, 75, 40, 40);
        rMessagePane.add(homeIconLabel, 0);
        homeIconLabel.setVisible(false);
        homeNameLabel.setBounds(65, 75, 275, 40);
        rMessagePane.add(homeNameLabel, 0);
        homeNameLabel.setVisible(false);

        //add message label to bottom-left container
        lPlayerLabel.setBounds(5,5,330,20);
        lPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lPlayerLabel.setFont(oldEnglish_16);
        lPlayerLabel.setText(homePlayer);
        lMessagePane.add(lPlayerLabel,0);
    }

    //*********************** initialize board setup **********************//
    // When the board is in the board setup phase (as determined by the    //
    // boardSetup flag), the user will be provided with additional "setup" //
    // icons (implemented as Squares) that will aid in the board setup     //
    // process.                                                            //
    // The "setup" Squares will be given a Position object in the format   //
    // (board, row, column), with the board equal to 0, row referring      //
    // to the row of "setup" icons, and column referring to the column of  //
    // "setup" icons.                                                      //
    /////////////////////////////////////////////////////////////////////////
    private void initBoardSetup()
    {
        optionsMenu.setEnabled(false);
        saveMenuItem.setEnabled(false);

        //black checker
        placeVisitorChecker = new Square(new Position(0, 1, 1), NIL);
        placeVisitorChecker.setIcon(setupCheckerBlack);
        rMessagePane.add(placeVisitorChecker, 0);
        placeVisitorChecker.setBounds(80, 35, 40, 40);
        placeVisitorChecker.setToolTipText("Checker");
        placeVisitorChecker.addMouseListener(mouseAdapter);
        //black checker count
        visitorCheckerLabel = new JLabel();
        visitorCheckerLabel.setText("x " + visitorCheckers);
        rMessagePane.add(visitorCheckerLabel, 0);
        visitorCheckerLabel.setBounds(125, 45, 30, 20);

        //black king
        placeVisitorKing = new Square(new Position(0, 1, 2), NIL);
        placeVisitorKing.setIcon(setupKingBlack);
        rMessagePane.add(placeVisitorKing, 0);
        placeVisitorKing.setBounds(200, 35, 40, 40);
        placeVisitorKing.setToolTipText("King");
        placeVisitorKing.addMouseListener(mouseAdapter);
        //black king count
        visitorKingLabel = new JLabel();
        visitorKingLabel.setText("x " + visitorKings);
        rMessagePane.add(visitorKingLabel, 0);
        visitorKingLabel.setBounds(245, 45, 30, 20);

        //visitor blocked
        placeVisitorBlocked = new Square(new Position(0, 2, 1), NIL);
        placeVisitorBlocked.setIcon(squareBlocked);
        rMessagePane.add(placeVisitorBlocked, 0);
        placeVisitorBlocked.setBounds(20, 85, 40, 40);
        placeVisitorBlocked.setToolTipText("Blocked Square");
        placeVisitorBlocked.addMouseListener(mouseAdapter);
        //visitor blocked count
        visitorBlockedLabel = new JLabel();
        visitorBlockedLabel.setText("x " + visitorBlocked);
        rMessagePane.add(visitorBlockedLabel, 0);
        visitorBlockedLabel.setBounds(65, 95, 30, 20);

        //visitor safe
        placeVisitorSafe = new Square(new Position(0, 2, 2), NIL);
        placeVisitorSafe.setIcon(squareSafe);
        rMessagePane.add(placeVisitorSafe, 0);
        placeVisitorSafe.setBounds(140, 85, 40, 40);
        placeVisitorSafe.setToolTipText("Safe Zone");
        placeVisitorSafe.addMouseListener(mouseAdapter);
        //visitor safe count
        visitorSafeLabel = new JLabel();
        visitorSafeLabel.setText("x " + visitorSafe);
        rMessagePane.add(visitorSafeLabel, 0);
        visitorSafeLabel.setBounds(185, 95, 30, 20);

        //visitor mine
        placeVisitorMine = new Square(new Position(0, 2, 3), NIL);
        placeVisitorMine.setIcon(setupMine);
        rMessagePane.add(placeVisitorMine, 0);
        placeVisitorMine.setBounds(260, 85, 40, 40);
        placeVisitorMine.setToolTipText("Smart Mine");
        placeVisitorMine.addMouseListener(mouseAdapter);
        //visitor mine count
        visitorMineLabel = new JLabel();
        visitorMineLabel.setText("x " + visitorMines);
        rMessagePane.add(visitorMineLabel, 0);
        visitorMineLabel.setBounds(305, 95, 30, 20);

        //red checker
        placeHomeChecker = new Square(new Position(0, 3, 1), NIL);
        placeHomeChecker.setIcon(setupCheckerRed);
        lMessagePane.add(placeHomeChecker, 0);
        placeHomeChecker.setBounds(80, 35, 40, 40);
        placeHomeChecker.setToolTipText("Checker");
        placeHomeChecker.addMouseListener(mouseAdapter);
        //red checker count
        homeCheckerLabel = new JLabel();
        homeCheckerLabel.setText("x " + homeCheckers);
        lMessagePane.add(homeCheckerLabel, 0);
        homeCheckerLabel.setBounds(125, 45, 30, 20);

        //red king
        placeHomeKing = new Square(new Position(0, 3, 2), NIL);
        placeHomeKing.setIcon(setupKingRed);
        lMessagePane.add(placeHomeKing, 0);
        placeHomeKing.setBounds(200, 35, 40, 40);
        placeHomeKing.setToolTipText("King");
        placeHomeKing.addMouseListener(mouseAdapter);
        //red king count
        homeKingLabel = new JLabel();
        homeKingLabel.setText("x " + homeKings);
        lMessagePane.add(homeKingLabel, 0);
        homeKingLabel.setBounds(245, 45, 30, 20);

        //home blocked
        placeHomeBlocked = new Square(new Position(0, 4, 1), NIL);
        placeHomeBlocked.setIcon(squareBlocked);
        lMessagePane.add(placeHomeBlocked, 0);
        placeHomeBlocked.setBounds(20, 85, 40, 40);
        placeHomeBlocked.setToolTipText("Blocked Square");
        placeHomeBlocked.addMouseListener(mouseAdapter);
        //home blocked count
        homeBlockedLabel = new JLabel();
        homeBlockedLabel.setText("x " + homeBlocked);
        lMessagePane.add(homeBlockedLabel, 0);
        homeBlockedLabel.setBounds(65, 95, 30, 20);

        //home safe
        placeHomeSafe = new Square(new Position(0, 4, 2), NIL);
        placeHomeSafe.setIcon(squareSafe);
        lMessagePane.add(placeHomeSafe, 0);
        placeHomeSafe.setBounds(140, 85, 40, 40);
        placeHomeSafe.setToolTipText("Safe Zone");
        placeHomeSafe.addMouseListener(mouseAdapter);
        //home safe count
        homeSafeLabel = new JLabel();
        homeSafeLabel.setText("x " + homeSafe);
        lMessagePane.add(homeSafeLabel, 0);
        homeSafeLabel.setBounds(185, 95, 30, 20);

        //home mine
        placeHomeMine = new Square(new Position(0, 4, 3), NIL);
        placeHomeMine.setIcon(setupMine);
        lMessagePane.add(placeHomeMine, 0);
        placeHomeMine.setBounds(260, 85, 40, 40);
        placeHomeMine.setToolTipText("Smart Mine");
        placeHomeMine.addMouseListener(mouseAdapter);
        //home mine count
        homeMineLabel = new JLabel();
        homeMineLabel.setText("x " + homeMines);
        lMessagePane.add(homeMineLabel, 0);
        homeMineLabel.setBounds(305, 95, 30, 20);

        placeHomeChecker.setEnabled(false);
        placeHomeKing.setEnabled(false);
        placeHomeBlocked.setEnabled(false);
        placeHomeSafe.setEnabled(false);
        placeHomeMine.setEnabled(false);
        homeCheckerLabel.setEnabled(false);
        homeKingLabel.setEnabled(false);
        homeBlockedLabel.setEnabled(false);
        homeSafeLabel.setEnabled(false);
        homeMineLabel.setEnabled(false);
    }

    //remove the board setup images (Squares)
    private void removeBoardSetup()
    {
        //remove the setup icons
        placeVisitorChecker.setVisible(false);
        placeVisitorKing.setVisible(false);
        placeVisitorSafe.setVisible(false);
        placeVisitorBlocked.setVisible(false);
        placeVisitorMine.setVisible(false);
        placeHomeChecker.setVisible(false);
        placeHomeKing.setVisible(false);
        placeHomeSafe.setVisible(false);
        placeHomeBlocked.setVisible(false);
        placeHomeMine.setVisible(false);
        visitorCheckerLabel.setVisible(false);
        visitorKingLabel.setVisible(false);
        visitorBlockedLabel.setVisible(false);
        visitorSafeLabel.setVisible(false);
        visitorMineLabel.setVisible(false);
        homeCheckerLabel.setVisible(false);
        homeKingLabel.setVisible(false);
        homeBlockedLabel.setVisible(false);
        homeSafeLabel.setVisible(false);
        homeMineLabel.setVisible(false);

        optionsMenu.setEnabled(true);
        saveMenuItem.setEnabled(true);
        
        initGamePlay();

        //inform the user that the board setup phase is over
        JOptionPane.showMessageDialog(this, "Board setup is complete!\n" +
              visitorPlayer + " has the first move.", "Board Setup",
              JOptionPane.INFORMATION_MESSAGE);
    }

    private void initGamePlay()
    {
        //enable the labels that will be used during game play
        lMessagePane.setBorder(null);
        lMessagePane.setVisible(true);
        lMessageLabel.setVisible(true);
        rMessagePane.setBorder(null);
        rMessagePane.setVisible(true);

        visitorIconLabel.setVisible(true);
        visitorIconLabel.setBorder(BorderFactory.createEtchedBorder(
              javax.swing.border.EtchedBorder.RAISED,
              new java.awt.Color(255, 255, 0), null));
        visitorNameLabel.setVisible(true);
        visitorNameLabel.setFont(oldEnglish_16b);
        homeIconLabel.setVisible(true);
        homeNameLabel.setVisible(true);
        homeNameLabel.setFont(oldEnglish_16);

        rPlayerLabel.setVisible(false);
        lPlayerLabel.setVisible(false);

        //add a message to the label
        lTextLabel.setText("Play game!");

        //set the dialog font
        javax.swing.UIManager.put("OptionPane.messageFont", oldEnglish_16);
    }

    //handle mouse click on Squares
    MouseAdapter mouseAdapter = new MouseAdapter()
    {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt)
        {
            Square mySquare =  (Square) evt.getSource();
            setupClicked(mySquare);
        }
    };

    private void updateSetupIcons(Position setup, Square destination)
    {
        //decrease the piece count and refresh the label value
        if (setup.getRow() == 1 && setup.getColumn() == 1) //visitor checker
        {
            visitorCheckers--;
            visitorCheckerLabel.setText("x " + visitorCheckers);

            if (visitorCheckers == 0)
                placeVisitorChecker.setEnabled(false);

            if (destination.getSafe())
                destination.setIcon(safeCheckerBlack);
            else
                destination.setIcon(checkerBlack);
        }
        else if (setup.getRow() == 1 && setup.getColumn() == 2) //visitor king
        {
            visitorKings--;
            visitorKingLabel.setText("x " + visitorKings);

            if (visitorKings == 0)
                placeVisitorKing.setEnabled(false);

            if (destination.getSafe())
                destination.setIcon(safeKingBlack);
            else
                destination.setIcon(kingBlack);
        }
        else if (setup.getRow() == 3 && setup.getColumn() == 1) //home checker
        {
            homeCheckers--;
            homeCheckerLabel.setText("x " + homeCheckers);

            if (homeCheckers == 0)
                placeHomeChecker.setEnabled(false);

            if (destination.getSafe())
                destination.setIcon(safeCheckerRed);
            else
                destination.setIcon(checkerRed);
        }
        else if (setup.getRow() == 3 && setup.getColumn() == 2) //home king
        {
            homeKings--;
            homeKingLabel.setText("x " + homeKings);

            if (homeKings == 0)
                placeHomeKing.setEnabled(false);

            if (destination.getSafe())
                destination.setIcon(safeKingRed);
            else
                destination.setIcon(kingRed);
        }
        else if (setup.getRow() == 2 && setup.getColumn() == 1) //visitor blocked
        {
            visitorBlocked--;
            visitorBlockedLabel.setText("x " + visitorBlocked);

            if (visitorBlocked == 0)
                placeVisitorBlocked.setEnabled(false);

            destination.setIcon(squareBlocked);
        }
        else if (setup.getRow() == 4 && setup.getColumn() == 1) //home blocked
        {
            homeBlocked--;
            homeBlockedLabel.setText("x " + homeBlocked);

            if (homeBlocked == 0)
                placeHomeBlocked.setEnabled(false);

            destination.setIcon(squareBlocked);
        }
        else if (setup.getRow() == 2 && setup.getColumn() == 2) //visitor safe
        {
            visitorSafe--;
            visitorSafeLabel.setText("x " + visitorSafe);

            if (visitorSafe == 0)
                placeVisitorSafe.setEnabled(false);

            if (destination.getPiece() == null)
                destination.setIcon(squareSafe);
            else
            {
                if (destination.getPiece() instanceof Checker)
                    destination.setIcon(safeCheckerBlack);
                else if (destination.getPiece() instanceof King)
                    destination.setIcon(safeKingBlack);
            }
        }
        else if (setup.getRow() == 4 && setup.getColumn() == 2) //home safe
        {
            homeSafe--;
            homeSafeLabel.setText("x " + homeSafe);

            if (homeSafe == 0)
                placeHomeSafe.setEnabled(false);

            if (destination.getPiece() == null)
                destination.setIcon(squareSafe);
            else
            {
                if (destination.getPiece() instanceof Checker)
                    destination.setIcon(safeCheckerRed);
                else if (destination.getPiece() instanceof King)
                    destination.setIcon(safeKingRed);
            }
        }
        else if (setup.getRow() == 2 && setup.getColumn() == 3) //visitor mine
        {
            visitorMines--;
            visitorMineLabel.setText("x " + visitorMines);

            if (visitorMines == 0)
                placeVisitorMine.setEnabled(false);
        }
        else if (setup.getRow() == 4 && setup.getColumn() == 3) //home mine
        {
            homeMines--;
            homeMineLabel.setText("x " + homeMines);

            if (homeMines == 0)
                placeHomeMine.setEnabled(false);
        }
    }

    //receive and manage "clicks" from the user during board setup phase
    private void setupClicked(Square sq)
    {
        //check if a first selection has been made
        if (firstSelectSetup == null)
        {
            if (sq.isEnabled() && referee.verifySelection(sq))
            {
                firstSelectSetup = sq;

                setHighlight(sq);

                //highlight the rows where pieces can be placed
                highlightSetup(sq);
            }
        }
        else
        {
            secondSelectSetup = sq;

            //remove the highlight after every selection, regardless of whether
            //or not it is valid
            removeHighlight(firstSelectSetup);
            removeHighlight(secondSelectSetup);
            removeSetupHighlight(firstSelectSetup);

            if (sq.isEnabled() && referee.executePlacement(firstSelectSetup, secondSelectSetup))
            {
                updateSetupIcons(firstSelectSetup.getPosition(), secondSelectSetup);

                toggleSetupTurn();

                //once all pieces have been placed, end the board setup phase
                if(homeCheckers == 0 && homeKings == 0 && homeBlocked == 0 &&
                        homeSafe == 0 && homeMines == 0)
                {
                    boardSetup = false;
                    removeBoardSetup();
                }
            }

            firstSelectSetup = null;
        }
    }

    //highlight the rows where the selected piece can be placed on the board
    private void highlightSetup(Square sq)
    {
        //visitor king or checker selected
        if (sq.getPosition().getRow() == 1)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() <= ALLOWABLE_ROWS)
                    setHighlight(square[i]);
            }
        }
        //visitor blocked square, safe zone or smart mine selected
        if (sq.getPosition().getRow() == 2)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() <= width / 2)
                    setHighlight(square[i]);
            }
        }
        //home king or checker selected
        if (sq.getPosition().getRow() == 3)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() > width - ALLOWABLE_ROWS)
                    setHighlight(square[i]);
            }
        }
        //home blocked square, safe zone or smart mine selected
        if (sq.getPosition().getRow() == 4)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() > width / 2)
                    setHighlight(square[i]);
            }
        }
    }

    //remove the highlighted rows during board setup
    private void removeSetupHighlight(Square sq)
    {
        //visitor king or checker selected
        if (sq.getPosition().getRow() == 1)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() <= 3)
                    removeHighlight(square[i]);
            }
        }
        //visitor blocked square, safe zone or smart mine selected
        if (sq.getPosition().getRow() == 2)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() <= width / 2)
                    removeHighlight(square[i]);
            }
        }
        //home king or checker selected
        if (sq.getPosition().getRow() == 3)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() > width - 3)
                    removeHighlight(square[i]);
            }
        }
        //home blocked square, safe zone or smart mine selected
        if (sq.getPosition().getRow() == 4)
        {
            for (int i = 0; i < square.length; i++)
            {
                if (square[i].getPosition().getRow() > width / 2)
                    removeHighlight(square[i]);
            }
        }
    }

    //toggle the availability of the setup icons
    private void toggleSetupTurn()
    {
        if (visitorCheckerLabel.isEnabled())
        {
            placeVisitorChecker.setEnabled(false);
            placeVisitorKing.setEnabled(false);
            placeVisitorBlocked.setEnabled(false);
            placeVisitorSafe.setEnabled(false);
            placeVisitorMine.setEnabled(false);
            visitorCheckerLabel.setEnabled(false);
            visitorKingLabel.setEnabled(false);
            visitorBlockedLabel.setEnabled(false);
            visitorSafeLabel.setEnabled(false);
            visitorMineLabel.setEnabled(false);
            
            if (homeCheckers > 0)
                placeHomeChecker.setEnabled(true);
            if (homeKings > 0)
                placeHomeKing.setEnabled(true);
            if (homeBlocked > 0)
                placeHomeBlocked.setEnabled(true);
            if (homeSafe > 0)
                placeHomeSafe.setEnabled(true);
            if (homeMines > 0)
                placeHomeMine.setEnabled(true);
            homeCheckerLabel.setEnabled(true);
            homeKingLabel.setEnabled(true);
            homeBlockedLabel.setEnabled(true);
            homeSafeLabel.setEnabled(true);
            homeMineLabel.setEnabled(true);
        }
        else
        {
            if (visitorCheckers > 0)
                placeVisitorChecker.setEnabled(true);
            if (visitorKings > 0)
                placeVisitorKing.setEnabled(true);
            if (visitorBlocked > 0)
                placeVisitorBlocked.setEnabled(true);
            if (visitorSafe > 0)
                placeVisitorSafe.setEnabled(true);
            if (visitorMines > 0)
                placeVisitorMine.setEnabled(true);
            visitorCheckerLabel.setEnabled(true);
            visitorKingLabel.setEnabled(true);
            visitorBlockedLabel.setEnabled(true);
            visitorSafeLabel.setEnabled(true);
            visitorMineLabel.setEnabled(true);
            placeHomeChecker.setEnabled(false);
            placeHomeKing.setEnabled(false);
            placeHomeBlocked.setEnabled(false);
            placeHomeSafe.setEnabled(false);
            placeHomeMine.setEnabled(false);
            homeCheckerLabel.setEnabled(false);
            homeKingLabel.setEnabled(false);
            homeBlockedLabel.setEnabled(false);
            homeSafeLabel.setEnabled(false);
            homeMineLabel.setEnabled(false);
        }
    }

    //load the Old English font with a given size and type
    private Font loadFont(int type, float size)
    {
        Font font = null;
        try
        {
            InputStream input = this.getClass().getResourceAsStream("/OLDENGL.TTF");
            font = Font.createFont(Font.PLAIN, input).deriveFont(type, size);
        }
        catch (IOException ioe)
        {
            System.err.println(ioe);
            System.exit(1);
        }
        catch (FontFormatException ffe)
        {
            System.err.println(ffe);
            System.exit(1);
        }
        return font;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boardMenuBar = new javax.swing.JMenuBar();
        gameMenu = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        closeMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        optionsMenu = new javax.swing.JMenu();
        replayMenuItem = new javax.swing.JMenuItem();
        drawMenuItem = new javax.swing.JMenuItem();
        forfeitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        UserGuideMenu = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        AboutMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("3D Checkers");
        setMinimumSize(new java.awt.Dimension(888, 640));
        setName("boardFrame"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        gameMenu.setText("Game");
        gameMenu.setFont(oldEnglish_16);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setFont(oldEnglish_14);
        saveMenuItem.setText("Save Game");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(saveMenuItem);

        closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        closeMenuItem.setFont(oldEnglish_14);
        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(closeMenuItem);
        gameMenu.add(jSeparator1);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setFont(oldEnglish_14);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        gameMenu.add(exitMenuItem);

        boardMenuBar.add(gameMenu);

        optionsMenu.setText("Options");
        optionsMenu.setFont(oldEnglish_16);

        replayMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        replayMenuItem.setFont(oldEnglish_14);
        replayMenuItem.setText("Instant Replay");
        replayMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replayMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(replayMenuItem);

        drawMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        drawMenuItem.setFont(oldEnglish_14);
        drawMenuItem.setText("Request Draw");
        drawMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                drawMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(drawMenuItem);

        forfeitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        forfeitMenuItem.setFont(oldEnglish_14);
        forfeitMenuItem.setText("Forfeit");
        forfeitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forfeitMenuItemActionPerformed(evt);
            }
        });
        optionsMenu.add(forfeitMenuItem);

        boardMenuBar.add(optionsMenu);

        helpMenu.setText("Help");
        helpMenu.setFont(oldEnglish_16);

        UserGuideMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        UserGuideMenu.setFont(oldEnglish_16);
        UserGuideMenu.setText("User's Guide");
        UserGuideMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserGuideMenuActionPerformed(evt);
            }
        });
        helpMenu.add(UserGuideMenu);
        helpMenu.add(jSeparator2);

        AboutMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        AboutMenu.setFont(oldEnglish_16);
        AboutMenu.setText("About");
        AboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuActionPerformed(evt);
            }
        });
        helpMenu.add(AboutMenu);

        boardMenuBar.add(helpMenu);

        setJMenuBar(boardMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //exit the program
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    //display the instant replay screen
    private void replayMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replayMenuItemActionPerformed
        setVisible(false);
        new ReplayScreen(this, true, width, game.getMoves()).setVisible(true);
        setVisible(true);
    }//GEN-LAST:event_replayMenuItemActionPerformed

    //close the game and go back to the welcome screen
    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        Main.restart();
        dispose();
    }//GEN-LAST:event_closeMenuItemActionPerformed

    //save the game
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy_HHmmss");
        String strDate = sdf.format(date);

        String fileName = visitorPlayer + "_vs_" + homePlayer + "_" + strDate + ".game";

        game.saveGame(square, fileName);

        JOptionPane.showMessageDialog(this, "Save complete!\n" +
              "File name: \n" + fileName, "Save Game",
              JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_saveMenuItemActionPerformed

    //allow a user to request a draw
    private void drawMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_drawMenuItemActionPerformed
        DrawDialog draw = new DrawDialog(this, true);
        User visitorUser = null;
        User homeUser = null;
        for (User user : Main.storage.getUsers()) {
            if (user.getUserName().equalsIgnoreCase(visitorPlayer)) {
                visitorUser = user;
            }
        }
        for (User user : Main.storage.getUsers()) {
            if (user.getUserName().equalsIgnoreCase(homePlayer)) {
                homeUser = user;
            }
        }
        draw.visitorUser = visitorUser;
        draw.homeUser = homeUser;
        draw.setVisitorLabelText(visitorPlayer);
        draw.setHomeLabelText(homePlayer);
        draw.setVisible(true);

    }//GEN-LAST:event_drawMenuItemActionPerformed

    //allow a user to forfeit
    private void forfeitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forfeitMenuItemActionPerformed
        ForfeitDialog forfeit = new ForfeitDialog(this, true);
        User visitorUser = null;
        User homeUser = null;
        for (User user : Main.storage.getUsers()) {
            if (user.getUserName().equalsIgnoreCase(visitorPlayer)) {
                visitorUser = user;
            }
        }
        for (User user : Main.storage.getUsers()) {
            if (user.getUserName().equalsIgnoreCase(homePlayer)) {
                homeUser = user;
            }
        }
        if (game.getVisitorTurn()) {
            forfeit.user = visitorUser;
            forfeit.otherUser = homeUser;
            forfeit.setLabelText(visitorPlayer);
            forfeit.setLabelIcon(true);
        } else {
            forfeit.user = homeUser;
            forfeit.otherUser = visitorUser;
            forfeit.setLabelText(homePlayer);
            forfeit.setLabelIcon(false);
        }
        forfeit.setVisible(true);
        
    }//GEN-LAST:event_forfeitMenuItemActionPerformed

    //view a PDF of the user's guide
    private void UserGuideMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserGuideMenuActionPerformed
        try
        {
            String filePath =new File(".").getCanonicalPath() + File.separator + "User's Guide.pdf";
            
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePath);
        }
        catch (IOException ioe)
        {
            JOptionPane.showMessageDialog(this, "Error loading User's Guide",
                  "User's Guide", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_UserGuideMenuActionPerformed

    private void AboutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutMenuActionPerformed
        new AboutDialog(null, true).setVisible(true);
    }//GEN-LAST:event_AboutMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutMenu;
    private javax.swing.JMenuItem UserGuideMenu;
    private javax.swing.JMenuBar boardMenuBar;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JMenuItem drawMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem forfeitMenuItem;
    private javax.swing.JMenu gameMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenuItem replayMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

    //additional variable declarations
    JLayeredPane lBoardPane;
    JLabel lBackgroundLabel;
    JLayeredPane rBoardPane;
    JLabel rBackgroundLabel;
    JLayeredPane BackgroundPane;
    JLabel BackgroundLabel;
    JLabel lMessageLabel;
    JLabel lTextLabel;
    JLabel rMessageLabel;
    JLabel rPlayerLabel;
    JLabel lPlayerLabel;
    JLayeredPane lMessagePane;
    JLayeredPane rMessagePane;
    JLabel homeIconLabel;
    JLabel homeNameLabel;
    JLabel visitorIconLabel;
    JLabel visitorNameLabel;
    Square placeVisitorChecker;
    Square placeVisitorKing;
    Square placeVisitorBlocked;
    Square placeVisitorSafe;
    Square placeVisitorMine;
    Square placeHomeChecker;
    Square placeHomeKing;
    Square placeHomeBlocked;
    Square placeHomeSafe;
    Square placeHomeMine;
    JLabel visitorCheckerLabel;
    JLabel visitorKingLabel;
    JLabel visitorBlockedLabel;
    JLabel visitorSafeLabel;
    JLabel visitorMineLabel;
    JLabel homeCheckerLabel;
    JLabel homeKingLabel;
    JLabel homeBlockedLabel;
    JLabel homeSafeLabel;
    JLabel homeMineLabel;
}