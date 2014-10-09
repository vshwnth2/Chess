package Board;
import ChessPieces.ChessPiece;


public class BoardSquare {
	public ChessPiece piece;
	
	/**
	 * default constructor, just an empty square with no pieces
	 */
	public BoardSquare(){
		this.setPiece(null);
	}
	
	/**
	 * adds piece to the square
	 * @param piece: piece to add
	 */
	public void changeSquarePiece(ChessPiece piece){
		this.setPiece(piece);
	}
	
	/**
	 * gets the player of the piece on the square
	 * if no piece return -1
	 * 
	 * @return: player on the square if piece
	 * 			-1 o.w.
	 */
	public int getPlayer(){
		if(getPiece() == null) return -1;
		return getPiece().getPlayer();
	}
	
	/**
	 * sets the square to a certain player, if no player just returns
	 * 
	 * @param team: team to assign square to
	 */
	public void setPlayer(int team){
		if(getPiece() == null) return;
		getPiece().player = team;
	}

	public ChessPiece getPiece() {
		return piece;
	}

	public void setPiece(ChessPiece piece) {
		this.piece = piece;
	}
}
