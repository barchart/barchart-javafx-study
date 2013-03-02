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
LOWP float mask(vec2 tco, vec2 odim) {
float lensq_m_25 = dot(tco, tco) - 0.25;
float ocov = clamp(0.5 * (odim.x + 1.0 - lensq_m_25 / odim.x), 0.0, odim.y);
float icov = clamp(0.5 * (idim.x + 1.0 - lensq_m_25 / idim.x), 0.0, idim.y);
return ocov - icov;
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * perVertexColor;
}
