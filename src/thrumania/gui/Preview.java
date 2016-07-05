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
    private  Panels panel;
    private  TimerTask timerTask;
    private  int time ;
    private Constants.Seasons seasons;



    public Preview( Panels panel , int time) {
        this.gamePanel = gamePanel;
        this.panel = panel;
        this.time = time;
        this.dayTimer();
        this.seasonalTimer();
    }


    private void seasonalTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if( panel instanceof  GamePanel) {
                    seasons = ((GamePanel) panel).getSeason();
                    if (seasons == Constants.Seasons.WINTER) seasons = Constants.Seasons.SPRING;
                    else seasons = Constants.Seasons.values()[seasons.getValue() + 1];
                    ((GamePanel) panel).setSeason(seasons);
                    panel.dispatchEvent(new SimpleMessages(panel, Messages.REPAINT));
                }else if ( panel instanceof  PlayPanel){

                    seasons = ((PlayPanel) panel).getSeason();
                    if( seasons == Constants.Seasons.WINTER ) seasons = Constants.Seasons.SPRING;
                      else seasons = Constants.Seasons.values()[seasons.getValue() + 1];
                    ((PlayPanel) panel).setSeason(seasons);
                    panel.dispatchEvent(new SimpleMessages(panel , Messages.REPAINT));


                }
            }
        };
//        this.schedule(timerTask, Preview.this.time, Preview.this.time);
        this.schedule(timerTask ,this.time , this.time );

    }
    private void dayTimer(){
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if( panel instanceof  GamePanel){
                if (((GamePanel) panel).getDayTime() == Constants.DayTime.NIGHT) ((GamePanel) panel).setDayTime(Constants.DayTime.MORNING);
                else if( ((GamePanel) panel).getDayTime() == Constants.DayTime.MORNING ) ((GamePanel) panel).setDayTime(Constants.DayTime.NIGHT);
                panel.dispatchEvent(new SimpleMessages(panel , Messages.REPAINT));

            }else if ( panel instanceof  PlayPanel){
                    if (  ((PlayPanel) panel).getDayTime() == Constants.DayTime.NIGHT ) ((PlayPanel) panel).setDayTime(Constants.DayTime.MORNING);
                    else if( ((PlayPanel) panel).getDayTime()  == Constants.DayTime.MORNING) ((PlayPanel) panel).setDayTime(Constants.DayTime.NIGHT);
                    panel.dispatchEvent(new SimpleMessages(panel ,Messages.REPAINT));
                }




                }
        };

        this.schedule(timerTask , this.time /4 , this.time/4 );
    }
}
