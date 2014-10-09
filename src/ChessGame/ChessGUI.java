package ChessGame;
import ChessPieces.*;
import Board.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.Vector;
 
public class ChessGUI extends JPanel implements ActionListener{

	int windowWidth = 800;
	int windowHeight = 800;
	
	int rows;
	int columns;
	
	ChessBoard board;
	
	int squareSize;
	
	boolean gameOver = false;
	boolean check = false;
	
	JFrame game;
	
	int whiteScore;
	int blackScore;
	String error = "";
	
	Vector<Point> possibleMoves;
	
	/**
	 * contructor for the GUI, sets the variables to defaults, creates the frame
	 * @param board: the board to take as reference to draw out
	 */
    public ChessGUI(ChessBoard board){ //takes in board
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        
        this.possibleMoves = new Vector<Point>();
        
        this.whiteScore = 0;
        this.blackScore = 0;
        
        this.rows = board.getBoardRows();
        this.columns = board.getBoardColumns();
        this.board = board;
        int buttonSize = windowHeight/10;
        this.setWindowHeight(this.getWindowHeight() + 100);
        
        game = new JFrame("ChessGUI");
        
        JPanel myPanel = initializePanel(windowWidth, windowHeight/11);
        initializeButtons(myPanel, buttonSize);
        setUpMenu();
        game.setContentPane(myPanel);
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.setSize(this.windowWidth, this.windowHeight + buttonSize);
        game.add(this);
        game.setVisible(true);
    }
    
    /**
     * extension of paintCompnonent
     */
    public void paintComponent(Graphics gameBoard) {
    	super.paintComponent(gameBoard);
    	int divider = Math.max(rows, columns) + 2;
    	this.squareSize = Math.min(windowWidth, windowHeight)/divider;
    	paintSquares(gameBoard);
    	paintPossibleMoves(gameBoard);
    	paintLabels(gameBoard);
    	addPieces(gameBoard);
    	addScores(gameBoard);
    	if(error != "") printError(gameBoard);
    	if(check) printCheck(gameBoard);
    }
    
