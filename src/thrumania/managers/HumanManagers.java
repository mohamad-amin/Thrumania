package thrumania.managers;

import thrumania.board.item.GameItems.people.Human;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sina on 6/24/16.
 */
public class HumanManagers {
    // singletone
    static HumanManagers instance;
    private ArrayList<Human> humans = new ArrayList<>();
    ThreadPoolExecutor threadPoolExecutor;
    public static HumanManagers getSharedInstance(){
         if( instance == null )
             instance = new HumanManagers();
        return  instance;


     }




        public  void makingThreadPool()   {


             threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            for ( int i =0 ; i< humans.size() ; i++){
                if( ! humans.get(i).isMoving())
                threadPoolExecutor.execute( ( Runnable) humans.get(i));

            }


            threadPoolExecutor.shutdown();





        }


    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }
}
