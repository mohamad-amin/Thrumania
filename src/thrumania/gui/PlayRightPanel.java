package thrumania.gui;

import thrumania.utils.Constants;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

/**
 * Created by sina on 6/28/16.
 */
public class PlayRightPanel extends JPanel {

    private Dimension d = new Dimension(this.getToolkit().getScreenSize().width - Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private int elementSize ;
    private int verticalSpaceBetweenElements = 10 ;
    private int horizontalSpaceBetweenElements = 45;
    private int initialPlace  =50;
    private PlayPanel playPanel;
    private String playerName ;
    private boolean zoomInIsSelected = false;
    private boolean zoomOutIsSelected = false;
    private  boolean saveIsSelected = false;
    private  boolean loadIsSelected = false ;
    private boolean rightArrowIsSelected = false ;
    private boolean leftArrowIsSelected = false ;
    private boolean upArrowIsSelected = false;
    private boolean downArrowIsSelected = false;
    private boolean exitIsSelected = false;
    private boolean undoIsSelected = false;
    private  boolean redoIsSelelcted =  false;

// TODO : implementing  buttons : what happens after clicking them
    public PlayRightPanel(PlayPanel playPanel) {
        this.playPanel = playPanel;
        this.setLayout(null);
        this.setSize(d);
        this.setLocation(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, 0);
        // TODO : for not mac  @mohammadAmin or @AmirhoseinBeheshti

        if( Constants.isMac)
            elementSize = 40;
        else  elementSize =  40;
        this.addMouseListener(new PlayRightPanelMouseListener());
    }


    private void findingSelectedObject1(int mouseXcord, int mouseYcord) {

        int elementCounter = 18;
        // upArrow :
        if (IntegerUtils.isInSideTheRangeOfCordinates(d.width / 2, verticalSpaceBetweenElements * elementCounter, d.width + elementSize, verticalSpaceBetweenElements * elementCounter + elementSize, mouseXcord, mouseYcord)) {
//            playPanel.setSelectedElelements(Constants.Elements.UP_ARROW);
            this.upArrowIsSelected = true;
//            playPanel.scrollUp();

            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {

                    upArrowIsSelected = false;
                    repaint();
                }

            }, 110);



        }

