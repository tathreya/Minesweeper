import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * Tanay Athreya, Period 4, 4/5/20
 * 
 * Overall, this lab was fun and I worked on it for 5-6 hours. This lab was quite complicated and had a lot of moving parts. I decided on using 2 parallel 2d arrays, one which
 * stored if a cell was revealed or not and if it had a flag and another that stored its value or if it had a mine. The recursive method to reveal cells was difficult but I eventually got it after
 * seeing the pseudocode on the slides. My error in this lab is that I create the board before the user's first move. This causes 2 problems: one is that if the user clicks on a mine as his/her first move, the user will
 * lose the game and the second is that if the user clicks on a cell which has a certain number of neighboring mines (for example 1) instead of actually revealing the rest using recursion, it only reveals that singular cell which is not how it works on the
 * online minesweeper games. I have an idea to fix it. I think that if I just let the user reveal something and then create the board making sure that the users move location is not affected, then it will work. This is what I will fix for the next iteration of this lab.
 * I also want to add more error checking in the main game loop so if the user enters an invalid row/col it will reask the user instead of crashing.
 *
 * Tanay Athreya, Period 4, 4/12/20
 * 
 * For the resubmission, I looked on Slack to check on the requirements of the game. On slack, someone asked if a singular number cell is revealed, should it recursively reveal the surroundings? The answer is no and that means that what I thought was broken in the first iteration of this lab
 * was actually working fine. I tested the rest of my game and found one error which was in the hasPlayerWon() method. Sometimes, the program would terminate (meaning the player won) premature because the number of unopened cells = num of mines. This is because my if statement for counting the
 * number of unopened cells did not consider flags (coverGrid[][] == 2). If a cell is flagged it is technically not unrevealed (coverGrid[][] == 0), however, by the logic of the game, a flagged cell cannot be revealed. I fixed this by adding an or statement that checked if coverGrid[][] == 2. I tried to make it so that 
 * the user cannot lose on the first move but I had trouble doing so. I knew I had to make the board based off the users first click and place the mines but I was not able to accomplish this. However, in the slack it said that it is your choice to make the player lose on the first move. Everything worked after this fix.
 * For the next iteration of this lab, I would like to use JAVA fx to make this game more interactive and fun. 
 * 
 * 
 * 
 */

public class P4_Athreya_Tanay_MinesweeperModel implements P4_Athreya_Tanay_MSModelInterface {
	
	private Integer[][] coverGrid;
	private Integer[][] fullGrid;
	
