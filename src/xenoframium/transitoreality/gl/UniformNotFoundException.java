package xenoframium.transitoreality.gl;

public class UniformNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2601559851137224090L;

	public UniformNotFoundException(String uniformName, ShaderProgram program) {
		super("Uniform: \"" + uniformName + "\" not found in vertex shader: \"" + program.getVertexShaderPath()
				+ "\" or in fragment shader: \"" + program.getFragmentShaderPath() + "\"");
	}
}
