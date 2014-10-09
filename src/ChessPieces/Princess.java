package ChessPieces;
import Board.BoardSquare;


public class Princess extends ChessPiece {
	public Princess(int x, int y, int player){
		super(x, y, player);
		setIdentifier("BN");
		setValue(6);
		if(player == 0){
			setCode("\u25C7");
		}
		else{
			setCode("\u25C6");
		}
	}
	
	public String movementRules(){
		return "can only move in an L shape or diagnol";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkDiagnols(endRow, endColumn, squares) || checkL(endRow, endColumn); //can go like knight or like bishop
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}
}
