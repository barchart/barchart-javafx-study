/*    */ package javafx.beans.value;
/*    */ 
/*    */ import com.sun.javafx.binding.ExpressionHelper;
/*    */ import javafx.beans.InvalidationListener;
/*    */ 
/*    */ public abstract class ObservableValueBase<T>
/*    */   implements ObservableValue<T>
/*    */ {
/*    */   private ExpressionHelper<T> helper;
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 54 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/* 62 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 70 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/* 78 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*    */   }
/*    */ 
/*    */   protected void fireValueChangedEvent()
/*    */   {
/* 89 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.value.ObservableValueBase
 * JD-Core Version:    0.6.2
 */