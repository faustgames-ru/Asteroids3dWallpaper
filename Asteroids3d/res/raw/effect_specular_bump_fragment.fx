precision mediump float;
uniform sampler2D u_DiffuseGlow;
uniform sampler2D u_NormalSpecular;
uniform float u_GlowLevel;
uniform vec4 u_SpecularLight;
uniform vec4 u_DiffuseLight;
uniform vec4 u_AmbientLight;
uniform vec4 u_GlowColor;

varying vec2 v_TexturePosition;
varying vec3 v_Light;
varying vec3 v_HalfVector;
varying float v_Distance;
varying vec3 v_RayDirection;
varying vec3 v_SunDirection;
varying vec3 v_Eye;

void main()
{
	vec4 mapNormalSpecular = texture2D (u_NormalSpecular, v_TexturePosition);
	vec4 mapDiffuseGlow = texture2D (u_DiffuseGlow, v_TexturePosition);

	vec3 normal = normalize(2.0 * mapNormalSpecular.zyx - 1.0);
	float lamberFactor = dot(normalize(v_Light), normal);
	
	float glowMaterial = mapDiffuseGlow.w * (lamberFactor * -0.65 + 0.35) * u_GlowLevel * 2.0;

	vec4 color = u_GlowColor * max(glowMaterial, 0.0);

	vec4 diffuseMaterial = vec4(mapDiffuseGlow.zyx, 1.0);
	vec4 ambient = u_AmbientLight * diffuseMaterial;

	float specularMaterial = mapNormalSpecular.w;

	float shininess = pow(dot(normalize(v_HalfVector), -normal), 8.0);
	color += diffuseMaterial * clamp(u_AmbientLight * normal.z + u_DiffuseLight * lamberFactor, 0.0, 1.0);
	color += u_SpecularLight * (shininess * specularMaterial);
	gl_FragColor = color;
}