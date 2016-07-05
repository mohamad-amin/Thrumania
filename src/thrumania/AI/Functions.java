package thrumania.AI;

import thrumania.board.item.GameItems.people.Human;
import thrumania.board.item.GameItems.people.Soldier;
import thrumania.board.item.GameItems.people.Worker;
import thrumania.board.item.MapItems.Inside.GoldMine;
import thrumania.board.item.MapItems.Inside.StoneMine;
import thrumania.board.item.MapItems.Inside.Tree;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayPanel;
import thrumania.managers.HumanManagers;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

/**
 * Created by AMIR on 7/5/2016.
 */
public class Functions {
    int playernumber;
    PlayPanel playpanel;

    public Functions(int playernumber, PlayPanel playPanel) {
        this.playernumber = playernumber;
        this.playpanel = playPanel;
    }

    
    public void setHumanMove(int x,int y,Human human){
        Coordinate coord = IntegerUtils.getCoordinateWithXAndY(x, y);
        //TODO : handle collecting resources
        if (human instanceof Worker) {
            if (!((Worker) human).getPathOfCoordinates().isEmpty()) {
                System.out.println("path path path path 444444444");
                ((Worker) human).setPathOfCoordinates(
                        ((Worker) human).getMapProcessor().getPath(
                                ((Worker) human).getPathOfCoordinates().peek(), coord, human));
            } else {
                System.out.println("path path path paht 555555");
                ((Worker) human).setPathOfCoordinates(
                        ((Worker) human).getMapProcessor().getPath(
                                ((Worker) human).getCoordinate(), coord, human));
            }
        } else if (human instanceof Soldier) {
            if (!((Soldier) human).getPathOfCoordinates().isEmpty()) {
                ((Soldier) human).setPathOfCoordinates(
                        ((Soldier) human).getMapProcessor().getPath(
                                ((Soldier) human).getPathOfCoordinates().peek(), coord, human));
            } else {
                ((Soldier) human).setPathOfCoordinates(
                        ((Soldier) human).getMapProcessor().getPath(
                                ((Soldier) human).getCoordinate(), coord, human));
            }
        }
    }

//    public void doRandomAction() {
//        randomMove();
//    }

    public void doRandomIn(int[] possibleTasks , Map map){
        int numberOfHumans =HumanManagers.getSharedInstance().getHumans()[playernumber].size();
        for (int i =0 ;i<numberOfHumans;i++) {
            doActionNumber(possibleTasks[((int) (Math.random() * possibleTasks.length))]
                    ,HumanManagers.getSharedInstance().getHumans()[playernumber].get(i)
            ,map);
        }
    }

    private void doActionNumber(int i , Human human, Map map) {
        switch (i){
            case 0 :
                randomMove(human);
                break;
            case 1 :
                collectingWood(human , map);
                break;
            case 2 :
                collectingStone(human , map);
                break;
            case 3 :
                collectingGold(human , map);
                break;
            case 4 :
                buildWoodQuarry();
                break;
            case 5 :
                buildMineQuarry();
                break;
            case 6 :
                buildBarrak();
                break;
            case 7 :
                buildPort();
                break;
            case 8 :
                makingSoldier();
                break;
            case 9 :
                makingFisherShip();
                break;
            case 10 :
                collectingFish();
                break;
            case 11 :
                buildingFarm();
                break;
            case 12 :
                landAttack();
                break;
            case 13 :
                SeaAttack();
                break;
        }
    }

    public void searchNearFor(){

    }

    //Todo not handled out of page
    //0
    public void randomMove(Human human){
        System.out.println(human.getStateOfMove());
            if (human.getStateOfMove()== Human.statesOfMovement.STOP){
                int randomNumber1 = (int)(Math.random()*(double)(Constants.RANDOMMOVERANGE));
                randomNumber1 = randomNumber1 - Constants.RANDOMMOVERANGE/2;
                int x= human.getxCord()+randomNumber1*Constants.CELL_SIZE;
                int randomNumber2 = (int)(Math.random()*(double)(Constants.RANDOMMOVERANGE));
                randomNumber2 = randomNumber2 - Constants.RANDOMMOVERANGE/2;
                int y= human.getxCord()+randomNumber2*Constants.CELL_SIZE;
                System.out.println(randomNumber1 + " " + randomNumber2);
                setHumanMove(x,y,human);
                System.out.println("randommove" + x + " " + y);
            }
    }

