precision mediump float;
attribute vec3 a_Position;
attribute vec3 a_ScreenOffset;
attribute vec2 a_TexturePosition;
attribute float a_Offset;
uniform float u_Aspect;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;

varying vec2 v_TexturePosition;
varying vec4 v_Position;
varying vec4 v_PositionCenter;
varying float v_Blend;
varying vec4 v_Color;
void main()
{
	vec4 pos = u_ViewMatrix * vec4(a_Position, 1.0);
	float viewZ = pos.z;
	pos = u_ProjectionMatrix * pos;
	pos /= pos.w;
	float len = sqrt(pos.x*pos.x + pos.y * pos.y);
	float sin = -pos.y / len;
	float cos = pos.x / len;
	
	if (a_ScreenOffset[2] > 0.25) 
		sin = -sin;
	
	float x = cos * a_ScreenOffset.x - sin * a_ScreenOffset.y;
	float y = sin * a_ScreenOffset.x + cos * a_ScreenOffset.y;
	v_PositionCenter = pos * 0.5 + 0.5;
	if (a_Offset < 1.0 ) 
	{
		gl_Position = pos + vec4(pos.x * (-1.0 + a_Offset), pos.y * (-1.0 + a_Offset), 0, 0) + vec4(x * a_Offset, y * a_Offset * u_Aspect, 0.0, 0.0);
		v_Color = vec4(0.5, 0.5, 0.5, 0.0);
	}
	else
	{
		gl_Position = pos + vec4(pos.x * (-1.0 + a_Offset), pos.y * (-1.0 + a_Offset), 0, 0) + vec4(x * a_Offset, y * a_Offset * u_Aspect, 0.0, 0.0);
		v_Color = vec4(0.25, 0.25, 0.25, 0.0);
	}
	
	if (a_ScreenOffset[2] > 0.5)
		v_Position = 0.5 + gl_Position * 0.5;
	else 
	{
		v_Position = v_PositionCenter;
		if (viewZ > 0.0 ) 
			gl_Position.z = 0.01;
	}
	
	v_Blend = clamp(0.8 + len * 0.5, 0.0, 1.0);
	v_TexturePosition = a_TexturePosition;
}