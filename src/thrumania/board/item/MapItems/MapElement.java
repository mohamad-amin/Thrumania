package thrumania.board.item.MapItems;

/**
 * Created by sina on 5/22/16.
 */
public  abstract  class MapElement {
    protected String springPictureName ;
    protected String summerPictureName ;
    protected String autmnPictureName;
    protected String winterPictureName;

    public String getAutmnPictureName() {
        return autmnPictureName;
    }

    public String getSummerPictureName() {

        return summerPictureName;
    }

    public String getSpringPictureName() {

        return springPictureName;
    }

    public boolean isCanPutOnLowLand() {

        return canPutOnLowLand;
    }

    public String getWinterPictureName() {
        return winterPictureName;
    }

    protected   boolean canPutOnLowLand;

}
