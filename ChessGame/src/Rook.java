/* This class has properties of Rook */

public class Rook extends Piece{
    private boolean hasMoved;

    public Rook(){
        role = "R";
        hasMoved = false;
    }

    @Override
    public boolean validMove(int rank2, int file2) {
        return false;
    }

    public boolean isHasMoved(){
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved){
        this.hasMoved = hasMoved;
    }
}
