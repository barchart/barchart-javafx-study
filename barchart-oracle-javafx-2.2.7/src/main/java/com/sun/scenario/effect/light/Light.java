/*     */ package com.sun.scenario.effect.light;
/*     */ 
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.beans.PropertyChangeSupport;
/*     */ 
/*     */ public abstract class Light
/*     */ {
/*  49 */   private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
/*     */   private final Type type;
/*     */   private Color4f color;
/*     */ 
/*     */   Light(Type paramType)
/*     */   {
/*  60 */     this(paramType, Color4f.WHITE);
/*     */   }
/*     */ 
/*     */   Light(Type paramType, Color4f paramColor4f)
/*     */   {
/*  71 */     if (paramType == null) {
/*  72 */       throw new InternalError("Light type must be non-null");
/*     */     }
/*  74 */     this.type = paramType;
/*  75 */     setColor(paramColor4f);
/*     */   }
/*     */ 
/*     */   public Type getType()
/*     */   {
/*  85 */     return this.type;
/*     */   }
/*     */ 
/*     */   public Color4f getColor()
/*     */   {
/*  94 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(Color4f paramColor4f)
/*     */   {
/* 110 */     if (paramColor4f == null) {
/* 111 */       throw new IllegalArgumentException("Color must be non-null");
/*     */     }
/* 113 */     Color4f localColor4f = this.color;
/* 114 */     this.color = paramColor4f;
/* 115 */     firePropertyChange("color", localColor4f, paramColor4f);
/*     */   }
/*     */ 
/*     */   public abstract float[] getNormalizedLightPosition();
/*     */ 
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 133 */     this.pcs.addPropertyChangeListener(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 143 */     this.pcs.removePropertyChangeListener(paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   void firePropertyChange(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 156 */     this.pcs.firePropertyChange(paramString, paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   public static enum Type
/*     */   {
/*  42 */     DISTANT, 
/*     */ 
/*  44 */     POINT, 
/*     */ 
/*  46 */     SPOT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.light.Light
 * JD-Core Version:    0.6.2
 */