package xenoframium.transitoreality.graphics;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.ImmutableMatrix4;
import xenoframium.glmath.linearalgebra.ImmutableVector3;
import xenoframium.glmath.linearalgebra.Vector3;

//Cameras are immutable
public class Camera {
	private final ImmutableVector3 position;
	private final ImmutableVector3 target;
	private final ImmutableVector3 up;

	private final ImmutableMatrix4 viewMatrix;

	public Camera(Vector3 cameraPosition, Vector3 viewTarget, Vector3 upVector) {
		position = cameraPosition.toImmutableVector3();
		target = viewTarget.toImmutableVector3();
		up = upVector.toImmutableVector3();

		viewMatrix = GLM.cameraLookAt(cameraPosition, viewTarget, upVector).toImmutableMatrix4();
	}

	public ImmutableVector3 getPosition() {
		return position;
	}

	public ImmutableVector3 getTarget() {
		return target;
	}

	public ImmutableVector3 getUpVector() {
		return up;
	}

	public ImmutableMatrix4 getViewMatrix() {
		return viewMatrix;
	}
}
