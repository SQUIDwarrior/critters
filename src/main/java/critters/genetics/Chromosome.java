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

import java.util.Random;

public class Chromosome {

	public static final int GENE_LENGTH = 16;
	public static final int MAX_GENE_MUTATIONS = 4;
	public static final int CROSSOVER = 4;
	
	private String gene;
	
	public Chromosome()
	{
		gene = generateRandomGene(GENE_LENGTH);
	}
	
	public Chromosome(String gene) {
		this.gene = gene;
	}
	
	public String getGene()
	{
		return gene;
	}


	public void mutate() {
		Random r = new Random(System.currentTimeMillis() + System.nanoTime());
		int mutations = r.nextInt(MAX_GENE_MUTATIONS + 1);
		for(int i = 0; i < mutations; i++)
		{
			int index = r.nextInt(GENE_LENGTH);
			int val = r.nextInt(2);

			String s1 = gene.substring(0, index);
			String s2 = gene.substring(index + 1);
			gene = s1 + val + s2;
		}
	}
	
	public String toString()
	{
		return gene;
	}
	
	public static Chromosome combine(Chromosome ac, Chromosome bc) {
		String gene1 = ac.getGene();
		String g1_1 = gene1.substring(0, CROSSOVER);
		String g1_2 = gene1.substring(CROSSOVER);

		String gene2 = bc.getGene();
		String g2_1 = gene2.substring(0, CROSSOVER);
		String g2_2 = gene2.substring(CROSSOVER);

		String child1 = g1_1 + g2_2;
		String child2 = g2_1 + g1_2;
		Random r = new Random(System.currentTimeMillis());
		return (r.nextInt(2) == 0) ? new Chromosome(child1) : new Chromosome(child2);
		
	}
	
	public static String generateRandomGene(int size) {
		Random r = new Random(System.currentTimeMillis() + System.nanoTime());
		
		String gene = "";
		for(int i = 0; i < size; i++)
		{
			gene += r.nextInt(2) + "";
		}
		
		return gene;
	}
}