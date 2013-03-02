/*     */ package com.sun.javafx.collections.transformation;
/*     */ 
/*     */ import com.sun.javafx.collections.ListListenerHelper;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class TransformationList<E, F> extends AbstractList<E>
/*     */   implements ObservableList<E>
/*     */ {
/*     */   protected List<? extends F> source;
/*     */   protected final boolean observable;
/*     */   private ListChangeListener<F> sourceListener;
/*     */   private ListListenerHelper<E> listenerHelper;
/*     */ 
/*     */   protected TransformationList(List<? extends F> paramList)
/*     */   {
/*  67 */     if (paramList == null) {
/*  68 */       throw new NullPointerException();
/*     */     }
/*  70 */     this.source = paramList;
/*  71 */     if ((paramList instanceof ObservableList)) {
/*  72 */       this.observable = true;
/*  73 */       ((ObservableList)paramList).addListener(getListener());
/*     */     } else {
/*  75 */       this.observable = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public final List<? extends F> getDirectSource()
/*     */   {
/*  84 */     return this.source;
/*     */   }
/*     */ 
/*     */   public final List<?> getBottomMostSource()
/*     */   {
/*  94 */     List localList = this.source;
/*  95 */     while ((localList instanceof TransformationList)) {
/*  96 */       localList = ((TransformationList)localList).source;
/*     */     }
/*  98 */     return localList;
/*     */   }
/*     */ 
/*     */   private ListChangeListener<F> getListener() {
/* 102 */     if (this.sourceListener == null) {
/* 103 */       this.sourceListener = new ListChangeListener()
/*     */       {
/*     */         public void onChanged(ListChangeListener.Change<? extends F> paramAnonymousChange)
/*     */         {
/* 107 */           TransformationList.this.onSourceChanged(paramAnonymousChange);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/* 112 */     return this.sourceListener;
/*     */   }
/*     */ 
/*     */   protected abstract void onSourceChanged(ListChangeListener.Change<? extends F> paramChange);
/*     */ 
/*     */   public final void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 123 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public final void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 128 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public final void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 133 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public final void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 138 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public abstract int getSourceIndex(int paramInt);
/*     */ 
/*     */   public final int getBottomMostSourceIndex(int paramInt)
/*     */   {
/* 157 */     List localList = this.source;
/* 158 */     int i = getSourceIndex(paramInt);
/* 159 */     while ((localList instanceof TransformationList)) {
/* 160 */       TransformationList localTransformationList = (TransformationList)localList;
/* 161 */       i = localTransformationList.getSourceIndex(i);
/* 162 */       localList = localTransformationList.source;
/*     */     }
/* 164 */     return i;
/*     */   }
/*     */ 
/*     */   protected void fireChange(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 172 */     ListListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.transformation.TransformationList
 * JD-Core Version:    0.6.2
 */