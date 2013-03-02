/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import java.util.List;
/*     */ import javafx.beans.value.WritableValue;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class Styleable
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*     */   private ObservableMap<WritableValue, List<Style>> styleMap;
/*     */ 
/*     */   public abstract String getId();
/*     */ 
/*     */   public abstract List<String> getStyleClass();
/*     */ 
/*     */   public abstract String getStyle();
/*     */ 
/*     */   public abstract Styleable getStyleableParent();
/*     */ 
/*     */   public abstract List<StyleableProperty> getStyleableProperties();
/*     */ 
/*     */   public abstract Node getNode();
/*     */ 
/*     */   /** @deprecated */
/*     */   public ObservableMap<WritableValue, List<Style>> getStyleMap()
/*     */   {
/* 104 */     return this.styleMap;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setStyleMap(ObservableMap<WritableValue, List<Style>> paramObservableMap)
/*     */   {
/* 113 */     this.styleMap = paramObservableMap;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Styleable
 * JD-Core Version:    0.6.2
 */