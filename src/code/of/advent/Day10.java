package code.of.advent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Day10 extends JPanel {
	
	private static ArrayList<Point> points;
	private static char[][] grid;
	final static int ROWS = 400;
	final static int COLS = 400;
	private static int seconds = 0;

	private BufferedImage canvas;
	
	public Day10() {
		canvas = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		fillCanvas(Color.BLACK);
	}
	
	public void fillCanvas(Color c) {
		int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
	}
	
	private class Point {
		private int posX;
		private int posY;
		private int velX;
		private int velY;
		
		
		
		public Point(int x, int y, int vx, int vy) {
			posX = x;
			posY = y;
			velX = vx;
			velY = vy;
		}
		
		public void move() {
			posX += velX;
			posY += velY;
		}
		
		public int getPosX() {
			return posX;
		}

		public int getPosY() {
			return posY;
		}

		public int getVelX() {
			return velX;
		}

		public int getVelY() {
			return velY;
		}
	}

	public static void main(String[] args) {
		points = new ArrayList<Point>();
		grid = new char[COLS][ROWS];
		
		Day10 derp = new Day10();
		try {
			BufferedReader br = new BufferedReader(new FileReader("points_of_ligh.txt"));
			String line = null;
			
			while((line = br.readLine()) != null) {
				
				String stringX = line.substring(line.indexOf('<') + 1, line.indexOf(','));
				String stringY = line.substring(line.indexOf(',') + 1, line.indexOf('>'));
				String velX = line.substring(line.lastIndexOf('<') + 1, line.lastIndexOf(','));
				String velY = line.substring(line.lastIndexOf(',') + 1, line.lastIndexOf('>'));
				int x = Integer.parseInt(stringX.trim());
				int y = Integer.parseInt(stringY.trim());
				int vx = Integer.parseInt(velX.trim());
				int vy = Integer.parseInt(velY.trim());
				
				Point p = derp.new Point(x, y, vx, vy);
				points.add(p);
			}
			
			System.out.println("Number of rows: " + points.size());
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
//		for (Point p : points) {
//			System.out.println(p.getPosX() + "\t" + p.getPosY() + "\t" + p.getVelX() + "\t" + p.getVelY());
//		}
		
		while(!pointsTogether()) {
			seconds++;
			moveAllPoints();
			System.out.println(points.get(0).getPosX() + "\t" + points.get(0).getPosY());
		}
		
		System.out.println("Seconds: " + seconds);
		
		
		JFrame frame = new JFrame();
		
		frame.add(derp);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Timer timer = new Timer(4000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seconds++;
				System.out.println(seconds);
				moveAllPoints();
				derp.drawGrid();
			}
			
		});
		timer.start();
		
		
	}
	
	public void drawGrid() {
		fillCanvas(Color.BLACK);
        for (Point p : points) {
        	int x = p.getPosX();
        	int y = p.getPosY();
        	if (x > 0 && x < 400 && y > 0 && y < 400)
        		canvas.setRGB(x, y, Color.WHITE.getRGB());
        }
        repaint();
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, null, null);
	
	}
	
	public static boolean pointsTogether() {
		for (Point p : points) {
			if (p.getPosX() < 100 || p.getPosX() > 300 || p.getPosY() < 100 || p.getPosY() > 300)
				return false;
		}
		
		return true;
	}
	
	public static void nextFrame() {
		clearGrid();
		moveAllPoints();
		updateGrid();
		displayGrid();
	}
	
	public static void moveAllPoints() {
		for (Point p : points) {
			p.move();
		}
	}
	
	public static void displayGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				System.out.print(grid[j][i]);
			}
			System.out.println();
		}
	}
	
	public static void updateGrid() {
		for (Point p : points) {
			int y = p.getPosY();
			int x = p.getPosX();
			if (y < 0 || y > ROWS-1 || x < 0 || x > COLS-1)
				continue;
			else
				grid[x][y] = '#';
		}
	}
	
	public static void clearGrid() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[j][i] = '.';
			}
		}
	}

}
