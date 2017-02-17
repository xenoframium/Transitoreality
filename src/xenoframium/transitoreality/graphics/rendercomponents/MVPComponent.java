package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.gl.ShaderProgram;
import xenoframium.transitoreality.gl.ShaderUniform;
import xenoframium.transitoreality.gl.Uniform;
import xenoframium.transitoreality.graphics.renderer.Renderer;

import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class MVPComponent implements RenderComponent{
	private final ShaderProgram shaderProgram;
	private final Uniform mvpLocation;

	public MVPComponent(ShaderProgram program) {
		shaderProgram = program;
		mvpLocation = new Uniform(shaderProgram, ShaderUniform.MVP_MATRIX);
	}
	
	@Override
	public void onRender(Renderer renderer) {
		Matrix4 mvpMatrix = renderer.getProjection().getProjectionMatrix();
		mvpMatrix.multiply(renderer.getCamera().getViewMatrix()).multiply(renderer.getModelMatrix());

		glUniformMatrix4fv(mvpLocation.getLocation(), false, mvpMatrix.asBuffer());
	}

}
