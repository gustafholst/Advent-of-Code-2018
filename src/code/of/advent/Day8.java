package code.of.advent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day8 {

	private static Scanner scanner;
	
	private static ArrayList<Node> nodeTree;

	public Day8() {
		// TODO Auto-generated constructor stub
	}

	private class Node {
		private int numChildren;
		private int numMeta;
		ArrayList<Integer> metaData;
		ArrayList<Node> children;
		
		public Node() {
			numChildren = 0;
			numMeta = 0;
			metaData = new ArrayList<Integer>();
			children = new ArrayList<Node>();
		}
		
		private int getMetaSum() {
			int sum = 0;
			for (int n : metaData)
				sum += n;
			
			return sum;
		}
		
		private int getNodeValue() {
			if (children.size() == 0)   //base case
				return getMetaSum();
			
			int value = 0;
			for (int i = 0; i < numMeta; i++) {
				try {
					int index = metaData.get(i);
					if (index > 0)
						value += children.get(index - 1).getNodeValue();
				}
				catch (IndexOutOfBoundsException e) {};
			}
		
			return value;
		}
		
		public void readNode() {
			numChildren = scanner.nextInt();
			numMeta = scanner.nextInt();
			
			for (int i = 0; i < numChildren; i++) {
				Node n = new Node();
				n.readNode();
				nodeTree.add(n);
				children.add(n);
			}
			
			for (int j = 0; j < numMeta; j++) {
				int meta = scanner.nextInt();
				metaData.add(meta);
			}
		}
	}
	
	public static void main(String[] args) {
		Day8 derp = new Day8();
		nodeTree = new ArrayList<Node>();
		
		Node root = derp.new Node();
		
		try {
			scanner = new Scanner(new File("memory.txt"));
			
			root.readNode();
			
			nodeTree.add(root);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int total = 0;
		
		for (Node node : nodeTree) {
			total += node.getMetaSum();
		}
		
		System.out.println("Amount of nodes: " + nodeTree.size());
		System.out.println("Sum of all meta data is: " + total);
		
		
		System.out.println("----- Part 2 -----");
		
		
		int rootNodeValue = 0;
		
		rootNodeValue = root.getNodeValue();
		
		System.out.println("The value of the root is: " + rootNodeValue);
		
		
	}

}
