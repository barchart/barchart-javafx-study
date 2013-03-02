/*    */ package com.sun.scenario.effect.impl.sw.java;
/*    */ 
/*    */ import com.sun.scenario.effect.Effect.AccelType;
/*    */ import com.sun.scenario.effect.impl.sw.RendererDelegate;
/*    */ 
/*    */ public class JSWRendererDelegate
/*    */   implements RendererDelegate
/*    */ {
/*    */   public Effect.AccelType getAccelType()
/*    */   {
/* 39 */     return Effect.AccelType.NONE;
/*    */   }
/*    */ 
/*    */   public String getPlatformPeerName(String paramString, int paramInt) {
/* 43 */     return "com.sun.scenario.effect.impl.sw.java.JSW" + paramString + "Peer";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWRendererDelegate
 * JD-Core Version:    0.6.2
 */