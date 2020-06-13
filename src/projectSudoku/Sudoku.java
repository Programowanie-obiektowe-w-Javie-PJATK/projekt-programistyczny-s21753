package projectSudoku;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Font;

public class Sudoku {

	private JFrame ramkasudoku;
	boolean isStarted = false;
	int prevBoard[][] = new int[9][9];

	private final Timer timer = new Timer(1000, (ActionListener) null);
	final JLabel timerLabel = new JLabel("Pozostały czas:");
	final JButton startButton = new JButton("Start");
	final JButton submitButton = new JButton("Sprawdź");
	final JButton hintButton = new JButton("Podpowiedź");
	final JTextField grid[][] = new JTextField[9][9];
	int timeCount = -1;


	//Odliczanie czasu
	public String countToTime(int count){
		String min = Integer.toString(count/60);
		String sec = Integer.toString(count%60);
		if(Integer.parseInt(min) == 0) min = "0"+min;
		if(Integer.parseInt(sec)/10 == 0) sec = "0"+sec;
		return min+":"+sec;
	}

	//Event handler po skonczeniu gry
	public void gameOver(){
		timerLabel.setVisible(false);
		timer.stop();
		prevBoard = SudokuSolver.solve(prevBoard);
		boolean isFine = true;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				if(grid[i][j].getText().equals("")){
					isFine = false;
					break;
				}
				else if(Integer.parseInt(grid[i][j].getText()) != prevBoard[i][j]){
					isFine = false;
					break;
				}
			}
		}
		if(isFine && isStarted) JOptionPane.showMessageDialog(null, "Gratulacje, wygrałeś!");
		else JOptionPane.showMessageDialog(null, "Przegrałeś.");
		isStarted = false;
		submitButton.setVisible(false);
		hintButton.setVisible(false);
		startButton.setText("Start");
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				grid[i][j].setText("");
				grid[i][j].setEditable(false);
			}
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sudoku window = new Sudoku();
					window.ramkasudoku.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Sudoku() {
		initialize();
	}


	private void initialize() {
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				timeCount--;
				timerLabel.setText("Pozostały czas-"+countToTime(timeCount));
				if(timeCount == 0) gameOver();
			}
		});


		ramkasudoku = new JFrame();
		ramkasudoku.setTitle("Sudoku s21753");
		ramkasudoku.setBounds(100, 100, 668, 438);
		ramkasudoku.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ramkasudoku.getContentPane().setLayout(null);

		/////////utworzenie kratki


		int h = 12, w = 13, hi = 39, wi = 37;

		for(int i = 0; i < 9 ; i++){
			if(i%3 == 0 && i!=0) w += 13;

			for(int j = 0; j < 9; j++){
				if(j%3 == 0 && j!=0) h += 11;

				grid[i][j] = new JTextField();
				grid[i][j].setColumns(10);
				grid[i][j].setBounds(h, w, 38, 37);
				ramkasudoku.getContentPane().add(grid[i][j]);
				h += hi;
			}
			h = 12;
			w += wi;
		}

		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				grid[i][j].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.BOLD, 22));
				grid[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				grid[i][j].setEditable(false);
			}
		}

		 
		hintButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
		hintButton.setBounds(435, 339, 155, 37);
		ramkasudoku.getContentPane().add(hintButton);
		
		submitButton.setVisible(false);
		hintButton.setVisible(false);
		timerLabel.setVisible(false);
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameOver();
			}
		});
		submitButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
		submitButton.setBounds(435, 270, 155, 37);
		ramkasudoku.getContentPane().add(submitButton);

		JLabel difficultyLabel = new JLabel("Poziom trudności:");
		difficultyLabel.setFont(new Font("Calibri Light", Font.BOLD, 16));
		difficultyLabel.setBounds(435, 70, 155, 24);
		ramkasudoku.getContentPane().add(difficultyLabel);

		timerLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
		timerLabel.setFont(new Font("Calibri Light", Font.BOLD, 16));
		timerLabel.setBounds(435, 13, 176, 16);
		ramkasudoku.getContentPane().add(timerLabel);

		final JRadioButton easyButton = new JRadioButton("Łatwy");
		easyButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
		easyButton.setBounds(435, 103, 127, 25);
		ramkasudoku.getContentPane().add(easyButton);

		final JRadioButton mediumButton = new JRadioButton("Średni");
		mediumButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
		mediumButton.setBounds(435, 133, 127, 25);
		ramkasudoku.getContentPane().add(mediumButton);

		final JRadioButton hardButton = new JRadioButton("Trudny");
		hardButton.setFont(new Font("Calibri Light", Font.BOLD, 13));
		hardButton.setBounds(435, 163, 127, 25);
		ramkasudoku.getContentPane().add(hardButton);

		ButtonGroup bg = new ButtonGroup();
		bg.add(easyButton);
		bg.add(mediumButton);
		bg.add(hardButton);
		bg.setSelected(mediumButton.getModel(), true);



		startButton.setBounds(435, 206, 155, 37);
		ramkasudoku.getContentPane().add(startButton);
		startButton.setFont(new Font("Calibri Light", Font.BOLD, 18));
		
		
		hintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			 		
				Random r = new Random();
				int i = r.nextInt(9);
				int j = r.nextInt(9);
				do {
 					i = r.nextInt(9);
					j = r.nextInt(9);
				}while(!grid[i][j].getText().equals(""));
				
				
					prevBoard = SudokuSolver.solve(prevBoard);
					 		grid[i][j].setEditable(false);
							grid[i][j].setText(Integer.toString(prevBoard[i][j]));
						 
			  }
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(isStarted){
					isStarted = false;
					prevBoard = SudokuSolver.solve(prevBoard);
					for(int i = 0; i < 9; i++){
						for(int j = 0; j < 9; j++){
							grid[i][j].setEditable(false);
							grid[i][j].setText(Integer.toString(prevBoard[i][j]));
						}
					}
					startButton.setText("Start");
					timer.stop();
					timerLabel.setVisible(false);
					submitButton.setVisible(false);
 					hintButton.setVisible(false);
				}
				else{
					int difficulty = 1;
					if(easyButton.isSelected()) difficulty = 0;
					else if (hardButton.isSelected()) difficulty = 2;
					else difficulty = 1;

					if(difficulty == 0) timeCount = 900;
					else if(difficulty == 1) timeCount = 600;
					else timeCount = 300;

					int board[][] = new int[9][9];
					do{
						board = SudokuGenerator.generate(difficulty);
					}while(board[0][0] == -1);
					for(int i = 0; i < 9; i++){
						for(int j = 0; j < 9; j++){
							prevBoard[i][j] = board[i][j];
						}
					}
					for(int i = 0; i < 9; i++){
						for(int j = 0; j < 9; j++){
							if(board[i][j] != 0){
								grid[i][j].setText(Integer.toString(board[i][j]));
							}
							else {
								grid[i][j].setText("");
								grid[i][j].setEditable(true);
							}
						}
					}
					submitButton.setVisible(true);
					hintButton.setVisible(true);
					startButton.setText("Poddaj się.");
					timerLabel.setVisible(true);
					timer.start();
					isStarted = true;
				}
			}
		});
	}
}
