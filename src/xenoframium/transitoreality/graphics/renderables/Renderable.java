package xenoframium.transitoreality.graphics.renderables;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;

public interface Renderable {
	public void render(Window window, Camera camera, Projection projection);
	
	public Matrix4 getModelMatrix();
	public void setModelMatrix(Matrix4 newModelMatrix);
}
