package projectSudoku;

public class SudokuSolver {

	public static boolean isBlockValid(int x, int y, int board[][]){
		return board[x][y] == 0;
	}
	
	//Sprawdzenie czy gra jest ukonczona
	public static boolean isComplete(int board[][]){
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] == 0) return false;
			}
		}
		return true;
	}
	
	//Sprawdzenie czy obecny ruch jest poprawny
	public static boolean isMoveValid(int x, int y, int move, int board[][]){
		
		//Sprawdza czy wprowadzony numer jest poprawny
		if(x < 0 || y < 0 || x > 9 || y > 9) return false;
		
		//Sprawdza czy kolumna ma cyfry 1-9
		for(int i = 0; i < 9; i++){
			if(board[i][y] == move) return false;
		}
		
		//Sprawdza czy wiersz ma cyfry 1-9
		for(int i = 0; i < 9; i++){
			if(board[x][i] == move) return false;
		}
		
		//Sprawdza 3x3 kwadrat czy ma cyfry 1-9
		for(int i = (x/3)*3; i < (x/3)*3 + 3; i++){
			for(int j = (y/3)*3; j < (y/3)*3 + 3; j++){
				if(board[i][j] == move) return false;
			}
		}
		
		//Jesli wszystko sie zgadza, zwraca true
		return true;
	}
	
	//Przejście do następnego pustego kwadratu
	public static Point getNext(int x, int y, int board[][]){

		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(board[i][j] == 0) return new Point(i, j);
			}
		}
		return null;
	}
	
	public static boolean solve(int x, int y, int move, int board[][]){

		Point next = getNext(x, y, board);

		if(next != null){
			//Wprowadzanie cyfr rozwiazania
			for(int i = 1; i < 10; i++){
				if(isMoveValid(next.x, next.y, i, board)){
					board[next.x][next.y] = i;
					if(solve(next.x, next.y, i, board)){
						return true;
					}
					board[next.x][next.y] = 0;
				}
			}
		} else return true;
		return false;
	}
	
	public static int[][] solve(int[][] board){
		int garbageBoard[][] = new int[9][9];
		garbageBoard[0][0] = -1;
		int i = 0 , j = 0;
		for(int k = 1; k < 10; k++){
			if(solve(i, j, k, board))
				return board;
		}
		return garbageBoard;
	}
}