	public P4_Athreya_Tanay_MinesweeperModel(int numRows, int numCols, int numMines) {
		
		
		coverGrid = new Integer[numRows][numCols];
		fullGrid = new Integer[numRows][numCols];
		
		
		//initialize fullGrid, (0 means clear cell, 1,2...n means number of neighboring mines, -1 == mine)
		//initialize coverGrid (0 means unrevealed and 1 means revealed, 2 means flag)
		
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				fullGrid[i][j] = 0;
				coverGrid[i][j] = 0;
			}
		}
		
		//place mines randomly across board
		
		Random rand = new Random();
		
		for ( int i = 0; i < numMines; i++ ) {
				
			 //get a random cell 
			 boolean placeMine = false;
			 
			 while (!placeMine) {
				 int rowNumber = rand.nextInt((numRows-1) +1);
				 int colNumber = rand.nextInt((numCols-1) + 1);
			 
			 
				 if (!isMine(rowNumber,colNumber)) {
					 setMine(rowNumber,colNumber);
					 placeMine = true;
				 }
			 }
			 
		}
		
		
		//calculate number of adjacent mines for each cell in fullGrid
		
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				
				if (!isMine(i,j)) {
					int numAdjMines = getAdjacentMines(i,j);
					fullGrid[i][j] = numAdjMines;
				}
				
				
			}
		}
		
		
		
	}

	@Override
	public boolean isMine(int row, int col) {
		// TODO Auto-generated method stub
		
		if (fullGrid[row][col] == -1) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void setMine(int row, int col) {
		
		fullGrid[row][col] = -1;
		
		
	}

	@Override
	public boolean hasFlag(int row, int col) {
		// TODO Auto-generated method stub
		
		if (coverGrid[row][col] == 2) {
			return true;
		}
		else {
			return false;
		}
		
	}

	@Override
	public boolean isNumber(int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void toggleFlag(int row, int col) {
	
		// if the cell at row,col is already revealed then tell user that they cannot place flag on revealed cell and quit method
		
		if (coverGrid[row][col] == 1) {
			System.out.println("You cannot place a flag on a revealed cell!");
			return;
		}
		else {
			
			// if cell at row,col already has flag then set the state back to 0 which is unrevealed
			if (coverGrid[row][col] == 2) {
				
				coverGrid[row][col] = 0;
				
			}
			// otherwise there is no flag on row,col then we must put a flag on it
			else {
				
				coverGrid[row][col] = 2;
				
			}
			
		}
		
		
	}

	@Override
	public boolean hasPlayerWon() {
		
		if (numUnopenedCells() == totalNumMines()) {
			return true;
		}
		else {
			return false;
		}
		
		
	}

	@Override
	public int numMinesLeft() {
		// TODO Auto-generated method stub
		
		// number of total mines - number of flags
		return totalNumMines() - totalNumFlags();
		
	}

	@Override
	public int getAdjacentMines(int row, int col) {
		
		int numAdjMines = 0;
		
		for (int i = row - 1 ; i <= (row + 1) ; i++  ) {

	        for (int j = col - 1 ; j <= (col + 1) ; j ++  ) {
	        	
	        	// this makes sure we don't count the passed in location of (row,col)
	        	// as a neighbor of itself
	        	if (!(i == row && j == col)) {
	        		
	        		if (isValidLocation(i,j) && fullGrid[i][j] == -1) {
	        			numAdjMines++;
	        		}
	        		
	        	}
	        	
	        }
	        
		}
		
		return numAdjMines;
		
	}

	@Override
	public ArrayList<Integer> allSurrounding(int row, int col) {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> allSurrounding = new ArrayList<Integer>();
		
		for (int i = row - 1; i <= (row + 1); i++) {
			
			for (int j = col - 1 ; j <= (col + 1); j++) {
				
				if (!(i == row && j == col)) {
					
					// only adds locations that are valid and whose cells do not contain mines
					if (isValidLocation(i,j) && fullGrid[i][j] != -1) {
						
						allSurrounding.add(i);
						allSurrounding.add(j);
						
					}
				}
			}
		}
		
		return allSurrounding;
	}
	
	@Override
	public void recursiveReveal(int row, int col) {
		
		// if not revealed then reveal
		if (coverGrid[row][col] == 0) {
			
			coverGrid[row][col] = 1;
			
			
		}
		
		// if it is a mine then game over
		if (isMine(row,col)) {
			System.out.println("Game Over!");
			System.exit(0);
			return;
		}
		
		// if it is blank then
		if (fullGrid[row][col] == 0) {
			
			ArrayList<Integer> allSurrounding = allSurrounding(row,col);
			
			for (int i = 0; i < allSurrounding.size(); i+=2) {
				
				if (coverGrid[allSurrounding.get(i)][allSurrounding.get(i+1)] == 0) {
					recursiveReveal(allSurrounding.get(i),allSurrounding.get(i+1));
				}
			}
			
		}
		
	}

	@Override	
	public void revealAll() {
		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				
				coverGrid[i][j] = 1;
				
			}
		}
		
	}

	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return fullGrid.length;
		
	}

	@Override
	public int getColumns() {
		// TODO Auto-generated method stub
		return fullGrid[0].length;
	}



	@Override
	public boolean isValidLocation(int row, int col) {
		
			if((col < 0) || (row <0) ) {
		        return false;   
		    }
			
		    if((col >= getColumns()) || (row >= getRows())) {
		        return false;  
		    }
		    
		    return true;
		    
	}
	
	@Override
	public int numUnopenedCells() {
		
		
		// TODO Auto-generated method stub
		
		int totalUnopenedCells = 0;
		
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getColumns(); j++) {
				
				// if tile is unreavealed or flagged
				if (coverGrid[i][j] == 0 || coverGrid[i][j] == 2) {
					
					totalUnopenedCells++;
					
				}
			}
		}
		
		return totalUnopenedCells;
	}
	
	@Override
	public int totalNumFlags() {
		// TODO Auto-generated method stub
		
		int total = 0;
		
		for (int i = 0; i < getRows(); i ++) {
			for (int j = 0; j < getColumns(); j++) {
				if (coverGrid[i][j] == 2) {
					total++;
				}
			}
		}
		
		return total;
		
	}



	@Override
	public int totalNumMines() {
		// TODO Auto-generated method stub
		
		int total = 0;
		
		for (int i = 0; i < getRows(); i ++) {
			for (int j = 0; j < getColumns(); j++) {
				if (fullGrid[i][j] == -1) {
					total++;
				}
			}
		}
		
		return total;
	}



	@Override
	public void printFullBoard() {
		
		// printing column headers
		System.out.print("  ");
		for (int i = 0; i < getColumns(); i++) {
			System.out.print(i + " ");
		}
				
		System.out.println();
		
		for (int i = 0 ; i < getRows(); i++) {
			
			System.out.print(i + " ");
			
			for (int j = 0; j < getColumns() ; j++) {
				
				// if its a mine
				if (fullGrid[i][j] == -1) {
					System.out.print("M ");
				}
				// if its a clear cell
				else if (fullGrid[i][j] == 0) {
					System.out.print("  ");
				}
				// if its any other cell (numbered cell)
				else {
					System.out.print(fullGrid[i][j] + " ");
				}
			}
			
			System.out.println();
			
		}
		
		
	}

	@Override
	public void printUserBoard() {
		
		
		// printing column headers
		System.out.print("  ");
		for (int i = 0; i < getColumns(); i++) {
			System.out.print(i + " ");
		}
		
		System.out.println();
		
		for (int i = 0; i < getRows(); i++) {
		
			System.out.print(i+ " ");
			
			for (int j = 0; j < getColumns(); j++) {
				
				// if its unrevealed print -
				if (coverGrid[i][j] == 0) {
					System.out.print("- ");
				}
				// if its revealed then print fullGrids value
				else if (coverGrid[i][j] == 1) {
					
					// if its a mine
					if (fullGrid[i][j] == -1) {
						System.out.print("M ");
					}
					// if its a clear cell
					else if (fullGrid[i][j] == 0) {
						System.out.print("  ");
					}
					// if its any other cell (numbered cell)
					else {
						System.out.print(fullGrid[i][j] + " ");
					}
					
				}
				// otherwise its a flag so print an f
				else {
					
					System.out.print("F ");
					
				}
				
			}
			
			System.out.println();
			
		}
		
		
	}
	
}
