/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelperBase;
/*     */ import java.util.Arrays;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ 
/*     */ public abstract class ListListenerHelper<E> extends ExpressionHelperBase
/*     */ {
/*     */   public static <E> ListListenerHelper<E> addListener(ListListenerHelper<E> paramListListenerHelper, InvalidationListener paramInvalidationListener)
/*     */   {
/*  18 */     if (paramInvalidationListener == null) {
/*  19 */       throw new NullPointerException();
/*     */     }
/*  21 */     return paramListListenerHelper == null ? new SingleInvalidation(paramInvalidationListener, null) : paramListListenerHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListListenerHelper<E> removeListener(ListListenerHelper<E> paramListListenerHelper, InvalidationListener paramInvalidationListener) {
/*  25 */     if (paramInvalidationListener == null) {
/*  26 */       throw new NullPointerException();
/*     */     }
/*  28 */     return paramListListenerHelper == null ? null : paramListListenerHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListListenerHelper<E> addListener(ListListenerHelper<E> paramListListenerHelper, ListChangeListener<? super E> paramListChangeListener) {
/*  32 */     if (paramListChangeListener == null) {
/*  33 */       throw new NullPointerException();
/*     */     }
/*  35 */     return paramListListenerHelper == null ? new SingleChange(paramListChangeListener, null) : paramListListenerHelper.addListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListListenerHelper<E> removeListener(ListListenerHelper<E> paramListListenerHelper, ListChangeListener<? super E> paramListChangeListener) {
/*  39 */     if (paramListChangeListener == null) {
/*  40 */       throw new NullPointerException();
/*     */     }
/*  42 */     return paramListListenerHelper == null ? null : paramListListenerHelper.removeListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(ListListenerHelper<E> paramListListenerHelper, ListChangeListener.Change<? extends E> paramChange) {
/*  46 */     if (paramListListenerHelper != null) {
/*  47 */       paramChange.reset();
/*  48 */       paramListListenerHelper.fireValueChangedEvent(paramChange);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static <E> boolean hasListeners(ListListenerHelper<E> paramListListenerHelper) {
/*  53 */     return paramListListenerHelper != null;
/*     */   }
/*     */ 
/*     */   protected abstract ListListenerHelper<E> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ListListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ListListenerHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener);
/*     */ 
/*     */   protected abstract ListListenerHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange);
/*     */ 
/*     */   private static class Generic<E> extends ListListenerHelper<E>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private ListChangeListener<? super E>[] changeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private boolean locked;
/*     */ 
/*     */     private Generic(InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 147 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 148 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(ListChangeListener<? super E> paramListChangeListener1, ListChangeListener<? super E> paramListChangeListener2) {
/* 152 */       this.changeListeners = new ListChangeListener[] { paramListChangeListener1, paramListChangeListener2 };
/* 153 */       this.changeSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(InvalidationListener paramInvalidationListener, ListChangeListener<? super E> paramListChangeListener) {
/* 157 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 158 */       this.invalidationSize = 1;
/* 159 */       this.changeListeners = new ListChangeListener[] { paramListChangeListener };
/* 160 */       this.changeSize = 1;
/*     */     }
/*     */ 
/*     */     protected Generic<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 165 */       if (this.invalidationListeners == null) {
/* 166 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 167 */         this.invalidationSize = 1;
/*     */       } else {
/* 169 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 170 */         if (this.locked) {
/* 171 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 172 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 173 */         } else if (this.invalidationSize == i) {
/* 174 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 175 */           if (this.invalidationSize == i) {
/* 176 */             j = i * 3 / 2 + 1;
/* 177 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 180 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 182 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 187 */       if (this.invalidationListeners != null) {
/* 188 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 189 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 190 */             if (this.invalidationSize == 1) {
/* 191 */               if (this.changeSize == 1) {
/* 192 */                 return new ListListenerHelper.SingleChange(this.changeListeners[0], null);
/*     */               }
/* 194 */               this.invalidationListeners = null;
/* 195 */               this.invalidationSize = 0; break;
/* 196 */             }if ((this.invalidationSize == 2) && (this.changeSize == 0)) {
/* 197 */               return new ListListenerHelper.SingleInvalidation(this.invalidationListeners[(1 - i)], null);
/*     */             }
/* 199 */             int j = this.invalidationSize - i - 1;
/* 200 */             InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 201 */             if (this.locked) {
/* 202 */               this.invalidationListeners = new InvalidationListener[this.invalidationListeners.length];
/* 203 */               System.arraycopy(arrayOfInvalidationListener, 0, this.invalidationListeners, 0, i + 1);
/*     */             }
/* 205 */             if (j > 0) {
/* 206 */               System.arraycopy(arrayOfInvalidationListener, i + 1, this.invalidationListeners, i, j);
/*     */             }
/* 208 */             this.invalidationSize -= 1;
/* 209 */             if (!this.locked) {
/* 210 */               this.invalidationListeners[this.invalidationSize] = null;
/*     */             }
/*     */ 
/* 213 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 217 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 222 */       if (this.changeListeners == null) {
/* 223 */         this.changeListeners = new ListChangeListener[] { paramListChangeListener };
/* 224 */         this.changeSize = 1;
/*     */       } else {
/* 226 */         int i = this.changeListeners.length;
/*     */         int j;
/* 227 */         if (this.locked) {
/* 228 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 229 */           this.changeListeners = ((ListChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 230 */         } else if (this.changeSize == i) {
/* 231 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 232 */           if (this.changeSize == i) {
/* 233 */             j = i * 3 / 2 + 1;
/* 234 */             this.changeListeners = ((ListChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 237 */         this.changeListeners[(this.changeSize++)] = paramListChangeListener;
/*     */       }
/* 239 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 244 */       if (this.changeListeners != null) {
/* 245 */         for (int i = 0; i < this.changeSize; i++) {
/* 246 */           if (paramListChangeListener.equals(this.changeListeners[i])) {
/* 247 */             if (this.changeSize == 1) {
/* 248 */               if (this.invalidationSize == 1) {
/* 249 */                 return new ListListenerHelper.SingleInvalidation(this.invalidationListeners[0], null);
/*     */               }
/* 251 */               this.changeListeners = null;
/* 252 */               this.changeSize = 0; break;
/* 253 */             }if ((this.changeSize == 2) && (this.invalidationSize == 0)) {
/* 254 */               return new ListListenerHelper.SingleChange(this.changeListeners[(1 - i)], null);
/*     */             }
/* 256 */             int j = this.changeSize - i - 1;
/* 257 */             ListChangeListener[] arrayOfListChangeListener = this.changeListeners;
/* 258 */             if (this.locked) {
/* 259 */               this.changeListeners = new ListChangeListener[this.changeListeners.length];
/* 260 */               System.arraycopy(arrayOfListChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 262 */             if (j > 0) {
/* 263 */               System.arraycopy(arrayOfListChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 265 */             this.changeSize -= 1;
/* 266 */             if (!this.locked) {
/* 267 */               this.changeListeners[this.changeSize] = null;
/*     */             }
/*     */ 
/* 270 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 274 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 279 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 280 */       int i = this.invalidationSize;
/* 281 */       ListChangeListener[] arrayOfListChangeListener = this.changeListeners;
/* 282 */       int j = this.changeSize;
/*     */       try
/*     */       {
/* 285 */         this.locked = true;
/* 286 */         for (int k = 0; k < i; k++) {
/* 287 */           arrayOfInvalidationListener[k].invalidated(paramChange.getList());
/*     */         }
/* 289 */         for (k = 0; k < j; k++) {
/* 290 */           paramChange.reset();
/* 291 */           arrayOfListChangeListener[k].onChanged(paramChange);
/*     */         }
/*     */       } finally {
/* 294 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<E> extends ListListenerHelper<E>
/*     */   {
/*     */     private final ListChangeListener<? super E> listener;
/*     */ 
/*     */     private SingleChange(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 109 */       this.listener = paramListChangeListener;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 114 */       return new ListListenerHelper.Generic(paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 119 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 124 */       return new ListListenerHelper.Generic(this.listener, paramListChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 129 */       return paramListChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 134 */       this.listener.onChanged(paramChange);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<E> extends ListListenerHelper<E>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(InvalidationListener paramInvalidationListener)
/*     */     {
/*  75 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  80 */       return new ListListenerHelper.Generic(this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  85 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/*  90 */       return new ListListenerHelper.Generic(this.listener, paramListChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListListenerHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/*  95 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 100 */       this.listener.invalidated(paramChange.getList());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.ListListenerHelper
 * JD-Core Version:    0.6.2
 */