package ChessPieces;
import Board.BoardSquare;


public class Rook extends ChessPiece {
	public Rook(int x, int y, int player){
		super(x, y, player);
		setIdentifier("R");
		setValue(5);
		if(player == 0){
			setCode("\u2656");
		}
		else{
			setCode("\u265C");
		}
	}
	
	public String movementRules(){
		return "can only move on a straight";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}

	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkStraights(endRow, endColumn, squares); //can only go straight line
	}
}