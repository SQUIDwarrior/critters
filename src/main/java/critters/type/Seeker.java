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
package critters.type;

import java.util.Random;

import critters.Critter;

public class Seeker extends Critter {
	
	public static enum Corner {NE, NW, SE, SW};
	public static int roundCounter = 1;
	private int lifeCounter;
	private int previousRound;
	
	private static int metomorphosisTime = 1000;
	private int moveTimer = 0;
	private Random random1 = new Random(System.currentTimeMillis());
	private Corner bestCorner;
        private static Corner randomCorner;
	private static int[] corners = new int[4];
	
	public Seeker()
	{
		if(roundCounter < 50)
		{
			// get a random corner
			Corner corner = getCornerBasedOnDirection();
			
			// increment the correct corner index
			corners[corner.ordinal()]++;
		}
	}
	
	@Override
	public Action getAction() {
		if(previousRound < roundCounter)
                    previousRound = roundCounter;
		else
                    roundCounter++;
		
		lifeCounter++;
		
		
		while(roundCounter < 300)
                    return clusterAtBestCorner();		
                
                
                
                while(roundCounter % 600 < 300)
                    return clusterAtCorner(randomCorner);
		
                randomCorner = getRandomCorner();
		return defenseBehavior();
		
		
	}

	@Override
	public char getSymbol() {
		
		if(direction == Direction.NORTH)
		return '^';
	else if(direction == Direction.SOUTH)
		return 'V';
	else if(direction == Direction.EAST)
		return '>';
	else
		return '<';
		
	}
	
	private Action attackBehavior(int type)
	{
		if(FRONT == Type.OTHER)			
		{			
			return Action.INFECT;
		}
		else if(LEFT == Type.OTHER)
		{			
			return Action.TURN_LEFT;
		}
		else if(RIGHT == Type.OTHER)
		{			
			return Action.TURN_RIGHT;
		}
		else if(BACK == Type.OTHER)
		{			
			return Action.HOP;
		}
		else if(FRONT != Type.EMPTY)
		{		
			
			if(type == 0)
			{
				int bleh = random1.nextInt(100);
				if(bleh < 50)
					return Action.TURN_RIGHT;
				else
					return Action.TURN_LEFT;
			}
			else 
			{	moveTimer++;			
				if(moveTimer < 2)				
				{					
					if(LEFT != Type.EMPTY && RIGHT != Type.EMPTY && BACK != Type.EMPTY)				
						return defenseBehavior();				
					else if(direction == Direction.NORTH)
					{
						if(bestCorner == Corner.NE || bestCorner == Corner.SE)			
							return Action.TURN_RIGHT;			
						else 
							return Action.TURN_LEFT;
						
					}
					else if(direction == Direction.SOUTH)
					{
						if(bestCorner == Corner.NW || bestCorner == Corner.SW)			
							return Action.TURN_RIGHT;			
						else 
							return Action.TURN_LEFT;
					}
					else if(direction == Direction.EAST)
					{
						if(bestCorner == Corner.SE || bestCorner == Corner.SW)			
							return Action.TURN_RIGHT;			
						else 
							return Action.TURN_LEFT;
					}
					else
					{
						if(bestCorner == Corner.NE || bestCorner == Corner.NW)			
							return Action.TURN_RIGHT;			
						else 
							return Action.TURN_LEFT;
					}
				}				
				else
				{										
					return defenseBehavior();
				}
			}
		}		
		else
		{
			moveTimer = 0;		
			return Action.HOP;
		}
	}
	
