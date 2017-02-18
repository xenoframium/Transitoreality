package xenoframium.transitoreality.gl;

import xenoframium.glwrapper.GlShader;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

public class VertexShader {
    public static final VertexShader TEXTURED_VERTEX_SHADER = new VertexShader(new File("shaders/texturedVertexShader.glsl"));

    private final String shaderPath;

    private final GlShader shader;

    public VertexShader(File shaderFile) {
        shaderPath = shaderFile.getAbsolutePath();

        try {
            shader = new GlShader(GL_VERTEX_SHADER, shaderFile);
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

