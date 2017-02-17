package xenoframium.transitoreality.gl;

public enum ShaderUniform {
	MVP_MATRIX("mvp_matrix"), COLOUR("colour");
	
	private String name;
	
	protected String getName() {
		return name;
	}
	
	ShaderUniform(String name) {
		this.name = name;
	}
}
