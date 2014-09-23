precision lowp float;
 
uniform sampler2D u_Image;
 
varying vec2 v_texCoord;
 
void main()
{
	vec4 c = texture2D(u_Image, v_texCoord);
	gl_FragColor = (clamp(c, 0.9, 1.0) - 0.9)*10.0;
}