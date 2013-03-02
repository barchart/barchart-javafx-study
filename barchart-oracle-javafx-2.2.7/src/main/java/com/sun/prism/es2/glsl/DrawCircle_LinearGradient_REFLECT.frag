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
uniform vec4 jsl_pixCoordOffset;
vec2 pixcoord = vec2(
    gl_FragCoord.x-jsl_pixCoordOffset.x,
    ((jsl_pixCoordOffset.z-gl_FragCoord.y)*jsl_pixCoordOffset.w)-jsl_pixCoordOffset.y);
uniform vec2 idim;
LOWP float mask(vec2 tco, vec2 odim) {
float lensq_m_25 = dot(tco, tco) - 0.25;
float ocov = clamp(0.5 * (odim.x + 1.0 - lensq_m_25 / odim.x), 0.0, odim.y);
float icov = clamp(0.5 * (idim.x + 1.0 - lensq_m_25 / idim.x), 0.0, idim.y);
return ocov - icov;
}
const int MAX_FRACTIONS = 12;
const float TEXTURE_WIDTH = 16.0;
const float FULL_TEXEL_X = 1.0 / TEXTURE_WIDTH;
const float HALF_TEXEL_X = FULL_TEXEL_X / 2.0;
uniform vec4 fractions[12];
uniform sampler2D colors;
uniform float offset;
vec4 sampleGradient(float dist) {
int i;
float relFraction = 0.0;
for (i = 0;
i < MAX_FRACTIONS - 1;++i){
relFraction += clamp((dist - fractions[i].x) * fractions[i].y, 0.0, 1.0);
}
float tc = HALF_TEXEL_X + (FULL_TEXEL_X * relFraction);
return texture2D(colors, vec2(tc, offset));
}
vec4 cycleNone(float dist) {
if (dist <= 0.0){
return texture2D(colors, vec2(0.0, offset));
}
 else if (dist >= 1.0){
return texture2D(colors, vec2(1.0, offset));
}
 else {
return sampleGradient(dist);
}
}
vec4 cycleReflect(float dist) {
dist = 1.0 - (abs(fract(dist * 0.5) - 0.5) * 2.0);
return sampleGradient(dist);
}
vec4 cycleRepeat(float dist) {
dist = fract(dist);
return sampleGradient(dist);
}
uniform vec4 gradParams;
uniform vec3 perspVec;
LOWP vec4 paint(vec2 winCoord) {
vec3 fragCoord = vec3(winCoord.x, winCoord.y, 1.0);
float dist = dot(fragCoord, gradParams.xyz);
float wdist = dot(fragCoord, perspVec);
return cycleReflect(gradParams.w + dist / wdist);
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * paint(pixcoord) * perVertexColor;
}
