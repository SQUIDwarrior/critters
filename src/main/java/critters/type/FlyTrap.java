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

import critters.Critter;


public class FlyTrap extends Critter {

	@Override
	public Action getAction() {
		
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

	@Override
	public char getSymbol() {
			
		return 'T';
		
	}

	@Override
	public String getDescription() {
		return "Non-mobile critter that waits for prey to simply blunder into it.  Clusters of FlyTraps " +
				"will always attempt to face outwards. While FlyTraps do not move, they will rotate towards prey.";
	}
        

}
