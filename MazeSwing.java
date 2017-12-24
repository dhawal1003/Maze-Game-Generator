package maze;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeSwing {

	public static int rows, columns, count = 0;
	public static int mrows, mcols;
	public static JButton[][] maze;
	public static JPanel p;

	public MazeSwing(int rows, int columns) {
		mrows = 2 * rows + 1;
		mcols = 2 * columns + 1;
		maze = new JButton[mrows][mcols];
		initialMaze();
	}

	private void initialMaze() {
		p = new JPanel();
		GridLayout gl = new GridLayout(mrows, mcols);
		p.setLayout(gl);

		for (int i = 0; i < mrows; i++)
			for (int j = 0; j < mcols; j++) {
				maze[i][j] = new JButton();
				if (j % 2 == 0) {

					maze[i][j].setBackground(Color.BLACK);
					maze[i][j].setBorderPainted(false);
				}

				else if (j % 2 == 1 && i % 2 == 0) {
					maze[i][j].setBackground(Color.BLACK);
					maze[i][j].setBorderPainted(false);

				} else {
					maze[i][j].setBackground(Color.WHITE);
					maze[i][j].setBorderPainted(false);
				}

				p.add(maze[i][j]);
			}
		// displayMaze();
	}

	public static int randInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String generateRandomChars(String candidateChars, int length) {

		StringBuilder sb = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
		}

		return sb.toString();
	}

	private void createMaze() {
		int cell1, cell2 = 0;
		int X = 0, Y = 0, XWall = 0, YWall = 0;
		String candidateChars = "LRUD";
		candidateChars.charAt(new Random().nextInt(candidateChars.length()));

		DisjSets ds = new DisjSets(rows * columns);

		while (count != (rows * columns - 1)) {

			cell1 = randInt(0, rows * columns - 1);
			X = 2 * ((cell1 / columns) + 1) - 1;
			Y = 2 * ((cell1 % columns) + 1) - 1;

			String wall = generateRandomChars(candidateChars, 1);

			if (wall.equals("L")) {

				XWall = X;
				YWall = Y - 1;
				cell2 = cell1 - 1;

			} else if (wall.equals("R")) {

				XWall = X;
				YWall = Y + 1;
				cell2 = cell1 + 1;

			} else if (wall.equals("U")) {
				XWall = X - 1;
				YWall = Y;
				cell2 = cell1 - columns;

			} else if (wall.equals("D")) {
				XWall = X + 1;
				YWall = Y;
				cell2 = cell1 + columns;

			}

			if (XWall > 0 && YWall > 0 && XWall < 2 * rows && YWall < 2 * columns) {

				if (ds.find(cell1) != ds.find(cell2)) {

					maze[XWall][YWall].setBackground(Color.WHITE);
					ds.union(ds.find(cell1), ds.find(cell2));
					count++;
				}
			}

		}
		maze[1][0].setBackground(Color.WHITE);
		maze[1][0].setBorderPainted(false);
		maze[mrows - 2][mcols - 1].setBackground(Color.WHITE);
		maze[mrows - 2][mcols - 1].setBorderPainted(false);
	}

	private void displayMaze() {

		for (int i = 0; i < mrows; i++) {
			for (int j = 0; j < mcols; j++) {
				System.out.print(maze[i][j] + " ");
			}
			System.out.println();
		}
	}

	public JPanel getPanel() {
		return p;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the rows and columns of the maze: ");
		rows = sc.nextInt();
		columns = sc.nextInt();
		sc.close();

		System.out.println("The " + rows + " * " + columns + " maze is shown as a Java Applet: ");
		MazeSwing m = new MazeSwing(rows, columns);
		m.createMaze();
		// m.displayMaze();
		JFrame f = new JFrame("MAZE");
		f.add(m.getPanel());
		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
