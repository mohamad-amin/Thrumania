package thrumania.board.item.GameItems.LiveElementItems;

/**
 * Created by AMIR on 6/28/2016.
 */
public class Capacity {
    int wood;
    int iron;
    int gold;

    public int getWood() {
        return wood;
    }

    public void setWood(int wood) {
        this.wood = wood;
    }

    public void addWood(int wood){
        this.wood=this.wood+wood;
    }

    public int getIron() {
        return iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }

    public void addIron(int iron){
        this.iron=this.iron+iron;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void addGold(int gold) {
        this.gold = this.gold + gold;
    }
}
