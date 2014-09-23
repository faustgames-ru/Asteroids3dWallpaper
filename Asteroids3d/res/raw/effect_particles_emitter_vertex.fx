precision mediump float;

attribute vec3 a_MidPoint;
attribute vec3 a_Position;
attribute vec2 a_TexturePosition;
attribute vec3 a_StartVelocity;
attribute vec3 a_Acceleration;
attribute vec4 a_Color;
attribute float a_Scale;
attribute float a_LifeTime;
attribute float a_RebornTime;
attribute float a_InitPhase;

uniform float u_Time;
uniform float u_AlphaFading;
uniform float u_ScaleFading;
uniform mat4 u_ProjectionMatrix;
uniform mat4 u_ViewMatrix;
uniform vec4 u_Color;
uniform float u_TimeStep;

varying vec2 v_TexturePosition;
varying vec4 v_Color;

void main()
{  
	float periond = a_RebornTime + a_LifeTime;
	float lifeTime = mod(u_Time + a_RebornTime + a_InitPhase, periond);
	float life = lifeTime / a_LifeTime;
	float scale = a_Scale;
	
	vec4 color = a_Color;
	
	if (life < u_AlphaFading)
		color *= clamp(life, 0.0, u_AlphaFading) / u_AlphaFading;	
	else if ((1.0 - life) < u_AlphaFading)
		color *= clamp(1.0 - life, 0.0, u_AlphaFading) / u_AlphaFading;

	scale = a_Scale;
	if (life < u_ScaleFading)
		scale = clamp(life, 0.0, u_ScaleFading) * a_Scale / u_ScaleFading;	
	else if ((1.0 - life) < u_ScaleFading)
		scale = clamp(1.0 - life, 0.0, u_ScaleFading) * a_Scale / u_ScaleFading;

	vec3 velocity = a_StartVelocity + a_Acceleration * lifeTime; 

	vec4 p1 = u_ViewMatrix * vec4(a_MidPoint + velocity * lifeTime, 1.0);
	vec4 p2 = u_ViewMatrix * vec4(a_MidPoint + velocity * (lifeTime + u_TimeStep), 1.0);
	vec3 position1 = vec3(p1.x,p1.y,p1.z);
	vec3 position2 = vec3(p2.x,p2.y,p1.z);

	vec3 direction = position2 - position1;
	if (length(direction) < 1.0)
		direction = normalize(direction);
	vec3 tangent = normalize(cross(direction, vec3(0.0,0.0,1.0)));

	vec3 position = position1 + direction * a_Position.x * scale + tangent * a_Position.y * scale;
	
	gl_Position = u_ProjectionMatrix * vec4(position, 1.0);
	v_Color = color * u_Color;
	v_TexturePosition = a_TexturePosition;
}