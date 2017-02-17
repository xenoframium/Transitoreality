package xenoframium.transitoreality.graphics.renderables;

import xenoframium.glmath.linearalgebra.Matrix4;
import xenoframium.transitoreality.graphics.renderer.Renderer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GroupRenderable implements Renderable, Set<Renderable> {

    private HashSet<Renderable> childRenderables = new HashSet<>();
    private Matrix4 modelMatrix = new Matrix4();

    @Override
    public void render(Renderer renderer) {
        for (Renderable renderable : childRenderables) {
            renderer.renderObject(renderable);
        }
    }

    @Override
    public Matrix4 getModelMatrix() {
        return modelMatrix;
    }

    @Override
    public void setModelMatrix(Matrix4 newModelMatrix) {
        modelMatrix = newModelMatrix;
    }

    @Override
    public boolean contains(Object object) {
        return childRenderables.contains(object);
    }

    @Override
    public int size() {
        return childRenderables.size();
    }

    @Override
    public boolean isEmpty() {
        return childRenderables.isEmpty();
    }

    @Override
    public Iterator<Renderable> iterator() {
        return childRenderables.iterator();
    }

    @Override
    public Object[] toArray() {
        return childRenderables.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array) {
        return childRenderables.toArray(array);
    }

    @Override
    public boolean add(Renderable renderable) {
        return childRenderables.add(renderable);
    }

    @Override
    public boolean remove(Object object) {
        return childRenderables.remove(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return childRenderables.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends Renderable> collection) {
        return childRenderables.addAll(collection);
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return childRenderables.retainAll(collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return childRenderables.removeAll(collection);
    }

    @Override
    public void clear() {
        childRenderables.clear();
    }
}
