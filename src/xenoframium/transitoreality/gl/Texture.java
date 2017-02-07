package xenoframium.transitoreality.gl;

import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import xenoframium.glwrapper.GlTexture;

//Texture class is immutable
public class Texture {
	private final int width;
	private final int height;
	
	private final GlTexture glTexture;

	public Texture(boolean hasAlpha, File textureFile) {
		IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
		IntBuffer compBuffer = BufferUtils.createIntBuffer(1);

		int rgbFormat;

		if (!hasAlpha) {
			rgbFormat = STBImage.STBI_rgb;
		} else {
			rgbFormat = STBImage.STBI_rgb_alpha;
		}

		FloatBuffer fBuffer = STBImage.stbi_loadf(textureFile.getAbsolutePath(), widthBuffer, heightBuffer, compBuffer,
				rgbFormat);

		width = widthBuffer.get(0);
		height = heightBuffer.get(0);

		int composition;
		
		switch (compBuffer.get(0)) {
		case STBImage.STBI_rgb:
			composition = GL_RGB;
			break;
		case STBImage.STBI_rgb_alpha:
			composition = GL_RGBA;
			break;
		default:
			composition = -1;
			break;
		}

		glTexture = new GlTexture();
		glTexture.bind(GL_TEXTURE_2D);
		
		glTexImage2D(GL_TEXTURE_2D, 0, composition, width, height, 0, composition, GL_FLOAT, fBuffer);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	}
	
	public void bind() {
		glTexture.bind(GL_TEXTURE_2D);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
