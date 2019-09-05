package code.of.advent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

//10 players; last marble is worth 1618 points: high score is 8317
//13 players; last marble is worth 7999 points: high score is 146373
//17 players; last marble is worth 1104 points: high score is 2764
//21 players; last marble is worth 6111 points: high score is 54718
//30 players; last marble is worth 5807 points: high score is 37305

//464 players; last marble is worth 7 173 000 points: ?????????

public class Day9 {

	public Day9() {
		// TODO Auto-generated constructor stub
	}
	
	
	private class Circle extends LinkedList<Integer> {
		ListIterator<Integer> current;
		
		public Circle() {
			add(0);
			current = listIterator(0);
		}
		
		public void addMarble(int marble) {
			if (size() == 1) {
				current.add(marble);
			}
			else {
				for (int i = 0; i < 1; i++) {
					if (!current.hasNext())
						current = listIterator(0);
					
					current.next();				
				}
				
				current.add(marble);
			}
		}
		
		public long removeMarble() {
			int removed = 0;
			for (int i = 0; i < 8; i++) {
				if (!current.hasPrevious())
					current = listIterator(size());
				
				removed = current.previous();
			}
			
			current.remove();
			current.next();
			
			return removed;
		}
	}

	public static void main(String[] args) {
		Day9 derp = new Day9();
		
		final int numPlayers = 464;
		final int lastMarble = 7173000;
		
		long[] players = new long[numPlayers];
		Circle circle = derp.new Circle();
		
		int nextMarble = 1;
		while (nextMarble <= lastMarble) {
			for (int i = 0; i < players.length; i++) {
				if (nextMarble % 23 == 0) {
					players[i] += nextMarble;
					players[i] += circle.removeMarble();
				}
				else {
					circle.addMarble(nextMarble);
				}
				
				nextMarble++;
				if (nextMarble % 1000000 == 0)
					System.out.println("en miljon");
				
				if (nextMarble > lastMarble)
					break;
				
			}
		}

		long winningScore = 0;
		for (long player : players) {
			if (player > winningScore) {
				winningScore = player;
			}
		}
		
		System.out.println("Highest score: " + winningScore);
	}
}
