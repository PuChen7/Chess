/*
 * This class has properties of Queen
 */
 
public class Queen extends Piece{
	
	private Rook tmpR;
	private Bishop tmpB;

	public Queen(){
		role = "Q";
		tmpR = new Rook();
		tmpB = new Bishop();
	}
	
	@Override
	public boolean validMove(int rank2, int file2) {
		//Movement of a Queen is the same as Rook plus Bishop.
		return (tmpR.validMove(rank2, file2) ||
				tmpB.validMove(rank2, file2));
	}
	
	@Override
	public void setBoard(Piece[][] board) {
		this.board = board;
		tmpR.board = board;
		tmpB.board = board;
	}

	@Override
	public void setAxis(int newRank, int newFile){
		this.rank = newRank;
		this.file = newFile;
		tmpR.setAxis(newRank,newFile);
		tmpB.setAxis(newRank,newFile);
	}
}
