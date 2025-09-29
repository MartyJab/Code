public class fleeing_a_maze {

	static char[][] lab1 = { //
			{ 'X', 'X', 'X', 'X', ' ', 'X', 'X', 'X', 'X', 'X' }, //
			{ 'X', ' ', ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', 'X', ' ', ' ', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', ' ', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', 'X', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } //
	};

	static char[][] lab2 = { //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, //
			{ 'X', ' ', ' ', 'X', ' ', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', ' ', 'X', ' ', ' ', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', ' ' }, //
			{ 'X', ' ', 'X', ' ', 'X', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } //
	};

	static char[][] lab3 = { //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' }, //
			{ 'X', ' ', ' ', 'X', ' ', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', ' ', 'X', ' ', ' ', ' ', 'X' }, //
			{ 'X', ' ', 'X', 'X', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', 'X', ' ', ' ', 'X', ' ', 'X' }, //
			{ 'X', ' ', 'X', ' ', 'X', 'X', 'X', 'X', ' ', 'X' }, //
			{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' }, //
			{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } //
	};

	public static void draw(char[][] lab) {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				System.out.print(lab[i][j]);
			}
			System.out.println();
		}
		for (int i = 0; i < 19; i++) { //Zeilenumbrüche für meinen Editor
			System.out.println();
		}
	}

	private static boolean labyrinth(int x, int y, int z, char[][] lab) throws InterruptedException {

		/*
		 * z gibt an, aus welchem Nachbarfeld der Aufruf gemacht wurde, damit
		 * dieser Weg nicht ausgewählt werden kann
		 * 1 = von unten, 2 = von links, 3 = von oben, 4 = von rechts, 0 = Start
		 */

		if (lab[x][y] == ' ') {

			lab[x][y] = '*';
			draw(lab);

			if (x == 9 || x == 0 || y == 9 || y == 0) {
				System.out.println("Ausweg wurde gefunden.");
				while (true)
					;
			}

			Thread.sleep(250);

		} else
			return false;

		if (z != 3) {
			labyrinth(x - 1, y, 1, lab); // nach oben gehen
		}
		if (z != 4) {
			labyrinth(x, y + 1, 2, lab); // nach rechts gehen
		}
		if (z != 1) {
			labyrinth(x + 1, y, 3, lab); // nach unten gehen
		}
		if (z != 2) {
			labyrinth(x, y - 1, 4, lab); // nach links gehen
		}

		lab[x][y] = ' ';
		draw(lab);

		return false;
	}

	public static void main(String[] args) throws InterruptedException {

		/*
		 * lab1 ist ein Labyrinth
		 * lab2 ist ein anderes Labyrinth
		 * lab3 ist ein Labyrinth ohne Ausgang
		 */
    
		if (labyrinth(5, 5, 0, lab1) == false) {
			System.out.println("Es gibt keinen Ausweg.");
		}
		;

	}

}
