package thrumania.gui;

import thrumania.utils.Constants;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 */
public class MiniMapPanel extends JPanel {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE , this.getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);

    public MiniMapPanel() {
        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE , Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.ORANGE);
        this.setLayout(null);
    }
}
