package thrumania.AI;

/**
 * Created by sina on 7/5/16.
 */
public class State {
    int currentstate;

    public int getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(int currentstate) {
        this.currentstate = currentstate;
    }

    public int[] giveMePossibleTasks(){
        switch (currentstate){
            case 0 :
                int [] a0 = {1};
                return a0;
            case 1 :
                //soldier
                break;
            case 2 :
                //attack
                break;
            case 3 :
                break;
            case 4 :
                break;
            case 5:
                break;
            default:
                return null;
        }
        return null;
    }

    //Todo
    public int updateState(){
        return currentstate;
    }
}
