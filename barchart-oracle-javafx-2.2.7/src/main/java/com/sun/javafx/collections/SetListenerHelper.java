/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelperBase;
/*     */ import java.util.Arrays;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public abstract class SetListenerHelper<E> extends ExpressionHelperBase
/*     */ {
/*     */   public static <E> SetListenerHelper<E> addListener(SetListenerHelper<E> paramSetListenerHelper, InvalidationListener paramInvalidationListener)
/*     */   {
/*  18 */     if (paramInvalidationListener == null) {
/*  19 */       throw new NullPointerException();
/*     */     }
/*  21 */     return paramSetListenerHelper == null ? new SingleInvalidation(paramInvalidationListener, null) : paramSetListenerHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetListenerHelper<E> removeListener(SetListenerHelper<E> paramSetListenerHelper, InvalidationListener paramInvalidationListener) {
/*  25 */     if (paramInvalidationListener == null) {
/*  26 */       throw new NullPointerException();
/*     */     }
/*  28 */     return paramSetListenerHelper == null ? null : paramSetListenerHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetListenerHelper<E> addListener(SetListenerHelper<E> paramSetListenerHelper, SetChangeListener<? super E> paramSetChangeListener) {
/*  32 */     if (paramSetChangeListener == null) {
/*  33 */       throw new NullPointerException();
/*     */     }
/*  35 */     return paramSetListenerHelper == null ? new SingleChange(paramSetChangeListener, null) : paramSetListenerHelper.addListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetListenerHelper<E> removeListener(SetListenerHelper<E> paramSetListenerHelper, SetChangeListener<? super E> paramSetChangeListener) {
/*  39 */     if (paramSetChangeListener == null) {
/*  40 */       throw new NullPointerException();
/*     */     }
/*  42 */     return paramSetListenerHelper == null ? null : paramSetListenerHelper.removeListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(SetListenerHelper<E> paramSetListenerHelper, SetChangeListener.Change<E> paramChange) {
/*  46 */     if (paramSetListenerHelper != null)
/*  47 */       paramSetListenerHelper.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   public static <E> boolean hasListeners(SetListenerHelper<E> paramSetListenerHelper)
/*     */   {
/*  52 */     return paramSetListenerHelper != null;
/*     */   }
/*     */ 
/*     */   protected abstract SetListenerHelper<E> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract SetListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract SetListenerHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener);
/*     */ 
/*     */   protected abstract SetListenerHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(SetChangeListener.Change<E> paramChange);
/*     */ 
/*     */   private static class Generic<E> extends SetListenerHelper<E>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private SetChangeListener<? super E>[] changeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private boolean locked;
/*     */ 
/*     */     private Generic(InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 146 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 147 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(SetChangeListener<? super E> paramSetChangeListener1, SetChangeListener<? super E> paramSetChangeListener2) {
/* 151 */       this.changeListeners = new SetChangeListener[] { paramSetChangeListener1, paramSetChangeListener2 };
/* 152 */       this.changeSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(InvalidationListener paramInvalidationListener, SetChangeListener<? super E> paramSetChangeListener) {
/* 156 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 157 */       this.invalidationSize = 1;
/* 158 */       this.changeListeners = new SetChangeListener[] { paramSetChangeListener };
/* 159 */       this.changeSize = 1;
/*     */     }
/*     */ 
/*     */     protected Generic<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 164 */       if (this.invalidationListeners == null) {
/* 165 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 166 */         this.invalidationSize = 1;
/*     */       } else {
/* 168 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 169 */         if (this.locked) {
/* 170 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 171 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 172 */         } else if (this.invalidationSize == i) {
/* 173 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 174 */           if (this.invalidationSize == i) {
/* 175 */             j = i * 3 / 2 + 1;
/* 176 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 179 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 181 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 186 */       if (this.invalidationListeners != null) {
/* 187 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 188 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 189 */             if (this.invalidationSize == 1) {
/* 190 */               if (this.changeSize == 1) {
/* 191 */                 return new SetListenerHelper.SingleChange(this.changeListeners[0], null);
/*     */               }
/* 193 */               this.invalidationListeners = null;
/* 194 */               this.invalidationSize = 0; break;
/* 195 */             }if ((this.invalidationSize == 2) && (this.changeSize == 0)) {
/* 196 */               return new SetListenerHelper.SingleInvalidation(this.invalidationListeners[(1 - i)], null);
/*     */             }
/* 198 */             int j = this.invalidationSize - i - 1;
/* 199 */             InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 200 */             if (this.locked) {
/* 201 */               this.invalidationListeners = new InvalidationListener[this.invalidationListeners.length];
/* 202 */               System.arraycopy(arrayOfInvalidationListener, 0, this.invalidationListeners, 0, i + 1);
/*     */             }
/* 204 */             if (j > 0) {
/* 205 */               System.arraycopy(arrayOfInvalidationListener, i + 1, this.invalidationListeners, i, j);
/*     */             }
/* 207 */             this.invalidationSize -= 1;
/* 208 */             if (!this.locked) {
/* 209 */               this.invalidationListeners[this.invalidationSize] = null;
/*     */             }
/*     */ 
/* 212 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 216 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 221 */       if (this.changeListeners == null) {
/* 222 */         this.changeListeners = new SetChangeListener[] { paramSetChangeListener };
/* 223 */         this.changeSize = 1;
/*     */       } else {
/* 225 */         int i = this.changeListeners.length;
/*     */         int j;
/* 226 */         if (this.locked) {
/* 227 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 228 */           this.changeListeners = ((SetChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 229 */         } else if (this.changeSize == i) {
/* 230 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 231 */           if (this.changeSize == i) {
/* 232 */             j = i * 3 / 2 + 1;
/* 233 */             this.changeListeners = ((SetChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 236 */         this.changeListeners[(this.changeSize++)] = paramSetChangeListener;
/*     */       }
/* 238 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 243 */       if (this.changeListeners != null) {
/* 244 */         for (int i = 0; i < this.changeSize; i++) {
/* 245 */           if (paramSetChangeListener.equals(this.changeListeners[i])) {
/* 246 */             if (this.changeSize == 1) {
/* 247 */               if (this.invalidationSize == 1) {
/* 248 */                 return new SetListenerHelper.SingleInvalidation(this.invalidationListeners[0], null);
/*     */               }
/* 250 */               this.changeListeners = null;
/* 251 */               this.changeSize = 0; break;
/* 252 */             }if ((this.changeSize == 2) && (this.invalidationSize == 0)) {
/* 253 */               return new SetListenerHelper.SingleChange(this.changeListeners[(1 - i)], null);
/*     */             }
/* 255 */             int j = this.changeSize - i - 1;
/* 256 */             SetChangeListener[] arrayOfSetChangeListener = this.changeListeners;
/* 257 */             if (this.locked) {
/* 258 */               this.changeListeners = new SetChangeListener[this.changeListeners.length];
/* 259 */               System.arraycopy(arrayOfSetChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 261 */             if (j > 0) {
/* 262 */               System.arraycopy(arrayOfSetChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 264 */             this.changeSize -= 1;
/* 265 */             if (!this.locked) {
/* 266 */               this.changeListeners[this.changeSize] = null;
/*     */             }
/*     */ 
/* 269 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 273 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<E> paramChange)
/*     */     {
/* 278 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 279 */       int i = this.invalidationSize;
/* 280 */       SetChangeListener[] arrayOfSetChangeListener = this.changeListeners;
/* 281 */       int j = this.changeSize;
/*     */       try
/*     */       {
/* 284 */         this.locked = true;
/* 285 */         for (int k = 0; k < i; k++) {
/* 286 */           arrayOfInvalidationListener[k].invalidated(paramChange.getSet());
/*     */         }
/* 288 */         for (k = 0; k < j; k++)
/* 289 */           arrayOfSetChangeListener[k].onChanged(paramChange);
/*     */       }
/*     */       finally {
/* 292 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<E> extends SetListenerHelper<E>
/*     */   {
/*     */     private final SetChangeListener<? super E> listener;
/*     */ 
/*     */     private SingleChange(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 108 */       this.listener = paramSetChangeListener;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 113 */       return new SetListenerHelper.Generic(paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 118 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 123 */       return new SetListenerHelper.Generic(this.listener, paramSetChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 128 */       return paramSetChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<E> paramChange)
/*     */     {
/* 133 */       this.listener.onChanged(paramChange);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<E> extends SetListenerHelper<E>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(InvalidationListener paramInvalidationListener)
/*     */     {
/*  74 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  79 */       return new SetListenerHelper.Generic(this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  84 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/*  89 */       return new SetListenerHelper.Generic(this.listener, paramSetChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetListenerHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/*  94 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<E> paramChange)
/*     */     {
/*  99 */       this.listener.invalidated(paramChange.getSet());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.SetListenerHelper
 * JD-Core Version:    0.6.2
 */