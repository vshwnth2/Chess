package ChessPieces;
import java.awt.Point;
import java.util.Vector;

import Board.BoardSquare;

public class ChessPiece {
	private String identifier; //tells us what piece we are dealing with to print out
	private int row; //row the piece is on
	private int column; //column the piece is on
	public int player; //who owns the piece
	private Vector<Point> validMoves; //all the valid moves for this piece
	private int value; //how much it is worth (custom) -- helpful for endgame
	private String code;
	private int numOfMoves;
	
	/**
	 * Constructor
	 * 
	 * @param x: row of the piece
	 * @param y: column of the piece
	 * @param player: side of the piece
	 */
	public ChessPiece(int x, int y, int player){
		setNumOfMoves(0);
		setRow(x);
		setColumn(y);
		setIdentifier("none");
		this.setPlayer(player);
		setValidMoves(new Vector<Point>());
	}
	
	public String movementRules(){
		return "";
	}
	
	/**
	 * moves the piece on the board
	 *  
	 * @param endRow: row to which piece is moving to
	 * @param endColumn: column to which 
	 * @param squares: game board
	 * @return: piece at the end location (useful for capturing undoing)
	 */
	public ChessPiece move(int endRow, int endColumn, BoardSquare[][] squares){
        ChessPiece endPiece = squares[endRow][endColumn].getPiece(); //either null or the captured piece
		this.setRow(endRow);
		this.setColumn(endColumn);
		//moving the piece to end spot
        squares[endRow][endColumn].setPiece(this);
        squares[endRow][endColumn].setPlayer(getPlayer());
        return endPiece;
	}
	
	/**
	 * gets all the valid move for this piece
	 * 
	 * @param king: king on this piece's team
	 * @param squares: game board
	 * @param boardRows: number of rows on board
	 * @param boardColumns: number of columns on board
	 */
	public void getAllValidMoves(King king, BoardSquare[][] squares, int boardRows, int boardColumns){
		getValidMoves().removeAllElements(); // clears current entries
		for(int potentialRow=0; potentialRow<boardRows; potentialRow++){
			for(int potentialColumn=0; potentialColumn<boardColumns; potentialColumn++){
				if(squares[potentialRow][potentialColumn].getPlayer() == getPlayer() || (potentialRow==getRow() && potentialColumn==getColumn())){ // if no movement or if end location is same color as this piece
					continue;
				}
				boolean valid = checkValidMove(potentialRow, potentialColumn, squares); // true if it can move to i,j
				int startR = 0;
				int startC = 0;
				ChessPiece tempPiece = null;
				ChessPiece endPiece = null;
				if(valid == true){
					startR = getRow();
					startC = getColumn();
					endPiece = move(potentialRow, potentialColumn, squares); // makes move to check for self check
					squares[startR][startC].setPiece(null);
					tempPiece = king.selfCheck(squares, boardRows, boardColumns); //checks for selfcheck
					move(startR, startC, squares); // moves piece back to original square
					squares[potentialRow][potentialColumn].setPiece(endPiece);
				}
				
				if(valid == true && tempPiece == null){ //valid move and didnt put self in check
					getValidMoves().addElement(new Point(potentialRow,potentialColumn)); //adds to valid moves vector
				}
			}
		}
	}

	/**
	 * checks if piece is moving in some straight line, and if so, checks if anything is in the way
	 * 
	 * @param endRow: row where piece wants to go
	 * @param endColumn: column where piece wants to go
	 * @param squares: game board
	 * @return: true if it can move in a straight line and there is no pieces in the way for the particular move
	 */
	protected boolean checkStraights(int endRow, int endColumn, BoardSquare[][] squares){
		int verticalMovement = endRow - getRow();
		int horizontalMovement = endColumn - getColumn();
		if(verticalMovement == 0){ //no vertical movement so  moving horizontally
			return checkHorizObstruction(getRow(), getColumn(), endColumn, squares);
		}
		else if(horizontalMovement == 0){ //no horiz movement so moving vertically
			return checkVertObstruction(getRow(), endRow, getColumn(), squares);
		}
		else{ //its not moving in straight line
			return false;
		}
	}
	
	/**	 
	 * checks if piece is moving in some diagnol line, and if so, checks if anything is in the way
	 *
	 * @param endRow: row where piece wants to go
	 * @param endColumn: column where piece wants to go
	 * @param squares: game board
	 * @return: true if it can move in a diagnol line and there is no pieces in the way for the particular move
	 */
	protected boolean checkDiagnols(int endRow, int endColumn, BoardSquare[][] squares){
		double verticalMovement = endRow - getRow();
		double horizontalMovement = endColumn - getColumn();
		if(horizontalMovement == (double)0){ // b/c can't divide by 0
			return false;
		}
		if(verticalMovement/horizontalMovement == (double)-1){ //moving up right or down left
			return posDiagObstruction(getRow(), getColumn(), endRow, endColumn, squares);
		}
		else if (verticalMovement/horizontalMovement == (double)1){ //moving up left or down right
			return negDiagObstruction(getRow(), getColumn(), endRow, endColumn, squares);
		}
		else{ //not moving in diagnol
			return false;
		}
	}
	
