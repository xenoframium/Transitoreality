package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.transitoreality.gl.ShaderAttribute;
import xenoframium.transitoreality.gl.Texture;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.gl.Vbo;
import xenoframium.transitoreality.graphics.Renderer;

public class TextureComponent implements RenderComponent{
	private final Texture texture;
	
	public TextureComponent(Vao vao, float[] uvCoords, Texture texture) {
		Vbo uvVbo = new Vbo(uvCoords);
		vao.addAttribArray(ShaderAttribute.UV_TEXTURE_COORDINATE_ATTRIBUTE, 2, uvVbo);
		this.texture = texture;
	}
	
	@Override
	public void onRender(Renderer renderer) {
		texture.bind();
	}

}
