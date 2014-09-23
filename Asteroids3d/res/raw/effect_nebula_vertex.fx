precision mediump float;
attribute vec3 a_Position;
attribute vec2 a_TexturePosition;

uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform float u_Cos1;
uniform float u_Cos2;
uniform float u_Sin1;
uniform float u_Sin2;

varying vec2 v_TexturePosition1;
varying vec2 v_TexturePosition2;

void main()
{
	vec4 pos = u_ViewMatrix * vec4(a_Position, 1.0);
	pos = u_ProjectionMatrix * pos;
	gl_Position = pos;
	vec2 p = a_TexturePosition - 0.5;
	v_TexturePosition1 = vec2(p.x * u_Cos1 - p.y * u_Sin1, p.x * u_Sin1 + p.y * u_Cos1) + 0.5;
	v_TexturePosition2 = vec2(p.x * u_Cos2 - p.y * u_Sin2, p.x * u_Sin2 + p.y * u_Cos2) + 0.5;
}