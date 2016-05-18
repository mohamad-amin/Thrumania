package thrumania.gui;

import thrumania.board.Map;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class GameFrame extends JFrame {

    private Map map;
    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    private GamePanel gamePanel;
    private BottomPanel bottomPanel;
    private RightPanel rightPanel;
    private MiniMapPanel miniMapPanel;
    public GameFrame( Map map) {
        this.map = map;
        this.setLayout(null);
        this.setLocation(0,0);
        this.setSize(d);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);

        gamePanel = new GamePanel(map);
        bottomPanel = new BottomPanel();
        rightPanel = new RightPanel();
        miniMapPanel = new MiniMapPanel();
        this.add(gamePanel);
        this.add(bottomPanel);
        this.add(rightPanel);
        this.add(miniMapPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }


}
