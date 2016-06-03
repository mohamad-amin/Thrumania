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
    private  Integer time = 10000;
    private Constants.Seasons seasons;



    public Preview( GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.dayTimer();
        this.seasonalTimer();
    }


    private void seasonalTimer(){
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
    private void dayTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {

                if ( gamePanel.getDayTime() == Constants.DayTime.NIGHT) gamePanel.setDayTime(Constants.DayTime.MORNING);
                else if( gamePanel.getDayTime() == Constants.DayTime.MORNING ) gamePanel.setDayTime( Constants.DayTime.NIGHT);
                gamePanel.dispatchEvent(new SimpleMessages(gamePanel , Messages.REPAINT));
            }
        };

        this.schedule(timerTask , this.time /4 , this.time/4 );
    }
}
