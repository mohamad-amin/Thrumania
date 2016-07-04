package thrumania.board.item.GameItems.buildings;

import thrumania.board.item.GameItems.LiveElementItems.Health;
import thrumania.board.item.GameItems.LiveElementItems.Side;
import thrumania.board.item.GameItems.LiveElements;
import thrumania.board.item.MapItems.Inside.Tree;
import thrumania.board.item.MapItems.Map;
import thrumania.gui.PlayBottomPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;

import java.awt.*;

/**
 * Created by AMIR on 6/28/2016.
 */
public class WoodQuarry extends LiveElements {

    //    TODO : @amirhosein :  requirments for building one

    public WoodQuarry(Coordinate realPosition, Coordinate startingPoint , int sideNumber , PlayBottomPanel playBottomPanel,Map map) {
        this.map= map;
        map.getCell(realPosition.getRow(),realPosition.getColumn()).setCanSetBuilding(false);
        map.getCell(startingPoint.getRow(),startingPoint.getColumn()).setCanSetBuilding(false);
        this.playBottomPanel = playBottomPanel;
        this.side = new Side(sideNumber);
        this.startingPoint = startingPoint;
        this.realPosition = realPosition;
        this.health = new Health (2000,2000);
        setWithOnePicture("construction.png");
        ChangeElementsPlayerNumber(1);
    }

    private void ChangeElementsPlayerNumber(int task) {
        int startx = (-(Constants.OcupationOfQuarry))+ realPosition.getRow();
        int starty = (-(Constants.OcupationOfQuarry))+ realPosition.getColumn();
        int endx = (Constants.OcupationOfQuarry)+ realPosition.getRow();
        int endy = (Constants.OcupationOfQuarry)+ realPosition.getColumn();
        if (startx < 0 ) startx =0;
        if (starty < 0) starty = 0;
        if (endx >= Constants.MATRIX_HEIGHT) endx = Constants.MATRIX_HEIGHT -1;
        if (endy >= Constants.MATRIX_WIDTH) endy = Constants.MATRIX_WIDTH -1;
        for (int i = startx; i<=endx ;i++){
            for (int j = starty; j<=endy ;j++){
                if (map.getCell(i,j).getInsideElementsItems() instanceof Tree){
                    if (task ==1) Occupy(i,j);
                    else UnOccupy(i,j);
                }
            }
        }
    }

    private void Occupy(int i , int j){
        ((Tree) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(playerNumber);
    }

    private void UnOccupy(int i, int j) {
        ((Tree) map.getCell(i,j).getInsideElementsItems()).setPlayerNumber(-1);
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
    public void findingSelectedObject(int mouseXcord, int mouseYcord) {}

    @Override
    public void constructed() {
        underConstructed = false;
        setWithOnePicture("woodquarry.png");
    }

    @Override
    public void destroy() {
        super.destroy();
        ChangeElementsPlayerNumber(2);
    }
}
