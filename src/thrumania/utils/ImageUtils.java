package thrumania.utils;

import thrumania.main.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * Created by mohamadamin on 5/18/16.
 */

public class ImageUtils {

    public static Image getImage(String name) {
        name = "res/images/" + name;
        Cacher<String,Image> cacher = Controller.getInstance().getImageCacher();
        Image result = cacher.get(name);
        if (result == null) {
//            result = Toolkit.getDefaultToolkit().getImage(name);
            result = new ImageIcon(name).getImage();
            cacher.insert(name, result);
        }
        return result;
    }


}
