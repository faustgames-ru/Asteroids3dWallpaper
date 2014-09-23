precision mediump float;
attribute vec3 a_Position;
attribute vec3 a_Normal;
attribute vec2 a_TexturePosition;
attribute float a_TransformIndex;

uniform mat3 u_NormalModelMatrix[32];
uniform mat4 u_ModelMatrix[32];

uniform mat3 u_NormalMatrix;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform vec3 u_LightPosition;

varying vec2 v_TexturePosition;
varying float v_LamberFactor;
varying float v_Glowing;

void main()
{
	int transformIndex = int(a_TransformIndex);
    vec4 pos = u_ModelMatrix[transformIndex] * vec4(a_Position, 1.0);
    vec4 lightPos = u_ViewMatrix * vec4(u_LightPosition, 1.0);
    vec4 modelpos = pos;
    pos = u_ViewMatrix * pos;
    gl_Position = u_ProjectionMatrix * pos;
    v_TexturePosition = a_TexturePosition;
    vec3 normal = u_NormalMatrix  * (u_NormalModelMatrix[transformIndex] * a_Normal);
    vec3 light = normalize(lightPos.xyz - pos.xyz);
    v_LamberFactor = dot (light, normalize(normal));	
	v_Glowing = -v_LamberFactor * 0.25 + 0.75;
}