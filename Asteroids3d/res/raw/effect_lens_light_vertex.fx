precision mediump float;

uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ModelMatrix;

attribute vec3 a_Position;
attribute vec3 a_Center;
attribute vec2 a_TexturePosition;
attribute vec4 a_Color;

varying vec2 v_DepthMapPosition;
varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	vec4 world = u_ModelMatrix * vec4(a_Position, 1.0);
	world = u_ViewMatrix * world;
	
	vec4 center = u_ModelMatrix * vec4(a_Center, 1.0);
	center = u_ViewMatrix * center;

	vec2 cs = normalize(center.xy);
	world -= center;
	world.xy *= mat2(cs.x, cs.y, -cs.y, cs.x);// vec2(world.x * cs.x + world.y * cs.y, -world.x * cs.y + world.y * cs.x);
	world += center;
		
	gl_Position = u_ProjectionMatrix * world;
	
	center = u_ProjectionMatrix * center;
	v_DepthMapPosition = 0.5 + (center.xy / center.w) * 0.5;

	v_TexturePosition = a_TexturePosition;
	v_Color = a_Color;

}