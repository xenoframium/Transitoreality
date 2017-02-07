package xenoframium.transitoreality.gl;

import java.util.HashMap;
import java.util.Map;

public class Windows {
	private static final Map<Long, Window> windowMap = new HashMap<>();
	
	public static Window getWindow(Long id) {
		return windowMap.get(id);
	}
	
	protected static void addWindow(Window window) {
		windowMap.put(window.getId(), window);
	}
}
