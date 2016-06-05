package thrumania.utils;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mohamadamin on 6/5/16.
 */
public class FileUtils {

    public static boolean saveHashMapToFile(HashMap<Integer, Object> map, String fileName) {
        File file = new File("data/map/"+fileName);
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(map);
            s.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
