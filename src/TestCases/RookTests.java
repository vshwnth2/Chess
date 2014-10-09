package TestCases;
import Board.RectangleBoard;
import ChessPieces.Pawn;
import ChessPieces.Rook;
import junit.framework.TestCase;


public class RookTests extends TestCase {
	public void testRookPositive(){
		System.out.println("\n in rook positive testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Rook(6,1,0));
		board.insertPieceOnBoard(new Rook(1,7,1));
		
		assertTrue(board.changeBoard(6, 1, 6, 4)); // right
		assertTrue(board.changeBoard(1, 7, 1, 4)); // left
		assertTrue(board.changeBoard(6,4,4,4)); // up
		assertTrue(board.changeBoard(1, 4, 3, 4)); //down
	}
	
	public void testRookNegative(){
		System.out.println("\n in rook negative testing");
		RectangleBoard board = new RectangleBoard(8,8,false);

		board.insertPieceOnBoard(new Rook(4,4,0));
		
		//negative cases
		assertFalse(board.changeBoard(4, 4, 2, 2)); //diagnol
		assertFalse(board.changeBoard(4,4,3,6)); //L move
		assertFalse(board.changeBoard(4,4,1,7)); //random spot
	}

	public void testRookObstruction(){
		System.out.println("\n in rook obstruction testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Rook(4,4,0));
		board.insertPieceOnBoard(new Pawn(4,2,1));
		board.insertPieceOnBoard(new Pawn(5,4,0));
		board.insertPieceOnBoard(new Pawn(4,6,1));
		board.insertPieceOnBoard(new Pawn(1,4,1));
		
		assertFalse(board.changeBoard(4,4,4,0)); //left
		assertFalse(board.changeBoard(4,4,6,4)); //down
		assertFalse(board.changeBoard(4,4,4,7)); //right
		assertFalse(board.changeBoard(4,4,0,4)); //up
	}
}
