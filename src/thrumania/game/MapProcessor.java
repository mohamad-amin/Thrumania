package thrumania.game;

import thrumania.board.item.MapItems.Cell;
import thrumania.utils.Constants;
import thrumania.utils.Coordinate;
import thrumania.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by mohamadamin on 6/23/16.
 */
public class MapProcessor {

    public static MapProcessor mapProcessor;

    private long[][][][] distances;
    private List<List<Cell>> islands;
    private List<Cell> lands;
    private Cell[][] cells;

    public MapProcessor(Cell[][] cells) {
        this.cells = cells;
        this.lands = new ArrayList<>();
        this.islands = new ArrayList<>();
        fillDistances();
        fillLands();
        mapProcessor = this;
    }

    public static MapProcessor getInstance() {
        return mapProcessor;
    }

    public long[][][][] getDistances() {
        return distances;
    }

    public List<List<Cell>> getIslands() {
        return islands;
    }

    public List<Cell> getLands() {
        return lands;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void initializeDistances() {
        distances = new long[cells.length][cells[0].length][cells.length][cells[0].length];
//        for (int i=0; i<cells.length; i++) {
//            for (int j=0; j<cells[0].length; j++) {
//                for (int x=0; x<cells.length; x++) {
//                    for (int y=0; y<cells[0].length; y++) {
//                        distances[i][j][x][y] = Constants.DISTANCE_ISLAND_DISTINGUISHER;
//                    }
//                }
//            }
//        }
    }

    private void fillDistances() {
        initializeDistances();
        for (int i=0; i<cells.length; i++) {
            for (int j=0; j<cells[0].length; j++) {
                distances[i][j][i][j] = 0;
            }
        }
        for (int i=0; i<cells.length; i++) {
            for (int j=0; j<cells[0].length; j++) {
                if (cells[i][j].getId() < 3) {
                    lands.add(cells[i][j]);
                } else if (cells[i][j].getId() < 6) {
                    lands.add(cells[i][j]);
                } else if (cells[i][j].getId() < 8) {

                } else {

                }
//                if (IntegerUtils.isInRange(0, cells[0].length-1, j+1)) distances[i][j][i][j+1] = getDetachingEdgeWeight(i,j,i,j+1);
//                if (IntegerUtils.isInRange(0, cells[0].length-1, j-1)) distances[i][j][i][j-1] = getDetachingEdgeWeight(i,j,i,j-1);
//                if (IntegerUtils.isInRange(0, cells.length-1, i+1)) distances[i][j][i+1][j] = getDetachingEdgeWeight(i,j,i+1,j);
//                if (IntegerUtils.isInRange(0, cells.length-1, i-1)) distances[i][j][i-1][j] = getDetachingEdgeWeight(i,j,i-1,j);
//                if (IntegerUtils.isInRange(0, cells[0].length-1, j+1)) distances[i][j][i][j+1] = getEdgeWeight(i,j,i,j+1);
//                if (IntegerUtils.isInRange(0, cells[0].length-1, j-1)) distances[i][j][i][j-1] = getEdgeWeight(i,j,i,j-1);
//                if (IntegerUtils.isInRange(0, cells.length-1, i+1)) distances[i][j][i+1][j] = getEdgeWeight(i,j,i+1,j);
//                if (IntegerUtils.isInRange(0, cells.length-1, i-1)) distances[i][j][i-1][j] = getEdgeWeight(i,j,i-1,j);
            }
        }
        int i, j;
        for (Cell cell : lands) {
            i = cell.getPosition().getRow();
            j = cell.getPosition().getColumn();
            distances[i][j] = floydDijikstra(i, j);
        }
    }

    private long[][] floydDijikstra(int u, int v) {

        long[][] distances = new long[cells.length][cells[0].length];
        boolean[][] visited = new boolean[cells.length][cells[0].length];

        for (int i=0; i<distances.length; i++) {
            for (int j=0; j<distances[0].length; j++) {
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        distances[u][v] = 0;

        for (int i=0; i<distances.length; i++) {
            for (int j=0; j<distances[0].length; j++) {

                final Coordinate position = getMinVertex(distances, visited);
                int r = position.getRow(), c = position.getColumn();
                visited[r][c] = true;

                List<Coordinate> neighbours = getNeighboursDirect(r,c);
                for (Coordinate neighbour : neighbours) {
                    long d = distances[r][c] + getDetachingEdgeWeight(r, c, neighbour.getRow(), neighbour.getColumn());
                    if (distances[neighbour.getRow()][neighbour.getColumn()] > d) {
                        distances[neighbour.getRow()][neighbour.getColumn()] = d;
                    }
                }

            }
        }

        return distances;
        
    }

    public Stack<Coordinate> getPath(Coordinate start, Coordinate end) {
        Coordinate[][] path = pathDijikstra(start.getRow(), start.getColumn());
        Stack<Coordinate> result = new Stack<>();
        while (end != null) {
            result.add(end);
            end = path[end.getRow()][end.getColumn()];
        }
        return result;
    }

    public Coordinate[][] pathDijikstra(int u, int v) {

        long[][] distances = new long[cells.length][cells[0].length];
        boolean[][] visited = new boolean[cells.length][cells[0].length];
        Coordinate[][] path = new Coordinate[cells.length][cells[0].length];

        for (int i=0; i<distances.length; i++) {
            for (int j=0; j<distances[0].length; j++) {
                distances[i][j] = Integer.MAX_VALUE;
            }
        }
        distances[u][v] = 0;

        for (int i=0; i<distances.length; i++) {
            for (int j=0; j<distances[0].length; j++) {

                final Coordinate position = getMinVertex(distances, visited);
                int r = position.getRow(), c = position.getColumn();
                visited[r][c] = true;

                List<Coordinate> neighbours = getNeighbours(r,c);
                for (Coordinate neighbour : neighbours) {
                    long d = distances[r][c] + getDetachingEdgeWeight(r, c, neighbour.getRow(), neighbour.getColumn());
                    if (distances[neighbour.getRow()][neighbour.getColumn()] > d) {
                        distances[neighbour.getRow()][neighbour.getColumn()] = d;
                        path[neighbour.getRow()][neighbour.getColumn()] = new Coordinate(r, c);
                    }
                }

            }
        }

        return path;

    }

    private Coordinate getMinVertex(long[][] distances, boolean[][] visited) {
        long x = Integer.MAX_VALUE;
        Coordinate position = new Coordinate(-1, -1);
        for (int i=0; i<distances.length; i++) {
            for (int j=0; j<distances[0].length; j++) {
                if (!visited[i][j] && distances[i][j] <= x) {
                    position.setRow(i);
                    position.setColumn(j);
                    x = distances[i][j];
                }
            }
        }
        return position;
    }

    private List<Coordinate> getNeighboursDirect(int i, int j) {
        List<Coordinate> neighbuors = new ArrayList<>();
        if (IntegerUtils.isInRange(0, cells[0].length-1, j+1)) neighbuors.add(new Coordinate(i, j+1));
        if (IntegerUtils.isInRange(0, cells[0].length-1, j-1)) neighbuors.add(new Coordinate(i, j-1));
        if (IntegerUtils.isInRange(0, cells.length-1, i+1)) neighbuors.add(new Coordinate(i+1, j));
        if (IntegerUtils.isInRange(0, cells.length-1, i-1)) neighbuors.add(new Coordinate(i-1, j));
        return neighbuors;
    }

    private List<Coordinate> getNeighbours(int i, int j) {
        List<Coordinate> neighbuors = new ArrayList<>();
        if (IntegerUtils.isInRange(0, cells[0].length-1, j+1)) neighbuors.add(new Coordinate(i, j+1));
        if (IntegerUtils.isInRange(0, cells[0].length-1, j-1)) neighbuors.add(new Coordinate(i, j-1));
        if (IntegerUtils.isInRange(0, cells.length-1, i+1)) neighbuors.add(new Coordinate(i+1, j));
        if (IntegerUtils.isInRange(0, cells.length-1, i-1)) neighbuors.add(new Coordinate(i-1, j));
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1))
            neighbuors.add(new Coordinate(i-1, j-1));
        if (IntegerUtils.isInRange(0, cells.length-1, i-1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1))
            neighbuors.add(new Coordinate(i-1, j+1));
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j-1))
            neighbuors.add(new Coordinate(i+1, j-1));
        if (IntegerUtils.isInRange(0, cells.length-1, i+1) && IntegerUtils.isInRange(0, cells[0].length-1, j+1))
            neighbuors.add(new Coordinate(i+1, j+1));
        return neighbuors;
    }

    private int getDetachingEdgeWeight(int i, int j, int x, int y) {
        int result = Integer.MAX_VALUE;
        if (cells[i][j].getId() < 3) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_LOWLAND_LOWLAND;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_LOWLAND_HIGHLAND;
            } else if (cells[x][y].getId() < 8) {
                result = Constants.DISTANCE_ISLAND_DISTINGUISHER;
            }
        } else if (cells[i][j].getId() < 6) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_LOWLAND_HIGHLAND;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_HIGHLAND_HIGHLAND;
            } else {
                result = Constants.DISTANCE_ISLAND_DISTINGUISHER;
            }
        } else if (cells[i][j].getId() < 8) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_ISLAND_DISTINGUISHER;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_ISLAND_DISTINGUISHER;
            } else {
                result = Constants.DISTANCE_ISLAND_DISTINGUISHER;
            }
        } else {
            // Todo maybe
        }
        return result;
    }

    private int getXXXEdgeWeight(int i, int j, int x, int y) {
        int result = Integer.MAX_VALUE;
        if (cells[i][j].getId() < 3) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_LOWLAND_LOWLAND;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_LOWLAND_HIGHLAND;
            } else if (cells[x][y].getId() < 8) {
                result = Constants.DISTANCE_WATER_LAND;
            }
        } else if (cells[i][j].getId() < 6) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_LOWLAND_HIGHLAND;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_HIGHLAND_HIGHLAND;
            } else {
                result = Constants.DISTANCE_WATER_WATER;
            }
        } else if (cells[i][j].getId() < 8) {
            if (cells[x][y].getId() < 3) {
                result = Constants.DISTANCE_WATER_LAND;
            } else if (cells[x][y].getId() < 6) {
                result = Constants.DISTANCE_WATER_WATER;
            } else if (cells[x][y].getId() < 8) {
                // Todo: maybe
            }
        } else {
            // Todo maybe
        }
        return result;
    }

    private void fillLands() {
        int z;
        boolean added;
        for (int i=0; i<cells.length; i++) {
            for (int j=0; j<cells[0].length; j++) {
                if (cells[i][j].getId() >= 6) continue;
                z = 0;
                added = false;
                for (List<Cell> island : islands) {
                    Coordinate position = island.get(0).getPosition();
                    if (distances[i][j][position.getRow()][position.getColumn()] < Constants.DISTANCE_ISLAND_DISTINGUISHER) {
                        island.add(cells[i][j]);
                        islands.set(z, island);
                        added = true;
                    }
                    z++;
                }
                if (!added) {
                    List<Cell> newIsland = new ArrayList<>();
                    newIsland.add(cells[i][j]);
                    islands.add(newIsland);
                }
            }
        }
    }

    public List<Cell> findFurthestPoints(int howMany) {
        long distance = -1;
        List<Cell> result = new ArrayList<>();
        for (Cell i : lands) {
            for (Cell j : lands) {
                if (getDistance(i, j) > distance) {
                    distance = getDistance(i, j);
                    result.clear();
                    result.add(i);
                    result.add(j);
                }
            }
        }
        for (int i=2; i<howMany; i++) {
            distance = -1;
            Cell c = null;
            for (Cell cell : lands) {
                long tempDistance = 0;
                for (int j=0; j<i; j++) {
                    tempDistance += getDistance(cell, result.get(j));
                }
                if (tempDistance > distance) {
                    distance = tempDistance;
                    c = cell;
                }
            }
            result.add(c);
        }
        return result;
    }

    private long getDistance(Cell a, Cell b) {
        return getDistance(a.getPosition(), b.getPosition());
    }

    private long getDistance(Coordinate a, Coordinate b) {
        return distances[a.getRow()][a.getColumn()][b.getRow()][b.getColumn()];
    }

}
