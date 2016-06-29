package thrumania.board.item.MapItems;

import thrumania.board.item.InsideElementsItems;
import thrumania.board.item.MapItems.Cells.Cell;
import thrumania.board.item.MapItems.Cells.HighLand;
import thrumania.board.item.MapItems.Cells.LowLand;
import thrumania.board.item.MapItems.Cells.Sea;
import thrumania.gui.MiniMapPanel;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

/**
 * Created by mohamadamin on 5/18/16.
 */

// Row, Column

    // TODO : should handle drawing land next to stone_mine && Gold_mine :

public class Map {

    private MiniMapPanel miniMap;
    private Cell[][] cells;
    private int width, height;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        fillCells();
    }

    public void setMiniMap(MiniMapPanel miniMap) {
        this.miniMap = miniMap;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void fillCells() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = new Sea(new Coordinate(i, j));
                cell.setPictureName("ocean1.jpg");
                cells[i][j] = cell;
            }
        }
        if (miniMap != null) miniMap.updateMap();
    }

    public void changeMap(int i, int j,int type) {
        boolean valid = checkvalid(i , j ,type);
        if(valid) {
            int[][] adjacent = createAdjecant(i, j, type);
            intilizeAdjacent(adjacent, i, j, type);
            checkMiddleCell(adjacent, i, j, type);
            updateAdjecant(adjacent, i, j);
            updateOutAdjacent(i, j, type);
            if (miniMap != null) miniMap.updateMap();
        }
    }

    private boolean checkvalid(int i , int j , int type) {
        if(type == 2){
            boolean valid =true;
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (inRange(i + x, j + y) && (cells[i + x][j + y] instanceof Sea)) {
                        valid =false ;
                    }
                }
            }
            return valid;
        }else return true;
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];

    }

    private void updateOutAdjacent(int i, int j,int type) {
        if(type==1) {
            for (int x = -2; x < 3; x = x + 4) {
                if (IntegerUtils.isInRange(0, Constants.MATRIX_HEIGHT - 1, i + x))
                    if (cells[i + x][j] instanceof LowLand) numberAndLoad(i + x, j);
                if (IntegerUtils.isInRange(0, Constants.MATRIX_WIDTH - 1, j + x))
                    if (cells[i][j + x] instanceof LowLand) numberAndLoad(i, j + x);
            }
        }
        if (type ==0) {
            for (int x=-2;x<3;x=x+4){
                for (int y=-1;y<2;y++){
                   if (IntegerUtils.isInRange(0, Constants.MATRIX_HEIGHT - 1, i + x)&&
                           IntegerUtils.isInRange(0, Constants.MATRIX_WIDTH - 1, j + y)&&
                           (cells[i + x][j+y] instanceof LowLand || cells[i + x][j+y] instanceof HighLand))
                       numberAndLoad(i + x, j+y);
                    if (IntegerUtils.isInRange(0, Constants.MATRIX_HEIGHT - 1, i + y)&&
                            IntegerUtils.isInRange(0, Constants.MATRIX_WIDTH - 1, j + x)&&
                            (cells[i + y][j+x] instanceof LowLand || cells[i + y][j+x] instanceof HighLand))
                        numberAndLoad(i + y, j+x);
                }
            }
        }
    }

    private void numberAndLoad(int i, int j) {
        int temptype =0;
        if(cells[i][j] instanceof LowLand)  temptype = 1;
        if(cells[i][j] instanceof HighLand) temptype = 2;
        int n = checkFourSideAndGiveMeNumber(i, j, temptype);
        n=n+((temptype-1)*64);
        if(temptype !=2) {
            InsideElementsItems element = cells[i][j].getInsideElementsItems();
            cells[i][j] = new LowLand(new Coordinate(i, j));
            cells[i][j].setPictureName(n + ".png");
            cells[i][j].setInsideElementsItems(element);
                if (n == 8 || n == 5 || n == 2 || n == 1 || n == 4 || n == 10) {
                    cells[i][j].setCompeleteLand(false);
                } else {
                    cells[i][j].setCompeleteLand(true);
                }
            cells[i][j].setLand(true);
        } else {
            InsideElementsItems element = cells[i][j].getInsideElementsItems();
            cells[i][j] = new HighLand(new Coordinate(i, j));
            cells[i][j].setPictureName(n + ".png");
            cells[i][j].setInsideElementsItems(element);
            cells[i][j].setLand(true);
        }
    }

    ///different kind of kind lowland=1 sea=0 for
    private int[][] createAdjecant(int i, int j,int type) {
        int adjacent[][] = new int[3][3];
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                if(type != 2) {
                    if (inRange(i + x, j + y) && (cells[i + x][j + y] instanceof LowLand || cells[i + x][j + y] instanceof HighLand)
                            && !(x == 0 && y == 0)) {
                        if (type == 1) {
                            if (x * y == 0) adjacent[x + 1][y + 1] = 1;
                            else {
                                adjacent[x + 1][y + 1] = 1;
                                adjacent[1][y + 1] = 1;
                                adjacent[x + 1][1] = 1;
                            }
                        } else {
                            adjacent[x + 1][y + 1] = 1;
                        }
                    }
                }
                else {
                    if (inRange(i + x, j + y) && x*y==0 && cells[i + x][j + y] instanceof HighLand && !(x == 0 && y == 0)) {
                            adjacent[x + 1][y + 1] = 1;
                    }
                }
            }
        }
        return adjacent;
    }

    private void intilizeAdjacent(int[][] adjacent, int i, int j, int type) {
        if (type==1) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (adjacent[x + 1][y + 1] == 1&& !(cells[i+x][j+y] instanceof HighLand || cells[i+x][j+y] instanceof LowLand)) {
                            cells[i + x][j + y] = new LowLand(new Coordinate(i + x, j + y));
                            cells[i + x][j + y].setPictureName("0.png");
                    }
                }
            }
        }
        if (type==0) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (adjacent[x + 1][y + 1] == 1&& cells[i + x][j + y] instanceof HighLand) {
                            cells[i + x][j + y] = new LowLand(new Coordinate(i + x, j + y));
                            cells[i + x][j + y].setPictureName("0.png");
                    }
                }
            }
        }
    }

    private void updateAdjecant(int[][] adjacent, int x, int y) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (adjacent[i + 1][j + 1] == 1) {
                    numberAndLoad(x + i, y + j);
                }
            }
        }
    }

    private boolean inRange(int i, int j) {
        return IntegerUtils.isInRange(0, Constants.MATRIX_HEIGHT - 1, i)
                && IntegerUtils.isInRange(0, Constants.MATRIX_WIDTH - 1, j);
    }

    private int inRangeAndCode(int i, int j, int type) {
        int x=0;
        if (type==0) if (inRange(i,j)) if(cells[i][j] instanceof LowLand) x=1; else x=0;
        if (type==1) if (inRange(i,j)) if(cells[i][j] instanceof LowLand || cells[i][j] instanceof HighLand) x=1; else x=0;
        if (type==2) if (inRange(i,j)) if(cells[i][j] instanceof HighLand) x=1; else x=0;
        return x;
    }

    private int checkFourSideAndGiveMeNumber(int i, int j, int type) {
        return inRangeAndCode(i - 1, j ,type) * 4 +
                    inRangeAndCode(i, j - 1 ,type) * 2 +
                    inRangeAndCode(i, j + 1 ,type) * 8 +
                    inRangeAndCode(i + 1, j, type);
    }
    private void checkMiddleCell(int[][] adjacent, int i, int j, int type) {
        int x = adjacent[0][1] * 4 +
                adjacent[1][0] * 2 +
                adjacent[1][2] * 8 +
                adjacent[2][1];
        if(type==1) {
            cells[i][j] = new LowLand(new Coordinate(i, j));
            cells[i][j].setPictureName(Integer.toString(x) + ".png");
            insideoflowland(i,j,x);
        }
        else if (type == 0) {
            cells[i][j] = new Sea(new Coordinate(i, j));
            cells[i][j].setPictureName("ocean1.jpg");
        }else {
            cells[i][j]= new HighLand(new Coordinate(i,j));
            cells[i][j].setPictureName(Integer.toString(x + 64) + ".png");
        }
    }

    private void insideoflowland(int i , int j , int x) {
        InsideElementsItems element = (cells[i][j] == null) ? null : cells[i][j].getInsideElementsItems();
        if (element != null) {
            if (element.getClass().getSimpleName().compareTo("SmallFish") != 0) {
                //might get problem here
                //   cells[i][j].setInsideMapElemetn(element);
            }
        } else cells[i][j].setInsideElementsItems(element);
        cells[i][j].setLand(true);

        if (x == 8 || x == 5 || x == 2 || x == 1 || x == 4 || x == 10) {
            cells[i][j].setCompeleteLand(false);

        } else {
            cells[i][j].setCompeleteLand(true);
        }
    }
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }


    //    private void updateAdjecant(ArrayList<Coordinate> updatebar) {
