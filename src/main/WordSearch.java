package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.security.SecureRandom; 

public class WordSearch {

	private int rows, 
		columns, 
		numberOfWords,
		numberOfCharacters = 0;
	
	// list of words that the word search will contain
	private ArrayList<String> words;
	
	private ArrayList<ArrayList<Character>> wordSearchRows =  new ArrayList<ArrayList<Character>>();
	
	/*
	 * Constructor to initialize a word search given dimensions
	 * rows - number of rows the word search will contain
	 * columns - number of columns the word search will contain
	 */
	public WordSearch(int rows, int columns, int numberOfWords) {
		
		this.rows = rows;
		this.columns = columns;
		this.numberOfWords = numberOfWords;
		
		loadWords();
		buildWordSearchStructure();
		populateWordSearchWithWords();
		
	}
	
	/*
	 * Load the dictionary of words into an array list.
	 * Select <numberOfWords> random words to place in the word search,
	 * while making sure that the number of words can fit within the 
	 * word search given its boundaries
	 */
	private void loadWords() {
		
		// temporary array list to store the dictionary of words
		ArrayList<String> dictionary = new ArrayList<String>();
		
		// load the dictionary of words into a data structure
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("files/words.txt"));
			String line;
			while((line = br.readLine()) != null) dictionary.add(line);
			br.close();
			
		} catch(Exception e) {
			
			System.out.println(e);
			
		}
		
		SecureRandom r = new SecureRandom();
		
		boolean checkIfWordsCanFit = true;
		while(checkIfWordsCanFit) {
			
			words = new ArrayList<String>();
		
			// select numberOfWords random words from the dictionary
			int chosenWords = 0;
			while(chosenWords < numberOfWords) {
				
				int potentialWordIndex = r.nextInt(dictionary.size());
				
				// remove all - and . from the word
				String potentialWord = dictionary.get(potentialWordIndex).replace("-", "").replace(".", "");
				
				words.add(potentialWord);
			
				numberOfCharacters += dictionary.get(potentialWordIndex).length();
				
				chosenWords += 1;
								
			}
			
			if(numberOfCharacters <= (this.rows * this.columns) / 2 ) {
				
				checkIfWordsCanFit = false;
				
			}
		
		}
		
	}
	
	/*
	 * Create the data structure that will hold the word search
	 */
	private void buildWordSearchStructure() {
		
		// create a new linked list of columns for each row
		for(int i = 0; i < this.rows; i++ ) {
			
			ArrayList<Character> row = new ArrayList<Character>();
			
			// create a new linked list of characters for each column
			for(int j = 0; j < this.columns; j++) {		
				
				Character ch = new Character('0', i, j);
				row.add(ch);
				
			}
			
			this.wordSearchRows.add(row);
			
		}
		
	}
	
	/*
	 * Populate the word search with the selected words in random locations
	 */
	private void populateWordSearchWithWords() {
		
		for(String word : this.words) {
			
			placeWord(word);
			
		}
		
	}
	
	/*
	 * Place an individual word in the word search
	 */
	private void placeWord(String word) {
		
		boolean placed = false;
		int attempt = 0;
		while(!placed && attempt < 10) {
			
			int index = 0;
			
			// determine a random starting point
			SecureRandom sr = new SecureRandom();
			int randomX = sr.nextInt(this.rows);
			int randomY = sr.nextInt(this.columns);
			
			// Determine the direction of the word
			int directionX = 1 - sr.nextInt(2);
			int amplitudeX = sr.nextInt(2);
			int factorX = directionX * amplitudeX;
			
			int directionY = 1 - sr.nextInt(2);
			int amplitudeY = sr.nextInt(2);
			int factorY = directionY * amplitudeY;
			
			// default to left to right
			if(factorX + factorY == 0) {
				factorX = 1;
			}
					
			// make sure the word will fit based on its starting position
			if(randomX + (factorX * word.length()) > this.rows - 1 || 
				randomY + (factorY * word.length()) > this.columns - 1) {
				
				attempt++;
				continue;
				
			}
			
			// make sure there are no words in the path of this word
			boolean collision = false;
			int xTest = randomX;
			int yTest = randomY;
			int iTest = index;
			for(char c : word.toCharArray()) {
				
				ArrayList<Character> row = this.wordSearchRows.get(xTest);
				Character cell = row.get(yTest);
				
				if(cell.getValue() != word.charAt(iTest) && cell.getValue() != '0') {
					
					collision = true;
					break;
					
				}
				
				xTest += factorX;
				yTest += factorY;
				
				iTest++;
				
			}
			
			if(collision) {
				attempt++;
				continue;
			}
			
			// write the word to the word search
			for(char c : word.toCharArray()) {
				
				ArrayList<Character> row = this.wordSearchRows.get(randomX);
				Character cell = row.get(randomY);
				
				cell.setValue(word.toUpperCase().charAt(index));
				
				randomX += factorX;
				randomY += factorY;
				
				index++;
				
			}
			
			placed = true;
			
		}
		
	}
	
	/*
	 * Print the word search to the console
	 */
	public void printWordSearch() {
		
		for(ArrayList<Character> currentRow : this.wordSearchRows) {
			
			for(Character currentColumn : currentRow) {
				
				System.out.print(currentColumn.getValue() + " ");
				
			}
			
			System.out.println();
			
		}
		
	}
	
	/* 
	 * Return the Word Search
	 */
	public ArrayList<ArrayList<Character>> getWordSearch() {
		return this.wordSearchRows;
	}
	
	/*
	 * Return the number of characters in all words in the word search
	 */
	public int numberOfCharactersInAllWords() { 
		return this.numberOfCharacters;
	}
	
	/*
	 * Return the number of words in the word search
	 */
	public int numberOfWords() {
		return this.numberOfWords;
	}
	
	/*
	 * Return a word at a specified index
	 */
	public String getWord(int index) {
		return this.words.get(index);
	}
	
}
