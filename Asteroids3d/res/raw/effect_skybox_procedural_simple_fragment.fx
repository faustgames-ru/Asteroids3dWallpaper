precision mediump float;

uniform sampler2D u_Texture;
uniform vec4 u_FogColor;

varying vec2 v_TexturePosition2;
varying vec4 v_CloudsColor0;
varying vec4 v_CloudsColor1;
varying vec4 v_StartsColor;
varying float v_Alpha;

vec4 applyFog(vec4 rgb)
{
	return mix(rgb, u_FogColor, 0.2);
}

void main()
{
	vec4 c = texture2D (u_Texture, v_TexturePosition2);
	gl_FragColor =
		v_CloudsColor0 * c.y * 3.0 * v_CloudsColor0.w +
		v_CloudsColor1 * c.z * 3.0 * v_CloudsColor1.w;
	gl_FragColor.w = v_Alpha;
	gl_FragColor = mix(gl_FragColor, u_FogColor, 0.2);
	gl_FragColor += v_StartsColor * c.x * v_StartsColor.w * 1.5;

}


