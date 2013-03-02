/*     */ package com.sun.glass.ui;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class Screen
/*     */ {
/*     */   private long ptr;
/*     */   private int depth;
/*     */   private int x;
/*     */   private int y;
/*     */   private int width;
/*     */   private int height;
/*     */   private int visibleX;
/*     */   private int visibleY;
/*     */   private int visibleWidth;
/*     */   private int visibleHeight;
/*     */   private int resolutionX;
/*     */   private int resolutionY;
/*     */   private float scale;
/* 156 */   private static SettingsChangedCallback callback = null;
/*     */ 
/*     */   public static Screen getDeepestScreen()
/*     */   {
/*  11 */     return Application.GetApplication().staticScreen_getDeepestScreen();
/*     */   }
/*     */ 
/*     */   public static Screen getMainScreen() {
/*  15 */     return Application.GetApplication().staticScreen_getMainScreen();
/*     */   }
/*     */ 
/*     */   public static Screen getScreenForLocation(int x, int y) {
/*  19 */     return Application.GetApplication().staticScreen_getScreenForLocation(x, y);
/*     */   }
/*     */ 
/*     */   static Screen getScreenForPtr(long screenPtr)
/*     */   {
/*  24 */     return Application.GetApplication().staticScreen_getScreenForPtr(screenPtr);
/*     */   }
/*     */ 
/*     */   public static List<Screen> getScreens() {
/*  28 */     return Application.GetApplication().staticScreen_getScreens();
/*     */   }
/*     */ 
/*     */   public Screen()
/*     */   {
/*  51 */     this.ptr = 0L;
/*     */ 
/*  53 */     this.depth = 0;
/*     */ 
/*  55 */     this.x = 0;
/*  56 */     this.y = 0;
/*  57 */     this.width = 0;
/*  58 */     this.height = 0;
/*     */ 
/*  60 */     this.visibleX = 0;
/*  61 */     this.visibleY = 0;
/*  62 */     this.visibleWidth = 0;
/*  63 */     this.visibleHeight = 0;
/*     */ 
/*  65 */     this.resolutionX = 0;
/*  66 */     this.resolutionY = 0;
/*     */ 
/*  68 */     this.scale = 0.0F;
/*     */   }
/*     */ 
/*     */   protected Screen(long ptr, int depth, int x, int y, int width, int height, int visibleX, int visibleY, int visibleWidth, int visibleHeight, int resolutionX, int resolutionY, float scale)
/*     */   {
/*  80 */     this.ptr = ptr;
/*     */ 
/*  82 */     this.depth = depth;
/*     */ 
/*  84 */     this.x = x;
/*  85 */     this.y = y;
/*  86 */     this.width = width;
/*  87 */     this.height = height;
/*     */ 
/*  89 */     this.visibleX = visibleX;
/*  90 */     this.visibleY = visibleY;
/*  91 */     this.visibleWidth = visibleWidth;
/*  92 */     this.visibleHeight = visibleHeight;
/*     */ 
/*  94 */     this.resolutionX = resolutionX;
/*  95 */     this.resolutionY = resolutionY;
/*     */ 
/*  97 */     this.scale = scale;
/*     */   }
/*     */ 
/*     */   public int getDepth() {
/* 101 */     return this.depth;
/*     */   }
/*     */ 
/*     */   public int getX() {
/* 105 */     return this.x;
/*     */   }
/*     */ 
/*     */   public int getY() {
/* 109 */     return this.y;
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/* 113 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 117 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getVisibleX() {
/* 121 */     return this.visibleX;
/*     */   }
/*     */ 
/*     */   public int getVisibleY() {
/* 125 */     return this.visibleY;
/*     */   }
/*     */ 
/*     */   public int getVisibleWidth() {
/* 129 */     return this.visibleWidth;
/*     */   }
/*     */ 
/*     */   public int getVisibleHeight() {
/* 133 */     return this.visibleHeight;
/*     */   }
/*     */ 
/*     */   public int getResolutionX() {
/* 137 */     return this.resolutionX;
/*     */   }
/*     */ 
/*     */   public int getResolutionY() {
/* 141 */     return this.resolutionY;
/*     */   }
/*     */ 
/*     */   public float getScale() {
/* 145 */     return this.scale;
/*     */   }
/*     */ 
/*     */   public long getNativeScreen() {
/* 149 */     return this.ptr;
/*     */   }
/*     */ 
/*     */   public static void setCallback(SettingsChangedCallback callback)
/*     */   {
/* 159 */     callback = callback;
/*     */   }
/*     */ 
/*     */   public static SettingsChangedCallback getCallback() {
/* 163 */     return callback;
/*     */   }
/*     */ 
/*     */   private static void notifySettingsChanged() {
/* 167 */     if (callback != null)
/* 168 */       callback.settingsChanged();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 173 */     return "Screen:\n    ptr:" + getNativeScreen() + "\n" + "    depth:" + getDepth() + "\n" + "    x:" + getX() + "\n" + "    y:" + getY() + "\n" + "    width:" + getWidth() + "\n" + "    height:" + getHeight() + "\n" + "    visibleX:" + getVisibleX() + "\n" + "    visibleY:" + getVisibleY() + "\n" + "    visibleWidth:" + getVisibleWidth() + "\n" + "    visibleHeight:" + getVisibleHeight() + "\n" + "    scale:" + getScale() + "\n" + "    resolutionX:" + getResolutionX() + "\n" + "    resolutionY:" + getResolutionY() + "\n";
/*     */   }
/*     */ 
/*     */   public static abstract interface SettingsChangedCallback
/*     */   {
/*     */     public abstract void settingsChanged();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.Screen
 * JD-Core Version:    0.6.2
 */