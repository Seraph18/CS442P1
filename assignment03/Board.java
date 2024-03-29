package assignment03;

import static assignment03.BoardColors.BLACK;
import static assignment03.BoardColors.NONE;
import static assignment03.BoardColors.WHITE;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board {
	private Piece[][] board = new Piece[8][8];
	private BoardColors[][] boardColors = new BoardColors[8][8];
	private Stack<Command> executedStack = new Stack<>();
	private Stack<Command> undoneStack = new Stack<>();

	public List<Piece> Pieces = new ArrayList<>();
	public Board() {
		for(byte i = 0; i < 8; i++) { // columns=files
			for(byte j = 0; j < 8; j++) { // rows=rank
				board[i][j] = makeOpenSquare(i+1, j+1);
				if((i+j)%2 == 0) 
					boardColors[i][j] = BLACK;
				else
					boardColors[i][j] = WHITE;
			}
		}
		makePieces();
		initializeBoard();
	}
	public Piece makeOpenSquare(int file, int rank) {
		return new Piece(NONE, "--", " ", file*10+rank, false);
	}
	public Piece getICCF(int code) { //57
		int file = (code/10);
		int rank = (code % 10);
		return board[file-1][rank-1];
	}
	public BoardColors getColor(int code) { //57
		int file = (code/10);
		int rank = (code % 10);
		return boardColors[file-1][rank-1];
	}
	public void setICCF(Piece p) { 
		int rank = p.getPos()%10;
		int file = p.getPos()/10;
		board[file-1][rank-1] = p;
	}
	public void setColor(int code, BoardColors col) { //57
		int file = (code/10);
		int rank = (code % 10);
		boardColors[file-1][rank-1] = col;
	}
	public void initializeBoard() {
		for(Piece p : Pieces) {
			int rank = p.getPos()%10;
			int file = p.getPos()/10;
			board[file-1][rank-1] = p;
		}			
	}

	public void makePieces () {
		Pieces.add(new Piece(WHITE, "R", "\u2656", 11, true));
		Pieces.add(new Piece(WHITE, "N", "\u2658", 21, true));
		Pieces.add(new Piece(WHITE, "B", "\u2657", 31, true));
		Pieces.add(new Piece(WHITE, "K", "\u2654", 41, true));
		Pieces.add(new Piece(WHITE, "Q", "\u2655", 51, true));
		Pieces.add(new Piece(WHITE, "B", "\u2657", 61, true));
		Pieces.add(new Piece(WHITE, "N", "\u2658", 71, true));
		Pieces.add(new Piece(WHITE, "R", "\u2656", 81, true));

		Pieces.add(new Piece(WHITE, "P", "\u2659", 12, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 22, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 32, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 42, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 52, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 62, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 72, true));
		Pieces.add(new Piece(WHITE, "P", "\u2659", 82, true));

		Pieces.add(new Piece(BLACK, "R", "\u2656", 18, true));
		Pieces.add(new Piece(BLACK, "N", "\u2658", 28, true));
		Pieces.add(new Piece(BLACK, "B", "\u2657", 38, true));
		Pieces.add(new Piece(BLACK, "K", "\u2654", 48, true));
		Pieces.add(new Piece(BLACK, "Q", "\u2655", 58, true));
		Pieces.add(new Piece(BLACK, "B", "\u2657", 68, true));
		Pieces.add(new Piece(BLACK, "N", "\u2658", 78, true));
		Pieces.add(new Piece(BLACK, "R", "\u2656", 88, true));

		Pieces.add(new Piece(BLACK, "P", "\u2659", 17, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 27, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 37, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 47, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 57, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 67, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 77, true));
		Pieces.add(new Piece(BLACK, "P", "\u2659", 87, true));
	}	

	public void doNewCommand(Command cm) {
		// clear the undo stack
		// put this command on the executedStack
		// execute the command
		undoneStack.clear();
		executedStack.add(cm);
		cm.execute();
	}
	
	public void undoCommand() {
		// if there is a command to be undone
		// get the last executed command
		// move it to the redo stack
		// call the undo of the command
		if (!executedStack.isEmpty()) {
			Command commandToUndo = executedStack.pop();
			undoneStack.push(commandToUndo);
			commandToUndo.undo();
		}
	}
	public void redoCommand() {
		// if there is a command to be redone
		// get the last undone command
		// move it to the executed stack
		// call the execute of the command

		if (!undoneStack.isEmpty()) {
			Command commandToRedo = undoneStack.pop();
			executedStack.push(commandToRedo);
			commandToRedo.execute();
		}
	}
}
