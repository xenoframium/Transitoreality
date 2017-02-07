package xenoframium.transitoreality.gl;

import java.io.File;

public class ShaderIOException extends RuntimeException{
	private static final long serialVersionUID = -6798117422695981246L;

	public ShaderIOException(File missingShader) {
		super("IOException while reading shader at: " + missingShader.getAbsolutePath());
	}
}