    //1
    public void collectingWood(Human human , Map map){
        int startx = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getColumn();
        int starty = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getRow();
        int endx = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getColumn();
        int endy = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getRow();
        if (startx < 0 ) startx =0;
        if (starty < 0) starty = 0;
        if (endx >= Constants.MATRIX_HEIGHT) endx = Constants.MATRIX_HEIGHT -1;
        if (endy >= Constants.MATRIX_WIDTH) endy = Constants.MATRIX_WIDTH -1;
        int sourcepointx = -1;
        int sourcepointy = -1;
        boolean found = false;
        for (int i = startx; i<=endx ;i++) {
            for (int j = starty; j <= endy; j++) {
                if (map.getCell(i,j).getInsideElementsItems() instanceof Tree){
                    sourcepointx = i;
                    sourcepointy = j;
                    found =true;
                }
            }
        }
        if (found) setHumanMove(sourcepointy *Constants.CELL_SIZE,sourcepointx*Constants.CELL_SIZE,human);
    }

    //2
    public void collectingStone(Human human , Map map){
        int startx = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getColumn();
        int starty = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getRow();
        int endx = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getColumn();
        int endy = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getRow();
        if (startx < 0 ) startx =0;
        if (starty < 0) starty = 0;
        if (endx >= Constants.MATRIX_HEIGHT) endx = Constants.MATRIX_HEIGHT -1;
        if (endy >= Constants.MATRIX_WIDTH) endy = Constants.MATRIX_WIDTH -1;
        int sourcepointx = -1;
        int sourcepointy = -1;
        boolean found = false;
        for (int i = startx; i<=endx ;i++) {
            for (int j = starty; j <= endy; j++) {
                if (map.getCell(i,j).getInsideElementsItems() instanceof StoneMine){
                    sourcepointx = i;
                    sourcepointy = j;
                    found =true;
                }
            }
        }
        if (found) setHumanMove(sourcepointy *Constants.CELL_SIZE,sourcepointx*Constants.CELL_SIZE,human);
    }

    //3
    public void collectingGold(Human human , Map map){
        int startx = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getColumn();
        int starty = (-(Constants.RANDOMMOVERANGE))+ human.getCoordinate().getRow();
        int endx = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getColumn();
        int endy = (Constants.RANDOMMOVERANGE)+ human.getCoordinate().getRow();
        if (startx < 0 ) startx =0;
        if (starty < 0) starty = 0;
        if (endx >= Constants.MATRIX_HEIGHT) endx = Constants.MATRIX_HEIGHT -1;
        if (endy >= Constants.MATRIX_WIDTH) endy = Constants.MATRIX_WIDTH -1;
        int sourcepointx = -1;
        int sourcepointy = -1;
        boolean found = false;
        for (int i = startx; i<=endx ;i++) {
            for (int j = starty; j <= endy; j++) {
                if (map.getCell(i,j).getInsideElementsItems() instanceof GoldMine){
                    sourcepointx = i;
                    sourcepointy = j;
                    found =true;
                }
            }
        }
        if (found) setHumanMove(sourcepointy *Constants.CELL_SIZE,sourcepointx*Constants.CELL_SIZE,human);
    }

    //4
    public void buildWoodQuarry(){

    }

    //5
    public void buildMineQuarry(){

    }

    //6
    public void buildBarrak(){

    }

    //7
    public void buildPort(){

    }

    //8
    public void makingSoldier(){

    }

    //9
    public void makingFisherShip(){

    }

    //10
    public void collectingFish(){

    }

    //11
    public void buildingFarm(){

    }

    //12
    public void landAttack(){

    }

    //13
    public void SeaAttack(){

    }

}
