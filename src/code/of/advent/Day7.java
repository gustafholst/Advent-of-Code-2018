package code.of.advent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Day7 {

	private static ArrayList<Node> nodes;
	
	public Day7() {
		// TODO Auto-generated constructor stub
	}
	
	private class Worker {
		private Node current;
		
		public Worker() {
			current = null;
		}
		
		public void startWorking(Node n) {
			current = n;
			n.setBeingWorkedOn();
		}
		
		public boolean isIdle() {
			return current == null;
		}
		
		public Node work() {
			if (current != null) {
				current.timeAssemble();
				if (current.isAssembled()) {
					Node temp = current;
					current = null;
					return temp;
				}
			}
			return null;
		}
	}
	
	private class Node {
		private String name;
		private ArrayList<Node> required;
		private boolean assembled;
		private int duration;
		private boolean workedOn = false;
		
		public Node(String name) {
			this.name = name;
			required = new ArrayList<Node>();
			assembled = false;
			duration = 60 + name.charAt(0) - 64;
		}
		
		public int getDuration() {
			return duration;
		}
		
		public boolean isBeingWorkedOn() {
			return workedOn;
		}
		
		public void setBeingWorkedOn() {
			workedOn = true;
		}
		
		public String getName() {
			return name;
		}
		
		public void assemble() {
			assembled = true;
		}
		
		public void timeAssemble() {
			duration--;
			if (duration <= 0) {
				assembled = true;
			}
		}
		
		public boolean isAssembled() {
			return assembled;
		}
		
		public void addRequirement(Node n) {
			required.add(n);
		}
		
		public boolean canBeAssembled() {
			for (Node n : required) {
				if (!n.isAssembled())
					return false;
			}
			return true;
		}
		
		public ArrayList<Node> getRequirements() {
			return required;
		}
		
		public void disassemble() {
			assembled = false;
			for (Node n : required)
				n.disassemble();
		}
	}
	
	public static Node getNode(String nodeName) {
		for (Node n : nodes) {
			if (n.getName().equals(nodeName))
				return n;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Day7 derp = new Day7();
		nodes = new ArrayList<Node>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("instructions.txt"));
			String line = null;

			while((line = br.readLine()) != null) {	
				String req = line.substring(5,6);
				String name = line.substring(36,37);
				
				Node node = getNode(name);
				Node reqNode = getNode(req);
				
				if (node == null) {
					node = derp.new Node(name);	
					nodes.add(node);
				}
				if (reqNode == null) {
					reqNode = derp.new Node(req);	
					nodes.add(reqNode);
				}
				
				node.addRequirement(reqNode);
				
				
			}
			
			System.out.println("Number of rows: " + nodes.size());
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Collections.sort(nodes, new Comparator<Day7.Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				return o1.getName().compareTo(o2.getName());
			}
			
		});

		for (Node n : nodes) {
			System.out.print("Node name: " + n.getName() + "\tRequires: ");
			for (Node r : n.getRequirements()) {
				System.out.print(r.getName() + " ");
			}
			System.out.println();
		}
		
		ArrayList<Node> order = new ArrayList<Node>();
		
		while (order.size() < nodes.size()) {
			for (Node n : nodes) {
				if (!n.isAssembled() && n.canBeAssembled()) {
					n.assemble();
					order.add(n);
					break;
				}
			}
		}
		
		System.out.println("Assembly order:");
		for (Node n : order) {
			System.out.print(n.getName());
		}
		System.out.println();
		
		
		
		System.out.println("------ Part 2 -------");
		
		for (Node n : nodes) 
			n.disassemble();
		
		ArrayList<Node> assembled = new ArrayList<Node>();
		Worker[] workers = new Worker[5];
		for (int i = 0; i < workers.length; i++)
			workers[i] = derp.new Worker();
		
		int time = 0;
		
		while (assembled.size() < nodes.size()) {
			
			for (Node n : nodes) {		
				if (!n.isAssembled() && n.canBeAssembled() && !n.isBeingWorkedOn()) {
					for (Worker w : workers) {
						if (w.isIdle()) {
							w.startWorking(n);
							break;
						}
					}
				}
			}
			
			for (Worker w : workers) {
				Node finished = w.work();
				if (finished != null) {
					assembled.add(finished);
				}
			}
			
			time++;
		}
		
		System.out.println("Time passed: " + time);
		
	}

}
