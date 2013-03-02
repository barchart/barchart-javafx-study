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
uniform vec3 tx0;
uniform vec3 tx1;
uniform vec3 tx2;
void main() {
vec3 p1;
vec3 p2;
p1 = vec3(texCoord0.x, texCoord0.y, 1.0);
p2.z = dot(p1, tx2);
p2.x = dot(p1, tx0) / p2.z;
p2.y = dot(p1, tx1) / p2.z;
gl_FragColor = texture2D(baseImg, p2.xy);
}
