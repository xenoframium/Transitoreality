package xenoframium.transitoreality.graphics;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.ImmutableMatrix4;

//Projections are immutable
public class Projection {
	private final float width;
	private final float height;
	private final float near;
	private final float far;
	
	private final ImmutableMatrix4 projectionMatrix;

	public Projection(float width, float height, float near, float far) {
		this.width = width;
		this.height = height;
		this.near = near;
		this.far = far;
		
		projectionMatrix = GLM.ortho(width, height, near, far).toImmutableMatrix4();
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getNear() {
		return near;
	}

	public float getFar() {
		return far;
	}

	public ImmutableMatrix4 getProjectionMatrix() {
		return projectionMatrix;
	}
}
