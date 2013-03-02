/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import com.sun.javafx.css.Stylesheet.Origin;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.WritableValue;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.Labeled;
/*     */ 
/*     */ public class LabeledImpl extends Label
/*     */ {
/*     */   private final Shuttler shuttler;
/*     */ 
/*     */   public LabeledImpl(Labeled paramLabeled)
/*     */   {
/*  49 */     this.shuttler = new Shuttler(this, paramLabeled);
/*     */   }
/*     */ 
/*     */   private static void initialize(Shuttler paramShuttler, LabeledImpl paramLabeledImpl, Labeled paramLabeled)
/*     */   {
/*  56 */     paramLabeledImpl.setText(paramLabeled.getText());
/*  57 */     paramLabeled.textProperty().addListener(paramShuttler);
/*     */ 
/*  59 */     paramLabeledImpl.setGraphic(paramLabeled.getGraphic());
/*  60 */     paramLabeled.graphicProperty().addListener(paramShuttler);
/*     */ 
/*  62 */     List localList = StyleableProperties.STYLEABLES_TO_MIRROR;
/*     */ 
/*  64 */     int i = 0; for (int j = localList.size(); i < j; i++) {
/*  65 */       StyleableProperty localStyleableProperty = (StyleableProperty)localList.get(i);
/*     */ 
/*  70 */       if (!"-fx-skin".equals(localStyleableProperty.getProperty()))
/*     */       {
/*  72 */         WritableValue localWritableValue = localStyleableProperty.getWritableValue(paramLabeled);
/*  73 */         if ((localWritableValue instanceof Observable))
/*     */         {
/*  75 */           ((Observable)localWritableValue).addListener(paramShuttler);
/*     */ 
/*  77 */           Stylesheet.Origin localOrigin = StyleableProperty.getOrigin(localWritableValue);
/*  78 */           localStyleableProperty.set(paramLabeledImpl, localWritableValue.getValue(), localOrigin);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class StyleableProperties
/*     */   {
/* 144 */     static final List<StyleableProperty> STYLEABLES_TO_MIRROR = Collections.unmodifiableList(localArrayList);
/*     */ 
/*     */     static
/*     */     {
/* 139 */       List localList1 = Labeled.impl_CSS_STYLEABLES();
/* 140 */       List localList2 = Parent.impl_CSS_STYLEABLES();
/* 141 */       ArrayList localArrayList = new ArrayList(localList1);
/*     */ 
/* 143 */       localArrayList.removeAll(localList2);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Shuttler
/*     */     implements InvalidationListener
/*     */   {
/*     */     private final LabeledImpl labeledImpl;
/*     */     private final Labeled labeled;
/*     */ 
/*     */     Shuttler(LabeledImpl paramLabeledImpl, Labeled paramLabeled)
/*     */     {
/*  89 */       this.labeledImpl = paramLabeledImpl;
/*  90 */       this.labeled = paramLabeled;
/*  91 */       LabeledImpl.initialize(this, paramLabeledImpl, paramLabeled);
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/*  97 */       if (paramObservable == this.labeled.textProperty()) {
/*  98 */         this.labeledImpl.setText(this.labeled.getText());
/*     */       }
/*     */       else
/*     */       {
/*     */         Object localObject;
/*  99 */         if (paramObservable == this.labeled.graphicProperty())
/*     */         {
/* 103 */           localObject = StyleableProperty.getOrigin(this.labeled.graphicProperty());
/* 104 */           if ((localObject == null) || (localObject == Stylesheet.Origin.USER)) {
/* 105 */             this.labeledImpl.setGraphic(this.labeled.getGraphic());
/*     */           }
/*     */         }
/* 108 */         else if ((paramObservable instanceof WritableValue)) {
/* 109 */           localObject = (WritableValue)paramObservable;
/* 110 */           StyleableProperty localStyleableProperty = StyleableProperty.getStyleableProperty((WritableValue)localObject);
/*     */ 
/* 112 */           if (localStyleableProperty != null) {
/* 113 */             Stylesheet.Origin localOrigin = StyleableProperty.getOrigin((WritableValue)localObject);
/* 114 */             localStyleableProperty.set(this.labeledImpl, ((WritableValue)localObject).getValue(), localOrigin);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.LabeledImpl
 * JD-Core Version:    0.6.2
 */