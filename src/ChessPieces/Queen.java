package ChessPieces;
import Board.BoardSquare;


public class Queen extends ChessPiece {
	public Queen(int x, int y, int player){
		super(x, y, player);
		setIdentifier("Q");
		setValue(9);
		if(player == 0){
			setCode("\u2655");
		}
		else{
			setCode("\u265B");
		}
	}

	public String movementRules(){
		return "can only move on a straight or diagnol";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){		
		return checkStraights(endRow, endColumn, squares) || checkDiagnols(endRow, endColumn, squares); //can go in straight line or diagnol
	}

	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}
}