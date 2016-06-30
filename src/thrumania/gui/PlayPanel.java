package thrumania.gui;


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
        this.drawingOcean(g);
        int re = Constants.Drawer_HIGHT+1; if (start.getRow()==Constants.MATRIX_HEIGHT-Constants.Drawer_HIGHT) re= Constants.Drawer_HIGHT;
        int ce = Constants.DRAWER_WIDTH+1; if (start.getColumn()==Constants.MATRIX_WIDTH-Constants.DRAWER_WIDTH) ce= Constants.DRAWER_WIDTH;
        int r=-1; if (start.getRow()==0) r=0;
        for (; r < re; r++) {
            int c = -1;
            if (start.getColumn() == 0) c = 0;
            for (; c < ce; c++) {
                try {
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
//

                        else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Agliculture"))
                            g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                    c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                    r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                    Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                        else if (map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Castle"))
                            g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                    c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                    r * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                    Constants.CELL_SIZE - 10, Constants.CELL_SIZE - 10, null);
                        else {
                            g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                    c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                    r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                    Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("kalak");
                }
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

// TODO : mohammad amin imageUTilize
        private void addingHumansToMap(){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (int i =0 ; i < HumanManagers.getSharedInstance().getHumans().length ; i++){
                        for ( int j = 0 ; j< HumanManagers.getSharedInstance().getHumans()[i].size() ; j++){

                            HumanManagers.getSharedInstance().getHumans()[i].get(j).setIcon(
                                    new ImageIcon(ImageUtils.getImage(HumanManagers.getSharedInstance().getHumans()[i].get(j).getPicutreName())));
                            int x1, y1;
                            x1 =HumanManagers.getSharedInstance().getHumans()[i].get(j).getxCord() - start.getColumn()*Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE) ;
                            y1 = HumanManagers.getSharedInstance().getHumans()[i].get(j).getyCord() - start.getRow() * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE);
                            HumanManagers.getSharedInstance().getHumans()[i].get(j).setLocation(x1, y1);
                            add(HumanManagers.getSharedInstance().getHumans()[i].get(j));



                        }

                    }
                }
            });

    }


    private void drawingOcean(Graphics g) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            g.drawImage(ImageUtils.getImage("ocean1.jpg"),
                    -Constants.CELL_SIZE,
                    -Constants.CELL_SIZE,
                    (Constants.DRAWER_WIDTH+2)*Constants.CELL_SIZE ,
                    (Constants.Drawer_HIGHT+2)*Constants.CELL_SIZE ,
                    null);
        } else {
            //Todo ocean1night
            g.drawImage(ImageUtils.getImage("ocean1.jpg"),
                    -Constants.CELL_SIZE,
                    -Constants.CELL_SIZE,
                    (Constants.DRAWER_WIDTH+2)*Constants.CELL_SIZE ,
                    (Constants.Drawer_HIGHT+2)*Constants.CELL_SIZE ,
                    null);
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

        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
            for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++) {
                if (crd.equals(HumanManagers.getSharedInstance().getHumans()[i].get(j).getCoordinate()))
                    return HumanManagers.getSharedInstance().getHumans()[i].get(j);


            }


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

            this.addingHumansToMap();

            try {
                Thread.sleep(50);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private Human findingwhichHumanIsClicked(int x, int y) {
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
            for ( int j =0 ; j< HumanManagers.getSharedInstance().getHumans()[i].size() ; j++){

                if (HumanManagers.getSharedInstance().getHumans()[i].get(j).getCoordinate().equals(coord)) {

                    return HumanManagers.getSharedInstance().getHumans()[i].get(j);
                }
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
            // tODO Check it
            int x , y;
            x = e.getX() + start.getColumn() * Constants.CELL_SIZE;
            y = e.getY() + start.getRow() * Constants.CELL_SIZE;


            // TODO : handling teams in selection
            if (e.getModifiersEx() == 0 && e.getButton() == 1) {
                System.out.println(" you clicked here \t " + IntegerUtils.getCoordinateWithXAndY(x, y));


                System.out.println(gameSelectedElement);
                gameSelectedElement = findingwhichHumanIsClicked(x, y);





            } else if (e.getModifiersEx() == 256 && e.getButton() == 3) {


                // use right click to move else it it would realese the selected element



                if (gameSelectedElement instanceof  Worker ) {
//                    System.out.println("1");
                    if (((Worker) gameSelectedElement).isInAttackState()) {
                        System.out.println("2");
                        ((Worker) gameSelectedElement).setCanAttack(false);
                        ((Worker) gameSelectedElement).setKillingOpponent(false);
                        ((Worker) gameSelectedElement).setInAttackState(false);
                    }
//                    System.out.println("herer hre here");

                    setHumanAction(x , y);


                }else if ( gameSelectedElement instanceof  Soldier) {
                    if (((Worker) gameSelectedElement).isInAttackState()) {
                        ((Worker) gameSelectedElement).setCanAttack(false);
                        ((Worker) gameSelectedElement).setKillingOpponent(false);
                        ((Worker) gameSelectedElement).setInAttackState(false);
                    }

                    setHumanAction(x, y);
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
            isScoralling = false ;
            scoralSide = 4;
        }
    }

    private void setHumanAction(int x, int y) {

        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        Cell cell = map.getCell(coord.getRow(), coord.getColumn());


//        if ( cell.getInsideElementsItems() == null){
        //TODO : handle collecting resources

        if (gameSelectedElement instanceof Worker) {

            System.out.println("333333");
//                ((Worker) gameSelectedElement).setDistination(coord);

            ((Worker) gameSelectedElement).setPathOfCoordinates(((Worker) gameSelectedElement).getMapProcessor().getPath(((Worker) gameSelectedElement).getCoordinate() , coord , gameSelectedElement));
            System.out.println("we set hte path here");
            if (  ! ((Worker) gameSelectedElement).isExecuted() ) {
                ((Worker) gameSelectedElement).setExecuted(true);
                HumanManagers.getSharedInstance().getThreadPoolExecutor().execute((Human) gameSelectedElement);
            }


        }

        else if (gameSelectedElement instanceof Soldier) {


            ((Soldier) gameSelectedElement).setDistination(coord);
//
//
//


            if (  ! ((Soldier) gameSelectedElement).isExecuted() ) {
                ((Soldier) gameSelectedElement).setExecuted(true);
                HumanManagers.getSharedInstance().getThreadPoolExecutor().execute((Human) gameSelectedElement);
            }

        }
//            ((Human) gameSelectedElement).setxEnd(x);

//            ((Human) gameSelectedElement).setyEnd(y);

//       HumanManagers.getSharedInstance().makingThreadPool();



//        }
//        else if (cell.getInsideElementsItems() instanceof Castle) {
//            // TODO  : set stack : MS <>
//            if( gameSelectedElement instanceof  Human) {
////
//            }
//
//
//
//        }




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
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
            start.addRow(-1);
            continuousMovement.addRow(-1);
            for (int j = 1 ; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addRow(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollDown() {
        if (start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) {
            start.addRow(1);
            continuousMovement.addRow(1);
            for (int j =1 ; j < Constants.RATEOFSCROLL && isScoralling;j++) {
                continuousMovement.addRow(-((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            continuousMovement.setRow(0);
            continuousMovement.setColumn(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollRight() {
        if (start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) {
            start.addColumn(1);
            continuousMovement.addColumn(1);
            for (int j =1 ;j<Constants.RATEOFSCROLL & isScoralling;j++) {
                continuousMovement.addColumn(-((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            continuousMovement.setColumn(0);
            continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }

    public void scrollLeft() {
        if (start.getColumn() > 0) {
            start.addColumn(-1);
            continuousMovement.addColumn(-1);
            for (int j = 1; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addColumn(((float)1/(float)Constants.RATEOFSCROLL));
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
            continuousMovement.setColumn(0);
            continuousMovement.setRow(0);
        }
        this.miniMap.updateFocus(start);
        repaint();
    }
//Todo optimizing game panel like this;
    public Coordinate getStart() {
        return start;
    }
}