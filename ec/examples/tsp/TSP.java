package ec.examples.tsp;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TSP extends Problem implements SimpleProblemForm {

	public static final String P_INSTANCE_FILE = "instance";
	public Parameter defaultBase() {return new Parameter(P_INSTANCE_FILE);}
        
        private int [][]distances;
        
	public void setup(final EvolutionState state, final Parameter base)
	{
		super.setup(state, base);

		Parameter def = defaultBase();
		InputStream stream = state.parameters.getResource(base.push(P_INSTANCE_FILE), 
			def.push(P_INSTANCE_FILE));
                
                readInstance(stream, state);
	}

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
        
        private void readInstance(InputStream stream, EvolutionState state) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
                
                while(br.ready()) {
                    String temp = br.readLine();
                    state.output.warning(temp);
                }
                
                br.close();
            } catch (Exception e) {
                state.output.fatal("Falha ao tentar abrir o arquivo de instância do problema.", null);
            }
        }
}