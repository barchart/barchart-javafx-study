/*     */ package com.sun.prism.impl.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.openpisces.AlphaConsumer;
/*     */ import com.sun.openpisces.Renderer;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class OpenPiscesRasterizer
/*     */   implements ShapeRasterizer
/*     */ {
/*  21 */   private static MaskData emptyData = MaskData.create(new byte[1], 0, 0, 1, 1);
/*     */   private static Consumer savedConsumer;
/*     */ 
/*     */   public MaskData getMaskData(Shape paramShape, BasicStroke paramBasicStroke, RectBounds paramRectBounds, BaseTransform paramBaseTransform, boolean paramBoolean)
/*     */   {
/*  32 */     if ((paramBasicStroke != null) && (paramBasicStroke.getType() != 0))
/*     */     {
/*  38 */       paramShape = paramBasicStroke.createStrokedShape(paramShape);
/*  39 */       paramBasicStroke = null;
/*     */     }
/*  41 */     if (paramRectBounds == null) {
/*  42 */       if (paramBasicStroke != null)
/*     */       {
/*  46 */         paramShape = paramBasicStroke.createStrokedShape(paramShape);
/*  47 */         paramBasicStroke = null;
/*     */       }
/*     */ 
/*  50 */       paramRectBounds = new RectBounds();
/*     */ 
/*  52 */       paramRectBounds = (RectBounds)paramBaseTransform.transform(paramShape.getBounds(), paramRectBounds);
/*     */     }
/*  54 */     Rectangle localRectangle = new Rectangle(paramRectBounds);
/*  55 */     if (localRectangle.isEmpty()) {
/*  56 */       return emptyData;
/*     */     }
/*  58 */     Renderer localRenderer = null;
/*  59 */     if ((paramShape instanceof Path2D)) {
/*  60 */       localRenderer = OpenPiscesPrismUtils.setupRenderer((Path2D)paramShape, paramBasicStroke, paramBaseTransform, localRectangle);
/*     */     }
/*  62 */     if (localRenderer == null) {
/*  63 */       localRenderer = OpenPiscesPrismUtils.setupRenderer(paramShape, paramBasicStroke, paramBaseTransform, localRectangle);
/*     */     }
/*  65 */     int i = localRenderer.getOutpixMinX();
/*  66 */     int j = localRenderer.getOutpixMinY();
/*  67 */     int k = localRenderer.getOutpixMaxX();
/*  68 */     int m = localRenderer.getOutpixMaxY();
/*  69 */     int n = k - i;
/*  70 */     int i1 = m - j;
/*  71 */     if ((n <= 0) || (i1 <= 0)) {
/*  72 */       return emptyData;
/*     */     }
/*     */ 
/*  75 */     Consumer localConsumer = savedConsumer;
/*  76 */     if ((localConsumer == null) || (n * i1 > localConsumer.getAlphaLength())) {
/*  77 */       int i2 = n * i1 + 4095 & 0xFFFFF000;
/*  78 */       savedConsumer = localConsumer = new Consumer(i2);
/*  79 */       if (PrismSettings.verbose) {
/*  80 */         System.out.println("new alphas");
/*     */       }
/*     */     }
/*  83 */     localConsumer.setBoundsNoClone(i, j, n, i1);
/*  84 */     localRenderer.produceAlphas(localConsumer);
/*  85 */     return localConsumer.getMaskData();
/*     */   }
/*     */ 
/*     */   private static class Consumer
/*     */     implements AlphaConsumer
/*     */   {
/*     */     static byte[] savedAlphaMap;
/*     */     int x;
/*     */     int y;
/*     */     int width;
/*     */     int height;
/*     */     byte[] alphas;
/*     */     byte[] alphaMap;
/*     */     ByteBuffer alphabuffer;
/*  94 */     MaskData maskdata = new MaskData();
/*     */ 
/*     */     public Consumer(int paramInt) {
/*  97 */       this.alphas = new byte[paramInt];
/*  98 */       this.alphabuffer = ByteBuffer.wrap(this.alphas);
/*     */     }
/*     */ 
/*     */     public void setBoundsNoClone(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 102 */       this.x = paramInt1;
/* 103 */       this.y = paramInt2;
/* 104 */       this.width = paramInt3;
/* 105 */       this.height = paramInt4;
/* 106 */       this.maskdata.update(this.alphabuffer, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */ 
/*     */     public int getOriginX()
/*     */     {
/* 111 */       return this.x;
/*     */     }
/*     */ 
/*     */     public int getOriginY()
/*     */     {
/* 116 */       return this.y;
/*     */     }
/*     */ 
/*     */     public int getWidth()
/*     */     {
/* 121 */       return this.width;
/*     */     }
/*     */ 
/*     */     public int getHeight()
/*     */     {
/* 126 */       return this.height;
/*     */     }
/*     */ 
/*     */     public byte[] getAlphasNoClone() {
/* 130 */       return this.alphas;
/*     */     }
/*     */ 
/*     */     public int getAlphaLength() {
/* 134 */       return this.alphas.length;
/*     */     }
/*     */ 
/*     */     public MaskData getMaskData() {
/* 138 */       return this.maskdata;
/*     */     }
/*     */ 
/*     */     public void setMaxAlpha(int paramInt)
/*     */     {
/* 143 */       byte[] arrayOfByte = savedAlphaMap;
/* 144 */       if ((arrayOfByte == null) || (arrayOfByte.length != paramInt + 1)) {
/* 145 */         arrayOfByte = new byte[paramInt + 1];
/* 146 */         for (int i = 0; i <= paramInt; i++) {
/* 147 */           arrayOfByte[i] = ((byte)((i * 255 + paramInt / 2) / paramInt));
/*     */         }
/* 149 */         savedAlphaMap = arrayOfByte;
/*     */       }
/* 151 */       this.alphaMap = arrayOfByte;
/*     */     }
/*     */ 
/*     */     public void setAndClearRelativeAlphas(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 160 */       int i = this.width;
/* 161 */       int j = (paramInt1 - this.y) * i;
/* 162 */       byte[] arrayOfByte1 = this.alphas;
/* 163 */       byte[] arrayOfByte2 = this.alphaMap;
/* 164 */       int k = 0;
/* 165 */       for (int m = 0; m < i; m++) {
/* 166 */         k += paramArrayOfInt[m];
/* 167 */         paramArrayOfInt[m] = 0;
/* 168 */         arrayOfByte1[(j + m)] = arrayOfByte2[k];
/*     */       }
/*     */     }
/*     */ 
/*     */     public void setAndClearRelativeAlphas2(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 175 */       if (paramInt3 >= paramInt2) {
/* 176 */         byte[] arrayOfByte1 = this.alphas;
/* 177 */         byte[] arrayOfByte2 = this.alphaMap;
/* 178 */         int i = paramInt2 - this.x;
/* 179 */         int j = paramInt3 - this.x;
/* 180 */         int k = this.width;
/* 181 */         int m = (paramInt1 - this.y) * k;
/*     */ 
/* 183 */         int n = 0;
/* 184 */         while (n < i) {
/* 185 */           arrayOfByte1[(m + n)] = 0;
/* 186 */           n++;
/*     */         }
/* 188 */         int i1 = 0;
/* 189 */         while (n <= j) {
/* 190 */           i1 += paramArrayOfInt[n];
/* 191 */           paramArrayOfInt[n] = 0;
/* 192 */           int i2 = arrayOfByte2[i1];
/* 193 */           arrayOfByte1[(m + n)] = i2;
/* 194 */           n++;
/*     */         }
/* 196 */         paramArrayOfInt[n] = 0;
/* 197 */         while (n < k) {
/* 198 */           arrayOfByte1[(m + n)] = 0;
/* 199 */           n++;
/*     */         }
/*     */       } else {
/* 202 */         Arrays.fill(paramArrayOfInt, 0);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.shape.OpenPiscesRasterizer
 * JD-Core Version:    0.6.2
 */