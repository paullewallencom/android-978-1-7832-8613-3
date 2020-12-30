package com.opengltutorials.breakout;

import static android.opengl.GLES20.*;

public class TextureShaderProgram extends LightingProgram{

	private final int uTexUnitLocation;
	private final int aTexCoordLocation;
	
	static final String vertexShaderSource = 
	"attribute vec3 a_Pos; 							\n"
+ 	"attribute vec3 a_Normal;							\n"	
+	"attribute vec2 a_TexCoord;		\n"

+   "uniform mat4 u_WMatrix;							\n"
+	"uniform mat4 u_WVPMatrix;							\n"

+	"varying vec4 v_Pos;							\n"
+	"varying vec4 v_Normal;							\n"	
+	"varying vec2 v_TexCoord;			\n"

+ 	"void main() 									\n "
+ 	"{ 										\n"
+	"	v_Pos = vec4(a_Pos, 1.0) * u_WMatrix;				\n"
+	"	v_Normal = vec4(a_Normal, 0.0) * u_WMatrix;				\n"
+	"	v_TexCoord = a_TexCoord;								\n"
+	"	gl_Position = vec4(a_Pos, 1.0) * u_WVPMatrix;			\n"
+ 	"}";




	static final String fragmentShaderSource =
			"precision mediump float; 		\n"

		+	"varying vec4 v_Pos;		\n"	
		+	"varying vec4 v_Normal;		\n"
		+	"varying vec2 v_TexCoord; 			\n"
		
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
		
		+	"uniform sampler2D u_TexUnit;		\n"
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
		+	"	gl_FragColor = texture2D(u_TexUnit, v_TexCoord) * TotalLight; \n"
		+	"}";
	
	public TextureShaderProgram() {
		super(vertexShaderSource, fragmentShaderSource);
		
		uTexUnitLocation = glGetUniformLocation(program, U_TEXUNIT);
		aTexCoordLocation = glGetAttribLocation(program, A_TEXCOORD);

	}

	public void setUniform(Matrix4f world, Matrix4f WVP, int texID) {
		setUniform(world, WVP);
			
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texID);
			
		glUniform1i(uTexUnitLocation, 0);
	}

	public int getTexCoordLocation() {
		return aTexCoordLocation;
	}

}
