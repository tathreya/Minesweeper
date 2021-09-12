import java.util.ArrayList;

/**
 * 
 * Tanay Athreya, 4/1/20, Period 4
 * 
 * Overall, this lab was fun and took me 30 minutes to complete. This lab was simple in terms of coding but complicated in terms of 
 * logic. I have never played minesweeper so I spent 10 minutes playing it to familiarize myself with the game. I then brain-stormed a list of questions 
 * that the controller would ask the model. After brainstorming, I started making my own methods such as isGameOver(), getAdjacentMines(), etc. I also put my 
 * toggleFlag method on school loop and saw other implementations that I used in my own interface. For the next iteration of this lab, I would like to start actually coding minesweeper
 * based off the interface I created in this assignment. 
 *
 */
public interface P4_Athreya_Tanay_MSModelInterface {

	
	// getters for dimensions of boards
	int getRows();
	int getColumns();
	
	// checks if the cell at row,col is a mine
	boolean isMine(int row, int col);
	
	void setMine(int row, int col);
	
	// checks if the cell at row,col is a flag
	boolean hasFlag(int row, int col);
	
	int totalNumFlags();
	
	int totalNumMines();
	// checks if the cell at row,col is a number
	boolean isNumber(int row, int col);
	
	// toggles/untoggles flag on cell (row,col) depending on whether that cell already has or does not have a flag
	void toggleFlag(int row, int col);
	
	// checks if game is over, this should be used in the main while loop of the game logic
	boolean hasPlayerWon();
	
	// returns if row,col is a valid location on the grid
	boolean isValidLocation(int row, int col);

	// returns the number of mines left
	int numMinesLeft();
	
	int numUnopenedCells();
	
	// returns the number of adjacent mines to a specific tile
	int getAdjacentMines(int row, int col);
	
	// returns all the surrounding cells of the cell at row,col
	ArrayList<Integer> allSurrounding(int row, int col);
		
	// recursively reveals cells with the starting point at row,col
	void recursiveReveal(int row, int col);
	
	// reveals all the cells, this can be used when the game is over and you need to reveal the entire board
	void revealAll();
	
	void printUserBoard();
	
	void printFullBoard();

}
