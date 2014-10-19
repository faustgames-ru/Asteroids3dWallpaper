precision mediump float;

uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ModelMatrix;
uniform vec4 u_CloudsColor0;
uniform vec4 u_CloudsColor1;

attribute vec3 a_Position;
attribute vec2 a_TexturePosition;
attribute vec4 a_CloudsColor0;
attribute vec4 a_CloudsColor1;
attribute vec4 a_StartsColor;
attribute float a_Alpha;

varying vec2 v_TexturePosition2;
varying vec4 v_CloudsColor0;
varying vec4 v_CloudsColor1;
varying vec4 v_StartsColor;
varying float v_Alpha;

void main()
{
	v_TexturePosition2 = a_TexturePosition * 4.0;

	v_CloudsColor0 = a_CloudsColor0 * u_CloudsColor0;
	v_CloudsColor1 = a_CloudsColor1 * u_CloudsColor1;
	v_StartsColor = a_StartsColor;
	v_Alpha = a_Alpha;

	vec4 world = u_ModelMatrix * vec4(a_Position, 1.0);
	world = u_ViewMatrix * world;
	gl_Position = u_ProjectionMatrix * world;
}