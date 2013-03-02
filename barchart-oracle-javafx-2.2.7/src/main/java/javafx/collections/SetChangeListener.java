/*    */ package javafx.collections;
/*    */ 
/*    */ public abstract interface SetChangeListener<E>
/*    */ {
/*    */   public abstract void onChanged(Change<? extends E> paramChange);
/*    */ 
/*    */   public static abstract class Change<E>
/*    */   {
/*    */     private ObservableSet<E> set;
/*    */ 
/*    */     public Change(ObservableSet<E> paramObservableSet)
/*    */     {
/* 51 */       this.set = paramObservableSet;
/*    */     }
/*    */ 
/*    */     public ObservableSet<E> getSet()
/*    */     {
/* 59 */       return this.set;
/*    */     }
/*    */ 
/*    */     public abstract boolean wasAdded();
/*    */ 
/*    */     public abstract boolean wasRemoved();
/*    */ 
/*    */     public abstract E getElementAdded();
/*    */ 
/*    */     public abstract E getElementRemoved();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.SetChangeListener
 * JD-Core Version:    0.6.2
 */