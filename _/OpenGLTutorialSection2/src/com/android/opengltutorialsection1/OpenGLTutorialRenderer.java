package com.android.opengltutorialsection1;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glDrawArrays;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;


public class OpenGLTutorialRenderer implements Renderer {

	private final Context context;
	
	private static final int FLOAT_BYTES = 4;
	private final FloatBuffer vertexData;
	
	private int program;
	
	private static final String U_COLOUR = "u_Colour";
	private int uColourLocation;
	
	private static final String A_POS = "a_Pos";
	private int aPositionLocation;
	
	
	String vertexShaderSource = 
	"attribute vec4 a_Pos; 		\n"

+ 	"void main() 				\n "
+ 	"{ 							\n"	
+ 	"gl_Position = a_Pos;		\n"
+ 	"}";
	
	String fragmentShaderSource =
	"precision mediump float; 		\n"

+	"uniform vec4 u_Colour; 		\n"

+	"void main() 					\n"
+	"{ 								\n"
+		"gl_FragColor = u_Colour;	\n"
+	"}";
	
	
	public OpenGLTutorialRenderer(Context context) {
		
		this.context = context;
		
		float[] squareVerts = {
			-0.5f, -0.5f,
			0.5f, -0.5f,
			-0.5f, 0.5f,
			-0.5f, 0.5f,
			0.5f, -0.5f,
			0.5f, 0.5f
		};
		
		vertexData = ByteBuffer.allocateDirect(squareVerts.length * FLOAT_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		
		vertexData.put(squareVerts);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
		
		int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
		int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
		
		program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
		glUseProgram(program);
		
		uColourLocation = glGetUniformLocation(program, U_COLOUR);
		
		aPositionLocation = glGetAttribLocation(program, A_POS);
		
		vertexData.position(0);
		glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData);
		glEnableVertexAttribArray(aPositionLocation);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		// Clear rendering surface
		glClear(GL_COLOR_BUFFER_BIT);
		glUniform4f(uColourLocation, 1.0f, 0.0f, 0.0f, 1.0f);
		glDrawArrays(GL_TRIANGLES, 0, 6);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Set viewport to fill surface
		glViewport(0,0,width,height);
	}

}
