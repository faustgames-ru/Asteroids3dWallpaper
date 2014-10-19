precision mediump float;
uniform sampler2D u_DiffuseGlow;
uniform sampler2D u_NormalSpecular;
uniform float u_GlowLevel;
uniform vec4 u_SpecularLight;
uniform vec4 u_DiffuseLight;
uniform vec4 u_AmbientLight;
uniform vec4 u_GlowColor;
uniform vec4 u_FogColor;

varying vec2 v_TexturePosition;
varying vec3 v_Light;
varying vec3 v_HalfVector;
varying float v_Distance;
varying vec3 v_RayDirection;
varying vec3 v_SunDirection;
varying vec3 v_Eye;

vec4 applyFog(vec4 rgb)
{
	float distance = v_Distance;
	float fogAmount = 1.0 - exp(-distance*0.004);
	vec4 fogColor = u_FogColor;
	return mix(rgb, fogColor, clamp(fogAmount, 0.0, 1.0));
}

vec4 applyEllipticFog(vec4 rgb)
{
	float distance = v_Distance;
	vec3 eye = v_Eye;
	vec3 ray = normalize(v_RayDirection);
	vec3 sunDir = normalize(v_SunDirection);
	float u_FogDensity = 0.01;
	vec3 R = vec3(25.0, 25.0, 25.0);
	vec3 C = vec3(0.0, 0.0, 0.0);

	vec3 d = normalize(ray);
	vec3 o = eye;

	vec3 Axyz = d / R;
	vec3 Bxyz = (o - C) / R;

	float a = Axyz.x * Axyz.x + Axyz.y * Axyz.y + Axyz.z * Axyz.z;
	float b = 2.0 * (Axyz.x * Bxyz.x + Axyz.y * Bxyz.y + Axyz.z * Bxyz.z);
	float c = Bxyz.x * Bxyz.x + Bxyz.y * Bxyz.y + Bxyz.z * Bxyz.z - 1.0;

	float kpow2 = max(b*b - 4.0*a*c, 0.0);

	float k = sqrt(kpow2);
	float a2 = 2.0*a;
	float t1 = (-b + k) / a2;
	float t2 = (-b - k) / a2;
	float at = min(max(t1, 0.0), distance);
	float bt = min(max(t2, 0.0), distance);

	float dist = abs(bt - at);

	float fogAmount = 1.0 - exp(-dist*u_FogDensity);

	float sunAmount = max(dot(ray, sunDir), 0.0);
	float sunK = clamp(pow(sunAmount, 8.0), 0.0, 1.0);
	vec4  fogColor = mix(vec4(0.5, 0.6, 0.7, 1.0), // bluish
		vec4(1.0, 0.9, 0.7, 1.0), // yellowish
		sunK);

	return mix(rgb, fogColor, clamp(fogAmount, 0.0, 1.0));
}


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
	/*
	float fogAmount = 1.0 - exp(-v_Distance*0.005);
	vec4  fogColor = vec4(0.5, 0.6, 0.7, 1.0);
	gl_FragColor = mix(color, fogColor, clamp(fogAmount, 0.0, 1.0));
	*/
	gl_FragColor = applyFog(color);
}