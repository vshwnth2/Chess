package TestCases;
import Board.RectangleBoard;
import ChessPieces.Bishop;
import ChessPieces.Dictator;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;
import junit.framework.TestCase;

public class KingTests extends TestCase {
	public void testKingPositive(){
		System.out.println("\n in king positive testing");
		RectangleBoard board = new RectangleBoard(8,8, false);

		board.insertPieceOnBoard(new Pawn(5,7,0)); // to avoid the stalemate situation
		
		//positive test cases in all 8 directions
		assertTrue(board.changeBoard(7,0,6,1)); //up right
		assertTrue(board.changeBoard(0, 0, 1, 1)); //down right
		assertTrue(board.changeBoard(6,1,5,0)); //up left
		assertTrue(board.changeBoard(1,1,2,0)); //down left
		assertTrue(board.changeBoard(5,0,5,1)); //right
		assertTrue(board.changeBoard(2,0,1,0)); //up
		assertTrue(board.changeBoard(5,1,5,0)); //left
		assertTrue(board.changeBoard(1,0,2,0)); //down

	}
	
	public void testKingNegative(){
		System.out.println("\n in king negative testing");
		RectangleBoard board = new RectangleBoard(8,8, false);
		
		assertFalse(board.changeBoard(7,0,5,0)); //vert more than one
		assertFalse(board.changeBoard(7,0,4,3)); //diag more than one
		assertFalse(board.changeBoard(7,0,7,6)); //horiz more than one
		assertFalse(board.changeBoard(7,0,5,1)); //L shape
		assertFalse(board.changeBoard(7,0,2,0)); //random

	}
	
	public void testCheck1(){ //king cant move, but another piece can capture checking piece
		System.out.println("\n in check1 testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Dictator(4,2,0));
		board.insertPieceOnBoard(new Bishop(2,1,1));
		
		assertFalse(board.isCheck());
		board.changeBoard(4,2,1,2);
		assertTrue(board.isCheck());
		assertFalse(board.isCheckmate());
	}
	
	public void testCheck2(){ //king can move 
		System.out.println("\n in check2 testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Queen(4,2,0));
		
		assertFalse(board.isCheck());
		board.changeBoard(4,2,0,2);
		assertTrue(board.isCheck());
		assertFalse(board.isCheckmate());
		board.changeBoard(0,0,1,0);
		assertFalse(board.isCheck());
	}
	
	public void testCheck3(){ //king cant move but piece can block
		System.out.println("\n in check3 testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Queen(4,2,0));
		board.insertPieceOnBoard(new Rook(1,6,0));
		board.insertPieceOnBoard(new Rook(5,1,1));
		
		assertFalse(board.isCheck());
		board.changeBoard(4,2,0,2);
		assertTrue(board.isCheck());
		assertFalse(board.isCheckmate());
		board.changeBoard(5,1,0,1);
		assertFalse(board.isCheck());
	}
	
	public void testMustMoveOutOfCheck(){
		System.out.println("\n in must move out of check testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Rook(5,1,1));
		board.insertPieceOnBoard(new Queen(4,2,0));
		assertFalse(board.isCheck());
		board.changeBoard(4, 2, 0, 2);
		assertTrue(board.isCheck());
		assertFalse(board.changeBoard(5,1,4,1));//valid moves but doesnt take care of check
		assertFalse(board.changeBoard(5,1,5,5));
		assertTrue(board.changeBoard(5, 1, 0, 1));//blocks check
		board.undoMove(1); //undo, cuz couldve also moved king
		assertTrue(board.changeBoard(0,0,1,0));
	}
	
	public void testNoPuttingSelfInCheck(){
		System.out.println("\n in no putting self in check testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Rook(0,1,1));
		board.insertPieceOnBoard(new Queen(0,2,0));
		
		board.setCurrentPlayer(1);
		assertFalse(board.changeBoard(0,1,5,1)); // puts self in check
	}
}
