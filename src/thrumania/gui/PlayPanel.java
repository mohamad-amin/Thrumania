package thrumania.gui;


import thrumania.board.item.GameItems.buildings.Castle;
import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.GameItems.people.Soldier;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cell;
import thrumania.board.item.MapItems.LowLand;
import thrumania.board.item.MapItems.Map;
import thrumania.board.item.MapItems.MapElement;
import thrumania.managers.HumanManagers;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.ImageUtils;
import thrumania.utils.IntegerUtils;

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

    int yfocus = 0;
    int xfocus = 0;
    private Map map;
    private Coordinate focus = new Coordinate(0, 0);
    private Dimension d = new Dimension(Constants.DRAWER_WIDTH * Constants.CELL_SIZE, Constants.Drawer_HIGHT * Constants.CELL_SIZE);
    private Constants.ZoomScales zoomScale = Constants.ZoomScales.ZERO_SCALE;
    private MiniMapPanel miniMap;
    private Constants.Seasons season;
    private Constants.DayTime dayTime;
    private Preview preview;
    private Coordinate start = new Coordinate(0, 0);
    // TODO : check this shit
    private ArrayList<Human> shouldDrawHumans = new ArrayList<>();

    //    private Constants.playPanelElements  gameElement = Constants.playPanelElements.WORKER;
    private InsideElementsItems gameSelectedElement = null;
    private Constants.Elements selectedElelements = Constants.Elements.EMPTY;
    private boolean gameIsON;

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


