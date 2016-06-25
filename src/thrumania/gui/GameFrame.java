package thrumania.gui;

import thrumania.board.item.MapItems.Map;
import thrumania.utils.Constants;

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

    public GameFrame(Map map) {
        this.map = map;
        this.setLayout(null);
        this.setLocation(0,0);
        this.setSize(d);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setFocusable(false);
        Constants.mouseInitializer(this);

        miniMapPanel = new MiniMapPanel(map);
        this.add(miniMapPanel);
        map.setMiniMap(miniMapPanel);

        gamePanel = new GamePanel(map, miniMapPanel);
        this.add(gamePanel);

        bottomPanel = new BottomPanel(gamePanel);
        this.add(bottomPanel);

        rightPanel = new RightPanel(gamePanel);
        this.add(rightPanel);

        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

    }


}
