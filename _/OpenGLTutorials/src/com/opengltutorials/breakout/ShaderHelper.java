package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;
import android.util.Log;

public class ShaderHelper {

	public static int compileVertexShader(String shader) {
		return compileShader(GL_VERTEX_SHADER, shader);
	}
		
	public static int compileFragmentShader(String shader) {
		return compileShader(GL_FRAGMENT_SHADER, shader);
	}

	private static int compileShader(int type, String shader) {
		final int shaderObject = glCreateShader(type);

		if(shaderObject == 0) {
			return 0;
		}
		
		glShaderSource(shaderObject, shader);
		glCompileShader(shaderObject);

		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObject, GL_COMPILE_STATUS, compileStatus, 0);
				
		if(compileStatus[0] == 0) {
			Log.v("Shader", "Results of compiling source:" +"\n:"
					+ glGetShaderInfoLog(shaderObject));
			glDeleteShader(shaderObject);
			
			return 0;
		}
				
		return shaderObject;

	}
	
	public static int linkProgram(int vertexShader, int fragmentShader) {
		int programObject = glCreateProgram();
		if(programObject == 0) {
			return 0;
		}

		glAttachShader(programObject, vertexShader);
		glAttachShader(programObject, fragmentShader);
				
		glLinkProgram(programObject);

		final int[] linkStatus = new int[1];
		glGetProgramiv(programObject, GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == 0) {
			glDeleteProgram(programObject);
			return 0;
		}
				
		return programObject;

	}
	
	public static int build(String vertexShaderCode, String fragmentShaderCode)
	{
		int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
			
		return linkProgram(vertexShader, fragmentShader);
	}



}
