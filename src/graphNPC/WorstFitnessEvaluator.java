package graphNPC;

import org.jgap.FitnessEvaluator;
import org.jgap.IChromosome;

public class WorstFitnessEvaluator implements FitnessEvaluator {

    @Override
    public boolean isFitter(final double fitness1, final double fitness2) {
        return fitness1 < fitness2;
    }

    @Override
    public boolean isFitter(IChromosome chrom1, IChromosome chrom2) {
        return isFitter(chrom1.getFitnessValue(), chrom2.getFitnessValue());
    }
}