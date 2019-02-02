package graphNPC.MEDS;

import graphNPC.WorstFitnessEvaluator;
import org.jgap.Configuration;
import org.jgap.InvalidConfigurationException;
import org.jgap.NaturalSelector;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;

public class MEDSConfiguration extends Configuration {

    private static final int MUTATION_RATE = 10;

    public MEDSConfiguration() throws InvalidConfigurationException {

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
        this.addGeneticOperator(new CrossoverOperator(this, 0.35d));

        //Imposto l'operatore di mutazione
        this.addGeneticOperator(new MutationOperator(this, MUTATION_RATE));
        //this.setAlwaysCaculateFitness(true);
    }

    private NaturalSelector getBestChromosomesSelector() throws InvalidConfigurationException {
        BestChromosomesSelector bcs = new BestChromosomesSelector(this, 0.90d);
        bcs.setDoubletteChromosomesAllowed(true);
        return bcs;
    }
}