/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Solid_TextureYV12_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     localHashMap1.put("lumaTex", Integer.valueOf(0));
/* 45 */     localHashMap1.put("alphaTex", Integer.valueOf(3));
/* 46 */     localHashMap1.put("crTex", Integer.valueOf(1));
/* 47 */     localHashMap1.put("cbTex", Integer.valueOf(2));
/*    */ 
/* 49 */     HashMap localHashMap2 = new HashMap();
/* 50 */     localHashMap2.put("cbCrScale", Integer.valueOf(1));
/* 51 */     localHashMap2.put("lumaAlphaScale", Integer.valueOf(0));
/*    */ 
/* 53 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 0, false, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.Solid_TextureYV12_Loader
 * JD-Core Version:    0.6.2
 */