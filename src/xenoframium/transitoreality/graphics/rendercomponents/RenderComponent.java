package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public interface RenderComponent {
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable);
}
