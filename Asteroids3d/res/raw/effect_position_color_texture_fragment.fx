precision mediump float;

uniform sampler2D u_Texture;

varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{
	vec4 color = texture2D (u_Texture, v_TexturePosition) * v_Color; 
	color.w = (color.x + color.y + color.z) * 0.333;
	gl_FragColor = color;
}


