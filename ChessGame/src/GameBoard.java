/* This class implements the game board */

import java.util.ArrayList;

public class GameBoard {
    // the board should be 8x8 size
    Piece[][] gameBoard = new Piece[8][8];

    public ArrayList<Piece> blackChess;
    public ArrayList<Piece> whiteChess;
    public King whiteKing;
    public King blackKing;
    private boolean alive;
    public char promote = 'Q';

    public GameBoard(){
        initialize();
    }

    /* set up all pieces */
    public void initialize(){
        blackChess = new ArrayList<Piece>();
        whiteChess = new ArrayList<Piece>();

        int j;
        for(int i = 0; i < 8; i++){
            for(j = 0; j < 8; j++){
                gameBoard[i][j] = null;
            }
        }

        //init Rook
        gameBoard[0][0] = new Rook();
        gameBoard[0][7] = new Rook();
        gameBoard[7][0] = new Rook();
        gameBoard[7][7] = new Rook();
        //init Knight
        gameBoard[0][1] = new Knight();
        gameBoard[0][6] = new Knight();
        gameBoard[7][6] = new Knight();
        gameBoard[7][1] = new Knight();
        //init Bishop
        gameBoard[0][2] = new Bishop();
        gameBoard[0][5] = new Bishop();
        gameBoard[7][2] = new Bishop();
        gameBoard[7][5] = new Bishop();
        //init Queen
        gameBoard[0][3] = new Queen();
        gameBoard[7][3] = new Queen();
        //init King
        gameBoard[0][4] = new King();
        gameBoard[7][4] = new King();

        // Pawn (white)
        for(int i=0; i<8; i++){
            gameBoard[1][i] = new Pawn();
        }

        // Pawn (black)
        for(int i=0; i<8; i++){
            gameBoard[6][i] = new Pawn();
        }

        for (int i = 0; i <= 1; i++) {
            for (j = 0; j < 8; j++) {
                Piece piece = gameBoard[i][j];
                piece.setPlayer("black");
                piece.setBoard(gameBoard);
                piece.setAxis(i,j);
                blackChess.add(piece);
            }
        }
        for (int i = 6; i <= 7; i++) {
            for (j = 0; j < 8; j++) {
                Piece piece = gameBoard[i][j];
                piece.setPlayer("white");
                piece.setBoard(gameBoard);
                piece.setAxis(i,j);
                whiteChess.add(piece);
            }
        }
        blackKing = (King)gameBoard[0][4];
        whiteKing = (King)gameBoard[7][4];
        alive = true;
    }
}
