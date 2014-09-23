uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ModelMatrix;
uniform vec3 u_Eye;
uniform vec3 u_LightPosition;

precision mediump float;
attribute vec3 a_Position;
attribute vec2 a_TexCoordinate;
varying vec2 v_TexCoordinate;
varying float v_distance;
varying vec3 v_RayDirection;
varying vec3 v_SunDirection;
varying vec3 v_Eye;

void main() {
	v_TexCoordinate = a_TexCoordinate;
	vec4 world = u_ModelMatrix * vec4(a_Position, 1.0);
	v_distance = length(world.xyz - u_Eye);
	
	v_RayDirection = world.xyz - u_Eye;
	v_SunDirection = u_LightPosition;
	v_Eye = u_Eye;

	world = u_ViewMatrix * world;
	gl_Position = u_ProjectionMatrix * world;
}