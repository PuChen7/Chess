/*
 * This class has properties of Pawn
 */

public class Pawn extends Piece{
    //first move can advance two squares
    public boolean hasMoved;
    //en passante?
    public boolean ep;

    public Pawn(){
        role = "p";
        this.hasMoved = false;
    }

    @Override
    public boolean validMove(int rank2, int file2) {
        if(Math.abs(rank-rank2)==2){
            if(player.equals("white") && rank==6 && file==file2 && board[rank2+1][file]==null){
                return true;
            }
            if(player.equals("black") && rank==1 && file==file2 && board[rank2-1][file]==null){
                return true;
            }
            return false;
        }
        if(Math.abs(rank-rank2)==1){
            if(file==file2) {
                return true;
            }else if(Math.abs(file-file2)==1) {
                //Taking a piece
                if(board[rank2][file2]!=null) {
                    return true;
                }else {
                    //en passante?
                    if(ep){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     *
     * @param rank2 new rank
     * @param file2 new file
     * @return if valid promote move
     */
    public boolean validPromote(int rank2, int file2){
        if(player.equals("white")){
            if(rank2 == 0){
                return true;
            }
        }else{
            if(rank2 == 7){
                return true;
            }
        }
        return false;
    }

    public boolean validEp(){
        return false;
    }

    public void promote(int rank2, int file2){
        promote(rank2,file2,'Q');
    }

    /**
     * promote pawn to target piece
     * @param rank2 new rank
     * @param file2 new file
     * @param role new piece role
     */
    public void promote(int rank2, int file2, char role){
        Piece promote;
        switch(role){
            case 'Q': promote = new Queen();break;
            case 'N': promote = new Knight();break;
            case 'B' : promote = new Bishop();break;
            case 'R' : promote = new Rook();break;
            default: promote = new Queen();
        }
        promote.setAxis(rank2,file2);
        promote.setBoard(board);
        promote.setPlayer(player);

        board[rank2][file2] = null;
        board[rank2][file2] = promote;
    }
}
