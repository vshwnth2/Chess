package TestCases;
import Board.RectangleBoard;
import ChessPieces.Pawn;
import ChessPieces.Rook;
import junit.framework.TestCase;


public class GeneralTests extends TestCase {
	public void testMoveToOwnSquare(){
		System.out.println("\n in move to own spot test");
		RectangleBoard board = new RectangleBoard();
		
		assertFalse(board.changeBoard(7,0,6,0)); // rook move upward to own piece
		assertFalse(board.changeBoard(7,0,7,1)); // rook move sideway to own piece
		
		assertFalse(board.changeBoard(7,1,6,3)); // knight move to own piece
		board.changeBoard(6, 0, 5, 0); // player1 turn
		
		assertFalse(board.changeBoard(2,0,1,1)); //bishop to own piece
		assertFalse(board.changeBoard(0,4,0,5)); //king		
	}


	public void testFirstMovePlayer() {
		System.out.println("\n in first move player");
		RectangleBoard board = new RectangleBoard();
		assertFalse(board.changeBoard(1,0,2,0)); //player1 moving first(wrong player)
		assertTrue(board.changeBoard(6,0,5,0)); //player0 moving first
	}
	public void testOutOfBounds(){
		System.out.println("\n in out of bounds testing");
		RectangleBoard board = new RectangleBoard();
		
		assertFalse(board.changeBoard(7, 0, 8, 0)); // rook off board downwards (8)
		assertFalse(board.changeBoard(7, 0, 7, -1)); // rook off board leftwards (-1)
		assertFalse(board.changeBoard(7,1,8,3)); // knight off board downwards (8)
		board.changeBoard(6,0,5,0); //to test player1 bounds, switches turn to player1
		assertFalse(board.changeBoard(0,0,-2,0)); //rook off board upwards (-2)
		assertFalse(board.changeBoard(0,6,-1, 8)); // knight off board upwards and rightwards
		assertFalse(board.changeBoard(0,7,0,11)); //rook off board rightwards
	}
	
	public void testMoveInvisiblePiece(){
		System.out.println("\n in move invisible piece");
		RectangleBoard board = new RectangleBoard();
		assertFalse(board.changeBoard(5,0,4,0));
	}
	
	public void testSpotSamePlayer(){
		System.out.println("\n in same spot testing");
		RectangleBoard board = new RectangleBoard();
		assertFalse(board.changeBoard(6,0,6,0));
		assertFalse(board.changeBoard(7,6,7,6));
	}
	
	public void testCapturing(){
		System.out.println("\n in capturing testing");
		RectangleBoard board = new RectangleBoard(8,8,false);
		
		board.insertPieceOnBoard(new Rook(4,3,0));
		board.insertPieceOnBoard(new Pawn(4,6,1));
		
		assertTrue(board.changeBoard(4, 3, 4, 6));
		board.setCurrentPlayer(0);
		board.changeBoard(4, 6, 3, 6);
		assertEquals(-1, board.getSquares()[4][6].getPlayer());
	}

	public void testBoardPoints(){
		System.out.println("\n in board points testing");
		RectangleBoard board = new RectangleBoard();
		
		assertEquals(94, board.getTotalBoardPoints());
		board.getSquares()[0][0].piece = null; // removing a rook
		board.resetValidMoves(); // point calculation happens in this function
		assertEquals(89, board.getTotalBoardPoints());
	}
	
	public void testUndoOneMove(){
		System.out.println("\n undo move testing");
		RectangleBoard board = new RectangleBoard();
		
		board.changeBoard(6, 1, 4, 1);
		board.changeBoard(1, 0, 3, 0);		
		board.changeBoard(4,1,3,0); // capture
		
		board.undoMove(1); // undo capture
		
		assertEquals(1, board.getSquares()[3][0].getPlayer());
		assertEquals(0, board.getSquares()[4][1].getPlayer());
		
		board.undoMove(1); // undo simple pawn movement
		
		assertEquals(-1, board.getSquares()[3][0].getPlayer());
		assertEquals(1, board.getSquares()[1][0].getPlayer());
	}
	
	public void testUndoMultipleMoves(){
		System.out.println("\n undo move testing");
		RectangleBoard board = new RectangleBoard();
		
		board.changeBoard(6, 1, 4, 1);
		board.changeBoard(1, 0, 3, 0);		
		board.changeBoard(4,1,3,0); // capture
		
		board.undoMove(3); // undo capture

		assertEquals(-1, board.getSquares()[3][0].getPlayer());
		assertEquals(-1, board.getSquares()[4][1].getPlayer());
		
	}
	
	//TODO
	public void testRedoMoves(){
		RectangleBoard board = new RectangleBoard();
		
		board.changeBoard(6, 1, 4, 1);
		board.changeBoard(1, 0, 3, 0);
		board.changeBoard(4, 1, 3, 1);
		board.changeBoard(3, 0, 4, 0);
		board.undoMove(3);
//		board.redoMove(1);
		board.changeBoard(6,7,5,7);
	}
	
	public void testBasicMovement(){
		System.out.println("\n in movement testing");
		RectangleBoard board = new RectangleBoard();

		assertEquals(-1, board.getSquares()[5][0].getPlayer());
		assertEquals(0, board.getSquares()[6][0].getPlayer());
		board.changeBoard(6,0,5,0);
		assertEquals(0, board.getSquares()[5][0].getPlayer());
		assertEquals(-1, board.getSquares()[6][0].getPlayer());
	}
	
	public void testInsertDeleteToAllMoves(){
		RectangleBoard board = new RectangleBoard();
		assertEquals(0, board.getAllPastMoves().size()); //no moves at begining
		board.changeBoard(6, 0, 4, 0);
		assertEquals(1, board.getAllPastMoves().size()); //1 move happened
		board.changeBoard(1,0,3,0);
		assertEquals(2, board.getAllPastMoves().size()); //2 moves happened
		board.changeBoard(6, 6, 4, 6);
		assertEquals(3, board.getAllPastMoves().size()); //3 moves happened
//		board.undoMove(1);
//		assertEquals(2, board.getAllPastMoves().size()); //undo move, 2 moves happened
//		board.undoMove(2);
//		assertEquals(0, board.getAllPastMoves().size()); // undo 2 moves, 1 move happened
	}
	
	public void testUpdateAllValidMoves(){
		RectangleBoard board = new RectangleBoard();
		assertEquals(2, board.getPiece(7,1).getValidMoves().size());
		board.changeBoard(7,1,5,2);
		assertEquals(5, board.getPiece(5,2).getValidMoves().size());
	}
	
	public void testInsertingToRightSpot(){
		RectangleBoard board = new RectangleBoard(8,8,false);
		board.insertPieceOnBoard(new Pawn(5,5,0));
		assertEquals("P", board.getPiece(5, 5).getIdentifier());
		assertEquals(0, board.getPiece(5, 5).getPlayer());
	}
}
