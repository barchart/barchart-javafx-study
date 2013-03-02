/*    */ package com.sun.prism.es2.gl;
/*    */ 
/*    */ public class GLGPUInfo
/*    */ {
/*    */   final String vendor;
/*    */   final String model;
/*    */ 
/*    */   public GLGPUInfo(String paramString1, String paramString2)
/*    */   {
/* 20 */     this.vendor = paramString1;
/* 21 */     this.model = paramString2;
/*    */   }
/*    */ 
/*    */   public boolean matches(GLGPUInfo paramGLGPUInfo)
/*    */   {
/* 36 */     boolean bool = true;
/* 37 */     if (paramGLGPUInfo.vendor != null) {
/* 38 */       bool = this.vendor.startsWith(paramGLGPUInfo.vendor);
/*    */     }
/* 40 */     if (paramGLGPUInfo.model != null) {
/* 41 */       bool = this.model.contains(paramGLGPUInfo.model);
/*    */     }
/* 43 */     return bool;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 47 */     return "GLGPUInfo [vendor = " + this.vendor + ", model = " + this.model + "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.GLGPUInfo
 * JD-Core Version:    0.6.2
 */