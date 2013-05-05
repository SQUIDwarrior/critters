/*
 * Copyright (c) 2006 Shafik Amin
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

public class Hawk extends Critter
{
	private static int count;
	private static double prob;
	private static int totalcount = 0;
	private static boolean flag = false;
	private static long time;




    @Override
    public Action getAction() {
        flag = true;
        if(FRONT == Type.SAME && BACK == Type.EMPTY && LEFT == Type.EMPTY && RIGHT == Type.EMPTY)
        {
                if ( Math.random() < 0.5 )
                        return Action.TURN_LEFT;
                return Action.TURN_RIGHT;

        }

        if(FRONT ==  Type.SAME && (RIGHT ==  Type.SAME || RIGHT ==  Type.WALL)
        && (LEFT ==  Type.SAME || LEFT ==  Type.WALL))
        {
                if(Math.random() < 0.5)
                        return Action.TURN_LEFT;
                return Action.TURN_RIGHT;

        }
        if(FRONT == Type.OTHER && BACK == Type.SAME)
                return Action.INFECT;


        if(FRONT == Type.EMPTY && BACK == Type.EMPTY
        && RIGHT == Type.EMPTY && LEFT == Type.EMPTY)
        {
                prob *= 100;
                prob = (prob + 1) % 9;  //change this magic number to tweak, the lower, the more active
                prob /= 100;

                if (Math.random() < prob)
                        return  Action.INFECT;

                if (Math.random() < 0.8)
                        return  Action.HOP;

                if ( Math.random() < 0.5)
                        return Action.TURN_LEFT;
                return Action.TURN_RIGHT;
        }

        if (FRONT == Type.EMPTY && LEFT != Type.OTHER && RIGHT != Type.OTHER)
                return Action.HOP;
        if (FRONT ==  Type.OTHER)
                return Action.INFECT;

        // basically just simple 'intelligence'
        else
        {
                if (RIGHT == Type.OTHER)
                        return Action.TURN_RIGHT;
                if (LEFT == Type.OTHER)
                        return  Action.TURN_LEFT;
                if (BACK == Type.OTHER)
                {
                        if (Math.random() < 0.5)
                                return Action.TURN_LEFT;
                        return Action.TURN_RIGHT;
                }

                if (FRONT == Type.WALL)
                {
                        if(LEFT == Type.WALL)
                                return Action.TURN_RIGHT;
                        if(RIGHT == Type.WALL)
                                return Action.TURN_LEFT;
                }

                if (FRONT == Type.SAME)
                {
                        if (BACK == Type.SAME)
                        {
                                if(LEFT == Type.EMPTY)
                                        return Action.TURN_LEFT;
                                if(RIGHT == Type.EMPTY)
                                        return Action.TURN_RIGHT;
                        }
                        return Action.INFECT;
                }

                if ( Math.random() < 0.5)
                        return Action.TURN_LEFT;
                return Action.TURN_RIGHT;
        }
    }

    @Override
    public char getSymbol() {
        if(Math.random() < 0.5)
                return '~';
        else
                return '^';
    }

    @Override
    public String getDescription() {
        return "Hawk is an aggressive Critter that has some basic intelligence, " +
        		"as well as some randomness in movement built-in.";
    }
}
