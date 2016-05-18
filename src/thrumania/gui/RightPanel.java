package thrumania.gui;

import thrumania.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 */
public class RightPanel extends JPanel {

    private  Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE , Constants.Drawer_HIGHT * Constants.CELL_SIZE);

    public RightPanel() {

        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE , 0);
        this.setSize(d);
        this.setBackground(Color.CYAN);
        this.setLayout(null);

    }
}
