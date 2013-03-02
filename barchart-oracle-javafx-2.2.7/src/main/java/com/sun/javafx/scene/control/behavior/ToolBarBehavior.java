/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.ToolBar;
/*    */ import javafx.scene.input.KeyCode;
/*    */ 
/*    */ public class ToolBarBehavior extends BehaviorBase<ToolBar>
/*    */ {
/*    */   private static final String CTRL_F5 = "Ctrl_F5";
/* 51 */   protected static final List<KeyBinding> TOOLBAR_BINDINGS = new ArrayList();
/*    */ 
/*    */   public ToolBarBehavior(ToolBar paramToolBar)
/*    */   {
/* 40 */     super(paramToolBar);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings()
/*    */   {
/* 58 */     return TOOLBAR_BINDINGS;
/*    */   }
/*    */ 
/*    */   protected void callAction(String paramString) {
/* 62 */     if ("Ctrl_F5".equals(paramString)) {
/* 63 */       ToolBar localToolBar = (ToolBar)getControl();
/* 64 */       if (!localToolBar.getItems().isEmpty())
/* 65 */         ((Node)localToolBar.getItems().get(0)).requestFocus();
/*    */     }
/*    */     else {
/* 68 */       super.callAction(paramString);
/*    */     }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 53 */     TOOLBAR_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/* 54 */     TOOLBAR_BINDINGS.add(new KeyBinding(KeyCode.F5, "Ctrl_F5").ctrl());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ToolBarBehavior
 * JD-Core Version:    0.6.2
 */