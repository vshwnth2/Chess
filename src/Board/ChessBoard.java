package Board;

import java.awt.Point;
import java.util.Vector;

import ChessGame.Move;
import ChessPieces.ChessPiece;
import ChessPieces.King; 
 
public class ChessBoard {
 
    private BoardSquare[][] squares; // the game board
     
    int numOfplayers = 2;
    
    private King player0King; //king piece for player0
    private King player1King; //king piece for player1
     
    private boolean check = false; //if game is in check state
    private boolean checkmate = false; //if game is in checkmate state
    private boolean stalemate = false;
    	
    private int currentPlayer = 0; //whose turn is it
     
    ChessPiece checkingPiece = null; //whoever is checking the king
     
    private int boardRows; //number of rows on the board
    private int boardColumns; //number of columns on the board
     
    private Vector<Move> allPastMoves; //keeps track of the moves that have been played
    int undoneMoves = 0;
 
    private int totalBoardPoints; //keeps track indirectly of how many pieces on board, helps with endgame stalemates
    
    /**
     * inserts multiple pieces onto the board
     * uses the insertPieceOnBoard
     * 
     * @param pieces: array of pieces to insert onto the board
     */
    public void multipleInsertionsOnBoard(ChessPiece[] pieces){
    	for(int i=0; i < pieces.length; i++){
	        insertPieceOnBoard(pieces[i]);
    	}
    }
    
    /**
     * inserts piece onto the board
     * since the game board has changed, must reset valid moves, and check for end game situations (all the board conditions)
     * 
     * @param piece: piece to insert onto board
     */
    public void insertPieceOnBoard(ChessPiece piece){
        if(getSquares()[piece.getRow()][piece.getColumn()].getPlayer() != -1){
            return;
        }
        getSquares()[piece.getRow()][piece.getColumn()].setPiece(piece);
        resetValidMoves();
        checkEndGame();
        checkForCheck(getCurrentPlayer());
    }

    /**
     * iterates through every spot on the board and updates each pieces validMoves vector
     * also updates the total number of points on the board to help with end game stalemate checks
     */
    public void resetValidMoves(){
        King king = null;
        setTotalBoardPoints(0);
        for(int row=0; row<getBoardRows(); row++){
            for(int col=0; col<getBoardColumns(); col++){
                if(getSquares()[row][col].getPlayer() != -1){ // if current square has a piece
                    if(getSquares()[row][col].getPlayer() == 0) king = getPlayer0King(); //figure out exactly which player for later Checks/Checkmates
                    else king = getPlayer1King();
                    getSquares()[row][col].getPiece().getAllValidMoves(king, getSquares(), getBoardRows(), getBoardColumns()); // updates the valid moves
                    setTotalBoardPoints(getTotalBoardPoints() + getSquares()[row][col].getPiece().getValue()); //increments points on the board
                }
            }
        }
    }

    /**
     * goes through each piece for the player that is player, checks if any of their pieces have any valid moves
     * if any piece has a valid move, not a stalemate
     * else stalemate
     * 
     * @return if the game board is in a regular stalemate state
     */
    private boolean isBoardInStalemate(){
        for(int i=0; i<getBoardRows(); i++){
            for(int j=0; j<getBoardColumns(); j++){
                if(getSquares()[i][j].getPlayer() == getCurrentPlayer()){ // only check the pieces of the player who's playing
                    if(getSquares()[i][j].getPiece().getValidMoves().isEmpty() == false){ // does this piece have any valid moves?
                        return false; //false if anyone has a move
                    }
                }
            }
        }
        return true;
    }
 
    /**
     * undoes the move numOfTimes times
     * resets each pieces valid moves at the end
     * 
     * @param numOfTimes: number of moves to undo
     */
    public void undoMove(int numOfTimes){
        if(getAllPastMoves().isEmpty() || numOfTimes == 0 || undoneMoves == getAllPastMoves().size()){ // no moves to undo or parameter value has run out
            resetValidMoves();
        	return;
        }
        setStalemate(false);
        setCheckmate(false);
        setCurrentPlayer((getCurrentPlayer()+1)%2); //goes back a player
        Move recentMove = getAllPastMoves().elementAt(getAllPastMoves().size() - undoneMoves - 1);
        recentMove.getMovingPiece().changeNumOfMoves(-1);
        movement(recentMove.getMovingPiece(), recentMove.getEndRow(), recentMove.getEndColumn(), 
        		recentMove.getStartRow(), recentMove.getStartColumn(), recentMove.getCapturedPiece()); //undoes move
        undoneMoves = undoneMoves + 1;        
        undoMove(numOfTimes-1);
    }
    
//    public void redoMove(int numOfTimes){
//        if(getAllPastMoves().isEmpty() || numOfTimes == 0 || undoneMoves == 0){ // no moves to undo or parameter value has run out
//            resetValidMoves();
//            checkEndGame();
//        	return;
//        }
//    	setCurrentPlayer((getCurrentPlayer()+1)%2); //goes back a player
//        Move recentMove = getAllPastMoves().elementAt(getAllPastMoves().size() - undoneMoves);
//        recentMove.getMovingPiece().changeNumOfMoves(1);
//        movement(recentMove.getMovingPiece(), recentMove.getStartRow(), recentMove.getStartColumn(), 
//        		recentMove.getEndRow(), recentMove.getEndColumn(), recentMove.getCapturedPiece()); //undoes move
//        undoneMoves = undoneMoves - 1;
//        redoMove(numOfTimes-1);
//    }
    
