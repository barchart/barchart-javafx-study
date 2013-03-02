/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableSetValue;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public abstract class SetExpressionHelper<E> extends ExpressionHelperBase
/*     */ {
/*     */   protected final ObservableSetValue<E> observable;
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> addListener(SetExpressionHelper<E> paramSetExpressionHelper, ObservableSetValue<E> paramObservableSetValue, InvalidationListener paramInvalidationListener)
/*     */   {
/*  65 */     if ((paramObservableSetValue == null) || (paramInvalidationListener == null)) {
/*  66 */       throw new NullPointerException();
/*     */     }
/*  68 */     paramObservableSetValue.getValue();
/*  69 */     return paramSetExpressionHelper == null ? new SingleInvalidation(paramObservableSetValue, paramInvalidationListener, null) : paramSetExpressionHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> removeListener(SetExpressionHelper<E> paramSetExpressionHelper, InvalidationListener paramInvalidationListener) {
/*  73 */     if (paramInvalidationListener == null) {
/*  74 */       throw new NullPointerException();
/*     */     }
/*  76 */     return paramSetExpressionHelper == null ? null : paramSetExpressionHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> addListener(SetExpressionHelper<E> paramSetExpressionHelper, ObservableSetValue<E> paramObservableSetValue, ChangeListener<? super ObservableSet<E>> paramChangeListener) {
/*  80 */     if ((paramObservableSetValue == null) || (paramChangeListener == null)) {
/*  81 */       throw new NullPointerException();
/*     */     }
/*  83 */     return paramSetExpressionHelper == null ? new SingleChange(paramObservableSetValue, paramChangeListener, null) : paramSetExpressionHelper.addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> removeListener(SetExpressionHelper<E> paramSetExpressionHelper, ChangeListener<? super ObservableSet<E>> paramChangeListener) {
/*  87 */     if (paramChangeListener == null) {
/*  88 */       throw new NullPointerException();
/*     */     }
/*  90 */     return paramSetExpressionHelper == null ? null : paramSetExpressionHelper.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> addListener(SetExpressionHelper<E> paramSetExpressionHelper, ObservableSetValue<E> paramObservableSetValue, SetChangeListener<? super E> paramSetChangeListener) {
/*  94 */     if ((paramObservableSetValue == null) || (paramSetChangeListener == null)) {
/*  95 */       throw new NullPointerException();
/*     */     }
/*  97 */     return paramSetExpressionHelper == null ? new SingleSetChange(paramObservableSetValue, paramSetChangeListener, null) : paramSetExpressionHelper.addListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> SetExpressionHelper<E> removeListener(SetExpressionHelper<E> paramSetExpressionHelper, SetChangeListener<? super E> paramSetChangeListener) {
/* 101 */     if (paramSetChangeListener == null) {
/* 102 */       throw new NullPointerException();
/*     */     }
/* 104 */     return paramSetExpressionHelper == null ? null : paramSetExpressionHelper.removeListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(SetExpressionHelper<E> paramSetExpressionHelper) {
/* 108 */     if (paramSetExpressionHelper != null)
/* 109 */       paramSetExpressionHelper.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(SetExpressionHelper<E> paramSetExpressionHelper, SetChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 114 */     if (paramSetExpressionHelper != null)
/* 115 */       paramSetExpressionHelper.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   protected SetExpressionHelper(ObservableSetValue<E> paramObservableSetValue)
/*     */   {
/* 125 */     this.observable = paramObservableSetValue;
/*     */   }
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener);
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener);
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener);
/*     */ 
/*     */   protected abstract SetExpressionHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent();
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange);
/*     */ 
/*     */   public static class SimpleChange<E> extends SetChangeListener.Change<E>
/*     */   {
/*     */     private E old;
/*     */     private E added;
/*     */ 
/*     */     public SimpleChange(ObservableSet<E> paramObservableSet)
/*     */     {
/* 648 */       super();
/*     */     }
/*     */ 
/*     */     public SimpleChange(ObservableSet<E> paramObservableSet, SetChangeListener.Change<? extends E> paramChange) {
/* 652 */       super();
/* 653 */       this.old = paramChange.getElementRemoved();
/* 654 */       this.added = paramChange.getElementAdded();
/*     */     }
/*     */ 
/*     */     public SimpleChange<E> setRemoved(E paramE) {
/* 658 */       this.old = paramE;
/* 659 */       this.added = null;
/* 660 */       return this;
/*     */     }
/*     */ 
/*     */     public SimpleChange<E> setAdded(E paramE) {
/* 664 */       this.old = null;
/* 665 */       this.added = paramE;
/* 666 */       return this;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/* 671 */       return this.added != null;
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 676 */       return this.old != null;
/*     */     }
/*     */ 
/*     */     public E getElementAdded()
/*     */     {
/* 681 */       return this.added;
/*     */     }
/*     */ 
/*     */     public E getElementRemoved()
/*     */     {
/* 686 */       return this.old;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Generic<E> extends SetExpressionHelper<E>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private ChangeListener<? super ObservableSet<E>>[] changeListeners;
/*     */     private SetChangeListener<? super E>[] listChangeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private int setChangeSize;
/*     */     private boolean locked;
/*     */     private ObservableSet<E> currentValue;
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 337 */       super();
/* 338 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 339 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, ChangeListener<? super ObservableSet<E>> paramChangeListener1, ChangeListener<? super ObservableSet<E>> paramChangeListener2) {
/* 343 */       super();
/* 344 */       this.changeListeners = new ChangeListener[] { paramChangeListener1, paramChangeListener2 };
/* 345 */       this.changeSize = 2;
/* 346 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, SetChangeListener<? super E> paramSetChangeListener1, SetChangeListener<? super E> paramSetChangeListener2) {
/* 350 */       super();
/* 351 */       this.listChangeListeners = new SetChangeListener[] { paramSetChangeListener1, paramSetChangeListener2 };
/* 352 */       this.setChangeSize = 2;
/* 353 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, InvalidationListener paramInvalidationListener, ChangeListener<? super ObservableSet<E>> paramChangeListener) {
/* 357 */       super();
/* 358 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 359 */       this.invalidationSize = 1;
/* 360 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 361 */       this.changeSize = 1;
/* 362 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, InvalidationListener paramInvalidationListener, SetChangeListener<? super E> paramSetChangeListener) {
/* 366 */       super();
/* 367 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 368 */       this.invalidationSize = 1;
/* 369 */       this.listChangeListeners = new SetChangeListener[] { paramSetChangeListener };
/* 370 */       this.setChangeSize = 1;
/* 371 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableSetValue<E> paramObservableSetValue, ChangeListener<? super ObservableSet<E>> paramChangeListener, SetChangeListener<? super E> paramSetChangeListener) {
/* 375 */       super();
/* 376 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 377 */       this.changeSize = 1;
/* 378 */       this.listChangeListeners = new SetChangeListener[] { paramSetChangeListener };
/* 379 */       this.setChangeSize = 1;
/* 380 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 385 */       if (this.invalidationListeners == null) {
/* 386 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 387 */         this.invalidationSize = 1;
/*     */       } else {
/* 389 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 390 */         if (this.locked) {
/* 391 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 392 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 393 */         } else if (this.invalidationSize == i) {
/* 394 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 395 */           if (this.invalidationSize == i) {
/* 396 */             j = i * 3 / 2 + 1;
/* 397 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 400 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 402 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 407 */       if (this.invalidationListeners != null) {
/* 408 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 409 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 410 */             if (this.invalidationSize == 1) {
/* 411 */               if ((this.changeSize == 1) && (this.setChangeSize == 0))
/* 412 */                 return new SetExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/* 413 */               if ((this.changeSize == 0) && (this.setChangeSize == 1)) {
/* 414 */                 return new SetExpressionHelper.SingleSetChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 416 */               this.invalidationListeners = null;
/* 417 */               this.invalidationSize = 0; break;
/*     */             }
/* 419 */             int j = this.invalidationSize - i - 1;
/* 420 */             InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 421 */             if (this.locked) {
/* 422 */               this.invalidationListeners = new InvalidationListener[this.invalidationListeners.length];
/* 423 */               System.arraycopy(arrayOfInvalidationListener, 0, this.invalidationListeners, 0, i + 1);
/*     */             }
/* 425 */             if (j > 0) {
/* 426 */               System.arraycopy(arrayOfInvalidationListener, i + 1, this.invalidationListeners, i, j);
/*     */             }
/* 428 */             if (!this.locked) {
/* 429 */               this.invalidationListeners[(--this.invalidationSize)] = null;
/*     */             }
/*     */ 
/* 432 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 436 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 441 */       if (this.changeListeners == null) {
/* 442 */         this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 443 */         this.changeSize = 1;
/*     */       } else {
/* 445 */         int i = this.changeListeners.length;
/*     */         int j;
/* 446 */         if (this.locked) {
/* 447 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 448 */           this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 449 */         } else if (this.changeSize == i) {
/* 450 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 451 */           if (this.changeSize == i) {
/* 452 */             j = i * 3 / 2 + 1;
/* 453 */             this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 456 */         this.changeListeners[(this.changeSize++)] = paramChangeListener;
/*     */       }
/* 458 */       if (this.changeSize == 1) {
/* 459 */         this.currentValue = ((ObservableSet)this.observable.getValue());
/*     */       }
/* 461 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 466 */       if (this.changeListeners != null) {
/* 467 */         for (int i = 0; i < this.changeSize; i++) {
/* 468 */           if (paramChangeListener.equals(this.changeListeners[i])) {
/* 469 */             if (this.changeSize == 1) {
/* 470 */               if ((this.invalidationSize == 1) && (this.setChangeSize == 0))
/* 471 */                 return new SetExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 472 */               if ((this.invalidationSize == 0) && (this.setChangeSize == 1)) {
/* 473 */                 return new SetExpressionHelper.SingleSetChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 475 */               this.changeListeners = null;
/* 476 */               this.changeSize = 0; break;
/*     */             }
/* 478 */             int j = this.changeSize - i - 1;
/* 479 */             ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 480 */             if (this.locked) {
/* 481 */               this.changeListeners = new ChangeListener[this.changeListeners.length];
/* 482 */               System.arraycopy(arrayOfChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 484 */             if (j > 0) {
/* 485 */               System.arraycopy(arrayOfChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 487 */             this.changeSize -= 1;
/* 488 */             if (!this.locked) {
/* 489 */               this.changeListeners[this.changeSize] = null;
/*     */             }
/*     */ 
/* 492 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 496 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 501 */       if (this.listChangeListeners == null) {
/* 502 */         this.listChangeListeners = new SetChangeListener[] { paramSetChangeListener };
/* 503 */         this.setChangeSize = 1;
/*     */       } else {
/* 505 */         int i = this.listChangeListeners.length;
/*     */         int j;
/* 506 */         if (this.locked) {
/* 507 */           j = this.setChangeSize < i ? i : i * 3 / 2 + 1;
/* 508 */           this.listChangeListeners = ((SetChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/* 509 */         } else if (this.setChangeSize == i) {
/* 510 */           this.setChangeSize = trim(this.setChangeSize, this.listChangeListeners);
/* 511 */           if (this.setChangeSize == i) {
/* 512 */             j = i * 3 / 2 + 1;
/* 513 */             this.listChangeListeners = ((SetChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/*     */           }
/*     */         }
/* 516 */         this.listChangeListeners[(this.setChangeSize++)] = paramSetChangeListener;
/*     */       }
/* 518 */       if (this.setChangeSize == 1) {
/* 519 */         this.currentValue = ((ObservableSet)this.observable.getValue());
/*     */       }
/* 521 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 526 */       if (this.listChangeListeners != null) {
/* 527 */         for (int i = 0; i < this.setChangeSize; i++) {
/* 528 */           if (paramSetChangeListener.equals(this.listChangeListeners[i])) {
/* 529 */             if (this.setChangeSize == 1) {
/* 530 */               if ((this.invalidationSize == 1) && (this.changeSize == 0))
/* 531 */                 return new SetExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 532 */               if ((this.invalidationSize == 0) && (this.changeSize == 1)) {
/* 533 */                 return new SetExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/*     */               }
/* 535 */               this.listChangeListeners = null;
/* 536 */               this.setChangeSize = 0; break;
/*     */             }
/* 538 */             int j = this.setChangeSize - i - 1;
/* 539 */             SetChangeListener[] arrayOfSetChangeListener = this.listChangeListeners;
/* 540 */             if (this.locked) {
/* 541 */               this.listChangeListeners = new SetChangeListener[this.listChangeListeners.length];
/* 542 */               System.arraycopy(arrayOfSetChangeListener, 0, this.listChangeListeners, 0, i + 1);
/*     */             }
/* 544 */             if (j > 0) {
/* 545 */               System.arraycopy(arrayOfSetChangeListener, i + 1, this.listChangeListeners, i, j);
/*     */             }
/* 547 */             this.setChangeSize -= 1;
/* 548 */             if (!this.locked) {
/* 549 */               this.listChangeListeners[this.setChangeSize] = null;
/*     */             }
/*     */ 
/* 552 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 556 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 561 */       if ((this.changeSize == 0) && (this.setChangeSize == 0)) {
/* 562 */         notifyListeners(this.currentValue, null);
/*     */       } else {
/* 564 */         ObservableSet localObservableSet = this.currentValue;
/* 565 */         this.currentValue = ((ObservableSet)this.observable.getValue());
/* 566 */         notifyListeners(localObservableSet, null);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 572 */       SetExpressionHelper.SimpleChange localSimpleChange = this.setChangeSize == 0 ? null : new SetExpressionHelper.SimpleChange(this.observable, paramChange);
/* 573 */       notifyListeners(this.currentValue, localSimpleChange);
/*     */     }
/*     */ 
/*     */     private void notifyListeners(ObservableSet<E> paramObservableSet, SetExpressionHelper.SimpleChange<E> paramSimpleChange) {
/* 577 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 578 */       int i = this.invalidationSize;
/* 579 */       ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 580 */       int j = this.changeSize;
/* 581 */       SetChangeListener[] arrayOfSetChangeListener = this.listChangeListeners;
/* 582 */       int k = this.setChangeSize;
/*     */       try {
/* 584 */         this.locked = true;
/* 585 */         for (int m = 0; m < i; m++) {
/* 586 */           arrayOfInvalidationListener[m].invalidated(this.observable);
/*     */         }
/* 588 */         if ((this.currentValue != paramObservableSet) || (paramSimpleChange != null)) {
/* 589 */           for (m = 0; m < j; m++) {
/* 590 */             arrayOfChangeListener[m].changed(this.observable, paramObservableSet, this.currentValue);
/*     */           }
/* 592 */           if (k > 0)
/* 593 */             if (paramSimpleChange != null) {
/* 594 */               for (m = 0; m < k; m++)
/* 595 */                 arrayOfSetChangeListener[m].onChanged(paramSimpleChange);
/*     */             }
/*     */             else {
/* 598 */               paramSimpleChange = new SetExpressionHelper.SimpleChange(this.observable);
/* 599 */               if (this.currentValue == null) {
/* 600 */                 for (localIterator = paramObservableSet.iterator(); localIterator.hasNext(); ) { localObject1 = localIterator.next();
/* 601 */                   paramSimpleChange.setRemoved(localObject1);
/* 602 */                   for (n = 0; n < k; n++)
/* 603 */                     arrayOfSetChangeListener[n].onChanged(paramSimpleChange);
/*     */                 }
/*     */               }
/* 606 */               else if (paramObservableSet == null) {
/* 607 */                 for (localIterator = this.currentValue.iterator(); localIterator.hasNext(); ) { localObject1 = localIterator.next();
/* 608 */                   paramSimpleChange.setAdded(localObject1);
/* 609 */                   for (n = 0; n < k; n++)
/* 610 */                     arrayOfSetChangeListener[n].onChanged(paramSimpleChange); }
/*     */               }
/*     */               else
/*     */               {
/* 614 */                 for (localIterator = paramObservableSet.iterator(); localIterator.hasNext(); ) { localObject1 = localIterator.next();
/* 615 */                   if (!this.currentValue.contains(localObject1)) {
/* 616 */                     paramSimpleChange.setRemoved(localObject1);
/* 617 */                     for (n = 0; n < k; n++) {
/* 618 */                       arrayOfSetChangeListener[n].onChanged(paramSimpleChange);
/*     */                     }
/*     */                   }
/*     */                 }
/* 622 */                 for (localIterator = this.currentValue.iterator(); localIterator.hasNext(); ) { localObject1 = localIterator.next();
/* 623 */                   if (!paramObservableSet.contains(localObject1)) {
/* 624 */                     paramSimpleChange.setAdded(localObject1);
/* 625 */                     for (n = 0; n < k; n++)
/* 626 */                       arrayOfSetChangeListener[n].onChanged(paramSimpleChange);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */         }
/*     */       }
/*     */       finally
/*     */       {
/*     */         Iterator localIterator;
/*     */         Object localObject1;
/*     */         int n;
/* 636 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleSetChange<E> extends SetExpressionHelper<E>
/*     */   {
/*     */     private final SetChangeListener<? super E> listener;
/*     */     private ObservableSet<E> currentValue;
/*     */ 
/*     */     private SingleSetChange(ObservableSetValue<E> paramObservableSetValue, SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 255 */       super();
/* 256 */       this.listener = paramSetChangeListener;
/* 257 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 262 */       return new SetExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 267 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 272 */       return new SetExpressionHelper.Generic(this.observable, paramChangeListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 277 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 282 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramSetChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 287 */       return paramSetChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 292 */       ObservableSet localObservableSet = this.currentValue;
/* 293 */       this.currentValue = ((ObservableSet)this.observable.getValue());
/*     */       SetExpressionHelper.SimpleChange localSimpleChange;
/*     */       Iterator localIterator;
/*     */       Object localObject;
/* 294 */       if (this.currentValue != localObservableSet) {
/* 295 */         localSimpleChange = new SetExpressionHelper.SimpleChange(this.observable);
/* 296 */         if (this.currentValue == null) {
/* 297 */           for (localIterator = localObservableSet.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 298 */             this.listener.onChanged(localSimpleChange.setRemoved(localObject)); }
/*     */         }
/* 300 */         else if (localObservableSet == null) {
/* 301 */           for (localIterator = this.currentValue.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 302 */             this.listener.onChanged(localSimpleChange.setAdded(localObject)); }
/*     */         }
/*     */         else {
/* 305 */           for (localIterator = localObservableSet.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 306 */             if (!this.currentValue.contains(localObject)) {
/* 307 */               this.listener.onChanged(localSimpleChange.setRemoved(localObject));
/*     */             }
/*     */           }
/* 310 */           for (localIterator = this.currentValue.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 311 */             if (!localObservableSet.contains(localObject))
/* 312 */               this.listener.onChanged(localSimpleChange.setAdded(localObject));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 321 */       this.listener.onChanged(new SetExpressionHelper.SimpleChange(this.observable, paramChange));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<E> extends SetExpressionHelper<E>
/*     */   {
/*     */     private final ChangeListener<? super ObservableSet<E>> listener;
/*     */     private ObservableSet<E> currentValue;
/*     */ 
/*     */     private SingleChange(ObservableSetValue<E> paramObservableSetValue, ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 199 */       super();
/* 200 */       this.listener = paramChangeListener;
/* 201 */       this.currentValue = ((ObservableSet)paramObservableSetValue.getValue());
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 206 */       return new SetExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 211 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 216 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 221 */       return paramChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 226 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramSetChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 231 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 236 */       ObservableSet localObservableSet = this.currentValue;
/* 237 */       this.currentValue = ((ObservableSet)this.observable.getValue());
/* 238 */       if (this.currentValue != localObservableSet)
/* 239 */         this.listener.changed(this.observable, localObservableSet, this.currentValue);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 245 */       this.listener.changed(this.observable, this.currentValue, this.currentValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<E> extends SetExpressionHelper<E>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(ObservableSetValue<E> paramObservableSetValue, InvalidationListener paramInvalidationListener)
/*     */     {
/* 148 */       super();
/* 149 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 154 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 159 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 164 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 169 */       return this;
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 174 */       return new SetExpressionHelper.Generic(this.observable, this.listener, paramSetChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected SetExpressionHelper<E> removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 179 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 184 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 189 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.SetExpressionHelper
 * JD-Core Version:    0.6.2
 */