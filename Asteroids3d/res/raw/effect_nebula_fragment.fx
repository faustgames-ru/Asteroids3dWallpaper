precision mediump float;

uniform sampler2D u_ColorMap1;
uniform sampler2D u_ColorMap2;
uniform vec4 u_Color1;
uniform vec4 u_Color2;

varying vec2 v_TexturePosition1;
varying vec2 v_TexturePosition2;

void main()
{
	vec4 color1 = texture2D(u_ColorMap1, v_TexturePosition1); color1[3] = color1[0];
	vec4 color2 = texture2D(u_ColorMap2, v_TexturePosition2); color2[3] = color2[0];

	gl_FragColor = color1 * u_Color1 + color2 * u_Color2;
	gl_FragColor *= 2.0;
}