	private Action defenseBehavior()
	{
		if(FRONT == Type.OTHER)
			return Action.INFECT;
		else if(LEFT == Type.OTHER || BACK == Type.OTHER)
			return Action.TURN_LEFT;
		else if(RIGHT == Type.OTHER)
			return Action.TURN_RIGHT;
		else if(FRONT != Type.EMPTY)
		{	
			if(LEFT != Type.EMPTY && RIGHT != Type.EMPTY && BACK != Type.EMPTY)
				return Action.INFECT;
			else if(LEFT != Type.EMPTY)
				return Action.TURN_RIGHT;
			else if(RIGHT != Type.EMPTY)
				return Action.TURN_LEFT;
			else
			{
				int bleh = random1.nextInt(100);
				if(bleh < 50)
					return Action.TURN_RIGHT;
				else
					return Action.TURN_LEFT;
			}
		}
		else if(LEFT != Type.EMPTY)
		{
			if(BACK != Type.EMPTY || RIGHT != Type.EMPTY)
				return Action.INFECT;
			else
				return Action.TURN_RIGHT;
		}
		else if(RIGHT != Type.EMPTY)
		{
			if(BACK != Type.EMPTY || LEFT != Type.EMPTY)
				return Action.INFECT;
			else
				return Action.TURN_LEFT;
		}
		else 
			return Action.INFECT;
	}
	
	private Action clusterAtBestCorner()
	{
            updateBestCorner();
		// Move to a wall; infect any critters on the way
		return turnTowardsCorner(bestCorner);
	}
        
        private Action clusterAtCorner(Corner corner)
        {
            
            return turnTowardsCorner(corner);
        }
        
        private Corner getRandomCorner()
        {
            return Corner.values()[new Random().nextInt(4)];
        }
	
	private Action turnTowardsCorner(Corner corner)
	{
		
		if(direction == Direction.NORTH)
		{
			if(corner == Corner.NE || corner == Corner.NW)			
				return attackBehavior(1);			
			else if (corner == Corner.SW)
				return Action.TURN_LEFT;
			else
				return Action.TURN_RIGHT;
			
		}
		else if(direction == Direction.SOUTH)
		{
			if(corner == Corner.SE || corner == Corner.SW)
				return attackBehavior(1);
			else if (corner == Corner.NE)
				return Action.TURN_LEFT;
			else
				return Action.TURN_RIGHT;
		}
		else if(direction == Direction.EAST)
		{
			if(corner == Corner.NE || corner == Corner.SE)
				return attackBehavior(1);
			else if (corner == Corner.NW)
				return Action.TURN_LEFT;
			else
				return Action.TURN_RIGHT;
		}
		else
		{
			if(corner == Corner.NW || corner == Corner.SW)
				return attackBehavior(1);
			else if (corner == Corner.SE)
				return Action.TURN_LEFT;
			else
				return Action.TURN_RIGHT;
		}
		
	}
		
	private void updateBestCorner()
	{
		int NE = corners[Corner.NE.ordinal()];
		int SE = corners[Corner.SE.ordinal()];
		int NW = corners[Corner.NW.ordinal()];
		int SW = corners[Corner.SW.ordinal()];
		
		Corner corner = Corner.NE;
		int max = NE;
		if(SE > max)
		{
			max = SE;
			corner = Corner.SE;
		}
		
		if(NW > max)
		{
			max = NW;
			corner = Corner.NW;
		}
		
		if(SW > max)
		{
			max = SW;
			corner = Corner.SW;
		}
		
		// increment the counter once the best is found
		corners[corner.ordinal()]++;
		
		bestCorner = corner;
		
		
		
		
	}
	
	private Corner getCornerBasedOnDirection()
	{
		int bleh = random1.nextInt(100);
		if(direction == Direction.NORTH)
		{
			if (bleh < 50)
				return Corner.NE;
			else
				return Corner.NW;
		}
		else if(direction == Direction.SOUTH)
		{
			if(bleh < 50)
				return Corner.SE;
			else
				return Corner.SW;
		}
		else if(direction == Direction.EAST)
		{

			if(bleh < 50)
				return Corner.SE;
			else
				return Corner.NE;
		}
		else
		{

			if(bleh < 50)
				return Corner.SW;
			else
				return Corner.NW;
		}
	}
	
	private Action getRandomTurn()
	{
		int bleh = random1.nextInt(100);
		if(bleh < 50)
			return Action.TURN_RIGHT;
		else
			return Action.TURN_LEFT;
	}

	@Override
	public String getDescription() {
		return "Multi-behavior critter.  Attempts to collect at a corner in a defensive posture, then " +
				"begins coordinated movements around the board.";
	}

	

}
