package TestCases;
import Board.RectangleBoard;
import ChessPieces.Bishop;
import ChessPieces.Pawn;
import ChessPieces.Princess;
import junit.framework.TestCase;


public class PrincessTests extends TestCase {
	//Bishop Knight
	public void testPrincessPositive(){
		RectangleBoard board = new RectangleBoard(8,8,false);

		board.insertPieceOnBoard(new Bishop(6,1,0));
		board.insertPieceOnBoard(new Bishop(1,7,1));
		
		assertTrue(board.changeBoard(6, 1, 4, 3)); // up right
		assertTrue(board.changeBoard(1,7,4,4)); // down left
		assertTrue(board.changeBoard(4,3,2,1)); // up left
		assertTrue(board.changeBoard(4,4,7,7)); // down right
		
		board = new RectangleBoard();
		assertTrue(board.changeBoard(7,1,5,2)); // up right
		assertTrue(board.changeBoard(0,1,2,2)); // down right
		assertTrue(board.changeBoard(5,2,3,1)); // up left
		assertTrue(board.changeBoard(2,2,4,1)); // down left
		assertTrue(board.changeBoard(3,1,4,3)); // right down
		assertTrue(board.changeBoard(4,1,3,3)); // right up
		assertTrue(board.changeBoard(4, 3, 5, 1)); // left down
		assertTrue(board.changeBoard(3, 3, 2, 1)); // left up
	}
	
	public void testPrincessNegative(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Princess(4,4,0));
		
		assertFalse(board.changeBoard(4,4,1,4)); // vertical
		assertFalse(board.changeBoard(4,4,4,1)); // horizontal
	}
	
	public void testPrincessObstruction(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Princess(4,4,0));
		board.insertPieceOnBoard(new Pawn(6,2,1));
		board.insertPieceOnBoard(new Pawn(3,3,0));
		board.insertPieceOnBoard(new Pawn(3,5,1));
		board.insertPieceOnBoard(new Pawn(6,6,1));
		
		assertFalse(board.changeBoard(4,4,1,1));
		assertFalse(board.changeBoard(4,4,2,6));
		assertFalse(board.changeBoard(4,4,7,1));
		assertFalse(board.changeBoard(4,4,7,7));
	}
}
