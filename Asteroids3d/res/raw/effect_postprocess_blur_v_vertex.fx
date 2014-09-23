attribute vec3 a_Position;
attribute vec2 a_TexCoordinate;
 
uniform float u_TexelSize;

varying vec2 v_texCoord;
varying vec2 v_blurTexCoords[10];
 
void main()
{
	gl_Position = vec4(a_Position, 1.0);
	v_texCoord = a_TexCoordinate;
    v_blurTexCoords[ 0] = a_TexCoordinate + vec2(0.0, -5.0*u_TexelSize);
    v_blurTexCoords[ 1] = a_TexCoordinate + vec2(0.0, -4.0*u_TexelSize);
    v_blurTexCoords[ 2] = a_TexCoordinate + vec2(0.0, -3.0*u_TexelSize);
    v_blurTexCoords[ 3] = a_TexCoordinate + vec2(0.0, -2.0*u_TexelSize);
    v_blurTexCoords[ 4] = a_TexCoordinate + vec2(0.0, -1.0*u_TexelSize);
    v_blurTexCoords[ 5] = a_TexCoordinate + vec2(0.0,  1.0*u_TexelSize);
    v_blurTexCoords[ 6] = a_TexCoordinate + vec2(0.0,  2.0*u_TexelSize);
    v_blurTexCoords[ 7] = a_TexCoordinate + vec2(0.0,  3.0*u_TexelSize);
    v_blurTexCoords[ 8] = a_TexCoordinate + vec2(0.0,  4.0*u_TexelSize);
    v_blurTexCoords[ 9] = a_TexCoordinate + vec2(0.0,  5.0*u_TexelSize);
}