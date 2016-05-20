package thrumania.gui;

import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

/**
 * Created by sina on 5/18/16.
 * <p>
 * I ( sina ) will implement this class
 */
public class RightPanel extends JPanel {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private int elementSize = 40;
    private int verticalSpaceBetweenElements = 40;
    private int horizontalSpaceBetweenElements = 45;
    private int initialPlace  =50;
    private String playerName ;
    private boolean zoonInIsSelected = false;
    private boolean zoonOutIsSelected = false;
    private  boolean saveIsSelected = false;
    private  boolean loadIsSelected = false ;
    private boolean rightArrowIsSelected = false ;
    private boolean leftArrowIsSelected = false ;
    private boolean upArrowIsSelected = false;
    private boolean downArrowIsSelected = false;
    private boolean previewisElected = false;
    private boolean exitIsSelected = false;
    // TODO : right in constants
    public static enum rightPanelElements {
        ZOOM_IN, ZOOM_OUT, SAVE, LOAD, RIGHT_ARROW, LEFT_ARROW, DOWN_ARROW, UP_ARROW, PREVIEW, EXIT

    }

    private rightPanelElements controlElements;

    public RightPanel() {

        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, 0);
        this.setSize(d);
        this.setBackground(Color.CYAN);
        this.setLayout(null);
        this.mouseInitializer();


    }

    public rightPanelElements getControlElements() {
        return controlElements;
    }

    // TODO : refactoring : utilities
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
        //todo : implement htis method
        int elementCounter = 1;
        // upArrow :
        if (isInSideTheRange(d.width / 2, verticalSpaceBetweenElements * elementCounter, d.width + elementSize, verticalSpaceBetweenElements * elementCounter + elementSize, mouseXcord, mouseYcord)) {

            this.upArrowIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    upArrowIsSelected = false;
                }

            }, 110);

        }
        //  right Arrow :
        // left  arrow :
        // down arrow :
        // save :
        // load :
        // zoom in  :
        // zoom out :
        // undo :

        // exit :
        //preview :



    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // impelenting arrow keys
        g.drawImage(ImageUtils.getImage("bottomPanel.jpg"), 0, 0, d.width, d.height, null);
        int elementCounter = 1;
        g.drawImage(ImageUtils.getImage("upArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter , elementSize, elementSize, null);
        elementCounter += 1;
        g.drawImage(ImageUtils.getImage("rightArrowKeyRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        g.drawImage(ImageUtils.getImage("leftArrowKeyRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter += 1;
        g.drawImage(ImageUtils.getImage("downArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("saveRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);
        g.drawImage(ImageUtils.getImage("loadRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("zoomInRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        g.drawImage(ImageUtils.getImage("zoomOutRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        elementCounter += 2;
        g.drawImage(ImageUtils.getImage("undoRightPanel.png"), d.width /2 - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null);
        g.drawImage(ImageUtils.getImage("redoRightPanel.png"), d.width /2 + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize, null);
        elementCounter +=2;
        g.drawImage(ImageUtils.getImage("exitRightPanel.png"), d.width /2 , verticalSpaceBetweenElements * elementCounter , elementSize +10 , elementSize + 10 , null);
        elementCounter +=2 ;
        g.drawImage(ImageUtils.getImage("previewRightPanel.png"), d.width / 2 , verticalSpaceBetweenElements * elementCounter , elementSize + 10 , elementSize + 10 , null);

        repaint();
    }

    class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            //TODO implement this mehtod
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
