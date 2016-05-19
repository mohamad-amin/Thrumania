package thrumania.board;

import thrumania.board.item.Cell;
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
       // stateLoad();
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
        ArrayList <String> st = new ArrayList<>();
        File f=null;
        FileInputStream fis=null;
        InputStreamReader isr=null;
        BufferedReader br = null;
        try {
            String str;
            f = new File("map.txt");
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
        int [][] maper = new int [st.size()][st.get(0).length()];
        for (int i=0;i<st.size();i++){
            char []temp=(st.get(i)).toCharArray();
            for (int j=0;j<temp.length;j++){
                maper[i][j]=(temp[j]-48);//
            }
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
