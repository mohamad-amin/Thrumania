package thrumania.main;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import sun.audio.AudioStream;
import  thrumania.board.item.MapItems.Map;
import thrumania.gui.*;
import thrumania.utils.Cacher;
import thrumania.utils.Constants;
import thrumania.gui.MenuFrame;

import javax.sound.sampled.Clip;
import java.awt.*;

/**
 * Created by mohamadamin on 5/17/16.
 */

public class Controller {

    private static Controller controller;
    private Cacher<String,Image> imageCacher;
    private Cacher<String , Clip> audioStreamCacher;
    private  MenuFrame menu;


    private Controller() {

        controller = this;
        initializeCachers();

        Constants.initializeConstants();
//        new GameFrame(new Map(Constants.MATRIX_HEIGHT,Constants.MATRIX_WIDTH));
        menu = new MenuFrame();


    }


    public static Controller getInstance() {
        return controller;
    }

    private void initializeCachers() {

        this.imageCacher = new Cacher<>();
        this.audioStreamCacher = new Cacher<>();
    }

    public Cacher<String,Image> getImageCacher() {
        return imageCacher;
    }
    public Cacher<String , Clip> getAudioStreamCacher(){

        return audioStreamCacher;
    }
    public static void main(String[] args) {new Controller();}

}
