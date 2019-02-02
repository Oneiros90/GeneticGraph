package graphNPC.TSP;

import graphNPC.WorstFitnessEvaluator;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.NaturalSelector;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.GABreeder;
import org.jgap.impl.GreedyCrossover;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.SwappingMutationOperator;

public class TSPConfiguration extends Configuration {

    private static final int MUTATION_RATE = 50;

    public TSPConfiguration() throws InvalidConfigurationException {

        //Imposto le configurazioni di default
        this.setBreeder(new GABreeder());
        this.setRandomGenerator(new StockRandomGenerator());
        this.setEventManager(new EventManager());
        this.addNaturalSelector(getBestChromosomesSelector(), false);
        this.setMinimumPopSizePercent(0);
        this.setSelectFromPrevGen(1.0d);
        this.setKeepPopulationSizeConstant(true);
        this.setFitnessEvaluator(new WorstFitnessEvaluator());
        this.setChromosomePool(new ChromosomePool());

        //Imposto l'operatore di crossover
        this.addGeneticOperator(getGreedyCrossover());

        //Imposto l'operatore di mutazione
        this.addGeneticOperator(new SwappingMutationOperator(this, MUTATION_RATE));
        //this.setAlwaysCaculateFitness(true);
    }

    private NaturalSelector getBestChromosomesSelector() throws InvalidConfigurationException {
        BestChromosomesSelector bcs = new BestChromosomesSelector(this, 0.90d);
        bcs.setDoubletteChromosomesAllowed(true);
        return bcs;
    }

    private GreedyCrossover getGreedyCrossover() throws InvalidConfigurationException {
        GreedyCrossover greedy = new GreedyCrossover(this);
        greedy.setStartOffset(0);
        return greedy;
    }
}