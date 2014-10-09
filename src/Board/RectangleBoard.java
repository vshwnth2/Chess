package Board;
import java.util.Vector;

import ChessGame.Move;
import ChessPieces.*;


public class RectangleBoard extends ChessBoard {
	/**
	 * default constructor, creates an 8x8 board
	 */
	public RectangleBoard(){
		setBoardRows(8);
		setBoardColumns(8);
		createBoard(getBoardRows(), getBoardColumns());
		initializePieces();
		setAllPastMoves(new Vector<Move>());
	}

	/**
	 * another constructor where you can specifiy width and height of board, and whether to initialize the board with regular positions
	 * 
	 * @param width: width of board
	 * @param height: height of board
	 * @param intialize: whether to initialize pieces on board
	 */
	public RectangleBoard(int width, int height, boolean intialize){
		this.setBoardRows(width);
		this.setBoardColumns(height);
		createBoard(width, height);
		setAllPastMoves(new Vector<Move>());
		if(intialize)initializePieces();
		else setUpBoardWithKings(); //need to put kings on board no matter what
	}
	
	public RectangleBoard(int width, int height, boolean intialize, boolean princess, boolean empress, boolean dictator){
		this.setBoardRows(width);
		this.setBoardColumns(height);
		createBoard(width, height);
		setAllPastMoves(new Vector<Move>());
		if(intialize){
			if(princess || empress || dictator) specialInitialization(princess, empress, dictator);
			else initializePieces();
		}
		else setUpBoardWithKings(); //need to put kings on board no matter what
	}
	
	/**
	 * helper to set up kings on an empty board
	 */
	private void setUpBoardWithKings(){		
		King king0 = new King(7,0,0);
		King king1 = new King(0,0,1);
		this.setPlayer0King(king0);
		this.setPlayer1King(king1);
		
		this.getSquares()[0][0].changeSquarePiece(king1);
		this.getSquares()[7][0].changeSquarePiece(king0);
	}
	
	/**
	 * creates empty board, with boardsquares with no owners or pieces
     *
	 * @param width: width of board being created
	 * @param height: height of board being created
	 */
	private void createBoard(int width, int height){
		setSquares(new BoardSquare[width][height]);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				getSquares()[i][j] = new BoardSquare();
			}
		}		
	}

	/**
	 * initializes a typical board in their initial positions
	 * if board width is larger than 8, tries to center the pieces
	 * sets the valid moves for each piece at the end of initialization
	 */
	private void initializePieces(){
		int startPoint = (getBoardColumns()-8)/2;

		for(int i = startPoint; i < (startPoint+8); i++){
			ChessPiece pawn0 = new Pawn(getBoardRows()-2, i, 0);
			ChessPiece pawn1 = new Pawn(1, i, 1);
			getSquares()[1][i].changeSquarePiece(pawn1);
			getSquares()[getBoardRows()-2][i].changeSquarePiece(pawn0);
			
			ChessPiece piece0;
			ChessPiece piece1;
			if(i == startPoint || i == startPoint+7){
				piece0 = new Rook(getBoardRows()-1, i, 0);
				piece1 = new Rook(0, i, 1);
			}				
			else if(i == startPoint+1 || i == startPoint+6){
				piece0 = new Knight(getBoardRows()-1, i, 0);
				piece1 = new Knight(0, i, 1);
			}
			else if(i == startPoint+2 || i == startPoint+5){
				piece0 = new Bishop(getBoardRows()-1, i, 0);
				piece1 = new Bishop(0, i, 1);
			}
			else if(i == startPoint+3){
				piece0 = new Queen(getBoardRows()-1, i, 0);
				piece1 = new Queen(0, i, 1);
			}
			else if(i == startPoint+4){
				piece0 = new King(getBoardRows()-1, i, 0);
				piece1 = new King(0, i, 1);
				setPlayer0King((King) piece0);
				setPlayer1King((King) piece1);
			}
			else{
				piece0 = null;
				piece1 = null;
			}
			
			getSquares()[getBoardRows()-1][i].changeSquarePiece(piece0);
			getSquares()[0][i].changeSquarePiece(piece1);
		}
		
		resetValidMoves();
	}
	
	public void specialInitialization(boolean princess, boolean empress, boolean dictator){
		int startPoint = (getBoardColumns()-8)/2;

		for(int i = startPoint; i < (startPoint+8); i++){
			ChessPiece pawn0 = new Pawn(getBoardRows()-2, i, 0);
			ChessPiece pawn1 = new Pawn(1, i, 1);
			getSquares()[1][i].changeSquarePiece(pawn1);
			getSquares()[getBoardRows()-2][i].changeSquarePiece(pawn0);
			
			ChessPiece piece0;
			ChessPiece piece1;
			if(i == startPoint){
				if(empress) piece0 = new Empress(getBoardRows()-1, i, 0);
				else piece0 = new Rook(getBoardRows()-1, i, 0);
				piece1 = new Rook(0, i, 1);
			}
			else if(i == startPoint+7){
				piece0 = new Rook(getBoardRows()-1, i, 0);
				if(empress) piece1 = new Empress(0, i, 1);
				else piece1 = new Rook(0, i, 1);
			}
			else if(i == startPoint+1 || i == startPoint+6){
				piece0 = new Knight(getBoardRows()-1, i, 0);
				piece1 = new Knight(0, i, 1);
			}
			else if(i == startPoint + 2){
				if(princess) piece0 = new Princess(getBoardRows()-1, i, 0);
				else piece0 = new Bishop(getBoardRows()-1, i, 0);
				piece1 = new Bishop(0, i, 1);
			}
			else if (i == startPoint + 5){
				piece0 = new Bishop(getBoardRows()-1, i, 0);
				if(princess) piece1 = new Princess(0, i, 1);
				else piece1 = new Bishop(0, i, 1);
			}
			else if(i == startPoint+3){
				if(dictator){
					piece0 = new Dictator(getBoardRows()-1, i, 0);
					piece1 = new Dictator(0, i, 1);
				}
				else{
					piece0 = new Queen(getBoardRows()-1, i, 0);
					piece1 = new Queen(0, i, 1);
				}
			}
			else if(i == startPoint+4){
				piece0 = new King(getBoardRows()-1, i, 0);
				piece1 = new King(0, i, 1);
				setPlayer0King((King) piece0);
				setPlayer1King((King) piece1);
			}
			else{
				piece0 = null;
				piece1 = null;
			}
			
			getSquares()[getBoardRows()-1][i].changeSquarePiece(piece0);
			getSquares()[0][i].changeSquarePiece(piece1);
		}
		
		resetValidMoves();
	}
}
