package thrumania.board;

import thrumania.board.item.Cell;
import thrumania.board.item.LowLand;
import thrumania.board.item.Sea;
import thrumania.utils.Cacher;
import thrumania.utils.Coordinate;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by mohamadamin on 5/18/16.
 */

// Row, Column

public class Map {

    private Cell[][] cells;
    private int width, height;
    Cacher<Integer,int[][]> states;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width][height];
        this.states = new Cacher<>();
        stateLoad();
        fillCells();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void fillCells() {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                Cell cell = new Sea(new Coordinate(i, j));
                cell.setPictureName("ocean1.jpg");
                cells[i][j] = cell;
            }
        }
    }

    public void stateLoad(){
        ArrayList<String> input =fileReader();
        for (int i=0;i<input.size();i++){
            String temp=input.get(i);
            String[] divided = (temp.trim()).split(" ");
            int [][] doing = new int [(divided.length-1)/2][2];
            int key = Integer.parseInt(divided[0]);
            for (int j=0;j<(divided.length-1)/2;j++) {
                doing[j][0] = Integer.parseInt(divided[j * 2 + 1]);
                doing[j][1] = Integer.parseInt(divided[j * 2 + 2]);
            }
            states.insert(key,doing);
        }
    }

    public ArrayList<String> fileReader(){
        ArrayList <String> st = new ArrayList<>();
        File f=null;
        FileInputStream fis=null;
        InputStreamReader isr=null;
        BufferedReader br = null;
        try {
            String str;
            f = new File("res/otomataStates");
            fis=new FileInputStream(f);
            isr= new InputStreamReader(fis);
            br = new BufferedReader(isr);
            while (true){
                try{
                    str=br.readLine().trim();
                } catch (NullPointerException e){
                    break;                                              ////so ahmaghane should be repaired later
                }
                st.add(str);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return st;
    }

    public void changeMap(int i,int j){
        int number = cells[i][j].getNumberOfCell(cells);
        int[][] putCell = states.get(number);
        for (int o=0;o<putCell.length;o++){
            int row = i+putCell[o][0]/3-1;
            int column = j+putCell[o][0]%3-1;
            cells[row][column] = new LowLand(new Coordinate(row,column));
            cells[row][column].setPictureName(new Integer(putCell[o][1]).toString()+".png");
        }

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
