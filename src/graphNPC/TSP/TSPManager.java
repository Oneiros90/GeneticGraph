package graphNPC.TSP;

import graph.Graph;
import graphNPC.BrutalManager;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.impl.IntegerGene;

public class TSPManager {

    private static final int POPULATION = 500;
    private static final int GENERATIONS = 100;
    private Graph graph;
    private Configuration conf;
    private TSPFitnessFunction fitnessFunction;

    public TSPManager(Graph g) {
        this.graph = g;
        this.fitnessFunction = new TSPFitnessFunction(this.graph);
    }

    public TSPResult findGASolution() throws InterruptedException {
        Configuration.reset();

        try {
            //Inizializzo l'oggetto Configuration
            this.conf = new TSPConfiguration();
            this.conf.setPopulationSize(POPULATION);
            this.conf.setFitnessFunction(this.fitnessFunction);

            //Creo il cromosoma modello
            IChromosome modelChromosome = createModelChromosome();

            //Imposto il cromosoma modello come cromosoma di base
            this.conf.setSampleChromosome(modelChromosome);

            //Creo la mia popolazione di individui partendo dal cromosoma di base
            IChromosome[] population = new IChromosome[POPULATION];
            for (int i = 0; i < POPULATION; i++) {
                population[i] = cloneAndRandomize(modelChromosome);
            }

            //Faccio evolvere la mia popolazione per 100 generazioni
            Genotype genotype = new Genotype(this.conf, new Population(this.conf, population));
            for (int i = 0; i < GENERATIONS; i++){
                genotype.evolve();
                Thread.sleep(0);
            }

            //Ottengo l'individuo migliore
            IChromosome chromosome = genotype.getFittestChromosome();

            //Ritorno il risultato
            return new TSPResult(chromosome, graph);

        } catch (InvalidConfigurationException ex) {
            return null;
        }
    }

    public TSPResult findBFSOptimal() throws InterruptedException {
        int verCount = this.graph.getVerticesCount();
        int[] optimal = BrutalManager.getBest(verCount, this.fitnessFunction,
                BrutalManager.WeakerFitnessSelector);
        return new TSPResult(optimal, graph);
    }

    /** Crea il cromosoma modello*/
    private IChromosome createModelChromosome() throws InvalidConfigurationException {
        Gene[] modelGenes = new Gene[this.graph.getVerticesCount()];
        for (int i = 0; i < modelGenes.length; i++) {
            modelGenes[i] = new IntegerGene(this.conf, 0, this.graph.getVerticesCount() - 1);
            modelGenes[i].setAllele(i);
        }
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

    private IChromosome cloneAndRandomize(IChromosome model) throws InvalidConfigurationException {
        IChromosome clone = clone(model);
        Gene[] copy = clone.getGenes();

        //Mescolo i geni della copia (il primo gene non viene spostato, essendo il vertice di partenza)
        for (int i = copy.length - 1; i > 0; i--) {
            int random = 1 + (int) (Math.random() * i);

            //Swap
            Gene aux = copy[random];
            copy[random] = copy[i];
            copy[i] = aux;
        }

        return clone;
    }
}
