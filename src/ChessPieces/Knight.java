package ChessPieces;
import Board.BoardSquare;


public class Knight extends ChessPiece {
	public Knight(int x, int y, int player){
		super(x, y, player);
		setIdentifier("N");
		setValue(1);
		if(player == 0){
			setCode("\u2658");
		}
		else{
			setCode("\u265E");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkL(endRow, endColumn);
	}
	
	public String movementRules(){
		return "can only move in an L shape";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		int endRow = kingRow;
		int endColumn = kingColumn;
		
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(endRow, endColumn, squares);
	}
}
