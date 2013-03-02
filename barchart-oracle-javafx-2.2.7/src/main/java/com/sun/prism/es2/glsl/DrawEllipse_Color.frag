#ifdef GL_ES
#extension GL_OES_standard_derivatives : enable
precision highp float;
precision highp int;
#define HIGHP highp
#define MEDIUMP mediump
#define LOWP lowp
#else
#define HIGHP
#define MEDIUMP
#define LOWP
#endif
varying vec2 texCoord0;
varying vec2 texCoord1;
varying LOWP vec4 perVertexColor;
uniform vec2 idim;
float ellipsemask(vec2 abstco, vec2 invarcradii) {
vec2 absecctco = abstco * invarcradii;
float ecclensq = dot(absecctco, absecctco);
float delta = dot(absecctco, invarcradii) * 2.0;
return clamp(0.5 + (1.0 - ecclensq) / delta, 0.0, 1.0);
}
LOWP float mask(vec2 tco, vec2 odim) {
vec2 abstco = abs(tco);
float ocov = ellipsemask(abstco, odim);
float icov = ellipsemask(abstco, idim);
return clamp(ocov - icov, 0.0, 1.0);
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * perVertexColor;
}
