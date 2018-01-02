package Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Matt on 2018-01-02.
 */
public class Population {
    private int size = 25;
    private int individualSize = 100;
    private int sampleSize = 15;

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

    public void mutate() {
        for (Individual i : individuals) {
            i.mutate();
        }
    }

    private void addIndividual(int initialization) {
        individuals.add(new Individual(initialization));
    }

    private class Individual {
        private int[] val = new int[individualSize];

        private Individual(int initialization) {
            Arrays.fill(val, initialization);
        }

        protected void mutate() {
            for (int i = 0; i < individualSize; i++) {
                if (Math.random() < changeChance) {
                    if (Math.random() < 0.5) {
                        val[i] = 0;
                    } else {
                        val[i] = 1;
                    }
                }
            }
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
