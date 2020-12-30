package com.android.opengltutorialsection1;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_TEST;
import static android.opengl.GLES20.GL_LESS;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClearDepthf;
import static android.opengl.GLES20.glDepthFunc;
import static android.opengl.GLES20.glEnable;
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
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class OpenGLTutorialRenderer implements Renderer {

	private final Context context;
	
	private static final int FLOAT_BYTES = 4;
	private final FloatBuffer vertexData;
	
	private int aPositionLocation;
	private int aTexCoordLocation;
	private int aNormalLocation;
	
	private int texture;
	
	private Brick[] bricks;
	
	// The ball
	private Ball ball;
	
	// The paddle
	private Paddle paddle;
	
	private static final int stride = 8 * FLOAT_BYTES;
	
	private DirectionalLight light;
	private Pointlight pLight;
	private TextureShaderProgram texProgram;
	// The lighting shader program
	private LightingProgram lightProgram;
	private Pipeline p;
	private Camera c;
	private PerspInfo persp;
	
	// The number of lives
	private int lives = 3;
	
	// The score
	private int score = 0;
	
	private Explosion explosion;
	
	static final String vertexShaderSource = 
	"attribute vec3 a_Pos; 							\n"
+	"attribute vec4 a_Colour;							\n"
+ 	"attribute vec3 a_Normal;							\n"		

+   	"uniform mat4 u_WMatrix;							\n"
+	"uniform mat4 u_WVPMatrix;							\n"

+	"varying vec4 v_Pos;							\n"
+	"varying vec4 v_Normal;							\n"	
+	"varying vec4 v_Colour;							\n"	

+ 	"void main() 												\n"
+ 	"{ 															\n"
+	"	v_Pos = vec4(a_Pos, 1.0) * u_WMatrix;					\n"
+	"	v_Normal = vec4(a_Normal, 0.0) * u_WMatrix;				\n"
+	"	v_Colour = a_Colour;									\n"
+	"	gl_Position = vec4(a_Pos, 1.0) * u_WVPMatrix;			\n"
+ 	"}";

	// The fragment shader source
	static final String fragmentShaderSource =
			"precision mediump float; 		\n"

		+	"varying vec4 v_Colour; 		\n"
		+	"varying vec4 v_Pos;		\n"	
		+	"varying vec4 v_Normal;		\n"
		
		+	"struct Baselight					\n"
		+	"{									\n"
		+	"	vec3 Colour;					\n"
		+	"	float AmbientIntensity;			\n"
		+	"	float DiffuseIntensity;			\n"
		+	"};									\n"
		
		+	"struct DirectionalLight			\n"
		+	"{									\n"
		+	"	Baselight Base;					\n"
		+	"	vec4 Direction;					\n"
		+	"};									\n"
		
		+	"struct Pointlight					\n"
		+	"{									\n"
		+	"	Baselight Base;					\n"
		+	"	vec4 Position;					\n"
		+	"	float Atten;					\n"
		+	"	float Falloff;					\n"
		+	"};									\n"

		+	"struct Spotlight					\n"
		+	"{									\n"
		+	"	Pointlight Base;				\n"
		+	"	vec4 Direction;					\n"
		+	"	float Cutoff;					\n"
		+	"};									\n"
		
		+	"uniform DirectionalLight gDirectionalLight;													\n"
		+	"uniform Pointlight gPointlight;																\n"	
		+ 	"uniform Spotlight gSpotlight;																	\n"
		+ 	"uniform vec4 u_EyePos;																			\n"
		+	"uniform float gMatSpecularIntensity;															\n"
		+	"uniform float gSpecularPower;																	\n"

		+	"vec4 CalcLightInternal(Baselight light, vec4 LightDir, vec4 Normal)							\n"	
		+	"{																								\n"
		+	"	vec4 AmbientColour = vec4(light.Colour, 1.0);						\n"
		+	"	vec4 dir = LightDir; 														\n"
		+	"	float DiffuseFactor = dot(Normal, -dir);													\n"
		+	"	vec4 DiffuseColour = vec4(0, 0, 0, 0);														\n"
		+	"	vec4 SpecularColour = vec4(0, 0, 0, 0);														\n"
		+	"	if(DiffuseFactor > 0.0) {																	\n"
		+	"		DiffuseColour = vec4(light.Colour, 1.0) * light.DiffuseIntensity * DiffuseFactor;		\n"
		+	"		vec4 vertexToEye = normalize(u_EyePos - v_Pos);											\n"
		+	"		vec4 LightReflect = normalize(reflect(dir, Normal));									\n"
		+	"		float SpecularFactor = dot(vertexToEye, LightReflect);									\n"
		+	"		SpecularFactor = pow(SpecularFactor, gSpecularPower);									\n"
		+	"		if(SpecularFactor > 0.0) {																\n"
		+	"			SpecularColour = vec4(light.Colour, 1.0) * gMatSpecularIntensity;					\n"
		+	"		}																						\n"
		+	"	}																							\n"
		+	"	return AmbientColour + DiffuseColour + SpecularColour;										\n"
		+	"}														\n"
		
		+	"vec4 CalcPointlight(Pointlight l, vec4 Normal)	\n"
		+	"{		\n"
		+	"	vec4 lightDir = v_Pos - l.Position;		\n"
		+	"	float distance = length(lightDir); \n"
		+	"	lightDir = normalize(lightDir);	\n"
		+	"	vec4 Colour = CalcLightInternal(l.Base, lightDir, Normal);	\n"
		+	"	float Attenuation = 1.0 - pow(clamp(distance / l.Atten, 0.0, 1.0), l.Falloff);	\n"
		+	"	return Colour * Attenuation;	\n"
		+	"}																								\n"

		+	"vec4 CalcSpotlight(Spotlight l, vec4 Normal)													\n"
		+	"{																								\n"
		+	"	vec4 lightToPixel = normalize(v_Pos - l.Base.Position);										\n"
		+	"	vec4 dir = normalize(l.Direction - l.Base.Position);															\n"
		+	"	float SpotFactor = dot(lightToPixel, dir);\n"
		+	"	float halfAngle = radians(l.Cutoff/2.0);														\n"
		+	"	float Sf = acos(SpotFactor);"
		+	"	if(Sf < halfAngle) {																	\n"
		+	"		vec4 Colour = CalcPointlight(l.Base, Normal);											\n"
		+	"		return Colour * pow(SpotFactor, l.Base.Falloff);						\n"
		+	"	}																								\n"
		+	"	else {																						\n"
		+	"		return vec4(0.0, 0.0, 0.0, 0.0);														\n"
		+	"	}																							\n"
		+	"}																								\n"
		
		+	"void main() 				\n"
		+	"{ 					\n"
		+	"	vec4 Normal = normalize(v_Normal);																		\n"
		+	"	vec4 TotalLight = CalcLightInternal(gDirectionalLight.Base, gDirectionalLight.Direction, Normal);		\n"
		+	"	TotalLight += CalcPointlight(gPointlight, Normal);														\n"
		+	"	TotalLight += CalcSpotlight(gSpotlight, Normal); 														\n"
		+	"	gl_FragColor = v_Colour * TotalLight;		\n"

		+	"}";
	
	
	public OpenGLTutorialRenderer(Context context) {
		
		this.context = context;
		
		float[] squareVerts = {
			-10f, -10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 1.0f,
			10f, -10f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 1.0f,
			-10f, 10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f,
			-10f, 10f, 100.0f,		0.0f, 0.0f, -1.0f,		0.0f, 0.0f,
			10f, -10f, 100.0f, 		0.0f, 0.0f, -1.0f, 		1.0f, 1.0f,
			10f, 10f, 100.0f, 		0.0f, 0.0f, -1.0f,		1.0f, 0.0f,
		};
		 
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
			
		p = new Pipeline();
		// Initialize the camera
		c = new Camera(new Vector3f(0.0f, 0.0f, -150.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0.0f, 1.0f,0.0f));
		persp = new PerspInfo(60.0f, width, height, 0.1f, 3000.0f);
			
		p.SetCamera(c.mPos, c.mTarget, c.mUp);
		p.SetPersp(persp);
		//p.Scale(new Vector3f(0.5f, 0.5f, 0.5f));
			
		light = new DirectionalLight();
		light.ambientIntens = 0.1f;
		light.colour = new Vector3f(1.0f, 1.0f, 1.0f);
		light.diffuseIntens = 0.1f;
		light.direction = new Vector3f(1.0f, 0.0f, 1.0f);
			
		// Create the pointlight
		pLight = new Pointlight();
		pLight.ambientIntens = 0.1f;
		pLight.diffuseIntens = 0.1f;
		pLight.colour = new Vector3f(1.0f, 1.0f, 1.0f);
		pLight.lightPosition = new Vector3f(20.0f, -10.0f, 0.0f);
		pLight.lightAtten = 110.0f;
		pLight.lightFalloff = 1.0f;
		
		explosion = new Explosion(new Vector3f(0, 0, 50));
		
		// Initialize the bricks
		bricks = new Brick[12];
				
		float h = 100;
		float x = -45;
		
		for(int i=0; i<12; i++)
		{
			// Create the array of bricks
			bricks[i] = new Brick(new Vector3f(x, h, 0.0f));
			// Insert the bricks into the grid
			x += 30;
			if(x > 45) {
				h -= 30;
				x = -45;
			}
		}
				
		// Initialize the ball
		ball = new Ball(new Vector3f(0.0f, -120.0f, 0.0f));
		
		// Initialize the paddle
		paddle = new Paddle(new Vector3f(0.0f, -130.0f, 0.0f));
		
		vertexData = ByteBuffer.allocateDirect(squareVerts.length * FLOAT_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
			
		vertexData.put(squareVerts);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
		glClearDepthf(1.0f);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		
		texProgram = new TextureShaderProgram();
		lightProgram = new LightingProgram(vertexShaderSource, fragmentShaderSource);
		
		texture = Texture.loadTexture(context, R.drawable.brick);
		
		
		explosion.onCreate();
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		glClear(GL_COLOR_BUFFER_BIT);
		glClear(GL_DEPTH_BUFFER_BIT);
		
		// Set the texture shader program and attributes
		texProgram.useProgram();
		texProgram.setDirectionalLight(light);
		texProgram.setPointlight(pLight);
		texProgram.setEyePos(c.mPos);
		texProgram.setSpecular(0, 0);
		
		// Draw all bricks
		for(int i=0; i<12; i++)
		{	
			// Set the position of the bricks
			p.WorldPos(bricks[i].getPos());
			// Set the uniform attributes
			texProgram.setUniform(p.getWorldTrans(), p.getWVPTrans(), texture);
			// Set the attribute pointers
			bricks[i].setAttributePoints(texProgram.getPositionAttribLocation(), texProgram.getNormalAttribLocation(), texProgram.getTexCoordLocation());
			// Draw each brick
			bricks[i].draw(p.getWVPTrans());
		}
		
		//  Set the lighting shader program and attributes
		lightProgram.useProgram();
		lightProgram.setDirectionalLight(light);
		lightProgram.setPointlight(pLight);
		lightProgram.setEyePos(c.mPos);
		lightProgram.setSpecular(0, 0);
		
		// Set the position of the ball
		p.WorldPos(ball.getPos());
		// Set the uniform attributes
		lightProgram.setUniform(p.getWorldTrans(), p.getWVPTrans());
		// Set the attribute pointers
		ball.setAttributePoints(lightProgram.getPositionAttribLocation(), lightProgram.getNormalAttribLocation(), lightProgram.getColourAttribLocation());
		// Draw the ball
		ball.draw();

		// Set the position of the paddle
		p.WorldPos(paddle.getPos());
		// Set the uniform attributes
		lightProgram.setUniform(p.getWorldTrans(), p.getWVPTrans());
		// Set the attribute pointers
		paddle.setAttributePoints(lightProgram.getPositionAttribLocation(), lightProgram.getNormalAttribLocation(), lightProgram.getColourAttribLocation());
		// Draw the paddle
		paddle.draw();
		// Remove shaders
		glUseProgram(0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		// Set viewport to fill surface
		glViewport(0,0,width,height);
	}
	
	// Get the number of lives
	public int getLives() {
		return lives;
	}
		
	// Get the score
	public int getScore() {
		return score;
	}

}
