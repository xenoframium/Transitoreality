package xenoframium.transitoreality.graphics;

import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public interface Selectable {
	public void onSelection(Window window, Camera camera, Projection projection, Renderable renderable);
}
