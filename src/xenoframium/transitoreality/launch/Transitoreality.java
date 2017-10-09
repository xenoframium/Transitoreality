package xenoframium.transitoreality.launch;

import xenoframium.ecs.Entity;
import xenoframium.ecs.EntityManager;
import xenoframium.ecsrender.*;
import xenoframium.ecsrender.gl.Camera;
import xenoframium.ecsrender.gl.Projection;
import xenoframium.ecsrender.system.Input;
import xenoframium.ecsrender.system.Window;
import xenoframium.ecsrender.universalcomponents.PositioningComponent;
import xenoframium.ecsrender.universalcomponents.PositioningSystem;
import xenoframium.glmath.linearalgebra.Vec3;
import xenoframium.transitoreality.interpreter.InterpretedComponent;
import xenoframium.transitoreality.interpreter.VarType;

import java.io.File;

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
 * Created by chrisjung on 5/10/17.
 */
public class Transitoreality {
    public static final int windowWidth = 800;
    public static final int windowHeight = 600;
    public static final float fov = 90;
    public static final float nearClippingPlane = 0.1f;
    public static final float farClippingPlane = 100.0f;

    public static final Projection projection = Projection.createPerspectiveProjection(fov, ((float) windowWidth) / windowHeight, nearClippingPlane, farClippingPlane);
    public static final Camera camera = new Camera(new Vec3(0, 0, -1), new Vec3(0, 0, 0));
    public static final FontInfo fontInfo;
    public static final Input input;

    private static final Window window;

    static {
        window = GraphicsInitialiser.initGlAndContext("Transitoreality", windowWidth, windowHeight);
        input = new Input(window);
        fontInfo = new FontInfo(new File("fonts/LiberationSerif-Regular.ttf"), 1024, 1024, ' ', '~', 100);
        window.enableVsync();
    }

    static void initSystems() {
        PositioningSystem positioningSystem = new PositioningSystem();
        EntityManager.subscribeSystem(positioningSystem, PositioningComponent.class);

        SelectionSystem selectionSystem = new SelectionSystem(window, input, projection);
        EntityManager.subscribeSystem(selectionSystem, SelectableComponent.class);
        EntityManager.addSystemPredecessors(selectionSystem, positioningSystem);

        PickingSystem pickSystem = new PickingSystem(window, input, 100, projection, camera);
        EntityManager.subscribeSystem(pickSystem, PickableComponent.class);

        RenderSystem2D renderSystem2D = new RenderSystem2D(projection, camera);
        EntityManager.subscribeSystem(renderSystem2D, Renderable2D.class);
        EntityManager.addSystemPredecessors(renderSystem2D, positioningSystem);
    }

    static void runDebug() {
//        InterpretedComponent comp = new InterpretedComponent(null, new VarType[]{null});
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwPollEvents();
            EntityManager.updateSystems();
            window.swapBuffers();
        }
        glfwTerminate();
        window.destroy();
    }

    static void run() {
        while (!window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwPollEvents();
            EntityManager.updateSystems();
            window.swapBuffers();
        }
        glfwTerminate();
        window.destroy();
    }
}
