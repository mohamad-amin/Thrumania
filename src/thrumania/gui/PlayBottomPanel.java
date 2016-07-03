package thrumania.gui;

import thrumania.board.item.InsideElementsItems;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 6/28/16.
 */
public class PlayBottomPanel  extends JPanel  {
    PlayPanel playPanel;
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);

    public PlayBottomPanel(){
        this.setLocation(0, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        System.out.println("hello");
        InsideElementsItems gameSelectedElement = playPanel.getGameSelectedElement();
        if(gameSelectedElement!=null) gameSelectedElement.paintingOptions(g);
    }

    public void setPlayPanel(PlayPanel playPanel) {
        this.playPanel = playPanel;
    }
}
