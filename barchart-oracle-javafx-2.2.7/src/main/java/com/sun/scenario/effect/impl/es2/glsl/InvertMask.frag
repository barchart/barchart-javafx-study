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
uniform sampler2D baseImg;
uniform vec2 offset;
void main() {
float val = texture2D(baseImg, texCoord0 - offset).a;
float inv = 1.0 - val;
gl_FragColor = vec4(inv, inv, inv, inv);
}
