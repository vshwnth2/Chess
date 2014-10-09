package ChessPieces;
import Board.BoardSquare;


public class King extends ChessPiece {
	public King(int x, int y, int player){
		super(x, y, player);
		setIdentifier("K");
		setValue(0);
		if(player == 0){
			setCode("\u2654");
		}
		else{
			setCode("\u265A");
		}
	}
	
	public String movementRules(){
		return "can only move 1 spot in any direction around it";
	}
	
	/**
	 * checks if this piece is in check
	 * 
	 * @param squares: board
	 * @param rows: range of rows to check
	 * @param columns: range of columns to check
	 * @return checking piece if there is a check
	 * 		   null if no check
	 */
	public ChessPiece selfCheck(BoardSquare[][] squares, int rows, int columns){
		for(int i=0; i < rows; i++){
			for(int j=0; j < columns; j++){
				if(squares[i][j].getPlayer() != -1 && squares[i][j].getPlayer() != getPlayer()){ // if piece on current square is opponent to the king passed in
					if(squares[i][j].getPiece().checkAttackingKing(getRow(), getColumn(), squares) == true){ // checks if that piece on that sq can attack King
						return squares[i][j].getPiece();
					}
				}
			}
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return checkKingMove(endRow, endColumn);
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		int endRow = kingRow;
		int endColumn = kingColumn;
		
		return checkBoundaries(kingRow, kingColumn) && checkKingMove(endRow, endColumn);
	}
	
	/**
	 * used for checkValidMove and checkAttackingKing
	 * checks if movement is within 1 square for any direction
     *
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @return true if king can make the move
	 */
	private boolean checkKingMove(int endRow, int endColumn) {
		int verticalMovement = endRow - getRow();
		int horizontalMovement = endColumn - getColumn();
		if(Math.abs((double)horizontalMovement) > 1 || Math.abs((double)verticalMovement) > 1){
			return false;
		}
		return true;
	}
}
