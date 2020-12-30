package com.android.opengltutorialsection1;

import static android.opengl.GLES20.*;

abstract class ShaderProgram {
	protected static final String U_WMATRIX = "u_WMatrix";
	protected static final String U_WVPMATRIX = "u_WVPMatrix";
	
	protected static final String A_POSITION = "a_Pos";
	protected static final String A_NORMAL = "a_Normal";
	protected static final String A_COLOUR = "a_Colour";
	
	protected static final String U_EYEPOS = "u_EyePos";
	
	protected static final String U_DIRLIGHTCOL = "gDirectionalLight.Base.Colour";
	protected static final String U_DIRLIGHTAMB = "gDirectionalLight.Base.AmbientIntensity";
	protected static final String U_DIRLIGHTDIR = "gDirectionalLight.Direction";
	protected static final String U_DIRLIGHTDIFF = "gDirectionalLight.Base.DiffuseIntensity";
	
	protected static final String U_POINTLIGHTCOL = "gPointlight.Base.Colour";
	protected static final String U_POINTLIGHTAMB = "gPointlight.Base.AmbientIntensity";
	protected static final String U_POINTLIGHTDIF = "gPointlight.Base.DiffuseIntensity";
	protected static final String U_POINTLIGHTPOS = "gPointlight.Position";
	protected static final String U_POINTLIGHTATT = "gPointlight.Atten";
	protected static final String U_POINTLIGHTFAL = "gPointlight.Falloff";
	
	protected static final String U_SPOTLIGHTCOL = "gSpotlight.Base.Base.Colour";
	protected static final String U_SPOTLIGHTAMB = "gSpotlight.Base.Base.AmbientIntensity";
	protected static final String U_SPOTLIGHTDIF = "gSpotlight.Base.Base.DiffuseIntensity";
	protected static final String U_SPOTLIGHTPOS = "gSpotlight.Base.Position";
	protected static final String U_SPOTLIGHTATT = "gSpotlight.Base.Atten";
	protected static final String U_SPOTLIGHTFAL = "gSpotlight.Base.Falloff";
	protected static final String U_SPOTLIGHTDIR = "gSpotlight.Direction";
	protected static final String U_SPOTLIGHTCUT = "gSpotlight.Cutoff";
	
	protected static final String U_MATSPEC = "gMatSpecularIntensity";
	protected static final String U_SPECPOW = "gSpecularPower";
	
	protected static final String A_TEXCOORD = "a_TexCoord";
		
	protected static final String U_TEXUNIT = "a_TexUnit";
	
	protected static final String U_TIME = "u_Time";
	protected static final String A_DIRECTION = "a_Direction";
	protected static final String A_STARTTIME = "a_StartTime";
	
	protected final int program;
	
	protected ShaderProgram(String vertexShader, String fragmentShader) {
		program = ShaderHelper.build(vertexShader, fragmentShader);
	}
	
	public void useProgram() {
		glUseProgram(program);
	}
}
