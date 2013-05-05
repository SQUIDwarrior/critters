/*
 * Copyright (c) 2013 Mike Deats
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created on May 4, 2013
 */
package critters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import critters.Critter.Action;
import critters.Critter.Direction;
import critters.Critter.Scent;
import critters.Critter.Type;
import critters.type.*;

public class CritterModel {

	private static final String[] DEFAULT_TYPES = {
			FlyTrap.class.getSimpleName(), Food.class.getSimpleName(),
			Hawk.class.getSimpleName(), Mothra.class.getSimpleName(),
			Predator.class.getSimpleName(), Runner.class.getSimpleName(),
			Seeker.class.getSimpleName(), Spinner.class.getSimpleName(),
			Wanderer.class.getSimpleName() };
	private static final String DEFAULT_TYPE_PACKAGE = "critters.type";
	public static final int DEFAULT_POPULATION = 100;
	public static final int MAX_POPULATION = 1000;
	private boolean DEBUG = false;
	private boolean firstRun = true;
	protected int population;
	private int maxPopulation;
	private int columns;
	private int rows;
	private Critter[][] grid;
	private HashMap<Point, Critter> inactiveCritterHash;
	private ArrayList<Point> activeCritterList;
	protected HashMap<Class<? extends Critter>, Integer> populationCounters;
	private static Random random1 = new Random(System.currentTimeMillis());
	private static Random random2 = new Random(System.currentTimeMillis() * 2);
	private static Collection<Class<? extends Critter>> availableCritterTypes;

	public CritterModel(int cols, int rows) {
		columns = cols;
		this.rows = rows;
		init();
	}

	private void init() {
		grid = new Critter[rows][columns];
		population = 0;
		maxPopulation = rows * columns;

		inactiveCritterHash = new HashMap<Point, Critter>(maxPopulation);
		activeCritterList = new ArrayList<Point>(maxPopulation);
		populationCounters = new HashMap<Class<? extends Critter>, Integer>(
				maxPopulation);

	}

