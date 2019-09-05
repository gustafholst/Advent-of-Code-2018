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
import java.util.Scanner;

public class Day13 {

	private final static int ROWS = 150;
	private final static int COLS = 150;
	
	private static ArrayList<Cart> carts;
	
	private static char[][] nextGrid;
	private static char[][] grid;
	private static char[][] tracks;
	
	private static Direction up;
	private static Direction down;
	private static Direction left;
	private static Direction right;
	
	public Day13() {
		// TODO Auto-generated constructor stub
	}
	
	private class Cart {
		private int xPos;
		private int yPos;
		private int numTurns;
		private Direction dir;
		
 		public Cart(int x, int y, Direction direction) {
			xPos = x;
			yPos = y;
			numTurns = 0;
			dir = direction;
		}
		
		public void move() {		
			grid[yPos][xPos] = tracks[yPos][xPos];
			xPos += dir.x;
			yPos += dir.y;
			char track = tracks[yPos][xPos];
			Direction heading = determineHeading(track, dir, this);
			grid[yPos][xPos] = getNextChar(heading);
			dir = heading;
		}
		
		public int getNextX() {
			return xPos + dir.x;
		}
		
		public int getNextY() {
			return yPos + dir.y;
		}
		
		public boolean aboutToCrash(Cart other) {
			if (this.getNextX() == other.getNextX()
				&& this.getNextY() == other.getNextY()) {
					System.out.println("regular");
					return true;
				}
			
			if (this.xPos == other.xPos && this.yPos > other.yPos) {
				if (this.dir == up && other.dir == down && Math.abs(this.yPos - other.yPos) < 2) {
					System.out.println("simpel1");
					return true;
				}
			}
			
			if (this.yPos == other.yPos && this.xPos > other.xPos) {
				if (this.dir == left && other.dir == right && Math.abs(this.xPos - other.xPos) < 2) {
					System.out.println("simpel2");
					return true;
				}
			}
			
			return false;
		}
		
	}
	
	private class Direction {
		public int x, y;
		
		public Direction(int _x, int _y) {
			x = _x;
			y = _y;
		}
		
	}

	private static char getNextChar(Direction d) {
		if (d == up)
			return '^';
		if (d == down)
			return 'v';
		if (d == left)
			return '<';
		if (d == right)
			return '>';
		
		return 'r';  //check for bugs
	}
	
	private static Direction getDirection(char c) {
		Direction dir = null;
		Day13 d = null;
		
		switch (c) {
		case '^':
			dir = up;
			break;
		case 'v':
			dir = down;
			break;
		case '<':
			dir = left;
			break;
		case '>':
			dir = right;
			break;
		default:
		}
		
		return dir;
	}
	
	private static Direction getTurn(Direction d, int turns) {
		if (d == up) {
			if (turns == 2)
				return right;
			if (turns == 1)
				return up;
			
			return left;
		}
		if (d == down) {
			if (turns == 2)
				return left;
			if (turns == 1)
				return down;
			
			return right;
		}
		if (d == left) {
			if (turns == 2)
				return up;
			if (turns == 1)
				return left;
			
			return down;
		}
		if (d == right) {
			if (turns == 2)
				return down;
			if (turns == 1)
				return right;
			
			return up;
		}
		return null;
	}
	
	private static Direction determineHeading(char c, Direction d, Cart cart) {
		Day13 day = new Day13();
		Direction dir = day.new Direction(0,0);
		
		switch (c) {
		case '|':
			dir = d;
			break;
		case '-':
			dir = d;
			break;
		case '\\':
			if (d == right)   //from right or left
				dir = down;
			if (d == up)   //from up or down
				dir = left;
			if (d == left)
				dir = up;
			if (d == down)
				dir = right;
			break;
		case '/':
			if (d == right)   //from right or left
				dir = up;
			if (d == up)   //from up or down
				dir = right;
			if (d == left)
				dir = down;
			if (d == down)
				dir = left;
			break;
		case '+':
			dir = getTurn(d, cart.numTurns);
			cart.numTurns = (cart.numTurns + 1) % 3;
			break;
		default:
		}
		
		return dir;
	}
	
