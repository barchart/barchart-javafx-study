/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DrawEllipse_LinearGradient_REPEAT_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     localHashMap1.put("colors", Integer.valueOf(0));
/*    */ 
/* 46 */     HashMap localHashMap2 = new HashMap();
/* 47 */     localHashMap2.put("idim", Integer.valueOf(0));
/* 48 */     localHashMap2.put("fractions", Integer.valueOf(1));
/* 49 */     localHashMap2.put("gradParams", Integer.valueOf(14));
/* 50 */     localHashMap2.put("perspVec", Integer.valueOf(15));
/* 51 */     localHashMap2.put("offset", Integer.valueOf(13));
/*    */ 
/* 53 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 1, true, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.DrawEllipse_LinearGradient_REPEAT_Loader
 * JD-Core Version:    0.6.2
 */