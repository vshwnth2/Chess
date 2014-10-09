package TestCases;
import Board.RectangleBoard;
import ChessPieces.Bishop;
import ChessPieces.Dictator;
import ChessPieces.Knight;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;
import junit.framework.TestCase;

public class EndGameTests extends TestCase {
	public void testCheckmate(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Dictator(4,2,0));
		assertFalse(board.isCheckmate());
		board.changeBoard(4,2,1,2);
		assertTrue(board.isCheckmate());
	}
	
	public void testNormalStalemate(){
		RectangleBoard board = new RectangleBoard(8,8,false);		
		board.insertPieceOnBoard(new Queen(7,1,0));

		assertFalse(board.isStalemate());
		board.changeBoard(7, 1, 2, 1);
		
		board.printBoard();
		assertTrue(board.isStalemate());

		board.insertPieceOnBoard(new Rook(2,0,1));
		assertFalse(board.isStalemate());
	}
	
	public void testKingKingEnd(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Pawn(6,0,1));
		
		//King-King endgame
		assertFalse(board.isStalemate());
		board.changeBoard(7,0,6,0);
		assertTrue(board.isStalemate());
	}
	
	public void testKingKingKnightEnd(){
		System.out.println("\n in king king knight testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Pawn(6,0,1));
		board.insertPieceOnBoard(new Knight(1,7,0));
		
		assertFalse(board.isStalemate());
		board.changeBoard(7,0,6,0);
		assertTrue(board.isStalemate());
	}
	
	public void testKingKingBishopEnd(){
		RectangleBoard board = new RectangleBoard(8,8,false);		

		board.insertPieceOnBoard(new Pawn(6,0,1));
		board.insertPieceOnBoard(new Bishop(1,7,0));
		
		assertFalse(board.isStalemate());
		board.changeBoard(7,0,6,0);
		assertTrue(board.isStalemate());
	}
	
}
