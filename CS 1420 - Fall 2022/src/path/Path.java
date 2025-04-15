/*
 * This will allow us to create a path object. The path object has an array list of points, 
 * a int of the pointCount in that array list, and an array list that contains
 * values of the lengths between each of the points. 
 * 
 * @author Natalie Wilkins
 * 2022 - 11 - 09
 * 
 */



package path;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Path {

	private int pointCount;
	ArrayList<Point> pointsList = new ArrayList<Point>();
	ArrayList<Double> lengthsList = new ArrayList<Double>();

	/*
	 * builds an empty path (no points).
	 */
	public Path() {

	}

	/*
	 * takes a scanner as a parameter. we assume the scanner is already open and
	 * scanning a path file. scans in the count and the points to build a path.
	 * 
	 */
	public Path(Scanner s) {
		if (s.hasNext()) {
			pointCount = s.nextInt();
			while (s.hasNext()) {
				pointsList.add(new Point(s.nextInt(), s.nextInt()));
			}
		}
		for (int i = 1; i < pointsList.size(); i++) {
			double x1 = pointsList.get(i-1).getX();
			double x2 = pointsList.get(i).getX();
			double y1 = pointsList.get(i-1).getY();
			double y2 = pointsList.get(i).getY();
			double length = Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1));
			lengthsList.add(length);
			}
	}

	/*
	 * returns the number of points in the path.
	 */
	public int getPointCount() {
		return pointsList.size();
	}

	/*
	 * returns the x coordinate of the nth element in the path. n starts at 0 for
	 * the first x coordinate.
	 * 
	 */
	public int getX(int n) {
		return (int) pointsList.get(n).getX();
	}

	/*
	 * returns the y coordinate of the nth element in the path. n starts at 0 for
	 * the first y coordinate.
	 * 
	 */
	public int getY(int n) {
		return (int) pointsList.get(n).getY();
	}

	/*
	 * adds an extra point to the end of the path.
	 */
	public void add(int x, int y) {
		pointsList.add(new Point(x, y));
		pointCount++;
	}
	
	
	
	/*
	 *  draws the line segments to the screen
	 *  takes a graphics object g
	 */
	void draw (Graphics g) {
		g.setColor(Color.black);
		for (int i = 1; i < pointsList.size(); i++) {
			g.drawLine((int) pointsList.get(i-1).getX(),(int)pointsList.get(i-1).getY(), 
					(int) pointsList.get(i).getX(), (int) pointsList.get(i).getY());
		}
	}
	

	/*
	 * allows us to print out our path in a legible format. must print out a count
	 * first, then a space, followed by the x and y coordinate of each point, spaces
	 * in between everything. can use \n for line breaks if we want to separate the
	 * coordinates with a new line.
	 */
	public String toString() {
		String s = new String();
		s = pointsList.size() + "\n";

		for (Point p : pointsList) {
			s = s + (int) p.getX() + " ";
			s = s + (int) p.getY() + "\n";
		}
		return s;
	}
	
	/*
	 * this reaches into the lengthsList array list and returns
	 * the length of the lineIndex at the position we call for.
	 * 
	 * int lineIndex position of line segment we want
	 */
	public double lengthOfLine(int lineIndex) {
		return lengthsList.get(lineIndex);
	}
	
	
	/*
	 * uses geometry to calculate the length of each line segment
	 * between each of our points. there will be one less line segment
	 * than point. 
	 * 
	 */
	public double totalLength() {
		double totalLength = 0;
		for (int i = 0; i < lengthsList.size(); i++)
			totalLength += lengthsList.get(i);
		
		return totalLength;
	}

}