    /**
     * prints error message when invalid move
     * @param gameBoard: the Graphics component to draw on
     */
    public void printError(Graphics gameBoard){
    	gameBoard.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, squareSize/6));
    	if(error != ""){
	    	int x = Integer.parseInt(error.substring(0,1));
	    	int y = Integer.parseInt(error.substring(1,2));
	    	ChessPiece piece = board.getSquares()[x][y].getPiece();
	    	if(piece != null) gameBoard.drawString("ERROR INVALID MOVE: " + piece.getIdentifier() + " " + piece.movementRules(), squareSize, squareSize-10);
    	}
	    else gameBoard.drawString("ERROR INVALID MOVE: no piece at spot or wrong player moving", squareSize, squareSize-10);
    	error = "";
    }
    
    /**
     * prints which player is in check when there is a check
     * @param gameBoard: the Graphics component to draw on
     */
    private void printCheck(Graphics gameBoard){
    	gameBoard.setColor(java.awt.Color.BLACK);
    	int fontSize = squareSize/3;
    	gameBoard.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, fontSize));
    	gameBoard.drawString("Player " + board.getCurrentPlayer() + " in CHECK!", windowWidth/2 - 2*squareSize, (rows+2)*squareSize);
    	check = false;
    }
    
    /**
     * wrapper for controller to eventually invoke print check
     * @param c: whether board is in check
     */
    public void checkMessage(boolean c){
    	check = c;
    }
    
    /**
     * wrapper for controller to eventually envoke error check
     * @param e: whether move was error
     * @param piece: piece that was trying to make the move
     */
    public void errorMessage(boolean e, ChessPiece piece){
    	if(e) {
    		if(piece != null){
        		error = Integer.toString(piece.getRow()) + Integer.toString(piece.getColumn());
    		}
    	}
    }
    
    /**
     * wrapper for controller to eventually envoke checkmate check
     * @param c: whether game is in checkmate state
     */
    public void checkmateMessage(boolean c){
    	if(c){
    		endGameOptions("CHECKMATE");		
    	}
    }
    
    /**
     * wrapper for controller to eventually envoke stalemake check
     * @param s: whether game is in stalemate
     */
    public void stalemateMessage(boolean s){
    	if(s){
    		endGameOptions("STALEMATE");
    	}
    }
    
    /**
     * when game is over, show screen with endGame options
     * @param type: why the game ended
     */
    private void endGameOptions(String type){
        Object[] options = { "New Game", "Exit" };
        String winner = "Tie game";
        if(type == "CHECKMATE"){
            this.changeScore((board.getCurrentPlayer()+1)%2, true);
            winner = Integer.toString((board.getCurrentPlayer()+1)%2);
        }
        else{
            this.changeScore((board.getCurrentPlayer()+1)%2, false);
            this.changeScore(board.getCurrentPlayer(), false);
        }
        int selection = JOptionPane.showOptionDialog(game, type + " player " + winner + " wins! Game over... Another game?", "Game Over", 1, 1, null, options, options[0]);
        if(selection == 1){
            setVisible(false);
            game.dispose();
        }
        else{
            newGameScreen();
        }
    }
    
    /**
     * draws the scores of the game
     * @param gameBoard: graphics to where to draw to
     */
    private void addScores(Graphics gameBoard){
    	gameBoard.setColor(java.awt.Color.BLACK);
    	int fontSize = squareSize/4;
    	gameBoard.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, fontSize));
    	gameBoard.drawString("WHITE (Player 0): " + whiteScore, squareSize, squareSize - 2*fontSize);
    	gameBoard.drawString("BLACK (Player 1): " + blackScore, (2*windowWidth)/3, squareSize - 2*fontSize);
    }

    /**
     * draws the gamebaord squares
     * @param gameBoard: graphics to where to draw to
     */
    private void paintSquares(Graphics gameBoard){
    	for (int row = 0; row < rows; row++) {
    	    for (int column = 0; column < columns; column++) {
	    		if((row+column) % 2 == 0) {
	    		    gameBoard.setColor(new Color(244, 127, 36)); //white
	    		}
	    		else{
	    		    gameBoard.setColor(new Color(0, 60, 125)); //black
	    		}
	    		gameBoard.fillRect((column+1)*squareSize, (row+1)*squareSize, squareSize, squareSize);
    	    }
    	}
    }
    
    /**
     * draw the possible moves for a piece
     * @param gameBoard: graphics to where to draw to
     */
    private void paintPossibleMoves(Graphics gameBoard){
    	for(int i=0; i<possibleMoves.size(); i++){
    		Point p = possibleMoves.elementAt(i);
    		int x = (int) p.getX();
    		x = (x*squareSize) + squareSize;
    		int y = (int) p.getY();
    		y = (y*squareSize) + squareSize;
    		gameBoard.setColor(new Color(160,160,160));
    		gameBoard.fillRect(y, x, squareSize, squareSize);
    	}
    }
    
    /**
     * draws the labels on the side of the board
     * @param gameBoard: graphics to where to draw to
     */
    private void paintLabels(Graphics gameBoard){
    	gameBoard.setColor(java.awt.Color.BLACK);
    	int fontSize = squareSize/2;
    	gameBoard.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, fontSize));
    	char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    	for(int i=0; i<columns; i++){
    		gameBoard.drawString(Character.toString(alphabet[i]), (i+1)*squareSize + fontSize/2, (rows+1)*squareSize + fontSize);
    	}
    	for(int i=0; i<rows; i++){
    		gameBoard.drawString(Integer.toString(rows-i), fontSize, squareSize*i + squareSize + squareSize/2 + fontSize/2);
    	}
    }
    
    /**
     * draws the pieces on the graphics
     * @param gameBoard: graphics where to draw
     */
    private void addPieces(Graphics gameBoard){
    	int pieceSize = squareSize;
    	gameBoard.setFont(new java.awt.Font("Arial Unicode MS", java.awt.Font.PLAIN, pieceSize));
    	int centerPiece = squareSize/5;
    	int y = rows;
    	int x = columns;
    	
    	BoardSquare[][] squares = board.getSquares();
    	
    	for(int i=0; i < y; i++){
    		for(int j=0; j < x; j++){
    			ChessPiece piece = squares[i][j].getPiece();
    			if(piece != null){
        			gameBoard.drawString(piece.getCode(), squareSize*(j+1), (squareSize*(i+2)) - centerPiece);    				
    			}
    		}
    	}
    }
    
    /**
     * initialize the undo button at the bottom
     * @param myPanel: panel where to add it
     * @param size: size of the button
     */
    private void initializeButtons(JPanel myPanel, int size) {
		JButton button = new JButton("Undo Move");
		button.setBounds(0, windowHeight - size, windowWidth, size);
		button.addActionListener(this);
		myPanel.add(button);
    }

    /**
     * initialize the panel
     * @param width: width of panel
     * @param height: height of panel
     * @return the JPanel
     */
    private JPanel initializePanel(int width, int height) {
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(width,height));
        myPanel.setLayout(new BorderLayout());
        return myPanel;
    }
 
    /**
     * sets up the menu bar at top left
     */
    private void setUpMenu() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        addMenuOptions(file, "New Game");
        addMenuOptions(file, "Restart");
        addMenuOptions(file, "Forfeit");
        menubar.add(file);
        game.setJMenuBar(menubar);
    }
 
    /**
     * adds the different options to the menu
     * @param file: the menu
     * @param option: the option to add
     */
    private void addMenuOptions(JMenu file, String option){
    	JMenuItem item = new JMenuItem(option);
        item.addActionListener(this);
        file.add(item);
    }
    
    /**
     * what to do when an action is performed (click a menu item)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
    	String actionName = e.getActionCommand();
    	int newBoard = -1;
    	if(actionName == "Undo Move"){
        	board.undoMove(1);
            check = false;
    	}
    	else if(actionName == "New Game"){
        	newGameScreen();
    	}
    	else if(actionName == "Restart"){
        	newBoard = checkAction("Restart");
        	if(newBoard == 0){
        		newBoard = JOptionPane.showConfirmDialog(game, "Player " + (board.getCurrentPlayer()+1)%2 + " okay with game restart?", "check restart", JOptionPane.YES_NO_OPTION);
        	}
    	}
    	else if(actionName == "Forfeit"){
        	newBoard = checkAction("Forfeit");
        	if(newBoard == 0){
        		if(board.getCurrentPlayer() == 0) this.changeScore(1, true);
        		else changeScore(0, true);
        	}
    	}
    	if(newBoard == 0){
    		endGameOptions("Game ended by user");
    	}
        this.repaint();
    }
    
    /**
     * shows diaglog with confirmation of the action that was performed
     * @param type: type of action
     * @return: which button was clicked
     */
    private int checkAction(String type){
    	return JOptionPane.showConfirmDialog(game, "Player " + board.getCurrentPlayer() + " " + type + " Game?", type, JOptionPane.YES_NO_OPTION);
    }
    
    /**
     * what to do when the game is over/ what to show to screen
     */
    private void newGameScreen(){
    	Object[] options = { "Regular", "Custom Rows/Columns", "With Custom Pieces" };
    	int selection = JOptionPane.showOptionDialog(game, "What type of new game?", "New Game", 1, 1, null, options, options[0]);
    	if(selection == 0) this.board = new RectangleBoard();
    	else if(selection == 1){
    		getDimUserInput();
    		this.board = new RectangleBoard(rows, columns, true);
    	}
    	else if(selection == 2){
    		getPieceUserInput();
    	}
    }
    
    /**
     * the options for the custom pieces on the board
     */
    private void getPieceUserInput(){
    	Object[] possibleValues = { "Princess Only", "Empress Only", "Dictator Only", "Princess and Empress", "Princess and Dictator", "Empress and Dictator", "All Three" };
    	int result = JOptionPane.showOptionDialog(game, "What type of pieces?", "Piece Selection", 1, 1, null, possibleValues, possibleValues[0]);;
    	boolean prin, emp, dict;
    	prin = emp = dict = false;
    	if(result == 0 || result == 3 || result == 4 || result == 6) prin = true;
    	if(result == 1 || result == 3 || result == 5 || result == 6) emp = true;
    	if(result == 2 || result == 4 || result == 5 || result == 6) dict = true;
		this.board = new RectangleBoard(8,8,true, prin,emp,dict);
    }
    
    /**
     * gets the preferences for amount of rows and columns for new game
     */
    private void getDimUserInput(){
    	Object[] possibleValues = { "8", "9", "10", "11", "12", "13", "14", "15" };
    	Object selectedValue = JOptionPane.showInputDialog(null,
    	"Choose number of rows", "Row Selection",
    	JOptionPane.INFORMATION_MESSAGE, null,
    	possibleValues, possibleValues[0]);
    	this.rows = Integer.parseInt(selectedValue.toString());
    	selectedValue = JOptionPane.showInputDialog(null,
    	"Choose number of columns", "Column Selection",
    	JOptionPane.INFORMATION_MESSAGE, null,
    	possibleValues, possibleValues[0]);
    	this.columns = Integer.parseInt(selectedValue.toString());
    }
    
    /**
     * wrapper around repaint, does endgame checks before repainting
     * @param newBoard: board to draw
     */
    public void repaintBoard(ChessBoard newBoard){
    	if(board.isCheckmate() || board.isStalemate()){
    		this.setGameOver(true);
    	}
    	if(isGameOver()){
    		createGameOverScreen(newBoard);
    	}
    	this.repaint();
    }
    
    /**
     * screen to show when game is over
     * @param newBoard: board to draw and set to gui variable
     */
    private void createGameOverScreen(ChessBoard newBoard){
    	int player = board.getCurrentPlayer();
    	boolean checkMate = board.isCheckmate();
    	changeScore((player+1)%2, checkMate);
    	this.setGameOver(false);
    	this.board = newBoard;
    }
    
    /**
     * updates score for a player
     * @param player: player whos score is updating
     * @param win: if they won (for how many points to increment)
     */
    private void changeScore(int player, boolean win){
    	int increment = 1;
    	if(win) increment = 2;
    	if(player == 1) blackScore += increment;
    	else whiteScore += increment;
    }
    
    /**
     * tells it to use the chess controller to listen for mouse events
     * @param c: the controller to use for mouse events
     */
    public void addMouseEventListener(ChessController c){
    	this.addMouseListener(c);
    }   
    
	public int getWindowWidth() {
		return windowWidth;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public int getWindowHeight() {
		return windowHeight;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public ChessBoard getBoard() {
		return board;
	}

	public void setBoard(ChessBoard board) {
		this.board = board;
	}

	public int getSquareSize() {
		return squareSize;
	}

	public void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

}