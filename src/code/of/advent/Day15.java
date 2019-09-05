package code.of.advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class Day15 {

	public static Spot[][] grid;
	final static int ROWS = 32;
	final static int COLS = 32;
	private static int elfAttackPower = 2;
	private static int numElves = 0;
	
	private static ArrayList<Unit> units;
			
	public Day15() {}
	
	private static void showGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				Spot s = grid[i][j];
				if (s.wall)
					System.out.print('#');
				else if (s.getContent() != null) {
					System.out.print(s.getContent().getType());
				}
				else {
					System.out.print('.');
				}
			}
			
			for (Unit u : units) {
				
				if (u.getCurrent().getRow() == i && !u.isDead()) {
					System.out.print('\t');
					System.out.print(u.getType() + "(" + u.getHp() + ")");
				}
			}
			System.out.println();
		}
	}
	
	private void localizeNeighbours() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j].findNeighbours(grid);
			}
		}
	}
	
	private void sortUnits() {
		Collections.sort(units, new UnitReadingOrder());
	}
	
	public class UnitReadingOrder implements Comparator<Unit> {
		
		@Override
		public int compare(Unit u1, Unit u2) {
			Spot s1 = u1.getCurrent();
			Spot s2 = u2.getCurrent();
			if (s1.getRow() < s2.getRow())  return -1;					
			if (s1.getRow() > s2.getRow())  return 1;
			if (s1.getCol() < s2.getCol())  return -1;
			if (s1.getCol() > s2.getCol())  return 1;
			return 0;
		}
	}
	
	private int run() {
			
		showGrid();		
		
		boolean battleOver = false;
		int round = 0;
		while(!battleOver) {
			sortUnits();
			System.out.println("Round: " + ++round);
			for (Unit u : units) {
				if (u.isDead()) continue;
				
				if (u.canAttack()) {
					Unit enemy = u.attack();
				}
				else {
					battleOver = u.determineNextStep(units);	
					u.move();
					if (u.canAttack()) {
						Unit enemy = u.attack();
					}
				}
			}		
			showGrid();			
		}
		
		//calculate
		int sum = 0;
		int aliveElves = 0;
		for (Unit u : units) {
			if (!u.isDead()) {
				sum += u.getHp();
				
				if(u.getType() == 'E')
					aliveElves++;
			}
		}
		
		System.out.println("Outcome: " + (round-1) * sum);
		return aliveElves;
	}
	
	public int distance(Spot a, Spot b) {
		return Math.abs(a.getRow() - b.getRow()) + Math.abs(a.getCol() - b.getCol());
	}
	
	public static void resetValues() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j].setG(Integer.MAX_VALUE);
				grid[i][j].setF(Integer.MAX_VALUE);
				grid[i][j].setH(0);
			}
		}
	}
	
	private void readFromFile() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("cave.txt"))));
					
			numElves = 0;
			int row = 0;
			String line;
			while((line = in.readLine()) != null) {
				int col = 0;
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					
					Spot spot;
					
					if (c == 'G') {
						spot = new Spot(row, col, false);
						Unit unit = new Unit(c, spot, this, 3);
						units.add(unit);
						spot.setContent(unit);
					}
					
					else if (c == 'E') {
						spot = new Spot(row, col, false);
						Unit unit = new Unit(c, spot, this, elfAttackPower);
						units.add(unit);
						spot.setContent(unit);
						numElves++;
					}
					
					else if (c == '.') {
						spot = new Spot(row,col, false);
					}
					else {
						spot = new Spot(row ,col, true);
					}
							
					grid[row][col] = spot;
					col++;
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

	public static void main(String[] args) {
		Day15 day = new Day15();
		
		int elves = 0;
		do {
			grid = new Spot[ROWS][COLS];
			units = new ArrayList<Unit>();
			
			elfAttackPower++;
			
			day.readFromFile();			
			day.localizeNeighbours();
			
			elves = day.run();
			
		} while (elves < numElves);

		System.out.println("Attack power: " + elfAttackPower);
	}

}
