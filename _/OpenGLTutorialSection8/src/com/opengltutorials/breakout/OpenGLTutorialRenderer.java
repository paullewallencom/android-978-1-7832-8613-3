package com.opengltutorials.breakout;

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
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glViewport;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView.Renderer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

public class OpenGLTutorialRenderer implements Renderer {
	// The application context
	private final Context context;

	// The texture shader program
	private TextureShaderProgram texProgram;
	
	// The lighting shader program
	private LightingProgram lightProgram;
	
	// The directional and point light
	private DirectionalLight light;
	private Pointlight pLight;
	
	// The pipeline
	private Pipeline p;
	
	// The camera
	private Camera c;
	
	// The list of explosions
	private List<Explosion> explode;
	
	// The perspective information
	private PerspInfo persp;
	
	// The number of lives
	private int lives = 3;
	
	// The score
	private int score = 0;
	
	// The texture for the brick
	private int texture;
	
	// The array of bricks
	private Brick[] bricks;
	
	// The ball
	private Ball ball;
	
	// The paddle
	private Paddle paddle;
	
	// Elapsed time and current time
	private float elapsedTime;
	private float currentTime;
	
	// Width and height of screen
	private int width;
	private int height;
	
	// The spatial hash grid
	private SpatialHashGrid grid;
	
	// The vertex shader source
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
		// Set the context
		this.context = context;
		
		// Initialize the pipeline
		p = new Pipeline();
		
