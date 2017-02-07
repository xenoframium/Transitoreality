package xenoframium.transitoreality.gl;

import xenoframium.glwrapper.GlUniform;

public class Uniform {
	private GlUniform glUniform;

	public Uniform(ShaderProgram program, ShaderUniform uniform) {
		glUniform = new GlUniform(program.getProgram(), uniform.getName());
		if (glUniform.getLocation() == -1) {
			throw new UniformNotFoundException(uniform.getName(), program);
		}
	}
	
	public Uniform(ShaderProgram program, String name) {
		glUniform = new GlUniform(program.getProgram(), name);
	}
	
	public int getLocation() {
		return glUniform.getLocation();
	}
}
