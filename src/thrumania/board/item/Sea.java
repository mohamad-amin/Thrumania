package thrumania.board.item;

import thrumania.utils.Coordinate;

import java.util.List;

/**
 * Created by mohamadamin on 5/18/16.
 */
public class Sea extends Cell {

    public Sea(Coordinate position) {
        super(position);
        code= 0;
    }

    @Override
    public Coordinate getPosition() {
        return super.getPosition();
    }

    @Override
    public void setPosition(Coordinate position) {
        super.setPosition(position);
    }

    @Override
    public String getPictureName() {
        return super.getPictureName();
    }

    @Override
    public void setPictureName(String pictureName) {
        super.setPictureName(pictureName);
    }

}
