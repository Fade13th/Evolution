package Util;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by Matt on 2018-01-03.
 */
public class SubjectivePane extends JPanel{
    List<Coord> coords;
    Color color;

    public SubjectivePane(List<Coord> coords, Color color) {
        this.coords = coords;
        this.color = color;

        setPreferredSize(new Dimension(600,50));
    }

    public void paint(Graphics g) {
        g.setColor(color);

        for (Coord c : coords) {
            g.drawLine(c.x, c.y, c.x, c.y);
        }
    }
}
