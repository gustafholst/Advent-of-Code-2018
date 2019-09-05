package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day6 {

	private static ArrayList<Coordinate> coordinates;
	private static Coordinate[][] grid;
	
	private final static int ROWS = 400;
	private final static int COLS = 400;
	
	public Day6() {
		// TODO Auto-generated constructor stub
	}
	
	private class Coordinate {
		private String xy;
		private String name = "";
		
		public Coordinate(String c, String _name) {
			xy = c;	
			name = _name;
		}
		
		public Coordinate(String c) {
			xy = c;	
			name = ".";
		}
		
		public void setName(String _name) {
			name = _name;
		}
		
		int getX() {
			return Integer.parseInt((xy.substring(0, xy.indexOf(','))));
		}
		
		int getY() {
			return Integer.parseInt(xy.substring(xy.indexOf(',') + 2));
		}
		
		String getName() {
			return name;
		}
		
		String getLowerName() {
			return name.toLowerCase();
		}
		
		int getDistance(Coordinate other) {
			int distX = Math.abs(getX() - other.getX());
			int distY = Math.abs(getY()- other.getY());
			return distX + distY;
		}
	}
	
	private static void markCoordinates() {
		for (Coordinate c : coordinates) {
			grid[c.getX()][c.getY()] = c;
		}
	}
	
	private static void printGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.printf("%s", grid[j][i].getName());  //obs col, row
			}
			System.out.println();
		}
	}
	
	private static String determineChar(Coordinate thisCoord) {
		int smallestDist = 100000;
		String theChar = "";
		for (Coordinate c : coordinates) {
			if (thisCoord.getDistance(c) < smallestDist) {
				theChar = c.getLowerName();
				smallestDist = thisCoord.getDistance(c);
			}
			else if (thisCoord.getDistance(c) == smallestDist) {
				theChar = ".";
			}		
		}
		return theChar;
	}
	
	private static boolean isFinite(Coordinate coord) {
		boolean finite = true;
		for (int i = 0 ; i < ROWS; i++) {
			if (grid[0][i].getLowerName().equals(coord.getLowerName()))
				finite = false;
			if (grid[ROWS-1][i].getLowerName().equals(coord.getLowerName()))
				finite = false;
		}
		
		for (int j = 0 ; j < COLS; j++) {
			if (grid[j][0].getLowerName().equals(coord.getLowerName()))
				finite = false;
			if (grid[j][COLS-1].getLowerName().equals(coord.getLowerName()))
				finite = false;
		}
		
		return finite;
	}

	public static void main(String[] args) {
		Day6 derp = new Day6();
		coordinates = new ArrayList<Coordinate>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("coordinates.txt"));
			String line = null;
			int count = 0;
			while((line = br.readLine()) != null) {	
				String coordName = "";
				char letter = (char)(count % 26 + 65);
				coordName += letter ;
				if (count > 26)
					coordName += letter;
				Coordinate c = derp.new Coordinate(line, coordName);		
				coordinates.add(c);
				count = count + 1;
			}
			
			System.out.println("Number of rows: " + coordinates.size());
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		grid = new Coordinate[ROWS][COLS];
		
		markCoordinates();
		
		
		//
		String theChar;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				if (grid[i][j] != null)
					continue;
				
				Coordinate c = derp.new Coordinate(i + ", " + j);
				theChar = determineChar(c);
				c.setName(theChar);
				grid[i][j] = c;
			}
		}
		
		//printGrid();
		
		int largestArea = 0;
		Coordinate winningCoord = null;
		for (Coordinate c : coordinates) {
			if (!isFinite(c))
				continue;
			
			int area = 0;
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++) {
					if (grid[i][j].getLowerName().equals(c.getLowerName()))
						area++;
				}
			}
			
			if (area > largestArea) {
				largestArea = area;
				winningCoord = c;
			}
		}
		
		System.out.println("Largest area is " + largestArea);
		System.out.println("Coordinate name: " + winningCoord.getName());
		
		System.out.println("----Part 2------");
		
		int safeCount = 0;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				int sum = 0;
				for (Coordinate c : coordinates) {
					sum += grid[i][j].getDistance(c);
				}
				
				if (sum < 10000)
					safeCount++;
			}
		}
			
		System.out.println("Safe are is of size: " + safeCount);
		
	}

}
