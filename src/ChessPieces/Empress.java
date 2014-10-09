package ChessPieces;
import Board.BoardSquare;


public class Empress extends ChessPiece {
	public Empress(int x, int y, int player){
		super(x, y, player);
		setIdentifier("RN");
		setValue(6);
		if(player == 0) setCode("\u25B3");
		else{
			setCode("\u25B2");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkStraights(endRow, endColumn, squares) || checkL(endRow, endColumn); //can go straight or like knight
	}
	
	public String movementRules(){
		return "can only move in an L shape or straight line";
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}
}
