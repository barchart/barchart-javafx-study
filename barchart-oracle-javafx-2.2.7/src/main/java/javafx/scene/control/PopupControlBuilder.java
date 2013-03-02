/*     */ package javafx.scene.control;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.stage.PopupWindowBuilder;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class PopupControlBuilder<B extends PopupControlBuilder<B>> extends PopupWindowBuilder<B>
/*     */   implements Builder<PopupControl>
/*     */ {
/*     */   private int __set;
/*     */   private String id;
/*     */   private double maxHeight;
/*     */   private double maxWidth;
/*     */   private double minHeight;
/*     */   private double minWidth;
/*     */   private double prefHeight;
/*     */   private double prefWidth;
/*     */   private Skin<?> skin;
/*     */   private String style;
/*     */   private Collection<? extends String> styleClass;
/*     */ 
/*     */   public static PopupControlBuilder<?> create()
/*     */   {
/*  15 */     return new PopupControlBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(PopupControl paramPopupControl) {
/*  23 */     super.applyTo(paramPopupControl);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramPopupControl.setId(this.id); break;
/*     */       case 1:
/*  30 */         paramPopupControl.setMaxHeight(this.maxHeight); break;
/*     */       case 2:
/*  31 */         paramPopupControl.setMaxWidth(this.maxWidth); break;
/*     */       case 3:
/*  32 */         paramPopupControl.setMinHeight(this.minHeight); break;
/*     */       case 4:
/*  33 */         paramPopupControl.setMinWidth(this.minWidth); break;
/*     */       case 5:
/*  34 */         paramPopupControl.setPrefHeight(this.prefHeight); break;
/*     */       case 6:
/*  35 */         paramPopupControl.setPrefWidth(this.prefWidth); break;
/*     */       case 7:
/*  36 */         paramPopupControl.setSkin(this.skin); break;
/*     */       case 8:
/*  37 */         paramPopupControl.setStyle(this.style); break;
/*     */       case 9:
/*  38 */         paramPopupControl.getStyleClass().addAll(this.styleClass);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B id(String paramString)
/*     */   {
/*  49 */     this.id = paramString;
/*  50 */     __set(0);
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxHeight(double paramDouble)
/*     */   {
/*  60 */     this.maxHeight = paramDouble;
/*  61 */     __set(1);
/*  62 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/*  71 */     this.maxWidth = paramDouble;
/*  72 */     __set(2);
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */   public B minHeight(double paramDouble)
/*     */   {
/*  82 */     this.minHeight = paramDouble;
/*  83 */     __set(3);
/*  84 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/*  93 */     this.minWidth = paramDouble;
/*  94 */     __set(4);
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefHeight(double paramDouble)
/*     */   {
/* 104 */     this.prefHeight = paramDouble;
/* 105 */     __set(5);
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefWidth(double paramDouble)
/*     */   {
/* 115 */     this.prefWidth = paramDouble;
/* 116 */     __set(6);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public B skin(Skin<?> paramSkin)
/*     */   {
/* 126 */     this.skin = paramSkin;
/* 127 */     __set(7);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public B style(String paramString)
/*     */   {
/* 137 */     this.style = paramString;
/* 138 */     __set(8);
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(Collection<? extends String> paramCollection)
/*     */   {
/* 148 */     this.styleClass = paramCollection;
/* 149 */     __set(9);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public B styleClass(String[] paramArrayOfString)
/*     */   {
/* 157 */     return styleClass(Arrays.asList(paramArrayOfString));
/*     */   }
/*     */ 
/*     */   public PopupControl build()
/*     */   {
/* 164 */     PopupControl localPopupControl = new PopupControl();
/* 165 */     applyTo(localPopupControl);
/* 166 */     return localPopupControl;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.PopupControlBuilder
 * JD-Core Version:    0.6.2
 */