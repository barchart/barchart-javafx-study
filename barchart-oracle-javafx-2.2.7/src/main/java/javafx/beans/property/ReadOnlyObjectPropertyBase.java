/*    */ package javafx.beans.property;
/*    */ 
/*    */ import com.sun.javafx.binding.ExpressionHelper;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ 
/*    */ public abstract class ReadOnlyObjectPropertyBase<T> extends ReadOnlyObjectProperty<T>
/*    */ {
/*    */   ExpressionHelper<T> helper;
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 67 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 72 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/* 77 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*    */   {
/* 82 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*    */   }
/*    */ 
/*    */   protected void fireValueChangedEvent()
/*    */   {
/* 93 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyObjectPropertyBase
 * JD-Core Version:    0.6.2
 */