package thrumania.gui;

import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sina on 5/18/16.
 * <p>
 * I ( sina ) will implement this class
 */
public class RightPanel extends JPanel {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private int elementSize = 30;
    private int verticalSpaceBetweenElements = 30;
    private int horizontalSpaceBetweenElements = 50;

    public RightPanel() {

        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, 0);
        this.setSize(d);
        this.setBackground(Color.CYAN);
        this.setLayout(null);


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // impelenting arrow keys
        g.drawImage(ImageUtils.getImage("bottomPanel.jpg"), 0, 0, d.width, d.height, null);
        int elementCounter = 1;
        g.drawImage(ImageUtils.getImage("upArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter += 1;
        g.drawImage(ImageUtils.getImage("rightArrowKeyRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        g.drawImage(ImageUtils.getImage("leftArrowKeyRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter +=1 ;
        g.drawImage(ImageUtils.getImage("downArrowKeyRightPanel.png"), d.width / 2 , verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter +=2;
        g.drawImage(ImageUtils.getImage("saveRightPanel.png"), d.width /2 - horizontalSpaceBetweenElements / 2 , verticalSpaceBetweenElements * elementCounter, elementSize , elementSize + 5 , null);
        g.drawImage(ImageUtils.getImage("loadRightPanel.png"), d.width/2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter  , elementSize , elementSize  + 5, null);
        repaint();
    }
}
