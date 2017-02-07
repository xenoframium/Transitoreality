package xenoframium.transitoreality.graphics.rendercomponents;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.gl.ShaderAttribute;
import xenoframium.transitoreality.gl.ShaderProgram;
import xenoframium.transitoreality.gl.Uniform;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.gl.Vbo;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class VertexComponent implements RenderComponent {
	private final ShaderProgram shaderProgram;
	private final Uniform mvp;
	private final int numVertices;

	public VertexComponent(Vao vao, ShaderProgram program, float[] vertices) {
		Vbo vertexVbo = new Vbo(vertices);
		vao.addAttribArray(ShaderAttribute.VERTEX_POSITION_ATTRIBUTE, 3, vertexVbo);
		shaderProgram = program;
		mvp = new Uniform(shaderProgram, "mvp_matrix");
		
		numVertices = vertices.length / 3;
	}

	@Override
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable) {
		Matrix4 projectionMatrix = projection.getProjectionMatrix().toMatrix4();
		Matrix4 viewMatrix = camera.getViewMatrix().toMatrix4();
		Matrix4 mvpMatrix = projectionMatrix.multiply(viewMatrix).multiply(renderable.getModelMatrix());

		glUniformMatrix4fv(mvp.getLocation(), false, mvpMatrix.asBuffer());
		glDrawArrays(GL_TRIANGLES, 0, numVertices);
	}

}
