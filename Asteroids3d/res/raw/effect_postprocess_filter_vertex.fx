attribute vec3 a_Position;
attribute vec2 a_TexCoordinate;
 
varying vec2 v_texCoord;
 
void main()
{
    gl_Position = vec4(a_Position, 1.0);
    v_texCoord = a_TexCoordinate;
}