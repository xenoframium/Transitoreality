package xenoframium.transitoreality.graphics.rendercomponents;

import static org.lwjgl.opengl.GL11.*;

import xenoframium.transitoreality.gl.ShaderAttribute;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.gl.Vbo;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class TriangleDrawComponent implements RenderComponent {
	private final int numVertices;

	public TriangleDrawComponent(Vao vao, float[] vertices) {
		Vbo vertexVbo = new Vbo(vertices);
		vao.addAttribArray(ShaderAttribute.VERTEX_POSITION_ATTRIBUTE, 3, vertexVbo);
		
		numVertices = vertices.length / 3;
	}

	@Override
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable) {
		glDrawArrays(GL_TRIANGLES, 0, numVertices);
	}

}
