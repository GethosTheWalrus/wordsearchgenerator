package main;

public class Driver {
	
	public static void main(String[] args) {
		
		WordSearch ws = new WordSearch(20,20,10);
		
		System.out.println("Find these words:");
		for(int i = 0; i < ws.numberOfWords(); i++) {
			
			System.out.println(ws.getWord(i).toUpperCase());
			
		}
		
		ws.printWordSearch();
		
	}

}
