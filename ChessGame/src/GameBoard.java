/* This class implements the game board */

package chess;

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
	
	/**
     * Check if the king of the given player is under check.
     * @param player current player
     * @return whether the king under check
     */
    public boolean isUnderCheck(String player){
        int rank,file;
        ArrayList<Piece> chess;
        if(player.equals("white")){
            rank = whiteKing.rank;
            file = whiteKing.file;
            chess = blackChess;
        }else{
            rank = blackKing.rank;
            file = blackKing.file;
            chess = whiteChess;
        }
        for(Piece piece: chess){
            if(gameBoard[piece.rank][piece.file].validMove(rank, file)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if any moves are possible. If not, then it is either a checkmate or stalemate,
     * depending on whether player is currently under check or not.
     * @return if any moves are possible
     */
    public boolean isNoPieceCanMove(String player){
    	int kingrank,kingfile;
        ArrayList<Piece> chess1;
        ArrayList<Piece> chess2;
        if(player.equals("white")){
            kingrank = whiteKing.rank;
            kingfile = whiteKing.file;
            chess1 = whiteChess;
            chess2 = blackChess;
        }else{
            kingrank = blackKing.rank;
            kingfile = blackKing.file;
            chess1 = blackChess;
            chess2 = whiteChess;
        }
        for(Piece piece: chess1){
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if(gameBoard[i][j]==null || !gameBoard[i][j].getPlayer().equals(player)){
                        if(piece.validMove(i, j)){
                            int tmp;
                            Piece curPiece = piece;
                            Piece targetPiece = gameBoard[i][j];
                            int curRank = piece.rank;
                            int curFile = piece.file;
                            if(gameBoard[i][j]==null){
                                tmp = 1;
                                gameBoard[i][j] = piece;
                                gameBoard[i][j].setAxis(i,j);
                            }else{
                                tmp = 2;
                                chess2.remove(gameBoard[i][j]);
                            }
                            gameBoard[curRank][curFile] = null;
                            boolean flag = true;
                            for(Piece oppopiece: chess2){
                                if(oppopiece.validMove(kingrank, kingfile)){
                                    flag = false;
                                    break;
                                }
                            }
                            if(tmp ==1){
                            	gameBoard[i][j] = null;
                            }else{
                                chess2.add(targetPiece);
                            }
                            gameBoard[curRank][curFile] = curPiece;
                            gameBoard[curRank][curFile].setAxis(curRank,curFile);
                            //exists at least one piece one move can let player unchecked
                            if(flag){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    /**
     * check if valid for player to move from(rank1,file1) to(rank2,file2)
     * @param player current player
     * @param rank1 rank1
     * @param file1 file1
     * @param rank2 rank2
     * @param file2 file2
     * @return true if its valid
     */
    public boolean validInput(String player, int rank1, int file1, int rank2, int file2){
        if(gameBoard[rank1][file1]==null)
            return false;
        if(!gameBoard[rank1][file1].getPlayer().equals(player))
            return false;
        if(gameBoard[rank2][file2] == null || !gameBoard[rank2][file2].getPlayer().equals(player)){
            Piece piece = gameBoard[rank1][file1];
            if(piece.validMove(rank2,file2)){
                return true;
            }else if(piece.getRole()=='K' && ((King)piece).validCastling(rank2,file2)){
                return true;
            }
        }
        return false;
    }
	
	/*
	 * This method makes the move.
	 */
	public void makeMove(int rank1, int file1, int rank2, int file2){
		char role = gameBoard[rank1][file1].getRole();
        if(role=='R' && !((Rook)gameBoard[rank1][file1]).isHasMoved()){
            ((Rook)gameBoard[rank1][file1]).setHasMoved(true);
        }else if(role=='K'){//castling
            King king = (King)gameBoard[rank1][file1];
            if(!king.hasMoved){
                king.hasMoved = true;
                if(king.castled!=-1){
                    int indexR = king.castled;
                    int[] x1 = {0,0,7,7};
                    int[] y1 = {0,7,0,7};
                    int[] x2 = {0,0,7,7};
                    int[] y2 = {3,5,3,5};
                    makeMove(x1[indexR],y1[indexR],x2[indexR],y2[indexR]);
                }
            }
        }

        if(gameBoard[rank2][file2]!=null){
            King king;
            if(gameBoard[rank2][file2].getPlayer().equals("white")){
                king = whiteKing;
                whiteChess.remove(gameBoard[rank2][file2]);
            }else{
                king = blackKing;
                blackChess.remove(gameBoard[rank2][file2]);
            }
            if(king==gameBoard[rank2][file2]){
                alive = false;
            }
        }
        gameBoard[rank2][file2] = gameBoard[rank1][file1];
        gameBoard[rank2][file2].setAxis(rank2,file2);
        gameBoard[rank1][file1] = null;

        //promote
        if(gameBoard[rank2][file2].getRole()=='p' &&
           ((Pawn)gameBoard[rank2][file2]).validPromote(rank2,file2)){

            ArrayList<Piece> pieces;
            if(gameBoard[rank2][file2].getPlayer().equals("white")){
                pieces = whiteChess;
            }else{
                pieces = blackChess;
            }
            pieces.remove(gameBoard[rank2][file2]);
            ((Pawn)gameBoard[rank2][file2]).promote(rank2,file2,promote);
            pieces.add(gameBoard[rank2][file2]);
        }
	}
	
	/**
     * Check if there is a stalemate
     * @return true if stalemated
     */
    public boolean staleMate(String player){
    	if(!isUnderCheck(player)){
            if(isNoPieceCanMove(player)){
                return true;
            }
        }
        return false;
    }
    
	/**
     * Check if there is a checkMate
     * @return true if checkMate
     */
	 public boolean checkMate(String player){
		 if(isUnderCheck(player)){
	            if(isNoPieceCanMove(player)){
	                return true;
	            }
	        }
	        return false;
	 }
	
	/**
     * print board to screen
     * @return board
     */
    public String toString(){
        int count = 8;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard[i][j]!=null){
                    sb.append(gameBoard[i][j]);
                }else{
                    sb.append("  ");
                }
                sb.append(" ");
            }
            sb.append(count--);
            sb.append("\n");
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard[i][j]==null){
                    if((i%2==0 && j%2==1)||(i%2==1 && j%2==0)){
                        sb.append("##");
                    }else{
                        sb.append("  ");
                    }
                }else{
                    sb.append(gameBoard[i][j]);
                }
                sb.append(" ");
            }
            sb.append(count--);
            sb.append("\n");
        }

        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(gameBoard[i][j]!=null){
                    sb.append(gameBoard[i][j]);
                }else{
                    sb.append("  ");
                }
                sb.append(" ");
            }
            sb.append(count--);
            sb.append("\n");
        }
        sb.append(" a  b  c  d  e  f  g  h  \n");
        return sb.toString();
    }
    
    public static void main(String[] args) {
        GameBoard b = new GameBoard();
        System.out.println(b);
    }
    
    /**
    *
    * @return if the king is alive
    */
   public boolean kingAlive() {
       return alive;
   }
}

