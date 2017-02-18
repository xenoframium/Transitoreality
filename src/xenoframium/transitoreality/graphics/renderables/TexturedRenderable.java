package xenoframium.transitoreality.graphics.renderables;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.gl.ShaderProgram;
import xenoframium.transitoreality.gl.Texture;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.graphics.rendercomponents.MVPComponent;
import xenoframium.transitoreality.graphics.rendercomponents.TextureComponent;
import xenoframium.transitoreality.graphics.rendercomponents.TriangleDrawComponent;
import xenoframium.transitoreality.graphics.Renderer;

public class TexturedRenderable implements Renderable {
	private static final ShaderProgram shaderProgram = ShaderProgram.TEXTURED_SHADER_PROGRAM;

	private final Vao vao;

	private final MVPComponent mvpComponent;
	private final TextureComponent textureComponent;
	private final TriangleDrawComponent vertexComponent;
	
	private Matrix4 modelMatrix = Matrix4.getIdentity();
	
	public TexturedRenderable(float[] vertices, float[] uvCoords, Texture texture) {
		vao = new Vao();

		mvpComponent = new MVPComponent(shaderProgram);
		textureComponent = new TextureComponent(vao, uvCoords, texture);
		vertexComponent = new TriangleDrawComponent(vao, vertices);
	}

	@Override
	public void render(Renderer renderer) {
		shaderProgram.use();

		mvpComponent.onRender(renderer);
		textureComponent.onRender(renderer);
		vertexComponent.onRender(renderer);
	}

	@Override
	public Matrix4 getModelMatrix() {
		return modelMatrix;
	}

	@Override
	public void setModelMatrix(Matrix4 newModelMatrix) {
		modelMatrix = newModelMatrix;
	}

}
