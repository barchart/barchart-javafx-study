/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DrawRoundRect_ImagePattern_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     localHashMap1.put("inputTex", Integer.valueOf(0));
/*    */ 
/* 46 */     HashMap localHashMap2 = new HashMap();
/* 47 */     localHashMap2.put("content", Integer.valueOf(5));
/* 48 */     localHashMap2.put("oinvarcradii", Integer.valueOf(0));
/* 49 */     localHashMap2.put("xParams", Integer.valueOf(2));
/* 50 */     localHashMap2.put("yParams", Integer.valueOf(3));
/* 51 */     localHashMap2.put("perspVec", Integer.valueOf(4));
/* 52 */     localHashMap2.put("iinvarcradii", Integer.valueOf(1));
/*    */ 
/* 54 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 1, true, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.DrawRoundRect_ImagePattern_Loader
 * JD-Core Version:    0.6.2
 */