	/**
	 * checks if the piece is moving in an L shape
     *
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @return true if path is in L shape
	 */
	protected boolean checkL(int endRow, int endColumn) {
		int verticalMovement = endRow - getRow();
		int horizontalMovement = endColumn - getColumn();
		if(Math.abs((double)horizontalMovement) == 2 && Math.abs((double)verticalMovement) == 1){ //2 horiz, 1 vert
			return true;
		}
		else if(Math.abs((double)horizontalMovement) == 1 && Math.abs((double)verticalMovement) == 2){ //2 vert, 1 horiz
			return true;
		}
		//not L move
		return false;
	}
	
	/**
	 * checks if there are any obstructions along a horizontal path from start point to end point on squares grid
	 * 
	 * @param row: row of movement
	 * @param startColumn: column where piece is starting from
	 * @param endColumn: column where piece is moving to
	 * @param squares: board of the current board situation
	 * @return true if no obstruction on horizontal path
	 */
	protected boolean checkHorizObstruction(int row, int startColumn, int endColumn, BoardSquare[][] squares){
		int minColumn = Math.min(startColumn, endColumn); //we want to start at the smaller of the 2 points (in terms of column) and walk forward
		int maxColumn = Math.max(startColumn, endColumn); 
		
		for(int i = minColumn; i < maxColumn - 1; i++, minColumn++){ //starting at lower column and walking to maxColumn
			if(squares[row][minColumn+1].getPlayer() != -1){ // if piece exists then there is an obstruction (2 means there is no piece or owner of square)
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks if there are any obstructions along a vertical path from start point to end point on squares grid
	 * 
	 * @param startrow: row where piece is starting from
	 * @param endRow: row where piece is moving to
	 * @param Column: column of movement
	 * @param squares: board of the current board situation
	 * @return true if no obstruction on vertical path
	 */
	protected boolean checkVertObstruction(int startRow, int endRow, int column, BoardSquare[][] squares){
		int minRow = Math.min(startRow, endRow); // want to start at lower point and walk upward
		int maxRow = Math.max(startRow, endRow);

		for(int i = minRow; i < maxRow - 1; i++, minRow++){ //starting at low point and walking up to maxRow
			if(squares[minRow+1][column].getPlayer() != -1){ // if piece exits, obstruction
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checks if there are any obstructions along a positive sloping diagnol path from start point to end point on squares grid
	 * 
	 * @param startRow: start row for the piece to be moved
	 * @param startColumn: start column for the piece to be moved
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @param squares: board of the current board situation
	 * @return true if no obstruction on the particular positive diagnol path
	 */
	protected boolean posDiagObstruction(int startRow, int startColumn, int endRow, int endColumn, BoardSquare[][] squares){
		int maxRow = Math.max(startRow, endRow);
		int minColumn = Math.min(startColumn, endColumn);
		int limit = Math.abs(endRow - startRow) - 1;
		
		for(int i = 0; i < limit; i++, maxRow--, minColumn++){ //starting at top right of diagnol and walking down and to the left
			if(checkBoundaries(maxRow-1, minColumn+1)){
				if(squares[maxRow-1][minColumn+1].getPlayer() != -1){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * checks if there are any obstructions along a negative sloping diagnal path from start point to end point on squares grid
	 * 
	 * @param startRow: start row for the piece to be moved
	 * @param startColumn: start column for the piece to be moved
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @param squares: board of the current board situation
	 * @return true if no obstruction on the particular negative diagnol path
	 */
	protected boolean negDiagObstruction(int startRow, int startColumn, int endRow, int endColumn, BoardSquare[][] squares){
		int minRow = Math.min(startRow, endRow);
		int minColumn = Math.min(startColumn, endColumn);
		int limit = Math.abs(endRow - startRow) - 1;
		
		for(int i = 0; i < limit; i++, minRow++, minColumn++){ // starts at top left and walks down and to the right
			if(checkBoundaries(minRow+1, minColumn+1)){
				if(squares[minRow+1][minColumn+1].getPlayer() != -1){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * checks if it is valid for the piece to go from its current location to endRow, endColumn
	 * implemented in subclasses
     *
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @param squares: board of the current board situation
	 * @return true if piece can move to endRow, endColumn
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		return false;
	}
	
	/**
	 * checks if the point is in piece's valid moves vector
	 * 
	 * @param endPoint: point we are checking
	 * @return true if in the validMoves vector
	 * 		   false o.w.
	 */
	public boolean isInValidMoves(Point endPoint){
		if(getValidMoves().contains(endPoint)) return true;
		return false;
	}
	
	/**
     * checks if the piece is attacking the king at the coordinate provided on squares
	 * implemented in subclasses
     *
	 * @param kingRow: king that is being attacked
	 * @param kingColumn: destination column for piece to be moved
	 * @param squares: board of the current board situation
	 * @return true if this piece is attacking the king at kingRow, kingColumn
	 */	
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return false;
	}

	/**
     * check is the row and column provided is in range 
	 * needed for checkmate process
     *
	 * @param row: any row
	 * @param column: any column
	 * @return true if row,column is on board
	 */	
	public boolean checkBoundaries(int row, int column){
		if(row > 7 || row < 0 || column > 7 || column < 0) return false;
		return true;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Vector<Point> getValidMoves() {
		return validMoves;
	}

	public void setValidMoves(Vector<Point> validMoves) {
		this.validMoves = validMoves;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getNumOfMoves() {
		return numOfMoves;
	}

	public void setNumOfMoves(int numOfMoves) {
		this.numOfMoves = numOfMoves;
	}
	
	public void changeNumOfMoves(int value) {
		this.numOfMoves = this.numOfMoves + value;
	}
}
