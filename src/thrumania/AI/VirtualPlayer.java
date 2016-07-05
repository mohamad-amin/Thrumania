package thrumania.AI;

import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayPanel;

/**
 * Created by AMIR on 7/5/2016.
 */
public class VirtualPlayer implements Runnable {
    int playernumber;
    PlayPanel playPanel;
    Functions functions ;
    State state;
    Map map;

    public VirtualPlayer(int playernumber , PlayPanel playPanel) {
        System.out.println(playernumber);
        this.map = playPanel.getMap();
        this.playPanel = playPanel;
        this.playernumber = playernumber;
        functions = new Functions(playernumber,playPanel);
        state = new State();
    }

    @Override
    public void run() {
        while(playPanel.gameIsON) {
            functions.doRandomIn(state.giveMePossibleTasks(),map);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            state.updateState();
        }
    }
}
