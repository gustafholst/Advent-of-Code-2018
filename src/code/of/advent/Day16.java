package code.of.advent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Day16 {

	ArrayList<Code> codes;
	ArrayList<Code> instructions;
	int[] mainRegister;
	
	public Day16() {
		
	}
	
	public void copyRegister(ArrayList<Integer> a, int[] b) {
		for (int i = 0; i < 4; i++) {
			b[i] = a.get(i);
		}
	}
	
	public boolean sameContent(int[] a, ArrayList<Integer> b) {
		for (int i = 0; i < 4; i++) {
			if (a[i] != b.get(i))
				return false;
		}
		return true;
	}
	
	public abstract class OpCode {
		int opCode = -1;
		ArrayList<Integer> possibleCodes = new ArrayList<Integer>();
		protected int[] register = new int[4];		
		public abstract boolean test(Code c);
		public abstract void process(Code c);
	}
	
	public class Addr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = register[c.a] + register[c.b];		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] + mainRegister[c.b];	
		}
	}
	
	public class Addi extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = register[c.a] + c.b;		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] + c.b;	
		}
	}
	
	public class Mulr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = register[c.a] * register[c.b];		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] * mainRegister[c.b];	
		}
	}
	
	public class Muli extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = register[c.a] * c.b;			
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] * c.b;	
		}
	}
	
	public class Banr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = before.get(c.a) & before.get(c.b);		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] & mainRegister[c.b];	
		}
	}
	
	public class Bani extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = before.get(c.a) & c.b;		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] & c.b;	
		}
	}
	
	public class Borr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = before.get(c.a) | before.get(c.b);		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] | mainRegister[c.b];	
		}
	}
	
	public class Bori extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = before.get(c.a) | c.b;		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a] | c.b;	
		}
	}
	
	public class Setr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = before.get(c.a);		
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = mainRegister[c.a];	
		}
	}
	
	public class Seti extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			register[c.c] = c.a;	
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			mainRegister[c.c] = c.a;	
		}
	}
	
	public class Gtir extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (c.a > before.get(c.b))
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (c.a > mainRegister[c.b])
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Gtri extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (before.get(c.a) > c.b)
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (mainRegister[c.a] > c.b)
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Gtrr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (before.get(c.a) > before.get(c.b))
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (mainRegister[c.a] > mainRegister[c.b])
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Eqir extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (c.a == before.get(c.b))
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (c.a == mainRegister[c.b])
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Eqri extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (before.get(c.a) == c.b)
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (mainRegister[c.a] == c.b)
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Eqrr extends OpCode {
		public boolean test(Code c) {
			ArrayList<Integer> before = c.getBefore();
			copyRegister(before, register);
			if (before.get(c.a) == before.get(c.b))
				register[c.c] = 1;
			else
				register[c.c] = 0;
			return sameContent(register, c.getAfter());
		}
		public void process(Code c) {
			if (mainRegister[c.a] ==  mainRegister[c.b])
				mainRegister[c.c] = 1 ;	
			else
				mainRegister[c.c] = 0;
		}
	}
	
	public class Code {
		int op;
		int a;
		int b;
		int c;
		
		int matching;
		
		ArrayList<Integer> before;
		ArrayList<Integer> after;
		
		public Code(int op, int a, int b, int c) {
			this.op = op;
			this.a = a;
			this.b = b;
			this.c = c;
			before = new ArrayList<Integer>(4);
			after = new ArrayList<Integer>(4);
			matching = 0;
		}
		
		public void addBefore(int i) {
			before.add(i);
		}
		
		public void addAfter(int i) {
			after.add(i);
		}
		
		public ArrayList<Integer> getBefore() {
			return before;
		}
		
		public ArrayList<Integer> getAfter() {
			return after;
		}
		
	}
	
	public void readFile() {
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File("code.txt"))));
			
			String line = null;
			while ((line = bf.readLine()) != null) {
				if (line.startsWith("Before:")) {
					String before = line;
					String code = bf.readLine();
					String after = bf.readLine();
					bf.readLine();
					
					Scanner codeScan = new Scanner(code);

					Code op = new Code(codeScan.nextInt(), codeScan.nextInt(), codeScan.nextInt(), codeScan.nextInt());
					codeScan.close();
						
					before = before.substring(before.indexOf('[') + 1, before.indexOf(']'));
					before = before.replaceAll(", ", " ");
					Scanner befScan = new Scanner(before);
					
					while (befScan.hasNextInt()) {
						op.addBefore(befScan.nextInt());			
					}
					befScan.close();
					
					after = after.substring(after.indexOf('[') + 1, after.indexOf(']'));
					after = after.replaceAll(", ", " ");
					Scanner afterScan = new Scanner(after);
					
					while (afterScan.hasNextInt()) {
						op.addAfter(afterScan.nextInt());
					}
					afterScan.close();
					
					codes.add(op);	
				}
				else {
					Scanner insScan = new Scanner(line);
					
					if(insScan.hasNextInt()) {
						Code instr = new Code(insScan.nextInt(), insScan.nextInt(), insScan.nextInt(), insScan.nextInt());
						insScan.close();
						
						instructions.add(instr);
					}
				}
			}
			
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run() {
		codes = new ArrayList<Code>();
		instructions = new ArrayList<Code>();
		readFile();
		
		ArrayList<OpCode> opCodes = new ArrayList<OpCode>(16);
		opCodes.add(new Addr());  //0
		opCodes.add(new Addi());  //1
		opCodes.add(new Mulr());  //2
		opCodes.add(new Muli());  //3
		opCodes.add(new Banr());  //4
		opCodes.add(new Bani());  //5
		opCodes.add(new Borr());  //6
		opCodes.add(new Bori());  //7
		opCodes.add(new Setr());  //8
		opCodes.add(new Seti());  //9
		opCodes.add(new Gtir());  //10
		opCodes.add(new Gtri());  //11
		opCodes.add(new Gtrr());  //12
		opCodes.add(new Eqir());  //13
		opCodes.add(new Eqri());  //14
		opCodes.add(new Eqrr());  //15
		
		
		ArrayList<OpCode> done = new ArrayList<OpCode>();
		ArrayList<Integer> taken = new ArrayList<Integer>();
		while (done.size() < 16) {
			
			for (int i = 0; i < opCodes.size(); i++) {
				opCodes.get(i).possibleCodes.clear();
				for (int j = 0; j < 16; j++) {
					if (taken.contains(j))
						continue;
					
					boolean pass = true;
					
					for (Code c : codes) {			
						if (c.op == j) {
							if (!opCodes.get(i).test(c)) {
								pass = false;		
								break;
							}			
						}
					}
					
					if (pass) {
						opCodes.get(i).possibleCodes.add(j);
					}
				}
			}
			
			for (OpCode oc : opCodes) {
				if (oc.possibleCodes.size() == 1) {
					oc.opCode = oc.possibleCodes.get(0);
					done.add(oc);
					taken.add(oc.possibleCodes.get(0));
				}
			}
			
			opCodes.removeAll(done);
		}
		

		for (OpCode oc : done)
			System.out.println(oc.opCode);
		
		System.out.println("------------------------");
		
		mainRegister = new int[4];
		
		for (Code instruction : instructions) {
			for (OpCode oc : done) {
				if (oc.opCode == instruction.op)
					oc.process(instruction);
			}
		}
		
		System.out.println("Value in register 0: " + mainRegister[0]);
		System.out.println("Num instructions: " + instructions.size());
	}
	
	public int findLargest(int[] arr) {
		int largest = 0;
		int largestValue = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] > largestValue) {
				largest = i;
				largestValue = arr[i];
			}		
			System.out.print(arr[i] + "\t");
		}
		System.out.println();
		return largest;
	}
	
	public static void main(String[] args) {
		Day16 day = new Day16();
		
		day.run();

	}

}
