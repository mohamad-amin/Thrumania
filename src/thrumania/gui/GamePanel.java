package thrumania.gui;

import thrumania.board.Map;
import thrumania.main.Controller;
import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
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
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        }, 200);
    }

    @Override
    public void paint(Graphics g) {
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {
                g.drawImage(
                        ImageUtils.getImage(map.getCells()[r][c].getPictureName()),
                        map.getCells()[r][c].getPosition().getColumn() * Constants.CELL_SIZE,
                        map.getCells()[r][c].getPosition().getRow() * Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        Constants.CELL_SIZE,
                        null);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("hello");
        int row = (e.getY()/Constants.CELL_SIZE);
        int column = (e.getX()/Constants.CELL_SIZE);
        map.changeMap(row,column);
        System.out.println(row);
        System.out.println(column);
        repaint();                                     /////chera timer kar nemikone??
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

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
