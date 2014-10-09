package TestCases;
import Board.RectangleBoard;
import ChessPieces.Bishop;
import ChessPieces.Pawn;
import junit.framework.TestCase;

public class BishopTests extends TestCase {
	public void testBishopPositive(){
		RectangleBoard board = new RectangleBoard(8,8,false);

		board.insertPieceOnBoard(new Bishop(6,1,0));
		board.insertPieceOnBoard(new Bishop(1,7,1));
		
		assertTrue(board.changeBoard(6, 1, 4, 3)); // up right
		assertTrue(board.changeBoard(1,7,4,4)); // down left
		assertTrue(board.changeBoard(4,3,2,1)); // up left
		assertTrue(board.changeBoard(4,4,7,7)); // down right
	}
	
	public void testBishopNegative(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Bishop(4,4,0));
		
		assertFalse(board.changeBoard(4,4,1,4)); // vertical
		assertFalse(board.changeBoard(4,4,4,1)); // horizontal
		assertFalse(board.changeBoard(4,4,6,3)); // L
	}
	
	public void testBishopObstruction(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Bishop(4,4,0));
		
		//obstruction pieces
		board.insertPieceOnBoard(new Pawn(6,2,1));
		board.insertPieceOnBoard(new Pawn(3,3,0));
		board.insertPieceOnBoard(new Pawn(3,5,1));
		board.insertPieceOnBoard(new Pawn(6,6,1));
		
		assertFalse(board.changeBoard(4,4,1,1)); //up left
		assertFalse(board.changeBoard(4,4,2,6)); //up right
		assertFalse(board.changeBoard(4,4,7,1));//down left
		assertFalse(board.changeBoard(4,4,7,7)); //down right
	}
}
