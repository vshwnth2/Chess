package ChessGame;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import Board.*;
import ChessPieces.ChessPiece;

public class ChessController implements MouseListener{

	ChessBoard board;
	ChessGUI gui;
	
	int pressX;
	int pressY;
	int releaseX;
	int releaseY;
	
	public ChessController(ChessBoard board, ChessGUI gui){
		this.board = board;
		this.gui = gui;
	}
	
	public void changeBoard(ChessBoard board){
		this.board = board;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * what we do when the mouse is pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		changeBoard(gui.board);
		int square = gui.squareSize;
		if(boundaryChecks(e)){
			this.pressX = (e.getX() - square) / square; //mapping the x coordinate click to the 2d array coordinates of my board
			this.pressY = (e.getY() - square) / square; //similar for y
			ChessPiece piece = board.getSquares()[pressY][pressX].getPiece();
			if(piece != null && piece.getPlayer() == board.getCurrentPlayer()){
				gui.possibleMoves = piece.getValidMoves(); // used for possible moves highlight
				gui.repaint();
			}
		}
		else{
			this.pressX = -1;
			this.pressY = -1;
		}
	}

	/**
	 * check if the mouse is clicking on the board
	 * @param e: mouse event
	 * @return: if the mouse click is on board
	 */
	private boolean boundaryChecks(MouseEvent e){
		int square = gui.squareSize;
		if(e.getX() < square || e.getX() > square*(board.getBoardColumns()+1)){
			return false;
		}
		if(e.getY() < square || e.getY() > square*(board.getBoardRows()+1)){
			return false;
		}
		if(gui.isGameOver()) return false;
		
		return true;
	}
	
	/**
	 * what to do when we release the mouse
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		gui.possibleMoves = new Vector<Point>();
		if(this.pressX < 0 || this.pressY < 0){
			return;
		}
		if(boundaryChecks(e)){
			int square = gui.squareSize;
			this.releaseX = (e.getX() - square) / square; //same mapping done
			this.releaseY = (e.getY() - square) / square;
			int player = board.getCurrentPlayer();
			ChessPiece piece =  board.getSquares()[pressY][pressX].getPiece(); // need to do Y first cuz thats the "row" 
			boolean x = board.changeBoard(pressY, pressX, releaseY, releaseX);
			gui.repaint(); // repaint board now that it changes

			// a bunch of checks for messages to display based on move result
			if(piece != null && player == piece.getPlayer()) gui.errorMessage(!x,piece);
			else gui.errorMessage(!x, null);
			boolean stalemate = board.isStalemate();
			boolean check = board.isCheck();
			boolean checkmate = board.isCheckmate();
			gui.checkMessage(check);
			gui.checkmateMessage(checkmate);
			gui.stalemateMessage(stalemate);
			gui.repaintBoard(board);
		}
		changeBoard(gui.board);
	}
}
