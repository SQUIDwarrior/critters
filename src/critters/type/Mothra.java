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

public class Mothra extends Critter {

	public static int roundCounter = 1;
	private int lifeCounter;
	private int previousRound;
	
	private static int metomorphosisTime = 500;
	
	public Mothra()
	{
		lifeCounter = 0;
	}
	
	private Random random1 = new Random(System.currentTimeMillis());
	@Override
	public Action getAction() {

		if(previousRound < roundCounter)
			previousRound = roundCounter;
		else
			roundCounter++;
		
		lifeCounter++;
	
		
		if(lifeCounter < metomorphosisTime)
		{
			if(FRONT == Type.OTHER)
				return Action.INFECT;
			else if(LEFT == Type.OTHER || BACK == Type.OTHER)
				return Action.TURN_LEFT;
			else if(RIGHT == Type.OTHER)
				return Action.TURN_RIGHT;
			else if(FRONT == Type.SAME || FRONT == Type.WALL)
			{			
				if(LEFT == Type.SAME || LEFT == Type.WALL)
					return Action.TURN_RIGHT;
				else if(RIGHT == Type.SAME || RIGHT == Type.WALL)
					return Action.TURN_LEFT;			
				else
					return Action.INFECT;
			}
			else 
				return Action.INFECT;
		}
		else
		{
			if(FRONT == Type.OTHER)
				return Action.INFECT;
			else if(LEFT == Type.OTHER)
				return Action.TURN_LEFT;
			else if(RIGHT == Type.OTHER)
				return Action.TURN_RIGHT;
			else if(BACK == Type.OTHER)
				return Action.HOP;
			else if(FRONT == Type.WALL || FRONT == Type.SAME)
			{			
				int bleh = random1.nextInt(100);
				if(bleh < 50)
					return Action.TURN_RIGHT;
				else
					return Action.TURN_LEFT;
			}
			else
				return Action.HOP;
		}
		
	}

	@Override
	public char getSymbol() {
		
		if(lifeCounter < metomorphosisTime)
		{
			return '@';
		}
		else
		{
			if(lifeCounter % 2 == 0)
			{
				return '+';
			}
			else
			{
				return 'x';
			}
		}
		
	}

	@Override
	public String getDescription() {
		return "Multi-stage critter that begins life similar to FlyTrap, but after enough time has passed" +
				" morphes into a more agressive critter like Hawk.";
	}

	

}
