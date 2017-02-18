package xenoframium.transitoreality.gl;

import xenoframium.glwrapper.GlShader;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;

public class FragmentShader {
    public static final FragmentShader TEXTURED_FRAGMENT_SHADER = new FragmentShader(new File("shaders/texturedFragmentShader.glsl"));

    private final String shaderPath;

    private final GlShader shader;

    public FragmentShader(File shaderFile) {
        shaderPath = shaderFile.getAbsolutePath();

        try {
            shader = new GlShader(GL_FRAGMENT_SHADER, shaderFile);
        } catch (IOException exception) {
            throw new ShaderIOException(shaderFile);
        }
    }

    public String getPath() {
        return shaderPath;
    }

    GlShader getShader() {
        return shader;
    }
}
