package ChessPieces;
import Board.BoardSquare;


public class Dictator extends ChessPiece {
	public Dictator(int x, int y, int player){
		super(x, y, player);
		setIdentifier("QN");
		setValue(12);
		if(player == 0) setCode("\u2606");
		else setCode("\u2605");
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkStraights(endRow, endColumn, squares) || checkL(endRow, endColumn) || checkDiagnols(endRow, endColumn, squares); // can go like queen(straight&diags) and like knight(L)
	}
	
	public String movementRules(){
		return "can only move in an L shape, diagnol, or straight line";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}
}
