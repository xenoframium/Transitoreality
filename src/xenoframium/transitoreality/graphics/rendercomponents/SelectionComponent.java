package xenoframium.transitoreality.graphics.rendercomponents;

import xenoframium.glmath.GLM;
import xenoframium.glmath.linearalgebra.*;
import xenoframium.transitoreality.graphics.Selectable;
import xenoframium.transitoreality.graphics.renderer.Renderer;

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
	
	private Line3f getMouseRay(Renderer renderer) {
		Matrix4 viewMatrix = renderer.getCamera().getViewMatrix();
		Matrix4 projectionMatrix = renderer.getProjection().getProjectionMatrix();
		Matrix4 inverseMVP = projectionMatrix.multiply(viewMatrix).multiply(renderer.getModelMatrix()).inverse();

		float mouseXPos = renderer.getWindow().getMouseXPos();
		float mouseYPos = renderer.getWindow().getMouseYPos();
		float nearPlane = renderer.getProjection().getNear();

		Vector4 mousePos = new Vector4(mouseXPos, mouseYPos, nearPlane, 0);
		mousePos = inverseMVP.multiply(mousePos);
		Vector3 mousePosFront = new Vector3(mousePos.x, mousePos.y, 0);
		Vector3 mousePosBack = new Vector3(mousePos.x, mousePos.y, 1);
		
		return GLM.lineFromPoints(mousePosFront, mousePosBack);
	}
	
	@Override
	public void onRender(Renderer renderer) {
		Line3f ray = getMouseRay(renderer);
		for (Triangle triangle : triangles) {
			if (GLM.doesLineIntersectTriangle(ray, triangle)) {
				selectable.onSelection();
				return;
			}
		}
	}

}
