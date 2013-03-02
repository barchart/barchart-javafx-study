/*     */ package javafx.scene.effect;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.scenario.effect.light.DistantLight;
/*     */ import com.sun.scenario.effect.light.PointLight;
/*     */ import com.sun.scenario.effect.light.SpotLight;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.scene.paint.Color;
/*     */ 
/*     */ public abstract class Light
/*     */ {
/*     */   private com.sun.scenario.effect.light.Light peer;
/*     */   private ObjectProperty<Color> color;
/*     */   private BooleanProperty effectDirty;
/*     */ 
/*     */   protected Light()
/*     */   {
/*  47 */     impl_markDirty();
/*     */   }
/*     */ 
/*     */   abstract com.sun.scenario.effect.light.Light impl_createImpl();
/*     */ 
/*     */   com.sun.scenario.effect.light.Light impl_getImpl()
/*     */   {
/*  54 */     if (this.peer == null) {
/*  55 */       this.peer = impl_createImpl();
/*     */     }
/*  57 */     return this.peer;
/*     */   }
/*     */ 
/*     */   public final void setColor(Color paramColor)
/*     */   {
/*  72 */     colorProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getColor() {
/*  76 */     return this.color == null ? Color.WHITE : (Color)this.color.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> colorProperty() {
/*  80 */     if (this.color == null) {
/*  81 */       this.color = new ObjectPropertyBase(Color.WHITE)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  85 */           Light.this.impl_markDirty();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/*  90 */           return Light.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/*  95 */           return "color";
/*     */         }
/*     */       };
/*     */     }
/*  99 */     return this.color;
/*     */   }
/*     */ 
/*     */   void impl_sync() {
/* 103 */     if (impl_isEffectDirty()) {
/* 104 */       impl_update();
/* 105 */       impl_clearDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   void impl_update() {
/* 110 */     if (getColor() != null)
/* 111 */       impl_getImpl().setColor(Toolkit.getToolkit().toColor4f(getColor()));
/*     */   }
/*     */ 
/*     */   private void setEffectDirty(boolean paramBoolean)
/*     */   {
/* 117 */     effectDirtyProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   final BooleanProperty effectDirtyProperty() {
/* 121 */     if (this.effectDirty == null) {
/* 122 */       this.effectDirty = new SimpleBooleanProperty(this, "effectDirty");
/*     */     }
/* 124 */     return this.effectDirty;
/*     */   }
/*     */ 
/*     */   boolean impl_isEffectDirty() {
/* 128 */     return this.effectDirty == null ? false : this.effectDirty.get();
/*     */   }
/*     */ 
/*     */   final void impl_markDirty() {
/* 132 */     setEffectDirty(true);
/*     */   }
/*     */ 
/*     */   final void impl_clearDirty() {
/* 136 */     setEffectDirty(false);
/*     */   }
/*     */ 
/*     */   public static class Spot extends Light.Point
/*     */   {
/*     */     private DoubleProperty pointsAtX;
/*     */     private DoubleProperty pointsAtY;
/*     */     private DoubleProperty pointsAtZ;
/*     */     private DoubleProperty specularExponent;
/*     */ 
/*     */     public Spot()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Spot(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Color paramColor)
/*     */     {
/* 545 */       setX(paramDouble1);
/* 546 */       setY(paramDouble2);
/* 547 */       setZ(paramDouble3);
/* 548 */       setSpecularExponent(paramDouble4);
/* 549 */       setColor(paramColor);
/*     */     }
/*     */ 
/*     */     @Deprecated
/*     */     SpotLight impl_createImpl()
/*     */     {
/* 559 */       return new SpotLight();
/*     */     }
/*     */ 
/*     */     public final void setPointsAtX(double paramDouble)
/*     */     {
/* 574 */       pointsAtXProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getPointsAtX() {
/* 578 */       return this.pointsAtX == null ? 0.0D : this.pointsAtX.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty pointsAtXProperty() {
/* 582 */       if (this.pointsAtX == null) {
/* 583 */         this.pointsAtX = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 587 */             Light.Spot.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 592 */             return Light.Spot.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 597 */             return "pointsAtX";
/*     */           }
/*     */         };
/*     */       }
/* 601 */       return this.pointsAtX;
/*     */     }
/*     */ 
/*     */     public final void setPointsAtY(double paramDouble)
/*     */     {
/* 616 */       pointsAtYProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getPointsAtY() {
/* 620 */       return this.pointsAtY == null ? 0.0D : this.pointsAtY.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty pointsAtYProperty() {
/* 624 */       if (this.pointsAtY == null) {
/* 625 */         this.pointsAtY = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 629 */             Light.Spot.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 634 */             return Light.Spot.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 639 */             return "pointsAtY";
/*     */           }
/*     */         };
/*     */       }
/* 643 */       return this.pointsAtY;
/*     */     }
/*     */ 
/*     */     public final void setPointsAtZ(double paramDouble)
/*     */     {
/* 658 */       pointsAtZProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getPointsAtZ() {
/* 662 */       return this.pointsAtZ == null ? 0.0D : this.pointsAtZ.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty pointsAtZProperty() {
/* 666 */       if (this.pointsAtZ == null) {
/* 667 */         this.pointsAtZ = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 671 */             Light.Spot.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 676 */             return Light.Spot.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 681 */             return "pointsAtZ";
/*     */           }
/*     */         };
/*     */       }
/* 685 */       return this.pointsAtZ;
/*     */     }
/*     */ 
/*     */     public final void setSpecularExponent(double paramDouble)
/*     */     {
/* 701 */       specularExponentProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getSpecularExponent() {
/* 705 */       return this.specularExponent == null ? 1.0D : this.specularExponent.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty specularExponentProperty() {
/* 709 */       if (this.specularExponent == null) {
/* 710 */         this.specularExponent = new DoublePropertyBase(1.0D)
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 714 */             Light.Spot.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 719 */             return Light.Spot.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 724 */             return "specularExponent";
/*     */           }
/*     */         };
/*     */       }
/* 728 */       return this.specularExponent;
/*     */     }
/*     */ 
/*     */     void impl_update()
/*     */     {
/* 733 */       super.impl_update();
/* 734 */       SpotLight localSpotLight = (SpotLight)impl_getImpl();
/*     */ 
/* 736 */       localSpotLight.setPointsAtX((float)getPointsAtX());
/* 737 */       localSpotLight.setPointsAtY((float)getPointsAtY());
/* 738 */       localSpotLight.setPointsAtZ((float)getPointsAtZ());
/* 739 */       localSpotLight.setSpecularExponent((float)Utils.clamp(0.0D, getSpecularExponent(), 4.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Point extends Light
/*     */   {
/*     */     private DoubleProperty x;
/*     */     private DoubleProperty y;
/*     */     private DoubleProperty z;
/*     */ 
/*     */     public Point()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Point(double paramDouble1, double paramDouble2, double paramDouble3, Color paramColor)
/*     */     {
/* 337 */       setX(paramDouble1);
/* 338 */       setY(paramDouble2);
/* 339 */       setZ(paramDouble3);
/* 340 */       setColor(paramColor);
/*     */     }
/*     */ 
/*     */     @Deprecated
/*     */     PointLight impl_createImpl()
/*     */     {
/* 350 */       return new PointLight();
/*     */     }
/*     */ 
/*     */     public final void setX(double paramDouble)
/*     */     {
/* 365 */       xProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getX() {
/* 369 */       return this.x == null ? 0.0D : this.x.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty xProperty() {
/* 373 */       if (this.x == null) {
/* 374 */         this.x = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 378 */             Light.Point.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 383 */             return Light.Point.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 388 */             return "x";
/*     */           }
/*     */         };
/*     */       }
/* 392 */       return this.x;
/*     */     }
/*     */ 
/*     */     public final void setY(double paramDouble)
/*     */     {
/* 407 */       yProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getY() {
/* 411 */       return this.y == null ? 0.0D : this.y.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty yProperty() {
/* 415 */       if (this.y == null) {
/* 416 */         this.y = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 420 */             Light.Point.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 425 */             return Light.Point.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 430 */             return "y";
/*     */           }
/*     */         };
/*     */       }
/* 434 */       return this.y;
/*     */     }
/*     */ 
/*     */     public final void setZ(double paramDouble)
/*     */     {
/* 449 */       zProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getZ() {
/* 453 */       return this.z == null ? 0.0D : this.z.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty zProperty() {
/* 457 */       if (this.z == null) {
/* 458 */         this.z = new DoublePropertyBase()
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 462 */             Light.Point.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 467 */             return Light.Point.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 472 */             return "z";
/*     */           }
/*     */         };
/*     */       }
/* 476 */       return this.z;
/*     */     }
/*     */ 
/*     */     void impl_update()
/*     */     {
/* 481 */       super.impl_update();
/* 482 */       PointLight localPointLight = (PointLight)impl_getImpl();
/*     */ 
/* 484 */       localPointLight.setX((float)getX());
/* 485 */       localPointLight.setY((float)getY());
/* 486 */       localPointLight.setZ((float)getZ());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class Distant extends Light
/*     */   {
/*     */     private DoubleProperty azimuth;
/*     */     private DoubleProperty elevation;
/*     */ 
/*     */     public Distant()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Distant(double paramDouble1, double paramDouble2, Color paramColor)
/*     */     {
/* 184 */       setAzimuth(paramDouble1);
/* 185 */       setElevation(paramDouble2);
/* 186 */       setColor(paramColor);
/*     */     }
/*     */ 
/*     */     DistantLight impl_createImpl()
/*     */     {
/* 191 */       return new DistantLight();
/*     */     }
/*     */ 
/*     */     public final void setAzimuth(double paramDouble)
/*     */     {
/* 207 */       azimuthProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getAzimuth() {
/* 211 */       return this.azimuth == null ? 45.0D : this.azimuth.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty azimuthProperty() {
/* 215 */       if (this.azimuth == null) {
/* 216 */         this.azimuth = new DoublePropertyBase(45.0D)
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 220 */             Light.Distant.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 225 */             return Light.Distant.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 230 */             return "azimuth";
/*     */           }
/*     */         };
/*     */       }
/* 234 */       return this.azimuth;
/*     */     }
/*     */ 
/*     */     public final void setElevation(double paramDouble)
/*     */     {
/* 250 */       elevationProperty().set(paramDouble);
/*     */     }
/*     */ 
/*     */     public final double getElevation() {
/* 254 */       return this.elevation == null ? 45.0D : this.elevation.get();
/*     */     }
/*     */ 
/*     */     public final DoubleProperty elevationProperty() {
/* 258 */       if (this.elevation == null) {
/* 259 */         this.elevation = new DoublePropertyBase(45.0D)
/*     */         {
/*     */           public void invalidated()
/*     */           {
/* 263 */             Light.Distant.this.impl_markDirty();
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 268 */             return Light.Distant.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 273 */             return "elevation";
/*     */           }
/*     */         };
/*     */       }
/* 277 */       return this.elevation;
/*     */     }
/*     */ 
/*     */     void impl_update()
/*     */     {
/* 282 */       super.impl_update();
/* 283 */       DistantLight localDistantLight = (DistantLight)impl_getImpl();
/*     */ 
/* 285 */       localDistantLight.setAzimuth((float)getAzimuth());
/* 286 */       localDistantLight.setElevation((float)getElevation());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.Light
 * JD-Core Version:    0.6.2
 */