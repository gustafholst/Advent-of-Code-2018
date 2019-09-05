package code.of.advent;

import java.util.ArrayList;
import java.util.ListIterator;

public class Day14 {

	private static ArrayList<Integer> scoreboard;
	
			
	public Day14() {
		// TODO Auto-generated constructor stub
	}
	
	private class ScoreBoard extends ArrayList<Integer> {
		ListIterator<Integer> elf1;
		ListIterator<Integer> elf2;
		
		int e1;
		int e2;
		
		public ScoreBoard() {
			super.add(3);
			super.add(7);
			//elf1 = listIterator(0);
			//elf2 = listIterator(1);
			e1 = 0;
			e2 = 1;
		}
		
		public void generate() {
			int num1 = get(e1);
			int num2 = get(e2);
			
			int sum = num1 + num2;
			
			if (sum > 9) {
				add(sum/10);
				add(sum%10);
			}
			else {
				add(sum);
			}
		}
		
		public void moveElf1() {
			int steps = get(e1) + 1;
			
			for (int i = 0; i < steps; i++) {
				if (e1 < size()-1)
					e1++;
				else
					e1 = 0;
			}
		}
		
		public void moveElf2() {
			int steps = get(e2) + 1;
			
			for (int i = 0; i < steps; i++) {
				if (e2 < size()-1)
					e2++;
				else
					e2 = 0;
			}
		}
		
		public int getElf1() {
			return get(e1);
		}
		
		public int getElf2() {
			return get(e2);
		}
		
		public void printBoard() {
			for (int i = 0; i < size(); i++) {
				if (i == e1)
					System.out.print("(" + get(i) + ")");
				else if (i == e2)
					System.out.print("[" + get(i) + "]");
				else
					System.out.print(get(i) + " ");
			}
			System.out.println();
		}
		
		public void printNext10(int numRec) {
			for (int i = numRec; i < numRec+10; i++) {		
				System.out.print(get(i));
			}
			System.out.println();
		}
	}

	
	
	public static void main(String[] args) {
		Day14 day = new Day14();
		
		ScoreBoard scoreboard = day.new ScoreBoard();
		
		final int numRecipes = 323081;
		String searched = "" + numRecipes;
		
		boolean found = false;
		int foundIndex = 0;
		
		int current = 0;
	
		while (!found) {
			scoreboard.generate();
			scoreboard.moveElf1();
			scoreboard.moveElf2();
			
			for (int i = current; i < scoreboard.size() - searched.length(); i++) {
				StringBuilder build = new StringBuilder();
				for (int j = i; j < i + searched.length(); j++) {				
					build.append(scoreboard.get(j));
				}
				String sample = build.toString();
				
				if (sample.equals(searched)) {
					found = true;
					foundIndex = i;
				}
				current ++;
			}
			//scoreboard.printBoard();		
		}
		
		System.out.println("Searched recipes appear after " + foundIndex + " recipes.");
		//scoreboard.printNext10(numRecipes);
	}

}
