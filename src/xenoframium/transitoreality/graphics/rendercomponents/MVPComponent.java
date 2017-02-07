package xenoframium.transitoreality.graphics.rendercomponents;

import static org.lwjgl.opengl.GL20.*;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.gl.ShaderProgram;
import xenoframium.transitoreality.gl.ShaderUniform;
import xenoframium.transitoreality.gl.Uniform;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class MVPComponent implements RenderComponent{
	private final ShaderProgram shaderProgram;
	private final Uniform mvpLocation;

	public MVPComponent(ShaderProgram program) {
		shaderProgram = program;
		mvpLocation = new Uniform(shaderProgram, ShaderUniform.MVP_MATRIX);
	}
	
	@Override
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable) {
		Matrix4 mvpMatrix = projection.getProjectionMatrix().toMatrix4();
		mvpMatrix.multiply(camera.getViewMatrix().toMatrix4()).multiply(renderable.getModelMatrix());

		glUniformMatrix4fv(mvpLocation.getLocation(), false, mvpMatrix.asBuffer());
	}

}
