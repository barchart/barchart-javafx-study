/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import com.sun.javafx.cursor.CursorFrame;
/*     */ import com.sun.javafx.cursor.ImageCursorFrame;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Point;
/*     */ import java.awt.image.BufferedImage;
/*     */ 
/*     */ class SwingCursors
/*     */ {
/*     */   private static Cursor createCustomCursor(ImageCursorFrame paramImageCursorFrame)
/*     */   {
/*  45 */     java.awt.Toolkit localToolkit = java.awt.Toolkit.getDefaultToolkit();
/*     */ 
/*  47 */     double d1 = paramImageCursorFrame.getWidth();
/*  48 */     double d2 = paramImageCursorFrame.getHeight();
/*  49 */     Dimension localDimension = localToolkit.getBestCursorSize((int)d1, (int)d2);
/*     */ 
/*  51 */     double d3 = paramImageCursorFrame.getHotspotX() * localDimension.getWidth() / d1;
/*  52 */     double d4 = paramImageCursorFrame.getHotspotY() * localDimension.getHeight() / d2;
/*  53 */     Point localPoint = new Point((int)d3, (int)d4);
/*     */ 
/*  55 */     com.sun.javafx.tk.Toolkit localToolkit1 = com.sun.javafx.tk.Toolkit.getToolkit();
/*     */ 
/*  57 */     BufferedImage localBufferedImage = (BufferedImage)localToolkit1.toExternalImage(paramImageCursorFrame.getPlatformImage(), BufferedImage.class);
/*     */ 
/*  62 */     return localToolkit.createCustomCursor(localBufferedImage, localPoint, null);
/*     */   }
/*     */ 
/*     */   static Cursor embedCursorToCursor(CursorFrame paramCursorFrame) {
/*  66 */     switch (1.$SwitchMap$com$sun$javafx$cursor$CursorType[paramCursorFrame.getCursorType().ordinal()]) {
/*     */     case 1:
/*  68 */       return Cursor.getPredefinedCursor(0);
/*     */     case 2:
/*  70 */       return Cursor.getPredefinedCursor(1);
/*     */     case 3:
/*  72 */       return Cursor.getPredefinedCursor(2);
/*     */     case 4:
/*  74 */       return Cursor.getPredefinedCursor(3);
/*     */     case 5:
/*  76 */       return Cursor.getPredefinedCursor(4);
/*     */     case 6:
/*  78 */       return Cursor.getPredefinedCursor(5);
/*     */     case 7:
/*  80 */       return Cursor.getPredefinedCursor(6);
/*     */     case 8:
/*  82 */       return Cursor.getPredefinedCursor(7);
/*     */     case 9:
/*  84 */       return Cursor.getPredefinedCursor(8);
/*     */     case 10:
/*  86 */       return Cursor.getPredefinedCursor(9);
/*     */     case 11:
/*  88 */       return Cursor.getPredefinedCursor(10);
/*     */     case 12:
/*  90 */       return Cursor.getPredefinedCursor(11);
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*  94 */       return Cursor.getPredefinedCursor(12);
/*     */     case 16:
/*  96 */       return Cursor.getPredefinedCursor(13);
/*     */     case 17:
/*     */     case 18:
/*     */     case 19:
/* 101 */       return Cursor.getPredefinedCursor(0);
/*     */     case 20:
/* 103 */       return null;
/*     */     case 21:
/* 105 */       return createCustomCursor((ImageCursorFrame)paramCursorFrame);
/*     */     }
/*     */ 
/* 108 */     return Cursor.getPredefinedCursor(0);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.SwingCursors
 * JD-Core Version:    0.6.2
 */