//        for (int i=0;i<updatebar.size();i++){
//            int x =checkFourSideAndGiveMeNumber(updatebar.get(i).getRow(),updatebar.get(i).getColumn());
//            cells[updatebar.get(i).getRow()][updatebar.get(i).getColumn()]= new LowLand(new Coordinate(updatebar.get(i).getRow(),updatebar.get(i).getColumn()));
//            cells[updatebar.get(i).getRow()][updatebar.get(i).getColumn()].setPictureName(new Integer(x).toString()+".png");
//        }
//    }


    //    public int getNumberOfCell(int x,int y,ArrayList<Coordinate> updatebar){
//        int number=0;
//        int factor=1;
//        for (int i=-1; i<2; i++) {
//            for (int j=-1; j<2; j++) {
//                int row = x+i;
//                int column = y+j;
//                int l=0;
//                if (IntegerUtils.isInRange(0, Constants.MATRIX_SIZE-1, row)
//                        && IntegerUtils.isInRange(0, Constants.MATRIX_SIZE-1, column)
//                        && !(new Coordinate(x,y).equals(new Coordinate(row, column)))
//                        &&cells[row][column] instanceof LowLand ) {
//                    l=1;
//                    updatebar.add(new Coordinate(row,column));
//                }
//                number += factor * l;
//                factor*=2;
//            }
//        }
//        return number;
//    }

    //    public int checkFourSideAndGiveMeNumber(int i,int j){
