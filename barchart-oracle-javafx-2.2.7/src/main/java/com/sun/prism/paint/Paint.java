/*    */ package com.sun.prism.paint;
/*    */ 
/*    */ public abstract class Paint
/*    */ {
/*    */   private final Type type;
/*    */   private final boolean proportional;
/*    */ 
/*    */   Paint(Type paramType, boolean paramBoolean)
/*    */   {
/* 40 */     this.type = paramType;
/* 41 */     this.proportional = paramBoolean;
/*    */   }
/*    */ 
/*    */   public final Type getType() {
/* 45 */     return this.type;
/*    */   }
/*    */ 
/*    */   public boolean isProportional() {
/* 49 */     return this.proportional;
/*    */   }
/*    */ 
/*    */   public abstract boolean isOpaque();
/*    */ 
/*    */   public static enum Type
/*    */   {
/* 12 */     COLOR("Color", false, false), 
/* 13 */     LINEAR_GRADIENT("LinearGradient", true, false), 
/* 14 */     RADIAL_GRADIENT("RadialGradient", true, false), 
/* 15 */     IMAGE_PATTERN("ImagePattern", false, true);
/*    */ 
/*    */     private String name;
/*    */     private boolean isGradient;
/*    */     private boolean isImagePattern;
/*    */ 
/* 21 */     private Type(String paramString, boolean paramBoolean1, boolean paramBoolean2) { this.name = paramString;
/* 22 */       this.isGradient = paramBoolean1;
/* 23 */       this.isImagePattern = paramBoolean2; }
/*    */ 
/*    */     public String getName() {
/* 26 */       return this.name;
/*    */     }
/*    */     public boolean isGradient() {
/* 29 */       return this.isGradient;
/*    */     }
/*    */     public boolean isImagePattern() {
/* 32 */       return this.isImagePattern;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.paint.Paint
 * JD-Core Version:    0.6.2
 */