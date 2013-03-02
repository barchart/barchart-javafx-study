/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.SetExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public abstract class ReadOnlySetPropertyBase<E> extends ReadOnlySetProperty<E>
/*     */ {
/*     */   private SetExpressionHelper<E> helper;
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  68 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  73 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/*  78 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/*  83 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/*  88 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/*  93 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 108 */     SetExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 125 */     SetExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlySetPropertyBase
 * JD-Core Version:    0.6.2
 */