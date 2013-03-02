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
uniform vec2 oinvarcradii;
uniform vec2 iinvarcradii;
float rrmask(vec2 arcreltco, vec2 invarcradii) {
vec2 ecctco = arcreltco * invarcradii;
float ecclensq = dot(ecctco, ecctco);
float pix = dot(ecctco / ecclensq, invarcradii);
return clamp(0.5 + (1.0 - ecclensq) / (2.0 * pix), 0.0, 1.0);
}
LOWP float mask(vec2 tco, vec2 flatdim) {
vec2 arcreltco = max(abs(tco) - flatdim, 0.0010);
float ocov = rrmask(arcreltco, oinvarcradii);
float icov = rrmask(arcreltco, iinvarcradii);
return clamp(ocov - icov, 0.0, 1.0);
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * perVertexColor;
}
