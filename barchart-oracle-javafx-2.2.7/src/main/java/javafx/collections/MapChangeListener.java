/*    */ package javafx.collections;
/*    */ 
/*    */ public abstract interface MapChangeListener<K, V>
/*    */ {
/*    */   public abstract void onChanged(Change<? extends K, ? extends V> paramChange);
/*    */ 
/*    */   public static abstract class Change<K, V>
/*    */   {
/*    */     private final ObservableMap<K, V> map;
/*    */ 
/*    */     public Change(ObservableMap<K, V> paramObservableMap)
/*    */     {
/* 54 */       this.map = paramObservableMap;
/*    */     }
/*    */ 
/*    */     public ObservableMap<K, V> getMap()
/*    */     {
/* 62 */       return this.map;
/*    */     }
/*    */ 
/*    */     public abstract boolean wasAdded();
/*    */ 
/*    */     public abstract boolean wasRemoved();
/*    */ 
/*    */     public abstract K getKey();
/*    */ 
/*    */     public abstract V getValueAdded();
/*    */ 
/*    */     public abstract V getValueRemoved();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.collections.MapChangeListener
 * JD-Core Version:    0.6.2
 */