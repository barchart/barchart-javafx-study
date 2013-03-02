/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableListValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ListExpressionHelper<E> extends ExpressionHelperBase
/*     */ {
/*     */   protected final ObservableListValue<E> observable;
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> addListener(ListExpressionHelper<E> paramListExpressionHelper, ObservableListValue<E> paramObservableListValue, InvalidationListener paramInvalidationListener)
/*     */   {
/*  78 */     if ((paramObservableListValue == null) || (paramInvalidationListener == null)) {
/*  79 */       throw new NullPointerException();
/*     */     }
/*  81 */     paramObservableListValue.getValue();
/*  82 */     return paramListExpressionHelper == null ? new SingleInvalidation(paramObservableListValue, paramInvalidationListener, null) : paramListExpressionHelper.addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> removeListener(ListExpressionHelper<E> paramListExpressionHelper, InvalidationListener paramInvalidationListener) {
/*  86 */     if (paramInvalidationListener == null) {
/*  87 */       throw new NullPointerException();
/*     */     }
/*  89 */     return paramListExpressionHelper == null ? null : paramListExpressionHelper.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> addListener(ListExpressionHelper<E> paramListExpressionHelper, ObservableListValue<E> paramObservableListValue, ChangeListener<? super ObservableList<E>> paramChangeListener) {
/*  93 */     if ((paramObservableListValue == null) || (paramChangeListener == null)) {
/*  94 */       throw new NullPointerException();
/*     */     }
/*  96 */     return paramListExpressionHelper == null ? new SingleChange(paramObservableListValue, paramChangeListener, null) : paramListExpressionHelper.addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> removeListener(ListExpressionHelper<E> paramListExpressionHelper, ChangeListener<? super ObservableList<E>> paramChangeListener) {
/* 100 */     if (paramChangeListener == null) {
/* 101 */       throw new NullPointerException();
/*     */     }
/* 103 */     return paramListExpressionHelper == null ? null : paramListExpressionHelper.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> addListener(ListExpressionHelper<E> paramListExpressionHelper, ObservableListValue<E> paramObservableListValue, ListChangeListener<? super E> paramListChangeListener) {
/* 107 */     if ((paramObservableListValue == null) || (paramListChangeListener == null)) {
/* 108 */       throw new NullPointerException();
/*     */     }
/* 110 */     return paramListExpressionHelper == null ? new SingleListChange(paramObservableListValue, paramListChangeListener, null) : paramListExpressionHelper.addListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> ListExpressionHelper<E> removeListener(ListExpressionHelper<E> paramListExpressionHelper, ListChangeListener<? super E> paramListChangeListener) {
/* 114 */     if (paramListChangeListener == null) {
/* 115 */       throw new NullPointerException();
/*     */     }
/* 117 */     return paramListExpressionHelper == null ? null : paramListExpressionHelper.removeListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(ListExpressionHelper<E> paramListExpressionHelper) {
/* 121 */     if (paramListExpressionHelper != null)
/* 122 */       paramListExpressionHelper.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public static <E> void fireValueChangedEvent(ListExpressionHelper<E> paramListExpressionHelper, ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 127 */     if (paramListExpressionHelper != null)
/* 128 */       paramListExpressionHelper.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   protected ListExpressionHelper(ObservableListValue<E> paramObservableListValue)
/*     */   {
/* 138 */     this.observable = paramObservableListValue;
/*     */   }
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener);
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> paramChangeListener);
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener);
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener);
/*     */ 
/*     */   protected abstract ListExpressionHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener);
/*     */ 
/*     */   protected abstract void fireValueChangedEvent();
/*     */ 
/*     */   protected abstract void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange);
/*     */ 
/*     */   private static class MappedChange<E> extends ListChangeListener.Change<E>
/*     */   {
/*     */     private final ListChangeListener.Change<? extends E> source;
/*     */     private int[] perm;
/*     */ 
/*     */     private MappedChange(ObservableList<E> paramObservableList, ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 625 */       super();
/* 626 */       this.source = paramChange;
/*     */     }
/*     */     public boolean next() {
/* 629 */       return this.source.next(); } 
/* 630 */     public void reset() { this.source.reset(); } 
/* 631 */     public int getFrom() { return this.source.getFrom(); } 
/* 632 */     public int getTo() { return this.source.getTo(); } 
/* 633 */     public List<E> getRemoved() { return this.source.getRemoved(); } 
/*     */     protected int[] getPermutation() {
/* 635 */       if (this.perm == null) {
/* 636 */         if (this.source.wasPermutated()) {
/* 637 */           int i = this.source.getFrom();
/* 638 */           int j = this.source.getTo() - i;
/* 639 */           this.perm = new int[j];
/* 640 */           for (int k = 0; k < j; k++)
/* 641 */             this.perm[k] = this.source.getPermutation(i + k);
/*     */         }
/*     */         else {
/* 644 */           this.perm = new int[0];
/*     */         }
/*     */       }
/* 647 */       return this.perm;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Generic<E> extends ListExpressionHelper<E>
/*     */   {
/*     */     private InvalidationListener[] invalidationListeners;
/*     */     private ChangeListener<? super ObservableList<E>>[] changeListeners;
/*     */     private ListChangeListener<? super E>[] listChangeListeners;
/*     */     private int invalidationSize;
/*     */     private int changeSize;
/*     */     private int listChangeSize;
/*     */     private boolean locked;
/*     */     private ObservableList<E> currentValue;
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, InvalidationListener paramInvalidationListener1, InvalidationListener paramInvalidationListener2)
/*     */     {
/* 335 */       super();
/* 336 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener1, paramInvalidationListener2 };
/* 337 */       this.invalidationSize = 2;
/*     */     }
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, ChangeListener<? super ObservableList<E>> paramChangeListener1, ChangeListener<? super ObservableList<E>> paramChangeListener2) {
/* 341 */       super();
/* 342 */       this.changeListeners = new ChangeListener[] { paramChangeListener1, paramChangeListener2 };
/* 343 */       this.changeSize = 2;
/* 344 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, ListChangeListener<? super E> paramListChangeListener1, ListChangeListener<? super E> paramListChangeListener2) {
/* 348 */       super();
/* 349 */       this.listChangeListeners = new ListChangeListener[] { paramListChangeListener1, paramListChangeListener2 };
/* 350 */       this.listChangeSize = 2;
/* 351 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, InvalidationListener paramInvalidationListener, ChangeListener<? super ObservableList<E>> paramChangeListener) {
/* 355 */       super();
/* 356 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 357 */       this.invalidationSize = 1;
/* 358 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 359 */       this.changeSize = 1;
/* 360 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, InvalidationListener paramInvalidationListener, ListChangeListener<? super E> paramListChangeListener) {
/* 364 */       super();
/* 365 */       this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 366 */       this.invalidationSize = 1;
/* 367 */       this.listChangeListeners = new ListChangeListener[] { paramListChangeListener };
/* 368 */       this.listChangeSize = 1;
/* 369 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     private Generic(ObservableListValue<E> paramObservableListValue, ChangeListener<? super ObservableList<E>> paramChangeListener, ListChangeListener<? super E> paramListChangeListener) {
/* 373 */       super();
/* 374 */       this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 375 */       this.changeSize = 1;
/* 376 */       this.listChangeListeners = new ListChangeListener[] { paramListChangeListener };
/* 377 */       this.listChangeSize = 1;
/* 378 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 383 */       if (this.invalidationListeners == null) {
/* 384 */         this.invalidationListeners = new InvalidationListener[] { paramInvalidationListener };
/* 385 */         this.invalidationSize = 1;
/*     */       } else {
/* 387 */         int i = this.invalidationListeners.length;
/*     */         int j;
/* 388 */         if (this.locked) {
/* 389 */           j = this.invalidationSize < i ? i : i * 3 / 2 + 1;
/* 390 */           this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/* 391 */         } else if (this.invalidationSize == i) {
/* 392 */           this.invalidationSize = trim(this.invalidationSize, this.invalidationListeners);
/* 393 */           if (this.invalidationSize == i) {
/* 394 */             j = i * 3 / 2 + 1;
/* 395 */             this.invalidationListeners = ((InvalidationListener[])Arrays.copyOf(this.invalidationListeners, j));
/*     */           }
/*     */         }
/* 398 */         this.invalidationListeners[(this.invalidationSize++)] = paramInvalidationListener;
/*     */       }
/* 400 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 405 */       if (this.invalidationListeners != null) {
/* 406 */         for (int i = 0; i < this.invalidationSize; i++) {
/* 407 */           if (paramInvalidationListener.equals(this.invalidationListeners[i])) {
/* 408 */             if (this.invalidationSize == 1) {
/* 409 */               if ((this.changeSize == 1) && (this.listChangeSize == 0))
/* 410 */                 return new ListExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/* 411 */               if ((this.changeSize == 0) && (this.listChangeSize == 1)) {
/* 412 */                 return new ListExpressionHelper.SingleListChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 414 */               this.invalidationListeners = null;
/* 415 */               this.invalidationSize = 0; break;
/* 416 */             }if ((this.invalidationSize == 2) && (this.changeSize == 0) && (this.listChangeSize == 0)) {
/* 417 */               return new ListExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[(1 - i)], null);
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
/* 428 */             this.invalidationSize -= 1;
/* 429 */             if (!this.locked) {
/* 430 */               this.invalidationListeners[this.invalidationSize] = null;
/*     */             }
/*     */ 
/* 433 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 437 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 442 */       if (this.changeListeners == null) {
/* 443 */         this.changeListeners = new ChangeListener[] { paramChangeListener };
/* 444 */         this.changeSize = 1;
/*     */       } else {
/* 446 */         int i = this.changeListeners.length;
/*     */         int j;
/* 447 */         if (this.locked) {
/* 448 */           j = this.changeSize < i ? i : i * 3 / 2 + 1;
/* 449 */           this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/* 450 */         } else if (this.changeSize == i) {
/* 451 */           this.changeSize = trim(this.changeSize, this.changeListeners);
/* 452 */           if (this.changeSize == i) {
/* 453 */             j = i * 3 / 2 + 1;
/* 454 */             this.changeListeners = ((ChangeListener[])Arrays.copyOf(this.changeListeners, j));
/*     */           }
/*     */         }
/* 457 */         this.changeListeners[(this.changeSize++)] = paramChangeListener;
/*     */       }
/* 459 */       if (this.changeSize == 1) {
/* 460 */         this.currentValue = ((ObservableList)this.observable.getValue());
/*     */       }
/* 462 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 467 */       if (this.changeListeners != null) {
/* 468 */         for (int i = 0; i < this.changeSize; i++) {
/* 469 */           if (paramChangeListener.equals(this.changeListeners[i])) {
/* 470 */             if (this.changeSize == 1) {
/* 471 */               if ((this.invalidationSize == 1) && (this.listChangeSize == 0))
/* 472 */                 return new ListExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 473 */               if ((this.invalidationSize == 0) && (this.listChangeSize == 1)) {
/* 474 */                 return new ListExpressionHelper.SingleListChange(this.observable, this.listChangeListeners[0], null);
/*     */               }
/* 476 */               this.changeListeners = null;
/* 477 */               this.changeSize = 0; break;
/* 478 */             }if ((this.changeSize == 2) && (this.invalidationSize == 0) && (this.listChangeSize == 0)) {
/* 479 */               return new ListExpressionHelper.SingleChange(this.observable, this.changeListeners[(1 - i)], null);
/*     */             }
/* 481 */             int j = this.changeSize - i - 1;
/* 482 */             ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 483 */             if (this.locked) {
/* 484 */               this.changeListeners = new ChangeListener[this.changeListeners.length];
/* 485 */               System.arraycopy(arrayOfChangeListener, 0, this.changeListeners, 0, i + 1);
/*     */             }
/* 487 */             if (j > 0) {
/* 488 */               System.arraycopy(arrayOfChangeListener, i + 1, this.changeListeners, i, j);
/*     */             }
/* 490 */             this.changeSize -= 1;
/* 491 */             if (!this.locked) {
/* 492 */               this.changeListeners[this.changeSize] = null;
/*     */             }
/*     */ 
/* 495 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 499 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 504 */       if (this.listChangeListeners == null) {
/* 505 */         this.listChangeListeners = new ListChangeListener[] { paramListChangeListener };
/* 506 */         this.listChangeSize = 1;
/*     */       } else {
/* 508 */         int i = this.listChangeListeners.length;
/*     */         int j;
/* 509 */         if (this.locked) {
/* 510 */           j = this.listChangeSize < i ? i : i * 3 / 2 + 1;
/* 511 */           this.listChangeListeners = ((ListChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/* 512 */         } else if (this.listChangeSize == i) {
/* 513 */           this.listChangeSize = trim(this.listChangeSize, this.listChangeListeners);
/* 514 */           if (this.listChangeSize == i) {
/* 515 */             j = i * 3 / 2 + 1;
/* 516 */             this.listChangeListeners = ((ListChangeListener[])Arrays.copyOf(this.listChangeListeners, j));
/*     */           }
/*     */         }
/* 519 */         this.listChangeListeners[(this.listChangeSize++)] = paramListChangeListener;
/*     */       }
/* 521 */       if (this.listChangeSize == 1) {
/* 522 */         this.currentValue = ((ObservableList)this.observable.getValue());
/*     */       }
/* 524 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 529 */       if (this.listChangeListeners != null) {
/* 530 */         for (int i = 0; i < this.listChangeSize; i++) {
/* 531 */           if (paramListChangeListener.equals(this.listChangeListeners[i])) {
/* 532 */             if (this.listChangeSize == 1) {
/* 533 */               if ((this.invalidationSize == 1) && (this.changeSize == 0))
/* 534 */                 return new ListExpressionHelper.SingleInvalidation(this.observable, this.invalidationListeners[0], null);
/* 535 */               if ((this.invalidationSize == 0) && (this.changeSize == 1)) {
/* 536 */                 return new ListExpressionHelper.SingleChange(this.observable, this.changeListeners[0], null);
/*     */               }
/* 538 */               this.listChangeListeners = null;
/* 539 */               this.listChangeSize = 0; break;
/* 540 */             }if ((this.listChangeSize == 2) && (this.invalidationSize == 0) && (this.changeSize == 0)) {
/* 541 */               return new ListExpressionHelper.SingleListChange(this.observable, this.listChangeListeners[(1 - i)], null);
/*     */             }
/* 543 */             int j = this.listChangeSize - i - 1;
/* 544 */             ListChangeListener[] arrayOfListChangeListener = this.listChangeListeners;
/* 545 */             if (this.locked) {
/* 546 */               this.listChangeListeners = new ListChangeListener[this.listChangeListeners.length];
/* 547 */               System.arraycopy(arrayOfListChangeListener, 0, this.listChangeListeners, 0, i + 1);
/*     */             }
/* 549 */             if (j > 0) {
/* 550 */               System.arraycopy(arrayOfListChangeListener, i + 1, this.listChangeListeners, i, j);
/*     */             }
/* 552 */             this.listChangeSize -= 1;
/* 553 */             if (!this.locked) {
/* 554 */               this.listChangeListeners[this.listChangeSize] = null;
/*     */             }
/*     */ 
/* 557 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 561 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 566 */       if ((this.changeSize == 0) && (this.listChangeSize == 0)) {
/* 567 */         notifyListeners(this.currentValue, null);
/*     */       } else {
/* 569 */         ObservableList localObservableList1 = this.currentValue;
/* 570 */         this.currentValue = ((ObservableList)this.observable.getValue());
/* 571 */         if (this.currentValue != localObservableList1) {
/* 572 */           NonIterableChange.GenericAddRemoveChange localGenericAddRemoveChange = null;
/* 573 */           if (this.listChangeSize > 0) {
/* 574 */             int i = this.currentValue == null ? 0 : this.currentValue.size();
/* 575 */             ObservableList localObservableList2 = localObservableList1 == null ? FXCollections.emptyObservableList() : FXCollections.unmodifiableObservableList(localObservableList1);
/*     */ 
/* 578 */             localGenericAddRemoveChange = new NonIterableChange.GenericAddRemoveChange(0, i, localObservableList2, this.observable);
/*     */           }
/* 580 */           notifyListeners(localObservableList1, localGenericAddRemoveChange);
/*     */         } else {
/* 582 */           notifyListeners(this.currentValue, null);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 589 */       ListExpressionHelper.MappedChange localMappedChange = this.listChangeSize == 0 ? null : new ListExpressionHelper.MappedChange(this.observable, paramChange, null);
/* 590 */       notifyListeners(this.currentValue, localMappedChange);
/*     */     }
/*     */ 
/*     */     private void notifyListeners(ObservableList<E> paramObservableList, ListChangeListener.Change<E> paramChange) {
/* 594 */       InvalidationListener[] arrayOfInvalidationListener = this.invalidationListeners;
/* 595 */       int i = this.invalidationSize;
/* 596 */       ChangeListener[] arrayOfChangeListener = this.changeListeners;
/* 597 */       int j = this.changeSize;
/* 598 */       ListChangeListener[] arrayOfListChangeListener = this.listChangeListeners;
/* 599 */       int k = this.listChangeSize;
/*     */       try {
/* 601 */         this.locked = true;
/* 602 */         for (int m = 0; m < i; m++) {
/* 603 */           arrayOfInvalidationListener[m].invalidated(this.observable);
/*     */         }
/* 605 */         if (paramChange != null) {
/* 606 */           for (m = 0; m < j; m++) {
/* 607 */             arrayOfChangeListener[m].changed(this.observable, paramObservableList, this.currentValue);
/*     */           }
/* 609 */           for (m = 0; m < k; m++)
/* 610 */             arrayOfListChangeListener[m].onChanged(paramChange);
/*     */         }
/*     */       }
/*     */       finally {
/* 614 */         this.locked = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleListChange<E> extends ListExpressionHelper<E>
/*     */   {
/*     */     private final ListChangeListener<? super E> listener;
/*     */     private ObservableList<E> currentValue;
/*     */ 
/*     */     private SingleListChange(ObservableListValue<E> paramObservableListValue, ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 268 */       super();
/* 269 */       this.listener = paramListChangeListener;
/* 270 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 275 */       return new ListExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 280 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 285 */       return new ListExpressionHelper.Generic(this.observable, paramChangeListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 290 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 295 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramListChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 300 */       return paramListChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 305 */       ObservableList localObservableList1 = this.currentValue;
/* 306 */       this.currentValue = ((ObservableList)this.observable.getValue());
/* 307 */       if (this.currentValue != localObservableList1) {
/* 308 */         int i = this.currentValue == null ? 0 : this.currentValue.size();
/* 309 */         ObservableList localObservableList2 = localObservableList1 == null ? FXCollections.emptyObservableList() : FXCollections.unmodifiableObservableList(localObservableList1);
/*     */ 
/* 312 */         NonIterableChange.GenericAddRemoveChange localGenericAddRemoveChange = new NonIterableChange.GenericAddRemoveChange(0, i, localObservableList2, this.observable);
/* 313 */         this.listener.onChanged(localGenericAddRemoveChange);
/*     */       }
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 319 */       this.listener.onChanged(new ListExpressionHelper.MappedChange(this.observable, paramChange, null));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleChange<E> extends ListExpressionHelper<E>
/*     */   {
/*     */     private final ChangeListener<? super ObservableList<E>> listener;
/*     */     private ObservableList<E> currentValue;
/*     */ 
/*     */     private SingleChange(ObservableListValue<E> paramObservableListValue, ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 212 */       super();
/* 213 */       this.listener = paramChangeListener;
/* 214 */       this.currentValue = ((ObservableList)paramObservableListValue.getValue());
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 219 */       return new ListExpressionHelper.Generic(this.observable, paramInvalidationListener, this.listener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 224 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 229 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 234 */       return paramChangeListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 239 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramListChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 244 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 249 */       ObservableList localObservableList = this.currentValue;
/* 250 */       this.currentValue = ((ObservableList)this.observable.getValue());
/* 251 */       if (this.currentValue != localObservableList)
/* 252 */         this.listener.changed(this.observable, localObservableList, this.currentValue);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 258 */       this.listener.changed(this.observable, this.currentValue, this.currentValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SingleInvalidation<E> extends ListExpressionHelper<E>
/*     */   {
/*     */     private final InvalidationListener listener;
/*     */ 
/*     */     private SingleInvalidation(ObservableListValue<E> paramObservableListValue, InvalidationListener paramInvalidationListener)
/*     */     {
/* 161 */       super();
/* 162 */       this.listener = paramInvalidationListener;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 167 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramInvalidationListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 172 */       return paramInvalidationListener.equals(this.listener) ? null : this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 177 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 182 */       return this;
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 187 */       return new ListExpressionHelper.Generic(this.observable, this.listener, paramListChangeListener, null);
/*     */     }
/*     */ 
/*     */     protected ListExpressionHelper<E> removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 192 */       return this;
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent()
/*     */     {
/* 197 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 202 */       this.listener.invalidated(this.observable);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.ListExpressionHelper
 * JD-Core Version:    0.6.2
 */