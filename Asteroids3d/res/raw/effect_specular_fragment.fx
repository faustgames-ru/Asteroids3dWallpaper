precision mediump float;
uniform sampler2D u_Diffuse;
uniform sampler2D u_Glow;

uniform vec4 u_SpecularLight;
uniform vec4 u_DiffuseLight;
uniform vec4 u_AmbientLight;

varying vec2 v_TexturePosition;
varying float v_LamberFactor;
varying float v_Glowing;

void main()
{
	float lamberFactor= v_LamberFactor;
	gl_FragColor = u_AmbientLight;
	vec4 diffuseMaterial = texture2D (u_Diffuse, v_TexturePosition);
	vec4 glowMaterial = texture2D (u_Glow, v_TexturePosition);
	gl_FragColor += diffuseMaterial * u_DiffuseLight * lamberFactor;
	gl_FragColor += glowMaterial * v_Glowing * 0.5;
	gl_FragColor[3] = 1.0;
}