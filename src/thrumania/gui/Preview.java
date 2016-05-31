package thrumania.gui;

import thrumania.messages.Messages;
import thrumania.messages.SimpleMessages;
import thrumania.utils.Constants;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by sina on 5/23/16.
 */
public class Preview extends Timer {

    private  GamePanel gamePanel;
    private  TimerTask timerTask;
    private  Integer time = 1500;
    private Constants.Seasons seasons;
    public Preview( GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.timer();
    }
    private void timer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                seasons =gamePanel.getSeason();
                if( seasons == Constants.Seasons.WINTER) seasons = Constants.Seasons.SPRING;
                else seasons = Constants.Seasons.values()[seasons.getValue()+1];
                gamePanel.setSeason(seasons);
                gamePanel.dispatchEvent(new SimpleMessages(gamePanel , Messages.REPAINT ));
            }
        };
//        this.schedule(timerTask, Preview.this.time, Preview.this.time);
        this.schedule(timerTask ,this.time , this.time );

    }
}
