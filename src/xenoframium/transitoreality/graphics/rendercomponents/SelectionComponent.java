package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.Line3f;
import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.glmath.linearalgebra.Triangle;
import xenoframium.glmath.linearalgebra.Vector3;
import xenoframium.glmath.linearalgebra.Vector4;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.Selectable;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class SelectionComponent implements RenderComponent{
	private final Selectable selectable;
	private final Triangle[] triangles;
	
	public SelectionComponent(Selectable selectable, float[] vertices) {
		this.selectable = selectable;
		triangles = new Triangle[vertices.length / 9];

		for (int i = 0; i < vertices.length / 9; i++) {
			int vertexIndex = i * 9;
			Vector3 vertex1 = new Vector3(vertices[vertexIndex], vertices[vertexIndex + 1],
					vertices[vertexIndex + 2]);
			Vector3 vertex2 = new Vector3(vertices[vertexIndex + 3], vertices[vertexIndex + 4],
					vertices[vertexIndex + 5]);
			Vector3 vertex3 = new Vector3(vertices[vertexIndex + 6], vertices[vertexIndex + 7],
					vertices[vertexIndex + 8]);
			triangles[i] = new Triangle(vertex1, vertex2, vertex3);
		}
	}
	
	private Line3f getMouseRay(Window window, Camera camera, Projection projection, Renderable renderable) {
		Matrix4 modelMatrix = renderable.getModelMatrix();
		Matrix4 viewMatrix = camera.getViewMatrix().toMatrix4();
		Matrix4 projectionMatrix = projection.getProjectionMatrix().toMatrix4();
		Matrix4 inverseMVP = projectionMatrix.multiply(viewMatrix).multiply(modelMatrix).inverse();
		
		Vector4 mousePos = new Vector4(window.getMouseXPos(), window.getMouseYPos(), projection.getNear(), 0);
		mousePos = inverseMVP.multiply(mousePos);
		Vector3 mousePosFront = new Vector3(mousePos.x, mousePos.y, 0);
		Vector3 mousePosBack = new Vector3(mousePos.x, mousePos.y, 1);
		
		return GLM.lineFromPoints(mousePosFront, mousePosBack);
	}
	
	@Override
	public void onRender(Window window, Camera camera, Projection projection, Renderable renderable) {
		Line3f ray = getMouseRay(window, camera, projection, renderable);
		for (Triangle triangle : triangles) {
			if (GLM.doesLineIntersectTriangle(ray, triangle)) {
				selectable.onSelection(window, camera, projection, renderable);
				return;
			}
		}
	}

}
