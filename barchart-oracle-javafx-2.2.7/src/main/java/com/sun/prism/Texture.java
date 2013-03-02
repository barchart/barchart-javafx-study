/*    */ package com.sun.prism;
/*    */ 
/*    */ import java.nio.Buffer;
/*    */ 
/*    */ public abstract interface Texture extends GraphicsResource
/*    */ {
/*    */   public abstract PixelFormat getPixelFormat();
/*    */ 
/*    */   public abstract int getPhysicalWidth();
/*    */ 
/*    */   public abstract int getPhysicalHeight();
/*    */ 
/*    */   public abstract int getContentX();
/*    */ 
/*    */   public abstract int getContentY();
/*    */ 
/*    */   public abstract int getContentWidth();
/*    */ 
/*    */   public abstract int getContentHeight();
/*    */ 
/*    */   public abstract long getNativeSourceHandle();
/*    */ 
/*    */   public abstract int getLastImageSerial();
/*    */ 
/*    */   public abstract void setLastImageSerial(int paramInt);
/*    */ 
/*    */   public abstract void update(Image paramImage);
/*    */ 
/*    */   public abstract void update(Image paramImage, int paramInt1, int paramInt2);
/*    */ 
/*    */   public abstract void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*    */ 
/*    */   public abstract void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
/*    */ 
/*    */   public abstract void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean);
/*    */ 
/*    */   public abstract void update(MediaFrame paramMediaFrame, boolean paramBoolean);
/*    */ 
/*    */   public abstract WrapMode getWrapMode();
/*    */ 
/*    */   public abstract void setWrapMode(WrapMode paramWrapMode);
/*    */ 
/*    */   public abstract boolean getLinearFiltering();
/*    */ 
/*    */   public abstract void setLinearFiltering(boolean paramBoolean);
/*    */ 
/*    */   public static enum WrapMode
/*    */   {
/* 49 */     CLAMP_TO_ZERO, 
/* 50 */     CLAMP_TO_EDGE, 
/* 51 */     REPEAT;
/*    */   }
/*    */ 
/*    */   public static enum Usage
/*    */   {
/* 39 */     DEFAULT, 
/* 40 */     DYNAMIC, 
/* 41 */     STATIC;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.Texture
 * JD-Core Version:    0.6.2
 */