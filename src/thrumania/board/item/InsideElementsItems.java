package thrumania.board.item;

import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by AMIR on 6/28/2016.
 */
public abstract class InsideElementsItems extends JLabel {
    protected PlayBottomPanel playBottomPanel;
    protected boolean b1IsSelected = false;
    protected boolean b2IsSelected = false;
    protected boolean b3IsSelected = false;
    protected boolean b4IsSelected = false;
    protected boolean b5IsSelected = false;
    protected boolean b6IsSelected = false;
    protected boolean b7IsSelected = false;
    protected boolean b8IsSelected = false;
    protected boolean b9IsSelected = false;
    protected Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE,
            getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    protected int elementsSize = 50;
    protected int spaceBetweenElements = 50;

    public abstract void paintingOptions(Graphics g);

    public abstract void findingSelectedObject(int x, int y);
}
