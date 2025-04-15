package lab09;

/**
 * A grid object represents the grid part of a life simulation.
 * The grid object keeps track of the state of each square
 * (alive, dead), and allows the user to make one step in the
 * life simulation.
 * 
 * Note that this class does not do any drawing or other I/O.
 * It just represents a grid.
 * 
 * @author Peter Jensen and Natalie Wilkins
 * @version 25 October 2022
 */
public class Grid
{
    boolean[][] grid;
    boolean stateOfSquare = false;
    int height;
    int width;
    
    
    /**
     * Constructor - creates an empty grid of the
     * specified dimensions.
     * 
     * @param width The width of the new grid
     * @param height The height of the new grid
     */
    public Grid (int width, int height)
    {
    	grid = new boolean[width][height];
    	this.height = height;
    	this.width = width;
    }

    /**
     * Returns true if the specified cell in the grid
     * is alive, false otherwise.  If the coordinates
     * are illegal (outside the grid), false is returned.
     * 
     * @param row  a row number
     * @param column a column number
     * @return true if that cell is alive
     */
    public boolean isAlive (int row, int column)
    {
    	if (column >= width || row >= height || column < 0 || row < 0)
    		return false;
    	else
    		return grid[column][row];
    	
    }
    
    /** 
     * Returns the width of the grid.
     * 
     * @return the width of this grid
     */
    public int getWidth ()
    {
    	return width;
    }

    /** 
     * Returns the height of the grid.
     * 
     * @return the height of this grid
     */
    public int getHeight ()
    {
    	return height;
    }
    
    /**
     * Sets the state of the specified cell in the
     * grid.
     * 
     * @param row a row number
     * @param column a column number
     * @param isAlive true if the grid cell should be alive
     */
    public void setCellState(int row, int column, boolean isAlive)
    {
    	if (isAlive == true)
    		grid[column][row] = true;
    	else
    		grid[column][row] = false;
    }
    
 
    /**
     * Clears the grid, all cells marked as
     * dead.
     */
    public void clear ()
    {
    	for (int col = 0; col < width; col++)
    		for (int row = 0; row < height; row++)
    			grid[col][row] = false;
    }
    
    /**
     * Performs one 'life' step using the standard rules
     * of life:
     * 
     * Any live cell with fewer than two neighbors dies, as if by loneliness.
     * Any live cell with more than three neighbors dies, as if by overcrowding.
     * Any live cell with two or three neighbors lives, unchanged, to the next generation.
     * Any dead cell with exactly three neighbors comes to life.
     * 
     * Care must be taken to make sure the next generation is kept separate from the 
     * current generation.
     */
    public void stepOneGeneration ()
    {
    	// Create a spare grid
    	boolean[][] spareGrid = new boolean[width][height];
    	
    	
    	// Loop through each grid location
    	for (int col = 0; col < width; col++)
    		for (int row = 0; row < height; row++) {
    			int liveNeighborCount = 0;
    			if (isAlive(row-1, col-1) == true)
    				liveNeighborCount++;
    			if (isAlive(row-1, col) == true)
    				liveNeighborCount++;
    			if (isAlive(row-1, col+1) == true)
    				liveNeighborCount++;
    			if (isAlive(row, col-1) == true)
    				liveNeighborCount++;
    			if (isAlive(row, col+1) == true)
    				liveNeighborCount++;
    			if (isAlive(row+1, col-1) == true)
    				liveNeighborCount++;
    			if (isAlive(row+1, col) == true)
    				liveNeighborCount++;
    			if (isAlive(row+1, col+1) == true)
    				liveNeighborCount++;	
    			System.out.println(liveNeighborCount);
    			
//    			if ((liveNeighborCount == 2 || liveNeighborCount == 3) && isAlive(row,col) == true)
//    				spareGrid[col][row] = true;
//    			
//    			if (liveNeighborCount ==3 && isAlive(row,col) == false)
//    				spareGrid[col][row] = true;
    			
    			spareGrid[col][row] = liveNeighborCount == 3 || (isAlive(col,row) && liveNeighborCount == 2);
    			
    			
    			
    		}
    	grid = spareGrid;
    	
    			
    	  	
    	    // Set the alive/dead state in the spare grid according to the rules
    	
    	// Store the spare grid in the grid field (replacing the old grid with the new one)
    }
}
