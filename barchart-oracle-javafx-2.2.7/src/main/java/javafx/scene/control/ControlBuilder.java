/*     */ package javafx.scene.control;
/*     */ 
/*     */ import javafx.scene.ParentBuilder;
/*     */ 
/*     */ public abstract class ControlBuilder<B extends ControlBuilder<B>> extends ParentBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private ContextMenu contextMenu;
/*     */   private double maxHeight;
/*     */   private double maxWidth;
/*     */   private double minHeight;
/*     */   private double minWidth;
/*     */   private double prefHeight;
/*     */   private double prefWidth;
/*     */   private Skin<?> skin;
/*     */   private Tooltip tooltip;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Control paramControl) {
/*  18 */     super.applyTo(paramControl);
/*  19 */     int i = this.__set;
/*  20 */     while (i != 0) {
/*  21 */       int j = Integer.numberOfTrailingZeros(i);
/*  22 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  23 */       switch (j) { case 0:
/*  24 */         paramControl.setContextMenu(this.contextMenu); break;
/*     */       case 1:
/*  25 */         paramControl.setMaxHeight(this.maxHeight); break;
/*     */       case 2:
/*  26 */         paramControl.setMaxWidth(this.maxWidth); break;
/*     */       case 3:
/*  27 */         paramControl.setMinHeight(this.minHeight); break;
/*     */       case 4:
/*  28 */         paramControl.setMinWidth(this.minWidth); break;
/*     */       case 5:
/*  29 */         paramControl.setPrefHeight(this.prefHeight); break;
/*     */       case 6:
/*  30 */         paramControl.setPrefWidth(this.prefWidth); break;
/*     */       case 7:
/*  31 */         paramControl.setSkin(this.skin); break;
/*     */       case 8:
/*  32 */         paramControl.setTooltip(this.tooltip);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B contextMenu(ContextMenu paramContextMenu)
/*     */   {
/*  43 */     this.contextMenu = paramContextMenu;
/*  44 */     __set(0);
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxHeight(double paramDouble)
/*     */   {
/*  54 */     this.maxHeight = paramDouble;
/*  55 */     __set(1);
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   public B maxWidth(double paramDouble)
/*     */   {
/*  65 */     this.maxWidth = paramDouble;
/*  66 */     __set(2);
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public B minHeight(double paramDouble)
/*     */   {
/*  76 */     this.minHeight = paramDouble;
/*  77 */     __set(3);
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public B minWidth(double paramDouble)
/*     */   {
/*  87 */     this.minWidth = paramDouble;
/*  88 */     __set(4);
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefHeight(double paramDouble)
/*     */   {
/*  98 */     this.prefHeight = paramDouble;
/*  99 */     __set(5);
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   public B prefWidth(double paramDouble)
/*     */   {
/* 109 */     this.prefWidth = paramDouble;
/* 110 */     __set(6);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public B skin(Skin<?> paramSkin)
/*     */   {
/* 120 */     this.skin = paramSkin;
/* 121 */     __set(7);
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   public B tooltip(Tooltip paramTooltip)
/*     */   {
/* 131 */     this.tooltip = paramTooltip;
/* 132 */     __set(8);
/* 133 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ControlBuilder
 * JD-Core Version:    0.6.2
 */