/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.scene.control.ComboBox;
/*    */ import javafx.scene.control.SelectionModel;
/*    */ import javafx.scene.control.SingleSelectionModel;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.input.KeyEvent;
/*    */ 
/*    */ public class ComboBoxListViewBehavior<T> extends ComboBoxBaseBehavior<T>
/*    */ {
/* 58 */   protected static final List<KeyBinding> COMBO_BOX_BINDINGS = new ArrayList();
/*    */ 
/*    */   public ComboBoxListViewBehavior(ComboBox<T> paramComboBox)
/*    */   {
/* 49 */     super(paramComboBox);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings()
/*    */   {
/* 66 */     return COMBO_BOX_BINDINGS;
/*    */   }
/*    */ 
/*    */   protected void callAction(String paramString) {
/* 70 */     if ("selectPrevious".equals(paramString))
/* 71 */       selectPrevious();
/* 72 */     else if ("selectNext".equals(paramString))
/* 73 */       selectNext();
/*    */     else
/* 75 */       super.callAction(paramString);
/*    */   }
/*    */ 
/*    */   private ComboBox<T> getComboBox()
/*    */   {
/* 80 */     return (ComboBox)getControl();
/*    */   }
/*    */ 
/*    */   private void selectPrevious() {
/* 84 */     SingleSelectionModel localSingleSelectionModel = getComboBox().getSelectionModel();
/* 85 */     if (localSingleSelectionModel == null) return;
/* 86 */     localSingleSelectionModel.selectPrevious();
/*    */   }
/*    */ 
/*    */   private void selectNext() {
/* 90 */     SingleSelectionModel localSingleSelectionModel = getComboBox().getSelectionModel();
/* 91 */     if (localSingleSelectionModel == null) return;
/* 92 */     localSingleSelectionModel.selectNext();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 60 */     COMBO_BOX_BINDINGS.add(new KeyBinding(KeyCode.UP, KeyEvent.KEY_PRESSED, "selectPrevious"));
/* 61 */     COMBO_BOX_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "selectNext"));
/* 62 */     COMBO_BOX_BINDINGS.addAll(COMBO_BOX_BASE_BINDINGS);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ComboBoxListViewBehavior
 * JD-Core Version:    0.6.2
 */