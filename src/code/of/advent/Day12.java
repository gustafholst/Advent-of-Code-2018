package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12 {

	public Day12() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ArrayList<String> rules = new ArrayList<String>(32);
		final int offset = 2;
		String initialState = "";
		String currentState = "";
		
		Day10 derp = new Day10();
		try {
			BufferedReader br = new BufferedReader(new FileReader("pots.txt"));
			String line = null;
			
			initialState = br.readLine();
			initialState = initialState.substring(initialState.indexOf(':') + 2);
			
			while((line = br.readLine()) != null) {
				if (line.equals("") || line.endsWith("."))
					continue;
				
				rules.add(line.substring(0, 5));
			}
			
			System.out.println( initialState);
			System.out.println("Number of pots: " + initialState.length());
			System.out.println("Number of rules: " + rules.size());
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//for (String s : rules)
		//	System.out.println(s);
		
		int addLength = 200;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < addLength; i++)
			sb.append('.');
		
		String addition = sb.toString();
		
		currentState = addition + initialState + addition;
		
		long generations = 200;
		
		System.out.print("Generation: " + 0 + "\tFirst: " + currentState.indexOf('#') + "Last: " + currentState.lastIndexOf('#'));
		System.out.println("Length: " + (currentState.lastIndexOf('#') - currentState.indexOf('#')));
		
		StringBuilder build;
		
		for (long i = 0L; i < generations; i++) {
			build = new StringBuilder();
			
			for (int k = 0; k < offset; k++)
				build.append('.');
			
			for (int j = 0; j < currentState.length() - 4; j++) {
						
				String check = currentState.substring(j, j + 5);
				char add = '.';
				for (String rule : rules) {
					if (check.equals(rule))
						add = '#';
				}
				
				build.append(add);
			}
			
			for (int k = 0; k < offset; k++)
				build.append('.');
			
			currentState = build.toString();
			System.out.print("Generation: " + (i + 1) + "\tFirst: " + currentState.indexOf('#') + "Last: " + currentState.lastIndexOf('#'));
			System.out.println("Length: " + (currentState.lastIndexOf('#') - currentState.indexOf('#')));
			//System.out.println(currentState);
		}
		
		int sum = 0;
		for (int index = 0; index < currentState.length(); index++) {
			if (currentState.charAt(index) == '#')
				sum += index - addition.length();
		}
		
		
		
		System.out.println("Sum: " + sum);
		
		
		
		int begin = currentState.indexOf('#');
		int end =  currentState.lastIndexOf('#');
		String template = currentState.substring(begin, end + 1);
		
		System.out.println("Begin: " + begin + "\tEnd: " + end);
		System.out.println(template);
		
		long bigSum = 0L;
		for (int index = 0; index < currentState.length(); index++) {
			if (currentState.charAt(index) == '#')
				bigSum += index - addition.length() + 49999999800L;  //50 000 000 000 - (the calculated) 200 (generations)
		}
		
		System.out.println("Big sum: " + bigSum);
	}

}
