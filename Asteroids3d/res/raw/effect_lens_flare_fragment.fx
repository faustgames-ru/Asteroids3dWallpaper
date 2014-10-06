precision mediump float;
uniform sampler2D u_ColorMap;
uniform sampler2D u_DepthMap;
uniform vec4 u_Color;
varying vec2 v_TexturePosition;
varying vec4 v_Position;
varying vec4 v_PositionCenter;
varying float v_Blend;
varying vec4 v_Color;
varying float v_Mix;

void main()
{
	vec4 color = texture2D(u_ColorMap, v_TexturePosition) * (u_Color + v_Color);
	//vec4 depthColor1 = texture2D (u_DepthMap, vec2(v_PositionCenter[0], v_PositionCenter[1]));
	vec4 depthColor1 = texture2D (u_DepthMap, v_Position.xy);
	float depth1 = clamp(1.0 - depthColor1[0] * 4.0, 0.0, 1.0);
	//gl_FragColor *= (depth*0.1 + depth1*0.9) * v_Blend;
	vec4 colord = color * (depth1 * v_Blend);
	gl_FragColor = mix(colord, color, v_Mix);
}
