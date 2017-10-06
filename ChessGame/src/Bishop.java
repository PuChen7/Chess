/*
 * This class has properties of Bishop
 */

public class Bishop extends Piece{

    public Bishop(){
        role = "B";
    }

    @Override
    public boolean validMove(int rank2, int file2) {
        if(Math.abs(rank2 - rank) != Math.abs(file2 - file)){
            return false;
        }

        int start = rank,end = rank2,direction =1,newFile = file;
        if(rank < rank2){
            if(file < file2){//right down
            }else{//left down
                direction = -1;
            }
        }else{
            if(file < file2){//right up
                start = rank2;
                end = rank;
                newFile = file2;
                direction = -1;
            }else{//left up
                start = rank2;
                end = rank;
                newFile = file2;
            }
        }
        for(int i = start+1,j = newFile + direction; i <end; i ++){
            if(board[i][j] != null){
                return false;
            }
            j += direction;
        }
        return true;
    }

}
