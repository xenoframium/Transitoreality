package xenoframium.transitoreality.gl;

import static org.lwjgl.opengl.GL20.*;

import java.io.File;
import java.io.IOException;

import xenoframium.glwrapper.GlProgram;
import xenoframium.glwrapper.GlShader;

public class ShaderProgram {
	private final GlProgram glProgram;

	private final String vertexShaderPath;
	private final String fragmentShaderPath;

	public ShaderProgram(File vertexShaderFile, File fragmentShaderFile) {
		vertexShaderPath = vertexShaderFile.getAbsolutePath();
		fragmentShaderPath = fragmentShaderFile.getAbsolutePath();
		
		GlShader vertexShader;
		GlShader fragmentShader;
		try {
			vertexShader = new GlShader(GL_VERTEX_SHADER, vertexShaderFile);
		} catch (IOException exception) {
			throw new ShaderIOException(vertexShaderFile);
		}
		try {
			fragmentShader = new GlShader(GL_FRAGMENT_SHADER, fragmentShaderFile);
		} catch (IOException exception) {
			throw new ShaderIOException(fragmentShaderFile);
		}
		
		glProgram = new GlProgram(vertexShader, fragmentShader);
	}

	public void use() {
		glProgram.use();
	}

	protected String getVertexShaderPath() {
		return vertexShaderPath;
	}
	
	protected String getFragmentShaderPath() {
		return fragmentShaderPath;
	}

	protected GlProgram getProgram() {
		return glProgram;
	}
}
