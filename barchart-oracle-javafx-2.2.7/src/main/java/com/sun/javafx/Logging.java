/*     */ package com.sun.javafx;
/*     */ 
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ 
/*     */ public class Logging
/*     */ {
/*  40 */   private static PlatformLogger layoutLogger = null;
/*     */ 
/*  57 */   private static PlatformLogger focusLogger = null;
/*     */ 
/*  74 */   private static PlatformLogger inputLogger = null;
/*     */ 
/*  91 */   private static PlatformLogger cssLogger = null;
/*     */ 
/* 108 */   private static PlatformLogger javafxLogger = null;
/*     */ 
/*     */   public static final PlatformLogger getLayoutLogger()
/*     */   {
/*  46 */     if (layoutLogger == null) {
/*  47 */       layoutLogger = Toolkit.getToolkit().getLogger("layout");
/*     */     }
/*  49 */     return layoutLogger;
/*     */   }
/*     */ 
/*     */   public static final PlatformLogger getFocusLogger()
/*     */   {
/*  63 */     if (focusLogger == null) {
/*  64 */       focusLogger = Toolkit.getToolkit().getLogger("focus");
/*     */     }
/*  66 */     return focusLogger;
/*     */   }
/*     */ 
/*     */   public static final PlatformLogger getInputLogger()
/*     */   {
/*  80 */     if (inputLogger == null) {
/*  81 */       inputLogger = Toolkit.getToolkit().getLogger("input");
/*     */     }
/*  83 */     return inputLogger;
/*     */   }
/*     */ 
/*     */   public static final PlatformLogger getCSSLogger()
/*     */   {
/*  97 */     if (cssLogger == null) {
/*  98 */       cssLogger = Toolkit.getToolkit().getLogger("css");
/*     */     }
/* 100 */     return cssLogger;
/*     */   }
/*     */ 
/*     */   public static final PlatformLogger getJavaFXLogger()
/*     */   {
/* 114 */     if (javafxLogger == null) {
/* 115 */       javafxLogger = Toolkit.getToolkit().getLogger("javafx");
/*     */     }
/* 117 */     return javafxLogger;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.Logging
 * JD-Core Version:    0.6.2
 */