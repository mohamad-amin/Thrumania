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
    static ThreadPoolExecutor threadPoolExecutor;



//    private HumanManagers(){
//       // instance = new HumanManagers();
//        instance= this;
//        this.makingThreadPool();
//    }

    public static HumanManagers getSharedInstance(){
         if( instance == null ) {
             instance = new HumanManagers();
             threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();


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


    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }
}
