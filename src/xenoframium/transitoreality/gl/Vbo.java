package xenoframium.transitoreality.gl;

import static org.lwjgl.opengl.GL15.*;

import xenoframium.glwrapper.GlVbo;

public class Vbo {
	private final GlVbo glVbo;

	public Vbo(float[] data) {
		glVbo = new GlVbo();
		glVbo.bind(GL_ARRAY_BUFFER);
		glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
	}

	public void bind() {
		glVbo.bind(GL_ARRAY_BUFFER);
	}
}
