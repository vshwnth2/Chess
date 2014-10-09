package TestCases;
import Board.RectangleBoard;
import ChessPieces.Empress;
import ChessPieces.Pawn;
import junit.framework.TestCase;


public class EmpressTests extends TestCase {
	//Rook-Knight
	public void testEmpressPositive(){
		System.out.println("\n in empress positive testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Empress(6,1,0));
		board.insertPieceOnBoard(new Empress(1,7,1));
		
		//rook like movements
		assertTrue(board.changeBoard(6, 1, 6, 4)); // right
		assertTrue(board.changeBoard(1, 7, 1, 4)); // left
		assertTrue(board.changeBoard(6,4,4,4)); // up
		assertTrue(board.changeBoard(1, 4, 3, 4)); //down
		
		//knight like movements
		RectangleBoard board2 = new RectangleBoard(8,8,false);
		
		board2.insertPieceOnBoard(new Empress(7,1,0));
		board2.insertPieceOnBoard(new Empress(0,1,1));
		
		assertTrue(board2.changeBoard(7,1,5,2)); // up right
		assertTrue(board2.changeBoard(0,1,2,2)); // down right
		assertTrue(board2.changeBoard(5,2,3,1)); // up left
		assertTrue(board2.changeBoard(2,2,4,1)); // down left
		assertTrue(board2.changeBoard(3,1,4,3)); // right down
		assertTrue(board2.changeBoard(4,1,3,3)); // right up
		assertTrue(board2.changeBoard(4, 3, 5, 1)); // left down
		assertTrue(board2.changeBoard(3, 3, 2, 1)); // left up
	}
	
	public void testEmpressNegative(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Empress(5,1,0));
		
		assertFalse(board.changeBoard(5, 1, 6, 0));//down left
		assertFalse(board.changeBoard(5,1,7,3));//down right
		assertFalse(board.changeBoard(5,1,4,0)); //up left
		assertFalse(board.changeBoard(5, 1, 2, 4)); //upright
	}
	
	public void testEmpressObstruction(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Empress(4,4,0));
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
