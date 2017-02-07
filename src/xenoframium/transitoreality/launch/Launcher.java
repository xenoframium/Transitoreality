package xenoframium.transitoreality.launch;

public class Launcher {
	private static Transitoreality gameInstance;
	
	public static void main(String[] args) {
		gameInstance = new Transitoreality();
		gameInstance.startGame();
	}
	
	public static Transitoreality getGameInstance() {
		return gameInstance;
	}
}
