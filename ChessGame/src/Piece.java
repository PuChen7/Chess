/* Abstract class - Piece */

public abstract class Piece {
    //black or white
    protected String player;
    //king queen bishop knight rook pawn
    protected String role;
    //the board
    protected Piece[][] board;
    public int rank;
    public int file;

    /**
     * detect whether is a valid move from (rank,file) to (rank2,file2)
     * different role has different movement. implements by each role itself.
     * @return true if the move is valid
     */
    public abstract boolean validMove(int rank2, int file2);


    /**
     * get the player (white or black)
     * @return  white or black
     */
    public String getPlayer(){
        return this.player;
    }

    /**
     * @return role type
     */
    public char getRole() {
        return role.charAt(0);
    }

    @Override
    public String toString() {
        return player.charAt(0) + role;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public void setAxis(int newRank, int newFile){
        this.rank = newRank;
        this.file = newFile;
    }
}
