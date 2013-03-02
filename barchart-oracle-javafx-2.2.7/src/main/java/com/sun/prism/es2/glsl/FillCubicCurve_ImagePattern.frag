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
LOWP float mask(vec2 tc0, vec2 tc1) {
vec3 vwt = vec3(tc0.x, tc0.y, tc1.x);
vec3 px = dFdx(vwt);
vec3 py = dFdy(vwt);
float vsq = vwt.x * vwt.x;
float vsq3 = 3.0 * vsq;
float fx = (vsq3 * px.x) - (vwt.z * px.y) - (vwt.y * px.z);
float fy = (vsq3 * py.x) - (vwt.z * py.y) - (vwt.y * py.z);
float sd = tc1.y * (((vsq * vwt.x) - (vwt.y * vwt.z)) / sqrt(fx * fx + fy * fy));
float alpha = 0.5 - sd;
return clamp(alpha, 0.0, 1.0);
}
uniform sampler2D inputTex;
uniform vec4 xParams;
uniform vec4 yParams;
uniform vec3 perspVec;
uniform vec4 content;
LOWP vec4 paint(vec2 winCoord) {
vec3 fragCoord = vec3(winCoord.x, winCoord.y, 1.0);
float wParam = dot(fragCoord, perspVec);
vec2 texCoord = vec2(dot(xParams.xyz, fragCoord) / wParam + xParams.w, dot(yParams.xyz, fragCoord) / wParam + yParams.w);
texCoord = fract(texCoord);
texCoord = vec2(content.x, content.y) + texCoord * vec2(content.z, content.w);
return texture2D(inputTex, texCoord);
}
void main() {
gl_FragColor = mask(texCoord0, texCoord1) * paint(pixcoord) * perVertexColor;
}
