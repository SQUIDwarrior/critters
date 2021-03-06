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

import critters.genetics.DNA;

public abstract class Critter {	

	public static enum Action {TURN_LEFT, TURN_RIGHT, HOP, INFECT};
	public static enum Direction {NORTH, SOUTH, EAST, WEST};
	public static enum Type {SAME, OTHER, WALL, EMPTY};
	public static enum Scent {SAME, OTHER, NONE};
	
	public static final int MAX_SMELL_DEPTH = 5;
	public static final int MAX_SMELL_WIDTH = 7;
	
	public DNA dna = new DNA();
	
	// These define the the state of each location
	// around the Critter
	public Type FRONT;
	public Type LEFT;
	public Type RIGHT;
	public Type BACK;
	
	// These define the Critter's direction and smell field 
	public Direction direction;
	// 28 29 30 31 32 33 34
	// 21 22 23 24 25 26 27
	// 14 15 16 17 18 19 20
	// 7  8  9  10 11 12 13
	// 0  1  2  3  4  5  6
	public Scent[][] smellField = new Scent[MAX_SMELL_DEPTH][MAX_SMELL_WIDTH];
	
	public Critter()
	{
		direction = Direction.NORTH;
	}
	
	public Critter(DNA dna){
		direction = Direction.NORTH;
		this.dna = dna;
	}
        
	
	public String getStatus()
	{
		String str = this.getClass().getName() + "\n";
		
		str += "FRONT = " + FRONT + "\n";
		str += "LEFT = " + LEFT + "\n";
		str += "RIGHT = " + RIGHT + "\n";
		str += "BACK = " + BACK + "\n";
		
		str += "direction = " + direction + "\n";
		
		
		for(int i = 0; i < MAX_SMELL_DEPTH; i++)
		{
			str += "\n\n";
			for(int j = 0; j < MAX_SMELL_WIDTH; j++)
				str += smellField[i][j] + " ";
		
		}
		
		return str;
	}
		
	public abstract Action getAction();
	
	public abstract char getSymbol();
	
	public abstract String getDescription();
	
	public DNA getDNA()
	{
		return dna;
	}
	
	
}
