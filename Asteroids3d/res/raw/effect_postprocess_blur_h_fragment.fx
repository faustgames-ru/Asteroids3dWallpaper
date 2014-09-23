precision mediump float;
 
uniform sampler2D u_Image;
 
varying vec2 v_texCoord;
varying vec2 v_blurTexCoords[10];
 
void main()
{
    gl_FragColor = vec4(0.0);
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[0]) *0.010000;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[1]) *0.026995;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[2]) *0.064759;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[3]) *0.120985;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[4]) *0.176033;
    gl_FragColor += texture2D(u_Image, v_texCoord        ) *0.199471;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[5]) *0.176033;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[6]) *0.120985;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[7]) *0.064759;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[8]) *0.026995;
    gl_FragColor += texture2D(u_Image, v_blurTexCoords[9]) *0.010000;
}