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
uniform float threshold;
void main() {
vec3 luminanceVector = vec3(0.2125, 0.7154, 0.0721);
vec4 val = texture2D(baseImg, texCoord0);
float luminance = dot(luminanceVector, val.rgb);
luminance = max(0.0, luminance - val.a * threshold);
gl_FragColor = val * sign(luminance);
}
