/*    */ package com.sun.javafx.tk.quantum;
/*    */ 
/*    */ public class GlassPrismMouseEvent
/*    */   implements PrismEvent
/*    */ {
/*    */   ViewScene view;
/*    */   int type;
/*    */   int button;
/*    */   int x;
/*    */   int y;
/*    */   int xAbs;
/*    */   int yAbs;
/*    */   int clickCount;
/*    */   int modifiers;
/*    */   boolean isPopupTrigger;
/*    */   boolean isSynthesized;
/*    */   double wheelRotation;
/*    */ 
/*    */   protected GlassPrismMouseEvent(ViewScene paramViewScene, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, boolean paramBoolean2, double paramDouble)
/*    */   {
/* 25 */     this.view = paramViewScene;
/* 26 */     this.type = paramInt1;
/* 27 */     this.button = paramInt2;
/* 28 */     this.x = paramInt3;
/* 29 */     this.y = paramInt4;
/* 30 */     this.xAbs = paramInt5;
/* 31 */     this.yAbs = paramInt6;
/* 32 */     this.clickCount = paramInt7;
/* 33 */     this.modifiers = paramInt8;
/* 34 */     this.isPopupTrigger = paramBoolean1;
/* 35 */     this.wheelRotation = paramDouble;
/* 36 */     this.isSynthesized = paramBoolean2;
/*    */   }
/*    */ 
/*    */   protected GlassPrismMouseEvent(ViewScene paramViewScene, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
/*    */   {
/* 43 */     this(paramViewScene, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 1, 0, false, paramBoolean, 0.0D);
/*    */   }
/*    */ 
/*    */   protected ViewScene view() {
/* 47 */     return this.view;
/*    */   }
/*    */ 
/*    */   protected int type() {
/* 51 */     return this.type;
/*    */   }
/*    */ 
/*    */   protected int button() {
/* 55 */     return this.button;
/*    */   }
/*    */ 
/*    */   protected int x() {
/* 59 */     return this.x;
/*    */   }
/*    */ 
/*    */   protected int y() {
/* 63 */     return this.y;
/*    */   }
/*    */ 
/*    */   protected int xAbs() {
/* 67 */     return this.xAbs;
/*    */   }
/*    */ 
/*    */   protected int yAbs() {
/* 71 */     return this.yAbs;
/*    */   }
/*    */ 
/*    */   protected int clickCount() {
/* 75 */     return this.clickCount;
/*    */   }
/*    */ 
/*    */   protected int modifiers() {
/* 79 */     return this.modifiers;
/*    */   }
/*    */ 
/*    */   protected boolean isPopupTrigger() {
/* 83 */     return this.isPopupTrigger;
/*    */   }
/*    */ 
/*    */   protected double wheelRotation() {
/* 87 */     return this.wheelRotation;
/*    */   }
/*    */ 
/*    */   protected boolean isSynthesized() {
/* 91 */     return this.isSynthesized;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassPrismMouseEvent
 * JD-Core Version:    0.6.2
 */