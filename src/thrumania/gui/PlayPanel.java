package thrumania.gui;


import thrumania.board.item.GameItems.buildings.*;
import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.GameItems.people.Soldier;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.GameItems.ships.ContainerShip;
import thrumania.board.item.GameItems.ships.FisherShip;
import thrumania.board.item.GameItems.ships.Ships;
import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Cells.Sea;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.board.item.MapItems.Map;
import thrumania.managers.HumanManagers;
import thrumania.managers.PortsManager;
import thrumania.managers.ShipsManager;
import thrumania.messages.EmptyingHuman;
import thrumania.messages.Messages;
import thrumania.messages.PickingHumanUp;
import thrumania.messages.RemovingFromPanel;
import thrumania.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by mohamadamin on 6/24/16.
 */
public class PlayPanel extends Panels implements MouseMotionListener, Runnable {
    private Constants.Elements selectedElements;
    private Map map;
    private Coordinate start = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private MiniMapPanel miniMap;
    private Preview preview;
    private InsideElementsItems gameSelectedElement = null;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    public boolean gameIsON;
    private Constants.BuildSomething buildSomething = null;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    // needed for the resources :
    private int woordRes = 1000;
    private int ironRes = 1000;
    private int foodRes = 1000;
    private int goldRes = 1000;
    private boolean isScoralling = false;
    int scoralSide = 4;
    private int ZeroScale = Constants.giveMeZeroScale();
    private int playernumber;
    private int tempNumberOfPlayers;

    public Constants.BuildSomething getBuildSomething() {
        return buildSomething;
    }

    public void setBuildSomething(Constants.BuildSomething buildSomething) {
        this.buildSomething = buildSomething;
    }

    public Constants.Elements getSelectedElements() {
        return selectedElements;
    }

    public void setSelectedElements(Constants.Elements selectedElement) {
        this.selectedElements = selectedElement;
    }

    FloatingCoordinate continuousMovement = new FloatingCoordinate(0, 0);
    private PlayBottomPanel playBottomPanel;

