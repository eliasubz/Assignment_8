import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private ArrayList<Player> players = new ArrayList<>();
	private List<Category> categories;
	private int numOfQuestions;
	private Player currentPlayer;
	private int placesOnBoard;
	private int amountToWin;

	public Game(List<Category> categories, int numOfQuestions, int placesOnBoard, int amountToWin) {
		this.categories = categories;
		this.numOfQuestions = numOfQuestions;
		this.placesOnBoard = placesOnBoard;
		this.amountToWin = amountToWin;
		this.currentPlayer = players.get(0);
		setQuestions();
	}

	public void setQuestions(){
		for(int i=0; i<numOfQuestions;i++) {
			for(Category category : categories){
				category.addQuestion(new Question(Integer.toString(i)));
			}
		}
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2);
	}

	public void addPlayer(Player player) {

		players.add(player);
		System.out.println(player.getName() + " was added");
		System.out.println("They are player number " + players.size());

	}


	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(currentPlayer.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {

				currentPlayer.setGettingOutOfPenaltyBox(true);
				System.out.println(currentPlayer.getName() + " is getting out of the penalty box");

				movePlayer(currentPlayer, roll);
				notify(currentPlayer);
				askQuestion(currentCategory());
			} else {
				System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
				currentPlayer.setGettingOutOfPenaltyBox(false);
			}

		} else {

			movePlayer(currentPlayer, roll);
			notify(currentPlayer);
			askQuestion(currentCategory());
		}

	}

	public void movePlayer(Player player, int roll){
		int place = player.getPlace();
		if(place+roll<placesOnBoard-1){
			player.setPlace(place+roll);
		}else{
			player.setPlace(place+roll-placesOnBoard);
		}
	}

	public void notify(Player player){
		System.out.println(player.getName()
				+ "'s new location is "
				+ player.getPurse());
		System.out.println("The category is " + currentCategory());
	}


	private void askQuestion(Category category) {
		System.out.println(category.removeQuestion());
	}

	private Category currentCategory() {

		int c = currentPlayer.getPlace()%categories.size();
		return categories.get(c);

	}

	public void switchPLayers(){
		currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				switchPLayers();

				return winner;
			} else {
				switchPLayers();
				return true;
			}

		} else {

			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size())
				currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size())
			currentPlayer = 0;
		return true;
	}

	private boolean didPlayerWin() {
		return !(currentPlayer.getPurse() == amountToWin);
	}
}
