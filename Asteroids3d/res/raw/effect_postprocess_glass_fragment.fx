precision mediump float;
 
uniform sampler2D u_Image;
uniform sampler2D u_Glass;
uniform sampler2D u_Filter;

varying float v_aspect;
varying vec2 v_texCoord;
varying vec2 v_texCoordGlass;
 
void main()
{

	//vec2 offset = v_texCoord - v_distortion1;
	//offset.x *= v_aspect;
	//float level = 0.2 * clamp(1.0 - length(offset), 0.0, 1.0);

	vec4 c1 = texture2D(u_Filter, v_texCoord);
	float b = c1.r + c1.g + c1.b*0.3333;
	float level = 0.05 * b;

	vec4 c2 = texture2D(u_Glass, v_texCoordGlass);
	vec2 newTexcoord = v_texCoord + vec2((c2.r-0.5)*level, (c2.g-0.5)*level);
	vec4 c3 = texture2D(u_Image, newTexcoord);
    gl_FragColor = c3;
}