    //    private woodR
//Todo player number for teams
    public PlayPanel(Map map, MiniMapPanel panel, int playernumber) {
        this.playernumber = playernumber;
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
        tempNumberOfPlayers = Constants.NUMBER_OF_PLAYERS;
//        this.preview =  new Preview(this, 20000);



    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int seasonnum = giveMeSeasonNum();
        this.drawingOcean(g,seasonnum);
        int re = Constants.Drawer_HIGHT + 1;
        if (start.getRow() == Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) re = Constants.Drawer_HIGHT;
        int ce = Constants.DRAWER_WIDTH + 1;
        if (start.getColumn() == Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) ce = Constants.DRAWER_WIDTH;
        int r = -1;
        if (start.getRow() == 0) r = 0;
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
                                    Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                        else {
                            g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + start.getRow()][c + start.getColumn()].getInsideElementsItems())),
                                    c * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE),
                                    r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE),
                                    Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("kalak");
                } catch (NumberFormatException e){

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
    private void addingHumansToMap() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
                    for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++) {

                        HumanManagers.getSharedInstance().getHumans()[i].get(j).setIcon(
                                new ImageIcon(ImageUtils.getImage(HumanManagers.getSharedInstance().getHumans()[i].get(j).getPicutreName())));




                        int x1, y1;
                        //TODO we should correct human by  * (Constants.CELL_SIZE / Constants.giveMeZeroScale())
                        x1 = (HumanManagers.getSharedInstance().getHumans()[i].get(j).getxCord() * Constants.CELL_SIZE / ZeroScale) - start.getColumn() * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE);
                        y1 = (HumanManagers.getSharedInstance().getHumans()[i].get(j).getyCord() * Constants.CELL_SIZE / ZeroScale) - start.getRow() * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE);

                        Coordinate tempCrd = IntegerUtils.getCoordinateWithXAndY(x1, y1);
                        if(! HumanManagers.getSharedInstance().getHumans()[i].get(j).isHumanInsideTheShip()){
                        HumanManagers.getSharedInstance().getHumans()[i].get(j).setLocation(x1, y1);
                       }
                        add(HumanManagers.getSharedInstance().getHumans()[i].get(j));


                    }

                }
            }
        });

    }

    public void fixingPositions() {
        System.out.println(Constants.CELL_SIZE);
        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
            for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++) {
                System.out.println(i);
                System.out.println(j);
                System.out.println(HumanManagers.getSharedInstance().getHumans()[i].get(j).getxCord() - start.getColumn() * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE));
                System.out.println(HumanManagers.getSharedInstance().getHumans()[i].get(j).getyCord() - start.getRow() * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE));
                int x1, y1;
                x1 = HumanManagers.getSharedInstance().getHumans()[i].get(j).getxCord() - start.getColumn() * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE);
                y1 = HumanManagers.getSharedInstance().getHumans()[i].get(j).getyCord() - start.getRow() * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE);
                HumanManagers.getSharedInstance().getHumans()[i].get(j).setLocation(x1, y1);
            }
        }
    }


    private void drawingOcean(Graphics g,int season) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            if (season!=3)
            g.drawImage(ImageUtils.getImage("ocean1.jpg"),
                    -Constants.CELL_SIZE,
                    -Constants.CELL_SIZE,
                    (Constants.DRAWER_WIDTH + 2) * Constants.CELL_SIZE,
                    (Constants.Drawer_HIGHT + 2) * Constants.CELL_SIZE,
                    null);
            else
                g.drawImage(ImageUtils.getImage("ocean1winter.png"),
                        -Constants.CELL_SIZE,
                        -Constants.CELL_SIZE,
                        (Constants.DRAWER_WIDTH + 2) * Constants.CELL_SIZE,
                        (Constants.Drawer_HIGHT + 2) * Constants.CELL_SIZE,
                        null);
        } else {
            g.drawImage(ImageUtils.getImage("ocean1Night.jpg"),
                    -Constants.CELL_SIZE,
                    -Constants.CELL_SIZE,
                    (Constants.DRAWER_WIDTH + 2) * Constants.CELL_SIZE,
                    (Constants.Drawer_HIGHT + 2) * Constants.CELL_SIZE,
                    null);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int scoralSidetemp;
        if ((start.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH * Constants.CELL_SIZE) - Constants.RANGEOFSCROLL,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            scoralSidetemp = 2;
        else if ((start.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT * Constants.CELL_SIZE) - Constants.RANGEOFSCROLL,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            scoralSidetemp = 1;
        else if ((start.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getX()))
            scoralSidetemp = 0;
        else if ((start.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.RANGEOFSCROLL, e.getY()))
            scoralSidetemp = 3;
        else scoralSidetemp = 4;
        if (scoralSide == 4) {
            if (scoralSidetemp != 4) {
                scoralSide = scoralSidetemp;
                isScoralling = true;
                scoral();
            }
        } else {
            if (scoralSidetemp == 4) {
                scoralSide = 4;
                isScoralling = false;
            } else if (scoralSidetemp != scoralSide) {
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
        checkWetherISGameFinished();
        while (gameIsON) {

            synchronized (HumanManagers.getSharedInstance().getHumans()) {
                this.addingHumansToMap();
            }

            synchronized ((ShipsManager.getShipInstance().getShips())) {
                this.addingShipsToMap();
            }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }




        }
        System.exit(0);
    }

    private Human findingwhichHumanIsClicked(int x, int y) {
        // TODO : handling same team number for clicking
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().length; i++) {
            for (int j = 0; j < HumanManagers.getSharedInstance().getHumans()[i].size(); j++) {

                if (HumanManagers.getSharedInstance().getHumans()[i].get(j).getCoordinate().equals(coord)) {

                    return HumanManagers.getSharedInstance().getHumans()[i].get(j);
                }
            }


        }
        return null;

    }

    private Ships findingWhichShipIsClicked(int x, int y) {
        // TODO : handling same team number for clicking
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);

        for (int i = 0; i < ShipsManager.getShipInstance().getShips().length; i++) {
            for (int j = 0; j < ShipsManager.getShipInstance().getShips()[i].size(); j++) {
                if (ShipsManager.getShipInstance().getShips()[i].get(j).getCoordinate().equals(coord)) {

                    return ShipsManager.getShipInstance().getShips()[i].get(j);
                }

            }

        }
        return null;
    }

    public void setStart(Coordinate coordinate) {
        start = coordinate;
        repaint();
    }

    public InsideElementsItems getGameSelectedElement() {
        return gameSelectedElement;
    }

    public void setPlayBottomPanel(PlayBottomPanel playBottomPanel) {
        this.playBottomPanel = playBottomPanel;
    }

    public void zoomIn() {
        int column = Constants.DRAWER_WIDTH / 2 + start.getColumn();
        int row = Constants.Drawer_HIGHT / 2 + start.getRow();
        zoomScale = Constants.incScale(zoomScale);
        fixingStartInZoom(row, column);
        repaint();
        this.miniMap.updateFocus(start);
    }

    public void zoomOut() {
        int column = Constants.DRAWER_WIDTH / 2 + start.getColumn();
        int row = Constants.Drawer_HIGHT / 2 + start.getRow();
        zoomScale = Constants.decScale(zoomScale);
        fixingStartInZoom(row, column);
        repaint();
        this.miniMap.updateFocus(start);
    }

    public Map getMap() {
        return map;
    }

    private class GamePanelMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // tODO Check it
            int x, y, realx, realy;
            x = (e.getX() + start.getColumn() * Constants.CELL_SIZE);
            y = (e.getY() + start.getRow() * Constants.CELL_SIZE);
            realx = (e.getX() / Constants.CELL_SIZE) + start.getColumn();
            realy = (e.getY() / Constants.CELL_SIZE) + start.getRow();

            if (buildSomething != null) {
                building(realx, realy);
                System.out.println("game selected is .....");
                setHumanAction(x, y);
            } else {
                // TODO : handling teams in selectio
                if (e.getModifiersEx() == 0 && e.getButton() == 1) {
                    System.out.println(" you clicked here \t " + IntegerUtils.getCoordinateWithXAndY(x, y));
                    //TODO : finding which element is clicked
 //                   if (((findingwhichElementIsClicked(x, y, realx, realy))!=null&&((findingwhichElementIsClicked(x, y, realx, realy)).getPlayerNumber() == playernumber))) {
                        gameSelectedElement = findingwhichElementIsClicked(x, y, realx, realy);
                        System.out.println(gameSelectedElement);
                        playBottomPanel.repaint();
 //                   }
                } else if (e.getModifiersEx() == 256 && e.getButton() == 3) {
                    // use right click to move else it it would realese the selected element
                    if (gameSelectedElement instanceof Human)
                        setHumanAction(x, y);
                    else if (gameSelectedElement instanceof Ships)
                        setShipAction(x, y);
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
            isScoralling = false;
            scoralSide = 4;
        }
    }

    private InsideElementsItems findingwhichElementIsClicked(int x, int y, int realx, int realy) {
        InsideElementsItems temp = findingwhichHumanIsClicked(x, y);
        if (temp != null)
            return temp;
            temp = findingWhichShipIsClicked(x , y);
            if (temp !=  null)
                return  temp ;
        return map.getCell(realy, realx).getInsideElementsItems();
    }

    private void setHumanAction(int x, int y) {

        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
//        Cell cell = map.getCell(coord.getRow(), coord.getColumn());


//        if ( cell.getInsideElementsItems() == null){
        //TODO : handle collecting resources

        if (gameSelectedElement instanceof Worker) {


            if (!((Worker) gameSelectedElement).getPathOfCoordinates().isEmpty()) {
                System.out.println("path path path path 444444444");
                ((Worker) gameSelectedElement).setPathOfCoordinates(
                        ((Worker) gameSelectedElement).getMapProcessor().getPath(
                                ((Worker) gameSelectedElement).getPathOfCoordinates().peek(), coord, gameSelectedElement));

            } else {
                System.out.println("path path path paht 555555");
                ((Worker) gameSelectedElement).setPathOfCoordinates(
                        ((Worker) gameSelectedElement).getMapProcessor().getPath(
                                ((Worker) gameSelectedElement).getCoordinate(), coord, gameSelectedElement));


            }


        } else if (gameSelectedElement instanceof Soldier) {

            if (!((Soldier) gameSelectedElement).getPathOfCoordinates().isEmpty()) {

                ((Soldier) gameSelectedElement).setPathOfCoordinates(
                        ((Soldier) gameSelectedElement).getMapProcessor().getPath(
                                ((Soldier) gameSelectedElement).getPathOfCoordinates().peek(), coord, gameSelectedElement));

            } else {
                ((Soldier) gameSelectedElement).setPathOfCoordinates(
                        ((Soldier) gameSelectedElement).getMapProcessor().getPath(
                                ((Soldier) gameSelectedElement).getCoordinate(), coord, gameSelectedElement));


            }
//            if (!((Soldier) gameSelectedElement).isExecuted()) {
//                ((Soldier) gameSelectedElement).setExecuted(true);
//                HumanManagers.getSharedInstance().getThreadPoolExecutor().execute((Human) gameSelectedElement);
//            }


        }


    }

    private void setShipAction(int x, int y) {
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);

        if (gameSelectedElement instanceof Ships) {

            if (!((Ships) gameSelectedElement).getPathOfCoordinates().isEmpty()) {
                ((Ships) gameSelectedElement).setPathOfCoordinates(((Ships) gameSelectedElement).getMapProcessor().getPath(
                        ((Ships) gameSelectedElement).getPathOfCoordinates().peek(), coord, gameSelectedElement));


            } else if (((Ships) gameSelectedElement).getPathOfCoordinates().isEmpty()) {

                ((Ships) gameSelectedElement).setPathOfCoordinates(((Ships) gameSelectedElement).getMapProcessor().getPath(
                        ((Ships) gameSelectedElement).getCoordinate(), coord, gameSelectedElement));


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
            startX = Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT - 1;
        if (startY + Constants.DRAWER_WIDTH > Constants.MATRIX_WIDTH)
            startY = Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH - 1;
        start = new Coordinate(startX, startY);
    }

    public void scoral() {
        new Thread(() -> {
            while (isScoralling) {
                changeColOrRow();
                try {
                    Thread.sleep(Constants.scrollSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    public void scrollUp() {
        if (start.getRow() > 0) {
            start.addRow(-1);
            continuousMovement.addRow(-1);
            for (int j = 1; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addRow(((float) 1 / (float) Constants.RATEOFSCROLL));
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
            for (int j = 1; j < Constants.RATEOFSCROLL && isScoralling; j++) {
                continuousMovement.addRow(-((float) 1 / (float) Constants.RATEOFSCROLL));
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
            for (int j = 1; j < Constants.RATEOFSCROLL & isScoralling; j++) {
                continuousMovement.addColumn(-((float) 1 / (float) Constants.RATEOFSCROLL));
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
                continuousMovement.addColumn(((float) 1 / (float) Constants.RATEOFSCROLL));
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

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        if (e.getID() == Messages.REPAINT) {
            this.repaint();
        } else if (e.getID() == Messages.EMPTING_HUMAN) {
            EmptyingHuman r = (EmptyingHuman) e;
            emptyHumanFromShip(r);

        } else if (e.getID() == Messages.PICKING_HUMAN_UP) {

            PickingHumanUp r = (PickingHumanUp) e;
            pickHumanUp(r);

        } else if (e.getID() == Messages.REMOVING_FROM_PANEL) {
            RemovingFromPanel r = (RemovingFromPanel) e;
            removingHumanFromPanel(r.getHuman());
        }

        super.processComponentEvent(e);

    }

    private void removingHumanFromPanel(Human human) {
        this.remove(human);
        this.repaint();
//        this.removeNotify();
//        this.revalidate();
//        this.repaint();
    }

    private void pickHumanUp(PickingHumanUp pickingHumanUp) {
        pickingHumanUp.getContainerShip().getIndsideHumans().add(pickingHumanUp.getHuman());
        pickingHumanUp.getHuman().setHumanInsideTheShip(true);
        this.remove(pickingHumanUp.getHuman());
        pickingHumanUp.getHuman().setVisible(false);
        this.repaint();


    }

    private void emptyHumanFromShip(EmptyingHuman emptyingHuman) {

        while (!emptyingHuman.getContainerShip().getIndsideHumans().isEmpty()) {
            System.out.println("now we are going to empty this guy :\t" + emptyingHuman.getContainerShip().getIndsideHumans().get(0));
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setHumanInsideTheShip(false);
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setCoordinate(emptyingHuman.getEndCoord());
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setxCord(IntegerUtils.getXAndYWithCoordinate(emptyingHuman.getEndCoord())[0]);
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setyCord(IntegerUtils.getXAndYWithCoordinate(emptyingHuman.getEndCoord())[1]);
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setHumanInsideTheShip(false);
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setIcon(new ImageIcon(ImageUtils.getImage(emptyingHuman.getContainerShip().getIndsideHumans().get(0).getPicutreName())));
            emptyingHuman.getContainerShip().getIndsideHumans().get(0).setVisible(true);
            add(emptyingHuman.getContainerShip().getIndsideHumans().get(0));

            emptyingHuman.getContainerShip().getIndsideHumans().remove(0);
        }


    }


    //Todo optimizing game panel like this;
    public Coordinate getStart() {
        return start;
    }

    public int getGoldRes() {
        return goldRes;
    }

    public void setGoldRes(int goldRes) {
        this.goldRes += goldRes;
    }

    public int getFoodRes() {

        return foodRes;
    }

    public void setFoodRes(int foodRes) {
        this.foodRes += foodRes;
    }

    public int getIronRes() {

        return ironRes;
    }

    public void setIronRes(int ironRes) {
        this.ironRes += ironRes;
    }

    public int getWoordRes() {

        return woordRes;
    }

    public void setWoordRes(int woordRes) {
        this.woordRes += woordRes;
    }

    public void buildWorker(Castle castle) {
        synchronized (HumanManagers.getSharedInstance().getHumans()) {
            Worker worker = new Worker(this, map, IntegerUtils.getXAndYWithCoordinate(castle.getStartingPoint())[0], IntegerUtils.getXAndYWithCoordinate(castle.getStartingPoint())[1], castle.getSide().getNumberOfPlayer(), playBottomPanel);
            worker.setHomeCastleCoordinate(castle.getStartingPoint());
            HumanManagers.getSharedInstance().getHumans()[worker.getPlayerNumber()].add(worker);
            HumanManagers.getSharedInstance().getThreadPoolExecutor().execute(worker);
        }
    }

    public void buildSoldier(Barrack barrak) {
        synchronized (HumanManagers.getSharedInstance().getHumans()) {
            Soldier soldier = new Soldier(this, map, IntegerUtils.getXAndYWithCoordinate(barrak.getStartingPoint())[0], IntegerUtils.getXAndYWithCoordinate(barrak.getStartingPoint())[1], barrak.getSide().getNumberOfPlayer(), playBottomPanel);
            soldier.setHomeCastleCoordinate(barrak.getStartingPoint());
            HumanManagers.getSharedInstance().getHumans()[soldier.getPlayerNumber()].add(soldier);
            HumanManagers.getSharedInstance().getThreadPoolExecutor().execute(soldier);
        }
    }

    public void buildContainerShip(Port port) {
        synchronized (ShipsManager.getShipInstance().getShips()) {
            System.out.println("hellohello");
            ContainerShip containerShip = new ContainerShip(this, map, IntegerUtils.getXAndYWithCoordinate(port.getNeighborsea())[0],
                    IntegerUtils.getXAndYWithCoordinate(port.getNeighborsea())[1], port.getSide().getNumberOfPlayer());
            ShipsManager.getShipInstance().getShips()[containerShip.getPlayerNumber()].add(containerShip);
            ShipsManager.getShipInstance().getShipThreadPoolExecuter().execute(containerShip);
        }
    }

    public void buildFisherShip(Port port) {
        synchronized (ShipsManager.getShipInstance().getShips()) {
            FisherShip fisherShip = new FisherShip(this, map, IntegerUtils.getXAndYWithCoordinate(port.getNeighborsea())[0],
                    IntegerUtils.getXAndYWithCoordinate(port.getNeighborsea())[1], port.getSide().getNumberOfPlayer());
            ShipsManager.getShipInstance().getShips()[fisherShip.getPlayerNumber()].add(fisherShip);
            ShipsManager.getShipInstance().getShipThreadPoolExecuter().execute(fisherShip);
        }
    }

    public void building(int realx, int realy) {
        Coordinate realPosition = new Coordinate(realx, realy);
        switch (buildSomething) {
            case port:
                if (map.getCell(realy, realx) != null && !(map.getCell(realy, realx) instanceof Sea)) {
                    if (map.getCell(realy, realx).getCanSetBuilding()) {
                        if (Requirements.Port(foodRes, goldRes, ironRes)) {
                            if (map.getCell(realy, realx).getNeighbourSea(map.getCells()).getPosition() != null) {
                                Port p = new Port(realPosition,
                                        map.getCell(realy, realx).getNeighborLand(map.getCells()).getPosition(),
                                        map.getCell(realy, realx).getNeighbourSea(map.getCells()).getPosition(),
                                        (((Human) gameSelectedElement)).getPlayerNumber(), playBottomPanel, map);
                                map.getCell(realy, realx).setInsideElementsItems(p);
                                PortsManager.getPortSharedInstance().getPorts()[(((Human) gameSelectedElement)).getPlayerNumber()].add(p);
                            }
                        }
                    }
                }
                break;
            case barrak:
                if (map.getCell(realy, realx) != null && !(map.getCell(realy, realx) instanceof Sea)) {
                    if (map.getCell(realy, realx).getCanSetBuilding()) {
                        if (Requirements.Barrak(foodRes, goldRes, ironRes)) {
                            map.getCell(realy, realx).setInsideElementsItems(new Barrack(realPosition,
                                    map.getCell(realy, realx).getNeighborLand(map.getCells()).getPosition(),
                                    (((Human) gameSelectedElement)).getPlayerNumber(), playBottomPanel, map));
                        }
                    }
                }
                break;
            case woodquarry:
                if (map.getCell(realy, realx) != null && !(map.getCell(realy, realx) instanceof Sea)) {
                    if (map.getCell(realy, realx).getCanSetBuilding()) {
                        if (Requirements.WoodQuarry(foodRes, goldRes, ironRes)) {
                            map.getCell(realy, realx).setInsideElementsItems(new WoodQuarry(realPosition,
                                    map.getCell(realy, realx).getNeighborLand(map.getCells()).getPosition(),
                                    (((Human) gameSelectedElement)).getPlayerNumber(), playBottomPanel, map));
                        }
                    }
                }
                break;
            case minequarry:
                if (map.getCell(realy, realx) != null && !(map.getCell(realy, realx) instanceof Sea)) {
                    if (map.getCell(realy, realx).getCanSetBuilding()) {
                        if (Requirements.MineQuarry(foodRes, goldRes, ironRes)) {
                            map.getCell(realy, realx).setInsideElementsItems(new MineQuarry(realPosition,
                                    map.getCell(realy, realx).getNeighborLand(map.getCells()).getPosition(),
                                    (((Human) gameSelectedElement)).getPlayerNumber(), playBottomPanel, map));
                        }
                    }
                }
                break;
            case farm:
                if (map.getCell(realy, realx) != null && !(map.getCell(realy, realx) instanceof Sea)) {
                    if (map.getCell(realy, realx).getCanSetBuilding()) {
                        if (Requirements.Farm(foodRes, goldRes, ironRes)) {
                            map.getCell(realy, realx).setInsideElementsItems(new Farm(realPosition,
                                    map.getCell(realy, realx).getNeighborLand(map.getCells()).getPosition(),
                                    (((Human) gameSelectedElement)).getPlayerNumber(), playBottomPanel, map));
                        }
                    }
                }
                break;
        }
        repaint();
        buildSomething = null;
    }

    private void addingShipsToMap() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i < ShipsManager.getShipInstance().getShips().length; i++) {

                    for (int j = 0; j < ShipsManager.getShipInstance().getShips()[i].size(); j++) {
                        ShipsManager.getShipInstance().getShips()[i].get(j).setIcon(
                                new ImageIcon(ImageUtils.getImage(ShipsManager.getShipInstance().getShips()[i].get(j).getPictureName())));

                        int x1, y1;
                        //TODO we should correct human by  * (Constants.CELL_SIZE / Constants.giveMeZeroScale())
                        x1 = (ShipsManager.getShipInstance().getShips()[i].get(j).getxCord() * Constants.CELL_SIZE / ZeroScale) - start.getColumn() * Constants.CELL_SIZE + (int) (continuousMovement.getColumn() * Constants.CELL_SIZE);
                        y1 = (ShipsManager.getShipInstance().getShips()[i].get(j).getyCord() * Constants.CELL_SIZE / ZeroScale) - start.getRow() * Constants.CELL_SIZE + (int) (continuousMovement.getRow() * Constants.CELL_SIZE);

                        Coordinate tempCrd = IntegerUtils.getCoordinateWithXAndY(x1, y1);
                        ShipsManager.getShipInstance().getShips()[i].get(j).setLocation(x1, y1);

                        add(ShipsManager.getShipInstance().getShips()[i].get(j));
                    }

                }

            }
        });

    }

    public Constants.DayTime getDayTime() {
        return dayTime;
    }

    public void setDayTime(Constants.DayTime dayTime) {
        this.dayTime = dayTime;
    }
    private void checkWetherISGameFinished(){

        if  ( tempNumberOfPlayers == 1){

            gameIsON = false;

        }
        else gameIsON = true;


    }

    public int getTempNumberOfPlayers() {
        return tempNumberOfPlayers;
    }

    public void setTempNumberOfPlayers(int tempNumberOfPlayers) {
        this.tempNumberOfPlayers = tempNumberOfPlayers;
    }
}
