package Util;

import java.util.*;

/**
 * Created by Matt on 2018-01-02.
 */
public class Population {
    private int size = 25;
    private int individualSize = 100;

    private float changeChance = 0.005f;

    private List<Individual> individuals;

    public Population(int initialization) {
        individuals = new ArrayList<>();

        while (individuals.size() < size) {
            addIndividual(initialization);
        }
    }

    public int valueControl() {
        return 0;
    }

    public List<Integer> getValues() {
        List<Integer> vals = new ArrayList<>();

        for (Individual i : individuals) {
            vals.add(i.value());
        }

        return vals;
    }

    public Float getAveSubjective(List<Individual> sample) {
        float tot = 0;

        for (Individual i : individuals) {
            tot += f1(i, sample);
        }

        return tot/size;
    }

    public List<Individual> getSample(int size) {
        List<Individual> samples = new ArrayList<>();

        Collections.shuffle(individuals);

        for (int i = 0; i < size; i++) {
            samples.add(individuals.get(i));
        }

        return samples;
    }

    public void mutate() {
        List<Individual> nextGen = new ArrayList<>();

        for (Individual i : individuals) {
            nextGen.add(i.mutate());
        }

        individuals = nextGen;
    }

    public void fintessProportionate(List<Individual> S) {
        List<Individual> nextGen = new ArrayList<>();

        int totFitness = 0;

        for (Individual old : individuals) {
            totFitness += f1(old, S);
        }

        if (totFitness == 0) {
            for (Individual i : individuals) {
                nextGen.add(i.mutate());
            }
        }
        else {
            List<Float> weights = new ArrayList<>();
            float totWeight = 0.0f;

            for (int i = 0; i < individuals.size(); i++) {
                totWeight += (float)f1(individuals.get(i), S) / (float) totFitness;
                weights.add(totWeight);
            }

            for (int i = 0; i < size; i++) {
                float rand = (float) Math.random();

                for (int j = 0; j < size; j++) {
                    if (rand <= weights.get(j)) {
                        nextGen.add(individuals.get(j).mutate());
                        break;
                    }
                }
            }
        }

        individuals = nextGen;
    }

    private int f1(Individual a, List<Individual> S) {
        int tot = 0;

        for (Individual s : S) {
            tot += score1(a, s);
        }

        return tot;
    }

    private int score1(Individual a, Individual b) {
        if (a.value() > b.value()) {
            return 1;
        }
        else return 0;
    }

    private void addIndividual(int initialization) {
        individuals.add(new Individual(initialization));
    }

    public class Individual {
        private int[] val = new int[individualSize];

        private Individual(int initialization) {
            Arrays.fill(val, initialization);
        }

        protected Individual mutate() {
            Individual n = new Individual(0);

            for (int i = 0; i < individualSize; i++) {
                if (Math.random() < changeChance) {
                    if (Math.random() < 0.5) {
                        n.val[i] = 0;
                    } else {
                        n.val[i] = 1;
                    }
                }
                else {
                    n.val[i] = this.val[i];
                }
            }

            return n;
        }

        protected int value() {
            int i = 0;

            for (int j = 0; j < individualSize; j++) {
                i += val[j];
            }

            return i;
        }
    }
}
