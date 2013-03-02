/*    */ package javafx.beans;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ 
/*    */ public final class WeakInvalidationListener
/*    */   implements InvalidationListener, WeakListener
/*    */ {
/*    */   private final WeakReference<InvalidationListener> ref;
/*    */ 
/*    */   public WeakInvalidationListener(InvalidationListener paramInvalidationListener)
/*    */   {
/* 59 */     if (paramInvalidationListener == null) {
/* 60 */       throw new NullPointerException("Listener must be specified.");
/*    */     }
/* 62 */     this.ref = new WeakReference(paramInvalidationListener);
/*    */   }
/*    */ 
/*    */   public boolean wasGarbageCollected()
/*    */   {
/* 70 */     return this.ref.get() == null;
/*    */   }
/*    */ 
/*    */   public void invalidated(Observable paramObservable)
/*    */   {
/* 78 */     InvalidationListener localInvalidationListener = (WeakListener)this.ref.get();
/* 79 */     if (localInvalidationListener != null) {
/* 80 */       localInvalidationListener.invalidated(paramObservable);
/*    */     }
/*    */     else
/*    */     {
/* 85 */       paramObservable.removeListener(this);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.WeakInvalidationListener
 * JD-Core Version:    0.6.2
 */