/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ListExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ReadOnlyListPropertyBase<E> extends ReadOnlyListProperty<E>
/*     */ {
/*     */   private ListExpressionHelper<E> helper;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  66 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  71 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/*  76 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/*  81 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*  86 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*  91 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 106 */     ListExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 123 */     ListExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyListPropertyBase
 * JD-Core Version:    0.6.2
 */