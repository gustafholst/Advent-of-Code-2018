package code.of.advent;

import java.util.ArrayList;

public class Spot {
	
	public int row;
	public int col;
	
	public int f,g,h;
	
	Unit content;
	
	boolean wall;
	
	Spot parent;
	ArrayList<Spot> neighbours;

	public Spot(int row, int col, boolean wall) {
		this.col = col;
		this.row = row;
		this.wall = wall;
		f = Integer.MAX_VALUE;
		g = Integer.MAX_VALUE;
	}
	
	public int dist(Spot other) {
		return Math.abs(this.getRow() - other.getRow()) + Math.abs(this.getCol() - other.getCol());
	}
	
	public void calculateF() {
		f = h + g;
	}
	
	public void calculateH(Spot goal) {
		this.h = dist(goal);
	}
	
	public void findNeighbours(Spot[][] grid) {
		if (wall)
			return;
		
		neighbours = new ArrayList<Spot>();
	
		if (!grid[row][col+1].wall)
			neighbours.add(grid[row][col+1]);
		if (!grid[row+1][col].wall)
			neighbours.add(grid[row+1][col]);
		if (!grid[row][col-1].wall)
			neighbours.add(grid[row][col-1]);
		if (!grid[row-1][col].wall)
			neighbours.add(grid[row-1][col]);	
	}

	public int getRow() {
		return row;
	}

	public void setRow(int r) {
		this.row = r;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int c) {
		this.col = c;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Unit getContent() {
		return content;
	}

	public void setContent(Unit content) {
		this.content = content;
	}

	public Spot getParent() {
		return parent;
	}

	public void setParent(Spot parent) {
		this.parent = parent;
	}

	public ArrayList<Spot> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayList<Spot> neighbours) {
		this.neighbours = neighbours;
	}

}
