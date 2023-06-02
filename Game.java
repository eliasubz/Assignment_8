import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private List<Player> players;
	private List<Category> categories;
	private int numOfQuestions;
	private Player currentPlayer;
	private int placesOnBoard;
	private int amountToWin;

	public Game(List<Category> categories, int numOfQuestions, List<Player> players, int placesOnBoard, int amountToWin) {
		this.categories = categories;
		this.numOfQuestions = numOfQuestions;
		this.players = players;
		this.placesOnBoard = placesOnBoard;
		this.amountToWin = amountToWin;
		this.currentPlayer = players.get(0);
		setQuestions();
		System.out.println(isPlayable());
	}

	public void setQuestions(){
		for(int i=0; i<numOfQuestions;i++) {
			for(Category category : categories){
				category.addQuestion(new Question(Integer.toString(i)));
			}
		}
	}

	public String isPlayable() {
		boolean ok = (howManyPlayers() >= 2 && placesOnBoard%categories.size() == 0);
		if(ok){
			return "Have a nice game!";
		}else{
			return "Sorry game not playable!:333";
		}
	}


	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(currentPlayer.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			if (roll % 2 != 0) {
				System.out.println(currentPlayer.gettingOutOfPenaltyBox(true));
				movePlayer(currentPlayer, roll);
				notify(currentPlayer);
				askQuestion(currentCategory());
			} else {
				System.out.println(currentPlayer.gettingOutOfPenaltyBox(false));
			}
		} else {
			movePlayer(currentPlayer, roll);
			notify(currentPlayer);
			askQuestion(currentCategory());
		}
	}

	public void movePlayer(Player player, int roll){
		int place = player.getPlace() + roll;
		if(place<placesOnBoard){
			player.setPlace(place);
		}else{
			player.setPlace(place-placesOnBoard);
		}
	}

	public void notify(Player player){
		System.out.println(player.getName()
				+ "'s new location is "
				+ player.getPlace());
		System.out.println("The category is " + currentCategory().getTopic());
	}


	private void askQuestion(Category category) {
		System.out.println("Question number: " + category.removeQuestion());
	}

	private Category currentCategory() {
		int c = currentPlayer.getPlace()%categories.size();
		return categories.get(c);
	}

	public void switchPLayers(){
		currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox() && !currentPlayer.isGettingOutOfPenaltyBox()) {
			switchPLayers();
			return true;
		} else {
			System.out.println("Answer was correct!!!!");
			currentPlayer.addCoin();
			System.out.println(currentPlayer.getName()
					+ " now has "
					+ currentPlayer.getPurse()
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			switchPLayers();
			return winner;
		}
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.getName() + " was sent to the penalty box");
		currentPlayer.setGettingOutOfPenaltyBox(true);

		switchPLayers();

		return true;
	}

	private boolean didPlayerWin() {
		return !(currentPlayer.getPurse() == amountToWin);
	}
}
