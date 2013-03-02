/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.geometry.VPos;
/*     */ 
/*     */ public class RowConstraints extends ConstraintsBase
/*     */ {
/*     */   private DoubleProperty minHeight;
/*     */   private DoubleProperty prefHeight;
/*     */   private DoubleProperty maxHeight;
/*     */   private DoubleProperty percentHeight;
/*     */   private ObjectProperty<Priority> vgrow;
/*     */   private ObjectProperty<VPos> valignment;
/*     */   private BooleanProperty fillHeight;
/*     */ 
/*     */   public RowConstraints()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RowConstraints(double paramDouble)
/*     */   {
/*  88 */     this();
/*  89 */     setMinHeight((-1.0D / 0.0D));
/*  90 */     setPrefHeight(paramDouble);
/*  91 */     setMaxHeight((-1.0D / 0.0D));
/*     */   }
/*     */ 
/*     */   public RowConstraints(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 101 */     this();
/* 102 */     setMinHeight(paramDouble1);
/* 103 */     setPrefHeight(paramDouble2);
/* 104 */     setMaxHeight(paramDouble3);
/*     */   }
/*     */ 
/*     */   public RowConstraints(double paramDouble1, double paramDouble2, double paramDouble3, Priority paramPriority, VPos paramVPos, boolean paramBoolean)
/*     */   {
/* 113 */     this(paramDouble1, paramDouble2, paramDouble3);
/* 114 */     setVgrow(paramPriority);
/* 115 */     setValignment(paramVPos);
/* 116 */     setFillHeight(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final void setMinHeight(double paramDouble)
/*     */   {
/* 129 */     minHeightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMinHeight() {
/* 133 */     return this.minHeight == null ? -1.0D : this.minHeight.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty minHeightProperty() {
/* 137 */     if (this.minHeight == null) {
/* 138 */       this.minHeight = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 142 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 147 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 152 */           return "minHeight";
/*     */         }
/*     */       };
/*     */     }
/* 156 */     return this.minHeight;
/*     */   }
/*     */ 
/*     */   public final void setPrefHeight(double paramDouble)
/*     */   {
/* 169 */     prefHeightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPrefHeight() {
/* 173 */     return this.prefHeight == null ? -1.0D : this.prefHeight.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty prefHeightProperty() {
/* 177 */     if (this.prefHeight == null) {
/* 178 */       this.prefHeight = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 182 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 187 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 192 */           return "prefHeight";
/*     */         }
/*     */       };
/*     */     }
/* 196 */     return this.prefHeight;
/*     */   }
/*     */ 
/*     */   public final void setMaxHeight(double paramDouble)
/*     */   {
/* 209 */     maxHeightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMaxHeight() {
/* 213 */     return this.maxHeight == null ? -1.0D : this.maxHeight.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty maxHeightProperty() {
/* 217 */     if (this.maxHeight == null) {
/* 218 */       this.maxHeight = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 222 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 227 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 232 */           return "maxHeight";
/*     */         }
/*     */       };
/*     */     }
/* 236 */     return this.maxHeight;
/*     */   }
/*     */ 
/*     */   public final void setPercentHeight(double paramDouble)
/*     */   {
/* 250 */     percentHeightProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPercentHeight() {
/* 254 */     return this.percentHeight == null ? -1.0D : this.percentHeight.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty percentHeightProperty() {
/* 258 */     if (this.percentHeight == null) {
/* 259 */       this.percentHeight = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 263 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 268 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 273 */           return "percentHeight";
/*     */         }
/*     */       };
/*     */     }
/* 277 */     return this.percentHeight;
/*     */   }
/*     */ 
/*     */   public final void setVgrow(Priority paramPriority)
/*     */   {
/* 292 */     vgrowProperty().set(paramPriority);
/*     */   }
/*     */ 
/*     */   public final Priority getVgrow() {
/* 296 */     return this.vgrow == null ? null : (Priority)this.vgrow.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Priority> vgrowProperty() {
/* 300 */     if (this.vgrow == null) {
/* 301 */       this.vgrow = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 305 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 310 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 315 */           return "vgrow";
/*     */         }
/*     */       };
/*     */     }
/* 319 */     return this.vgrow;
/*     */   }
/*     */ 
/*     */   public final void setValignment(VPos paramVPos)
/*     */   {
/* 331 */     valignmentProperty().set(paramVPos);
/*     */   }
/*     */ 
/*     */   public final VPos getValignment() {
/* 335 */     return this.valignment == null ? null : (VPos)this.valignment.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<VPos> valignmentProperty() {
/* 339 */     if (this.valignment == null) {
/* 340 */       this.valignment = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 344 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 349 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 354 */           return "valignment";
/*     */         }
/*     */       };
/*     */     }
/* 358 */     return this.valignment;
/*     */   }
/*     */ 
/*     */   public final void setFillHeight(boolean paramBoolean)
/*     */   {
/* 371 */     fillHeightProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isFillHeight() {
/* 375 */     return this.fillHeight == null ? true : this.fillHeight.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty fillHeightProperty() {
/* 379 */     if (this.fillHeight == null) {
/* 380 */       this.fillHeight = new BooleanPropertyBase(true)
/*     */       {
/*     */         protected void invalidated() {
/* 383 */           RowConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 388 */           return RowConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 393 */           return "fillHeight";
/*     */         }
/*     */       };
/*     */     }
/* 397 */     return this.fillHeight;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 405 */     return "RowConstraints percentHeight=" + getPercentHeight() + " minHeight=" + getMinHeight() + " prefHeight=" + getPrefHeight() + " maxHeight=" + getMaxHeight() + " vgrow=" + getVgrow() + " fillHeight=" + isFillHeight() + " valignment=" + getValignment();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.RowConstraints
 * JD-Core Version:    0.6.2
 */