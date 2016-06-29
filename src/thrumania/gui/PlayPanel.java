package thrumania.gui;


import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.GameItems.people.Soldier;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.board.item.MapItems.Map;
import thrumania.managers.HumanManagers;
import thrumania.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class PlayPanel extends JPanel implements MouseMotionListener, Runnable {
    private Map map;
    private Coordinate start = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private MiniMapPanel miniMap;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    private Preview preview;
    // TODO : check this shit
    private ArrayList<Human> shouldDrawHumans = new ArrayList<>();
    private InsideElementsItems gameSelectedElement = null;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    private boolean gameIsON;
    // needed for the resources :
    private int woordRes =0 ;
    private int ironRes = 0 ;
    private int foodRes = 0;
    private int goldRes =0;
    private boolean isScoralling= false;
    int scoralSide=4;
    FloatingCoordinate continuousMovement = new FloatingCoordinate(0,0);

    public PlayPanel(Map map, MiniMapPanel panel) {
        this.miniMap = panel;
        this.map = map;
        this.setLayout(null);
        this.setLocation(0, 0);
        this.setSize(d);
        this.addMouseListener(new GamePanelMouseListener());
        this.addMouseMotionListener(this);
        this.miniMap.setPlayPanel(this);
        miniMap.updateMap();
        this.season = Constants.Seasons.SPRING;
        this.dayTime = Constants.DayTime.MORNING;
        this.gameIsON = true;
        this.season = Constants.Seasons.SPRING;
    }

@Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        int seasonnum = giveMeSeasonNum();
        int re = Constants.Drawer_HIGHT+1; if (start.getRow()==Constants.MATRIX_HEIGHT-Constants.Drawer_HIGHT) re= Constants.Drawer_HIGHT;
        int ce = Constants.DRAWER_WIDTH+1; if (start.getColumn()==Constants.MATRIX_WIDTH-Constants.DRAWER_WIDTH) ce= Constants.DRAWER_WIDTH;
        int r=-1; if (start.getRow()==0) r=0;
        for (; r < re; r++) {
            int c = -1;
            if (start.getColumn() == 0) c = 0;
            for (; c < ce; c++) {
                this.drawingOcean(r, c, g);
                if (map.getCells()[r + start.getRow()][c + start.getColumn()] instanceof LowLand || map.getCells()[r + start.getRow()][c + start.getColumn()] instanceof HighLand) {
                    g.drawImage(
                            ImageUtils.getImage(Integer.toString(Integer.parseInt(map.getCells()[r + start.getRow()][c + start.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png"),
                            c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                            r * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                            Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            null);
                }
                if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems() != null) {
                    if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Tree"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
//Todo Sina whats the meaning of  +10 we have difrent scales
                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("StoneMine"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE + 10, null);
//                    g.fillRect( c * Constants.CELL_SIZE , r * Constants.CELL_SIZE ,Constants.CELL_SIZE * 2 , Constants.CELL_SIZE * 2 );

                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Agliculture"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Castle"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE+(int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE+(int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE - 10, Constants.CELL_SIZE - 10, null);
                    else {
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                    }
                }
            }
//Todo what is this?
            Human tempHuman = this.findingHumanByCoordinates(new Coordinate(r + start.getRow(), c + start.getColumn()));
            if (tempHuman != null) {
                tempHuman.setShouldDraw(true);
                this.shouldDrawHumans.add(tempHuman);
            }
        }
    }

    private String getPictureNameAccordingToSeason(Constants.Seasons season, InsideElementsItems mapElement) {
        if (season.equals(Constants.Seasons.SPRING))
            return ((DeadElements) mapElement).getSpringPictureName();
        else if (season.equals(Constants.Seasons.SUMMER))
            return ((DeadElements) mapElement).getSummerPictureName();
        else if (season.equals(Constants.Seasons.AUTMN))
            return ((DeadElements) mapElement).getAutumnPictureName();
        else
            return ((DeadElements) mapElement).getWinterPictureName();
    }

    private void drawingHumans(Graphics g) {
//        System.out.println("manager is "+ HumanManagers.getSharedInstance().getHumans().size());

        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().size(); i++) {

            if (HumanManagers.getSharedInstance().getHumans().get(i) instanceof Worker && HumanManagers.getSharedInstance().getHumans().get(i).isShouldDraw()) {
                g.drawImage(ImageUtils.getImage("manStanding.png"), HumanManagers.getSharedInstance().getHumans().get(i).getxCord(), HumanManagers.getSharedInstance().getHumans().get(i).getyCord() - Constants.CELL_SIZE / 2, Constants.CELL_SIZE - Constants.CELL_SIZE / 3, Constants.CELL_SIZE, null);
            } else if (HumanManagers.getSharedInstance().getHumans().get(i) instanceof Soldier && HumanManagers.getSharedInstance().getHumans().get(i).isShouldDraw())
                g.drawImage(ImageUtils.getImage("manStanding.png"), start.getColumn() * Constants.CELL_SIZE + HumanManagers.getSharedInstance().getHumans().get(i).getxCord(), start.getRow() * Constants.CELL_SIZE + HumanManagers.getSharedInstance().getHumans().get(i).getyCord(), Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
        for (int i = 0; i < shouldDrawHumans.size(); i++) {
            shouldDrawHumans.get(i).setShouldDraw(false);
            shouldDrawHumans.remove(i);
        }


    }

    private void addingHumansToMap(){
        for ( int i= 0 ; i< HumanManagers.getSharedInstance().getHumans().size() ; i++){
            HumanManagers.getSharedInstance().getHumans().get(i).setIcon(
                    new ImageIcon(ImageUtils.getImage(HumanManagers.getSharedInstance().getHumans().get(i).getPicutreName())));
            this.add(HumanManagers.getSharedInstance().getHumans().get(i));
        }


    }

    private void drawingOcean(int row, int column, Graphics g) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            g.drawImage(ImageUtils.getImage("ocean1.jpg"),
                    column * Constants.CELL_SIZE + (int)(continuousMovement.getColumn()*Constants.CELL_SIZE),
                    row * Constants.CELL_SIZE + (int)(continuousMovement.getRow()*Constants.CELL_SIZE),
                    Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    null);
        } else {
            g.drawImage(ImageUtils.getImage("ocean1Night.jpg"), column * Constants.CELL_SIZE + (int)(continuousMovement.getColumn()*Constants.CELL_SIZE),
                    row * Constants.CELL_SIZE + (int)(continuousMovement.getRow()*Constants.CELL_SIZE) , Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int scoralSidetemp ;
        if ((start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH  * Constants.CELL_SIZE)-Constants.RANGEOFSCROLL,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            scoralSidetemp = 2;
        else if ((start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT * Constants.CELL_SIZE)-Constants.RANGEOFSCROLL,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            scoralSidetemp = 1;
        else if ((start.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getX()))
            scoralSidetemp = 0;
        else if ((start.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getY()))
            scoralSidetemp = 3;
        else scoralSidetemp =4;
        if (scoralSide ==4){
            if(scoralSidetemp!=4) {
                scoralSide = scoralSidetemp;
                isScoralling = true;
                scoral();
            }
        }else {
            if(scoralSidetemp==4) {
                scoralSide = 4;
                isScoralling = false;
            }
            else if(scoralSidetemp!=scoralSide){
                scoralSide = scoralSidetemp;
                continuousMovement.setColumn(0);
                continuousMovement.setRow(0);
            }
        }
//        this.miniMap.updatestart(start);
        repaint();
    }

    private int giveMeSeasonNum() {
        if (season.equals(Constants.Seasons.SPRING)) return 0;
        else if (season.equals(Constants.Seasons.SUMMER)) return 1;
        else if (season.equals(Constants.Seasons.AUTMN)) return 2;
        else if (season.equals(Constants.Seasons.WINTER)) return 3;
        return 4;
    }

    private Human findingHumanByCoordinates(Coordinate crd) {

        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().size(); i++) {

            if (crd.equals(HumanManagers.getSharedInstance().getHumans().get(i).getCoordinate()))
                return HumanManagers.getSharedInstance().getHumans().get(i);

        }


        return null;
    }

    private void nextSeason() {
        int x = giveMeSeasonNum();
        season = giveMeSeasonConstant(x + 1);
        repaint();
    }

    private Constants.Seasons giveMeSeasonConstant(int i) {
        i = i % 4;
        if (i == 0) return Constants.Seasons.SPRING;
        else if (i == 1) return Constants.Seasons.SUMMER;
        else if (i == 2) return Constants.Seasons.AUTMN;
        else return Constants.Seasons.WINTER;
    }

    public Constants.Seasons getSeason() {
        return season;
    }

    public void setSeason(Constants.Seasons season) {
        this.season = season;
    }


    @Override
    public void run() {
        while (gameIsON) {

            JLabel  j = new JLabel();
            j.setSize(300,300);
            j.setLocation(300, 300 );
            j.setIcon(new ImageIcon(ImageUtils.getImage("manStanding.png")));
//            add(j);
            addingHumansToMap();
//            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private Human findingwhichHumanIsClicked(int x, int y) {
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().size(); i++) {

            if (HumanManagers.getSharedInstance().getHumans().get(i).getCoordinate().equals(coord)) {

                return HumanManagers.getSharedInstance().getHumans().get(i);
            }


        }
        return null;

    }

    public void setStart(Coordinate coordinate) {
        start= coordinate;
    }

    private class GamePanelMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

            // TODO :
            if (e.getModifiersEx() == 0 && e.getButton() == 1) {
                System.out.println(" you clicked here \t " + IntegerUtils.getCoordinateWithXAndY(e.getX(), e.getY()));
                for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().size(); i++)
                    System.out.println("this human location is \t" + HumanManagers.getSharedInstance().getHumans().get(i).getCoordinate());

                gameSelectedElement = findingwhichHumanIsClicked(e.getX(), e.getY());
                System.out.println("gameSelectedItem is \t" + gameSelectedElement);


            } else if (gameSelectedElement instanceof Human) {

//
                // use right click to move else it it would realese the selected element

                if (e.getModifiersEx() == 256 && e.getButton() == 3 && !((Human) gameSelectedElement).isMoving()) {
                    setHumanAction(e.getX() , e.getY());


                }
            }


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

    private void setHumanAction(int x, int y) {

        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        Cell cell = map.getCell(coord.getRow(), coord.getColumn());


        if ( cell.getInsideElementsItems() == null){
            //TODO : handle collecting resources


            if (gameSelectedElement instanceof Worker)
                ((Worker) gameSelectedElement).setEndCord(IntegerUtils.getCoordinateWithXAndY(x, y));
            else if (gameSelectedElement instanceof Soldier)
                ((Soldier) gameSelectedElement).setEndCord(IntegerUtils.getCoordinateWithXAndY(x, y));
            ((Human) gameSelectedElement).setxEnd(x);

            ((Human) gameSelectedElement).setyEnd(y);
            ((Human) gameSelectedElement).setPaths(((Human) gameSelectedElement).getMapProcessor().getPath(((Human) gameSelectedElement).getCoordinate() , ((Human) gameSelectedElement).getEndCord(), gameSelectedElement));
            HumanManagers.getSharedInstance().getThreadPoolExecutor().execute((Human) gameSelectedElement);
//       HumanManagers.getSharedInstance().makingThreadPool();



        }
        else if (cell.getInsideElementsItems() instanceof Castle) {
                // TODO  : set stack : MS <>
                if( gameSelectedElement instanceof  Human) {
                    ((Human) gameSelectedElement).setPaths(((Human) gameSelectedElement).getMapProcessor().getPath(((Human) gameSelectedElement).getCoordinate() , coord, gameSelectedElement));
                    ((Human) gameSelectedElement).getPaths().remove(((Human) gameSelectedElement).getPaths().size() -1);
                    ((Human) gameSelectedElement).setEndCord(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1));
                    ((Human) gameSelectedElement).setyEnd(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1).getRow() * Constants.CELL_SIZE);
                    ((Human) gameSelectedElement).setxEnd(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1).getColumn() * Constants.CELL_SIZE);
                }
            }
    }

    public void fixingStartInZoom(int x, int y) {
        int startX = x - ((Constants.Drawer_HIGHT / 2) + (Constants.Drawer_HIGHT % 2));
        int startY = y - ((Constants.DRAWER_WIDTH / 2) + (Constants.DRAWER_WIDTH % 2));
        if (startX < 0)
            startX = 1;
        if (startY < 0)
            startY = 1;
        if (startX + Constants.Drawer_HIGHT > Constants.MATRIX_HEIGHT)
            startX = Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT-1;
        if (startY + Constants.DRAWER_WIDTH > Constants.MATRIX_WIDTH)
            startY = Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH-1;
        start = new Coordinate(startX, startY);
    }

    public void scoral(){
        new Thread(() -> {
            while (isScoralling) {
                changeColOrRow();
            }}).start();
    }

    private void changeColOrRow() {
        switch (scoralSide) {
            case 0:
                scrollLeft();
                break;
            case 1:
                scrollDown();
                break;
            case 2:
                scrollRight();
                break;
            case 3:
                scrollUp();
                break;
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollUp(){
        if (start.getRow() > 0) {
            for (int j = 1 ; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addRow(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addRow(-1);
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollDown() {
        if (start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) {
            for (int j =1 ; j < Constants.RATEOFSCROLL && isScoralling;j++) {
                continuousMovement.addRow(-((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addRow(1);
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollRight() {
        if (start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) {
            for (int j =1 ;j<Constants.RATEOFSCROLL & isScoralling;j++) {
                continuousMovement.addColumn(-((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addColumn(1);
            continuousMovement.setColumn(0);
            continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollLeft() {
        if (start.getColumn() > 0) {
            for (int j = 1; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addColumn(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            start.addColumn(-1);
            continuousMovement.setColumn(0);
            continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }


}