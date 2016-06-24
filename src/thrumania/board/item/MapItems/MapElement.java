package thrumania.board.item.MapItems;

import thrumania.board.item.InsideElementsItems;

/**
 * Created by sina on 5/22/16.
 */
public  abstract  class MapElement  extends InsideElementsItems{
    protected String springPictureName ;
    protected String summerPictureName ;
    protected String autmnPictureName;
    protected String winterPictureName;
    protected  int  capacity = 100;
    protected  int eachElementCapacity;



    public String getAutmnPictureName() {
        return autmnPictureName;
    }

    public String getSummerPictureName() {

        return summerPictureName;
    }

    public String getSpringPictureName() {

        return springPictureName;
    }



    public String getWinterPictureName() {
        return winterPictureName;
    }



}
