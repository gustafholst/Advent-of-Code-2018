package code.of.advent;

import java.io.*;
import java.util.ArrayList;

public class Advent {

	public static void main(String[] args) {		
		int counts[] = new int[123-97];
		int two = 0;
		int three = 0;
			
		for (int i = 0; i < counts.length; i++)
			counts[i] = 0;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.txt"));
			String line = null;
			
			boolean twoscounted = false;
			boolean threescounted = false;
			while ((line = br.readLine()) != null) {
				
				for (int i = 0; i < line.length(); i++) {
					char c = line.charAt(i);
					int index = (int)(c - 97);
					
					counts[index] += 1;
				}
				
				for (int i = 0; i < counts.length; i++) {
					System.out.println((char)(i + 97) + " : " + counts[i]);
				}
				
				for (int n : counts) {
					if (n == 2 && !twoscounted) {
						two++;
						twoscounted = true;
					}
						
					if (n == 3 && !threescounted) {
						three++;		
						threescounted = true;
					}
				}
				
				twoscounted = false;
				threescounted = false;
				
				for (int i = 0; i < counts.length; i++)
					counts[i] = 0;
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i = 0; i < counts.length; i++) {
			System.out.println((char)(i + 97) + " : " + counts[i]);
		}
			
		System.out.println("Checksum is: " + two + " * " + three + " = " + two * three);
	}

}
