package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;
import android.util.Log;

public class ShaderHelper {

	// Compiles the vertex shader
	public static int compileVertexShader(String shader) {
		return compileShader(GL_VERTEX_SHADER, shader);
	}
		
	// Compiles the fragment shader
	public static int compileFragmentShader(String shader) {
		return compileShader(GL_FRAGMENT_SHADER, shader);
	}

	// Compiles a shader
	private static int compileShader(int type, String shader) {
		// First create the shader
		final int shaderObject = glCreateShader(type);

		// If this goes wrong return 0
		if(shaderObject == 0) {
			return 0;
		}
		
		// Add the source and compile it
		glShaderSource(shaderObject, shader);
		glCompileShader(shaderObject);

		// Check the status of the compile
		final int[] compileStatus = new int[1];
		glGetShaderiv(shaderObject, GL_COMPILE_STATUS, compileStatus, 0);
				
		// If something went wrong return 0
		if(compileStatus[0] == 0) {
			Log.v("Shader", "Results of compiling source:" +"\n:"
					+ glGetShaderInfoLog(shaderObject));
			glDeleteShader(shaderObject);
			
			return 0;
		}
				
		return shaderObject;

	}
	
	// Link the two shadrs together
	public static int linkProgram(int vertexShader, int fragmentShader) {
		// Create the shader program
		int programObject = glCreateProgram();
		if(programObject == 0) {
			return 0;
		}

		// Attach the shaders to the program
		glAttachShader(programObject, vertexShader);
		glAttachShader(programObject, fragmentShader);
				
		// Link the shader
		glLinkProgram(programObject);

		// Check the link status and return 0 if something went wrong
		final int[] linkStatus = new int[1];
		glGetProgramiv(programObject, GL_LINK_STATUS, linkStatus, 0);
		if(linkStatus[0] == 0) {
			glDeleteProgram(programObject);
			return 0;
		}
				
		// Return the program
		return programObject;

	}
	
	// Build the program
	public static int build(String vertexShaderCode, String fragmentShaderCode)
	{
		int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderCode);
			
		return linkProgram(vertexShader, fragmentShader);
	}



}
