package ec.apps.knapsack;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class Knapsack extends Problem implements SimpleProblemForm {

	private final float knapsack;
	private final int numItems;

	public void setup(final EvolutionState state, 
					  final Parameter base)
	{
		super.setup(state, base);

		System.out.println("###" + base.pop().param);
		System.out.println("###" + base.pop().param);

		knapsack = new float[10];
		numItems = 10;
	}

	public void evaluate(final EvolutionState state, 
						 final Individual ind, 
						 final int subpopulation, 
						 final int threadnum)
	{
		if (ind.evaluated) return;

		if (!(ind instanceof BitVectorIndividual))
			state.output.fatal("Whoa! It's not a BitVectorIndividual!!!", null);
		BitVectorIndividual ind2 = (BitVectorIndividual)ind;

		//calcula fitness
		int sum = 0;
		for (int x = 0; x < ind2.genome.length; x++)
			sum += (ind2.genome[x] ? 1 : 0); 

		if (!(ind2.fitness instanceof SimpleFitness))
			state.output.fatal("Whoa! It's not a SimpleFitness!!!", null);
		
		//atribui fitness
		((SimpleFitness)ind2.fitness).setFitness(
			state, 
			((double)sum)/ind2.genome.length, //the fitness
			sum == ind2.genome.length); //is it ideal?
		ind2.evaluated = true;
	}

}