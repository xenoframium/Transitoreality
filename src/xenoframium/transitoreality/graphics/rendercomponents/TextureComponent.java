package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.transitoreality.gl.ShaderAttribute;
import xenoframium.transitoreality.gl.Texture;
import xenoframium.transitoreality.gl.Vao;
import xenoframium.transitoreality.gl.Vbo;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class TextureComponent implements RenderComponent{
	private final Texture texture;
	
	public TextureComponent(Vao vao, float[] uvCoords, Texture texture) {
		Vbo uvVbo = new Vbo(uvCoords);
		vao.addAttribArray(ShaderAttribute.UV_TEXTURE_COORDINATE_ATTRIBUTE, 2, uvVbo);
		this.texture = texture;
	}
	
	@Override
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable) {
		texture.bind();
	}

}
