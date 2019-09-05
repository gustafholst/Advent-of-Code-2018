package code.of.advent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Day5 {
	
	private static ArrayList<Character> polymer;
	
	public Day5() {
		// TODO Auto-generated constructor stub
	}

	private static boolean reacts(char a, char b) {
		String A = "" + a;
		String B = "" + b;
		
		if (!A.equals(B) && A.toLowerCase().equals(B.toLowerCase()))
			return true;
		
		return false;
	}
	
	private static void printPolymer(ArrayList<Character> a) {
		for (char c : a) {
			System.out.print(c);
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		
		polymer = new ArrayList<Character>();
		
		try {
			//BufferedReader br = new BufferedReader(new FileReader());
			InputStream in = new FileInputStream("polymer.txt");
			int c = in.read();
			while(c != -1) {
				polymer.add((char)c);
				c = in.read();
			}
			
			System.out.println("Number of chars: " + polymer.size());
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Character> original = (ArrayList<Character>) polymer.clone();
		
		ArrayList<Character> alphabet = new ArrayList<Character>();
		for (int i = 65; i < 91; i++)
			alphabet.add((char)i);
		
		ArrayList<Character> del = new ArrayList<Character>();
		del.add('|');
		
		for (int i = 0; i < alphabet.size(); i++) {
			ArrayList<Character> removed = new ArrayList<Character>();
			removed.add(alphabet.get(i));
			removed.add((char)(alphabet.get(i) + 32));
			printPolymer(removed);
			
			polymer = (ArrayList<Character>) original.clone();
			polymer.removeAll(removed);
			
			boolean more = true;
			while (more) {
				//printPolymer(polymer);
				
				ListIterator<Character> it = polymer.listIterator();
				more = false;
				while (it.hasNext()) {
					
					char c = it.next();	
					
					if (!it.hasNext())
						break;
					
					char following = polymer.get(it.nextIndex());
					
					if (reacts(c, following)) {
						it.set('|');
						it.next();
						it.set('|');
						more = true;
					}
				}
				
				polymer.removeAll(del);
			}
			
			System.out.println("Number of chars after: " + polymer.size());
		}
		
	}

}
