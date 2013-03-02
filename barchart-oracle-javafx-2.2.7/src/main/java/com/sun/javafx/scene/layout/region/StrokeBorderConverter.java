/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public final class StrokeBorderConverter extends StyleConverter<ParsedValue[], List<StrokeBorder>>
/*     */ {
/*     */   public static StrokeBorderConverter getInstance()
/*     */   {
/*  46 */     return Holder.STROKE_BORDER_CONVERTER;
/*     */   }
/*     */ 
/*     */   public List<StrokeBorder> convert(Map<StyleableProperty, Object> paramMap)
/*     */   {
/*  55 */     Paint[][] arrayOfPaint = (Paint[][])null;
/*  56 */     BorderStyle[][] arrayOfBorderStyle = (BorderStyle[][])null;
/*  57 */     Margins[] arrayOfMargins1 = null;
/*  58 */     Margins[] arrayOfMargins2 = null;
/*  59 */     Insets[] arrayOfInsets = null;
/*  60 */     List localList = StrokeBorder.impl_CSS_STYLEABLES();
/*  61 */     for (int i = 0; i < localList.size(); i++) {
/*  62 */       localObject1 = (StyleableProperty)localList.get(i);
/*  63 */       Object localObject2 = paramMap.get(localObject1);
/*  64 */       if (localObject2 != null)
/*     */       {
/*  68 */         if ("-fx-border-color".equals(((StyleableProperty)localObject1).getProperty()))
/*  69 */           arrayOfPaint = (Paint[][])localObject2;
/*  70 */         else if ("-fx-border-style".equals(((StyleableProperty)localObject1).getProperty()))
/*  71 */           arrayOfBorderStyle = (BorderStyle[][])localObject2;
/*  72 */         else if ("-fx-border-width".equals(((StyleableProperty)localObject1).getProperty()))
/*  73 */           arrayOfMargins1 = (Margins[])localObject2;
/*  74 */         else if ("-fx-border-radius".equals(((StyleableProperty)localObject1).getProperty()))
/*  75 */           arrayOfMargins2 = (Margins[])localObject2;
/*  76 */         else if ("-fx-border-insets".equals(((StyleableProperty)localObject1).getProperty()))
/*  77 */           arrayOfInsets = (Insets[])localObject2;
/*     */       }
/*     */     }
/*  80 */     i = arrayOfPaint != null ? arrayOfPaint.length : 0;
/*  81 */     Object localObject1 = new ArrayList();
/*  82 */     for (int j = 0; j < i; j++) {
/*  83 */       Object localObject3 = arrayOfBorderStyle != null ? arrayOfBorderStyle[java.lang.Math.min(j, arrayOfBorderStyle.length - 1)] : null;
/*  84 */       Object localObject4 = arrayOfMargins1 != null ? arrayOfMargins1[java.lang.Math.min(j, arrayOfMargins1.length - 1)] : null;
/*  85 */       Object localObject5 = arrayOfMargins2 != null ? arrayOfMargins2[java.lang.Math.min(j, arrayOfMargins2.length - 1)] : null;
/*  86 */       Insets localInsets = arrayOfInsets != null ? arrayOfInsets[java.lang.Math.min(j, arrayOfInsets.length - 1)] : null;
/*  87 */       StrokeBorder.Builder localBuilder = new StrokeBorder.Builder();
/*  88 */       localBuilder.setTopFill(arrayOfPaint[j][0]).setRightFill(arrayOfPaint[j][1]).setBottomFill(arrayOfPaint[j][2]).setLeftFill(arrayOfPaint[j][3]);
/*  89 */       if (localObject4 != null) {
/*  90 */         localBuilder.setTopWidth(localObject4.getTop()).setRightWidth(localObject4.getRight()).setBottomWidth(localObject4.getBottom()).setLeftWidth(localObject4.getLeft()).setProportionalWidth(localObject4.isProportional());
/*     */       }
/*  92 */       if (localObject5 != null) {
/*  93 */         localBuilder.setTopLeftCornerRadius(localObject5.getTop()).setTopRightCornerRadius(localObject5.getRight()).setBottomRightCornerRadius(localObject5.getBottom()).setBottomLeftCornerRadius(localObject5.getLeft()).setProportionalWidth(localObject5.isProportional());
/*     */       }
/*  95 */       if (localInsets != null) {
/*  96 */         localBuilder.setOffsets(localInsets);
/*     */       }
/*  98 */       if (localObject3 != null) {
/*  99 */         localBuilder.setTopStyle(localObject3[0]).setRightStyle(localObject3[1]).setBottomStyle(localObject3[2]).setLeftStyle(localObject3[3]);
/*     */       }
/* 101 */       ((List)localObject1).add(localBuilder.build());
/*     */     }
/* 103 */     return localObject1;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 108 */     return "StrokeBorderType";
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  41 */     private static final StrokeBorderConverter STROKE_BORDER_CONVERTER = new StrokeBorderConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.StrokeBorderConverter
 * JD-Core Version:    0.6.2
 */