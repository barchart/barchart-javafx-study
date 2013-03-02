/*    */ package javafx.scene.effect;
/*    */ 
/*    */ import com.sun.javafx.beans.event.AbstractNotifyListener;
/*    */ import javafx.beans.value.ObservableValue;
/*    */ 
/*    */ abstract class EffectChangeListener extends AbstractNotifyListener
/*    */ {
/*    */   protected ObservableValue registredOn;
/*    */ 
/*    */   public void register(ObservableValue paramObservableValue)
/*    */   {
/* 36 */     if (this.registredOn == paramObservableValue) {
/* 37 */       return;
/*    */     }
/* 39 */     if (this.registredOn != null) {
/* 40 */       this.registredOn.removeListener(getWeakListener());
/*    */     }
/* 42 */     this.registredOn = paramObservableValue;
/* 43 */     if (this.registredOn != null)
/* 44 */       this.registredOn.addListener(getWeakListener());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.effect.EffectChangeListener
 * JD-Core Version:    0.6.2
 */