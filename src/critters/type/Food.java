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

public class Food extends Critter {

	@Override
	public Action getAction() {
		return Action.TURN_LEFT;
	}

	@Override
	public char getSymbol() {
		return '+';
	}

	@Override
	public String getDescription() {
		return "Less of a critter and more of a meal, Food is simply that: fodder for any other " +
				"critter to infect without risk.";
	}

}
