package ChessGame;
import ChessPieces.ChessPiece;


public class Move {
	private ChessPiece movingPiece;
	private int startRow;
	private int startColumn;
	private int endRow;
	private int endColumn;
	private ChessPiece capturedPiece;
	
	public Move(ChessPiece movingPiece, int startRow, int startColumn, int endRow, int endColumn, ChessPiece capturedPiece){
		this.setMovingPiece(movingPiece);
		this.setStartRow(startRow);
		this.setStartColumn(startColumn);
		this.setEndRow(endRow);
		this.setEndColumn(endColumn);
		this.setCapturedPiece(capturedPiece);
	}
	
	public void printMove(){
		if(capturedPiece != null)
			System.out.println(movingPiece.getIdentifier() + " (" + startRow + ", " + startColumn + ") to (" + endRow + ", " + endColumn + ") capturing" + capturedPiece.getIdentifier());
		else
			System.out.println(movingPiece.getIdentifier() + " (" + startRow + ", " + startColumn + ") to (" + endRow + ", " + endColumn + ") capturing nothing");
	}

	public ChessPiece getCapturedPiece() {
		return capturedPiece;
	}

	public void setCapturedPiece(ChessPiece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}

	public ChessPiece getMovingPiece() {
		return movingPiece;
	}

	public void setMovingPiece(ChessPiece movingPiece) {
		this.movingPiece = movingPiece;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getEndColumn() {
		return endColumn;
	}

	public void setEndColumn(int endColumn) {
		this.endColumn = endColumn;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
}
