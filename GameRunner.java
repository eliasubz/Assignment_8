
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameRunner {

	private static boolean notAWinner;

	public static void main(String[] args) {

        int numOfQuestions = 50;
		int placesOnBoard = 12;
		int amountToWin = 6;
        List<Category> categories = new ArrayList<>();

        Category pop = new Category("Pop");
        Category science = new Category("Science");
        Category sports = new Category("Sports");
        Category rock = new Category("Rock");

        categories.add(pop);
        categories.add(science);
        categories.add(sports);
        categories.add(rock);

		Player ula = new Player("Ula", 0,0,false);
		Player leal = new Player("Leal", 0,0,false);
		Player bobby = new Player("Bobby", 0,0,false);

		Game aGame = new Game(categories, numOfQuestions, placesOnBoard, amountToWin);

		aGame.addPlayer(ula);
		aGame.addPlayer(leal);
		aGame.addPlayer(bobby);

		Random rand = new Random();

		do {

			aGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}

		} while (notAWinner);

	}
}
