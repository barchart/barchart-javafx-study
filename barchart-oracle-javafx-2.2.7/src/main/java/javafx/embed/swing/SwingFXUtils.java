/*     */ package javafx.embed.swing;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import javafx.scene.image.Image;
/*     */ import javafx.scene.image.PixelFormat;
/*     */ import javafx.scene.image.PixelReader;
/*     */ import javafx.scene.image.PixelWriter;
/*     */ import javafx.scene.image.WritableImage;
/*     */ import javafx.scene.image.WritablePixelFormat;
/*     */ import sun.awt.image.IntegerComponentRaster;
/*     */ 
/*     */ public class SwingFXUtils
/*     */ {
/*     */   public static WritableImage toFXImage(BufferedImage paramBufferedImage, WritableImage paramWritableImage)
/*     */   {
/*  70 */     int i = paramBufferedImage.getWidth();
/*  71 */     int j = paramBufferedImage.getHeight();
/*  72 */     switch (paramBufferedImage.getType()) {
/*     */     case 2:
/*     */     case 3:
/*  75 */       break;
/*     */     default:
/*  77 */       BufferedImage localBufferedImage = new BufferedImage(i, j, 3);
/*     */ 
/*  79 */       Graphics2D localGraphics2D = localBufferedImage.createGraphics();
/*  80 */       localGraphics2D.drawImage(paramBufferedImage, 0, 0, null);
/*  81 */       localGraphics2D.dispose();
/*  82 */       paramBufferedImage = localBufferedImage;
/*     */     }
/*     */ 
/*  86 */     if (paramWritableImage != null) {
/*  87 */       int k = (int)paramWritableImage.getWidth();
/*  88 */       int m = (int)paramWritableImage.getHeight();
/*  89 */       if ((k < i) || (m < j)) {
/*  90 */         paramWritableImage = null;
/*  91 */       } else if ((i < k) || (j < m)) {
/*  92 */         arrayOfInt = new int[k];
/*  93 */         PixelWriter localPixelWriter2 = paramWritableImage.getPixelWriter();
/*  94 */         WritablePixelFormat localWritablePixelFormat1 = PixelFormat.getIntArgbPreInstance();
/*  95 */         if (i < k) {
/*  96 */           localPixelWriter2.setPixels(i, 0, k - i, j, localWritablePixelFormat1, arrayOfInt, 0, 0);
/*     */         }
/*  98 */         if (j < m) {
/*  99 */           localPixelWriter2.setPixels(0, j, k, m - j, localWritablePixelFormat1, arrayOfInt, 0, 0);
/*     */         }
/*     */       }
/*     */     }
/* 103 */     if (paramWritableImage == null) {
/* 104 */       paramWritableImage = new WritableImage(i, j);
/*     */     }
/* 106 */     PixelWriter localPixelWriter1 = paramWritableImage.getPixelWriter();
/* 107 */     IntegerComponentRaster localIntegerComponentRaster = (IntegerComponentRaster)paramBufferedImage.getRaster();
/* 108 */     int[] arrayOfInt = localIntegerComponentRaster.getDataStorage();
/* 109 */     int n = localIntegerComponentRaster.getDataOffset(0);
/* 110 */     int i1 = localIntegerComponentRaster.getScanlineStride();
/* 111 */     WritablePixelFormat localWritablePixelFormat2 = paramBufferedImage.isAlphaPremultiplied() ? PixelFormat.getIntArgbPreInstance() : PixelFormat.getIntArgbInstance();
/*     */ 
/* 114 */     localPixelWriter1.setPixels(0, 0, i, j, localWritablePixelFormat2, arrayOfInt, n, i1);
/* 115 */     return paramWritableImage;
/*     */   }
/*     */ 
/*     */   public static BufferedImage fromFXImage(Image paramImage, BufferedImage paramBufferedImage)
/*     */   {
/* 148 */     PixelReader localPixelReader = paramImage.getPixelReader();
/* 149 */     if (localPixelReader == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     int i = (int)paramImage.getWidth();
/* 153 */     int j = (int)paramImage.getHeight();
/* 154 */     if (paramBufferedImage != null) {
/* 155 */       int k = paramBufferedImage.getType();
/* 156 */       m = paramBufferedImage.getWidth();
/* 157 */       n = paramBufferedImage.getHeight();
/* 158 */       if ((m < i) || (n < j) || ((k != 2) && (k != 3)))
/*     */       {
/* 162 */         paramBufferedImage = null;
/* 163 */       } else if ((i < m) || (j < n)) {
/* 164 */         localObject = paramBufferedImage.createGraphics();
/* 165 */         ((Graphics2D)localObject).setComposite(AlphaComposite.Clear);
/* 166 */         ((Graphics2D)localObject).fillRect(0, 0, m, n);
/* 167 */         ((Graphics2D)localObject).dispose();
/*     */       }
/*     */     }
/* 170 */     if (paramBufferedImage == null) {
/* 171 */       paramBufferedImage = new BufferedImage(i, j, 3);
/*     */     }
/* 173 */     IntegerComponentRaster localIntegerComponentRaster = (IntegerComponentRaster)paramBufferedImage.getRaster();
/* 174 */     int m = localIntegerComponentRaster.getDataOffset(0);
/* 175 */     int n = localIntegerComponentRaster.getScanlineStride();
/* 176 */     Object localObject = localIntegerComponentRaster.getDataStorage();
/* 177 */     WritablePixelFormat localWritablePixelFormat = paramBufferedImage.isAlphaPremultiplied() ? PixelFormat.getIntArgbPreInstance() : PixelFormat.getIntArgbInstance();
/*     */ 
/* 180 */     localPixelReader.getPixels(0, 0, i, j, localWritablePixelFormat, (int[])localObject, m, n);
/* 181 */     return paramBufferedImage;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swing.SwingFXUtils
 * JD-Core Version:    0.6.2
 */