    /**
     * gets the piece on the board a row,column
     * 
     * @param row: row where we want piece
     * @param column: column where we want piece
     * @return: piece at row,column
     * 			if no piece, return null
     */
    public ChessPiece getPiece(int row, int column){
    	if(getSquares()[row][column].getPlayer() == -1) return null;
    	return getSquares()[row][column].getPiece();
    }
     
    /**
     * Changes board so that piece located at (startRow, startColumn) goes to (endRow, endColumn), depending on conditions
     * Player0 must go first
     * Checks initial conditions then moves on to see if the move is valid for a piece
     * Checks if move puts themselves in check, and if so, undoes the move
     * Once piece is moved, checks if resulting position puts check on opponent (and check mate)
     * Switches whose turn it is
     * 
     * @param startRow: start row for the piece to be moved
     * @param startColumn: start column for the piece to be moved
     * @param endRow: destination row for piece to be moved
     * @param endColumn: destination column for piece to be moved
     * @return: true upon successful move, false o.w.
     */
    public boolean changeBoard(int startRow, int startColumn, int endRow, int endColumn){
        int player = getSquares()[startRow][startColumn].getPlayer(); //current player moving a piece         
        if(initialMovingChecks(startRow, startColumn, endRow, endColumn, player) == false) return false; //checks initial conditions of the parameters
        ChessPiece piece = getSquares()[startRow][startColumn].getPiece(); // piece at the start location
        if(checkValidMove(piece, startRow, startColumn, endRow, endColumn)){ // if the movement from start point to end point is valid
        	ChessPiece endPiece = movement(piece, startRow, startColumn, endRow, endColumn, null); // temporary doing the movement from start point to end point
            boolean causesCheck = checkForCheck(player); //checks if board is in check, both if player puts into self check, and if move gives other person check
            if(causesCheck == false){ //in selfCheck
                movement(piece, endRow, endColumn, startRow, startColumn, endPiece); // undoing the move, because result is a check for side that just moved
                return false;
            }
             
        	piece.changeNumOfMoves(1);
            resetPastMoves();
            //move is successful so going to add it to allPastMoves vector (used for undoing moves)
            Move move = new Move(piece, startRow, startColumn, endRow, endColumn, endPiece);
            getAllPastMoves().add(move);

            setCurrentPlayer((getCurrentPlayer()+1)%2); //switch whose turn it is
            resetValidMoves(); //board has changed so resets each pieces valid moves
            checkEndGame(); // checks for stalemate, checkmate
            return true;
        } 
        else{ //invalid move
            return false;
        }
    }
    
    private void resetPastMoves(){
    	for(int i=getAllPastMoves().size() - undoneMoves; i < getAllPastMoves().size(); ){
    		getAllPastMoves().remove(i);
    	}
    	undoneMoves = 0;
    }
     
    /**
     * checks if player is in self check and if player is giving other person check
     * 
     * @param player: player we are checking the conditions for
     * @return: true if not in self check
     * 			false o.w.
     */
    private boolean checkForCheck(int player){
        setCheck(false);
        King king1, king2;
        //need to set order in which we check for checks based on player
        if(player == 0){
            king1 = getPlayer0King();
            king2 = getPlayer1King();
        }
        else{
            king1 = getPlayer1King();
            king2 = getPlayer0King();
        }
        checkingPiece = king1.selfCheck(getSquares(), getBoardRows(), getBoardColumns());
        //after first self.Check, if there is a checkingPiece, we have moved ourselves into a check
        if(checkingPiece != null){
            return false;
        }
        checkingPiece = king2.selfCheck(getSquares(), getBoardRows(), getBoardColumns());
        //after 2nd time, if checkingPiece isn't null we have put other team in check
        if(checkingPiece != null){
            setCheck(true);
        }
        return true;
    }
     
    /**
     * check for checkmate and stalemate(regular and weird endgame situations)
     */
    protected void checkEndGame(){
        if(getTotalBoardPoints() < 2){ // pieces on board have no way of giving checkmate
//            System.out.println("STALEMATE DUE TO IMPOSSIBLE CHECKMATE");
            setStalemate(true);
            return;
        }
        if(isBoardInStalemate() == true){ //checks if stalemate is true
            if(isCheck() == true){
                setCheckmate(true); //stalemate + check = checkmate
            }
            else{ // no check 
                setStalemate(true);
            }
        }
        else{ // no end game situations are true
        	setStalemate(false);
        	setCheckmate(false);
        }
    }
     
