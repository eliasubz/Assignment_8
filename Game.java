import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
	private List<Player> players;
	private List<Category> categories;
	private int numOfQuestions;
	private Player currentPlayer;
	private int placesOnBoard = 12;
	private int amountToWin = 6;

	public Game(List<Category> categories, int numOfQuestions, List<Player> players) {
		this.categories = categories;
		this.numOfQuestions = numOfQuestions;
		this.players = players;
		this.currentPlayer = players.get(0);
		setQuestions();
		if(isPlayable()){
			System.out.println("Have a nice game!");
		}else{
			throw new RuntimeException("Sorry game not playable;3333");
		}
	}

	public void setQuestions(){
		for(int i=0; i<numOfQuestions;i++) {
			for(Category category : categories){
				category.addQuestion(new Question(Integer.toString(i)));
			}
		}
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= 2 && placesOnBoard % categories.size() == 0);
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

	public void switchPlayers(){
		currentPlayer = players.get((players.indexOf(currentPlayer)+1)%players.size());
	}

	public boolean wasCorrectlyAnswered() {
		if (currentPlayer.isInPenaltyBox() && !currentPlayer.isGettingOutOfPenaltyBox()) {
			switchPlayers();
			return true;
		} else {
			System.out.println("Answer was correct!!!!");
			currentPlayer.addCoin();
			System.out.println(currentPlayer.getName()
					+ " now has "
					+ currentPlayer.getPurse()
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			switchPlayers();
			return winner;
		}
	}

	public boolean wrongAnswer() {
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.getName() + " was sent to the penalty box");
		currentPlayer.setGettingOutOfPenaltyBox(true);

		switchPlayers();

		return true;
	}

	private boolean didPlayerWin() {
		return !(currentPlayer.getPurse() == amountToWin);
	}
}
