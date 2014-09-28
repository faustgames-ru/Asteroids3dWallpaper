precision mediump float;

uniform sampler2D u_Texture;

varying vec2 v_TexturePosition1;
varying vec2 v_TexturePosition2;
varying vec2 v_TexturePosition4;
varying vec4 v_CloudsColor0;
varying vec4 v_CloudsColor1;
varying vec4 v_StartsColor;
varying float v_Alpha;

void main()
{
	/*
	vec4 c = texture2D (u_Texture, v_TexturePosition2);
	gl_FragColor = vec4(c.x * c.y * 2.0 + (c.y + c.z) * c.z * v_CloudsLevel);
	*/

	/*
	vec4 c1 = texture2D (u_Texture, v_TexturePosition1);
	vec4 c2 = texture2D (u_Texture, v_TexturePosition2);
	vec4 c = c1 + c2;
	gl_FragColor = vec4(c.x * c.y + (c.y + c.z) * 0.5 * c.z * v_CloudsLevel);
	*/
	vec4 c1 = texture2D (u_Texture, v_TexturePosition1);
	vec4 c2 = texture2D (u_Texture, v_TexturePosition2);
	vec4 c4 = texture2D (u_Texture, v_TexturePosition4);
	//vec4 c = c1 + c2;
	//gl_FragColor = vec4(c4.x + c.x * c.y + (c4.z * 0.1 + (c.y + c.z) * 0.5 * c.z) * v_CloudsLevel);
	gl_FragColor = 
		c1.x * 0.25 + c2.x * 0.5 +
		v_StartsColor * c4.x * v_StartsColor.w * 2.0 +
		//v_StartsColor * (c1.x + c2.x + c4.x) * v_StartsColor.w +
		v_CloudsColor0 * (c1.y + c2.y + c4.y) * v_CloudsColor0.w + 
		v_CloudsColor1 * (c1.z + c2.z + c4.z) * v_CloudsColor1.w;
	gl_FragColor.w = v_Alpha;
}


