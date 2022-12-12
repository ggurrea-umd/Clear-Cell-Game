package model;

import java.util.Random;

/* This class must extend Game */
public class ClearCellGame extends Game{
	private Random random;
	private int strategy;
	private int score;
	

	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows,maxCols);
		
		this.strategy = strategy;
		this.random = random;
		score = 0;
	}
	
	public boolean isGameOver() {
		return checksEmptyRow(getMaxRows() - 1);
	}

	public int getScore() {
		return score;
	}

	public void nextAnimationStep() {
		BoardCell[][] copy = new BoardCell[getMaxRows() - 1][getMaxCols()];
		
		// Creates a copy of the board, minus the last row
		for(int row = 0; row < copy.length; row++) {
			for (int col = 0; col < copy[0].length; col++) {
				copy[row][col] = getBoardCell(row, col);
			}
		}
		
		// If the game is not over
		if (!isGameOver()) {
			// Places a random row as the first row
			for (int col = 0; col < getMaxCols(); col++) {
				setBoardCell(0, col, BoardCell.getNonEmptyRandomBoardCell(random));
			}
			
			// Moves all other rows down by one
			for(int row = 0; row < copy.length; row++) {
				for (int col = 0; col < copy[0].length; col++) {
					setBoardCell(row + 1, col, copy[row][col]);
				}
			}
		}
	}

	public void processCell(int rowIndex, int colIndex) {
		BoardCell color = getBoardCell(rowIndex, colIndex);
		
		if (color != BoardCell.EMPTY) {
			
			setBoardCell(rowIndex, colIndex, BoardCell.EMPTY);
			
			score++;
		
			// Process right
			if (colIndex != getMaxCols() - 1) {
				for (int col = colIndex + 1; col < getMaxCols(); col++) {
					if (getBoardCell(rowIndex, col) != color) {
						break;
					}
					
					setBoardCell(rowIndex, col, BoardCell.EMPTY);
					
					score++;
				}
			}
			
			// Process left
			if (colIndex != 0) {
				for (int col = colIndex - 1; col >= 0; col--) {
					if (getBoardCell(rowIndex, col) != color) {
						break;
					}
					
					setBoardCell(rowIndex, col, BoardCell.EMPTY);
					
					score++;
				}
			}
			
			// Process down
			if (rowIndex != getMaxRows() - 1) {
				for (int row = rowIndex + 1; row < getMaxRows(); row++) {
					if (getBoardCell(row, colIndex) != color) {
						break;
					}
					
					setBoardCell(row, colIndex, BoardCell.EMPTY);
					
					score++;
				}
			}
			
			// Process up
			if (rowIndex != 0) {
				for (int row = rowIndex - 1; row >= 0; row--) {
					if (getBoardCell(row, colIndex) != color) {
						break;
					}
					
					setBoardCell(row, colIndex, BoardCell.EMPTY);
					
					score++;
				}
			}
			
			// Process up and left
			if (rowIndex != 0 && colIndex != 0) {
				int row = rowIndex - 1;
				int col = colIndex - 1;
				
				while(row >= 0 && col >= 0) {
					if (getBoardCell(row, col) != color) {
						break;
					}
					
					setBoardCell(row, col, BoardCell.EMPTY);
					
					score++;
					row--;
					col--;
				}
			}
			
			// Process up and right
			if (rowIndex != 0 && colIndex != getMaxCols() - 1) {
				int row = rowIndex - 1;
				int col = colIndex + 1;
				
				while(row >= 0 && col <= getMaxCols() - 1) {
					if (getBoardCell(row, col) != color) {
						break;
					}
					
					setBoardCell(row, col, BoardCell.EMPTY);
					
					score++;
					row--;
					col++;
				}
			}
			
			// Process down and left
			if (rowIndex != getMaxRows() - 1 && colIndex != 0) {
				int row = rowIndex + 1;
				int col = colIndex - 1;
										
				while(row <= getMaxRows() - 1 && col >= 0) {
					if (getBoardCell(row, col) != color) {
						break;
					}
											
					setBoardCell(row, col, BoardCell.EMPTY);
											
					score++;
					row++;
					col--;
				}
			}
			
			// Process down and right
			if (rowIndex != getMaxRows() - 1 && colIndex != getMaxCols() - 1) {
				int row = rowIndex + 1;
				int col = colIndex + 1;
							
				while (row <= getMaxRows() - 1 && col <= getMaxCols() - 1) {
					if (getBoardCell(row, col) != color) {
						break;
					}
								
					setBoardCell(row, col, BoardCell.EMPTY);
								
					score++;
					row++;
					col++;
				}
			}
			
			for(int row = 0; row < getMaxRows() - 1; row++) {
				if (!checksEmptyRow(row)) {
					BoardCell[][] copy = new BoardCell[getMaxRows() - (row + 1)][getMaxCols()];
					
					// Creates a copy of the board after the empty row
					for(int i = 0; i < copy.length; i++) {
						for (int col = 0; col < copy[0].length; col++) {
							copy[i][col] = getBoardCell(i + row + 1, col);
						}
					}
					
					// Moves all other rows up by one
					for(int i = 0; i < copy.length; i++) {
						for (int col = 0; col < copy[0].length; col++) {
							setBoardCell(i + row, col, copy[i][col]);
						}
					}
					
					setRowWithColor(getMaxRows() - 1, BoardCell.EMPTY);
				}
			}
		}				
	}
	
	private boolean checksEmptyRow(int row) {
		int counter = 0;
		
		// Counts the amount of empty cells in the last row
		for (int col = 0; col < getMaxCols(); col++) {
			if (board[row][col] == BoardCell.EMPTY) {
				counter++;
			}
		}
				
		// If the amount of empty cells in the row is equal to the amount of empty cells, false is returned
		if (counter == getMaxCols()) {
			return false;
		}
				
		// Else true is returned
		return true;
	}
}