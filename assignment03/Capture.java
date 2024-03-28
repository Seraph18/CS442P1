package assignment03;

import static assignment03.BoardColors.NONE;

public class Capture implements Command {
	int iccf;
	int undoICCF;
	Piece lost;
	Board board;
	public Capture (Board brd, int i) {
		iccf = i;
		board = brd;
		// Compute undoICCF (see Move)
		int from = i / 100;
   		int to = i % 100;
    		undoICCF = (to * 100) + from;
	}

	@Override
	public void execute() {
		int fromICCF = iccf/100;
		int toICCF = iccf%100;
		Piece p = board.getICCF(fromICCF);
		board.setICCF(new Piece(NONE, "--", " ", fromICCF, false));
		lost = board.getICCF(toICCF);
		lost.setActive(false);
		p.setPos(toICCF);
		board.setICCF(p);
	}

	@Override
	public void undo() {
		// first reverse the move executed above
		// as is done in Move
		int fromICCF = iccf/100;
		int toICCF = iccf%100;
		Piece p = board.getICCF(toICCF);
		board.setICCF(new Piece(NONE, "--", " ", fromICCF, false));
		
		// next set lost back to active
		// set its position to its old location
		// set it onto the board.
		lost.setActive(true);
		lost.setPos(toICCF);
		p.setPos(fromICCF);
		board.setICCF(p);
		
	}
}
