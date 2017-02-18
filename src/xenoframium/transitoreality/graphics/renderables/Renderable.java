package xenoframium.transitoreality.graphics.renderables;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.graphics.Renderer;

public interface Renderable {
	void render(Renderer renderer);
	
	Matrix4 getModelMatrix();
	void setModelMatrix(Matrix4 newModelMatrix);
}
