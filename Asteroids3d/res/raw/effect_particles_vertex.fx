precision mediump float;
attribute vec3 a_Position;
attribute vec3 a_ScreenOffset;
attribute vec2 a_TexturePosition;
attribute float a_TransformIndex;

uniform float u_Aspect;
uniform vec4 u_Color;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ModelMatrix[32];

varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	int transformIndex = int(a_TransformIndex);
	vec4 modelpos = u_ModelMatrix[transformIndex] * vec4(a_Position, 1.0);
	vec4 pos = u_ViewMatrix * modelpos + vec4( a_ScreenOffset.x,  a_ScreenOffset.y * u_Aspect, 0.0, 0.0);
	gl_Position = u_ProjectionMatrix * pos;
	v_TexturePosition = a_TexturePosition;
	v_Color = u_Color;
	v_Color.a = 1.0;//(8.0 - abs(pos.z)) * 0.5;
}