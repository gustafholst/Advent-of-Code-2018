package code.of.advent;

public class Day11 {

	static final int serialNumber = 3613;
	
	public Day11() {
		// TODO Auto-generated constructor stub
	}
	
	private static int getHundreds(int num) {
		if (num < 100)
			return 0;
		
		 num /= 100;
		 return num % 10;
	}
	
	private static int calculateValue(int x, int y) {
		int rackID = x + 10;
		int powerLevel = rackID * y;
		powerLevel += serialNumber;
		powerLevel *= rackID;
		powerLevel = getHundreds(powerLevel);
		return powerLevel - 5;
	}

	public static void main(String[] args) {
		
		final int ROWS = 300;
		final int COLS = 300;
		int[][] grid = new int[ROWS+1][COLS+1];
		
		for (int i = 1; i <= ROWS; i++) {
			for (int j = 0; j <= COLS; j++) {
				grid[i][j] = calculateValue(i, j);
			}	
		}
		
		int largestPower = 0;
		int wX = 0;
		int wY = 0;
		int winningSize = 1;
		
		for (int size = 1; size <= 300; size++) {
			for (int i = 1; i <= ROWS-size; i++) {
				for (int j = 0; j <= COLS-size; j++) {
					
					int sum = 0;
					
					for (int k = 0; k < size; k++) {
						for (int l = 0; l < size; l++) {
							sum += grid[i+k][j+l];
						}
					}
					
					if (sum > largestPower) {
						largestPower = sum;
						wX = i;
						wY = j;
						winningSize = size;
					}
				}	
			}
		}
		
		
		System.out.println("Winning coordinate: " + wX + ", " + wY);
		System.out.println("Winning size: " + winningSize);
	}

}