	public static Collection<Class<? extends Critter>> getAvailableCritterTypes() {
		if (availableCritterTypes == null) {
			availableCritterTypes = new ArrayList<Class<? extends Critter>>(0);

			String typePackageStr = System.getProperty("critters.typePackageName");
			String typeStr = System.getProperty("critters.availableCritterTypes");

			String[] types = DEFAULT_TYPES;
			String typePackage = DEFAULT_TYPE_PACKAGE;
			if (typeStr != null) {
				types = typeStr.split(",");
			}

			if (typeStr != null) {
				typePackage = typePackageStr;
			}
			for (String clazz : types) {
				String className = typePackage + "." + clazz;
				try {
					@SuppressWarnings("unchecked")
					Class<? extends Critter> c = (Class<? extends Critter>) Class.forName(className);

					availableCritterTypes.add(c);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return availableCritterTypes;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return columns;
	}

	public int getPopulation() {
		return population;
	}

	/**
	 * Adds a new Critter at a random location
	 * 
	 * @param newCritter
	 * @return
	 */
	public boolean addCritter(Critter newCritter) {
		Point position = getNewPosition();
		Direction direction = getNewDirection();
		newCritter.direction = direction;
		setCritterAt(newCritter, position);

		activeCritterList.add(position);

		++population;

		return false;
	}

	/**
	 * Clears the grid of all Critters and resets all counters and lists.
	 * 
	 */
	public void clearGrid() {
		grid = null;
		grid = new Critter[rows][columns];
		activeCritterList.clear();
		inactiveCritterHash.clear();
		populationCounters.clear();
		population = 0;
		firstRun = true;
	}

	/**
	 * Returns a random Direction of type NORTH, SOUTH, EAST, or WEST.
	 * 
	 * @return A random Direction
	 */
	public Direction getNewDirection() {
		int x = random1.nextInt(4);

		Direction temp = null;

		if (x == Direction.NORTH.ordinal())
			temp = Direction.NORTH;
		else if (x == Direction.SOUTH.ordinal())
			temp = Direction.SOUTH;
		else if (x == Direction.EAST.ordinal())
			temp = Direction.EAST;
		else
			temp = Direction.WEST;

		return temp;
	}

	/**
	 * Gets a random Point with between (0, 1) and (rows - 1, columns - 1).
	 * 
	 * @return A random Point.
	 */
	public Point getNewPosition() {
		int x, y;

		do {
			x = random1.nextInt(columns);
			y = random2.nextInt(rows);
			if (y == 0)
				y = 1;
		} while (getCritterAt(new Point(x, y)) != null);

		return new Point(x, y);
	}

	/**
	 * Adds 'numCritters' of the Critter type specified by 'critterType'.
	 * 
	 * @param numCritters
	 * @param critterType
	 */
	public void populateCritter(int numCritters,
			Class<? extends Critter> critterType) {

		populationCounters.put(critterType, new Integer(numCritters));
		for (int i = 0; i < numCritters; i++) {
			if (population == maxPopulation) {
				return;
			}
			try {
				addCritter(critterType.newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This will update each Critter in the list based on their getAction()
	 * method. It will update the grid state as well as they performAction()
	 * method will update each Critter's view of the grid.
	 * 
	 */
	public void updateCritters() {

		if (firstRun)
			firstRun = false;
		else {
			activeCritterList.clear();
			activeCritterList.addAll(inactiveCritterHash.keySet());
		}

		inactiveCritterHash.clear();
		int roundCounter = 0;

		int[] sequence = generateRandomSequence(activeCritterList.size());
		for (int i : sequence) {

			Point point = null;
			try {
				point = activeCritterList.get(i);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("size = " + activeCritterList.size());
				for (int j : sequence)
					System.out.print(j + " ");
				System.exit(1);
			}

			if (!inactiveCritterHash.containsKey(point)) {
				try {
					boolean result = performActionOnCritterAt(point);
					if (!result) {
						inactiveCritterHash.put(point, getCritterAt(point));
						updateCritterView(point);
					}
				} catch (NullPointerException e) {
					System.out.println("i value = " + i);
					System.out.println(point);
					e.printStackTrace();
					System.exit(1);
				}
				if (DEBUG) {
					System.out.println("After action...");
					System.out.print(getCritterAt(point));
					System.out.println("---------------");
				}
				++roundCounter;
			}
		}

		if (DEBUG)
			System.out.println("Round count = " + roundCounter);

	}

	/**
	 * Sets the grid location specified by 'position' to the 'critter' parameter.
	 * Note that this method will update the Critter's position to the value of
	 * the 'position' parameter. This is to insure that any Critter always has the
	 * correct grid position.
	 * 
	 * @param critter
	 * @param position
	 */
	private boolean setCritterAt(Critter critter, Point point) {
		if (point.x < 0 || point.y < 1 || point.x >= columns || point.y >= rows)
			return false;

		grid[point.y][point.x] = critter;

		return true;
	}

	/**
	 * Gets the critter at point Point.
	 * 
	 * @param point
	 * @return The Critter found, null if outside the range.
	 */
	public Critter getCritterAt(Point point) {
		if (point.x < 0 || point.y < 1 || point.x >= columns || point.y >= rows)
			return null;

		return grid[point.y][point.x];
	}

	/**
	 * Updates the view of the Critter at point Position
	 * 
	 * @param position
	 */
	public void updateCritterView(Point position) {
		Critter critter = getCritterAt(position);
		if (critter == null)
			return;

		Type north = Type.EMPTY, south = Type.EMPTY, east = Type.EMPTY, west = Type.EMPTY;
		Direction direction = critter.direction;

		// Check for walls
		if (position.x + 1 == columns)
			east = Type.WALL;
		if (position.x - 1 < 0)
			west = Type.WALL;
		if (position.y + 1 == rows)
			south = Type.WALL;
		if (position.y - 1 == 0)
			north = Type.WALL;

		// Get the Critters at each location
		Critter cEast = getCritterAt(new Point(position.x + 1, position.y));
		Critter cWest = getCritterAt(new Point(position.x - 1, position.y));
		Critter cSouth = getCritterAt(new Point(position.x, position.y + 1));
		Critter cNorth = getCritterAt(new Point(position.x, position.y - 1));

		// Get the types at each location
		// East
		if (cEast != null && east != Type.WALL) {
			if (cEast.getClass().equals(critter.getClass()))
				east = Type.SAME;
			else
				east = Type.OTHER;
		}

		// West
		if (cWest != null && west != Type.WALL) {
			if (cWest.getClass().equals(critter.getClass()))
				west = Type.SAME;
			else
				west = Type.OTHER;
		}

		// North
		if (cNorth != null && north != Type.WALL) {
			if (cNorth.getClass().equals(critter.getClass()))
				north = Type.SAME;
			else
				north = Type.OTHER;
		}

		// South
		if (cSouth != null && south != Type.WALL) {
			if (cSouth.getClass().equals(critter.getClass()))
				south = Type.SAME;
			else
				south = Type.OTHER;
		}

		// Set the Critter's perception based on it's orientation
		if (direction == Direction.NORTH) {
			critter.FRONT = north;
			critter.LEFT = west;
			critter.RIGHT = east;
			critter.BACK = south;
		} else if (direction == Direction.EAST) {
			critter.FRONT = east;
			critter.LEFT = north;
			critter.RIGHT = south;
			critter.BACK = west;
		} else if (direction == Direction.SOUTH) {
			critter.FRONT = south;
			critter.LEFT = east;
			critter.RIGHT = west;
			critter.BACK = north;
		} else {
			critter.FRONT = west;
			critter.LEFT = south;
			critter.RIGHT = north;
			critter.BACK = east;
		}

		// updateSmellFieldAt(position);
	}

	/*
	 *TODO Haven't yet added this feature. Still needs work.
	 */
	private void updateSmellFieldAt(Point position) {
		Critter critter = getCritterAt(position);

		if (critter != null) {
			clearSmellField(critter);

			int max_row = Critter.MAX_SMELL_DEPTH;
			int max_col = Critter.MAX_SMELL_WIDTH;

			for (int row = 0; row < max_row; row++) {
				int delta_x = -1 * max_col / 2;
				int delta_y = row + 1;
				if (critter.direction == Direction.EAST
						|| critter.direction == Direction.WEST) {
					delta_x = row + 1;
					delta_y = max_col / 2;
				}

				for (int col = 0; col < max_col; col++) {
					if (critter.direction == Direction.NORTH
							|| critter.direction == Direction.WEST)
						delta_y *= -1;
					int y = position.y + delta_y;
					int x = position.x + delta_x;
					Critter temp = getCritterAt(new Point(x, y));

					// JEN IS THE COOLEST PERSON ON EARTH... SHE IS ALSO A VERY EXCELLENT
					// PROGRAMMER (OBVIOUSLY)... JEN IS GREAT
					// JEN IS SO GREAT YAY! I LOVE JEN
					if (temp != null) {
						Scent scentType;
						if (temp.getClass().equals(critter.getClass()))
							scentType = Scent.SAME;
						else
							scentType = Scent.OTHER;

						obfuscateSmell(critter, scentType, new Point(row, col));
					}

					++delta_x;
					if (critter.direction == Direction.WEST
							|| critter.direction == Direction.EAST)
						++delta_y;
				}
			}

		}

	}

	private void clearSmellField(Critter critter) {
		for (int i = 0; i < Critter.MAX_SMELL_DEPTH; i++)
			for (int j = 0; j < Critter.MAX_SMELL_WIDTH; j++)
				critter.smellField[i][j] = Scent.NONE;

	}

	private void obfuscateSmell(Critter critter, Scent scentType, Point position) {

		int row = position.x;
		int col = position.y;
		if (scentType == Scent.OTHER
				|| (scentType == Scent.SAME && critter.smellField[row][col] != Scent.OTHER))
			critter.smellField[row][col] = scentType;

		// check above
		if (row - 1 >= 0
				&& scentType == Scent.OTHER
				|| (scentType == Scent.SAME && critter.smellField[row - 1][col] != Scent.OTHER))
			critter.smellField[row - 1][col] = scentType;
		// check below
		if (row + 1 < Critter.MAX_SMELL_DEPTH
				&& scentType == Scent.OTHER
				|| (scentType == Scent.SAME && critter.smellField[row + 1][col] != Scent.OTHER))
			critter.smellField[row + 1][col] = scentType;
		// check left
		if (col - 1 >= 0
				&& scentType == Scent.OTHER
				|| (scentType == Scent.SAME && critter.smellField[row - 1][col - 1] != Scent.OTHER))
			critter.smellField[row][col - 1] = scentType;
		// check right
		if (col + 1 < Critter.MAX_SMELL_WIDTH
				&& scentType == Scent.OTHER
				|| (scentType == Scent.SAME && critter.smellField[row][col + 1] != Scent.OTHER))
			critter.smellField[row][col + 1] = scentType;

	}

	/**
	 * This takes a Point and calls the getAction() method for the Critter at that
	 * location (if one exists) to determine what action the critter should take
	 * this round. This method also updates the Critter's position, and location
	 * on the grid.
	 * 
	 * @param position
	 * @return
	 */
	public boolean performActionOnCritterAt(Point position) {
		updateCritterView(position);
		Critter critter = getCritterAt(position);
		if (DEBUG) {
			System.out.println("Before action...");
			System.out.print(critter);
		}
		Action action = critter.getAction();

		if (action == Action.HOP) {
			if (critter.FRONT != Type.EMPTY)
				return false;

			Point newPosition = new Point();
			newPosition.setLocation(position);

			if (critter.direction == Direction.EAST) {
				if (position.x + 1 >= columns)
					return false;

				newPosition.x += 1;

			} else if (critter.direction == Direction.WEST) {
				if (position.x - 1 < 0)
					return false;

				newPosition.x -= 1;
			} else if (critter.direction == Direction.SOUTH) {
				if (position.y + 1 >= rows)
					return false;

				newPosition.y += 1;
			} else {
				if (position.y - 1 < 1)
					return false;
				newPosition.y -= 1;
			}

			// set the Critter's current grid reference to null
			setCritterAt(null, position);

			// Update the Critter's new grid reference
			setCritterAt(critter, newPosition);

			inactiveCritterHash.put(newPosition, critter);

			updateCritterView(newPosition);
			return true;
		} else if (action == Action.TURN_LEFT) {
			if (critter.direction == Direction.NORTH)
				critter.direction = Direction.WEST;
			else if (critter.direction == Direction.SOUTH)
				critter.direction = Direction.EAST;
			else if (critter.direction == Direction.EAST)
				critter.direction = Direction.NORTH;
			else
				critter.direction = Direction.SOUTH;

			inactiveCritterHash.put(position, critter);
			updateCritterView(position);

			return true;
		} else if (action == Action.TURN_RIGHT) {
			if (critter.direction == Direction.NORTH)
				critter.direction = Direction.EAST;
			else if (critter.direction == Direction.SOUTH)
				critter.direction = Direction.WEST;
			else if (critter.direction == Direction.EAST)
				critter.direction = Direction.SOUTH;
			else
				critter.direction = Direction.NORTH;

			inactiveCritterHash.put(position, critter);
			updateCritterView(position);

			return true;
		} else if (action == Action.INFECT){
			if (critter.FRONT == Type.OTHER) {
				Point otherPosition = new Point();
				otherPosition.setLocation(position);

				if (critter.direction == Direction.NORTH)
					otherPosition.y -= 1;
				else if (critter.direction == Direction.SOUTH)
					otherPosition.y += 1;
				else if (critter.direction == Direction.EAST)
					otherPosition.x += 1;
				else
					otherPosition.x -= 1;

				Critter newCritter = null;

				try {
					newCritter = critter.getClass().newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// update the population counters
				int otherPop = populationCounters.get(
						getCritterAt(otherPosition).getClass()).intValue();
				populationCounters.put(getCritterAt(otherPosition).getClass(),
						new Integer(--otherPop));

				int newPop = populationCounters.get(critter.getClass()).intValue();
				populationCounters.put(critter.getClass(), new Integer(++newPop));

				// Set the new Critter's grid reference
				setCritterAt(newCritter, otherPosition);

				inactiveCritterHash.put(otherPosition, getCritterAt(otherPosition));
				inactiveCritterHash.put(position, critter);

				updateCritterView(position);
				updateCritterView(otherPosition);

				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * A simple text-based representation of the grid
	 * 
	 */
	public void printGrid() {
		for (int i = 0; i < rows; i++) {
			System.out.println();
			for (int j = 0; j < columns; j++) {
				if (grid[i][j] != null)
					System.out.print(grid[i][j].getSymbol());
				else
					System.out.print('_');
			}
		}
	}

	/**
	 * Udates the state of the grid wihout calling any critter actions. Used to
	 * ensure that all Critters on the grid have been updated
	 * 
	 */
	public void updateGridState() {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				if (grid[i][j] != null)
					updateCritterView(new Point(j, i));

	}

	/**
	 * This nifty little function returns a randomized set of the integers from 0
	 * to max-1. Since the sequence is completely random, it is possible to get a
	 * number sequence in ascending or descending order. However this is unlikely.
	 * No number is repqeated and all integers in the range will be included by
	 * the sequence.
	 * 
	 * @param max
	 * @return
	 */
	public int[] generateRandomSequence(int max) {
		Random random = new Random(System.currentTimeMillis());
		int[] sequence = new int[max];

		int seed = random.nextInt(max);

		int delta = random.nextInt(max);
		while (delta < 2)
			delta = random.nextInt(max);
		// System.out.println("seed = " + seed + "\ndelta = " + delta);
		int value = seed;
		for (int i = 0; i < max; i++) {
			sequence[i] = value;
			value = (value + delta) % max;
			if (value == seed) {
				value = (value + 1) % max;
				seed = value;
			}
		}

		return sequence;
	}

	public Map<Class<? extends Critter>, Integer> getPopulationCounters() {
		return populationCounters;
	}

}
