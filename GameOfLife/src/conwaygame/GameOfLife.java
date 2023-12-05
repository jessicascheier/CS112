package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int r = StdIn.readInt();
        int c = StdIn.readInt();
        grid = new boolean[r][c];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = StdIn.readBoolean();
                if (grid[i][j] == true) {
                    totalAliveCells++;
                }
            }
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        if (grid[row][col] == true) {
            return true;
        }
        return false;
        // WRITE YOUR CODE HERE
        //return true; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {
        if (getTotalAliveCells() >= 1) {
            return true;
        }
        // WRITE YOUR CODE HERE
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        int numAlive = 0;

        if ( (row != 0) && (grid[row - 1][col] == true) ) { //top
            numAlive++;
        }
        if ( (col != 0) && (grid[row][col - 1] == true) ) { //left
            numAlive++;
        }
        if ( (row + 1 < grid.length) && (grid[row + 1][col] == true) ) { //bottom
            numAlive++;
        }
        if ( (col + 1 < grid[row].length) && (grid[row][col + 1] == true) ) { //right
            numAlive++;
        }
        if ( (row != 0) && (col != 0) && (grid[row - 1][col - 1] == true) ) { //top left
            numAlive++;
        }
        if ( (row != 0) && (col + 1 < grid[row].length) && (grid[row - 1][col + 1] == true) ) { //top right
            numAlive++;
        }
        if ( (row + 1 < grid.length) && (col != 0) && (grid[row + 1][col - 1] == true) ) { //bottom left
            numAlive++;
        }
        if ( (row + 1 < grid.length) && (col + 1 < grid[row].length) && (grid[row + 1][col + 1] == true) ) { //bottom right
            numAlive++;
        }

        //top row excluding corner cells wrap around -- FIXED
        if (row == 0 && col != 0 && col != grid[row].length - 1) {
            if (grid[grid.length - 1][col] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][col - 1] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][col + 1] == true) {
                numAlive++;
            }
        }

        //top left corner cell wrap around -- FIXED
        if (col == 0 && row == 0) {
            if (grid[0][grid[0].length - 1] == true) { //right side same row
                numAlive++;
            }
            if (grid[1][grid[0].length - 1] == true) { //right side one row down
                numAlive++;
            }
            if (grid[grid.length - 1][0] == true) { //bottom row wrap
                numAlive++;
            }
            if (grid[grid.length - 1][1] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][grid[0].length - 1] == true) {
                numAlive++;
            }
        }

        //left column cells excluding corners wrap around -- FIXED
        if (col == 0 && row != 0 && row != grid.length - 1) {
            if (grid[row][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[row - 1][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[row + 1][grid[row].length - 1] == true) {
                numAlive++;
            }
        }

        //bottom left corner cell wrap around -- FIXED
        if (row == grid.length - 1 && col == 0) {
            if (grid[row][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[row - 1][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[0][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[0][0] == true) {
                numAlive++;
            }
            if (grid[0][1] == true) {
                numAlive++;
            }
        }
        
        //Bottom row, excluding corner, wrap around -- FIXED
        if (row == grid.length - 1 && col != 0 && col != grid[row].length - 1) {
            if (grid[0][col] == true) {
                numAlive++;
            }
            if (grid[0][col - 1] == true) {
                numAlive++;
            }
            if (grid[0][col + 1] == true) {
                numAlive++;
            }
        }

        //Bottom right corner cell wrap around -- FIXED
        if (row == grid.length - 1 && col == grid[row].length - 1) {
            if (grid[0][0] == true) {
                numAlive++;
            }
            if (grid[0][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[0][grid[row].length - 2] == true) {
                numAlive++;
            }
            if (grid[row][0] == true) {
                numAlive++;
            }
            if (grid[row - 1][0] == true) {
                numAlive++;
            }
        }

        //right column cells excluding corner wrap around -- FIXED
        if (col == grid[row].length - 1 && row != grid.length - 1 && row != 0) {
            if (grid[row][0] == true) {
                numAlive++;
            }
            if (grid[row - 1][0] == true) {
                numAlive++;
            }
            if (grid[row + 1][0] == true) {
                numAlive++;
            }
        }

        //top right corner cell wrap around -- FIXED
        if (row == 0 && col == grid[row].length - 1) {
            if (grid[row][0] == true) {
                numAlive++;
            }
            if (grid[row + 1][0] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][0] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][grid[row].length - 1] == true) {
                numAlive++;
            }
            if (grid[grid.length - 1][grid[row].length - 2] == true) {
                numAlive++;
            }
        }

        // WRITE YOUR CODE HERE
        return numAlive; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        boolean[][] newGrid = grid;
        ArrayList<Integer> log = new ArrayList<Integer>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                log.add(numOfAliveNeighbors(i, j));
            }
        }
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == true && log.get(count) <= 1) {
                    newGrid[i][j] = false; // alive cells with 1 or 0 neighbors die of loneliness
                } else if (grid[i][j] == false && log.get(count) == 3) {
                    newGrid[i][j] = true; // dead cells alive by reproduction
                } else if (grid[i][j] == true && ((log.get(count) == 2 || log.get(count) == 3))) {
                    newGrid[i][j] = true; // alive cells with 2 or 3 neighbors survive
                } else if (grid[i][j] == true && log.get(count) >= 4) {
                    newGrid[i][j] = false; // alive cells with 4+ neighbors die from overpopulation
                }
                count++;
            }
        }
        // WRITE YOUR CODE HERE
        return newGrid; // update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        int aliveCount = 0;
        boolean[][] compute = computeNewGrid();
        for (int i = 0; i < compute.length; i++) {
            for (int j = 0; j < compute[i].length; j++) {
                if (compute[i][j] == true) {
                    aliveCount++;
                }
            }
        }
        grid = compute;
        totalAliveCells = aliveCount;
        // WRITE YOUR CODE HERE
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {
        while (n != 0) {
            nextGeneration();
            n--;
        }
        // WRITE YOUR CODE HERE
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
        WeightedQuickUnionUF var = new WeightedQuickUnionUF(grid.length, grid[0].length);
        int communities = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == true) {

                //top right corner cell neighbors -- FIXED
                if (i == 0 && j == grid[i].length - 1) {
                    if (grid[i][0] == true) { // left corner
                        var.union(i, j, i, 0);
                    }
                    if (grid[i + 1][0] == true) { //left corner down 1
                        var.union(i, j, i + 1, 0);
                    }
                    if (grid[grid.length - 1][grid[i].length - 1] == true) { //bottom right corner
                        var.union(i, j, grid.length - 1, grid[i].length - 1);
                    }
                    if (grid[grid.length - 1][0] == true) { //bottom left corner
                        var.union(i, j, grid.length - 1, 0);
                    }
                    if (grid[grid.length - 1][grid[i].length - 2] == true) {
                        var.union(i, j, grid.length - 1, grid[i].length - 2);
                    }
                }

                //right column cells excluding corner wrap around -- FIXED
                if (j == grid[i].length - 1 && i != grid.length - 1 && i != 0) {
                    if (grid[i][0] == true) { //wrap to left side
                        var.union(i, j, i, 0);
                    }
                    if (grid[i - 1][0] == true) { //wrap to left side up 1
                        var.union(i, j, i - 1, 0);
                    }
                    if (grid[i + 1][0] == true) { //wrap to left side down 1
                        var.union(i, j, i + 1, 0);
                    }
                }

                //Bottom right corner cell wrap around -- FIXED
                if (i == grid.length - 1 && j == grid[i].length - 1) {
                    if (grid[0][grid[i].length - 1] == true) { //top right
                        var.union(i, j, 0, grid[i].length - 1);
                    }
                    if (grid[0][grid[i].length - 2] == true) { //top right left 2
                        var.union(i, j, 0, grid[i].length - 2);
                    }
                    if (grid[i][0] == true) { //leftmost
                        var.union(i, j, i, 0);
                    }
                    if (grid[i - 1][0] == true) { //leftmost up 1
                        var.union(i, j, i - 1, 0);
                    }
                    if (grid[0][0] == true) {
                        var.union(i, j, 0, 0);
                    }
                }

                //Bottom row, excluding corner, wrap around -- FIXED
                if (i == grid.length - 1 && j != 0 && j != grid[i].length - 1) {
                    if (grid[0][j] == true) { //cell in BR, cells in b/t corners will wrap to top
                        var.union(i, j, 0, j);
                    }
                    if (grid[0][j - 1] == true) { //cell in BR, wrap to top left 1
                        var.union(i, j, 0, j - 1);
                    }
                    if (grid[0][j + 1] == true) { //cell in BR, wrap to top right 1
                        var.union(i, j, 0, j + 1);
                    }
                }

                //bottom left corner cell wrap around -- FIXED
                if (i == grid.length - 1 && j == 0) {
                    if (grid[i][grid[i].length - 1] == true) { //wrap around to right side for bottom left corner cell
                        var.union(i, j, i, grid[i].length - 1);
                    }
                    if (grid[i - 1][grid[i].length - 1] == true) { //wrap around BL cell up 1
                        var.union(i, j, i - 1, grid[i].length - 1);
                    }
                    if (grid[0][grid[i].length - 1] == true) { // BL cell top right corner wrap
                        var.union(i, j, 0, grid[i].length - 1);
                    }
                    if (grid[0][0] == true) {
                        var.union(i, j, 0, 0);
                    }
                    if (grid[0][1] == true) {
                        var.union(i, j, 0, 1);
                    }
                }

                //left column cells excluding corners wrap around -- FIXED
                if (j == 0 && i != 0 && i != grid.length - 1) {
                    if (grid[i][grid[i].length - 1] == true) { //wrap to right side
                        var.union(i, j, i, grid[i].length - 1);
                    }
                    if (grid[i - 1][grid[i].length - 1] == true) { //wrap to right side up 1
                        var.union(i, j, i - 1, grid[i].length - 1);
                    }
                    if (grid[i + 1][grid[i].length - 1] == true) { //wrap to right side down 1
                        var.union(i, j, i + 1, grid[i].length - 1);
                    }
                }

                //top left corner cell wrap around -- FIXED
                if (j == 0 && i == 0) {
                    if (grid[i][grid[i].length - 1] == true) { //wrap to right side, same row
                        var.union(i, j, i, grid[i].length - 1);
                    }
                    if (grid[i + 1][grid[i].length - 1] == true) { //wrap to right side, one row down
                        var.union(i, j, i + 1, grid[i].length - 1);
                    }
                    if (grid[grid.length - 1][grid[i].length - 1] == true) { //wrap to right side, very last column and row
                        var.union(i, j, grid.length - 1, grid[i].length - 1);
                    }
                    if (grid[grid.length - 1][j] == true) {
                        var.union(i, j, grid.length - 1, j);
                    }
                    if (grid[grid.length - 1][j + 1] == true) {
                        var.union(i, j, grid.length - 1, j + 1);
                    }
                }

                //top row excluding corner cells wrap around -- FIXED
                if (i == 0 && j != 0 && j != grid[i].length - 1) {
                    if (grid[grid.length - 1][j] == true) { // top wraps to bottom
                        var.union(i, j, grid.length - 1, j);
                    }
                    if (grid[grid.length - 1][j - 1] == true) { //top wraps to bottom, shifts to left 1
                        var.union(i, j, grid.length - 1, j - 1);
                    }
                    if (grid[grid.length - 1][j + 1] == true) { //top wraps to bottom, shifts to right 1
                        var.union(i, j, grid.length - 1, j + 1);
                    }
                }

                //general neighbors
                if ( (i != 0) && (grid[i - 1][j] == true) ) { //top
                    var.union(i, j, i - 1, j);
                }
                if ( (j != 0) && (grid[i][j - 1] == true) ) { //left
                    var.union(i, j, i, j - 1);
                }
                if ( (i + 1 < grid.length) && (grid[i + 1][j] == true) ) { //bottom
                    var.union(i, j, i + 1, j);
                }
                if ( (j + 1 < grid[i].length) && (grid[i][j + 1] == true) ) { //right
                    var.union(i, j, i, j + 1);
                }
                if ( (i != 0) && (j != 0) && (grid[i - 1][j - 1] == true) ) { //top left
                    var.union(i, j, i - 1, j - 1);
                }
                if ( (i != 0) && (j + 1 < grid[i].length) && (grid[i - 1][j + 1] == true) ) { //top right
                    var.union(i, j, i - 1, j + 1);
                }
                if ( (i + 1 < grid.length) && (j != 0) && (grid[i + 1][j - 1] == true) ) { //bottom left
                    var.union(i, j, i + 1, j - 1);
                }
                if ( (i + 1 < grid.length) && (j + 1 < grid[i].length) && (grid[i + 1][j + 1] == true) ) { //bottom right
                    var.union(i, j, i + 1, j + 1);
                }

                }

                }
            }

            ArrayList<Integer> roots = new ArrayList<Integer>();
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == true /*&& roots.contains(roots.get(i) )*/) {
                        roots.add(var.find(i, j));
                    }
                }
            }

            ArrayList<Integer> temp = new ArrayList<Integer>();
            for (int i = 0; i < roots.size(); i++) {
                if (temp.contains(roots.get(i)) != true) {
                    temp.add(roots.get(i));
                }
            }

        communities = temp.size();
        // WRITE YOUR CODE HERE
        return communities; // update this line, provided so that code compiles
    }
}