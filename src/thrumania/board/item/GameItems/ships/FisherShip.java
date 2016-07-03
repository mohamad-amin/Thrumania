package thrumania.board.item.GameItems.ships;

import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Inside.SmallFish;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.managers.PortsManager;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;

/**
 * Created by sina on 7/3/16.
 */
public class FisherShip extends  Ships{
    // in rate of 1 second
    private int speedOfCollectingFood = 50 ;
    private  int capacityOfCollectingRecourses = 0;
    private int MAX_CAPACITY_OF_COLLECTING_FISH = 1200;
    private  boolean isCapacityOfCollectingResourcesFUll =false;
    private boolean isWinter = false;
    private Dimension d = new Dimension(Constants.CELL_SIZE, Constants.CELL_SIZE);
    private  Coordinate resourceCoordinate = null;
    private Coordinate whereToEmptiResources;

    public FisherShip(PlayPanel playPanel, Map map, int xCord, int yCord, int playerNumber){
        super.unitOfConsumingFood = 1;
        super.playPanel = playPanel;
        super.map = map;
        super.xCord = xCord;
        super.yCord = yCord;
        super.foodReq = 0;
        super.woodReq = 2000;
        super.ironReq= 500 ;
        super.goldReq =250 ;
        super.isAlive = true;
        this.mapProcessor = new MapProcessor(map.getCells());
        // TODO : doing set size in playpanel
        this.setSize(d);
        this.coordinate = IntegerUtils.getCoordinateWithXAndY(xCord , yCord);

    }


    private void consumingFood(){

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    private  boolean checkWetherGoalHasFishResource(Coordinate crd){

        Cell cell = map.getCell(crd.getRow() , crd.getColumn());
        if( cell.getInsideElementsItems() !=null && cell.getInsideElementsItems() instanceof SmallFish )
            return true;
        else  return  false;



    }
    private void examiningPath(){


        switch (moveState){


            case  STOP:
            {
                if( !pathOfCoordinates.isEmpty()){

                    moveState = StatesOfMoving.MOVE_BY_ORDER;

                }else if( pathOfCoordinates.isEmpty()){
                    checkWetherTheCapacityIsFull();
                    if( ! isCapacityOfCollectingResourcesFUll){

                        if( resourceCoordinate != null){
                            pathOfCoordinates = mapProcessor.getPath(coordinate , resourceCoordinate , this);
                            moveState = StatesOfMoving.MOVE_BY_ORDER;
                        }else moveState = StatesOfMoving.STOP;
                    }else if( isCapacityOfCollectingResourcesFUll){

                        // check to see it should empty or not
                        if(checkWheterThereIsInthePlaceToEmptyResources())
                        {
                            // TODO : empting resources
                        }else {
                        // we should find the best place for making empty and move to that place

                            moveState = StatesOfMoving.MOVE_BY_ORDER;
                            // find the best place to go
                        }

                    }else if( resourceCoordinate != null){
                        pathOfCoordinates = mapProcessor.getPath(coordinate , resourceCoordinate , this);
                        moveState = StatesOfMoving.MOVE_BY_ORDER;

                    }


                }

//                TODO :
                break;
            }
            case  MOVE_BY_ORDER:{

                if (  ! pathOfCoordinates.isEmpty() && pathOfCoordinates.peek().equals(coordinate)) {
                    pathOfCoordinates.pop();
                    moveState  = StatesOfMoving.STOP;
                }

                if( ! pathOfCoordinates.isEmpty()) {

                    if (super.checkWetherGoalIsLand(pathOfCoordinates.peek()))
                    {
                        while (!pathOfCoordinates.isEmpty())
                            pathOfCoordinates.pop();
                        moveState = StatesOfMoving.STOP;
                    }else if( pathOfCoordinates.size() == 1 && checkWetherGoalHasFishResource(pathOfCoordinates.peek()))
                    {
                        resourceCoordinate = pathOfCoordinates.pop();
                        moveState = StatesOfMoving.COLLECTING_FISH;
                    }else
                    super.regularMove(pathOfCoordinates.pop());


                }


                // TODO:

                break;
            }

            case COLLECTING_FISH:
            {
                this.checkWetherTheCapacityIsFull();
                if( !pathOfCoordinates.isEmpty())
                    moveState = StatesOfMoving.MOVE_BY_ORDER;
                else {
                    if( isCapacityOfCollectingResourcesFUll)
                    {

                        moveState = StatesOfMoving.COLLECTING_FISH_IS_DONE;

                    }
                    else {
                        // TODO : collect shit


                    }



                }



            break;
            }

            case COLLECTING_FISH_IS_DONE:
            {
                if( !pathOfCoordinates.isEmpty())
                    moveState =StatesOfMoving.MOVE_BY_ORDER;
                else if( false ){

                    // TODO : find the best way
                    moveState = StatesOfMoving.MOVE_BY_ORDER;
                }

                // TODO :
                break;
            }











        }


    }


private  void  checkWetherTheCapacityIsFull(){

    if( capacityOfCollectingRecourses == MAX_CAPACITY_OF_COLLECTING_FISH  )
        isCapacityOfCollectingResourcesFUll = true;
    else isCapacityOfCollectingResourcesFUll = false;

}

    private boolean checkWheterThereIsInthePlaceToEmptyResources(){

        if ( coordinate.equals(HomeCastleCoordinate))
            return true ;
        else{

            for ( int i =0 ; i < PortsManager.getPortSharedInstance().getPorts()[playerNumber].size() ; i++){

                if( PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getPortsCoordinate().equals(coordinate))
                    return true;
            }
            return  false;

        }

    }

//    private Coordinate findTheBestPlaceToEmptyResources(){
//        ArrayList<Coordinate> possibleCoordinates  = new ArrayList<>();
//        for (int  i =0 ; i< PortsManager.getPortSharedInstance().getPorts()[playerNumber].size() ; i++){
//            possibleCoordinates.add( PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getPortsCoordinate());
//            possibleCoordinates.add(HomeCastleCoordinate);
//
//        }
//
//        Collections.sort(possibleCoordinates);



//
//    }

    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void run() {
        while ( isAlive){
            this.consumingFood();
            if( canMove)
            examiningPath();

        }

    }
}
