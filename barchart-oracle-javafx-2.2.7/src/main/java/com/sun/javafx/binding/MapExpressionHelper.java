/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableMapValue;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class MapExpressionHelper<K, V> extends ExpressionHelperBase
/*     */ {
/*     */   protected final ObservableMapValue<K, V> observable;
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> addListener(MapExpressionHelper<K, V> paramMapExpressionHelper, ObservableMapValue<K, V> paramObservableMapValue, InvalidationListener paramInvalidationListener)
/*     */   {
/*  65 */     if ((paramObservableMapValue == null) || (paramInvalidationListener == null)) {
/*  66 */       throw new NullPointerException();
/*     */     }
/*  68 */     paramObservableMapValue.getValue();
/*  69 */     return paramMapExpressionHelper == null ? new SingleInvalidation(paramObservableMapValue, paramInvalidationListener, null) : paramMapExpressionHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> removeListener(MapExpressionHelper<K, V> paramMapExpressionHelper, InvalidationListener paramInvalidationListener) {
/*  73 */     if (paramInvalidationListener == null) {
/*  74 */       throw new NullPointerException();
/*     */     }
/*  76 */     return paramMapExpressionHelper == null ? null : paramMapExpressionHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> addListener(MapExpressionHelper<K, V> paramMapExpressionHelper, ObservableMapValue<K, V> paramObservableMapValue, ChangeListener<? super ObservableMap<K, V>> paramChangeListener) {
/*  80 */     if ((paramObservableMapValue == null) || (paramChangeListener == null)) {
/*  81 */       throw new NullPointerException();
/*     */     }
/*  83 */     return paramMapExpressionHelper == null ? new SingleChange(paramObservableMapValue, paramChangeListener, null) : paramMapExpressionHelper.addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> removeListener(MapExpressionHelper<K, V> paramMapExpressionHelper, ChangeListener<? super ObservableMap<K, V>> paramChangeListener) {
/*  87 */     if (paramChangeListener == null) {
/*  88 */       throw new NullPointerException();
/*     */     }
/*  90 */     return paramMapExpressionHelper == null ? null : paramMapExpressionHelper.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> addListener(MapExpressionHelper<K, V> paramMapExpressionHelper, ObservableMapValue<K, V> paramObservableMapValue, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/*  94 */     if ((paramObservableMapValue == null) || (paramMapChangeListener == null)) {
/*  95 */       throw new NullPointerException();
/*     */     }
/*  97 */     return paramMapExpressionHelper == null ? new SingleMapChange(paramObservableMapValue, paramMapChangeListener, null) : paramMapExpressionHelper.addListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpressionHelper<K, V> removeListener(MapExpressionHelper<K, V> paramMapExpressionHelper, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/* 101 */     if (paramMapChangeListener == null) {
/* 102 */       throw new NullPointerException();
/*     */     }
/* 104 */     return paramMapExpressionHelper == null ? null : paramMapExpressionHelper.removeListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public static <K, V> void fireValueChangedEvent(MapExpressionHelper<K, V> paramMapExpressionHelper) {
/* 108 */     if (paramMapExpressionHelper != null)
/* 109 */       paramMapExpressionHelper.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public static <K, V> void fireValueChangedEvent(MapExpressionHelper<K, V> paramMapExpressionHelper, MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/* 114 */     if (paramMapExpressionHelper != null)
/* 115 */       paramMapExpressionHelper.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   protected MapExpressionHelper(ObservableMapValue<K, V> paramObservableMapValue)
/*     */   {
/* 125 */     this.observable = paramObservableMapValue;
/*     */   }
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener);
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener);
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);
/*     */ 
/*     */   protected abstract MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent();
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange);
/*     */ 
/*     */   public static class SimpleChange<K, V> extends MapChangeListener.Change<K, V>
/*     */   {
/*     */     private K key;
/*     */     private V old;
/*     */     private V added;
/*     */ 
/*     */     public SimpleChange(ObservableMap<K, V> paramObservableMap)
/*     */     {
/* 665 */       super();
/*     */     }
/*     */ 
/*     */     public SimpleChange(ObservableMap<K, V> paramObservableMap, MapChangeListener.Change<? extends K, ? extends V> paramChange) {
/* 669 */       super();
/* 670 */       this.key = paramChange.getKey();
/* 671 */       this.old = paramChange.getValueRemoved();
/* 672 */       this.added = paramChange.getValueAdded();
/*     */     }
/*     */ 
/*     */     public SimpleChange<K, V> setRemoved(K paramK, V paramV) {
/* 676 */       this.key = paramK;
/* 677 */       this.old = paramV;
/* 678 */       this.added = null;
/* 679 */       return this;
/*     */     }
/*     */ 
/*     */     public SimpleChange<K, V> setAdded(K paramK, V paramV) {
/* 683 */       this.key = paramK;
/* 684 */       this.old = null;
/* 685 */       this.added = paramV;
/* 686 */       return this;
/*     */     }
/*     */ 
/*     */     public SimpleChange<K, V> setPut(K paramK, V paramV1, V paramV2) {
/* 690 */       this.key = paramK;
/* 691 */       this.old = paramV1;
/* 692 */       this.added = paramV2;
/* 693 */       return this;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/* 698 */       return this.added != null;
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 703 */       return this.old != null;
/*     */     }
/*     */ 
/*     */     public K getKey()
/*     */     {
/* 708 */       return this.key;
/*     */     }
/*     */ 
/*     */     public V getValueAdded()
/*     */     {
/* 713 */       return this.added;
/*     */     }
/*     */ 
/*     */     public V getValueRemoved()
/*     */     {
/* 718 */       return this.old;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Generic<K, V> extends MapExpressionHelper<K, V>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private ChangeListener<? super ObservableMap<K, V>>[] changeListeners;
/*     */     private MapChangeListener<? super K, ? super V>[] listChangeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private int setChangeSize;
/*     */     private boolean locked;
/*     */     private ObservableMap<K, V> currentValue;
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 345 */       super();
/* 346 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 347 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, ChangeListener<? super ObservableMap<K, V>> paramChangeListener1, ChangeListener<? super ObservableMap<K, V>> paramChangeListener2) {
/* 351 */       super();
/* 352 */       this.changeListeners = new ChangeListener[] { paramChangeListener1, paramChangeListener2 };
/* 353 */       this.changeSize = 2;
/* 354 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, MapChangeListener<? super K, ? super V> paramMapChangeListener1, MapChangeListener<? super K, ? super V> paramMapChangeListener2) {
/* 358 */       super();
/* 359 */       this.listChangeListeners = new MapChangeListener[] { paramMapChangeListener1, paramMapChangeListener2 };
/* 360 */       this.setChangeSize = 2;
/* 361 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, InvalidationListener paramInvalidationListener, ChangeListener<? super ObservableMap<K, V>> paramChangeListener) {
/* 365 */       super();
/* 366 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 367 */       this.invalidationSize = 1;
/* 368 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 369 */       this.changeSize = 1;
/* 370 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, InvalidationListener paramInvalidationListener, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/* 374 */       super();
/* 375 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 376 */       this.invalidationSize = 1;
/* 377 */       this.listChangeListeners = new MapChangeListener[] { paramMapChangeListener };
/* 378 */       this.setChangeSize = 1;
/* 379 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableMapValue<K, V> paramObservableMapValue, ChangeListener<? super ObservableMap<K, V>> paramChangeListener, MapChangeListener<? super K, ? super V> paramMapChangeListener) {
/* 383 */       super();
/* 384 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 385 */       this.changeSize = 1;
/* 386 */       this.listChangeListeners = new MapChangeListener[] { paramMapChangeListener };
/* 387 */       this.setChangeSize = 1;
/* 388 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 393 */       if (this.invalidationListeners == null) {
/* 394 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 395 */         this.invalidationSize = 1;
/*     */       } else {
/* 397 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 398 */         if (this.locked) {
/* 399 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 400 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 401 */         } else if (this.invalidationSize == i) {
/* 402 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 403 */           if (this.invalidationSize == i) {
/* 404 */             j = i * 3 / 2 + 1;
/* 405 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 408 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 410 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 415 */       if (this.invalidationListeners != null) {
/* 416 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 417 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 418 */             if (this.invalidationSize == 1) {
/* 419 */               if ((this.changeSize == 1) && (this.setChangeSize == 0))
/* 420 */                 return new MapExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/* 421 */               if ((this.changeSize == 0) && (this.setChangeSize == 1)) {
/* 422 */                 return new MapExpressionHelper.SingleMapChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 424 */               this.invalidationListeners = null;
/* 425 */               this.invalidationSize = 0; break;
/*     */             }
/* 427 */             int j = this.invalidationSize - i - 1;
/* 428 */             InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 429 */             if (this.locked) {
/* 430 */               this.invalidationListeners = new InvalidationListener[this.invalidationListeners.length];
/* 431 */               System.arraycopy(arrayOfInvalidationListener, 0, this.invalidationListeners, 0, i + 1);
/*     */             }
/* 433 */             if (j > 0) {
/* 434 */               System.arraycopy(arrayOfInvalidationListener, i + 1, this.invalidationListeners, i, j);
/*     */             }
/* 436 */             if (!this.locked) {
/* 437 */               this.invalidationListeners[(--this.invalidationSize)] = null;
/*     */             }
/*     */ 
/* 440 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 444 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 449 */       if (this.changeListeners == null) {
/* 450 */         this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 451 */         this.changeSize = 1;
/*     */       } else {
/* 453 */         int i = this.changeListeners.length;
/*     */         int j;
/* 454 */         if (this.locked) {
/* 455 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 456 */           this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 457 */         } else if (this.changeSize == i) {
/* 458 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 459 */           if (this.changeSize == i) {
/* 460 */             j = i * 3 / 2 + 1;
/* 461 */             this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 464 */         this.changeListeners[(this.changeSize++)] = paramChangeListener;
/*     */       }
/* 466 */       if (this.changeSize == 1) {
/* 467 */         this.currentValue = ((ObservableMap)this.observable.getValue());
/*     */       }
/* 469 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 474 */       if (this.changeListeners != null) {
/* 475 */         for (int i = 0; i < this.changeSize; i++) {
/* 476 */           if (paramChangeListener.equals(this.changeListeners[i])) {
/* 477 */             if (this.changeSize == 1) {
/* 478 */               if ((this.invalidationSize == 1) && (this.setChangeSize == 0))
/* 479 */                 return new MapExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 480 */               if ((this.invalidationSize == 0) && (this.setChangeSize == 1)) {
/* 481 */                 return new MapExpressionHelper.SingleMapChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 483 */               this.changeListeners = null;
/* 484 */               this.changeSize = 0; break;
/*     */             }
/* 486 */             int j = this.changeSize - i - 1;
/* 487 */             ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 488 */             if (this.locked) {
/* 489 */               this.changeListeners = new ChangeListener[this.changeListeners.length];
/* 490 */               System.arraycopy(arrayOfChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 492 */             if (j > 0) {
/* 493 */               System.arraycopy(arrayOfChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 495 */             if (!this.locked) {
/* 496 */               this.changeListeners[(--this.changeSize)] = null;
/*     */             }
/*     */ 
/* 499 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 503 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 508 */       if (this.listChangeListeners == null) {
/* 509 */         this.listChangeListeners = new MapChangeListener[] { paramMapChangeListener };
/* 510 */         this.setChangeSize = 1;
/*     */       } else {
/* 512 */         int i = this.listChangeListeners.length;
/*     */         int j;
/* 513 */         if (this.locked) {
/* 514 */           j = this.setChangeSize < i ? i : i * 3 / 2 + 1;
/* 515 */           this.listChangeListeners = ((MapChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/* 516 */         } else if (this.setChangeSize == i) {
/* 517 */           this.setChangeSize = trim(this.setChangeSize, this.listChangeListeners);
/* 518 */           if (this.setChangeSize == i) {
/* 519 */             j = i * 3 / 2 + 1;
/* 520 */             this.listChangeListeners = ((MapChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/*     */           }
/*     */         }
/* 523 */         this.listChangeListeners[(this.setChangeSize++)] = paramMapChangeListener;
/*     */       }
/* 525 */       if (this.setChangeSize == 1) {
/* 526 */         this.currentValue = ((ObservableMap)this.observable.getValue());
/*     */       }
/* 528 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 533 */       if (this.listChangeListeners != null) {
/* 534 */         for (int i = 0; i < this.setChangeSize; i++) {
/* 535 */           if (paramMapChangeListener.equals(this.listChangeListeners[i])) {
/* 536 */             if (this.setChangeSize == 1) {
/* 537 */               if ((this.invalidationSize == 1) && (this.changeSize == 0))
/* 538 */                 return new MapExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 539 */               if ((this.invalidationSize == 0) && (this.changeSize == 1)) {
/* 540 */                 return new MapExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/*     */               }
/* 542 */               this.listChangeListeners = null;
/* 543 */               this.setChangeSize = 0; break;
/*     */             }
/* 545 */             int j = this.setChangeSize - i - 1;
/* 546 */             MapChangeListener[] arrayOfMapChangeListener = this.listChangeListeners;
/* 547 */             if (this.locked) {
/* 548 */               this.listChangeListeners = new MapChangeListener[this.listChangeListeners.length];
/* 549 */               System.arraycopy(arrayOfMapChangeListener, 0, this.listChangeListeners, 0, i + 1);
/*     */             }
/* 551 */             if (j > 0) {
/* 552 */               System.arraycopy(arrayOfMapChangeListener, i + 1, this.listChangeListeners, i, j);
/*     */             }
/* 554 */             if (!this.locked) {
/* 555 */               this.listChangeListeners[(--this.setChangeSize)] = null;
/*     */             }
/*     */ 
/* 558 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 562 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 567 */       if ((this.changeSize == 0) && (this.setChangeSize == 0)) {
/* 568 */         notifyListeners(this.currentValue, null);
/*     */       } else {
/* 570 */         ObservableMap localObservableMap = this.currentValue;
/* 571 */         this.currentValue = ((ObservableMap)this.observable.getValue());
/* 572 */         notifyListeners(localObservableMap, null);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 578 */       MapExpressionHelper.SimpleChange localSimpleChange = this.setChangeSize == 0 ? null : new MapExpressionHelper.SimpleChange(this.observable, paramChange);
/* 579 */       notifyListeners(this.currentValue, localSimpleChange);
/*     */     }
/*     */ 
/*     */     private void notifyListeners(ObservableMap<K, V> paramObservableMap, MapExpressionHelper.SimpleChange<K, V> paramSimpleChange) {
/* 583 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 584 */       int i = this.invalidationSize;
/* 585 */       ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 586 */       int j = this.changeSize;
/* 587 */       MapChangeListener[] arrayOfMapChangeListener = this.listChangeListeners;
/* 588 */       int k = this.setChangeSize;
/*     */       try {
/* 590 */         this.locked = true;
/* 591 */         for (int m = 0; m < i; m++) {
/* 592 */           arrayOfInvalidationListener[m].invalidated(this.observable);
/*     */         }
/* 594 */         if ((this.currentValue != paramObservableMap) || (paramSimpleChange != null)) {
/* 595 */           for (m = 0; m < j; m++) {
/* 596 */             arrayOfChangeListener[m].changed(this.observable, paramObservableMap, this.currentValue);
/*     */           }
/* 598 */           if (k > 0)
/* 599 */             if (paramSimpleChange != null) {
/* 600 */               for (m = 0; m < k; m++)
/* 601 */                 arrayOfMapChangeListener[m].onChanged(paramSimpleChange);
/*     */             }
/*     */             else {
/* 604 */               paramSimpleChange = new MapExpressionHelper.SimpleChange(this.observable);
/*     */               int n;
/* 605 */               if (this.currentValue == null) {
/* 606 */                 for (localIterator = paramObservableMap.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 607 */                   paramSimpleChange.setRemoved(localEntry.getKey(), localEntry.getValue());
/* 608 */                   for (n = 0; n < k; n++)
/* 609 */                     arrayOfMapChangeListener[n].onChanged(paramSimpleChange);
/*     */                 }
/*     */               }
/* 612 */               else if (paramObservableMap == null) {
/* 613 */                 for (localIterator = this.currentValue.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 614 */                   paramSimpleChange.setAdded(localEntry.getKey(), localEntry.getValue());
/* 615 */                   for (n = 0; n < k; n++)
/* 616 */                     arrayOfMapChangeListener[n].onChanged(paramSimpleChange); }
/*     */               }
/*     */               else
/*     */               {
/* 620 */                 for (localIterator = paramObservableMap.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 621 */                   localObject1 = localEntry.getKey();
/* 622 */                   Object localObject2 = localEntry.getValue();
/* 623 */                   Object localObject3 = this.currentValue.get(localObject1);
/*     */                   int i2;
/* 624 */                   if (localObject3 != null) {
/* 625 */                     if (!localObject3.equals(localObject2)) {
/* 626 */                       paramSimpleChange.setPut(localObject1, localObject2, localObject3);
/* 627 */                       for (i2 = 0; i2 < k; i2++)
/* 628 */                         arrayOfMapChangeListener[i2].onChanged(paramSimpleChange);
/*     */                     }
/*     */                   }
/*     */                   else {
/* 632 */                     paramSimpleChange.setRemoved(localObject1, localObject2);
/* 633 */                     for (i2 = 0; i2 < k; i2++) {
/* 634 */                       arrayOfMapChangeListener[i2].onChanged(paramSimpleChange);
/*     */                     }
/*     */                   }
/*     */                 }
/* 638 */                 for (localIterator = this.currentValue.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 639 */                   localObject1 = localEntry.getKey();
/* 640 */                   if (!paramObservableMap.containsKey(localObject1)) {
/* 641 */                     paramSimpleChange.setAdded(localObject1, localEntry.getValue());
/* 642 */                     for (int i1 = 0; i1 < k; i1++)
/* 643 */                       arrayOfMapChangeListener[i1].onChanged(paramSimpleChange);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/*     */         Iterator localIterator;
/*     */         Map.Entry localEntry;
/*     */         Object localObject1;
/* 652 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleMapChange<K, V> extends MapExpressionHelper<K, V>
/*     */   {
/*     */     private final MapChangeListener<? super K, ? super V> listener;
/*     */     private ObservableMap<K, V> currentValue;
/*     */ 
/*     */     private SingleMapChange(ObservableMapValue<K, V> paramObservableMapValue, MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 255 */       super();
/* 256 */       this.listener = paramMapChangeListener;
/* 257 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 262 */       return new MapExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 267 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 272 */       return new MapExpressionHelper.Generic(this.observable, paramChangeListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 277 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 282 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramMapChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 287 */       return paramMapChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 292 */       ObservableMap localObservableMap = this.currentValue;
/* 293 */       this.currentValue = ((ObservableMap)this.observable.getValue());
/*     */       MapExpressionHelper.SimpleChange localSimpleChange;
/*     */       Iterator localIterator;
/*     */       Map.Entry localEntry;
/*     */       Object localObject1;
/* 294 */       if (this.currentValue != localObservableMap) {
/* 295 */         localSimpleChange = new MapExpressionHelper.SimpleChange(this.observable);
/* 296 */         if (this.currentValue == null) {
/* 297 */           for (localIterator = localObservableMap.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 298 */             this.listener.onChanged(localSimpleChange.setRemoved(localEntry.getKey(), localEntry.getValue())); }
/*     */         }
/* 300 */         else if (localObservableMap == null) {
/* 301 */           for (localIterator = this.currentValue.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 302 */             this.listener.onChanged(localSimpleChange.setAdded(localEntry.getKey(), localEntry.getValue())); }
/*     */         }
/*     */         else {
/* 305 */           for (localIterator = localObservableMap.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 306 */             localObject1 = localEntry.getKey();
/* 307 */             Object localObject2 = localEntry.getValue();
/* 308 */             Object localObject3 = this.currentValue.get(localObject1);
/* 309 */             if (localObject3 != null) {
/* 310 */               if (!localObject3.equals(localObject2))
/* 311 */                 this.listener.onChanged(localSimpleChange.setPut(localObject1, localObject2, localObject3));
/*     */             }
/*     */             else {
/* 314 */               this.listener.onChanged(localSimpleChange.setRemoved(localObject1, localObject2));
/*     */             }
/*     */           }
/* 317 */           for (localIterator = this.currentValue.entrySet().iterator(); localIterator.hasNext(); ) { localEntry = (Map.Entry)localIterator.next();
/* 318 */             localObject1 = localEntry.getKey();
/* 319 */             if (!localObservableMap.containsKey(localObject1))
/* 320 */               this.listener.onChanged(localSimpleChange.setAdded(localObject1, localEntry.getValue()));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 329 */       this.listener.onChanged(new MapExpressionHelper.SimpleChange(this.observable, paramChange));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<K, V> extends MapExpressionHelper<K, V>
/*     */   {
/*     */     private final ChangeListener<? super ObservableMap<K, V>> listener;
/*     */     private ObservableMap<K, V> currentValue;
/*     */ 
/*     */     private SingleChange(ObservableMapValue<K, V> paramObservableMapValue, ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 199 */       super();
/* 200 */       this.listener = paramChangeListener;
/* 201 */       this.currentValue = ((ObservableMap)paramObservableMapValue.getValue());
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 206 */       return new MapExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 211 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 216 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 221 */       return paramChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 226 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramMapChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 231 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 236 */       ObservableMap localObservableMap = this.currentValue;
/* 237 */       this.currentValue = ((ObservableMap)this.observable.getValue());
/* 238 */       if (this.currentValue != localObservableMap)
/* 239 */         this.listener.changed(this.observable, localObservableMap, this.currentValue);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 245 */       this.listener.changed(this.observable, this.currentValue, this.currentValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<K, V> extends MapExpressionHelper<K, V>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(ObservableMapValue<K, V> paramObservableMapValue, InvalidationListener paramInvalidationListener)
/*     */     {
/* 148 */       super();
/* 149 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 154 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 159 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 164 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 169 */       return this;
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 174 */       return new MapExpressionHelper.Generic(this.observable, this.listener, paramMapChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected MapExpressionHelper<K, V> removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 179 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 184 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 189 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.MapExpressionHelper
 * JD-Core Version:    0.6.2
 */