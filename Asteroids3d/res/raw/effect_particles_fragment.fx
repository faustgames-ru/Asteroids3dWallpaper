precision mediump float;
uniform sampler2D u_ColorMap;
varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	gl_FragColor = texture2D(u_ColorMap, v_TexturePosition);
	gl_FragColor.a = gl_FragColor.r;
    gl_FragColor *= v_Color;
}
