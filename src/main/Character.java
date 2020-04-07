package main;

public class Character {
	
	private char value;
	private int x, y;
	
	public Character(char value, int x, int y) {
		
		this.value = value;
		this.x = x;
		this.y = y;
		
	}
	
	public char getValue() {
		return this.value;
	}
	
	public void setValue(char value) {
		this.value = value;
	}
	
	public int[] getCoordinates() {
		int[] coordinates = {this.x, this.y};
		return coordinates;
	}

}