	private static void printGrid(char[][] g) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.print(g[i][j]);
			}
			System.out.println();
		}
	}
	
	private static Cart getCart(int x, int y) {
		for (Cart c : carts) {
			if (c.xPos == x && c.yPos == y)
				return c;
		}
		
		return null;
	}
	
	private static void copyGrid(char[][] from, char[][] to) {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				to[i][j] = from[i][j];
			}
		}
	}
	
	public static void main(String[] args) {
		Day13 derp = new Day13();
		
		up = derp.new Direction(0, -1);
		down = derp.new Direction(0, 1);
		left = derp.new Direction(-1, 0);
		right = derp.new Direction(1, 0);
		
		grid = new char[ROWS][COLS];
		tracks = new char[ROWS][COLS];
		nextGrid = new char[ROWS][COLS];
		
		carts = new ArrayList<Cart>();
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("tracks.txt"))));
					
			int row = 0;
			String line;
			while((line = in.readLine()) != null) {
				int col = 0;
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					
					if (c == '^' || c == 'v' || c == '<' || c == '>') {
						carts.add(derp.new Cart(col, row, getDirection(c)));
					}
					
					if (c == '^' || c == 'v') 
						tracks[row][col] = '|';					
					else if (c == '<' || c == '>') 
						tracks[row][col] = '-';
					else
						tracks[row][col] = c;	
							
					grid[row][col] = c;
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
		

		int lastCartX = 0;
		int lastCartY = 0;

		boolean done = false;
		
		Scanner scanner = new Scanner(System.in);
		while (carts.size() > 1) {
			//printGrid(grid);
			//copyGrid(grid, nextGrid);
			
			Collections.sort(carts, new Comparator<Cart>() {

				@Override
				public int compare(Cart c1, Cart c2) {
					
					if (c1.yPos == c2.yPos) {
						if (c1.xPos < c2.xPos) {
							return  -1;
						}
						else if (c2.xPos < c1.xPos) {
							return 1;
						}
					}
					
					if (c1.yPos < c2.yPos) {
						return  -1;
					}	
					
					if (c2.yPos < c1.yPos) {
						return 1;
					}
					
					return 0;
				}
				
			});
			
			ArrayList<Cart> remove = new ArrayList<Cart>();					
			for (Cart cart : carts) {
				for (Cart other : carts) {
					if (cart != other && cart.aboutToCrash(other)) {
						remove.add(cart);
						remove.add(other);
						
						printGrid(grid);
						System.out.println(cart.xPos + ", " + cart.yPos + " and " + other.xPos + ", " + other.yPos );
						scanner.nextLine();
						
						grid[cart.yPos][cart.xPos] = tracks[cart.yPos][cart.xPos];
						grid[other.yPos][other.xPos] = tracks[other.yPos][other.xPos];
					}
				}				
			}
			
			for (Cart r : remove)
				carts.remove(r);
			
			for (Cart cart : carts) {
				int nextX = cart.getNextX();
				int nextY = cart.getNextY();
				if (grid[nextY][nextX] == '>' || grid[nextY][nextX] == '<' ||
						grid[nextY][nextX] == '^' || grid[nextY][nextX] == 'v') {
					Cart other = getCart(nextX, nextY);
					remove.add(cart);
					remove.add(other);
					
					System.out.println("crash!");					
					printGrid(grid);
					System.out.println(cart.xPos + ", " + cart.yPos + " and " + other.xPos + ", " + other.yPos );
					scanner.nextLine();
					
					grid[cart.yPos][cart.xPos] = tracks[cart.yPos][cart.xPos];
					grid[other.yPos][other.xPos] = tracks[other.yPos][other.xPos];		
				}
				else {
					cart.move();
				}
			}
			
		
			
			//copyGrid(nextGrid, grid);
			//scanner.nextLine();
		}
		
		System.out.println("----------- Part 2 -----------");
		
		printGrid(grid);
		
		System.out.println("last cart: " + carts.get(0).xPos + ", " + carts.get(0).yPos);
	}

}
