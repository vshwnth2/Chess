package ChessGame;
import Board.RectangleBoard;

public class Chess {

	public static void main(String[] args) {
		RectangleBoard board = new RectangleBoard();
		ChessGUI gui = new ChessGUI(board);
		ChessController control = new ChessController(board, gui);
		gui.addMouseEventListener(control);
	}
}
