package graphNPC;

public class BrutalManager {

    public static boolean[] getBest(int n, BooleanEvaluator e, Selector s) throws InterruptedException {
        boolean[] solution = new boolean[n];

        boolean[] optimal = solution.clone();
        double optimalFitness = e.evaluate(optimal);

        a:
        while (true) {

            //Trovo la prossima soluzione
            int i = n - 1;
            b:
            while (solution[i] == true) {
                solution[i] = false;
                i--;

                //Se è l'ultima, termino la ricerca
                if (i == -1) {
                    break a;
                }
            }
            solution[i] = true;

            //Analizzo la soluzione trovata
            double solutionFitness = e.evaluate(solution);
            if (solutionFitness > 0 && (optimalFitness == -1 || s.isBetter(solutionFitness, optimalFitness))) {
                optimal = solution.clone();
                optimalFitness = solutionFitness;

            }

            Thread.sleep(0);
        }
        return optimal;
    }

    public static int[] getBest(int n, IntegerEvaluator e, Selector s) throws InterruptedException {
        int[] solution = new int[n];
        for (int i = 0; i < n; i++) {
            solution[i] = i;
        }
        int[] optimal = solution.clone();

        double optimalFitness = e.evaluate(optimal);

        while (true) {
            int k = -1;
            for (int i = 1; i < solution.length - 1; i++) {
                if (solution[i] < solution[i + 1] && i > k) {
                    k = i;
                }
            }
            if (k == -1) {
                break;
            }

            int j = -1;
            for (int i = k; i < solution.length; i++) {
                if (solution[i] > solution[k] && i > j) {
                    j = i;
                }
            }

            int swap = solution[k];
            solution[k] = solution[j];
            solution[j] = swap;

            int[] lastSeq = new int[solution.length - k - 1];
            for (int i = 0; i < lastSeq.length; i++) {
                lastSeq[i] = solution[i + k + 1];
            }
            for (int i = 0; i < lastSeq.length; i++) {
                solution[i + k + 1] = lastSeq[lastSeq.length - 1 - i];
            }

            double solutionFitness = e.evaluate(solution);
            if (solutionFitness > 0 && (optimalFitness == -1 || s.isBetter(solutionFitness, optimalFitness))) {
                optimal = solution.clone();
                optimalFitness = solutionFitness;
            }

            Thread.sleep(0);
        }

        return optimal;
    }

    public static final Selector GreaterFitnessSelector = new Selector() {

        @Override
        public boolean isBetter(double f1, double f2) {
            return f1 > f2;
        }
    };

    public static final Selector WeakerFitnessSelector = new Selector() {

        @Override
        public boolean isBetter(double f1, double f2) {
            return f1 < f2;
        }
    };
}

interface Selector {
    public boolean isBetter(double f1, double f2);
}