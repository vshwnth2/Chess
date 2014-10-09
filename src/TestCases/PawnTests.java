package TestCases;
import Board.RectangleBoard;
import junit.framework.TestCase;

public class PawnTests extends TestCase {
	
	public void testPawnBackwards(){
		System.out.println("\n in pawn backwards");
		RectangleBoard board = new RectangleBoard();
		
		board.changeBoard(6,0,4,0); //set up
		board.changeBoard(1,7,3,7);
		
		assertFalse(board.changeBoard(4,0,5,0)); //player0 moves back one spot
		board.changeBoard(6,7,5,7); //switches turn
		assertFalse(board.changeBoard(3,7,2,7)); //player1 moves up one spot(backwards with respect to the pawn)
	}
	
	public void testPawnCapture(){
		System.out.println("\n in pawn capture");
		RectangleBoard board = new RectangleBoard();
		
		board.changeBoard(6,0,4,0); // setup
		board.changeBoard(1,1,3,1);
		board.changeBoard(6,3,4,3);
		board.changeBoard(1,4,3,4);

		assertTrue(board.changeBoard(4,0,3,1)); //player0 captures
		assertTrue(board.changeBoard(3,4,4,3)); //player1 captures
		
		assertFalse(board.changeBoard(6,5,5,6)); //pawn moves up right with no piece to capture
		assertFalse(board.changeBoard(6,5,5,4)); //pawn moves up left with no piece to capture
		board.changeBoard(6,5,5,5);
		assertFalse(board.changeBoard(1,6,2,7)); //pawn moves down right with no piece to capture
		assertFalse(board.changeBoard(1,6,2,5)); //pawn moves down left wiht no piece to capture
	}
	
	public void testPawnTwoSpaces(){
		System.out.println("\n in pawn 2 space testing");
		RectangleBoard board = new RectangleBoard();
		
		assertTrue(board.changeBoard(6,0,4,0)); // pawn moves 2 spots on frist move player0
		assertTrue(board.changeBoard(1,1,3,1)); // pawn moves 2 spots on first move player1
		
		assertFalse(board.changeBoard(4,0,2,0)); // pawn moves 2 spots on 2nd move
		assertFalse(board.changeBoard(3,1,5,1)); // pawn moves 2 spots on 2nd move

		board.changeBoard(4,0,3,0); //set up
		board.changeBoard(3,0,2,0);
		
		assertFalse(board.changeBoard(1,0,3,0)); //pawn moves 2 on first move, but piece in way
	}
	
	public void testPawnOneSpace(){
		System.out.println("\n in pawn one space");
		RectangleBoard board = new RectangleBoard();

		assertTrue(board.changeBoard(6,0,5,0)); //player0 moves pawn 1 square on first turn
		assertTrue(board.changeBoard(1,0,2,0)); //player1 moves pawn 1 square on 1st turn
		assertTrue(board.changeBoard(5,0,4,0)); //player0 moves pawn 1 square on 2nd turn
		assertTrue(board.changeBoard(2,0,3,0)); //player1 moves pawn 1 square on 2nd turn

	}
}
