package thrumania.gui;

import thrumania.board.Map;
import thrumania.main.Controller;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.peer.MouseInfoPeer;
import java.util.*;
import java.util.Timer;

/**
 * Created by sina on 5/18/16.
 */

public class GamePanel extends JPanel implements MouseInputListener{

    private Map map;
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);;

    public GamePanel(Map map) {
        this.map = map;
        this.setLayout(null);
        this.setLocation(0,0);
        this.setSize(d);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 200);
        System.out.println(this.hasFocus());

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {
                g.drawImage(
                        ImageUtils.getImage("ocean1.jpg"),
                        map.getCells()[r][c].getPosition().getColumn() * Constants.CELL_SIZE,
                        map.getCells()[r][c].getPosition().getRow() * Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        null);
                g.drawImage(
                        ImageUtils.getImage(map.getCells()[r][c].getPictureName()),
                        map.getCells()[r][c].getPosition().getColumn() * Constants.CELL_SIZE,
                        map.getCells()[r][c].getPosition().getRow() * Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        null);
            }
        }
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = (e.getY()/Constants.CELL_SIZE);
        int column = (e.getX()/Constants.CELL_SIZE);
        boolean repaint = map.changeMap(row,column);
        if(repaint) repaint();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        int row = (e.getY()/Constants.CELL_SIZE);
        int column = (e.getX()/Constants.CELL_SIZE);
        boolean repaint = map.changeMap(row,column);
        if(repaint) repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
