package com.snakeGame;
import java.util.*;

public class SnakeGame {
	private char[][] board;
	private int currRow = 1;
	private int currCol = 0;
	private char currHead = 'v';
	private Queue<Node> snakeBody = new LinkedList<>();
	private int score = 0;
	
	SnakeGame(int row, int col) {
		board = new char[row][col];
		snakeBody.add(new Node(0, 0));
		snakeBody.add(new Node(1, 0));
		board[0][0] = '.';
		board[1][0] = currHead;
		generateFood();
	}
	
	public void start() {
		Scanner sc = new Scanner(System.in);
		printBoard();
		printScore();
		
		while (true) {
			System.out.println("Enter the move (U, D, R, L): ");
			char move = sc.next().toUpperCase().charAt(0);
			
			if (canMove(move)) {
				printBoard();
				printScore();
			}
			else {
				System.out.println("Game over!\nYour final score is: " + score);
				break;
			}
		}
		
		sc.close();
	}
	
	public boolean canMove(char move) {
		int newRow = currRow, newCol = currCol;
		char newHead = currHead;
		
		switch(move) {
			case 'U':
				newRow--;
				newHead = '^';
				break;
			case 'R':
				newCol++;
				newHead = '>';
				break;
			case 'L':
				newCol--;
				newHead = '<';
				break;
			case 'D':
				newRow++;
				newHead = 'v';
				break;
			default:
				System.out.println("Invalid move! Please try again");
				return true;
		}
		
		if (!validMove(newRow, newCol)) {
			return false;
		}
		
		else {
			if (board[newRow][newCol] == 'X') {
				generateFood();
				score += 10;
			}
			else {
				Node tail = snakeBody.poll();
				board[tail.row][tail.col] = '\0';
			}
		}
		
		snakeBody.add(new Node(newRow, newCol));
		
		board[currRow][currCol] = '.';
		currRow = newRow;
		currCol = newCol;
		currHead = newHead;
		board[newRow][newCol] = newHead;
		
		return true;
	}
	
	public boolean validMove(int row, int col) {
		return (row >= 0 && row < board.length && col >= 0 && col < board[0].length && board[row][col] != '.');
	}
	
	public void generateFood() {
		Random random = new Random();
		Node food;
		do {
			food = new Node(random.nextInt(board.length), random.nextInt(board[0].length));
		} while (board[food.row][food.col] != '\0');
		
		board[food.row][food.col] = 'X';
	}
	
	public void printBoard() {
		for (char[] row : board) {
			for (char col : row) {
				System.out.print((col == '\0' ? "\u25A0" : col) + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void printScore() {
		System.out.println("Score: " + score + "\n");
	}
}
