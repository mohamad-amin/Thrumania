package thrumania.managers;

import thrumania.AI.VirtualPlayer;
import thrumania.gui.PlayPanel;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by AMIR on 7/5/2016.
 */
public class AIManager {
    private VirtualPlayer [] player;
    static ThreadPoolExecutor AIThreadPoolExecuter ;
    int numberOfVirtualPlayers;
    PlayPanel playPanel;

    public AIManager(int numberOfVirtualPlayers, PlayPanel playPanel) {
        this.numberOfVirtualPlayers = numberOfVirtualPlayers;
        player = new VirtualPlayer[numberOfVirtualPlayers];
        AIThreadPoolExecuter =  (ThreadPoolExecutor) Executors.newCachedThreadPool();
        for (int i = 0 ;i < numberOfVirtualPlayers;i++){
            player [i] = new VirtualPlayer(i+1,playPanel);
            AIThreadPoolExecuter.execute(player[i]);
        }
    }
}
