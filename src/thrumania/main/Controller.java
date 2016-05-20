package thrumania.main;

import thrumania.board.Map;
import thrumania.gui.GameFrame;
import thrumania.utils.Cacher;
import thrumania.utils.Constants;

import java.awt.*;

/**
 * Created by mohamadamin on 5/17/16.
 */

public class Controller {

    private static Controller controller;
    private Cacher<String,Image> imageCacher;

    private Controller() {

        controller = this;
        initializeCachers();

        Constants.initializeConstants();
        new GameFrame(new Map( Constants.Drawer_HIGHT,Constants.DRAWER_WIDTH));

    }


    public static Controller getInstance() {
        return controller;
    }

    private void initializeCachers() {
        this.imageCacher = new Cacher<>();
    }

    public Cacher<String,Image> getImageCacher() {
        return imageCacher;
    }

    public static void main(String[] args) {new Controller();}

}
