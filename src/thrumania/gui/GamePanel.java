package thrumania.gui;

import thrumania.board.Map;
import thrumania.utils.Constants;
import thrumania.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by sina on 5/18/16.
 */
public class GamePanel extends JPanel {

    private Map map;
    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    public GamePanel(Map map) {
        this.map = map;
        this.setLayout(null);
        this.setLocation(0,0);
        //TODO : resize this panel because of having our side panels in futures.
        this.setSize(d);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for( int r =0 ; r< map.getWidth(); r++){
            for ( int c =0 ; c < map.getHeight(); c++ ){
                Image image = Toolkit.getDefaultToolkit().getImage("res/images/"+map.getCells()[r][c].getPictureName());
//                Image image = GuiUtils.createImageIcon(, getClass()).getImage();
                g.drawImage(image, map.getCells()[r][c].getPosition().getColumn()*Constants.CELL_SIZE , map.getCells()[r][c].getPosition().getRow() * Constants.CELL_SIZE, Constants.CELL_SIZE , Constants.CELL_SIZE,null);
            }
        }
    }
}
