package graphNPC.MEDS;

import graph.Graph;
import graphNPC.BrutalManager;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.BooleanGene;

public class MEDSManager {

    private static final int POPULATION = 500;
    private static final int GENERATIONS = 100;
    private Graph graph;
    private Configuration conf;
    private MEDSFitnessFunction fitnessFunction;

    public MEDSManager(Graph graph) {
        this.graph = graph;
        this.fitnessFunction = new MEDSFitnessFunction(this.graph);
    }

    public MEDSResult findGASolution() throws InterruptedException {
        Configuration.reset();

        try {
            //Inizializzo l'oggetto Configuration
            this.conf = new MEDSConfiguration();
            this.conf.setPopulationSize(POPULATION);
            this.conf.setFitnessFunction(this.fitnessFunction);

            //Creo il cromosoma modello e lo imposto come cromosoma di base
            IChromosome modelChromosome = createModelChromosome();
            this.conf.setSampleChromosome(modelChromosome);

            //Creo la mia popolazione di individui
            IChromosome[] population = new IChromosome[POPULATION];

            for (int i = 0; i < POPULATION; i++) {
                population[i] = clone(modelChromosome);
            }

            //Faccio evolvere la mia popolazione per 100 generazioni
            Genotype genotype = new Genotype(this.conf, new Population(this.conf, population));
            for (int i = 0; i < GENERATIONS; i++) {
                genotype.evolve();
                Thread.sleep(0);
            }

            //Ottengo l'individuo migliore
            IChromosome best = genotype.getFittestChromosome();

            //Ritorno null nel caso in cui l'individuo migliore non sia un edge dominating set
            if (best.getFitnessValue() == Double.MAX_VALUE) {
                return null;
            }

            //Ritorno il risultato
            return new MEDSResult(best, graph);

        } catch (InvalidConfigurationException ex) {
            return null;
        }
    }

    public MEDSResult findBFSOptimal() throws InterruptedException {
        int n = this.graph.getEdgesCount();
        boolean[] optimal = BrutalManager.getBest(n, this.fitnessFunction,
                BrutalManager.WeakerFitnessSelector);
        return new MEDSResult(optimal, graph);
    }

    /** Crea il cromosoma modello*/
    private IChromosome createModelChromosome() throws InvalidConfigurationException {

        //Creo un array di geni booleani, uno per ogni arco.
        Gene[] modelGenes = new Gene[this.graph.getEdgesCount()];
        for (int i = 0; i < modelGenes.length; i++) {
            modelGenes[i] = new BooleanGene(this.conf, false);
        }

        //Creo e restituisco il cromosoma modello
        return new Chromosome(this.conf, modelGenes);
    }

    /** Clona un cromosoma mescolandone i geni (tranne il primo)*/
    private IChromosome clone(IChromosome model) throws InvalidConfigurationException {

        //Ottengo i geni del cromosoma modello
        Gene[] modelGenes = model.getGenes();

        //Ne creo una copia
        Gene[] copy = new Gene[modelGenes.length];
        for (int k = 0; k < copy.length; k++) {
            copy[k] = modelGenes[k].newGene();
            copy[k].setAllele(modelGenes[k].getAllele());
        }

        //Ritorno il nuovo cromosoma
        return new Chromosome(this.conf, copy);
    }
}
