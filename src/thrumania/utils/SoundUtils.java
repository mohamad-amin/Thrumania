package thrumania.utils;



import javax.sound.sampled.*;
import java.io.*;


/**
 * Created by sina on 6/22/16.
 */
public class SoundUtils {
    public static boolean soundIsAlive = false;
    public static Clip clip;


    public static Clip play(String name) {
        name = "res/sounds/" + name;

        try {
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(name));
            clip.open(inputStream);
        }catch(FileNotFoundException e){
            System.out.println("wave file not found.");
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clip;

    }
}


