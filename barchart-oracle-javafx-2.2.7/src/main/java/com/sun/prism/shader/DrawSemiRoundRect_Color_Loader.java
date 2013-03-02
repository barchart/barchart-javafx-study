/*    */ package com.sun.prism.shader;
/*    */ 
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderFactory;
/*    */ import java.io.InputStream;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class DrawSemiRoundRect_Color_Loader
/*    */ {
/*    */   public static Shader loadShader(ShaderFactory paramShaderFactory, InputStream paramInputStream)
/*    */   {
/* 43 */     HashMap localHashMap1 = new HashMap();
/* 44 */     HashMap localHashMap2 = new HashMap();
/* 45 */     localHashMap2.put("idim", Integer.valueOf(1));
/* 46 */     localHashMap2.put("oinvarcradii", Integer.valueOf(0));
/*    */ 
/* 48 */     return paramShaderFactory.createShader(paramInputStream, localHashMap1, localHashMap2, 1, false, true);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.shader.DrawSemiRoundRect_Color_Loader
 * JD-Core Version:    0.6.2
 */