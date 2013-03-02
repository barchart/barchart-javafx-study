/*    */ package javafx.beans.property;
/*    */ 
/*    */ import com.sun.javafx.binding.ExpressionHelper;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ 
/*    */ public abstract class ReadOnlyBooleanPropertyBase extends ReadOnlyBooleanProperty
/*    */ {
/*    */   ExpressionHelper<Boolean> helper;
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 65 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 70 */     this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super Boolean> paramChangeListener)
/*    */   {
/* 75 */     this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super Boolean> paramChangeListener)
/*    */   {
/* 80 */     this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*    */   }
/*    */ 
/*    */   protected void fireValueChangedEvent()
/*    */   {
/* 91 */     ExpressionHelper.fireValueChangedEvent(this.helper);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyBooleanPropertyBase
 * JD-Core Version:    0.6.2
 */