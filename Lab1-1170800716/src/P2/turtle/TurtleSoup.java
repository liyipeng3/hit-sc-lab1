/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.List;
import java.util.Set;

import java.util.ArrayList;
import java.util.HashSet;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle     the turtle context
	 * @param sideLength length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
		turtle.turn(90);
		turtle.forward(sideLength);
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon; you
	 * should derive it and use it here.
	 * 
	 * @param sides number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		double result = 0.0;
		double side = sides;
		result = 180 * (side - 2) / side;
		return result;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior angles.
	 * 
	 * @param angle size of interior angles in degrees, where 0 < angle < 180
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		Double x = 0.0;
		x = 360 / (180 - angle);
		int result = Integer.parseInt(new java.text.DecimalFormat("0").format(x));
		return result;
	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns to
	 * draw.
	 * 
	 * @param turtle     the turtle context
	 * @param sides      number of sides of the polygon to draw
	 * @param sideLength length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(180 - calculateRegularPolygonAngle(sides));
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the Bearing towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle in
	 * the direction of the target point (targetX,targetY), given that the turtle is
	 * already at the point (currentX,currentY) and is facing at angle
	 * currentBearing. The angle must be expressed in degrees, where 0 <= angle <
	 * 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
	 * 
	 * @param currentBearing current direction as clockwise from north
	 * @param currentX       current location x-coordinate
	 * @param currentY       current location y-coordinate
	 * @param targetX        target point x-coordinate
	 * @param targetY        target point y-coordinate
	 * @return adjustment to Bearing (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360
	 */
	public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
			int targetY) {
		double angle = 0.0;
		if (currentX == targetX) {
			if (targetY > currentY)
				return toAngle(360.0 - currentBearing);
			else
				return toAngle(180.0 - currentBearing);
		}
		if (currentY == targetY) {
			if (targetX > currentX)
				return toAngle(90.0 - currentBearing);
			else
				return toAngle(270.0 - currentBearing);
		}
		double x1 = (double) currentX;
		double y1 = (double) currentY;
		double x2 = (double) targetX;
		double y2 = (double) targetY;

		double tan = (y2 - y1) / (x2 - x1);
		angle = Math.atan(tan) * 180 / Math.PI;

		if (x1 < x2 && y1 < y2) {
			return toAngle(90.0 - angle - currentBearing);
		}
		if (x1 > x2 && y1 > y2) {
			return toAngle(270.0 - angle - currentBearing);
		}
		if (x1 < x2 && y1 > y2) {
			return toAngle(180.0 - currentBearing - (90.0 - (180.0 - angle)));
		}
		if (x1 > x2 && y1 < y2) {
			return toAngle(180.0 + (180.0 - (90.0 - (180.0 - angle))));
		}
		return angle;
	}

	/**
	 * Given a sequence of points, calculate the Bearing adjustments needed to get
	 * from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e. 0
	 * degrees). For each subsequent point, assumes that the turtle is still facing
	 * in the direction it was facing when it moved to the previous point. You
	 * should use calculateBearingToPoint() to implement this function.
	 * 
	 * @param xCoords list of x-coordinates (must be same length as yCoords)
	 * @param yCoords list of y-coordinates (must be same length as xCoords)
	 * @return list of Bearing adjustments between points, of size 0 if (# of
	 *         points) == 0, otherwise of size (# of points) - 1
	 */
	public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
		// throw new RuntimeException("implement me!");
		List<Double> list = new ArrayList<>();
		double currentBearing = 0.0;
		for (int i = 0; i < xCoords.size() - 1; i++) {
			currentBearing = calculateBearingToPoint(currentBearing, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1),
					yCoords.get(i + 1));
			list.add(currentBearing);
		}
		return list;
	}

	/**
	 * Given a set of points, compute the convex hull, the smallest convex set that
	 * contains all the points in a set of input points. The gift-wrapping algorithm
	 * is one simple approach to this problem, and there are other algorithms too.
	 * 
	 * @param points a set of points with xCoords and yCoords. It might be empty,
	 *               contain only 1 point, two points or more.
	 * @return minimal subset of the input points that form the vertices of the
	 *         perimeter of the convex hull
	 */
	public static Set<Point> convexHull(Set<Point> points) {
		Set<Point> temp = new HashSet<Point>();
		for (Point p : points) {
			temp.add(p);
		}
		Set<Point> result = new HashSet<Point>();
		List<Point> list = new ArrayList<>();
		Point apoint = new Point(0, 0);
		Point npoint = new Point(0, 0);
		Point fpoint = new Point(0, 0);
		int last = 0;

		double min = 99999.0;
		if (temp.size() == 0)
			return result;
		if (temp.size() <= 2) {
			for (Point p : temp) {
				result.add(p);
			}
			return result;
		}
		for (Point p : temp) {
			if (p.x() < min) {
				min = p.x();
			}
		}
		for (Point p : temp) {
			if (p.x() == min) {
				list.add(p);
			}
		}
		min = 99999.0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).y() < min) {
				min = list.get(i).y();
				apoint = list.get(i);
			}
		}

		temp.remove(apoint);
		result.add(apoint);
		fpoint = apoint;

		while (temp.size() != 0) {
			min = 99999.0;
			double t = 0.0;
			for (Point p : temp) {
				int x1 = (int) apoint.x();
				int y1 = (int) apoint.y();
				int x2 = (int) p.x();
				int y2 = (int) p.y();
				t = toAngle(360.0 - calculateBearingToPoint(180.0, x1, y1, x2, y2));
				if (t < min) {
					min = t;
					npoint = p;
				}
			}
			System.out.println();
			temp.remove(npoint);
			result.add(npoint);
			if (fpoint.x() == npoint.x())
				break;
			if (result.size() == last)
				break;
			last = result.size();
			apoint = npoint;
		}
		return result;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can be
	 * as little or as much as you want.
	 * 
	 * @param turtle the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		turtle.color(PenColor.BLUE);
		for (int i = 0; i < 99; i++) {
			drawRegularPolygon(turtle, 11, 63);

			turtle.turn(33);
			turtle.forward(33);
			turtle.turn(233);

			switch (i % 10) {
			case 0:
				turtle.color(PenColor.RED);
				break;
			case 1:
				turtle.color(PenColor.PINK);
				break;
			case 2:
				turtle.color(PenColor.BLUE);
				break;
			case 3:
				turtle.color(PenColor.PINK);
				break;
			case 4:
				turtle.color(PenColor.ORANGE);
				break;
			case 5:
				turtle.color(PenColor.YELLOW);
				break;
			case 6:
				turtle.color(PenColor.GREEN);
				break;
			case 7:
				turtle.color(PenColor.BLUE);
				break;
			case 8:
				turtle.color(PenColor.MAGENTA);
				break;
			case 9:
				turtle.color(PenColor.CYAN);
				break;
			}
		}
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 * 
	 * @param args unused
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 40);

		drawPersonalArt(turtle);

		// drawRegularPolygon(turtle, 6, 55);

		// draw the window
		turtle.draw();
	}

	public static double toAngle(double angle) { // 计算合法角度函数
		while (angle >= 360.0) {
			angle = angle - 360.0;
		}
		while (angle < 0.0) {
			angle = angle + 360.0;
		}
		return angle;
	}
}
