/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.image.Image;
/*     */ 
/*     */ public final class BorderImageConverter extends StyleConverter<ParsedValue[], List<BorderImage>>
/*     */ {
/*     */   public static BorderImageConverter getInstance()
/*     */   {
/*  46 */     return Holder.BORDER_IMAGE_CONVERTER;
/*     */   }
/*     */ 
/*     */   public List<BorderImage> convert(Map<StyleableProperty, Object> paramMap)
/*     */   {
/*  55 */     String[] arrayOfString = null;
/*  56 */     BorderImage.BorderImageRepeat[] arrayOfBorderImageRepeat = null;
/*  57 */     BorderImage.BorderImageSlice[] arrayOfBorderImageSlice = null;
/*  58 */     Margins[] arrayOfMargins = null;
/*  59 */     Insets[] arrayOfInsets = null;
/*  60 */     List localList = BorderImage.impl_CSS_STYLEABLES();
/*  61 */     for (int i = 0; i < localList.size(); i++) {
/*  62 */       localObject1 = (StyleableProperty)localList.get(i);
/*  63 */       Object localObject2 = paramMap.get(localObject1);
/*  64 */       if (localObject2 != null)
/*     */       {
/*  67 */         if ("-fx-border-image-source".equals(((StyleableProperty)localObject1).getProperty()))
/*  68 */           arrayOfString = (String[])localObject2;
/*  69 */         else if ("-fx-border-image-repeat".equals(((StyleableProperty)localObject1).getProperty()))
/*  70 */           arrayOfBorderImageRepeat = (BorderImage.BorderImageRepeat[])localObject2;
/*  71 */         else if ("-fx-border-image-slice".equals(((StyleableProperty)localObject1).getProperty()))
/*  72 */           arrayOfBorderImageSlice = (BorderImage.BorderImageSlice[])localObject2;
/*  73 */         else if ("-fx-border-image-width".equals(((StyleableProperty)localObject1).getProperty()))
/*  74 */           arrayOfMargins = (Margins[])localObject2;
/*  75 */         else if ("-fx-border-image-insets".equals(((StyleableProperty)localObject1).getProperty()))
/*  76 */           arrayOfInsets = (Insets[])localObject2;
/*     */       }
/*     */     }
/*  79 */     i = arrayOfString != null ? arrayOfString.length : 0;
/*  80 */     Object localObject1 = new ArrayList();
/*  81 */     for (int j = 0; j < i; j++)
/*  82 */       if (arrayOfString[j] != null) {
/*  83 */         Object localObject3 = arrayOfBorderImageRepeat != null ? arrayOfBorderImageRepeat[java.lang.Math.min(j, arrayOfBorderImageRepeat.length - 1)] : null;
/*     */ 
/*  85 */         Object localObject4 = arrayOfBorderImageSlice != null ? arrayOfBorderImageSlice[java.lang.Math.min(j, arrayOfBorderImageSlice.length - 1)] : null;
/*     */ 
/*  87 */         Object localObject5 = arrayOfMargins != null ? arrayOfMargins[java.lang.Math.min(j, arrayOfMargins.length - 1)] : null;
/*     */ 
/*  89 */         Insets localInsets = arrayOfInsets != null ? arrayOfInsets[java.lang.Math.min(j, arrayOfInsets.length - 1)] : null;
/*     */ 
/*  91 */         BorderImage.Builder localBuilder = new BorderImage.Builder();
/*  92 */         localBuilder.setImage(new Image(arrayOfString[j]));
/*  93 */         if (localObject5 != null) {
/*  94 */           localBuilder.setTopWidth(localObject5.getTop()).setRightWidth(localObject5.getRight()).setBottomWidth(localObject5.getBottom()).setLeftWidth(localObject5.getLeft()).setProportionalWidth(localObject5.isProportional());
/*     */         }
/*     */ 
/* 100 */         if (localInsets != null) {
/* 101 */           localBuilder.setOffsets(localInsets);
/*     */         }
/* 103 */         if (localObject4 != null) {
/* 104 */           localBuilder.setFillCenter(localObject4.isFill()).setTopSlice(localObject4.getTop()).setRightSlice(localObject4.getRight()).setBottomSlice(localObject4.getBottom()).setLeftSlice(localObject4.getLeft()).setProportionalSlice(localObject4.isProportional());
/*     */         }
/*     */ 
/* 111 */         if (localObject3 != null) {
/* 112 */           localBuilder.setRepeatX(localObject3.getRepeatX()).setRepeatY(localObject3.getRepeatY());
/*     */         }
/*     */ 
/* 115 */         ((List)localObject1).add(localBuilder.build());
/*     */       }
/* 117 */     return localObject1;
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  41 */     private static final BorderImageConverter BORDER_IMAGE_CONVERTER = new BorderImageConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.BorderImageConverter
 * JD-Core Version:    0.6.2
 */