package thrumania.utils;

import thrumania.main.Controller;

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
            result = Toolkit.getDefaultToolkit().getImage(name);
            cacher.insert(name, result);
        }
        return result;
    }

}
