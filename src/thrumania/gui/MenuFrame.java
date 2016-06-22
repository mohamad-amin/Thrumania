package thrumania.gui;

import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 6/22/16.
 */



    /*
    *   play game
    *   play online
    *   exit
    *   load
    *   edith your map
    *   about us
    *


     */
public class MenuFrame extends JFrame {


    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    private int verticalSpace = 100;

    public MenuFrame() throws HeadlessException {
        this.setLayout(null);
        this.setLocation(0,0);
        this.setSize(d);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setFocusable(false);
        Constants.mouseInitializer(this);
        this.add( new MenuPanel());


        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

}
