package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.MapItems.Inside.GoldMine;
import thrumania.board.item.MapItems.Inside.StoneMine;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 7/1/2016.
 */
public class MineQuarry extends LiveElements{
    //    TODO : @amirhosein :  requirments for building one
    public MineQuarry(Coordinate realPosition, Coordinate startingPoint , int sideNumber , PlayBottomPanel playBottomPanel , Map map) {
        this.map = map;
        map.getCell(realPosition.getRow(),realPosition.getColumn()).setCanSetBuilding(false);
        map.getCell(startingPoint.getRow(),startingPoint.getColumn()).setCanSetBuilding(false);
        this.side = new Side(sideNumber);
        this.playerNumber = sideNumber;
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health(2000,2000);
        setWithOnePicture("construction.png");
        ChangeElementsPlayerNumber(1);
    }
/*
task one is occupy
task two is unoccupy
 */
    private void ChangeElementsPlayerNumber(int task) {
        int startx = (-(Constants.OcupationOfQuarry))+ realPosition.getColumn();
        int starty = (-(Constants.OcupationOfQuarry))+ realPosition.getRow();
        int endx = (Constants.OcupationOfQuarry)+ realPosition.getColumn();
        int endy = (Constants.OcupationOfQuarry)+ realPosition.getRow();
        if (startx < 0 ) startx =0;
        if (starty < 0) starty = 0;
        if (endx >= Constants.MATRIX_HEIGHT) endx = Constants.MATRIX_HEIGHT -1;
        if (endy >= Constants.MATRIX_WIDTH) endy = Constants.MATRIX_WIDTH -1;
        for (int i = startx; i<=endx ;i++) {
            for (int j = starty; j <= endy; j++) {
                if (task==1)Occupy(i,j);
                else UnOccupy(i,j);
            }
        }
    }

    private void Occupy(int i , int j) {
        if (map.getCell(i,j).getInsideElementsItems() instanceof StoneMine){
            ((StoneMine) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(playerNumber);
        }
        if (map.getCell(i,j).getInsideElementsItems() instanceof GoldMine){
            ((GoldMine) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(playerNumber);
        }
    }

    private  void UnOccupy(int i ,int j){
        if (map.getCell(i,j).getInsideElementsItems() instanceof StoneMine){
            ((StoneMine) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(-1);
        }
        if (map.getCell(i,j).getInsideElementsItems() instanceof GoldMine){
            ((GoldMine) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(-1);
        }
    }

    @Override
    public void paintingOptions(Graphics g) {
        super.paint(g);

        Font myFont = new Font("Party Business", Font.BOLD, 20);
        g.setFont(myFont);
        g.setColor(Color.WHITE);
        g.drawString("health :", 150, 30);
        g.drawString((Integer.toString(health.getHealth())), 300, 30);
        g.drawString("Side :", 150, 60);
        g.drawString((Integer.toString(side.getNumberOfPlayer() + 1)), 300, 60);

    }

    @Override
    public void findingSelectedObject(int x, int y) {

    }

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("minequarry.png");
    }

    @Override
    public void destroy() {
        super.destroy();
        ChangeElementsPlayerNumber(2);
    }
}
