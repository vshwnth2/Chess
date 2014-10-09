package TestCases;
import Board.RectangleBoard;
import ChessPieces.ChessPiece;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import junit.framework.TestCase;


public class DictatorTests extends TestCase {
	public void testDictatorPositive(){
		RectangleBoard board = new RectangleBoard();
		assertTrue(board.changeBoard(7,1,5,2)); // up right
		board.printBoard();

		assertTrue(board.changeBoard(0,1,2,2)); // down right
		assertTrue(board.changeBoard(5,2,3,1)); // up left
		assertTrue(board.changeBoard(2,2,4,1)); // down left
		assertTrue(board.changeBoard(3,1,4,3)); // right down
		assertTrue(board.changeBoard(4,1,3,3)); // right up
		assertTrue(board.changeBoard(4, 3, 5, 1)); // left down
		assertTrue(board.changeBoard(3, 3, 2, 1)); // left up
		
		board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Queen(6,1,0));
		board.insertPieceOnBoard(new Queen(1,7,1));
		
		assertTrue(board.changeBoard(6, 1, 6, 4)); // right
		assertTrue(board.changeBoard(1, 7, 1, 4)); // left
		assertTrue(board.changeBoard(6,4,3,4)); // up
		assertTrue(board.changeBoard(1, 4, 2, 4)); //down
		
		assertTrue(board.changeBoard(3, 4, 1, 6)); // up right
		assertTrue(board.changeBoard(2,4,4,2)); // down left
		assertTrue(board.changeBoard(1,6,2,7)); // down right
		assertTrue(board.changeBoard(4,2,3,1)); // up left
	}
	
	public void testDictatorNegative(){
		RectangleBoard board = new RectangleBoard(8,8,false);

		board.insertPieceOnBoard(new Queen(6,1,0));
		assertFalse(board.changeBoard(6,1,4,0)); //random move
	}
	
	public void testDictatorObstruction(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		ChessPiece[] pieces = new ChessPiece[]{new Queen(4,4,0), new Pawn(6,2,1), new Pawn(3,3,0), new Pawn(3,5,1), 
		                       new Pawn(6,6,1), new Pawn(4,2,1), new Pawn(5,4,0), new Pawn(1,4,1), new Pawn(4,6,0)};
		
		board.multipleInsertionsOnBoard(pieces);
		
		assertFalse(board.changeBoard(4,4,4,0)); //left
		assertFalse(board.changeBoard(4,4,6,4)); //down
		assertFalse(board.changeBoard(4,4,4,7)); //right
		assertFalse(board.changeBoard(4,4,0,4)); //up
		assertFalse(board.changeBoard(4,4,1,1)); //upleft
		assertFalse(board.changeBoard(4,4,2,6)); //up right
		assertFalse(board.changeBoard(4,4,7,1)); //down left
		assertFalse(board.changeBoard(4,4,7,7)); //down right
	}
}
