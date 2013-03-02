/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Texture_ImagePattern_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     localHashMap1.put("maskInput", Integer.valueOf(0));
/* 45 */     localHashMap1.put("inputTex", Integer.valueOf(1));
/*    */ 
/* 47 */     HashMap localHashMap2 = new HashMap();
/* 48 */     localHashMap2.put("content", Integer.valueOf(3));
/* 49 */     localHashMap2.put("xParams", Integer.valueOf(0));
/* 50 */     localHashMap2.put("yParams", Integer.valueOf(1));
/* 51 */     localHashMap2.put("perspVec", Integer.valueOf(2));
/*    */ 
/* 53 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 0, true, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.Texture_ImagePattern_Loader
 * JD-Core Version:    0.6.2
 */