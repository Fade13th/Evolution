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

        Map<Integer, List<Integer>> ranking = new TreeMap<>();

        for (int i = 0; i < individuals.size(); i++) {
            int val = individuals.get(i).value();

            if (!ranking.containsKey(val)) {
                ranking.put(val, new ArrayList<Integer>());
            }
                ranking.get(val).add(i);
        }

        SortedSet<Integer> keys = new TreeSet<>(ranking.keySet());

        float slots = 0;
        float slotSize = 1;

        for (Integer key : keys) {
            slots += ranking.get(key).size() * slotSize;

            slotSize++;
        }

        Map<Integer, Float> weights = new HashMap<>();
        float currWeight = 0.0f;

        slotSize = 1;
        for (Integer key : keys) {
            for (Integer ind : ranking.get(key)) {
                currWeight += (slotSize/slots);
                weights.put(ind, currWeight);
            }
            slotSize++;
        }

        for (int i = 0; i < size; i++) {
            float rand = (float) Math.random();

            int key = 0;
            float lowestMatch = 10.0f;

            for (Integer wkey : weights.keySet()) {
                if (weights.get(wkey) < lowestMatch && rand <= weights.get(wkey)) {
                    key = wkey;
                    lowestMatch = weights.get(wkey);
                }
            }

            nextGen.add(individuals.get(key).mutate());
        }

        for(Individual a : nextGen) {
            System.out.println(a.value());
        }
        System.out.println();
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
