/*
 * This class has properties of King
 */

public class King extends Piece{
	public boolean hasMoved;
	public int castled;
	
	public King(){
		role = "K";
		this.hasMoved = false;
		this.castled = -1;
	}

	@Override
	public boolean validMove(int rank2, int file2) {
		return Math.abs(rank2 - rank) <= 1 && Math.abs(file2 - file) <= 1;
	}
	
	public boolean validCastling(int rank2, int file2){
		//Do castling logic
		if(hasMoved){
			return false;
		}
		int i;
		Rook rook;
		String [] tmpP = {"black","black","white","white"};
		int[] tmpX = {0,0,7,7};
		int[] tmpY = {2,6,2,6};
		int[] tmpZ = {0,7,0,7};
		int[] starts = {1,5,1,5};
		int[] ends = {3,6,3,6};
		//four possible castling move
		for (i = 0; i < 4; i++) {
			if(player.equals(tmpP[i]) && rank2==tmpX[i] && file2==tmpY[i]){
				Piece tmp = board[tmpX[i]][tmpZ[i]];
				if(tmp!=null && tmp.getRole()=='R' && tmp.getPlayer().equals(tmpP[i])){
					rook = (Rook)tmp;
					if(!rook.isHasMoved()){
						break;
					}
				}
			}
		}
		if(i==4){
			return false;
		}
		castled = i;
		int start = starts[i];
		int end = ends[i];
		int tmpRank = (i/2==0)?0:7;
		//whether squares occupied between king and rook
		for (i = start; i <= end; i++) {
			if(board[tmpRank][i]!=null){
				castled = -1;
				return false;
			}
		}
		return true;
	}
}
