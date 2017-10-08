/*
 * This class has properties of Knight
 */

public class Knight extends Piece{
	
	public Knight(){
		role = "N";
	}
	
	@Override
	public boolean validMove(int rank2, int file2) {
		int diff1 = Math.abs(rank2 - rank);
		int diff2 = Math.abs(file2 - file);
		return (diff1 == 2 && diff2 == 1) || (diff1 == 1 && diff2 == 2);
	}
}
