package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;

public abstract class ShaderProgram {
	// Name of the matrices in the shader
	protected static final String U_WMATRIX = "u_WMatrix";
	protected static final String U_WVPMATRIX = "u_WVPMatrix";
		
	// Name of position, normal and colour attributes in the shader
	protected static final String A_POSITION = "a_Pos";
	protected static final String A_NORMAL = "a_Normal";
	protected static final String A_COLOUR = "a_Colour";

	// Name of eye position in shader
	protected static final String U_EYEPOS = "u_EyePos";
	
	// Name of directional light attributes in the shader
	protected static final String U_DIRLIGHTCOL = "gDirectionalLight.Base.Colour";
	protected static final String U_DIRLIGHTAMB = "gDirectionalLight.Base.AmbientIntensity";
	protected static final String U_DIRLIGHTDIR = "gDirectionalLight.Direction";
	protected static final String U_DIRLIGHTDIFF = "gDirectionalLight.Base.DiffuseIntensity";

	// Specular attribute names in the shader
	protected static final String U_MATSPEC = "gMatSpecularIntensity";
	protected static final String U_SPECPOW = "gSpecularPower";
	
	// Name of pointlight attributes in the shader
	protected static final String U_POINTLIGHTCOL = "gPointlight.Base.Colour";
	protected static final String U_POINTLIGHTAMB = "gPointlight.Base.AmbientIntensity";
	protected static final String U_POINTLIGHTDIF = "gPointlight.Base.DiffuseIntensity";
	protected static final String U_POINTLIGHTPOS = "gPointlight.Position";
	protected static final String U_POINTLIGHTATT = "gPointlight.Atten";
	protected static final String U_POINTLIGHTFAL = "gPointlight.Falloff";

	// Name of spotlight attributes in the shader
	protected static final String U_SPOTLIGHTCOL = "gSpotlight.Base.Base.Colour";
	protected static final String U_SPOTLIGHTAMB = "gSpotlight.Base.Base.AmbientIntensity";
	protected static final String U_SPOTLIGHTDIF = "gSpotlight.Base.Base.DiffuseIntensity";
	protected static final String U_SPOTLIGHTPOS = "gSpotlight.Base.Position";
	protected static final String U_SPOTLIGHTATT = "gSpotlight.Base.Atten";
	protected static final String U_SPOTLIGHTFAL = "gSpotlight.Base.Falloff";
	protected static final String U_SPOTLIGHTDIR = "gSpotlight.Direction";
	protected static final String U_SPOTLIGHTCUT = "gSpotlight.Cutoff";

	// Name of texture coordinate in the shader
	protected static final String A_TEXCOORD = "a_TexCoord";
	
	// Name of uniform texture in the shader
	protected static final String U_TEXUNIT = "a_TexUnit";
	
	// Names of time, direction and start time in particle shader
	protected static final String U_TIME = "u_Time";
	protected static final String A_DIRECTION = "a_Direction";
	protected static final String A_STARTTIME = "a_StartTime";

	// Shader program
	protected final int program;

	// Constructor that builds the shader
	protected ShaderProgram(String vertexShader, String fragmentShader) {
		program = ShaderHelper.build(vertexShader, fragmentShader);
	}

	// Uses the program
	public void useProgram() {
		glUseProgram(program);
	}

}
