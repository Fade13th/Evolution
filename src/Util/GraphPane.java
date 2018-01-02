package Util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matt on 2018-01-02.
 */
public class GraphPane extends JPanel {
    private List<Color> cols;
    private List<List<Coord>> points;

    public GraphPane(int width, int height) {
        setSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width,height));
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.WHITE);

        points = new ArrayList<>();
        cols = new ArrayList<>();
    }

    public void addPopulation(List<Coord> pop, Color colour) {
        points.add(pop);
        cols.add(colour);
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);

        for (int i = 0; i < points.size(); i++) {
            g.setColor(cols.get(i));
            for (Coord val : points.get(i)) {
                g.drawLine(val.x, val.y,val.x,val.y);
            }
        }
    }
}
