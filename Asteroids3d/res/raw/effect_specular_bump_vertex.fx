precision mediump float;
attribute vec3 a_Position;
attribute vec3 a_Normal;
attribute vec3 a_Tangent;
attribute vec3 a_BiNormal;
attribute vec2 a_TexturePosition;
attribute float a_TransformIndex;

uniform mat3 u_NormalModelMatrix[32];
uniform mat4 u_ModelMatrix[32];

uniform mat3 u_NormalMatrix;
uniform mat4 u_ViewMatrix;
uniform mat4 u_ProjectionMatrix;
uniform vec3 u_LightPosition;
uniform vec3 u_Eye;

varying vec2 v_TexturePosition;
varying vec3 v_Light;
varying vec3 v_HalfVector;
varying float v_distance;
varying vec3 v_RayDirection;
varying vec3 v_SunDirection;
varying vec3 v_Eye;

void main()
{
  int transformIndex = int(a_TransformIndex);
    vec4 pos = u_ModelMatrix[transformIndex] * vec4(a_Position, 1.0);

	v_distance = length(pos.xyz - u_Eye);

	v_RayDirection = pos.xyz - u_Eye;
	v_SunDirection = u_LightPosition;
	v_Eye = u_Eye;

	pos = u_ViewMatrix * pos;
    vec4 lightPos = u_ViewMatrix * vec4(u_LightPosition, 1.0);
    gl_Position = u_ProjectionMatrix * pos;
    v_TexturePosition = a_TexturePosition;
    vec3 n = u_NormalMatrix  * (u_NormalModelMatrix[transformIndex] * a_Normal);
    vec3 t = u_NormalMatrix  * (u_NormalModelMatrix[transformIndex] * a_Tangent);
    vec3 b = u_NormalMatrix  * (u_NormalModelMatrix[transformIndex] * a_BiNormal);
    vec3 lightDir = normalize(lightPos.xyz - pos.xyz);
    vec3 v;
    v.x = dot (lightDir, t);
    v.y = dot (lightDir, b);
    v.z = dot (lightDir, n);
    v_Light = normalize (v);
    vec3 halfVector = normalize(lightDir - normalize(pos.xyz));
    v.x = dot (halfVector, t);
    v.y = dot (halfVector, b);
    v.z = dot (halfVector, n);
    v_HalfVector = v;
}