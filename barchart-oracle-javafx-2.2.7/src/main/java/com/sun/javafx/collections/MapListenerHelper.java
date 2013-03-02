/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelperBase;
/*     */ import java.util.Arrays;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ 
/*     */ public abstract class MapListenerHelper<K, V> extends ExpressionHelperBase
/*     */ {
/*     */   public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> paramMapListenerHelper, InvalidationListener paramInvalidationListener)
/*     */   {
/*  18 */     if (paramInvalidationListener == null) {
/*  19 */       throw new NullPointerException();
/*     */     }
/*  21 */     return paramMapListenerHelper == null ? new SingleInvalidation(paramInvalidationListener, null) : paramMapListenerHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> paramMapListenerHelper, InvalidationListener paramInvalidationListener) {
/*  25 */     if (paramInvalidationListener == null) {
/*  26 */       throw new NullPointerException();
/*     */     }
/*  28 */     return paramMapListenerHelper == null ? null : paramMapListenerHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapListenerHelper<K, V> addListener(MapListenerHelper<K, V> paramMapListenerHelper, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/*  32 */     if (paramMapChangeListener == null) {
/*  33 */       throw new NullPointerException();
/*     */     }
/*  35 */     return paramMapListenerHelper == null ? new SingleChange(paramMapChangeListener, null) : paramMapListenerHelper.addListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapListenerHelper<K, V> removeListener(MapListenerHelper<K, V> paramMapListenerHelper, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/*  39 */     if (paramMapChangeListener == null) {
/*  40 */       throw new NullPointerException();
/*     */     }
/*  42 */     return paramMapListenerHelper == null ? null : paramMapListenerHelper.removeListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> void fireValueChangedEvent(MapListenerHelper<K, V> paramMapListenerHelper, MapChangeListener.Change<? extends K, ? extends V> paramChange) {
/*  46 */     if (paramMapListenerHelper != null)
/*  47 */       paramMapListenerHelper.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   public static <K, V> boolean hasListeners(MapListenerHelper<K, V> paramMapListenerHelper)
/*     */   {
/*  52 */     return paramMapListenerHelper != null;
/*     */   }
/*     */ 
/*     */   protected abstract MapListenerHelper<K, V> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract MapListenerHelper<K, V> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);
/*     */ 
/*     */   protected abstract MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange);
/*     */ 
/*     */   private static class Generic<K, V> extends MapListenerHelper<K, V>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private MapChangeListener<? super K, ? super V>[] changeListeners;
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
/*     */     private Generic(MapChangeListener<? super K, ? super V> paramMapChangeListener1, MapChangeListener<? super K, ? super V> paramMapChangeListener2) {
/* 151 */       this.changeListeners = new MapChangeListener[] { paramMapChangeListener1, paramMapChangeListener2 };
/* 152 */       this.changeSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(InvalidationListener paramInvalidationListener, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/* 156 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 157 */       this.invalidationSize = 1;
/* 158 */       this.changeListeners = new MapChangeListener[] { paramMapChangeListener };
/* 159 */       this.changeSize = 1;
/*     */     }
/*     */ 
/*     */     protected Generic<K, V> addListener(InvalidationListener paramInvalidationListener)
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
/*     */     protected MapListenerHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 186 */       if (this.invalidationListeners != null) {
/* 187 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 188 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 189 */             if (this.invalidationSize == 1) {
/* 190 */               if (this.changeSize == 1) {
/* 191 */                 return new MapListenerHelper.SingleChange(this.changeListeners[0], null);
/*     */               }
/* 193 */               this.invalidationListeners = null;
/* 194 */               this.invalidationSize = 0; break;
/* 195 */             }if ((this.invalidationSize == 2) && (this.changeSize == 0)) {
/* 196 */               return new MapListenerHelper.SingleInvalidation(this.invalidationListeners[(1 - i)], null);
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
/*     */     protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 221 */       if (this.changeListeners == null) {
/* 222 */         this.changeListeners = new MapChangeListener[] { paramMapChangeListener };
/* 223 */         this.changeSize = 1;
/*     */       } else {
/* 225 */         int i = this.changeListeners.length;
/*     */         int j;
/* 226 */         if (this.locked) {
/* 227 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 228 */           this.changeListeners = ((MapChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 229 */         } else if (this.changeSize == i) {
/* 230 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 231 */           if (this.changeSize == i) {
/* 232 */             j = i * 3 / 2 + 1;
/* 233 */             this.changeListeners = ((MapChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 236 */         this.changeListeners[(this.changeSize++)] = paramMapChangeListener;
/*     */       }
/* 238 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 243 */       if (this.changeListeners != null) {
/* 244 */         for (int i = 0; i < this.changeSize; i++) {
/* 245 */           if (paramMapChangeListener.equals(this.changeListeners[i])) {
/* 246 */             if (this.changeSize == 1) {
/* 247 */               if (this.invalidationSize == 1) {
/* 248 */                 return new MapListenerHelper.SingleInvalidation(this.invalidationListeners[0], null);
/*     */               }
/* 250 */               this.changeListeners = null;
/* 251 */               this.changeSize = 0; break;
/* 252 */             }if ((this.changeSize == 2) && (this.invalidationSize == 0)) {
/* 253 */               return new MapListenerHelper.SingleChange(this.changeListeners[(1 - i)], null);
/*     */             }
/* 255 */             int j = this.changeSize - i - 1;
/* 256 */             MapChangeListener[] arrayOfMapChangeListener = this.changeListeners;
/* 257 */             if (this.locked) {
/* 258 */               this.changeListeners = new MapChangeListener[this.changeListeners.length];
/* 259 */               System.arraycopy(arrayOfMapChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 261 */             if (j > 0) {
/* 262 */               System.arraycopy(arrayOfMapChangeListener, i + 1, this.changeListeners, i, j);
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
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 278 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 279 */       int i = this.invalidationSize;
/* 280 */       MapChangeListener[] arrayOfMapChangeListener = this.changeListeners;
/* 281 */       int j = this.changeSize;
/*     */       try
/*     */       {
/* 284 */         this.locked = true;
/* 285 */         for (int k = 0; k < i; k++) {
/* 286 */           arrayOfInvalidationListener[k].invalidated(paramChange.getMap());
/*     */         }
/* 288 */         for (k = 0; k < j; k++)
/* 289 */           arrayOfMapChangeListener[k].onChanged(paramChange);
/*     */       }
/*     */       finally {
/* 292 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<K, V> extends MapListenerHelper<K, V>
/*     */   {
/*     */     private final MapChangeListener<? super K, ? super V> listener;
/*     */ 
/*     */     private SingleChange(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 108 */       this.listener = paramMapChangeListener;
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 113 */       return new MapListenerHelper.Generic(paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 118 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 123 */       return new MapListenerHelper.Generic(this.listener, paramMapChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 128 */       return paramMapChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 133 */       this.listener.onChanged(paramChange);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<K, V> extends MapListenerHelper<K, V>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(InvalidationListener paramInvalidationListener)
/*     */     {
/*  74 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  79 */       return new MapListenerHelper.Generic(this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*  84 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/*  89 */       return new MapListenerHelper.Generic(this.listener, paramMapChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapListenerHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/*  94 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/*  99 */       this.listener.invalidated(paramChange.getMap());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.MapListenerHelper
 * JD-Core Version:    0.6.2
 */