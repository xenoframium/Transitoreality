package xenoframium.transitoreality.graphics.renderer;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.datastructures.Stack;
import xenoframium.transitoreality.gl.Window;
import xenoframium.transitoreality.graphics.Camera;
import xenoframium.transitoreality.graphics.Projection;
import xenoframium.transitoreality.graphics.renderables.Renderable;

public class Renderer {
    private final Window window;
    private final Camera camera;
    private final Projection projection;
    private final Stack<Matrix4> modelMatrices = new Stack<>();

    public Renderer(Window window, Camera camera, Projection projection) {
        this.window = window;
        this.camera = camera;
        this.projection = projection;
        modelMatrices.push(new Matrix4());
    }

    private void addModelMatrix(Renderable renderable) {
        modelMatrices.push(new Matrix4(modelMatrices.peek()).multiply(renderable.getModelMatrix()));
    }

    public void renderObject(Renderable renderable) {
        addModelMatrix(renderable);
        renderable.render(this);
        modelMatrices.pop();
    }

    public Window getWindow() {
        return window;
    }

    public Camera getCamera() {
        return camera;
    }

    public Projection getProjection() {
        return projection;
    }

    public Matrix4 getModelMatrix() {
        return modelMatrices.peek();
    }
}
