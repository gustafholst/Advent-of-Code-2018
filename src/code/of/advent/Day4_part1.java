package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Day4_part1 {

	static ArrayList<Event> events;
	
	public Day4_part1() {
		
	}
	
	public class Event {
		private String e;
		public Event(String in) {
			e = in;
		}
		
		public String getEvent() {
			return e;

		}
		public String getDateTimeString() {
			return e.substring(1, 17);
		}
		
		public String getGuard() {
			int hash = e.indexOf('#');
			if (hash < 0)
				return null;
			
			return e.substring(hash, e.indexOf(' ', hash));
		}
		
		public int getMinute() {
			return Integer.parseInt(e.substring(15,17));
		}
		
		public int getType() {
			if (e.indexOf("Guard") > 0) 
				return 0;
			else if (e.indexOf("falls") > 0)
				return 1;
			
			return 2;
		}
	}

	public static void main(String[] args) {
		Day4_part1 derp = new Day4_part1();
		events = new ArrayList<Event>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("events.txt"));
			String line = null;
			
			while((line = br.readLine()) != null) {
				Event e = derp.new Event(line);		
				events.add(e);
			}
			
			System.out.println("Number of rows: " + events.size());
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Event e : events)
			System.out.println(e.getEvent());
		
		Collections.sort(events, new Comparator<Day4_part1.Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getDateTimeString().compareTo(o2.getDateTimeString());
			}
			
		});
		
		
		
		
		ArrayList<String[]> days = new ArrayList<String[]>();
		
		
		String[] day = new String[61];
		for (int i = 0; i < events.size(); i++) {
			Event e = events.get(i);
			if (e.getType() == 0) {
				day = new String[61];
				day[60] = e.getGuard();
				for (int j = 0; j < day.length -1; j++)
					day[j] = ".";
			}
			
			String fill = ".";
			if (e.getType() == 1) 
				fill = "#";
			
			if (e.getType() != 0) {
				for (int k = e.getMinute(); k < day.length -1; k++)
					day[k] = fill;
			}
			
			if (i < events.size() -1) {
				if (events.get(i+1).getType() == 0)
					days.add(day);
			}
			
			if (i == events.size()-1)
				days.add(day);
		}
		
		
		
			
		System.out.println("number of days: " + days.size());
		
		HashMap<String, Integer> sleptMins = new HashMap<String, Integer>();
		for (String[] d : days) {
			String key = d[60];
			int count;
			if (sleptMins.containsKey(key)) 
				count = sleptMins.get(key);
			else 
				count = 0;
				
			for (String s : d) {
				if (s.equals("#"))
					count++;
			}
			
			sleptMins.put(key, count);	
		}
		
		int winningCount = 0;
		String winner = "";
		for (String key : sleptMins.keySet()) {
			if (sleptMins.get(key) > winningCount) {
				winner = key;
				winningCount = sleptMins.get(key);
			}
		}
		
		System.out.println("Guard with most slept minutes is " + winner);
		
		int[] minutes = new int[60];
		
		for (String[] d : days ) {
			if (d[60].equals(winner)) {
				for (int a = 0; a < 60; a++) {
					if (d[a].equals("#")) {
						minutes[a]++;
					}
				}
			}	
		}
		
		int winningMin = 0;
		int winningIndex = 0;
		for (int b = 0; b < minutes.length; b++) {
			if (minutes[b] > winningMin) {
				winningMin = minutes[b];
				winningIndex = b;
			}
		}
		
		for (String[] d : days) {
			//if (d[60].equals(winner))
				System.out.println(Arrays.toString(d));
		}
		
		System.out.println(Arrays.toString(minutes));
		
		int guardInt = Integer.parseInt(winner.substring(1));
		System.out.println(guardInt + " x " + winningIndex + " = " + guardInt*winningIndex); 
		
		
		HashMap<String, int[]> allSleptMins = new HashMap<String, int[]>();
		for (String[] d : days) {
			String key = d[60];
			int[] counts = new int[60];
			if (allSleptMins.containsKey(key)) 
				counts = allSleptMins.get(key);
				
			for (int i = 0; i < counts.length; i++) {
				if (d[i].equals("#"))
					counts[i]++;
			}
			
			allSleptMins.put(key, counts);	
		}
		
		int wC = 0;
		String w = "";
		int wi = 0; //winning index
		for (String key : allSleptMins.keySet()) {
			int[] arr = allSleptMins.get(key);
			for (int x = 0; x < arr.length; x++) {
				if (arr[x] > wC) {
					w = key;
					wC = arr[x];
					wi = x;
				}
			}
			
		}
		
		System.out.println("-----PART II------");
		int gInt = Integer.parseInt(w.substring(1));
		System.out.println(gInt + " x " + wi + " = " + gInt*wi); 
	}

}
