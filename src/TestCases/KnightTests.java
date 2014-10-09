package TestCases;
import Board.RectangleBoard;
import junit.framework.TestCase;

public class KnightTests extends TestCase {
	
	public void testKnightMovementNegative(){
		System.out.println("\n in knight negative testing");

		RectangleBoard board = new RectangleBoard();
		
		assertFalse(board.changeBoard(7,1,5,3)); // random incorrect movements
		assertFalse(board.changeBoard(0,1,4,1));
		
		board.changeBoard(7,1,5,2);
		board.changeBoard(0,1,2,2);
		
		assertFalse(board.changeBoard(5,2,5,0)); //horiz
		assertFalse(board.changeBoard(5,2,5,5));
		
		assertFalse(board.changeBoard(5,2,3,2)); //vert
		
		assertFalse(board.changeBoard(5,2,3,0)); //diag
		assertFalse(board.changeBoard(5,2,3,4));
	}
	
	public void testKnightMovementPositive(){
		System.out.println("\n in knight testing");
		RectangleBoard board = new RectangleBoard();
		assertTrue(board.changeBoard(7,1,5,2)); // up right
		assertTrue(board.changeBoard(0,1,2,2)); // down right
		assertTrue(board.changeBoard(5,2,3,1)); // up left
		assertTrue(board.changeBoard(2,2,4,1)); // down left
		assertTrue(board.changeBoard(3,1,4,3)); // right down
		assertTrue(board.changeBoard(4,1,3,3)); // right up
		assertTrue(board.changeBoard(4, 3, 5, 1)); // left down
		assertTrue(board.changeBoard(3, 3, 2, 1)); // left up
	}

}
