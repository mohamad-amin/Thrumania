package thrumania.gui;

import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.InsideElementsItems;
import thrumania.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by sina on 6/28/16.
 */
public class PlayBottomPanel  extends JPanel implements MouseListener {
    Constants.BottomPanelSelected bottomPanelSelected ;
    PlayPanel playPanel;

    public Constants.BottomPanelSelected getBottomPanelSelected() {
        return bottomPanelSelected;
    }

    public void setBottomPanelSelected(Constants.BottomPanelSelected bottomPanelSelected) {
        this.bottomPanelSelected = bottomPanelSelected;
    }

    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, getToolkit().getScreenSize().height - Constants.Drawer_HIGHT * Constants.CELL_SIZE);

    public PlayBottomPanel(){
        this.setLocation(0, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
        this.setSize(d);
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        InsideElementsItems gameSelectedElement = playPanel.getGameSelectedElement();
        if(gameSelectedElement!=null) gameSelectedElement.paintingOptions(g);
    }

    public void setPlayPanel(PlayPanel playPanel) {
        this.playPanel = playPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("hello");
        InsideElementsItems gameSelectedElement = playPanel.getGameSelectedElement();
        if(gameSelectedElement!=null) gameSelectedElement.findingSelectedObject(e.getX(), e.getY());
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

    public void function() {
        switch (bottomPanelSelected){
            case addWorker:
                playPanel.buildWorker((Castle) playPanel.getGameSelectedElement());
                break;
            case addSoldier:
                break;
            case addContainerShip:
                break;
            case addFisherShip:
                break;
        }
    }
}
