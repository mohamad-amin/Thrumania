package thrumania.gui;

import com.sun.javafx.tk.*;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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


    public BottomPanel() {
        this.setLocation(0, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.addMouseListener(new MymouseListener());
        toolkit.getBestCursorSize(1, 11);
        setCursor(toolkit.createCustomCursor(
                ImageUtils.getImage("cursorBottomPanel.png"),
                new Point(0,0),"custom cursor"));

        //  Button button1 = new Button("sina", "OceanBottomPanel.png", 0 ,100 , new Dimension(50,50));
//        this.add(button1);
//        this.add( new ImageIcon(ImageUtils.getImage("rightPanel.jpg")))
//        JLabel label = new JLabel();
//        label.setBounds(0,0,d.width , d.height);
//        label.setIcon(new ImageIcon(ImageUtils.getImage("rightPanel.jpg")));
//        this.add(label);


    }
    private boolean isInSideTheRange(int elementXcord1 , int elementYcord1, int elementXcord2 , int elementyCord2 , int mouseXcord , int mouseYcord ){
        if( elementXcord1 <= mouseXcord && elementXcord2 >= mouseXcord)
            if ( elementYcord1 <= mouseYcord && elementyCord2 >= mouseYcord)
                return true;
        return  false;
    }
    private void findingSelectedObject(int mouseXcord , int mouseYcord){
        int elementCounter= 1 ;

        //checking Deep sea
        if ( isInSideTheRange(elementCounter * spaceBetweenElements, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize , d.height/4 + elementsSize , mouseXcord, mouseYcord  ))
            this.selectedElement = elements.DEEP_SEA;
        // checking shallow sea
        elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize , d.height/4 + elementsSize , mouseXcord, mouseYcord  ))
            this.selectedElement = elements.SHALLOW_SEA;
            // cehcking small fish
            elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize , d.height/4 + elementsSize , mouseXcord, mouseYcord  ))
                this.selectedElement = elements.FISH;
            // checking tree
        elementCounter += 2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize , d.height/4 + elementsSize , mouseXcord, mouseYcord  ))
            this.selectedElement = elements.TREE;
        // checking stone mine
            elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements , d.height/4 ,elementCounter * spaceBetweenElements + elementsSize+10 , d.height/4 + elementsSize + 10, mouseXcord, mouseYcord  ))
            this.selectedElement = elements.STONE_MINE;
            // checking gold mine
            elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize + 10 , d.height/4 + elementsSize  , mouseXcord, mouseYcord  ))
            this.selectedElement = elements.GOLD_MINE;
        // chekcing high  land
            elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize + 10 , d.height/4 + elementsSize, mouseXcord, mouseYcord  ))
            this.selectedElement = elements.HIGH_ALTITTUDE_LAND;
        // checking low land
        elementCounter +=2;
        if ( isInSideTheRange(elementCounter * spaceBetweenElements + 10, d.height/4 ,elementCounter * spaceBetweenElements + elementsSize + 10 , d.height/4 + elementsSize , mouseXcord, mouseYcord  ))
            this.selectedElement = elements.LOW_ALTITTUDE_LAND;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int elementCounter = 1;

        g.drawImage(ImageUtils.getImage("rightPanel_Fotor.jpg"), 0, 0, d.width, d.height, null);
        g.drawImage(ImageUtils.getImage("OceanBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("seaBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("smallFish1BottomPanel.png"),  elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("treeBottomPanel.png"), elementCounter * spaceBetweenElements, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("quarryBottomPanel.png"), elementCounter * spaceBetweenElements , d.height / 4, elementsSize + 10, elementsSize + 10, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("GoldBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("higAlttitudeLandBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("lowAltitdueLandBottomPanel.png"), elementCounter * spaceBetweenElements + 10, d.height / 4, elementsSize, elementsSize, null);

        repaint();
        //lowAltitdueLandBottomPanel.png
//
    }

    class MymouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            findingSelectedObject(e.getX() , e.getY());
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
