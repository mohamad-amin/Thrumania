package thrumania.managers;

import thrumania.board.item.GameItems.people.Human;
import thrumania.utils.Constants;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by sina on 6/24/16.
 */
public class HumanManagers {
    // singletone
    static HumanManagers instance;
//    private ArrayList<Human> humans = new ArrayList<>();
    private ArrayList<Human> [ ] humans ;

    static ThreadPoolExecutor threadPoolExecutor;



//    private HumanManagers(){
//       // instance = new HumanManagers();
//        instance= this;
//        this.makingThreadPool();
//    }

    public static HumanManagers getSharedInstance(){
         if( instance == null ) {
             System.out.println("player number is" + Constants.NUMBER_OF_PLAYERS);
             instance = new HumanManagers();
             instance.humans = new ArrayList[Constants.NUMBER_OF_PLAYERS];
             for (int i = 0; i < instance.humans.length; i++) {
                 instance.humans[i] = new ArrayList<Human>();
             }
             threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//                threadPoolExecutor = new ThreadPoolExecutor(250);

//             new HumanManagers();
         }
        return  instance;


     }



        private   void makingThreadPool()   {


             threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
//            for ( int i =0 ; i< humans.size() ; i++){
//                System.out.println("1");
//                if( ! humans.get(i).isMoving())
//                threadPoolExecutor.execute( ( Runnable) humans.get(i));
//
//            }



//            threadPoolExecutor.shutdown();




        }




    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public ArrayList<Human>[] getHumans() {
        return humans;
    }
}
