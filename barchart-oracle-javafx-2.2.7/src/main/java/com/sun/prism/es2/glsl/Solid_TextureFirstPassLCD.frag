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
varying LOWP vec4 perVertexColor;
uniform sampler2D inputTex;
LOWP vec4 paint(vec2 texCoord) {
vec4 result = texture2D(inputTex, texCoord);
return result;
}
void main() {
gl_FragColor = paint(texCoord0) * perVertexColor;
}
