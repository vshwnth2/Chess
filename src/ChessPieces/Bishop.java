package ChessPieces;
import Board.BoardSquare;

public class Bishop extends ChessPiece {
	public Bishop(int x, int y, int player){
		super(x, y, player);
		setIdentifier("B");
		setValue(1); //helpful for endgame stalemate
		if(player == 0){
			setCode("\u2657");
		}
		else{
			setCode("\u265D");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkDiagnols(endRow, endColumn, squares); //diagnols are only valid moves for bishops
	}
	
	public String movementRules(){
		return "can only move in a diagnol";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}
}