    /**
     * Checks some of the initial conditions for moving piece from point a to b
     * 
     * @param startRow: start row for the piece to be moved
     * @param startColumn: start column for the piece to be moved
     * @param endRow: destination row for piece to be moved
     * @param endColumn: destination column for piece to be moved
     * @param player: player making the move
     * @return true if initial conditions hold true
     */
    private boolean initialMovingChecks(int startRow, int startColumn, int endRow, int endColumn, int player){
        if(startRow == endRow && startColumn == endColumn){ //moving to same square
            return false;
        }       
        if(player == -1){ //trying to move from a place where this is no piece
            return false;
        }
        if(player != getCurrentPlayer()){ //if its not your turn
            return false;
        }
        return true;
    }
    
    /**
     * wrapper around piece's move function, helps with end cases for undoing capturing moves
     * 
     * @param piece: piece that is moving
     * @param startRow: start row of the moving piece
     * @param startColumn: start column of the moving piece
     * @param endRow: row piece is going to
     * @param endColumn: column piece is going to
     * @param piece2: piece that goes on the spot at startRow, startColumn
     * @return
     */
    private ChessPiece movement(ChessPiece piece, int startRow, int startColumn, int endRow, int endColumn, ChessPiece piece2){
        ChessPiece endPiece = piece.move(endRow, endColumn, getSquares()); // piece gets moved, returns piece that was originally at the end location
    	getSquares()[startRow][startColumn].setPiece(piece2); // original start location gets occupied by parameter
    	return endPiece;
    }
 
    /**
     * helper for changeBoard
     * checks some edge cases, then goes into each piece's individual check scenarios
     * 
     * @param piece: piece being moved
     * @param startRow: starting row of the piece's movement
     * @param startColumn: starting column of the piece's movement
     * @param endRow: end row for where the piece is going to move to
     * @param endColumn: end column for where the piece is going to move to
     * @return true if all the initial conditions are met
     */
    private boolean checkValidMove(ChessPiece piece, int startRow, int startColumn, int endRow, int endColumn){
        if(endRow > (getBoardRows()-1) || endRow < 0 || 
                endColumn > (getBoardColumns()-1) || endColumn < 0 || 
                startRow > (getBoardRows()-1) || startRow < 0 || 
                startColumn > (getBoardColumns()-1) || startRow < 0){ //off board
            return false;
        }
        int player = getSquares()[startRow][startColumn].getPlayer();
        if(getSquares()[endRow][endColumn].getPlayer() == player){ // end spot has piece of same team as moving piece
            return false;
        }

        return piece.isInValidMoves(new Point(endRow, endColumn)); // checks if the point is in the piece's valid moves vector
    }
     
    /**
     * prints board to help visualize whats happening
     */
    public void printBoard(){
        for(int i = 0; i < getBoardRows(); i++){
            for(int j = 0; j < getBoardColumns(); j++){
                ChessPiece currentPiece = getSquares()[i][j].getPiece();
                if(getSquares()[i][j].getPlayer() != -1){
                    System.out.print(currentPiece.getIdentifier() + getSquares()[i][j].getPlayer());
                }
                else{
                    System.out.print("---");
                }
                System.out.print(" (" + i + ", " + j + ")\t");
            }
            System.out.print("\n");
        }   
    }

    /**
     * prints all the moves that have happened to this point in the game
     */
    public void printPastMoves(){
        System.out.println("All moves, most recent at top");
        int movesSize = getAllPastMoves().size();
        for(int i=0; i<movesSize; i++){
            Move m = getAllPastMoves().get(movesSize-i-1);
            System.out.print(m.getMovingPiece().getIdentifier() + " moved from (" + m.getStartRow() + ", " + m.getStartColumn() + ") to (" + m.getEndRow() + ", " + m.getEndColumn() + ") ");
            if(m.getCapturedPiece() == null) System.out.println("without capturing a piece");
            else System.out.println("capturing " + m.getCapturedPiece().getIdentifier());
        }
    }

	public boolean isStalemate() {
		return stalemate;
	}

	public void setStalemate(boolean stalemate) {
		this.stalemate = stalemate;
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}

	public BoardSquare[][] getSquares() {
		return squares;
	}

	public void setSquares(BoardSquare[][] squares) {
		this.squares = squares;
	}

	public Vector<Move> getAllPastMoves() {
		return allPastMoves;
	}

	public void setAllPastMoves(Vector<Move> allPastMoves) {
		this.allPastMoves = allPastMoves;
	}

	public int getTotalBoardPoints() {
		return totalBoardPoints;
	}

	public void setTotalBoardPoints(int totalBoardPoints) {
		this.totalBoardPoints = totalBoardPoints;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public King getPlayer0King() {
		return player0King;
	}

	public void setPlayer0King(King player0King) {
		this.player0King = player0King;
	}

	public King getPlayer1King() {
		return player1King;
	}

	public void setPlayer1King(King player1King) {
		this.player1King = player1King;
	}

	public int getBoardRows() {
		return boardRows;
	}

	public void setBoardRows(int boardRows) {
		this.boardRows = boardRows;
	}

	public int getBoardColumns() {
		return boardColumns;
	}

	public void setBoardColumns(int boardColumns) {
		this.boardColumns = boardColumns;
	}
     
}