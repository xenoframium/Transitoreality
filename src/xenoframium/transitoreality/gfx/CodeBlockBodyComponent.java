package xenoframium.transitoreality.gfx;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityManager;
import xenoframium.ecsrender.Renderable2D;
import xenoframium.ecsrender.universalcomponents.AttachmentComponent;
import xenoframium.ecsrender.universalcomponents.PositionComponent;
import xenoframium.ecsrender.universalcomponents.PositioningComponent;
import xenoframium.ecsrender.universalcomponents.ScaleComponent;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;

import java.util.*;
import java.util.List;

/**
 * Created by chrisjung on 9/10/17.
 */
public class CodeBlockBodyComponent implements Component, DynamicComponent {
    Entity anchor;
    Entity codeBlockEntity;
    Entity boxEntity;
    Entity[] textParts;
    List<Entity> textBoxes = new ArrayList<>();
    Map<String, Entity> textFields = new HashMap<>();

    float zValue;
    float width;
    float height = 1.2f;

    boolean shouldBlock = false;

    Set<DimensionChangeCallback> callbacks = Collections.newSetFromMap(new WeakHashMap<>());
    UpdateCallback updateCallback = new UpdateCallback();

    private class UpdateCallback implements DimensionChangeCallback {
        @Override
        public void onDimensionChange(float width, float height) {
            update();
        }
    }

    public CodeBlockBodyComponent(Entity anchor, CodeBlockBodyEntry[] entries, Vec4 backgroundColour, float zValue) {
        this.zValue = zValue;
        this.anchor = anchor;
        textParts = new Entity[entries.length];
        codeBlockEntity = EntityManager.createEntity();
        codeBlockEntity.addComponents(new PositioningComponent());
        codeBlockEntity.addComponents(new AttachmentComponent(anchor));
        int i=0;
        for (CodeBlockBodyEntry entry : entries) {
            Entity e = null;
            switch (entry.type) {
                case CODE_BLOCK:
                    e = entry.codeBlockBody;
                    e.getComponent(CodeBlockBodyComponent.class).subscribeUpdateCallback(updateCallback);
                    break;
                case TEXT_BOX:
                    e = EntityManager.createEntity();
                    TextBoxComponent tbc = new TextBoxComponent(e, entry.defaultText, new Vec4(1, 1, 1, 1), backgroundColour, zValue-0.1f);
                    e.addComponents(tbc);
                    break;
                case TEXT_FIELD:
                    e = EntityManager.createEntity();
                    textFields.put(entry.tag, e);
                    TextFieldComponent tfc = new TextFieldComponent(e, entry.defaultText, new Vec4(0, 0, 0, 1), new Vec4(1, 1, 1, 1), zValue-0.1f);
                    e.addComponents(tfc);
                    tfc.subscribeUpdateCallback(updateCallback);
                    break;
            }
            textParts[i] = e;
            e.addComponents(new AttachmentComponent(codeBlockEntity));
            e.addComponents(new PositioningComponent());
            e.addComponents(new PositionComponent(new Vec3(0, 0, 0)));
            i++;
        }
        boxEntity = EntityManager.createEntity();
        boxEntity.addComponents(new ScaleComponent(new Vec3(1, 1, 1)));
        boxEntity.addComponents(new PositioningComponent());
        boxEntity.addComponents(new AttachmentComponent(codeBlockEntity));
        boxEntity.addComponents(new Renderable2D(CommonMeshes.SQUARE_MESH, backgroundColour, zValue));
        update();
    }

    private void update() {
        if (shouldBlock) {
            return;
        }
        height = 1f;

        for (Entity e : textParts) {
            if (e.hasComponents(CodeBlockBodyComponent.class)) {
                CodeBlockBodyComponent comp = e.getComponent(CodeBlockBodyComponent.class);
                height = Math.max(height, comp.getHeight());
            }
        }

        height += 0.2f;

        float xOff = 0.1f;
        for (Entity e : textParts) {
            PositionComponent posc = e.getComponent(PositionComponent.class);
            posc.pos.x = -xOff;
            float width;
            if (e.hasComponents(TextFieldComponent.class)) {
                TextFieldComponent tfc = e.getComponent(TextFieldComponent.class);
                posc.pos.y = (height - tfc.getHeight()) / 2;
                width = tfc.getWidth();
                shouldBlock = true;
                tfc.setZValue(zValue-0.1f);
                shouldBlock = false;
            } else if (e.hasComponents(TextBoxComponent.class)) {
                TextBoxComponent tbc = e.getComponent(TextBoxComponent.class);
                posc.pos.y = (height - tbc.getHeight()) / 2;
                width = tbc.getWidth();
                shouldBlock = true;
                tbc.setZValue(zValue-0.1f);
                shouldBlock = false;
            } else {
                CodeBlockBodyComponent comp = e.getComponent(CodeBlockBodyComponent.class);
                posc.pos.y = (height - comp.getHeight()) / 2;
                width = comp.getWidth();
                shouldBlock = true;
                comp.setZValue(zValue-0.1f);
                shouldBlock = false;
            }
            xOff += width;
        }

        width = xOff + 0.1f;
        ScaleComponent comp = boxEntity.getComponent(ScaleComponent.class);
        comp.scale.x = width;
        comp.scale.y = height;

        for (DimensionChangeCallback callback : callbacks) {
            callback.onDimensionChange(width, height);
        }
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
    public void setZValue(float zValue) {
        this.zValue = zValue;
        for (Entity e : textParts) {

            if (e.hasComponents(TextFieldComponent.class)) {
                TextFieldComponent tfc = e.getComponent(TextFieldComponent.class);
                tfc.setZValue(zValue-0.1f);
            } else if (e.hasComponents(TextBoxComponent.class)) {
                TextBoxComponent tbc = e.getComponent(TextBoxComponent.class);
                tbc.setZValue(zValue-0.1f);
            } else {
                CodeBlockBodyComponent comp = e.getComponent(CodeBlockBodyComponent.class);
                comp.setZValue(zValue-0.1f);
            }
        }
        boxEntity.getComponent(Renderable2D.class).z = zValue;
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
