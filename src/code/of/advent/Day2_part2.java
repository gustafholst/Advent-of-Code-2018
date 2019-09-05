package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2_part2 {

	public static void main(String[] args) {
		
		ArrayList<String> rows = new ArrayList<String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			String line = null;
			
			while((line = br.readLine()) != null) {
				rows.add(line);
			}
			
			System.out.println("Number of rows: " + rows.size());
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		char c1 = ' ';
		char c2 = ' ';
		boolean found = false;
		int A = 0;
		int B = 0;
		int i = 0;
		for (int a = 0; a < rows.size() - 1; a++) {
			for (int b = a + 1; b < rows.size(); b++) {
				i = compareRows(rows.get(a), rows.get(b));
				if (i != -1) {
					c1 = rows.get(a).charAt(i);
					c2 = rows.get(b).charAt(i);
					A = a;
					B = b;
					found = true;
				}
				
				if (found)
					break;
				
			}
			if (found)
				break;
		}
			
		System.out.println("The common letters are: " + rows.get(A).substring(0, i) + rows.get(A).substring(i+1));
		System.out.println("Lines: " + A + " and " + B );
		System.out.println("letter number: " + i);
	}
	
	public static int compareRows(String a, String b) {
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				if (areEqualBeforeAndAfter(a,b,i)) {
					return i;
				}
			}	
		}
		return -1;
	}
	
	public static boolean areEqualBeforeAndAfter(String a, String b, int index) {
		return a.substring(0, index).equals(b.substring(0, index)) &&
				a.substring(index + 1).equals(b.substring(index + 1));
	}

}


//qyszphxoiseldjrntfygvdmanu
//qywzphxoiseldjrntfygvdmanu