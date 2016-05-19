package thrumania.gui;

import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 *
 * I ( sina ) will implement this class
 */
public class RightPanel extends JPanel {

    private  Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE , Constants.Drawer_HIGHT * Constants.CELL_SIZE);

    public RightPanel() {

        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE , 0);
        this.setSize(d);
        this.setBackground(Color.CYAN);
        this.setLayout(null);
//        Button button1 = new Button("sina", "OceanBottomPanel.png", 0 ,100 , new Dimension(50,50));
//        this.add(button1);
//        this.add( new ImageIcon(ImageUtils.getImage("rightPanel.jpg")))
//        JLabel label = new JLabel();
//        label.setBounds(0,0,d.width , d.height);
//        label.setIcon(new ImageIcon(ImageUtils.getImage("rightPanel.jpg")));
//        this.add(label);




    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(ImageUtils.getImage("rightPanel.jpg"),0 , 0 , d.width , d.height, null);
        g.drawImage(ImageUtils.getImage("OceanBottomPanel.png") , 50 , 100, 30,30, null);

    }
}