        //  right Arrow :
        elementCounter+= 4;
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width / 2 + horizontalSpaceBetweenElements  , verticalSpaceBetweenElements * elementCounter , d.width / 2  + horizontalSpaceBetweenElements   + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
            // Constants.selectedElement = Constants.Elements.RIGHT_ARROW;
//            playPanel.setSelectedElelements(Constants.Elements.RIGHT_ARROW);
            rightArrowIsSelected = true;
//            playPanel.scrollRight();
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {

                    rightArrowIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // left  arrow :
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width / 2 - horizontalSpaceBetweenElements  , verticalSpaceBetweenElements * elementCounter , d.width / 2  - horizontalSpaceBetweenElements   + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)) {

//            playPanel.setSelectedElelements( Constants.Elements.LEFT_ARROW);
            leftArrowIsSelected = true;
//            playPanel.scrollLeft();
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    leftArrowIsSelected = false;
                    repaint();
                }

            }, 110);



        }
        // down arrow :
        elementCounter +=4 ;
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width/2 , verticalSpaceBetweenElements * elementCounter , d.width/2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord )) {

//            playPanel.setSelectedElelements(  Constants.Elements.DOWN_ARROW);
            downArrowIsSelected = true;
//            playPanel.scrollDown();
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    downArrowIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // save :
        elementCounter +=6 ;
        if(IntegerUtils.isInSideTheRangeOfCordinates(d.width/2  - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter, d.width / 2 - horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord  , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.SAVE);
            saveIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    saveIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // load :
        if(IntegerUtils.isInSideTheRangeOfCordinates(d.width/2  + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter, d.width / 2 + horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord  , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.LOAD);
            loadIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    loadIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // zoom in  :
        elementCounter += 6;
        if ( IntegerUtils.isInSideTheRangeOfCordinates(d.width/2 - horizontalSpaceBetweenElements / 2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 - horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.ZOOM_IN);
            zoomInIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    zoomInIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // zoom out :
        if ( IntegerUtils.isInSideTheRangeOfCordinates(d.width/2 + horizontalSpaceBetweenElements / 2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 + horizontalSpaceBetweenElements / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.ZOOM_OUT);
            zoomOutIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    zoomOutIsSelected = false;
                    repaint();
                }

            }, 110);

        }
        // undo :
        elementCounter +=6;
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width/2 - horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , d.width /2 - horizontalSpaceBetweenElements /2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.UNDO);
            undoIsSelected = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    undoIsSelected = false;
                    repaint();
                }

            }, 110);


        }
        // rodo:
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width/2 + horizontalSpaceBetweenElements /2 , verticalSpaceBetweenElements * elementCounter , d.width /2 + horizontalSpaceBetweenElements /2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.REDO);
            redoIsSelelcted = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    redoIsSelelcted = false;
                    repaint();
                }

            }, 110);



        }
        // exit :
        elementCounter +=6;
        if( IntegerUtils.isInSideTheRangeOfCordinates(d.width /2 , verticalSpaceBetweenElements * elementCounter , d.width / 2 + elementSize , verticalSpaceBetweenElements * elementCounter + elementSize , mouseXcord , mouseYcord)){
//            playPanel.setSelectedElelements(Constants.Elements.EXIT);
            exitIsSelected  = true;
            repaint();
            new java.util.Timer().schedule(new TimerTask() {

                @Override
                public void run() {
                    exitIsSelected = false;
                    repaint();
                }

            }, 110);



        }



    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.drawImage(ImageUtils.getImage("bottomPanel.jpg"), 0, 0, d.width, d.height, null);


        int elementCounter = 1;
        // implementing resources :


        // wood
        //g.setColor(Color.WHITE);

      //  g.fillRect(d.width /6 , verticalSpaceBetweenElements * elementCounter - 10 , elementSize * 5 , elementSize * 5 );
        g.drawImage(ImageUtils.getImage("woodResource.png"), d.width / 5 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null );
         elementCounter +=4;
        g.drawImage(ImageUtils.getImage("ironResource.png"), d.width / 5 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null );
        elementCounter +=4 ;
        g.drawImage(ImageUtils.getImage("goldResource.png"), d.width / 5 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null );
        elementCounter +=4 ;
        g.drawImage(ImageUtils.getImage("foodResource.png"), d.width / 5 , verticalSpaceBetweenElements * elementCounter , elementSize , elementSize , null );

//         impelenting arrow keys
//         Up :'
        elementCounter += 5;

        if( ! upArrowIsSelected)
            g.drawImage(ImageUtils.getImage("upArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter , elementSize, elementSize, null);
        else if( upArrowIsSelected)
            g.drawImage(ImageUtils.getImage("upArrowKeyRightPanelHoover.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter , elementSize, elementSize, null);
        //right :
        elementCounter += 4;
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
        elementCounter += 4;
        if ( ! downArrowIsSelected)
            g.drawImage(ImageUtils.getImage("downArrowKeyRightPanel.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        else  if( downArrowIsSelected)
            g.drawImage(ImageUtils.getImage("downArrowKeyRightPanelHoover.png"), d.width / 2, verticalSpaceBetweenElements * elementCounter, elementSize, elementSize, null);
        // save :
        elementCounter += 6;
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
        elementCounter += 6;
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
        elementCounter += 6;
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
        elementCounter +=6;
        if( ! exitIsSelected)
            g.drawImage(ImageUtils.getImage("exitRightPanel.png"), d.width /2 , verticalSpaceBetweenElements * elementCounter , elementSize +10 , elementSize + 10 , null);
        else if( exitIsSelected)
            g.drawImage(ImageUtils.getImage("exitRightPanelHoover.png"), d.width /2 , verticalSpaceBetweenElements * elementCounter , elementSize +10 , elementSize + 10 , null);

//        repaint();
    }









    private  class  PlayRightPanelMouseListener implements MouseListener{


        @Override
        public void mouseClicked(MouseEvent e) {
                findingSelectedObject1(e.getX() , e.getY());
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