//        int x =cells[i-1][j].getCode()*4+
//                cells[i][j-1].getCode()*2+
//                cells[i][j+1].getCode()*8+
//                cells[i+1][j].getCode()*1;
//        System.out.println(x);
//        return x;
//    }

    //    public void changeMap(int i,int j){
//        ArrayList<Coordinate> updatebar= new ArrayList<>();
//        int number = getNumberOfCell(i,j,updatebar);
//        int[][] putCell = states.get(number);
//        for (int o=0;o<putCell.length;o++) {
//            int r = putCell[o][0] / 3 - 1;
//            int t = putCell[o][0] % 3 - 1;
//            int row = i + r;
//            int column = j + t;
//            if ((r==0||t==0)&& !(r==0&&t==0)){                           ////fixing a bug
//                updatebar.add(new Coordinate(row,column));
//                if (cells[i+2*r][j+2*t].getCode()==1) updatebar.add(new Coordinate( i+2*r,j+2*t));
//            }
//            cells[row][column] = new LowLand(new Coordinate(row, column));
//            cells[row][column].setPictureName(new Integer(putCell[o][1]).toString() + ".png");
//        }
//        updateAdjecant(updatebar);
//        System.out.println("map");
//    }

//    public void stateLoad(){
//        ArrayList<String> input =fileReader();
//        for (int i=0;i<input.size();i++){
//            String temp=input.get(i);
//            String[] divided = (temp.trim()).split(" ");
//            int [][] doing = new int [(divided.length-1)/2][2];
//            int key = Integer.parseInt(divided[0]);
//            for (int j=0;j<(divided.length-1)/2;j++) {
//                doing[j][0] = Integer.parseInt(divided[j * 2 + 1]);
//                doing[j][1] = Integer.parseInt(divided[j * 2 + 2]);
//            }
//            states.insert(key,doing);
//        }
//    }

    //    public ArrayList<String> fileReader(){
//        ArrayList <String> st = new ArrayList<>();
//        File f=null;
//        FileInputStream fis=null;
//        InputStreamReader isr=null;
//        BufferedReader br = null;
//        try {
//            String str;
//            f = new File("res/otomataStates");
//            fis=new FileInputStream(f);
//            isr= new InputStreamReader(fis);
//            br = new BufferedReader(isr);
//            while (true){
//                try{
//                    str=br.readLine().trim();
//                } catch (NullPointerException e){
//                    break;                                              ////so ahmaghane should be repaired later
//                }
//                st.add(str);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return st;
//    }
}
