/*    */ package com.sun.scenario.effect.impl.prism;
/*    */ 
/*    */ import com.sun.glass.ui.Screen;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import java.util.Map;
/*    */ import java.util.WeakHashMap;
/*    */ 
/*    */ public class PrFilterContext extends FilterContext
/*    */ {
/*    */   private static Screen defaultScreen;
/* 38 */   private static final Map<Screen, PrFilterContext> ctxMap = new WeakHashMap();
/*    */ 
/*    */   private PrFilterContext(Screen paramScreen)
/*    */   {
/* 42 */     super(paramScreen);
/*    */   }
/*    */ 
/*    */   public static PrFilterContext getInstance(Screen paramScreen) {
/* 46 */     if (paramScreen == null) {
/* 47 */       throw new IllegalArgumentException("Screen must be non-null");
/*    */     }
/* 49 */     PrFilterContext localPrFilterContext = (PrFilterContext)ctxMap.get(paramScreen);
/* 50 */     if (localPrFilterContext == null) {
/* 51 */       localPrFilterContext = new PrFilterContext(paramScreen);
/* 52 */       ctxMap.put(paramScreen, localPrFilterContext);
/*    */     }
/* 54 */     return localPrFilterContext;
/*    */   }
/*    */ 
/*    */   public static PrFilterContext getDefaultInstance() {
/* 58 */     if (defaultScreen == null)
/*    */     {
/* 61 */       defaultScreen = Screen.getMainScreen();
/*    */     }
/* 63 */     return getInstance(defaultScreen);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrFilterContext
 * JD-Core Version:    0.6.2
 */