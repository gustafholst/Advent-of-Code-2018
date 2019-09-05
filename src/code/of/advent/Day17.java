package code.of.advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day17 {
	
	final static int WIDTH = 2000;
	final static int HEIGHT = 2000;
	
	int minW;
	int maxW;
	int minH;
	int maxH;
	
	final int sourceX = 500;
	final int sourceY = 0;
	
	char[][] scan;

	
	boolean settled;
	boolean dripping;
	boolean done;
	
	int currentX;
	int currentY;
	int entryX;

	public Day17() {
		scan = new char[HEIGHT][WIDTH];
		fillScan();
		minW = Integer.MAX_VALUE;
		maxW = 0;
		minH = Integer.MAX_VALUE;
		maxH = 0;
		readFile();
	}
	
	public boolean containedLeft(int row, int col) {
		while (scan[row][col-1] != '#') {
			if (scan[row+1][col] == '.')
				return false;

			col--;
		}
		return true;
	}
	
	public boolean saturatedRight(int row, int col) {
		while (scan[row][col+1] != '#') {
			if (scan[row+1][col] == '.')
				return false;

			col++;
		}
		return true;
	}
		
	public boolean containedRight(int row, int col) {
		while (scan[row][col+1] != '#') {
			if (scan[row+1][col] == '.')
				return false;

			col++;
		}
		return true;
	}
	
	public boolean rowFilled(int row, int col) {
		while (scan[row][col+1] != '#') {
			if (scan[row][col+1] == '.')
				return false;
			
			col++;
		}
		return true;
	}
	
	public boolean filledStandingWater(int row, int col) {
		while (scan[row][col+1] != '#') {
			if (scan[row][col+1] != '|')
				return false;
			if (scan[row][col+1] != '~')
				return false;
			
			col++;
		}
		return true;
	}
	
	public void feedWater() {
		settled = false;
		dripping = false;
		done = false;
		currentX = sourceX;
		currentY = sourceY;
		
		while (!(settled || dripping || done)) {
			//move down
			while(currentY <= maxH && !(settled || dripping) && scan[currentY+1][currentX] == '.') {	
				if (currentY == maxH) {
					scan[currentY][currentX] = '|';
					dripping = true;
					break;
				}
				currentY++;			
			}
					
			//move left
			if (scan[currentY][currentX-1] == '.') {
				while (!(settled || dripping) && scan[currentY][currentX-1] == '.' && 
						(scan[currentY+1][currentX] == '#' || scan[currentY+1][currentX] == '~')) {
					
					currentX--;			
				}	
			}
			//move right
			else if (scan[currentY][currentX+1] == '.') {
				while (!(settled || dripping) && scan[currentY][currentX+1] == '.' && 
						(scan[currentY+1][currentX] == '#' || scan[currentY+1][currentX] == '~')) {
					
					currentX++;
				}	
			}
			
			
			if (scan[currentY+1][currentX] != '.') {
				//at position
				if (scan[currentY+1][currentX] == '|' || scan[currentY][currentX+1] == '|' || scan[currentY][currentX-1] == '|') {
					scan[currentY][currentX] = '|';
					
					int tempX = currentX;
					while(scan[currentY][tempX-1] == '~') {
						scan[currentY][tempX-1] = '|';
						tempX--;
					}
					tempX = currentX;
					while(scan[currentY][tempX+1] == '~') {
						scan[currentY][tempX+1] = '|';
						tempX++;
					}
					dripping = true;
				}
				else {
					scan[currentY][currentX] = '~';
					settled = true;
				}
			}
		}
	}
	
	public void run() {
		
		int count = 0;
		while (scan[sourceY+1][sourceX] != '|') {
			
		//	display();
			
			feedWater();	
			
			count++;
		}
		
		display();
			
		System.out.println("Amount of water: " + countWater());
		
		System.out.println("Amount of settled water: " + countSettledWater());
	}
	
	public int countWater() {
		int numWater = 0;
		for (int y = minH; y <= maxH; y++) {
			for (int x = 0; x <= WIDTH-1; x++) {
				if (scan[y][x] == '~' || scan[y][x] == '|')
					numWater++;
			}
		}
		return numWater;
	}
	
	public int countSettledWater() {
		int numWater = 0;
		for (int y = minH; y <= maxH; y++) {
			for (int x = 0; x <= WIDTH-1; x++) {
				if (scan[y][x] == '~')
					numWater++;
			}
		}
		return numWater;
	}
	
	public void readFile() {
		
		try {
			BufferedReader inFile = new BufferedReader(new InputStreamReader(new FileInputStream(new File("scan.txt"))));
			
			String line;
			while ((line = inFile.readLine()) != null) {
				
				if (line.startsWith("y=")) {
					int row;
					int colBegin;
					int colEnd;
					
					row = Integer.parseInt(line.substring(line.indexOf('=') + 1, line.indexOf(',')));
					colBegin = Integer.parseInt(line.substring(line.lastIndexOf('=') + 1, line.indexOf('.')));
					colEnd = Integer.parseInt(line.substring(line.lastIndexOf('.') + 1));
									
					for (int x = colBegin; x <= colEnd; x++) {
						scan[row][x] = '#';
					}
					
					if (row < minH)
						minH = row;
					
					if (colBegin < minW)
						minW = colBegin;
					
					if (colEnd > maxW)
						maxW = colEnd;
						
				}
				if (line.startsWith("x=")) {					
					int col;
					int rowBegin;
					int rowEnd;
					
					col = Integer.parseInt(line.substring(line.indexOf('=') + 1, line.indexOf(',')));
					rowBegin = Integer.parseInt(line.substring(line.lastIndexOf('=') + 1, line.indexOf('.')));
					rowEnd = Integer.parseInt(line.substring(line.lastIndexOf('.') + 1));
					
					for (int y = rowBegin; y <= rowEnd; y++) {
						scan[y][col] = '#';

					}
					
					if (col < minW)
						minW = col;
					
					if (col > maxW)
						maxW = col;
					
					if (rowBegin < minH)
						minH = rowBegin;
					
					if (rowEnd > maxH)
						maxH = rowEnd;
				}
			}
			
			inFile.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void display() {
		for (int y = minH; y <= maxH; y++) {
			for (int x = minW; x <= maxW; x++) {
				System.out.print(scan[y][x]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void fillScan() {
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				scan[y][x] = '.';
			}
		}
		scan[sourceY][sourceX] = '+';
	}
	
	public static void main(String[] args) {
		Day17 day = new Day17();

		day.run();

	}

}
