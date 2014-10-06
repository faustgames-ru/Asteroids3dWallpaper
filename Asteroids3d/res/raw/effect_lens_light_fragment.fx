precision mediump float;

uniform sampler2D u_Texture;
uniform sampler2D u_DepthMap;

varying vec2 v_DepthMapPosition;
varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	vec4 color = texture2D (u_Texture, v_TexturePosition) * v_Color;
	vec4 depth = texture2D (u_DepthMap, v_DepthMapPosition);
	float depth1 = clamp(1.0 - depth.x * 2.0, 0.0, 1.0);
	//color.w = max(max(color.x, color.y), color.z);
	gl_FragColor = color * depth1;
}


