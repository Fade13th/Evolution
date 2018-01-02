package Experiments;

import Util.Coord;
import Util.GraphPane;
import Util.Population;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 2018-01-02.
 */
public class Control {
    public int height = 300;
    public int width = 600;
    public int gens = 600;

    public Control () {
        GraphPane graphPane = new GraphPane(width,height);

        graphPane.addPopulation(generatePop(0), Color.red);
        graphPane.addPopulation(generatePop(1), Color.blue);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(graphPane);
        frame.pack();
        frame.setVisible(true);
    }

    private List<Coord> generatePop(int init) {
        List<Coord> points = new ArrayList<>();
        Population pop = new Population(init);
        for (int i = 0; i < gens; i++) {
            List<Integer> vals = pop.getValues();

            int widthScale = width/gens;
            int heightScale = height/100;

            for (Integer integer : vals) {
                points.add(new Coord(i*widthScale, height - (integer*heightScale)));
            }

            pop.mutate();
        }

        return points;
    }

    public static void main(String[] args) {
        new Control();
    }
}
