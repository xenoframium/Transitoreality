package xenoframium.transitoreality.graphics;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.Matrix4;

public class Projection {
	private float width;
	private float height;
	private float near;
	private float far;
	
	private Matrix4 projectionMatrix;

	public Projection(float width, float height, float near, float far) {
		this.width = width;
		this.height = height;
		this.near = near;
		this.far = far;
		
		projectionMatrix = GLM.ortho(width, height, near, far);
	}

	private void recalculateProjectionMatrix() {
		projectionMatrix = GLM.ortho(width, height, near, far);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		if (this.width == width) {
			return;
		}
		this.width = width;
		recalculateProjectionMatrix();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		if (this.height == height) {
			return;
		}
		this.height = height;
		recalculateProjectionMatrix();
	}

	public float getNear() {
		return near;
	}

	public void setNear(float near) {
		if (this.near == near) {
			return;
		}
		this.near = near;
		recalculateProjectionMatrix();
	}

	public float getFar() {
		return far;
	}

	public void setFar(float far) {
		if (this.far == far) {
			return;
		}
		this.far = far;
		recalculateProjectionMatrix();
	}

	public Matrix4 getProjectionMatrix() {
		return new Matrix4(projectionMatrix);
	}
}
