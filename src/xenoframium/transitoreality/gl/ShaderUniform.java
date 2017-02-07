package xenoframium.transitoreality.gl;

public enum ShaderUniform {
	MVP_MATRIX("mvp_matrix"), NON("NON");
	
	private String name;
	
	protected String getName() {
		return name;
	}
	
	private ShaderUniform(String name) {
		this.name = name;
	}
}
