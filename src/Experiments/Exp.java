package Experiments;

import Util.Coord;
import Util.GraphPane;
import Util.Population;
import Util.SubjectivePane;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2018-01-02.
 */
public class Exp {
    public int height = 300;
    public int width = 600;
    public int gens = 600;

    private int sampleSize = 15;

    private int experiment = 4;

    private List<Coord> points1 = new ArrayList<>();
    private List<Coord> sub1 = new ArrayList<>();
    private List<Coord> points2 = new ArrayList<>();
    private List<Coord> sub2 = new ArrayList<>();

    public Exp() {
        GraphPane graphPane = new GraphPane(width,height);

        evolvePops();

        graphPane.addPopulation(points1, Color.red);
        graphPane.addPopulation(points2, Color.green);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(graphPane, BorderLayout.CENTER);

        JPanel subjs = new JPanel(new GridLayout(2, 1));
        subjs.add(new SubjectivePane(sub1, Color.red));
        subjs.add(new SubjectivePane(sub2, Color.green));

        frame.add(subjs, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void evolvePops() {
        Population pop1 = new Population(0);
        Population pop2 = new Population(0);

        int widthScale = width/gens;
        int heightScale = height/100;

        for (int i = 0; i < gens; i++) {
            List<Population.Individual> sample1 = pop1.getSample(sampleSize);
            List<Population.Individual> sample2 = pop2.getSample(sampleSize);

            List<Integer> vals1 = pop1.getValues();
            Float subj1 = pop1.getAveSubjective(sample2, experiment);

            List<Integer> vals2 = pop2.getValues();
            Float subj2 = pop2.getAveSubjective(sample1, experiment);

            for (Integer integer : vals1) {
                points1.add(new Coord(i*widthScale, height - (integer*heightScale)));
            }
            for (Integer integer : vals2) {
                points2.add(new Coord(i*widthScale, height - (integer*heightScale)));
            }

            sub1.add(new Coord(i*widthScale, 49 - Math.round(subj1)));
            sub2.add(new Coord(i*widthScale, 49 - Math.round(subj2)));

            if (i == 100) {
                int a = 3;
            }
            pop1.fintessProportionate(sample2, experiment);
            pop2.fintessProportionate(sample1, experiment);
        }
    }

    public static void main(String[] args) {
        new Exp();
    }
}
