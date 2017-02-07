package xenoframium.transitoreality.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

import xenoframium.glwrapper.GlVao;

public class Vao {
	private final GlVao glVao;

	public Vao() {
		glVao = new GlVao();
		glVao.bind();
	}

	public void addAttribArray(ShaderAttribute attribute, int perVertexSize, Vbo vbo) {
		glEnableVertexAttribArray(attribute.getIndex());
		vbo.bind();
		glVertexAttribPointer(attribute.getIndex(), perVertexSize, GL_FLOAT, false, 0, NULL);
	}
	
	public void bind() {
		glVao.bind();
	}
}
