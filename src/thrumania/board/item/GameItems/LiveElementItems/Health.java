package thrumania.board.item.GameItems.LiveElementItems;

/**
 * Created by AMIR on 6/28/2016.
 */
public class Health {
    public int maximumhealth;
    public int health;

    public Health(int maximumhealth, int health) {
        this.maximumhealth = maximumhealth;
        this.health = health;
    }
    public void dec(int num){
        this.health = this.health - num;
    }
    public void inc(int num){
        this.health = this.health - num;
    }

    public int getHealth() {
        return health;
    }

    public int getMaximumhealth() {
        return maximumhealth;
    }
}
