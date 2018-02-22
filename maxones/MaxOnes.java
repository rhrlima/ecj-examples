package ec.apps.maxones;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;

public class MaxOnes extends Problem implements SimpleProblemForm {

	public void evaluate(final EvolutionState state, 
						 final Individual ind, 
						 final int subpopulation, 
						 final int threadnum)
	{
		if (ind.evaluated) return; //don't evaluate the individual if it's already evaluated

		if (!(ind instanceof BitVectorIndividual))
			state.output.fatal("Whoa! It's not a BitVectorIndividual!!!", null);
		BitVectorIndividual ind2 = (BitVectorIndividual)ind;

		int sum = 0;
		for (int x = 0; x < ind2.genome.length; x++)
			sum += (ind2.genome[x] ? 1 : 0); 
		
		if (!(ind2.fitness instanceof SimpleFitness))
			state.output.fatal("Whoa! It's not a SimpleFitness!!!", null);

		((SimpleFitness)ind2.fitness).setFitness(
			state, 
			((double)sum)/ind2.genome.length, //the fitness
			sum == ind2.genome.length); //is it ideal?
		ind2.evaluated = true;

	}

}