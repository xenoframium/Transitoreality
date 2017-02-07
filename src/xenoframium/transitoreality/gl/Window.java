package xenoframium.transitoreality.gl;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import xenoframium.glwrapper.GlfwWindow;
import xenoframium.glwrapper.WindowBuilder;
import xenoframium.glwrapper.WindowHintApplier;

public class Window {
	private static class WindowSizeCallback implements GLFWWindowSizeCallbackI {
		@Override
		public void invoke(long windowId, int newWidth, int newHeight) {
			Window window = Windows.getWindow(windowId);
			window.width = newWidth;
			window.height = newHeight;
		}
	}

	private static class MousePosCallback implements GLFWCursorPosCallbackI {
		@Override
		public void invoke(long windowId, double xPosition, double yPosition) {
			Window window = Windows.getWindow(windowId);
			window.mouseXPos = (float)( 2.0 * xPosition / window.width - 1);
			window.mouseYPos = (float)( - 2.0 * yPosition / window.height + 1);
		}
	}

	private static final WindowSizeCallback windowSizeCallback = new WindowSizeCallback();
	private static final MousePosCallback mousePosCallback = new MousePosCallback();

	private static final WindowHintApplier MAC_OS_X_PRESETS = new WindowHintApplier() {
		@Override
		public void applyWindowHints() {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
			glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
			glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		}
	};

	private static final WindowHintApplier LINUX_PRESETS = new WindowHintApplier() {
		@Override
		public void applyWindowHints() {
			glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
			glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		}
	};

	private static GlfwWindow sharedContext = GlfwWindow.getNullWindow();

	private final GlfwWindow glfwWindow;

	private int width;
	private int height;
	
	private float mouseXPos = 0;
	private float mouseYPos = 0;

	private Window(String title, int width, int height) {
		String os = System.getProperty("os.name").toLowerCase();
		WindowBuilder windowBuilder = new WindowBuilder(title, width, height).setSharedContext(sharedContext);

		this.width = width;
		this.height = height;

		if (os.contains("mac")) {
			windowBuilder.setWindowHintApplier(MAC_OS_X_PRESETS);
		} else if (os.contains("nux") || os.contains("nix")) {
			windowBuilder.setWindowHintApplier(LINUX_PRESETS);
		}

		glfwWindow = windowBuilder.build();

		if (sharedContext == GlfwWindow.getNullWindow()) {
			sharedContext = glfwWindow;
		}

		glfwSetWindowSizeCallback(getId(), windowSizeCallback);
		glfwSetCursorPosCallback(getId(), mousePosCallback);
		
	}
	
	public static Window createWindow(String title, int width, int height) {
		Window window = new Window(title, width, height);
		Windows.addWindow(window);
		window.makeCurrent();
		createCapabilities();
		return window;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public long getId() {
		return glfwWindow.getId();
	}
	
	public float getMouseXPos() {
		return mouseXPos;
	}
	
	public float getMouseYPos() {
		return mouseYPos;
	}
	
	public void makeCurrent() {
		glfwWindow.makeContextCurrent();
	}
}
