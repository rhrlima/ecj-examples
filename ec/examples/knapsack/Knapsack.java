package ec.examples.knapsack;

import ec.*;
import ec.simple.*;
import ec.util.*;
import ec.vector.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Knapsack extends Problem implements SimpleProblemForm {
	
	private static final String P_INSTANCE = "instance";
	
	private float profits[];
	private float weights[];
	private float maxWeight;
	
	@Override
	public void setup(final EvolutionState state, final Parameter base) {
		super.setup(state, base);
		
		InputStream stream = state.parameters.getResource(base.push(P_INSTANCE), new Parameter(P_INSTANCE));
		
		readInstance(state, stream);
	}
	
	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpopulation, int threadnum) {
            
		if (ind.evaluated) return;
		
		if (!(ind instanceof BitVectorIndividual))
			state.output.fatal("Whoa! It's not a BitVectorIndividual!!!", null);
		
		BitVectorIndividual ind2 = (BitVectorIndividual)ind;

		float sumProfit = 0.0f, sumWeight = 0.0f;
		for (int i = 0; i < ind2.genome.length; i++) {
			if (ind2.genome[i]) {
				sumProfit += this.profits[i];
				sumWeight += this.weights[i];
			}
		}
                
		if (!(ind2.fitness instanceof SimpleFitness))
			state.output.fatal("Whoa! It's not a SimpleFitness!!!", null);

		((SimpleFitness)ind2.fitness).setFitness(
			state, 
			(sumWeight <= maxWeight) ? sumProfit : (maxWeight-sumWeight), //the fitness
			false); //is it ideal?
		
		ind2.evaluated = true;
	}
	
	private void readInstance(EvolutionState state, InputStream stream) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
			int nItems;
			float wMax;
			String temp[];
			
			temp = br.readLine().split(" ");
			nItems	= Integer.parseInt(temp[0]);
			wMax	= Float.parseFloat(temp[1]);
			
			this.profits = new float[nItems];
			this.weights = new float[nItems];
			this.maxWeight = wMax;
			
			for (int i = 0; i < nItems; i++) {
				temp = br.readLine().split(" ");
				this.profits[i] = Float.parseFloat(temp[0]);
				this.weights[i] = Float.parseFloat(temp[1]);
			}
			
			br.close();
		} catch (IOException e) {
			state.output.fatal("Houve um erro durante a leitura do arquivo de instancia.");
		}
	}
}