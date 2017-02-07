package xenoframium.transitoreality.launch;

import xenoframium.transitoreality.gamestate.LaunchState;
import xenoframium.transitoreality.gamestate.StateStack;

public class Transitoreality {
	private final StateStack stateStack = new StateStack();
	private boolean gameShouldClose = false;
	
	public void startGame() {
		stateStack.push(new LaunchState());
		
		while (!gameShouldClose) {
			stateStack.peek().loop();
		}
	}
	
	public void terminateGame() {
		gameShouldClose = true;
	}
}
