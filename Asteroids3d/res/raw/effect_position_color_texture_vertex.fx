precision mediump float;

uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ModelMatrix;

attribute vec3 a_Position;
attribute vec2 a_TexturePosition;
attribute vec4 a_Color;

varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	v_TexturePosition = a_TexturePosition;
	v_Color = a_Color;

	vec4 world = u_ModelMatrix * vec4(a_Position, 1.0);
	world = u_ViewMatrix * world;
	gl_Position = u_ProjectionMatrix * world;
}