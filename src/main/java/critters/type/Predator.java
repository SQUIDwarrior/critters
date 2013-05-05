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

public class Predator extends Critter {
	private static int symbol = 0;
	
	public Predator()
	{
		symbol++;
	}

	private Random random1 = new Random(System.currentTimeMillis());
	@Override
	public Action getAction() {
				
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

	@Override
	public char getSymbol() {		
		
		return 'P';
		
	}

	@Override
	public String getDescription() {
		return "Aggressive, but simplistic critter that moves in a straight line until hitting a wall or " +
				"other critter then attempts to infect, or randomly turns left or right.";
	}

}
