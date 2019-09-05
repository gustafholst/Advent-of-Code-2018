package code.of.advent;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Unit {
	
	private char type;
	private int hp;
	private int attackPower;
	Spot current;
	Spot nextStep;

	public Spot getNextStep() {
		return nextStep;
	}

	public void setNextStep(Spot nextStep) {
		this.nextStep = nextStep;
	}
	
	public boolean canAttack() {
		for (Spot s : current.getNeighbours()) {
			Unit u = s.getContent();
			if (u != null && u.getType() != type && !u.isDead())
				return true;
		}
		return false;
	}
	
	public void hit(int damage) {
		hp -= damage;
		if (isDead()) {
			current.setContent(null);
		}
	}
	
	public boolean isDead() {
		return hp < 0;
	}
	
	public Unit attack() {
		ArrayList<Spot> neighbours = current.getNeighbours();
		Collections.sort(neighbours, new SpotReadingOrder());
		
		int lowestHP = Integer.MAX_VALUE;
		for (Spot s : current.getNeighbours()) {
			Unit u = s.getContent();
			if (u != null && u.getType() != type && !u.isDead()) {
				if (u.getHp() < lowestHP)
					lowestHP = u.getHp();
			}
		}
		for (Spot s : current.getNeighbours()) {
			Unit u = s.getContent();
			if (u != null && u.getType() != type && !u.isDead() && u.getHp() == lowestHP) {
				u.hit(this.attackPower);			
				return u;
			}
		}
		return null;
	}
	
	public class ChoiceComparator implements Comparator<ArrayList<Spot>> {
		
		SpotReadingOrder comp = new SpotReadingOrder();
		
		@Override
		public int compare(ArrayList<Spot> o1, ArrayList<Spot> o2) {
			if (o1.size() < o2.size()) return -1;
			if (o1.size() > o2.size()) return 1;
			
			int destination = o1.size() -1;
			int destResult = comp.compare(o1.get(destination), o2.get(destination));
			if (destResult == -1) return -1;
			if (destResult == 1) return 1;
				
			int beginResult = comp.compare(o1.get(0), o2.get(0));
			if (beginResult == -1) return -1;
			if (beginResult == 1) return 1;
			
			return 0;
		}
	}
	
	public boolean determineNextStep(ArrayList<Unit> units) {
		
		Queue<ArrayList<Spot>> choices = new PriorityQueue<ArrayList<Spot>>(11, new ChoiceComparator());
		
		boolean noMoreEnemies = true;
		for (Unit other : units) {
			//identify enemies
			if (other == this) continue;
			if (other.getType() == type || other.isDead()) continue;	
			
			noMoreEnemies = false;
			ArrayList<Spot> ne = other.getCurrent().getNeighbours();
			for (int i = 0; i < ne.size(); i++) {
				ArrayList<Spot> path = findPath(current ,ne.get(i));
				if (path != null && !path.isEmpty()) 
					choices.add(path);
				Day15.resetValues();
			}	
		}
		
		if (!choices.isEmpty()) {
			ArrayList<Spot> chosenPath = choices.poll();
			nextStep = chosenPath.get(0);
			
			SpotReadingOrder readingorder = new SpotReadingOrder();
			ArrayList<Spot> neigh = current.getNeighbours();
			for (int i = 0; i < neigh.size(); i++) {
				if (neigh.get(i).getContent() == null && readingorder.compare(neigh.get(i), nextStep) < 0) {
					ArrayList<Spot> alternativePath = findPath(neigh.get(i), chosenPath.get(chosenPath.size()-1));
					Day15.resetValues();
					if (alternativePath != null && alternativePath.size() <= chosenPath.size())
						nextStep = neigh.get(i);
				}		
			}
		}
		else
			nextStep = null;
		
		return noMoreEnemies;
	}
	
	public ArrayList<Spot> findPath(Spot source, Spot destination) {
		
		Comparator<Spot> spotcomparator = new SpotComparator();
		Queue<Spot> openQueue = new PriorityQueue<Spot>(11, spotcomparator);
		
		source.setParent(null);
		source.setG(0);
		source.calculateH(destination);
		source.calculateF();
		openQueue.add(source);
		
		final Set<Spot> closedSet = new HashSet<Spot>();
		
		while (!openQueue.isEmpty()) {
			
			Spot next = openQueue.poll();
			
			if (next == destination) {
				//backtrack path
				ArrayList<Spot> path = new ArrayList<Spot>();
				Spot s = destination;
				while (s.parent != null) {
					path.add(s);
					s = s.getParent();
				}
				
				Collections.reverse(path);
				
				return path;
			}
			
			ArrayList<Spot> neighbours = next.getNeighbours();
			SpotReadingOrder spotreadingorder = new SpotReadingOrder();
			Collections.sort(neighbours, spotreadingorder);  //evaluate in reading order
			
			Spot previous = neighbours.get(0);
			for (Spot neighbour : neighbours) {
				if (closedSet.contains(neighbour) || neighbour.getContent() != null)
					continue;
		
				int tentativeG = next.getG() + 1;
				
				if (tentativeG < neighbour.getG() || 
						(tentativeG == neighbour.getG() && spotreadingorder.compare(neighbour, previous) < 0)) {
					neighbour.setG(tentativeG);
					neighbour.calculateH(destination);
					neighbour.calculateF();
					neighbour.setParent(next);
					if (!openQueue.contains(neighbour)) {					
						openQueue.add(neighbour);
					}				
				}
				
				previous = neighbour;
			}
			
			closedSet.add(next);
		}
		
		return null;
	}

	public class SpotReadingOrder implements Comparator<Spot> {
		
		@Override
		public int compare(Spot o1, Spot o2) {
			if (o1.getRow() < o2.getRow())  return -1;					
			if (o1.getRow() > o2.getRow())  return 1;
			if (o1.getCol() < o2.getCol())  return -1;
			if (o1.getCol() > o2.getCol())  return 1;
			return 0;
		}
	}

	public class SpotComparator implements Comparator<Spot> {
		
		@Override
		public int compare(Spot o1, Spot o2) {
			if (o1.getF() < o2.getF())
				return -1;
			if(o2.getF() < o1.getF())
				return 1;
			
			return 0;
		}
	}

	public Unit(char type, Spot spot, Day15 d, int attackPower) {
		this.type = type;
		this.current = spot;
		hp = 200;
		this.attackPower = attackPower;
	}
	
	public void move() {	
		if (nextStep != null) {
			current.setContent(null); //free old spot
			nextStep.setContent(this);
			current = nextStep;	
		}
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public Spot getCurrent() {
		return current;
	}

	public void setCurrent(Spot current) {
		this.current = current;
	}

}
