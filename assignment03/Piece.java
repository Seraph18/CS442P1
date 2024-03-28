package assignment03;

public class Piece {
	private BoardColors color; 
	private String name; 
	private String code; 
	private int pos; 
	private boolean active;
	public Piece(BoardColors colorIn, 
			String nameIn, String codeIn,
			int posIn, boolean activeIn) {
		color = colorIn;
		name = nameIn;
		code = codeIn;
		pos = posIn;
		active = activeIn;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setPos(int iccf) {
		pos = iccf;
	}
	public BoardColors getColor() {
		return color;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public int getPos() {
		return pos;
	}
	@Override
	public String toString() {
		return color + " " + name + " " + pos;
	}
	
}
