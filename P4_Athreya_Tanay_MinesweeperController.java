import java.util.Scanner;

public class P4_Athreya_Tanay_MinesweeperController {
	
	
	P4_Athreya_Tanay_MinesweeperModel model;
	
	public P4_Athreya_Tanay_MinesweeperController(int numRows, int numCols, int numMines) {
		
		model = new P4_Athreya_Tanay_MinesweeperModel(numRows, numCols, numMines);
		
		
	}
	
	public void play() {
		
		System.out.println("Welcome to Minesweeper!");
		printUserBoard();
		System.out.println();
		printFullBoard();
		System.out.println("There are " + model.numMinesLeft() + " mines left!");
		
		Scanner keyboard;
		
			while (!(model.hasPlayerWon())) {
				
				System.out.print("Type r to reveal or f to flag: ");
				
				boolean notValidInput = true;
				
				while (notValidInput) {

					keyboard = new Scanner(System.in);
					String input = keyboard.nextLine();
				
					if (input.equals("f")) {
						
						System.out.print("Enter a row number");
						int row = keyboard.nextInt();
						System.out.println();
						
						System.out.print("Enter a column number");
						int col = keyboard.nextInt();
						
						model.toggleFlag(row, col);
						notValidInput = false;
					}
					else if (input.equals("r")) {
						
						System.out.print("Enter a row number");
						int row = keyboard.nextInt();
						System.out.println();
						
						System.out.print("Enter a column number");
						int col = keyboard.nextInt();
						
						model.recursiveReveal(row, col);
						
						notValidInput = false;
					}
					else {
						
						System.out.println("Invalid input, please try again!");
						notValidInput = true;
						
					}
					
				}
				
				printUserBoard();
				System.out.println();
				printFullBoard();
				
				System.out.println("There are " + model.numMinesLeft() + " mines left!");
				
				if (model.hasPlayerWon()) {
					System.out.println("You win!");
					System.exit(0);
				}
				else {
					continue;
				}
				
				
			}
		
		}
		
	public void printUserBoard() {
		
		model.printUserBoard();
	}
	
	public void printFullBoard() {
		
		model.printFullBoard();
		
	}
	
}
