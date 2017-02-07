package xenoframium.transitoreality.gamestate;

import java.util.ArrayList;

public class StateStack {
	private ArrayList<GameState> stateStack = new ArrayList<>();
	
	public GameState pop() {
		GameState state = peek();
		state.terminate();
		stateStack.remove(stateStack.size() - 1);
		return state;
	}
	
	public GameState peek() {
		return stateStack.get(stateStack.size() - 1);
	}
	
	public void push(GameState state) {
		state.init();
		stateStack.add(state);
	}
}
