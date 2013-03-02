/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class ExpressionHelper<T> extends ExpressionHelperBase
/*     */ {
/*     */   protected final ObservableValue<T> observable;
/*     */ 
/*     */   public static <T> ExpressionHelper<T> addListener(ExpressionHelper<T> paramExpressionHelper, ObservableValue<T> paramObservableValue, InvalidationListener paramInvalidationListener)
/*     */   {
/*  70 */     if ((paramObservableValue == null) || (paramInvalidationListener == null)) {
/*  71 */       throw new NullPointerException();
/*     */     }
/*  73 */     paramObservableValue.getValue();
/*  74 */     return paramExpressionHelper == null ? new SingleInvalidation(paramObservableValue, paramInvalidationListener, null) : paramExpressionHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <T> ExpressionHelper<T> removeListener(ExpressionHelper<T> paramExpressionHelper, InvalidationListener paramInvalidationListener) {
/*  78 */     if (paramInvalidationListener == null) {
/*  79 */       throw new NullPointerException();
/*     */     }
/*  81 */     return paramExpressionHelper == null ? null : paramExpressionHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <T> ExpressionHelper<T> addListener(ExpressionHelper<T> paramExpressionHelper, ObservableValue<T> paramObservableValue, ChangeListener<? super T> paramChangeListener) {
/*  85 */     if ((paramObservableValue == null) || (paramChangeListener == null)) {
/*  86 */       throw new NullPointerException();
/*     */     }
/*  88 */     return paramExpressionHelper == null ? new SingleChange(paramObservableValue, paramChangeListener, null) : paramExpressionHelper.addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <T> ExpressionHelper<T> removeListener(ExpressionHelper<T> paramExpressionHelper, ChangeListener<? super T> paramChangeListener) {
/*  92 */     if (paramChangeListener == null) {
/*  93 */       throw new NullPointerException();
/*     */     }
/*  95 */     return paramExpressionHelper == null ? null : paramExpressionHelper.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <T> void fireValueChangedEvent(ExpressionHelper<T> paramExpressionHelper) {
/*  99 */     if (paramExpressionHelper != null)
/* 100 */       paramExpressionHelper.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private ExpressionHelper(ObservableValue<T> paramObservableValue)
/*     */   {
/* 110 */     this.observable = paramObservableValue;
/*     */   }
/*     */ 
/*     */   protected abstract ExpressionHelper<T> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ExpressionHelper<T> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ExpressionHelper<T> addListener(ChangeListener<? super T> paramChangeListener);
/*     */ 
/*     */   protected abstract ExpressionHelper<T> removeListener(ChangeListener<? super T> paramChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent();
/*     */ 
/*     */   private static class Generic<T> extends ExpressionHelper<T>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private ChangeListener<? super T>[] changeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private boolean locked;
/*     */     private T currentValue;
/*     */ 
/*     */     private Generic(ObservableValue<T> paramObservableValue, InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 211 */       super(null);
/* 212 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 213 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(ObservableValue<T> paramObservableValue, ChangeListener<? super T> paramChangeListener1, ChangeListener<? super T> paramChangeListener2) {
/* 217 */       super(null);
/* 218 */       this.changeListeners = new ChangeListener[] { paramChangeListener1, paramChangeListener2 };
/* 219 */       this.changeSize = 2;
/* 220 */       this.currentValue = paramObservableValue.getValue();
/*     */     }
/*     */ 
/*     */     private Generic(ObservableValue<T> paramObservableValue, InvalidationListener paramInvalidationListener, ChangeListener<? super T> paramChangeListener) {
/* 224 */       super(null);
/* 225 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 226 */       this.invalidationSize = 1;
/* 227 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 228 */       this.changeSize = 1;
/* 229 */       this.currentValue = paramObservableValue.getValue();
/*     */     }
/*     */ 
/*     */     protected Generic<T> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 234 */       if (this.invalidationListeners == null) {
/* 235 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 236 */         this.invalidationSize = 1;
/*     */       } else {
/* 238 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 239 */         if (this.locked) {
/* 240 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 241 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 242 */         } else if (this.invalidationSize == i) {
/* 243 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 244 */           if (this.invalidationSize == i) {
/* 245 */             j = i * 3 / 2 + 1;
/* 246 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 249 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 251 */       return this;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 256 */       if (this.invalidationListeners != null) {
/* 257 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 258 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 259 */             if (this.invalidationSize == 1) {
/* 260 */               if (this.changeSize == 1) {
/* 261 */                 return new ExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/*     */               }
/* 263 */               this.invalidationListeners = null;
/* 264 */               this.invalidationSize = 0; break;
/* 265 */             }if ((this.invalidationSize == 2) && (this.changeSize == 0)) {
/* 266 */               return new ExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[(1 - i)], null);
/*     */             }
/* 268 */             int j = this.invalidationSize - i - 1;
/* 269 */             InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 270 */             if (this.locked) {
/* 271 */               this.invalidationListeners = new InvalidationListener[this.invalidationListeners.length];
/* 272 */               System.arraycopy(arrayOfInvalidationListener, 0, this.invalidationListeners, 0, i + 1);
/*     */             }
/* 274 */             if (j > 0) {
/* 275 */               System.arraycopy(arrayOfInvalidationListener, i + 1, this.invalidationListeners, i, j);
/*     */             }
/* 277 */             this.invalidationSize -= 1;
/* 278 */             if (!this.locked) {
/* 279 */               this.invalidationListeners[this.invalidationSize] = null;
/*     */             }
/*     */ 
/* 282 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 286 */       return this;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> addListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 291 */       if (this.changeListeners == null) {
/* 292 */         this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 293 */         this.changeSize = 1;
/*     */       } else {
/* 295 */         int i = this.changeListeners.length;
/*     */         int j;
/* 296 */         if (this.locked) {
/* 297 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 298 */           this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 299 */         } else if (this.changeSize == i) {
/* 300 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 301 */           if (this.changeSize == i) {
/* 302 */             j = i * 3 / 2 + 1;
/* 303 */             this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 306 */         this.changeListeners[(this.changeSize++)] = paramChangeListener;
/*     */       }
/* 308 */       if (this.changeSize == 1) {
/* 309 */         this.currentValue = this.observable.getValue();
/*     */       }
/* 311 */       return this;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 316 */       if (this.changeListeners != null) {
/* 317 */         for (int i = 0; i < this.changeSize; i++) {
/* 318 */           if (paramChangeListener.equals(this.changeListeners[i])) {
/* 319 */             if (this.changeSize == 1) {
/* 320 */               if (this.invalidationSize == 1) {
/* 321 */                 return new ExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/*     */               }
/* 323 */               this.changeListeners = null;
/* 324 */               this.changeSize = 0; break;
/* 325 */             }if ((this.changeSize == 2) && (this.invalidationSize == 0)) {
/* 326 */               return new ExpressionHelper.SingleChange(this.observable, this.changeListeners[(1 - i)], null);
/*     */             }
/* 328 */             int j = this.changeSize - i - 1;
/* 329 */             ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 330 */             if (this.locked) {
/* 331 */               this.changeListeners = new ChangeListener[this.changeListeners.length];
/* 332 */               System.arraycopy(arrayOfChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 334 */             if (j > 0) {
/* 335 */               System.arraycopy(arrayOfChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 337 */             this.changeSize -= 1;
/* 338 */             if (!this.locked) {
/* 339 */               this.changeListeners[this.changeSize] = null;
/*     */             }
/*     */ 
/* 342 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 346 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 351 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 352 */       int i = this.invalidationSize;
/* 353 */       ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 354 */       int j = this.changeSize;
/*     */       try
/*     */       {
/* 357 */         this.locked = true;
/* 358 */         for (int k = 0; k < i; k++) {
/* 359 */           arrayOfInvalidationListener[k].invalidated(this.observable);
/*     */         }
/* 361 */         if (j > 0) {
/* 362 */           Object localObject1 = this.currentValue;
/* 363 */           this.currentValue = this.observable.getValue();
/* 364 */           int m = !this.currentValue.equals(localObject1) ? 1 : this.currentValue == null ? 0 : localObject1 != null ? 1 : 0;
/* 365 */           if (m != 0)
/* 366 */             for (int n = 0; n < j; n++)
/* 367 */               arrayOfChangeListener[n].changed(this.observable, localObject1, this.currentValue);
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/* 372 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<T> extends ExpressionHelper<T>
/*     */   {
/*     */     private final ChangeListener<? super T> listener;
/*     */     private T currentValue;
/*     */ 
/*     */     private SingleChange(ObservableValue<T> paramObservableValue, ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 165 */       super(null);
/* 166 */       this.listener = paramChangeListener;
/* 167 */       this.currentValue = paramObservableValue.getValue();
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 172 */       return new ExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 177 */       return this;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> addListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 182 */       return new ExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 187 */       return paramChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 192 */       Object localObject = this.currentValue;
/* 193 */       this.currentValue = this.observable.getValue();
/* 194 */       int i = !this.currentValue.equals(localObject) ? 1 : this.currentValue == null ? 0 : localObject != null ? 1 : 0;
/* 195 */       if (i != 0)
/* 196 */         this.listener.changed(this.observable, localObject, this.currentValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<T> extends ExpressionHelper<T>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(ObservableValue<T> paramObservableValue, InvalidationListener paramInvalidationListener)
/*     */     {
/* 129 */       super(null);
/* 130 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 135 */       return new ExpressionHelper.Generic(this.observable, this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 140 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> addListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 145 */       return new ExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ExpressionHelper<T> removeListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 150 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 155 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.ExpressionHelper
 * JD-Core Version:    0.6.2
 */