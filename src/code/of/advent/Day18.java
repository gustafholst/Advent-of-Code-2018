package code.of.advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day18 {

	private static final int ROWS = 50;
	private static final int COLS = 50;
			
	char[][] current;
	char[][] next;
	
	public Day18() {
		current = new char[ROWS][COLS];
		next = new char[ROWS][COLS];
		readFile();
	}
	
	public int countTrees(int y, int x) {
		int numTrees = 0;
		
		if (y < ROWS-1) {			
			if (current[y+1][x] == '|') numTrees++;
		}
		if (y < ROWS-1 && x > 0) {			
			if (current[y+1][x-1] == '|') numTrees++;
		}
		if (y > 0) {		
			if (current[y-1][x] == '|') numTrees++;			
		}
		if (y > 0 && x < COLS-1) {
			if (current[y-1][x+1] == '|') numTrees++;
		}	
		if (x > 0 && y > 0) {
			if (current[y-1][x-1] == '|') numTrees++;
		}
		if (x < COLS-1 && y < ROWS-1) {
			if (current[y+1][x+1] == '|') numTrees++;
		}
		if (x > 0) {
			if (current[y][x-1] == '|') numTrees++;
		}
		if (x < COLS-1) {
			if (current[y][x+1] == '|') numTrees++;
		}	
		
		return numTrees;
	}
	
	public int countLumberYards(int y, int x) {
		int lumberYards = 0;
		
		if (y < ROWS-1) {			
			if (current[y+1][x] == '#') lumberYards++;
		}
		if (y < ROWS-1 && x > 0) {			
			if (current[y+1][x-1] == '#') lumberYards++;
		}
		if (y > 0) {		
			if (current[y-1][x] == '#') lumberYards++;		
		}
		if (y > 0 && x < COLS-1) {
			if (current[y-1][x+1] == '#') lumberYards++;
		}	
		if (x > 0 && y > 0) {
			if (current[y-1][x-1] == '#') lumberYards++;
		}
		if (x < COLS-1 && y < ROWS-1) {
			if (current[y+1][x+1] == '#') lumberYards++;
		}
		if (x > 0) {
			if (current[y][x-1] == '#') lumberYards++;
		}
		if (x < COLS-1) {
			if (current[y][x+1] == '#') lumberYards++;
		}	
		
		return lumberYards;
	}
	
	public boolean remainsLumberYard(int y, int x) {
		int lumberYards = countLumberYards(y, x);
		int trees = countTrees(y,x);
	
		if (lumberYards > 0 && trees > 0)
			return true;
		
		return false;
	}
	
	public void run() {
		
		// '.' -> '|' if 3 >= '|' adjacent
		// '|' -> '#' if 3 >= '#' adjacent
		// '#' remains if at least 1 '#' and 1 '|' else -> '.'
		
		
		copy(current, next);
		
		int numWoods = 0;
		int numLumber = 0;
		long resources = 0;
		int noChangeCount = 0;
		int count = 0;
		while(count < 640) {
			
			numWoods = 0;
			numLumber = 0;
			
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++) {
					if (current[i][j] == '.') {
						if (countTrees(i,j) >= 3)
							next[i][j] = '|';
					}
					if (current[i][j] == '|') {
						if (countLumberYards(i,j) >= 3)
							next[i][j] = '#';
					}
					if (current[i][j] == '#') {
						if (!remainsLumberYard(i,j))
							next[i][j] = '.';
					}
				}
			}
			
			count++;
			copy(next, current);
			
			
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++) {
					if (current[i][j] == '|') numWoods++;
					if (current[i][j] == '#') numLumber++;
				}
			}
			
					
			if (count > 600   ) { // && numWoods * numLumber > resources) {
				resources = numWoods * numLumber;
				System.out.println("Count: " + count + "\t" + resources);
				
			}
				
		}		
		
		display(current);
		
		
		
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (current[i][j] == '|') numWoods++;
				if (current[i][j] == '#') numLumber++;
			}
		}
		
		System.out.println("Total recourses: " + numWoods * numLumber);
		System.out.println("No change count: " + noChangeCount);
	}
	
	public void readFile() {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("acres.txt"))));
			
			String line;
			int row = 0;
			while ((line = bf.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					current[row][i] = line.charAt(i);
				}
				
				row++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void display(char[][] g) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.print(g[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void copy(char[][] from, char[][] to) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				to[i][j] = from[i][j];
			}
		}
	}
	
	public static void main(String[] args) {
		Day18 day = new Day18();
		
		day.run();

	}

}
