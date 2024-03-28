package assignment03;

import java.awt.Color;

public enum BoardColors {
	BLACK(Color.BLACK), WHITE(Color.WHITE), CLICKED(Color.CYAN), NONE(Color.RED);
	Color color;
	BoardColors(Color c) {
		color = c;
	}
	public Color color() {
		return color;
	}
	public String toString() {
		return name().substring(0, 1);
	}
}
