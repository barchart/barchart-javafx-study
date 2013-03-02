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
LOWP float mask(vec2 tco, vec2 tcc) {
vec2 cov = clamp(tcc + 0.5 - abs(tco), 0.0, 1.0);
cov = min(cov, tcc * 2.0);
return cov.x * cov.y;
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * perVertexColor;
}
