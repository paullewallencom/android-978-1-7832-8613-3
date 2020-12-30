package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;

public class LightingProgram extends ShaderProgram {

	// World matrix and World View Projection matrix locations
	private final int uWMatrixLocation;
	private final int uWVPMatrixLocation;
		
	// Locations for position, normals and colour
	private final int aPositionLocation;
	private final int aNormalLocation;
	private final int aColourLocation;

	// Eye position location
	private final int uEyePosLocation;
	
	// Directional light locations
	private final int uDirLightColLocation;
	private final int uDirLightAmbLocation;
	private final int uDirLightDirectLocation;
	private final int uDirLightDiffuseLocation;

	// Specular lighting locations
	private final int uMatSpecLocation;
	private final int uSpecPowLocation;
	
	// Pointlight locations
	private final int uPointlightColLocation;
	private final int uPointlightAmbLocation;
	private final int uPointlightDiffuseLocation;
	private final int uPointlightPositionLocation;
	private final int uPointlightAttenuationLocation;
	private final int uPointlightFalloffLocation;
	
	// Spotlight locations
	private final int uSpotlightColLocation;
	private final int uSpotlightAmbLocation;
	private final int uSpotlightDiffuseLocation;
	private final int uSpotlightPositionLocation;
	private final int uSpotlightAttenuationLocation;
	private final int uSpotlightFalloffLocation;
	private final int uSpotlightDirectionLocation;
	private final int uSpotlightCutoffLocation;

	
	// Constructor that compiles the shaders and retrieves attribute locations
	protected LightingProgram(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
		
		// Get matrix locations
		uWMatrixLocation = glGetUniformLocation(program, U_WMATRIX);
		uWVPMatrixLocation = glGetUniformLocation(program, U_WVPMATRIX);
				
		// Get vertex data locations
		aPositionLocation = glGetAttribLocation(program, A_POSITION);
		aNormalLocation = glGetAttribLocation(program, A_NORMAL);
		aColourLocation = glGetAttribLocation(program, A_COLOUR);

		// Get eye position
		uEyePosLocation = glGetUniformLocation(program, U_EYEPOS);
		
		// Get directional light locations
		uDirLightColLocation = glGetUniformLocation(program, U_DIRLIGHTCOL);
		uDirLightAmbLocation = glGetUniformLocation(program, U_DIRLIGHTAMB);
		uDirLightDirectLocation = glGetUniformLocation(program, U_DIRLIGHTDIR);
		uDirLightDiffuseLocation = glGetUniformLocation(program, U_DIRLIGHTDIFF);
		
		// Get specular lighting locations
		uMatSpecLocation = glGetUniformLocation(program, U_MATSPEC);
		uSpecPowLocation = glGetUniformLocation(program, U_SPECPOW);
		
		// Get pointlight locations
		uPointlightColLocation = glGetUniformLocation(program, U_POINTLIGHTCOL);
		uPointlightAmbLocation = glGetUniformLocation(program, U_POINTLIGHTAMB);
		uPointlightDiffuseLocation = glGetUniformLocation(program, U_POINTLIGHTDIF);
		uPointlightPositionLocation = glGetUniformLocation(program, U_POINTLIGHTPOS);
		uPointlightAttenuationLocation = glGetUniformLocation(program, U_POINTLIGHTATT);
		uPointlightFalloffLocation = glGetUniformLocation(program, U_POINTLIGHTFAL);
		
		// Get spotlight locations
		uSpotlightColLocation = glGetUniformLocation(program, U_SPOTLIGHTCOL);
		uSpotlightAmbLocation = glGetUniformLocation(program, U_SPOTLIGHTAMB);
		uSpotlightDiffuseLocation = glGetUniformLocation(program, U_SPOTLIGHTDIF);
		uSpotlightPositionLocation = glGetUniformLocation(program, U_SPOTLIGHTPOS);
		uSpotlightAttenuationLocation = glGetUniformLocation(program, U_SPOTLIGHTATT);
		uSpotlightFalloffLocation = glGetUniformLocation(program, U_SPOTLIGHTFAL);
		uSpotlightDirectionLocation = glGetUniformLocation(program, U_SPOTLIGHTDIR);
		uSpotlightCutoffLocation = glGetUniformLocation(program, U_SPOTLIGHTCUT);
	}

	// Set the eye position for the shader
	public void setEyePos(Vector3f eye) {
		glUniform3f(uEyePosLocation, eye.x, eye.y, eye.z);
	}
	
	// Set the specular uniform attributes
	public void setSpecular(float m, float p) {
		glUniform1f(uMatSpecLocation, m);
		glUniform1f(uSpecPowLocation, p);
	}
		
	// Set the pointlight's attributes
	public void setPointlight(Pointlight p) {
		glUniform3f(uPointlightColLocation, p.colour.x, p.colour.y, p.colour.z);
		glUniform1f(uPointlightAmbLocation, p.ambientIntens);
		glUniform1f(uPointlightDiffuseLocation, p.diffuseIntens);
		glUniform4f(uPointlightPositionLocation, p.lightPosition.x, p.lightPosition.y, p.lightPosition.z, 1.0f);
		glUniform1f(uPointlightAttenuationLocation, p.lightAtten);
		glUniform1f(uPointlightFalloffLocation, p.lightFalloff);
	}
	
	// Set the spotlight's attributes
	public void setSpotlight(Spotlight s) {
		glUniform3f(uSpotlightColLocation, s.colour.x, s.colour.y, s.colour.z);
		glUniform1f(uSpotlightAmbLocation, s.ambientIntens);
		glUniform1f(uSpotlightDiffuseLocation, s.diffuseIntens);
		glUniform4f(uSpotlightPositionLocation, s.lightPosition.x, s.lightPosition.y, s.lightPosition.z, 1.0f);
		glUniform1f(uSpotlightAttenuationLocation, s.lightAtten);
		glUniform1f(uSpotlightFalloffLocation, s.lightFalloff);
		glUniform4f(uSpotlightDirectionLocation, s.Direction.x, s.Direction.y, s.Direction.z, 1.0f);
		glUniform1f(uSpotlightCutoffLocation, s.cutoff);
	}

	// Set the directional lights attributes
	public void setDirectionalLight(DirectionalLight d) {
		glUniform3f(uDirLightColLocation, d.colour.x, d.colour.y, d.colour.z);
		glUniform1f(uDirLightAmbLocation, d.ambientIntens);
		Vector3f dir = d.direction;
		dir.normalize();
		glUniform4f(uDirLightDirectLocation, dir.x, dir.y, dir.z, 1.0f);
		glUniform1f(uDirLightDiffuseLocation, d.diffuseIntens);
	}

	// Set the world matrix and WVP matrix
	public void setUniform(Matrix4f world, Matrix4f WVP) {
		float[] w = world.convertShader();
		float[] wvp = WVP.convertShader();
				
		glUniformMatrix4fv(uWMatrixLocation, 1, false, w, 0);
		glUniformMatrix4fv(uWVPMatrixLocation, 1, false, wvp, 0);
				
	}

	// Get the position attribute location
	public int getPositionAttribLocation() {
		return aPositionLocation;
	}

	// Get the colour attribute location
	public int getColourAttribLocation() {
		return aColourLocation;
	}

	// Get the normal attribute location
	public int getNormalAttribLocation() {
		return aNormalLocation;
	}

}
