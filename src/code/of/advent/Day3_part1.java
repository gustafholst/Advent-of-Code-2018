package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day3_part1 {
	
	static String fabric[][];
	static ArrayList<Claim> claims;

	public Day3_part1() {

	}
	
	class Claim {	
		private String name;
		private int width;
		private int height;
		private int x;
		private int y;
		private boolean overlaps;
		
		public Claim() {
			overlaps = false;
		}
		public void setOverlaps() {
			overlaps = true;
		}
		public boolean isOverlapping() {
			return overlaps;
		}
		public String getName() {
			return name;
		}
		public int getWidth() {
			return width;
		}
		public int getHeight() {
			return height;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		
		public Claim readClaim(String str) {
			int at = str.indexOf('@');
			name = str.substring(0, at).trim();
			int comma = str.indexOf(',');
			int colon = str.indexOf(':');
			int ex = str.indexOf('x');
			x = Integer.parseInt(str.substring(at+2, comma));
			y = Integer.parseInt(str.substring(comma+1, colon));
			width = Integer.parseInt(str.substring(colon+2, ex));
			height = Integer.parseInt(str.substring(ex+1));
			
			return this;
		}
	}
	
	public static Claim findClaim(String id) {
		for (Claim c : claims) {
			if (c.getName().equals(id)) {
				return c;
			}
		}
		return null;
	}
	
	public static void drawClaim(Claim cl) {
		for (int i = cl.getX(); i < cl.getX() + cl.getWidth(); i++) {
			for (int j = cl.getY(); j < cl.getY() + cl.getHeight(); j++) {	
				if (fabric[i][j] == null) {
					fabric[i][j] = cl.getName();
				}
				else {
					//fabric[i][j] = "X";   //for part 1
					String id = fabric[i][j];
					Claim oldClaim = findClaim(id);
					if (oldClaim != null) {
						oldClaim.setOverlaps();
					}
					fabric[i][j] = cl.getName();
					cl.setOverlaps();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		claims = new ArrayList<Claim>();
		
		fabric = new String[1000][1000];
		
		Day3_part1 day = new Day3_part1();
		try {
			BufferedReader br = new BufferedReader(new FileReader("claims.txt"));
			String line = null;
			
			while((line = br.readLine()) != null) {
				Claim c = day.new Claim();
				c.readClaim(line);
				claims.add(c);
			}
			
			System.out.println("Number of rows: " + claims.size());
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Claim c : claims)
			drawClaim(c);

		int count = 0;
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {		
				if (fabric[i][j] != null) {
					if (fabric[i][j].equals("X")) {
						count++;
					}
				}			
			}	
		}
		
		System.out.println("Number of overlappping inches: " + count);
		
		for (Claim c : claims) {
			if (!c.isOverlapping())
				System.out.println("Claim with id " + c.getName() + " is not overlapping!");
		}
	}

}

