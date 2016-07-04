package thrumania.board.item.GameItems.ships;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.DeadElements;
import thrumania.board.item.MapItems.Inside.SmallFish;
import thrumania.board.item.MapItems.Map;
import thrumania.game.MapProcessor;
import thrumania.gui.PlayPanel;
import thrumania.managers.PortsManager;
import thrumania.messages.Messages;
import thrumania.messages.SimpleMessages;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.awt.*;
import java.util.Stack;

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
    private InsideElementsItems elementIsBeingCollected = null;
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

// TODO : we Should set the homecoordinate of ship
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
                            playPanel.setFoodRes( capacityOfCollectingRecourses);
                            capacityOfCollectingRecourses = 0;
                            if( resourceCoordinate!= null )
                                pathOfCoordinates  = mapProcessor.getPath(coordinate , resourceCoordinate , this);
                                moveState =StatesOfMoving.MOVE_BY_ORDER;

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

                    if ( super.checkWetherGoalIsLand(pathOfCoordinates.peek()))
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


                }else if( pathOfCoordinates.isEmpty())
                    moveState = StatesOfMoving.STOP;


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

                        if (((DeadElements) elementIsBeingCollected).getMAX_CAPACITY() == 0) {
                            map.getCell(resourceCoordinate.getRow(), resourceCoordinate.getColumn()).setInsideElementsItems(null);
                            playPanel.dispatchEvent(new SimpleMessages(playPanel, Messages.REPAINT));
                            if (isCapacityOfCollectingResourcesFUll)
                                moveState = StatesOfMoving.COLLECTING_HUMAN_IS_DONE;
                            else
                                moveState = StatesOfMoving.STOP;
                        }

                        if (elementIsBeingCollected != null)
                        {

                            try {
                                Thread.sleep(1000 / speedOfCollectingFood );
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                                if ( ((DeadElements) elementIsBeingCollected).getMAX_CAPACITY() != 0){

                                    if( ((DeadElements) elementIsBeingCollected).getMAX_CAPACITY() < ((DeadElements) elementIsBeingCollected).getEachElementCapacity()){

                                        capacityOfCollectingRecourses += ((DeadElements) elementIsBeingCollected).getMAX_CAPACITY();
                                        ((DeadElements) elementIsBeingCollected).setMAX_CAPACITY(((DeadElements) elementIsBeingCollected).getMAX_CAPACITY());
                                    }else {

                                        capacityOfCollectingRecourses += ((DeadElements) elementIsBeingCollected).getEachElementCapacity();
                                        ((DeadElements) elementIsBeingCollected).setMAX_CAPACITY(((DeadElements) elementIsBeingCollected).getEachElementCapacity());

                                    }




                            }







                        }
                        // TODO : collect shit


                    }



                }



            break;
            }

            case COLLECTING_FISH_IS_DONE:
            {
                if( !pathOfCoordinates.isEmpty())
                    moveState =StatesOfMoving.MOVE_BY_ORDER;
//                else if( false ){
                else{
                    // TODO :
                    pathOfCoordinates = findTheBestPlaceToEmptyResources();
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

    private Stack<Coordinate> findTheBestPlaceToEmptyResources(){
        Stack<Coordinate> path1 ;
        Stack<Coordinate> path2 ;
//        ArrayList<Coordinate> possibleCoordinates  = new ArrayList<>();
        Coordinate bestCoord = null;
        for (int  i =0 ; i< PortsManager.getPortSharedInstance().getPorts()[playerNumber].size() ; i++){
          if ( bestCoord == null)
              bestCoord = PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getNeighborsea();
            else {
              path1 = mapProcessor.getPath(coordinate , bestCoord , this);
              path2 = mapProcessor.getPath(coordinate , PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getNeighborsea(), this);
              if( path1.size() > path2.size())
                  bestCoord = PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getNeighborsea();
          }



//            possibleCoordinates.add( PortsManager.getPortSharedInstance().getPorts()[playerNumber].get(i).getPortsCoordinate());
//            possibleCoordinates.add(HomeCastleCoordinate);

        }
        // TODO for path 2
        path1 = mapProcessor.getPath(coordinate, bestCoord, this);
        if ( this.HomeCastleCoordinate != null) {
            path2 = mapProcessor.getPath(coordinate, HomeCastleCoordinate, this);
            if (path1.size() > path2.size())
                return path2;

            return path1;
        }else return   path1;

//        Collections.sort(possibleCoordinates);




    }

    @Override
    public void paintingOptions(Graphics g) {

    }

    @Override
    public void findingSelectedObject(int x, int y) {

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
