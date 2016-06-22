package thrumania.utils;

import javax.swing.*;
import java.awt.*;
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

    public static String chooseFile(Component parent, String directory) {
        JFileChooser fileChooser = new JFileChooser(directory);
        int result = fileChooser.showOpenDialog(parent);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                return fileChooser.getSelectedFile().getAbsolutePath();
            default:
                return null;
        }
    }

    public static boolean isMapFile(String filePath) {
        if (filePath == null) return false;
        File file = new File(filePath);
        return file.exists() && getFileExtension(filePath).equals("tmap");
    }

    public static String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    public static HashMap<Integer, Object> getHashMapFromFile(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            HashMap<Integer, Object> map = (HashMap<Integer, Object>) s.readObject();
            s.close();
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
