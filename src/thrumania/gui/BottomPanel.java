package thrumania.gui;

import com.sun.javafx.tk.*;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

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

    static enum elements {
        STONE_MINE, GOLD_MINE, HIGH_ALTITTUDE_LAND, LOW_ALTITTUDE_LAND, TREE, FISH, DEEP_SEA, SHALLOW_SEA
    }

    private elements selectedElement;
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

    public BottomPanel() {
        this.setLocation(0, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.setVisible(true);
        this.addMouseListener(new MymouseListener());
        this.mouseInitializer();
        //  Button button1 = new Button("sina", "OceanBottomPanel.png", 0 ,100 , new Dimension(50,50));
//        this.add(button1);
//        this.add( new ImageIcon(ImageUtils.getImage("rightPanel.jpg")))
//        JLabel label = new JLabel();
//        label.setBounds(0,0,d.width , d.height);
//        label.setIcon(new ImageIcon(ImageUtils.getImage("rightPanel.jpg")));
//        this.add(label);

    }

    private boolean isInSideTheRange(int elementXcord1, int elementYcord1, int elementXcord2, int elementyCord2, int mouseXcord, int mouseYcord) {
        if (elementXcord1 <= mouseXcord && elementXcord2 >= mouseXcord)
            if (elementYcord1 <= mouseYcord && elementyCord2 >= mouseYcord)
                return true;
        return false;
    }
    private void mouseInitializer(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.getBestCursorSize(1, 11);
        setCursor(toolkit.createCustomCursor(
                ImageUtils.getImage("cursorBottomPanel.png"),
                new Point(0, 0), "custom cursor"));

    }
    private void findingSelectedObject(int mouseXcord, int mouseYcord) {
        int elementCounter = 1;

        //checking Deep sea
        if (isInSideTheRange(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.DEEP_SEA;
            deepSeaIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    deepSeaIsSelected = false;
                }

            }, 110);
        }
        // checking shallow sea
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.SHALLOW_SEA;
            shallowSeaIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    shallowSeaIsSelected = false;
                }

            }, 110);
        }
        // cehcking small fish
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.FISH;
            smallFishIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    smallFishIsSelected = false;
                }

            }, 110);


        }
        // checking tree
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.TREE;
            treeIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    treeIsSelected = false;
                }

            }, 110);

        }
        // checking stone mine
        elementCounter += 2;

        if (isInSideTheRange(elementCounter * spaceBetweenElements, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize + 10, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.STONE_MINE;
            stoneMineIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    stoneMineIsSelected = false;
                }

            }, 110);

        }
        // checking gold mine
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.GOLD_MINE;
            goldMineIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    goldMineIsSelected = false;
                }

            }, 110);

        }
        // chekcing high  land
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.HIGH_ALTITTUDE_LAND;
            highLandIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    highLandIsSelected = false;
                }

            }, 110);


        }
        // checking low land
        elementCounter += 2;
        if (isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height / 4, elementCounter * spaceBetweenElements + elementsSize + 10, d.height / 4 + elementsSize, mouseXcord, mouseYcord)) {
            this.selectedElement = elements.LOW_ALTITTUDE_LAND;
            lowLandIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    lowLandIsSelected = false;
                }

            }, 110);

        }
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int elementCounter = 1;

        System.out.println("Here");

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

    }

    class MymouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            findingSelectedObject(e.getX(), e.getY());
            System.out.println(selectedElement);

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
