attribute vec4 a_Position;
attribute vec2 a_TexCoordinate;
 
uniform float u_Aspect;
uniform mat4 u_ProjectionMatrix;
//uniform vec3 u_Distortion1;
 
varying vec2 v_texCoord;
varying vec2 v_texCoordGlass;
varying float v_aspect;

void main()
{
    gl_Position = a_Position;
	v_texCoord = a_TexCoordinate;
    v_texCoordGlass = a_TexCoordinate * 0.5 - 0.25;
	v_texCoordGlass.x *= u_Aspect;
	v_texCoordGlass += 0.5;
	//v_texCoordGlass += vec2(u_Distortion1.x, u_Distortion1.y) * u_Distortion1.z;
	v_aspect = u_Aspect;
}