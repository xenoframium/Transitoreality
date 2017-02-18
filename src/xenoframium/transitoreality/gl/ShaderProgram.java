package xenoframium.transitoreality.gl;

import xenoframium.glwrapper.GlProgram;

public class ShaderProgram {
    public static final ShaderProgram TEXTURED_SHADER_PROGRAM = new ShaderProgram(VertexShader.TEXTURED_VERTEX_SHADER, FragmentShader.TEXTURED_FRAGMENT_SHADER);

	private final GlProgram glProgram;

	private final VertexShader vertexShader;
	private final FragmentShader fragmentShader;

	public ShaderProgram(VertexShader vertexShader, FragmentShader fragmentShader) {
	    this.vertexShader = vertexShader;
	    this.fragmentShader = fragmentShader;

		glProgram = new GlProgram(vertexShader.getShader(), fragmentShader.getShader());
	}

	public void use() {
		glProgram.use();
	}

	VertexShader getVertexShader() {
		return vertexShader;
	}
	
	FragmentShader getFragmentShader() {
		return fragmentShader;
	}

	GlProgram getProgram() {
		return glProgram;
	}
}
