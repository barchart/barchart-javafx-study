/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Solid_TextureSecondPassLCD_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     localHashMap1.put("glyphColor", Integer.valueOf(1));
/* 45 */     localHashMap1.put("dstColor", Integer.valueOf(0));
/*    */ 
/* 47 */     HashMap localHashMap2 = new HashMap();
/* 48 */     localHashMap2.put("gamma", Integer.valueOf(0));
/*    */ 
/* 50 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 1, false, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.Solid_TextureSecondPassLCD_Loader
 * JD-Core Version:    0.6.2
 */