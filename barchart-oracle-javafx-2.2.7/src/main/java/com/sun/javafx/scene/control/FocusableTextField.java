/*    */ package com.sun.javafx.scene.control;
/*    */ 
/*    */ import com.sun.javafx.event.EventDispatchChainImpl;
/*    */ import javafx.event.EventDispatchChain;
/*    */ import javafx.scene.control.TextField;
/*    */ 
/*    */ public final class FocusableTextField extends TextField
/*    */ {
/*    */   public void setFakeFocus(boolean paramBoolean)
/*    */   {
/* 37 */     setFocused(paramBoolean);
/*    */   }
/*    */ 
/*    */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*    */   {
/* 42 */     EventDispatchChainImpl localEventDispatchChainImpl = new EventDispatchChainImpl();
/* 43 */     localEventDispatchChainImpl.append(getEventDispatcher());
/* 44 */     return localEventDispatchChainImpl;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.FocusableTextField
 * JD-Core Version:    0.6.2
 */