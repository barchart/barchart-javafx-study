/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class PositionMapper
/*     */ {
/*     */   private ReadOnlyDoubleWrapper position;
/*     */   private DoubleProperty viewportSize;
/*     */   private IntegerProperty itemCount;
/*     */   private Callback<Integer, Double> itemSize;
/*     */ 
/*     */   private void setPosition(double paramDouble)
/*     */   {
/*  51 */     positionPropertyImpl().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPosition() {
/*  55 */     return this.position == null ? 0.0D : this.position.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyDoubleProperty positionProperty() {
/*  59 */     return positionPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyDoubleWrapper positionPropertyImpl() {
/*  63 */     if (this.position == null) {
/*  64 */       this.position = new ReadOnlyDoubleWrapper(this, "position");
/*     */     }
/*  66 */     return this.position;
/*     */   }
/*     */ 
/*     */   public final void setViewportSize(double paramDouble)
/*     */   {
/*  80 */     viewportSizeProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getViewportSize() {
/*  84 */     return this.viewportSize == null ? 0.0D : this.viewportSize.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty viewportSizeProperty() {
/*  88 */     if (this.viewportSize == null) {
/*  89 */       this.viewportSize = new SimpleDoubleProperty(this, "viewportSize");
/*     */     }
/*  91 */     return this.viewportSize;
/*     */   }
/*     */ 
/*     */   public final void setItemCount(int paramInt)
/*     */   {
/* 104 */     itemCountProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public final int getItemCount() {
/* 108 */     return this.itemCount == null ? 0 : this.itemCount.get();
/*     */   }
/*     */ 
/*     */   public final IntegerProperty itemCountProperty() {
/* 112 */     if (this.itemCount == null) {
/* 113 */       this.itemCount = new SimpleIntegerProperty(this, "itemCount");
/*     */     }
/* 115 */     return this.itemCount;
/*     */   }
/*     */ 
/*     */   private Callback<Integer, Double> getItemSize()
/*     */   {
/* 125 */     return this.itemSize;
/*     */   }
/* 127 */   public void setGetItemSize(Callback<Integer, Double> paramCallback) { this.itemSize = paramCallback; }
/*     */ 
/*     */ 
/*     */   public double computeViewportOffset(double paramDouble)
/*     */   {
/* 138 */     double d1 = Utils.clamp(0.0D, paramDouble, 1.0D);
/* 139 */     int i = getItemCount();
/* 140 */     double d2 = d1 * i;
/* 141 */     int j = (int)d2;
/* 142 */     double d3 = d2 - j;
/* 143 */     double d4 = ((Double)getItemSize().call(Integer.valueOf(j))).doubleValue();
/* 144 */     double d5 = d4 * d3;
/* 145 */     double d6 = getViewportSize() * d1;
/* 146 */     return d5 - d6;
/*     */   }
/*     */ 
/*     */   public void adjustPosition(double paramDouble)
/*     */   {
/* 160 */     setPosition(Utils.clamp(0.0D, paramDouble, 1.0D));
/*     */   }
/*     */ 
/*     */   public void adjustPositionToIndex(int paramInt) {
/* 164 */     if (getItemCount() <= 0)
/* 165 */       setPosition(0.0D);
/*     */     else
/* 167 */       adjustPosition(paramInt / getItemCount());
/*     */   }
/*     */ 
/*     */   public void adjustByPixelAmount(double paramDouble)
/*     */   {
/* 179 */     if (paramDouble == 0.0D) return;
/*     */ 
/* 189 */     int i = paramDouble > 0.0D ? 1 : 0;
/* 190 */     int j = getItemCount();
/* 191 */     double d1 = getPosition() * j;
/* 192 */     int k = (int)d1;
/* 193 */     if ((i != 0) && (k == j)) return;
/* 194 */     double d2 = ((Double)getItemSize().call(Integer.valueOf(k))).doubleValue();
/* 195 */     double d3 = d1 - k;
/* 196 */     double d4 = d2 * d3;
/*     */ 
/* 199 */     double d5 = 1.0D / j;
/*     */ 
/* 205 */     double d6 = computeOffsetForCell(k);
/* 206 */     double d7 = d2 + computeOffsetForCell(k + 1);
/*     */ 
/* 210 */     double d8 = d7 - d6;
/*     */ 
/* 213 */     double d9 = i != 0 ? paramDouble + d4 - getViewportSize() * getPosition() - d6 : -paramDouble + d7 - (d4 - getViewportSize() * getPosition());
/*     */ 
/* 221 */     double d10 = d5 * k;
/*     */ 
/* 225 */     while ((d9 > d8) && (((i != 0) && (k < j - 1)) || ((i == 0) && (k > 0)))) {
/* 226 */       if (i != 0) k++; else k--;
/* 227 */       d9 -= d8;
/* 228 */       d2 = ((Double)getItemSize().call(Integer.valueOf(k))).doubleValue();
/* 229 */       d6 = computeOffsetForCell(k);
/* 230 */       d7 = d2 + computeOffsetForCell(k + 1);
/* 231 */       d8 = d7 - d6;
/* 232 */       d10 = d5 * k;
/*     */     }
/*     */ 
/* 238 */     if (d9 > d8) {
/* 239 */       setPosition(i != 0 ? 1.0D : 0.0D);
/*     */     }
/*     */     else
/*     */     {
/*     */       double d11;
/* 240 */       if (i != 0) {
/* 241 */         d11 = d5 / Math.abs(d7 - d6);
/* 242 */         setPosition(d10 + d11 * d9);
/*     */       } else {
/* 244 */         d11 = d5 / Math.abs(d7 - d6);
/* 245 */         setPosition(d10 + d5 - d11 * d9);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int computeCurrentIndex()
/*     */   {
/* 254 */     return (int)(getPosition() * getItemCount());
/*     */   }
/*     */ 
/*     */   public double computeOffsetForCell(int paramInt)
/*     */   {
/* 265 */     double d1 = getItemCount();
/* 266 */     double d2 = Utils.clamp(0.0D, paramInt, d1) / d1;
/* 267 */     return -(getViewportSize() * d2);
/*     */   }
/*     */ 
/*     */   public void adjustByPixelChunk(double paramDouble)
/*     */   {
/* 275 */     setPosition(0.0D);
/* 276 */     adjustByPixelAmount(paramDouble);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.PositionMapper
 * JD-Core Version:    0.6.2
 */