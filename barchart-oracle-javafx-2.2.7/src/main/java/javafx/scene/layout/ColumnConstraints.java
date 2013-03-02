/*     */ package javafx.scene.layout;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.geometry.HPos;
/*     */ 
/*     */ public class ColumnConstraints extends ConstraintsBase
/*     */ {
/*     */   private DoubleProperty minWidth;
/*     */   private DoubleProperty prefWidth;
/*     */   private DoubleProperty maxWidth;
/*     */   private DoubleProperty percentWidth;
/*     */   private ObjectProperty<Priority> hgrow;
/*     */   private ObjectProperty<HPos> halignment;
/*     */   private BooleanProperty fillWidth;
/*     */ 
/*     */   public ColumnConstraints()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ColumnConstraints(double paramDouble)
/*     */   {
/*  88 */     this();
/*  89 */     setMinWidth((-1.0D / 0.0D));
/*  90 */     setPrefWidth(paramDouble);
/*  91 */     setMaxWidth((-1.0D / 0.0D));
/*     */   }
/*     */ 
/*     */   public ColumnConstraints(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 101 */     this();
/* 102 */     setMinWidth(paramDouble1);
/* 103 */     setPrefWidth(paramDouble2);
/* 104 */     setMaxWidth(paramDouble3);
/*     */   }
/*     */ 
/*     */   public ColumnConstraints(double paramDouble1, double paramDouble2, double paramDouble3, Priority paramPriority, HPos paramHPos, boolean paramBoolean)
/*     */   {
/* 113 */     this(paramDouble1, paramDouble2, paramDouble3);
/* 114 */     setHgrow(paramPriority);
/* 115 */     setHalignment(paramHPos);
/* 116 */     setFillWidth(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final void setMinWidth(double paramDouble)
/*     */   {
/* 129 */     minWidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMinWidth() {
/* 133 */     return this.minWidth == null ? -1.0D : this.minWidth.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty minWidthProperty() {
/* 137 */     if (this.minWidth == null) {
/* 138 */       this.minWidth = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 142 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 147 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 152 */           return "minWidth";
/*     */         }
/*     */       };
/*     */     }
/* 156 */     return this.minWidth;
/*     */   }
/*     */ 
/*     */   public final void setPrefWidth(double paramDouble)
/*     */   {
/* 169 */     prefWidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPrefWidth() {
/* 173 */     return this.prefWidth == null ? -1.0D : this.prefWidth.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty prefWidthProperty() {
/* 177 */     if (this.prefWidth == null) {
/* 178 */       this.prefWidth = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 182 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 187 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 192 */           return "prefWidth";
/*     */         }
/*     */       };
/*     */     }
/* 196 */     return this.prefWidth;
/*     */   }
/*     */ 
/*     */   public final void setMaxWidth(double paramDouble)
/*     */   {
/* 209 */     maxWidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMaxWidth() {
/* 213 */     return this.maxWidth == null ? -1.0D : this.maxWidth.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty maxWidthProperty() {
/* 217 */     if (this.maxWidth == null) {
/* 218 */       this.maxWidth = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 222 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 227 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 232 */           return "maxWidth";
/*     */         }
/*     */       };
/*     */     }
/* 236 */     return this.maxWidth;
/*     */   }
/*     */ 
/*     */   public final void setPercentWidth(double paramDouble)
/*     */   {
/* 250 */     percentWidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPercentWidth() {
/* 254 */     return this.percentWidth == null ? -1.0D : this.percentWidth.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty percentWidthProperty() {
/* 258 */     if (this.percentWidth == null) {
/* 259 */       this.percentWidth = new DoublePropertyBase(-1.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 263 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 268 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 273 */           return "percentWidth";
/*     */         }
/*     */       };
/*     */     }
/* 277 */     return this.percentWidth;
/*     */   }
/*     */ 
/*     */   public final void setHgrow(Priority paramPriority)
/*     */   {
/* 293 */     hgrowProperty().set(paramPriority);
/*     */   }
/*     */ 
/*     */   public final Priority getHgrow() {
/* 297 */     return this.hgrow == null ? null : (Priority)this.hgrow.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Priority> hgrowProperty() {
/* 301 */     if (this.hgrow == null) {
/* 302 */       this.hgrow = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 306 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 311 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 316 */           return "hgrow";
/*     */         }
/*     */       };
/*     */     }
/* 320 */     return this.hgrow;
/*     */   }
/*     */ 
/*     */   public final void setHalignment(HPos paramHPos)
/*     */   {
/* 333 */     halignmentProperty().set(paramHPos);
/*     */   }
/*     */ 
/*     */   public final HPos getHalignment() {
/* 337 */     return this.halignment == null ? null : (HPos)this.halignment.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<HPos> halignmentProperty() {
/* 341 */     if (this.halignment == null) {
/* 342 */       this.halignment = new ObjectPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 346 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 351 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 356 */           return "halignment";
/*     */         }
/*     */       };
/*     */     }
/* 360 */     return this.halignment;
/*     */   }
/*     */ 
/*     */   public final void setFillWidth(boolean paramBoolean)
/*     */   {
/* 374 */     fillWidthProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isFillWidth() {
/* 378 */     return this.fillWidth == null ? true : this.fillWidth.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty fillWidthProperty() {
/* 382 */     if (this.fillWidth == null) {
/* 383 */       this.fillWidth = new BooleanPropertyBase(true)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 387 */           ColumnConstraints.this.requestLayout();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 392 */           return ColumnConstraints.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 397 */           return "fillWidth";
/*     */         }
/*     */       };
/*     */     }
/* 401 */     return this.fillWidth;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 409 */     return "ColumnConstraints percentWidth=" + getPercentWidth() + " minWidth=" + getMinWidth() + " prefWidth=" + getPrefWidth() + " maxWidth=" + getMaxWidth() + " hgrow=" + getHgrow() + " fillWidth=" + isFillWidth() + " halignment=" + getHalignment();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.ColumnConstraints
 * JD-Core Version:    0.6.2
 */