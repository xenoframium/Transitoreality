package xenoframium.transitoreality.graphics;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.ImmutableMatrix4;
import xenoframium.glmath.linearalgebra.ImmutableVector3;
import xenoframium.glmath.linearalgebra.Matrix4;
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

	public Vector3 getPosition() {
		return position.toVector3();
	}

	public Vector3 getTarget() {
		return target.toVector3();
	}

	public Vector3 getUpVector() {
		return up.toVector3();
	}

	public Matrix4 getViewMatrix() {
		return viewMatrix.toMatrix4();
	}
}
