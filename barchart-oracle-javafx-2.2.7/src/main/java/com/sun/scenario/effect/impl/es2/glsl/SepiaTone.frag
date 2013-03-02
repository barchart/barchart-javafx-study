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
uniform float level;
void main() {
vec3 weightBW = vec3(0.3, 0.59, 0.11);
vec3 weightSep = vec3(1.6, 1.2, 0.9);
vec4 srcClr = texture2D(baseImg, texCoord0);
float l = dot(srcClr.rgb, weightBW);
vec3 lum = vec3(l, l, l);
vec3 sep = lum * weightSep;
gl_FragColor.rgb = mix(sep, srcClr.rgb, 1.0 - level);
gl_FragColor.a = srcClr.a;
}
