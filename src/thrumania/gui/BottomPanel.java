package thrumania.gui;

import com.sun.javafx.tk.*;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

/**
 * Created by sina on 5/18/16.
 */
public class BottomPanel extends JPanel {

    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);


    private int elementsSize = 50;
    private int spaceBetweenElements = 50;

    private boolean deepSeaIsSelected = false;
    private boolean shallowSeaIsSelected = false;
    private boolean treeIsSelected = false;
    private boolean smallFishIsSelected = false;
    private boolean stoneMineIsSelected = false;
    private boolean goldMineIsSelected = false;
    private boolean highLandIsSelected = false;
    private boolean lowLandIsSelected = false;
    private  boolean aglicultureIsSelected = false;

    private GamePanel gamePanel ;

    public BottomPanel(GamePanel gamePanel) {
        this.setLocation(0, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addMouseListener(new MymouseListener());
        this.gamePanel = gamePanel;

        //  Button button1 = new Button("sina", "OceanBottomPanel.png", 0 ,100 , new Dimension(50,50));
//        this.add(button1);
//        this.add( new ImageIcon(ImageUtils.getImage("rightPanel.jpg")))
//        JLabel label = new JLabel();
//        label.setBounds(0,0,d.width , d.height);
//        label.setIcon(new ImageIcon(ImageUtils.getImage("rightPanel.jpg")));
//        this.add(label);



    }



    private void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = 1;

        //checking Deep sea
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
             gamePanel.setSelectedElelements(Constants.Elements.DEEP_SEA);
            deepSeaIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    deepSeaIsSelected = false;
                    repaint();
                }

            }, 110);
        }
        // checking shallow sea
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
             this.gamePanel.setSelectedElelements(Constants.Elements.SHALLOW_SEA);
            shallowSeaIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    shallowSeaIsSelected = false;
                    repaint();
                }

            }, 110);
        }
        // cehcking small fish
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            gamePanel.setSelectedElelements( Constants.Elements.FISH);
            smallFishIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    smallFishIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // checking tree
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
             gamePanel.setSelectedElelements(Constants.Elements.TREE);
            treeIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    treeIsSelected = false;
                    repaint();
                }

            }, 110);

        }
        // checking stone mine
        elementCounter += 2;

        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize + 10, mouseXcord, mouseYcord)) {
            gamePanel.setSelectedElelements(Constants.Elements.STONE_MINE);
            stoneMineIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    stoneMineIsSelected = false;
                    repaint();
                }

            }, 110);

        }
        // checking gold mine
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            gamePanel.setSelectedElelements(Constants.Elements.GOLD_MINE);
            goldMineIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {

                    goldMineIsSelected = false;
                    repaint();
                }

            }, 110);

        }
        // chekcing high  land
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            gamePanel.setSelectedElelements( Constants.Elements.HIGH_ALTITTUDE_LAND);
            highLandIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    highLandIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // checking low land
        elementCounter += 2;
        if (IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            gamePanel.setSelectedElelements( Constants.Elements.LOW_ALTITTUDE_LAND);
            lowLandIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    lowLandIsSelected = false;
                    repaint();
                }

            }, 110);

        }
        elementCounter +=2;
        if( IntegerUtils.isInSideTheRangeOfCordinates(elementCounter * spaceBetweenElements + 10 , d.height /4 , elementCounter * spaceBetweenElements + elementsSize , d.height / 4 + elementsSize , mouseXcord , mouseYcord)){
            gamePanel.setSelectedElelements( Constants.Elements.AGRICULTURE);
            aglicultureIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    aglicultureIsSelected = false;
                    repaint();
                }

            }, 110);

        }

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int elementCounter = 1;

        g.drawImage(ImageUtils.getImage("rightPanel.jpg"), 0, 0, d.width, d.height, null);
        if (!deepSeaIsSelected)
            g.drawImage(ImageUtils.getImage("OceanBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        else if (deepSeaIsSelected) {

            g.drawImage(ImageUtils.getImage("OceanBottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);

            //deapSeaIsSelected = false;
        }

        elementCounter += 2;
        if (!shallowSeaIsSelected)
            g.drawImage(ImageUtils.getImage("seaBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        else if (shallowSeaIsSelected) {
            g.drawImage(ImageUtils.getImage("seaBottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);

        }
        elementCounter += 2;
        if (!smallFishIsSelected)
            g.drawImage(ImageUtils.getImage("smallFish1BottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        else if (smallFishIsSelected)
            g.drawImage(ImageUtils.getImage("smallFish1BottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        if (!treeIsSelected)
            g.drawImage(ImageUtils.getImage("treeBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        else if (treeIsSelected)
            g.drawImage(ImageUtils.getImage("treeBottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);

        elementCounter += 2;
        if (!stoneMineIsSelected)
            g.drawImage(ImageUtils.getImage("quarryBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize + 10, elementsSize + 10, null);
        else if (stoneMineIsSelected)
            g.drawImage(ImageUtils.getImage("quarryBottomPanelHoover.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize + 10, elementsSize + 10, null);
        elementCounter += 2;
        if (!goldMineIsSelected)
            g.drawImage(ImageUtils.getImage("GoldBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        else if (goldMineIsSelected)
            g.drawImage(ImageUtils.getImage("GoldBottomPanelHoover.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        if (!highLandIsSelected)
            g.drawImage(ImageUtils.getImage("higAlttitudeLandBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        else if (highLandIsSelected)
            g.drawImage(ImageUtils.getImage("higAlttitudeLandBottomPanelHoover.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        if (!lowLandIsSelected)
            g.drawImage(ImageUtils.getImage("lowAltitdueLandBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        else if (lowLandIsSelected)
            g.drawImage(ImageUtils.getImage("lowAltitdueLandBottomPanelHoover.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        elementCounter +=2;
        if( ! aglicultureIsSelected)
            g.drawImage(ImageUtils.getImage("aglicultureBottomPanel.png"),elementCounter * spaceBetweenElements + 10 , d.height / 4 , elementsSize , elementsSize , null );
        else if( aglicultureIsSelected)
            g.drawImage(ImageUtils.getImage("aglicultureBottomPanelHoover.png"),elementCounter * spaceBetweenElements + 10 , d.height / 4 , elementsSize , elementsSize , null );

//        repaint();
        //lowAltitdueLandBottomPanel.png
//
    }


    class MymouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            findingSelectedObject(e.getX(), e.getY());

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
