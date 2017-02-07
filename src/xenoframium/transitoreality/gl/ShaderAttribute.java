package xenoframium.transitoreality.gl;

public enum ShaderAttribute {
	VERTEX_POSITION_ATTRIBUTE(0), UV_TEXTURE_COORDINATE_ATTRIBUTE(1);
	
	private int index;
	
	protected int getIndex() {
		return index;
	}
	
	private ShaderAttribute(int index) {
		this.index = index;
	}
}
