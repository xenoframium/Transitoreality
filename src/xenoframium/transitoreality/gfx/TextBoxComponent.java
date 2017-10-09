package xenoframium.transitoreality.gfx;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityManager;
import xenoframium.ecsrender.Mesh;
import xenoframium.ecsrender.Renderable2D;
import xenoframium.ecsrender.TextInfo;
import xenoframium.ecsrender.TextMeshGenerator;
import xenoframium.ecsrender.universalcomponents.AttachmentComponent;
import xenoframium.ecsrender.universalcomponents.PositionComponent;
import xenoframium.ecsrender.universalcomponents.PositioningComponent;
import xenoframium.ecsrender.universalcomponents.ScaleComponent;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.transitoreality.launch.Launcher;
import xenoframium.transitoreality.launch.Transitoreality;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by chrisjung on 8/10/17.
 */
public class TextBoxComponent implements Component, DynamicComponent {
    Entity textEntity;
    Entity boxEntity;
    Entity textboxEntity;
    Entity anchor;
    Renderable2D textRenderable;
    Renderable2D boxRenderable;
    Vec4 backgroundColour;
    Vec4 foregroundColour;
    String text;
    private float width;
    private float height;
    float zValue;

    Set<DimensionChangeCallback> callbacks = Collections.newSetFromMap(new WeakHashMap<>());

    public TextBoxComponent(Entity anchor, String text, Vec4 foregroundColour, Vec4 backgroundColour, float zValue) {
        this.zValue = zValue;
        this.text = text;
        this.foregroundColour = foregroundColour;
        this.backgroundColour = backgroundColour;
        this.anchor = anchor;

        textEntity = EntityManager.createEntity();
        textEntity.addComponents(new PositionComponent(new Vec3(-0.15f, 0.25f, zValue)));
        textEntity.addComponents(new PositioningComponent());

        boxRenderable = new Renderable2D(CommonMeshes.SQUARE_MESH, backgroundColour, zValue-0.1f);
        boxEntity = EntityManager.createEntity();
        boxEntity.addComponents(boxRenderable);
        boxEntity.addComponents(new ScaleComponent(new Vec3(1, 1, 1)));
        boxEntity.addComponents(new PositioningComponent());

        textboxEntity = EntityManager.createEntity();

        textEntity.addComponents(new AttachmentComponent(textboxEntity));
        boxEntity.addComponents(new AttachmentComponent(textboxEntity));
        textboxEntity.addComponents(new PositioningComponent());
        textboxEntity.addComponents(new AttachmentComponent(anchor));
        height = 1;

        update();
    }

    void update() {
        if (textRenderable != null) {
            textRenderable.close();
            textEntity.removeComponents(Renderable2D.class);
        }
        TextInfo inf = TextMeshGenerator.assemble(Transitoreality.fontInfo, text, 0, foregroundColour, backgroundColour);


        textRenderable = new Renderable2D(inf, zValue-0.2f);
        textEntity.addComponents(textRenderable);

        width = Math.max(inf.width, 1) + 0.2f;
        Renderable2D renderable = boxEntity.getComponent(Renderable2D.class);
        renderable.z = zValue - 0.1f;
        renderable.setColour(backgroundColour);
        boxEntity.getComponent(ScaleComponent.class).scale.x = width;

        for (DimensionChangeCallback callback : callbacks) {
            callback.onDimensionChange(width, height);
        }
    }

    public void setText(String newText) {
        text = newText;
        update();
    }

    public void setZValue(float z) {
        zValue = z;
        update();
    }

    public String getText() {
        return text;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getZValue() {
        return zValue;
    }

    @Override
    public void subscribeUpdateCallback(DimensionChangeCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void unsubscribeUpdateCallback(DimensionChangeCallback callback) {
        callbacks.remove(callback);
    }
}
