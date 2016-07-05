package thrumania.gui;

import thrumania.board.item.MapItems.Map;
import thrumania.game.network.ServerNode;
import thrumania.utils.Constants;
import thrumania.utils.FileUtils;
import thrumania.utils.ImageUtils;
import thrumania.utils.SoundUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;

/**
 * Created by sina on 6/22/16.
 */
public class MenuPanel extends JPanel {


    private Dimension d = new Dimension(getToolkit().getScreenSize().width, getToolkit().getScreenSize().height);
    private int verticalSpaceInit = 200;
    private int verticalSpace = 70;
    private int horzontalSpace = getToolkit().getScreenSize().width / 2 - 450;
    private boolean newgameIsSelected = false;
    private boolean playWithYourFriendsIsSelected = false;
    private boolean loadIsSelected = false;
    private boolean exitIsSelected = false;
    private boolean makeMapIsSelected = false;

    public MenuPanel() throws HeadlessException {
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(d);
        this.addMouseListener(new MyMouseListener());
        this.addMouseMotionListener(new MyMouseMotionListener());
        SoundUtils.play("menu.wav").start();
        this.setFocusable(false);
    }

    private void findingSelectedElement(int x, int y) {

        int counter = 1;
        this.newgameIsSelected = horzontalSpace <= x && x <= horzontalSpace + 160 && counter * verticalSpace + verticalSpaceInit - 30 <= y && y <= counter * verticalSpace + verticalSpaceInit + 10;
        repaint();
        counter++;
        this.playWithYourFriendsIsSelected = horzontalSpace <= x && x <= horzontalSpace + 400 && counter * verticalSpace + verticalSpaceInit - 30 <= y && y <= counter * verticalSpace + verticalSpaceInit + 10;
        repaint();

        counter++;

        if (horzontalSpace <= x && x <= horzontalSpace + 70 && counter * verticalSpace + verticalSpaceInit - 30 <= y && y <= counter * verticalSpace + verticalSpaceInit + 10)
            this.loadIsSelected = true;
        else loadIsSelected = false;

        counter++;
        if (horzontalSpace <= x && x <= horzontalSpace + 370 && counter * verticalSpace + verticalSpaceInit - 30 <= y && y <= counter * verticalSpace + verticalSpaceInit + 10)
            this.makeMapIsSelected = true;
        else makeMapIsSelected = false;

        counter++;
        this.exitIsSelected = horzontalSpace <= x && x <= horzontalSpace + 70 && counter * verticalSpace + verticalSpaceInit - 30 <= y && y <= counter * verticalSpace + verticalSpaceInit + 10;

    }

    private void stringDrawer(Graphics g) {
        int counter = 1;
        Font myFont = new Font("Party Business", Font.BOLD, 45);

        g.setFont(myFont);
        g.drawImage(ImageUtils.getImage("menu.jpg"), 0, 0, getToolkit().getScreenSize().width, getToolkit().getScreenSize().height - 25, null);
//        g.drawImage(ImageUtils.getImage("newGame.png"), 50 , counter * verticalSpace , 160 , 50, null );
        if (newgameIsSelected)
            g.setColor(Color.yellow);
        else g.setColor(Color.white);

        g.drawString("new game", horzontalSpace, counter * verticalSpace + verticalSpaceInit);
        counter++;
        if (playWithYourFriendsIsSelected)
            g.setColor(Color.yellow);
        else g.setColor(Color.white);

        g.drawString("play with your friends", horzontalSpace, counter * verticalSpace + verticalSpaceInit);
        counter++;
        if (loadIsSelected)
            g.setColor(Color.yellow);
        else g.setColor(Color.white);

        g.drawString("load", horzontalSpace, counter * verticalSpace + verticalSpaceInit);
        counter++;
        if (makeMapIsSelected)
            g.setColor(Color.yellow);
        else g.setColor(Color.white);

        g.drawString("make your own map", horzontalSpace, counter * verticalSpace + verticalSpaceInit);
        counter++;
        if (exitIsSelected)
            g.setColor(Color.yellow);
        else g.setColor(Color.white);

        g.drawString("exit", horzontalSpace, counter * verticalSpace + verticalSpaceInit);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.stringDrawer(g);

    }


    private class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (makeMapIsSelected) {
                SoundUtils.clip.stop();
                new GameFrame(new Map(Constants.MATRIX_HEIGHT, Constants.MATRIX_WIDTH));
            } else if (newgameIsSelected) {

                String mapFilePath = "";
                JOptionPane.showMessageDialog(MenuPanel.this, "Choose map file", "New Game", JOptionPane.INFORMATION_MESSAGE);
                mapFilePath = FileUtils.chooseFile(MenuPanel.this, "data/map");
                if  (!FileUtils.isMapFile(mapFilePath)) {
                    JOptionPane.showMessageDialog(MenuPanel.this,
                            "The selected file isn't a valid file :(", "New Game", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                HashMap<Integer, Object> loadedMap = FileUtils.getHashMapFromFile(mapFilePath);
                if (loadedMap == null) {
                    JOptionPane.showMessageDialog(MenuPanel.this,
                            "Corrupted map, couldn't load :(", "New Game", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String players = JOptionPane.showInputDialog(MenuPanel.this, "Please enter players' count:",
                            "New Game", JOptionPane.INFORMATION_MESSAGE);
                    try {

                        new PlayFrame(loadedMap, Integer.valueOf(players), false);
                        SoundUtils.clip.stop();

                    } catch (Exception exception) {
                        exception.printStackTrace();
                        JOptionPane.showMessageDialog(MenuPanel.this, "Wrong players' count :(", "New Game",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else if (playWithYourFriendsIsSelected) {
                int reply = JOptionPane.showConfirmDialog(null, "Are you server?", "Network Play", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    String mapFilePath = "";
                    JOptionPane.showMessageDialog(MenuPanel.this, "Choose map file", "New Game", JOptionPane.INFORMATION_MESSAGE);
                    mapFilePath = FileUtils.chooseFile(MenuPanel.this, "data/map");
                    if  (!FileUtils.isMapFile(mapFilePath)) {
                        JOptionPane.showMessageDialog(MenuPanel.this,
                                "The selected file isn't a valid file :(", "New Game", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    HashMap<Integer, Object> loadedMap = FileUtils.getHashMapFromFile(mapFilePath);
                    if (loadedMap == null) {
                        JOptionPane.showMessageDialog(MenuPanel.this,
                                "Corrupted map, couldn't load :(", "New Game", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String players = JOptionPane.showInputDialog(MenuPanel.this, "Please enter players' count:",
                                "New Game", JOptionPane.INFORMATION_MESSAGE);
                        try {

                            PlayFrame frame = new PlayFrame(loadedMap, Integer.valueOf(players), true);
                            frame.getPlayPanel().setNetwork(new ServerNode(frame.getPlayPanel(), Integer.valueOf(players), loadedMap));
                            SoundUtils.clip.stop();

                        } catch (Exception exception) {
                            exception.printStackTrace();
                            JOptionPane.showMessageDialog(MenuPanel.this, "Wrong players' count :(", "New Game",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } else {

                }
            } else if ( exitIsSelected){


                System.exit(0);

            }

            SoundUtils.soundIsAlive = false;


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

    private class MyMouseMotionListener implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            findingSelectedElement(e.getX(), e.getY());
        }
    }


}
