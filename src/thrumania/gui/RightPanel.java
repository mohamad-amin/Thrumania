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
    private boolean zoomInIsSelected = false;
    private boolean zoomOutIsSelected = false;
    private  boolean saveIsSelected = false;
    private  boolean loadIsSelected = false ;
    private boolean rightArrowIsSelected = false ;
    private boolean leftArrowIsSelected = false ;
    private boolean upArrowIsSelected = false;
    private boolean downArrowIsSelected = false;
    private boolean previewIsSelected = false;
    private boolean exitIsSelected = false;
    private boolean undoIsSelected = false;
    private  boolean redoIsSelelcted =  false;
    // TODO : right in constants
    public static enum rightPanelElements {
        ZOOM_IN, ZOOM_OUT, SAVE, LOAD, RIGHT_ARROW, LEFT_ARROW, DOWN_ARROW, UP_ARROW, PREVIEW, EXIT , UNDO , REDO

    }

    private rightPanelElements controlElements;

    public RightPanel() {

        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, 0);
        this.setSize(d);
        this.setBackground(Color.CYAN);
        this.setLayout(null);
        this.mouseInitializer();
        this.addMouseListener(new MyMouseListener());


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
            this.controlElements = rightPanelElements.UP_ARROW;
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
        elementCounter++;
        if( isInSideTheRange(d.width / 2 + horizontalSpaceBetweenElements  , verticalSpaceBetweenElements * elementCounter , d.width / 2  + horizontalSpaceBetweenElements   + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.DOWN_ARROW;
            rightArrowIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    rightArrowIsSelected = false;
                }

            }, 110);

        }
        // left  arrow :
        if( isInSideTheRange(d.width / 2 - horizontalSpaceBetweenElements  , verticalSpaceBetweenElements * elementCounter , d.width / 2  - horizontalSpaceBetweenElements   + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)) {
            controlElements = rightPanelElements.LEFT_ARROW;
            leftArrowIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    leftArrowIsSelected = false;
                }

            }, 110);


        }
        // down arrow :
        elementCounter ++ ;
        if( isInSideTheRange(d.width/2 , verticalSpaceBetweenElements * elementCounter , d.width/2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord )) {
            controlElements = rightPanelElements.DOWN_ARROW;
            downArrowIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    downArrowIsSelected = false;
                }

            }, 110);

        }
        // save :
        elementCounter +=2 ;
        if(isInSideTheRange(d.width/2  - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter, d.width / 2 - horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord  , mouseYcord)){
            controlElements = rightPanelElements.SAVE;
            saveIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    saveIsSelected = false;
                }

            }, 110);

        }
        // load :
        if(isInSideTheRange(d.width/2  + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter, d.width / 2 + horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord  , mouseYcord)){
            controlElements = rightPanelElements.LOAD;
            loadIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    loadIsSelected = false;
                }

            }, 110);

        }
        // zoom in  :
        elementCounter += 2;
        if ( isInSideTheRange(d.width/2 - horizontalSpaceBetweenElements / 2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 - horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.ZOOM_IN;
            zoomInIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    zoomInIsSelected = false;
                }

            }, 110);

        }
        // zoom out :
        if ( isInSideTheRange(d.width/2 + horizontalSpaceBetweenElements / 2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 + horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.ZOOM_OUT;
            zoomOutIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    zoomOutIsSelected = false;
                }

            }, 110);

        }
        // undo :
        elementCounter +=2;
        if( isInSideTheRange(d.width/2 - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , d.width /2 - horizontalSpaceBetweenElements /2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.UNDO;
            undoIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    undoIsSelected = false;
                }

            }, 110);


        }
        // rodo:
        if( isInSideTheRange(d.width/2 + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , d.width /2 + horizontalSpaceBetweenElements /2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.REDO;
            redoIsSelelcted = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    redoIsSelelcted = false;
                }

            }, 110);


        }
        // exit :
        elementCounter +=2;
        if( isInSideTheRange(d.width /2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.EXIT;
            exitIsSelected  = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    exitIsSelected = false;
                }

            }, 110);


        }
        //preview :
        elementCounter +=2;
        if( isInSideTheRange(d.width / 2  , verticalSpaceBetweenElements * elementCounter , d.width / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            controlElements = rightPanelElements.PREVIEW;
            previewIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    previewIsSelected = false;
                }

            }, 110);

        }



    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(ImageUtils.getImage("bottomPanel.jpg"), 0, 0, d.width, d.height, null);
        int elementCounter = 1;
        // impelenting arrow keys
        // Up :
        if( ! upArrowIsSelected)
            g.drawImage(ImageUtils.getImage("upArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter , elementSize, elementSize, null);
        else if( upArrowIsSelected)
            g.drawImage(ImageUtils.getImage("upArrowKeyRightPanelHoover.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter , elementSize, elementSize, null);
        //right :
        elementCounter += 1;
        if( ! rightArrowIsSelected)
            g.drawImage(ImageUtils.getImage("rightArrowKeyRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else if ( rightArrowIsSelected)
            g.drawImage(ImageUtils.getImage("rightArrowKeyRightPanelHoover.png"), d.width / 2 + horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // left:
        if ( !leftArrowIsSelected )
            g.drawImage(ImageUtils.getImage("leftArrowKeyRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else if( leftArrowIsSelected)
            g.drawImage(ImageUtils.getImage("leftArrowKeyRightPanelHoover.png"), d.width / 2 - horizontalSpaceBetweenElements, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // down :
        elementCounter += 1;
        if ( ! downArrowIsSelected)
            g.drawImage(ImageUtils.getImage("downArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else  if( downArrowIsSelected)
            g.drawImage(ImageUtils.getImage("downArrowKeyRightPanelHoover.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // save :
        elementCounter += 2;
        if(!  saveIsSelected)
            g.drawImage(ImageUtils.getImage("saveRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);
        else if( saveIsSelected) {
            g.drawImage(ImageUtils.getImage("saveRightPanelHoover.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);

        }// load :
        if( ! loadIsSelected)
            g.drawImage(ImageUtils.getImage("loadRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);
        else if ( loadIsSelected)
            g.drawImage(ImageUtils.getImage("loadRightPanelHoover.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize + 5, null);
        // zoom in :
        elementCounter += 2;
        if( ! zoomInIsSelected)
            g.drawImage(ImageUtils.getImage("zoomInRightPanel.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else if ( zoomInIsSelected)
            g.drawImage(ImageUtils.getImage("zoomInRightPanelHoover.png"), d.width / 2 - horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // zoom out :
        if (! zoomOutIsSelected)
            g.drawImage(ImageUtils.getImage("zoomOutRightPanel.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else if ( zoomOutIsSelected)
            g.drawImage(ImageUtils.getImage("zoomOutRightPanelHoover.png"), d.width / 2 + horizontalSpaceBetweenElements / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // undo
        elementCounter += 2;
        if( ! undoIsSelected)
            g.drawImage(ImageUtils.getImage("undoRightPanel.png"), d.width /2 - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null);
        else if( undoIsSelected)
            g.drawImage(ImageUtils.getImage("undoRightPanelHoover.png"), d.width /2 - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null);
        // redo
        if( ! redoIsSelelcted)
            g.drawImage(ImageUtils.getImage("redoRightPanel.png"), d.width /2 + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize, null);
        else if( redoIsSelelcted)
            g.drawImage(ImageUtils.getImage("redoRightPanelHoover.png"), d.width /2 + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize, null);
        // exit :
        elementCounter +=2;
        if( ! exitIsSelected)
            g.drawImage(ImageUtils.getImage("exitRightPanel.png"), d.width /2 , verticalSpaceBetweenElements * elementCounter , elementSize +10 , elementSize + 10 , null);
        else if( exitIsSelected)
            g.drawImage(ImageUtils.getImage("exitRightPanelHoover.png"), d.width /2 , verticalSpaceBetweenElements * elementCounter , elementSize +10 , elementSize + 10 , null);
        // preview :
        elementCounter +=2 ;
        if ( ! previewIsSelected)
            g.drawImage(ImageUtils.getImage("previewRightPanel.png"), d.width / 2 , verticalSpaceBetweenElements * elementCounter , elementSize + 10 , elementSize + 10 , null);
        else if ( previewIsSelected)
            g.drawImage(ImageUtils.getImage("previewRightPanelHoover.png"), d.width / 2 , verticalSpaceBetweenElements * elementCounter , elementSize + 10 , elementSize + 10 , null);

//        repaint();
    }

    class MyMouseListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            findingSelectedObject(e.getX() , e.getY());
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
