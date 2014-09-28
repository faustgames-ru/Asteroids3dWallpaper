precision mediump float;
uniform sampler2D u_Specular;
uniform sampler2D u_Diffuse;
uniform sampler2D u_Normal;
uniform sampler2D u_Glow;
uniform float u_GlowLevel;
uniform vec4 u_SpecularLight;
uniform vec4 u_DiffuseLight;
uniform vec4 u_AmbientLight;
uniform vec4 u_GlowColor;

const float u_FogDensity = 0.01;

varying vec2 v_TexturePosition;
varying vec3 v_Light;
varying vec3 v_HalfVector;
varying float v_distance;
varying vec3 v_RayDirection;
varying vec3 v_SunDirection;
varying vec3 v_Eye;

vec4 applySunFog(
	in vec4  rgb,      // original color of the pixel
    in float distance, // camera to point distance
    in vec3  rayDir,   // camera to point vector
    in vec3  sunDir )  // sun light direction
{
    float fogAmount = 1.0 - exp( -distance*u_FogDensity );
    float sunAmount = max( dot( rayDir, sunDir ), 0.0 );
    vec4  fogColor  = mix( vec4(0.5,0.6,0.7,1.0), // bluish
                           vec4(1.0,0.9,0.7,1.0), // yellowish
                           clamp(pow(sunAmount,1.0), 0.0, 1.0) );
    return mix( rgb, fogColor, clamp(fogAmount, 0.0, 1.0) );
}

vec4 applyFog( in vec4  rgb,      // original color of the pixel
               in float distance, // camera to point distance
               in vec3  rayOri,   // camera position
               in vec3  rayDir,
			   in vec3  sunDir )  // camera to point vector
{
    float b = u_FogDensity;
    float c = 0.75;
	float rayOriH = abs(rayOri.z);
	float rayDirH = abs(rayDir.z);
    float fogAmount = c * exp(-rayOriH*b) * (1.0-exp( -distance*rayDirH*b ))/rayDirH;
    float sunAmount = max( dot( normalize(rayDir), sunDir ), 0.0 );
    vec4  fogColor  = mix( vec4(0.5,0.6,0.7,1.0), // bluish
                           vec4(1.0,0.9,0.7,1.0), // yellowish
                           clamp(pow(sunAmount,1.0), 0.0, 1.0) );
    return mix( rgb, fogColor, clamp(fogAmount, 0.0, 1.0) );
}

void main()
{
	vec3 normal = normalize(2.0 * texture2D (u_Normal, v_TexturePosition).rgb - 1.0);
	float lamberFactor= dot (normalize(v_Light), normal);
	vec4 glowMaterial = texture2D (u_Glow, v_TexturePosition) * (lamberFactor * -0.65 + 0.35 ) * u_GlowLevel * 2.0;
	vec4 color = max(glowMaterial, 0.0) * u_GlowColor;
	vec4 diffuseMaterial = texture2D (u_Diffuse, v_TexturePosition);
	vec4 specularMaterial = texture2D (u_Specular, v_TexturePosition);
	float shininess = pow (dot (normalize(v_HalfVector), normal), 4.0);
	color += u_AmbientLight * normal.z;
	color += diffuseMaterial * u_DiffuseLight * lamberFactor;
	color += specularMaterial * u_SpecularLight * shininess;
	//gl_FragColor = applyFog(color, v_distance, v_Eye, normalize(v_RayDirection), normalize(v_SunDirection));
	gl_FragColor = applySunFog(color, v_distance, normalize(v_RayDirection), normalize(v_SunDirection));
}