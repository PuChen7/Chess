/*
 * This class has properties of Rook
 */

public class Rook extends Piece{

    private boolean hasMoved;

    public Rook(){
        role = "R";
        hasMoved = false;
    }

    @Override
    public boolean validMove(int rank2, int file2) {
        if((rank != rank2) && (file != file2)){
            return false;
        }
        //check the range, define start, end and direction
        int start,end,direction;
        if(rank != rank2) {//up-down
            direction = 1;
            if (rank < rank2) {
                start = rank;
                end = rank2;
            } else {
                start = rank2;
                end = rank;
            }
        }else{//left-right
            direction = 0;
            if(file < file2){
                start = file;
                end = file2;
            }else{
                start = file2;
                end = file;
            }
        }
        //Go through start and end to check every square
        for(int i = start+1; i < end; i ++){
            if(direction==1) {
                if (board[i][file] != null) {
                    return false;
                }
            }else{
                if (board[rank][i] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
