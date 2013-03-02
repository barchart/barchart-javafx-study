/*    */ package com.sun.scenario.effect.impl.es2;
/*    */ 
/*    */ import com.sun.scenario.effect.Effect.AccelType;
/*    */ import com.sun.scenario.effect.impl.hw.ShaderSource;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class ES2ShaderSource
/*    */   implements ShaderSource
/*    */ {
/*    */   public InputStream loadSource(String paramString)
/*    */   {
/* 19 */     return ES2ShaderSource.class.getResourceAsStream("glsl/" + paramString + ".frag");
/*    */   }
/*    */ 
/*    */   public Effect.AccelType getAccelType()
/*    */   {
/* 24 */     return Effect.AccelType.OPENGL;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.es2.ES2ShaderSource
 * JD-Core Version:    0.6.2
 */