package thrumania.gui;

import thrumania.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 */
public class BottomPanel extends JPanel {
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, getToolkit().getScreenSize().height - Constants.Drawer_HIGHT *Constants.CELL_SIZE );
    public BottomPanel()  {
        this.setLocation(0 , Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);

    }
}
