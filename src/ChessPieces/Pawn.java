package ChessPieces;
import java.awt.Point;

import Board.BoardSquare;

public class Pawn extends ChessPiece {
	private boolean firstMove = true; //used for checking if pawn can move 2 spots legally
	public Pawn(int x, int y, int player){
		super(x, y, player);
		setIdentifier("P");
		setValue(3);
		if(player == 0){
			setCode("\u2659");
		}
		else{
			setCode("\u265F");
		}
	}
	
	public String movementRules(){
		return "1st turn: 1-2 spots forward, else 1 spot forward. capture only diagnol";
	}
	
	public boolean isInValidMoves(Point endPoint){
		if(getValidMoves().contains(endPoint)){
			firstMove = false;
			return true;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkValidMove(int, int, BoardSquare[][])
	 */
	public boolean checkValidMove(int endRow, int endColumn, BoardSquare[][] squares){
		int verticalMovement = endRow - getRow();
		int horizontalMovement = endColumn - getColumn();
		int player = squares[getRow()][getColumn()].getPlayer();
		
		if(wrongDirection(verticalMovement, player)){ //checks for if the movement of the piece is in the backwords direction
			return false;
		}
		
		if(Math.abs(horizontalMovement) > 1 || Math.abs(verticalMovement) > 2){ //if piece moves more than 1 horizontally or 2 vertically, must be invalid
			return false;
		}
		
		if(Math.abs(horizontalMovement) == 1){ //capturing logic (must be capturing if it is moving 1 unit horizontally
			return checkCapture(squares, endRow, endColumn, verticalMovement, player);
		}
		
		if(checkVerticalDestination(squares, endRow, endColumn) == true){ // checks if destination spot is empty (can't capture moving forward)
			if(/*isFirstMove() == true*/getNumOfMoves() == 0 && Math.abs(verticalMovement) == 2){ //can move 2 spots on first turn, can't otherwise
				return checkMoveTwoSpots(squares, endRow);
				
			}
			else if (Math.abs(verticalMovement) == 1) { //normal case
//				firstMove = false; //setting this to false whenever function returns true, since it means at least one move is done
				return true;
			}
			else{//leftover case is when vertMovement is 2 and its not first move
				return false;
			}
		}
		else{ //another piece in destination spot
			return false;
		}
	}

	/**
	 * HELPER: checks to make sure there is no obstruction ahead if piece wants to move 2 spots ahead
	 * 
	 * @param squares: board of the current board situation
	 * @param endRow: destination row for piece to be moved
	 * @return true if no obstruction for moving 2 pieces
	 */
	private boolean checkMoveTwoSpots(BoardSquare[][] squares, int endRow){
		boolean check = checkVertObstruction(getRow(), endRow, getColumn(), squares); // check if there is piece in the way when moving 2 spots
		if(check == true){
//			firstMove = false; // since check is true, we will be able to make move, so firstMove is made
		}
		return check;
	}

	/**
	 * HELPER
	 * checks if destination location has a piece in it or not
	 * no piece can be in a spot vertically in front of pawn, it can only capture diagnolly
	 * 
	 * @param squares: board of the current board situation
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @return true if no piece on destination spot
	 */
	private boolean checkVerticalDestination(BoardSquare[][] squares, int endRow, int endColumn){
		if(squares[endRow][endColumn].getPlayer() != -1){ // is there any piece at the destination spot?
			return false;
		}
		return true;
	}

	/**
	 * HELPER
	 * making sure the capture is done correctly and in right direction with right pieces
	 * 
	 * @param squares: board of the current board situation
	 * @param endRow: destination row for piece to be moved
	 * @param endColumn: destination column for piece to be moved
	 * @param verticalMovement: how much piece moves vertically
	 * @param player: player that is moving
	 * @return true if capture is done properly
	 */
	private boolean checkCapture(BoardSquare[][] squares, int endRow, int endColumn, int verticalMovement, int player){
		if(player == 1 && verticalMovement == 1 && squares[endRow][endColumn].getPlayer() == 0){ //player1 moves downward and takes player0
			return true;
		}
		else if(player == 0 && verticalMovement == -1 && squares[endRow][endColumn].getPlayer() == 1){ //player0 moves upwards and takes player1
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * HELPER
	 * checks if you are going in wrong direction based on which player
	 * 
	 * @param verticalMovement: how much piece moves vertically
	 * @param player: player that is moving
	 * @return true if going wrong direction
	 */
	private boolean wrongDirection(int verticalMovement, int player){
		if(player == 0 && verticalMovement > 0){ //player0 pawns must move upwards (negative vertMove), so if not, going in wrong direction
			return true;
		}
		else if(player == 1 && verticalMovement < 0){ //player1 pawns must move downwards (positive vertMove), so if not, going in wrong direction
			return true;
		}
		else{
			return false; //going in right direction
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see ChessPiece#checkAttackingKing(int, int, BoardSquare[][])
	 */
	public boolean checkAttackingKing(int kingRow, int kingColumn, BoardSquare[][] squares){
		return checkBoundaries(kingRow, kingColumn) && checkValidMove(kingRow, kingColumn, squares);
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