		// Initialize the camera
		c = new Camera(new Vector3f(0.0f, 0.0f, -150.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector3f(0.0f, 1.0f,0.0f));
		
		// Get the width and height of screen
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		height = metrics.heightPixels;

		// Initialize the perspective information
		persp = new PerspInfo(60.0f, width, height, 0.1f, 3000.0f);
		
		// Set the camera and perspective information in the pipeline
		p.SetCamera(c.mPos, c.mTarget, c.mUp);
		p.SetPersp(persp);
		
		// Create the directional light
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

		// Initialize the spatial hash grid
		grid = new SpatialHashGrid(280, 280, 35, 140, 140);
		
		// Initialize the bricks
		bricks = new Brick[12];
		
		float h = 100;
		float x = -45;
		
		for(int i=0; i<12; i++)
		{
			// Create the array of bricks
			bricks[i] = new Brick(new Vector3f(x, h, 0.0f));
			// Insert the bricks into the grid
			grid.insertStaticEnt(bricks[i]);
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
		
		// Set the current time
		currentTime = System.nanoTime();
		
		// Initialize the list of explosions
		explode = new ArrayList<Explosion>(12);
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
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
				
		// Set the elapsed time and current time
		elapsedTime = (System.nanoTime() - currentTime)/ 1000000000.0f;
		currentTime = System.nanoTime(); 
		
		// Set the position of the ball
		p.WorldPos(ball.getPos());
		// Set the uniform attributes
		lightProgram.setUniform(p.getWorldTrans(), p.getWVPTrans());
		// Set the attribute pointers
		ball.setAttributePoints(lightProgram.getPositionAttribLocation(), lightProgram.getNormalAttribLocation(), lightProgram.getColourAttribLocation());
		// Update the ball
		ball.update(elapsedTime);
		// If the ball hasn't started movement make the ball move with the paddle
		if(!ball.isStart())
			ball.getPos().x = paddle.getPos().x;
		// Draw the ball
		ball.draw();

		// Set the position of the paddle
		p.WorldPos(paddle.getPos());
		// Set the uniform attributes
		lightProgram.setUniform(p.getWorldTrans(), p.getWVPTrans());
		// Set the attribute pointers
		paddle.setAttributePoints(lightProgram.getPositionAttribLocation(), lightProgram.getNormalAttribLocation(), lightProgram.getColourAttribLocation());
		// Update the paddle
		paddle.update(elapsedTime);
		// Draw the paddle
		paddle.draw();
		// Remove shaders
		glUseProgram(0);
		
		// Place the ball and paddle into the dynamic cells
		grid.clearDynamicCells();
		grid.insertDynamicEnt(ball);
		grid.insertDynamicEnt(paddle);

		// Check for collisions with the ball and brick
		List<GameEntity> entities = grid.getPossibleStaticColliders(ball);
		for(int i=0; i<entities.size(); i++) {
			if(isOverlap(ball, entities.get(i))) {
				if(!entities.get(i).isDead()) {
					// Increase the score
					score += 100;
					// Create a new explosion and add it
					Explosion ex = new Explosion(entities.get(i).getPos());
					ex.onCreate();
					explode.add(ex);
					// Set the brick to dead
					entities.get(i).setDead(true);
					// Remove the brick from the grid
					grid.removeStaticEnt(entities.get(i));
					// Set the new velocity for the ball based on where it hit the brick
					if(ball.getPos().y < entities.get(i).getPos().y - entities.get(i).getHeight()/2) {
						ball.setVel(new Vector3f(ball.getVel().x, -ball.getVel().y, ball.getVel().z));
					}
					else if(ball.getPos().y > entities.get(i).getPos().y + entities.get(i).getHeight()/2) {
						ball.setVel(new Vector3f(ball.getVel().x, -ball.getVel().y, ball.getVel().z));
					}
					if(ball.getPos().x < entities.get(i).getPos().x - entities.get(i).getWidth()/2) {
						ball.setVel(new Vector3f(-ball.getVel().x, ball.getVel().y, ball.getVel().z));
					}
					else if(ball.getPos().x > entities.get(i).getPos().x + entities.get(i).getWidth()/2) {
						ball.setVel(new Vector3f(-ball.getVel().x, ball.getVel().y, ball.getVel().z));
					}
				}
				break;
			}
		}
		
		// Now check for collisions with the paddle
		entities = grid.getPossibleDynamicColliders(ball);
		for(int i=0; i<entities.size(); i++) {
			// If it collides, change the direction
			if(isOverlap(ball, entities.get(i))) {
				if(ball.getPos().y > entities.get(i).getPos().y && ball.getVel().y < 0) {
					ball.getVel().y = -ball.getVel().y;
				}
				if(ball.getPos().x > entities.get(i).getPos().x && ball.getVel().x < 0) {
					ball.getVel().x = -ball.getVel().x;
				}
				if(ball.getPos().x < entities.get(i).getPos().x && ball.getVel().x > 0) {
					ball.getVel().x = -ball.getVel().x;
				}
			}
		}
		
		// If the ball goes past the bottom of the screen
		if(ball.getPos().y < -140) {
			// Set the start to false
			ball.setStart(false);
			// Decrease lives
			lives--;
			// Set the ball's position to the paddle and reverse the velocity
			ball.setPos(new Vector3f(paddle.getPos().x, paddle.getPos().y + 15, paddle.getPos().z));
			if(ball.getVel().y < 0) {
				ball.getVel().y = -ball.getVel().y;
			}
		}
		
		// Render the explosions
		for(int i=0; i<explode.size(); i++) {
			p.WorldPos(new Vector3f(0.0f, 0.0f, 110.0f));
			explode.get(i).render(p.getWVPTrans());
		}
		
		// Check if all bricks are dead
		boolean allDead = true;
		for(int i=0; i<bricks.length; i++) {
			if(!bricks[i].isDead()) {
				allDead = false;
			}
		}
		// If the bricks are dead load the Complete screen
		if(allDead) {
			final Intent gameComplete = new Intent(context, GameCompleteActivity.class);
			gameComplete.putExtra("com.OpenGLProject.data", score);
			context.startActivity(gameComplete);
		}

		// If the player is out of lives, go to Game Over screen
		if(lives == 0) {
			final Intent gameOver = new Intent(context, GameOver.class);
			context.startActivity(gameOver);
		}

	}
	
	public void onTouchEvent (MotionEvent e) {
		float x = e.getX();

		// Move the paddle based on touch movement
		switch(e.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			if(!ball.isStart())
				ball.setStart(true);
			x = ((x / width) * 140) - 70;
			Vector3f newpos = new Vector3f(x, -130.0f, 0.0f);
			paddle.setPos(newpos);

			break;
		}
			
	}
	
	// Set the acceleration values on the paddle
	public void setAccelVals(float x) {
		paddle.setVelX(x);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		glViewport(0,0,width,height);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); 
		glClearDepthf(1.0f);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		
		// Initialize the texture and lighting shader programs
		texProgram = new TextureShaderProgram();
		lightProgram = new LightingProgram(vertexShaderSource, fragmentShaderSource);

		// Load the texture
		texture = Texture.loadTexture(context, R.drawable.brick);

	}
	
	// Get the number of lives
	public int getLives() {
		return lives;
	}
	
	// Get the score
	public int getScore() {
		return score;
	}
	
	// Check for collisions
	public boolean isOverlap(GameEntity first, GameEntity second) {
		// Get the lower coordinates of the bounding boxes
		float firstLowerLeftX = first.getPos().x - (first.getWidth()/2);
		float firstLowerLeftY = first.getPos().y - (first.getHeight()/2);
		float secondLowerLeftX = second.getPos().x - (second.getWidth()/2);
		float secondLowerLeftY = second.getPos().y - (second.getHeight() / 2);
			
		// Check for collisions by check all boundaries
		if(firstLowerLeftX < secondLowerLeftX + second.getWidth() &&
		   firstLowerLeftX + first.getWidth() > secondLowerLeftX &&
		   firstLowerLeftY < secondLowerLeftY + second.getHeight() &&
		   firstLowerLeftY + first.getHeight() > secondLowerLeftY)
			return true;
		else
			return false;
	}

	// Set the extra lives
	public void setExtraLives(int l) {
		lives += l;
	}

}
