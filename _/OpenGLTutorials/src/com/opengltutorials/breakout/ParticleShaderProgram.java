package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;

public class ParticleShaderProgram extends ShaderProgram{

	private final int uWVPMatrixLocation;
	private final int uTimeLocation;
		
	private final int aPosLocation;
	private final int aColLocation;
	private final int aDirectionLocation;
	private final int aStartTimeLocation;
			
	private final static String vertexShader = 
	"attribute vec3 a_Pos; 											\n"	
+	"attribute vec3 a_Colour;										\n"
+	"attribute vec3 a_Direction;									\n"
+	"attribute float a_StartTime;									\n"

+	"uniform mat4 u_WVPMatrix;										\n"
+	"uniform float u_Time;											\n"

+	"varying float v_ElapsedTime;									\n"
+	"varying vec3 v_Colour;											\n"

+ 	"void main() 													\n"
+ 	"{ 																\n"
+	"	v_Colour = a_Colour;										\n"
+	"	v_ElapsedTime = u_Time - a_StartTime;						\n"
+	"	float grav = v_ElapsedTime * v_ElapsedTime / 15.0;		\n"
+	"	vec3 curPos = a_Pos + (a_Direction * v_ElapsedTime);		\n"
+	"	curPos.y -= grav;							\n"
+	"	gl_Position = vec4(curPos, 1.0) * u_WVPMatrix;				\n"
+	"	gl_PointSize = 15.0;										\n"
+ 	"}";
	
	private final static String fragmentShader = 
			"precision mediump float;										\n"

		+	"varying vec3 v_Colour;											\n"
		+	"varying float v_ElapsedTime;									\n"

		+	"void main()													\n"
		+	"{																\n"
		+	"	gl_FragColor = vec4(v_Colour / v_ElapsedTime, 1.0);			\n"
		+	"}";
	
	public ParticleShaderProgram() {
		super(vertexShader, fragmentShader);
		
		uWVPMatrixLocation = glGetUniformLocation(program, U_WVPMATRIX);
		uTimeLocation = glGetUniformLocation(program, U_TIME);
			
		aPosLocation = glGetAttribLocation(program, A_POSITION);
		aColLocation = glGetAttribLocation(program, A_COLOUR);
		aDirectionLocation = glGetAttribLocation(program, A_DIRECTION);
		aStartTimeLocation = glGetAttribLocation(program, A_STARTTIME);

	}
	
	public void setUniform(Matrix4f WVP, float time) {
		float[] wvp = WVP.convertShader();
			
		glUniformMatrix4fv(uWVPMatrixLocation, 1, false, wvp, 0);
		glUniform1f(uTimeLocation, time);
	}
	
	public int getPosAttributeLocation() {
		return aPosLocation;
	}
		
	public int getColAttributeLocation() {
		return aColLocation;
	}
		

	public int getDirectionAttributeLocation() {
		return aDirectionLocation;
	}
		
	public int getStartTimeAttributeLocation() {
		return aStartTimeLocation;
	}



}
