/*     */ package com.sun.scenario.effect;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper.StateAccessor;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class Effect
/*     */ {
/*  54 */   public static final Effect DefaultInput = null;
/*     */   private WeakPCSList propListeners;
/*     */   private final PropertyChangeListener inputListener;
/*     */   private final List<Effect> inputs;
/*     */   private final List<Effect> unmodifiableInputs;
/*     */   private final int maxInputs;
/*     */ 
/*     */   protected Effect()
/*     */   {
/*  74 */     this.inputs = Collections.emptyList();
/*  75 */     this.unmodifiableInputs = this.inputs;
/*  76 */     this.maxInputs = 0;
/*  77 */     this.inputListener = null;
/*     */   }
/*     */ 
/*     */   protected Effect(Effect paramEffect)
/*     */   {
/*  86 */     this.inputs = new ArrayList(1);
/*  87 */     this.unmodifiableInputs = Collections.unmodifiableList(this.inputs);
/*  88 */     this.maxInputs = 1;
/*  89 */     this.inputListener = new InputChangeListener(null);
/*  90 */     setInput(0, paramEffect);
/*     */   }
/*     */ 
/*     */   protected Effect(Effect paramEffect1, Effect paramEffect2)
/*     */   {
/* 100 */     this.inputs = new ArrayList(2);
/* 101 */     this.unmodifiableInputs = Collections.unmodifiableList(this.inputs);
/* 102 */     this.maxInputs = 2;
/* 103 */     this.inputListener = new InputChangeListener(null);
/* 104 */     setInput(0, paramEffect1);
/* 105 */     setInput(1, paramEffect2);
/*     */   }
/*     */ 
/*     */   Object getState()
/*     */   {
/* 116 */     return null;
/*     */   }
/*     */ 
/*     */   public int getNumInputs()
/*     */   {
/* 125 */     return this.inputs.size();
/*     */   }
/*     */ 
/*     */   public final List<Effect> getInputs()
/*     */   {
/* 135 */     return this.unmodifiableInputs;
/*     */   }
/*     */ 
/*     */   protected void setInput(int paramInt, Effect paramEffect)
/*     */   {
/* 150 */     if ((paramInt < 0) || (paramInt >= this.maxInputs)) {
/* 151 */       throw new IllegalArgumentException("Index must be within allowable range");
/*     */     }
/*     */ 
/* 154 */     if (paramInt < this.inputs.size()) {
/* 155 */       Effect localEffect = (Effect)this.inputs.get(paramInt);
/* 156 */       if (localEffect != null) {
/* 157 */         localEffect.removePropertyChangeListener(this.inputListener);
/*     */       }
/* 159 */       this.inputs.set(paramInt, paramEffect);
/*     */     } else {
/* 161 */       this.inputs.add(paramEffect);
/*     */     }
/* 163 */     if (paramEffect != null) {
/* 164 */       paramEffect.addPropertyChangeListener(this.inputListener);
/*     */     }
/* 166 */     firePropertyChange("inputs", null, this.inputs);
/*     */   }
/*     */ 
/*     */   public static BaseBounds combineBounds(BaseBounds[] paramArrayOfBaseBounds)
/*     */   {
/* 181 */     Object localObject = null;
/* 182 */     if (paramArrayOfBaseBounds.length == 1)
/* 183 */       localObject = paramArrayOfBaseBounds[0];
/*     */     else {
/* 185 */       for (int i = 0; i < paramArrayOfBaseBounds.length; i++) {
/* 186 */         BaseBounds localBaseBounds = paramArrayOfBaseBounds[i];
/* 187 */         if ((localBaseBounds != null) && (!localBaseBounds.isEmpty())) {
/* 188 */           if (localObject == null) {
/* 189 */             localObject = new RectBounds();
/* 190 */             localObject = ((BaseBounds)localObject).deriveWithNewBounds(localBaseBounds);
/*     */           } else {
/* 192 */             localObject = ((BaseBounds)localObject).deriveWithUnion(localBaseBounds);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 197 */     if (localObject == null) {
/* 198 */       localObject = new RectBounds();
/*     */     }
/* 200 */     return localObject;
/*     */   }
/*     */ 
/*     */   public static Rectangle combineBounds(Rectangle[] paramArrayOfRectangle) {
/* 204 */     Rectangle localRectangle1 = null;
/* 205 */     if (paramArrayOfRectangle.length == 1)
/* 206 */       localRectangle1 = paramArrayOfRectangle[0];
/*     */     else {
/* 208 */       for (int i = 0; i < paramArrayOfRectangle.length; i++) {
/* 209 */         Rectangle localRectangle2 = paramArrayOfRectangle[i];
/* 210 */         if ((localRectangle2 != null) && (!localRectangle2.isEmpty())) {
/* 211 */           if (localRectangle1 == null)
/* 212 */             localRectangle1 = new Rectangle(localRectangle2);
/*     */           else {
/* 214 */             localRectangle1.add(localRectangle2);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 219 */     if (localRectangle1 == null) {
/* 220 */       localRectangle1 = new Rectangle();
/*     */     }
/* 222 */     return localRectangle1;
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 229 */     int i = paramArrayOfImageData.length;
/* 230 */     Rectangle[] arrayOfRectangle = new Rectangle[i];
/* 231 */     for (int j = 0; j < i; j++) {
/* 232 */       arrayOfRectangle[j] = paramArrayOfImageData[j].getTransformedBounds(paramRectangle);
/*     */     }
/* 234 */     Rectangle localRectangle = combineBounds(arrayOfRectangle);
/* 235 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public abstract ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect);
/*     */ 
/*     */   public static BaseBounds transformBounds(BaseTransform paramBaseTransform, BaseBounds paramBaseBounds)
/*     */   {
/* 296 */     if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/* 297 */       return paramBaseBounds;
/*     */     }
/* 299 */     Object localObject = new RectBounds();
/* 300 */     localObject = paramBaseTransform.transform(paramBaseBounds, (BaseBounds)localObject);
/* 301 */     return localObject;
/*     */   }
/*     */ 
/*     */   protected ImageData ensureTransform(FilterContext paramFilterContext, ImageData paramImageData, BaseTransform paramBaseTransform, Rectangle paramRectangle)
/*     */   {
/* 309 */     if ((paramBaseTransform == null) || (paramBaseTransform.isIdentity())) {
/* 310 */       return paramImageData;
/*     */     }
/* 312 */     if (!paramImageData.validate(paramFilterContext)) {
/* 313 */       paramImageData.unref();
/* 314 */       return new ImageData(paramFilterContext, null, new Rectangle());
/*     */     }
/* 316 */     return paramImageData.transform(paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public final BaseBounds getBounds()
/*     */   {
/* 350 */     return getBounds(null, null);
/*     */   }
/*     */ 
/*     */   Effect getDefaultedInput(int paramInt, Effect paramEffect) {
/* 354 */     return getDefaultedInput((Effect)this.inputs.get(paramInt), paramEffect);
/*     */   }
/*     */ 
/*     */   static Effect getDefaultedInput(Effect paramEffect1, Effect paramEffect2) {
/* 358 */     return paramEffect1 == null ? paramEffect2 : paramEffect1;
/*     */   }
/*     */ 
/*     */   public abstract BaseBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect);
/*     */ 
/*     */   public Point2D transform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 407 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   public Point2D untransform(Point2D paramPoint2D, Effect paramEffect)
/*     */   {
/* 440 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   public static Filterable createCompatibleImage(FilterContext paramFilterContext, int paramInt1, int paramInt2)
/*     */   {
/* 462 */     return Renderer.getRenderer(paramFilterContext).createCompatibleImage(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static Filterable getCompatibleImage(FilterContext paramFilterContext, int paramInt1, int paramInt2)
/*     */   {
/* 491 */     return Renderer.getRenderer(paramFilterContext).getCompatibleImage(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public static void releaseCompatibleImage(FilterContext paramFilterContext, Filterable paramFilterable)
/*     */   {
/* 504 */     Renderer.getRenderer(paramFilterContext).releaseCompatibleImage(paramFilterable);
/*     */   }
/*     */ 
/*     */   public abstract AccelType getAccelType(FilterContext paramFilterContext);
/*     */ 
/*     */   public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 577 */     this.propListeners = WeakPCSList.add(this.propListeners, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*     */   {
/* 587 */     this.propListeners = WeakPCSList.remove(this.propListeners, paramPropertyChangeListener);
/*     */   }
/*     */ 
/*     */   protected void firePropertyChange(String paramString, Object paramObject1, Object paramObject2)
/*     */   {
/* 602 */     this.propListeners = WeakPCSList.firePropertyChange(this.propListeners, this, paramString, paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  63 */     AccessHelper.setStateAccessor(new AccessHelper.StateAccessor() {
/*     */       public Object getState(Effect paramAnonymousEffect) {
/*  65 */         return paramAnonymousEffect.getState();
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static class WeakPCSList
/*     */   {
/*     */     WeakReference<PropertyChangeListener> delegateListener;
/*     */     WeakPCSList next;
/*     */ 
/*     */     private WeakPCSList(PropertyChangeListener paramPropertyChangeListener)
/*     */     {
/* 612 */       this.delegateListener = new WeakReference(paramPropertyChangeListener);
/*     */     }
/*     */ 
/*     */     public static WeakPCSList add(WeakPCSList paramWeakPCSList, PropertyChangeListener paramPropertyChangeListener)
/*     */     {
/* 618 */       WeakPCSList localWeakPCSList1 = null;
/* 619 */       WeakPCSList localWeakPCSList2 = null;
/* 620 */       int i = 0;
/*     */       WeakPCSList localWeakPCSList3;
/* 621 */       while (paramWeakPCSList != null) {
/* 622 */         localWeakPCSList3 = paramWeakPCSList.next;
/* 623 */         Object localObject = paramWeakPCSList.delegateListener.get();
/* 624 */         if (localObject == null) {
/* 625 */           if (localWeakPCSList2 != null)
/* 626 */             localWeakPCSList2.next = localWeakPCSList3;
/*     */         }
/*     */         else {
/* 629 */           if (localObject == paramPropertyChangeListener) {
/* 630 */             i = 1;
/*     */           }
/* 632 */           localWeakPCSList2 = paramWeakPCSList;
/* 633 */           if (localWeakPCSList1 == null) {
/* 634 */             localWeakPCSList1 = paramWeakPCSList;
/*     */           }
/*     */         }
/* 637 */         paramWeakPCSList = localWeakPCSList3;
/*     */       }
/* 639 */       if (i == 0) {
/* 640 */         localWeakPCSList3 = new WeakPCSList(paramPropertyChangeListener);
/* 641 */         if (localWeakPCSList1 == null) {
/* 642 */           return localWeakPCSList3;
/*     */         }
/* 644 */         if (localWeakPCSList2 != null) {
/* 645 */           localWeakPCSList2.next = localWeakPCSList3;
/*     */         }
/*     */       }
/* 648 */       return localWeakPCSList1;
/*     */     }
/*     */ 
/*     */     public static WeakPCSList remove(WeakPCSList paramWeakPCSList, PropertyChangeListener paramPropertyChangeListener)
/*     */     {
/* 654 */       WeakPCSList localWeakPCSList1 = null;
/* 655 */       WeakPCSList localWeakPCSList2 = null;
/* 656 */       while (paramWeakPCSList != null) {
/* 657 */         WeakPCSList localWeakPCSList3 = paramWeakPCSList.next;
/* 658 */         Object localObject = paramWeakPCSList.delegateListener.get();
/* 659 */         if ((localObject == null) || (localObject == paramPropertyChangeListener)) {
/* 660 */           if (localWeakPCSList2 != null)
/* 661 */             localWeakPCSList2.next = localWeakPCSList3;
/*     */         }
/*     */         else {
/* 664 */           localWeakPCSList2 = paramWeakPCSList;
/* 665 */           if (localWeakPCSList1 == null) {
/* 666 */             localWeakPCSList1 = paramWeakPCSList;
/*     */           }
/*     */         }
/* 669 */         paramWeakPCSList = localWeakPCSList3;
/*     */       }
/* 671 */       return localWeakPCSList1;
/*     */     }
/*     */ 
/*     */     public static WeakPCSList firePropertyChange(WeakPCSList paramWeakPCSList, Effect paramEffect, String paramString, Object paramObject1, Object paramObject2)
/*     */     {
/* 679 */       WeakPCSList localWeakPCSList1 = null;
/* 680 */       if (paramWeakPCSList != null) {
/* 681 */         PropertyChangeEvent localPropertyChangeEvent = new PropertyChangeEvent(paramEffect, paramString, paramObject1, paramObject2);
/*     */ 
/* 683 */         WeakPCSList localWeakPCSList2 = null;
/*     */         do {
/* 685 */           WeakPCSList localWeakPCSList3 = paramWeakPCSList.next;
/* 686 */           PropertyChangeListener localPropertyChangeListener = (PropertyChangeListener)paramWeakPCSList.delegateListener.get();
/* 687 */           if (localPropertyChangeListener == null) {
/* 688 */             if (localWeakPCSList2 != null)
/* 689 */               localWeakPCSList2.next = localWeakPCSList3;
/*     */           }
/*     */           else {
/* 692 */             localPropertyChangeListener.propertyChange(localPropertyChangeEvent);
/* 693 */             localWeakPCSList2 = paramWeakPCSList;
/* 694 */             if (localWeakPCSList1 == null) {
/* 695 */               localWeakPCSList1 = paramWeakPCSList;
/*     */             }
/*     */           }
/* 698 */           paramWeakPCSList = localWeakPCSList3;
/* 699 */         }while (paramWeakPCSList != null);
/*     */       }
/* 701 */       return localWeakPCSList1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum AccelType
/*     */   {
/* 518 */     INTRINSIC("Intrinsic"), 
/*     */ 
/* 523 */     NONE("CPU/Java"), 
/*     */ 
/* 529 */     SIMD("CPU/SIMD"), 
/*     */ 
/* 535 */     FIXED("CPU/Fixed"), 
/*     */ 
/* 540 */     OPENGL("OpenGL"), 
/*     */ 
/* 545 */     DIRECT3D("Direct3D");
/*     */ 
/*     */     private String text;
/*     */ 
/*     */     private AccelType(String paramString) {
/* 550 */       this.text = paramString;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 555 */       return this.text;
/*     */     }
/*     */   }
/*     */ 
/*     */   private class InputChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     private InputChangeListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*     */     {
/* 176 */       Effect.this.firePropertyChange("inputs", null, Effect.this.inputs);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.Effect
 * JD-Core Version:    0.6.2
 */