//        this.preview = new Preview()
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int seasonnum = giveMeSeasonNum();
        for (int r = 0; r < Constants.Drawer_HIGHT; r++) {
            for (int c = 0; c < Constants.DRAWER_WIDTH; c++) {
                this.drawingOcean(r, c, g);
                if (map.getCells()[r + focus.getRow()][c + focus.getColumn()] instanceof LowLand) {
                    g.drawImage(
                            ImageUtils.getImage(Integer.toString(Integer.parseInt(map.getCells()[r + focus.getRow()][c + focus.getColumn()].getPictureNameWithoutExtension()) + seasonnum * 16) + ".png"),
                            c * Constants.CELL_SIZE,
                            r * Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            Constants.CELL_SIZE,
                            null);

                    // TODO :
                    Human tempHuman = this.findingHumanByCoordinates(new Coordinate(r + focus.getRow(), c + focus.getColumn()));
                    if (tempHuman != null) {
                        tempHuman.setShouldDraw(true);
                        this.shouldDrawHumans.add(tempHuman);
                    }


                }

                if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems() != null) {
                    if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Tree"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("StoneMine"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE + 10, null);
//

                    else if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Agliculture"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
                                Constants.CELL_SIZE, Constants.CELL_SIZE, null);
                    else if (map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems().getClass().getSimpleName().equals("Castle"))
                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE,
                                Constants.CELL_SIZE - 10, Constants.CELL_SIZE - 10, null);
                    else {
//                        g.drawImage(ImageUtils.getImage(getPictureNameAccordingToSeason(season, map.getCells()[r + focus.getRow()][c + focus.getColumn()].getInsideElementsItems())), c * Constants.CELL_SIZE, r * Constants.CELL_SIZE - Constants.INSIDE_CELL_ELEMENT_SIZE,
//                                Constants.CELL_SIZE + 15, Constants.CELL_SIZE + 15, null);
                    }
                }
            }
        }
        this.drawingHumans(g);
    }

    //
    private String getPictureNameAccordingToSeason(Constants.Seasons season, InsideElementsItems mapElement) {
        if (season.equals(Constants.Seasons.SPRING))
            return ((MapElement) mapElement).getSpringPictureName();
        else if (season.equals(Constants.Seasons.SUMMER))
            return ((MapElement) mapElement).getSummerPictureName();
        else if (season.equals(Constants.Seasons.AUTMN))
            return ((MapElement) mapElement).getAutmnPictureName();
        else
            return ((MapElement) mapElement).getWinterPictureName();
    }

    private void drawingHumans(Graphics g) {
//        System.out.println("manager is "+ HumanManagers.getSharedInstance().getHumans().size());

        for (int i = 0; i < HumanManagers.getSharedInstance().getHumans().size(); i++) {

            if (HumanManagers.getSharedInstance().getHumans().get(i) instanceof Worker && HumanManagers.getSharedInstance().getHumans().get(i).isShouldDraw()) {
                g.drawImage(ImageUtils.getImage("manStanding.png"), HumanManagers.getSharedInstance().getHumans().get(i).getxCord(), HumanManagers.getSharedInstance().getHumans().get(i).getyCord() - Constants.CELL_SIZE / 2, Constants.CELL_SIZE - Constants.CELL_SIZE / 3, Constants.CELL_SIZE, null);

            } else if (HumanManagers.getSharedInstance().getHumans().get(i) instanceof Soldier && HumanManagers.getSharedInstance().getHumans().get(i).isShouldDraw())
                g.drawImage(ImageUtils.getImage("manStanding.png"), focus.getColumn() * Constants.CELL_SIZE + HumanManagers.getSharedInstance().getHumans().get(i).getxCord(), focus.getRow() * Constants.CELL_SIZE + HumanManagers.getSharedInstance().getHumans().get(i).getyCord(), Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }

        for (int i = 0; i < shouldDrawHumans.size(); i++) {

            shouldDrawHumans.get(i).setShouldDraw(false);
            shouldDrawHumans.remove(i);


        }


    }

    private void drawingOcean(int row, int column, Graphics g) {
        if (this.dayTime == Constants.DayTime.MORNING) {
            g.drawImage(ImageUtils.getImage("ocean1.jpg"), column * Constants.CELL_SIZE,
                    row * Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    Constants.CELL_SIZE,
                    null);
        } else {
            g.drawImage(ImageUtils.getImage("ocean1Night.jpg"), column * Constants.CELL_SIZE,
                    row * Constants.CELL_SIZE, Constants.CELL_SIZE, Constants.CELL_SIZE, null);
        }
    }

    public void setFocus(Coordinate position) {
        this.focus = position;
        repaint();
    }


    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if ((focus.getColumn() < Constants.MATRIX_WIDTH - Constants.DRAWER_WIDTH) &&
                IntegerUtils.isInRange((Constants.DRAWER_WIDTH - 1) * Constants.CELL_SIZE,
                        (Constants.DRAWER_WIDTH) * Constants.CELL_SIZE,
                        e.getX()))
            focus.addColumn(1);
        if ((focus.getRow() < Constants.MATRIX_HEIGHT - Constants.Drawer_HIGHT) &&
                IntegerUtils.isInRange((Constants.Drawer_HIGHT - 1) * Constants.CELL_SIZE,
                        (Constants.Drawer_HIGHT) * Constants.CELL_SIZE,
                        e.getY()))
            focus.addRow(1);
        if ((focus.getColumn() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getX()))
            focus.addColumn(-1);
        if ((focus.getRow() > 0) &&
                IntegerUtils.isInRange(0, Constants.CELL_SIZE, e.getY()))
            focus.addRow(-1);
        this.miniMap.updateFocus(focus);
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
            this.repaint();
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
            ((Human) gameSelectedElement).setPaths(((Human) gameSelectedElement).getMapProcessor().getPath(((Human) gameSelectedElement).getCoordinate() , ((Human) gameSelectedElement).getEndCord()));
            HumanManagers.getSharedInstance().makingThreadPool();




        }
        else if (cell.getInsideElementsItems() instanceof Castle) {
                // TODO  : set stack : MS <>
                if( gameSelectedElement instanceof  Human) {
                    ((Human) gameSelectedElement).setPaths(((Human) gameSelectedElement).getMapProcessor().getPath(((Human) gameSelectedElement).getCoordinate() , coord));
                    ((Human) gameSelectedElement).getPaths().remove(((Human) gameSelectedElement).getPaths().size() -1);
                    ((Human) gameSelectedElement).setEndCord(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1));
                    ((Human) gameSelectedElement).setyEnd(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1).getRow() * Constants.CELL_SIZE);
                    ((Human) gameSelectedElement).setxEnd(((Human) gameSelectedElement).getPaths().get(((Human) gameSelectedElement).getPaths().size() -1).getColumn() * Constants.CELL_SIZE);
                }



            }




    }


}