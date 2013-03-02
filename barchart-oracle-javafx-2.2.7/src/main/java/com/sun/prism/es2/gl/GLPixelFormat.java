/*     */ package com.sun.prism.es2.gl;
/*     */ 
/*     */ public class GLPixelFormat
/*     */ {
/*     */   private final Attributes attributes;
/*     */   private final long nativeScreen;
/*     */   private long nativePFInfo;
/*     */ 
/*     */   protected GLPixelFormat(long paramLong, Attributes paramAttributes)
/*     */   {
/*  16 */     this.nativeScreen = paramLong;
/*  17 */     this.attributes = paramAttributes;
/*     */   }
/*     */ 
/*     */   public Attributes getAttributes() {
/*  21 */     return this.attributes;
/*     */   }
/*     */ 
/*     */   public long getNativeScreen() {
/*  25 */     return this.nativeScreen;
/*     */   }
/*     */ 
/*     */   protected void setNativePFInfo(long paramLong) {
/*  29 */     this.nativePFInfo = paramLong;
/*     */   }
/*     */ 
/*     */   public long getNativePFInfo() {
/*  33 */     return this.nativePFInfo; } 
/*     */   public static class Attributes { public static final int RED_SIZE = 0;
/*     */     public static final int GREEN_SIZE = 1;
/*     */     public static final int BLUE_SIZE = 2;
/*     */     public static final int ALPHA_SIZE = 3;
/*     */     public static final int DEPTH_SIZE = 4;
/*     */     public static final int DOUBLEBUFFER = 5;
/*     */     public static final int ONSCREEN = 6;
/*     */     public static final int NUM_ITEMS = 7;
/*     */     private boolean onScreen;
/*     */     private boolean doubleBuffer;
/*     */     private int alphaSize;
/*     */     private int blueSize;
/*     */     private int greenSize;
/*     */     private int redSize;
/*     */     private int depthSize;
/*     */ 
/*  57 */     public Attributes() { this.onScreen = true;
/*  58 */       this.doubleBuffer = true;
/*  59 */       this.depthSize = 24;
/*  60 */       this.redSize = (this.greenSize = this.blueSize = this.alphaSize = 8); }
/*     */ 
/*     */     public boolean isOnScreen()
/*     */     {
/*  64 */       return this.onScreen;
/*     */     }
/*     */ 
/*     */     public boolean isDoubleBuffer() {
/*  68 */       return this.doubleBuffer;
/*     */     }
/*     */ 
/*     */     public int getDepthSize() {
/*  72 */       return this.depthSize;
/*     */     }
/*     */ 
/*     */     public int getAlphaSize() {
/*  76 */       return this.alphaSize;
/*     */     }
/*     */ 
/*     */     public int getBlueSize() {
/*  80 */       return this.blueSize;
/*     */     }
/*     */ 
/*     */     public int getGreenSize() {
/*  84 */       return this.greenSize;
/*     */     }
/*     */ 
/*     */     public int getRedSize() {
/*  88 */       return this.redSize;
/*     */     }
/*     */ 
/*     */     public void setOnScreen(boolean paramBoolean) {
/*  92 */       this.onScreen = paramBoolean;
/*     */     }
/*     */ 
/*     */     public void setDoubleBuffer(boolean paramBoolean) {
/*  96 */       this.doubleBuffer = paramBoolean;
/*     */     }
/*     */ 
/*     */     public void setDepthSize(int paramInt) {
/* 100 */       this.depthSize = paramInt;
/*     */     }
/*     */ 
/*     */     public void setAlphaSize(int paramInt) {
/* 104 */       this.alphaSize = paramInt;
/*     */     }
/*     */ 
/*     */     public void setBlueSize(int paramInt) {
/* 108 */       this.blueSize = paramInt;
/*     */     }
/*     */ 
/*     */     public void setGreenSize(int paramInt) {
/* 112 */       this.greenSize = paramInt;
/*     */     }
/*     */ 
/*     */     public void setRedSize(int paramInt) {
/* 116 */       this.redSize = paramInt;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 120 */       return "onScreen: " + this.onScreen + "redSize : " + this.redSize + ", " + "greenSize : " + this.greenSize + ", " + "blueSize : " + this.blueSize + ", " + "alphaSize : " + this.alphaSize + ", " + "depthSize : " + this.depthSize + ", " + "doubleBuffer : " + this.doubleBuffer;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.gl.GLPixelFormat
 * JD-Core Version:    0.6.2
 */