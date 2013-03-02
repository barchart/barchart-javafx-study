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
uniform sampler2D maskInput;
LOWP float mask(vec2 texCoord) {
return texture2D(maskInput, texCoord).a;
}
void main() {
gl_FragColor = mask(texCoord0) * perVertexColor;
}
