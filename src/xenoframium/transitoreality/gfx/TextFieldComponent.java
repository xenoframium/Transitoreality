package xenoframium.transitoreality.gfx;

import xenoframium.ecs.Component;
import xenoframium.ecs.Entity;
import xenoframium.ecsrender.SelectableComponent;
import xenoframium.ecsrender.SelectionCallback;
import xenoframium.ecsrender.system.CharCallback;
import xenoframium.ecsrender.system.KeyPressCallback;
import xenoframium.ecsrender.system.Window;
import xenoframium.ecsrender.universalcomponents.PositioningComponent;
import xenoframium.ecsrender.universalcomponents.ScaleComponent;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.glmath.linearalgebra.Vec4;
import xenoframium.transitoreality.launch.Transitoreality;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL45.*;

/**
 * Created by chrisjung on 8/10/17.
 */
public class TextFieldComponent implements Component, DynamicComponent {
    TextBoxComponent textBox;
    Entity boxEntity;
    boolean isSelected = false;
    private float width;
    private float height;

    private TypeCallback typeCallback = new TypeCallback();
    private BackspaceCallback backspaceCallback = new BackspaceCallback();

    Set<DimensionChangeCallback> callbacks = Collections.newSetFromMap(new WeakHashMap<>());

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
        return textBox.getZValue();
    }

    @Override
    public void setZValue(float zValue) {
        textBox.setZValue(zValue);
        update();
    }

    @Override
    public void subscribeUpdateCallback(DimensionChangeCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void unsubscribeUpdateCallback(DimensionChangeCallback callback) {
        callbacks.remove(callback);
    }

    class FieldSelectionCallback implements SelectionCallback {
        @Override
        public void onSelection(Entity selectedEntity, int mouseButton, Vec3 selectionPoint, Vec3 triangleNormal) {
            textBox.backgroundColour = new Vec4(0, 0, 0, 1);
            textBox.foregroundColour = new Vec4(1, 1, 1, 1);
            textBox.update();
            isSelected = true;
        }

        @Override
        public void onDeselection() {
            textBox.backgroundColour = new Vec4(1, 1, 1, 1);
            textBox.foregroundColour = new Vec4(0, 0, 0, 1);
            textBox.update();
            isSelected = false;
        }
    }

    class TypeCallback implements CharCallback {
        @Override
        public void invoke(Window window, char character, int mods) {
            if (isSelected) {
                if (' ' <= character && character <= '~') {
                    textBox.setText(textBox.getText() + character);
                    update();
                }
            }
        }
    }

    class BackspaceCallback implements KeyPressCallback {
        @Override
        public void invoke(Window window, int key, int scancode, int action, int mods) {
            if (action != GLFW_PRESS && action != GLFW_REPEAT) {
                return;
            }
            if (key == GLFW_KEY_BACKSPACE) {
                if (isSelected && textBox.getText().length() > 0) {
                    textBox.setText(textBox.getText().substring(0, textBox.getText().length() - 1));
                    update();
                }
            }
        }
    }

    public TextFieldComponent(Entity anchor, String text, Vec4 foregroundColour, Vec4 backgroundColour, float zValue) {
        textBox = new TextBoxComponent(anchor, text, foregroundColour, backgroundColour, zValue);
        boxEntity = textBox.boxEntity;
        boxEntity.addComponents(new SelectableComponent(CommonMeshes.SQUARE_MESH, new FieldSelectionCallback(), GL_TRIANGLE_STRIP));
        Transitoreality.input.subscribeCharCallback(typeCallback);
        Transitoreality.input.subscribeKeyPressCallback(backspaceCallback);
        update();
    }

    private void update() {
        width = textBox.getWidth();
        height = textBox.getHeight();
        for (DimensionChangeCallback callback : callbacks) {
            callback.onDimensionChange(width, height);
        }
    }
}
