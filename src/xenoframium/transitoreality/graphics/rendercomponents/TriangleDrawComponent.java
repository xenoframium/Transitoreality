package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.transitoreality.gl.ShaderAttribute;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.gl.Vbo;
import xenoframium.transitoreality.graphics.renderer.Renderer;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public class TriangleDrawComponent implements RenderComponent {
	private final int numVertices;
	private final Vao vao;

	public TriangleDrawComponent(Vao vao, float[] vertices) {
		this.vao = vao;

		Vbo vertexVbo = new Vbo(vertices);
		vao.addAttribArray(ShaderAttribute.VERTEX_POSITION_ATTRIBUTE, 3, vertexVbo);
		
		numVertices = vertices.length / 3;
	}

	@Override
	public void onRender(Renderer renderer) {
	    vao.bind();
		glDrawArrays(GL_TRIANGLES, 0, numVertices);
	}

}
