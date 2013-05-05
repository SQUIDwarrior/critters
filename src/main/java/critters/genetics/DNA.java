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
package critters.genetics;

import java.util.ArrayList;
import java.util.Random;


public class DNA {

	public static final int MAX_CHROMOSOMES = 4;
	private ArrayList<Chromosome> helix;

	public DNA() {
		helix = new ArrayList<Chromosome>();
		for (int i = 0; i < MAX_CHROMOSOMES; i++) {
			helix.add(new Chromosome());
		}
	}

	public DNA(ArrayList<Chromosome> dna) {
		if (dna != null && !dna.isEmpty()) {
			helix = new ArrayList<Chromosome>(dna);
		} else {
			throw new IllegalArgumentException("DNA cannot be empty or null");
		}
	}

	public ArrayList<Chromosome> getDNA() {
		return helix;
	}

	public void mutate() {
		Random r = new Random(System.currentTimeMillis());

		int i = r.nextInt(MAX_CHROMOSOMES);

		helix.get(i).mutate();
	}

	public static DNA combine(DNA a, DNA b) {
		ArrayList<Chromosome> newStrand = new ArrayList<Chromosome>();
		for (int i = 0; i < DNA.MAX_CHROMOSOMES; i++) {
			Chromosome aC = a.getDNA().get(i);
			Chromosome bC = b.getDNA().get(i);

			Chromosome newC = Chromosome.combine(aC, bC);
			newStrand.add(newC);
		}

		return new DNA(newStrand);
	}
	
	public String toString()
	{
		String str = "";
		for(int i = 0; i < MAX_CHROMOSOMES; i++)
		{
			str += "Chromosome " + i + "=" + helix.get(i) + "\n";
		}
		return str.substring(0, str.length() - 1);
	}

	public static void main(String[] args) {

		DNA d1 = new DNA();
		DNA d2 = new DNA();

		System.out.println("d1=\n" + d1);
		System.out.println("d2=\n" + d2);
		DNA child = DNA.combine(d1, d2);
		System.out.println("child=\n" + child);
		child.mutate();
		System.out.println("child mutated=\n" + child);
	}

}
