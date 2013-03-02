/*    */ package com.sun.javafx.scene.web.behavior;
/*    */ 
/*    */ import com.sun.javafx.PlatformUtil;
/*    */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*    */ import com.sun.javafx.scene.control.behavior.KeyBinding;
/*    */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*    */ import com.sun.javafx.scene.web.skin.HTMLEditorSkin;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javafx.scene.input.KeyCode;
/*    */ import javafx.scene.web.HTMLEditor;
/*    */ 
/*    */ public class HTMLEditorBehavior extends BehaviorBase<HTMLEditor>
/*    */ {
/* 28 */   protected static final List<KeyBinding> HTMLEDITOR_BINDINGS = new ArrayList();
/*    */ 
/*    */   public HTMLEditorBehavior(HTMLEditor paramHTMLEditor)
/*    */   {
/* 47 */     super(paramHTMLEditor);
/*    */   }
/*    */ 
/*    */   protected List<KeyBinding> createKeyBindings()
/*    */   {
/* 52 */     return HTMLEDITOR_BINDINGS;
/*    */   }
/*    */ 
/*    */   protected void callAction(String paramString)
/*    */   {
/*    */     HTMLEditor localHTMLEditor;
/*    */     HTMLEditorSkin localHTMLEditorSkin;
/* 57 */     if (("bold".equals(paramString)) || ("italic".equals(paramString)) || ("underline".equals(paramString))) {
/* 58 */       localHTMLEditor = (HTMLEditor)getControl();
/* 59 */       localHTMLEditorSkin = (HTMLEditorSkin)localHTMLEditor.getSkin();
/* 60 */       localHTMLEditorSkin.keyboardShortcuts(paramString);
/* 61 */     } else if ("F12".equals(paramString)) {
/* 62 */       localHTMLEditor = (HTMLEditor)getControl();
/* 63 */       localHTMLEditorSkin = (HTMLEditorSkin)localHTMLEditor.getSkin();
/* 64 */       localHTMLEditorSkin.getImpl_traversalEngine().getTopLeftFocusableNode();
/*    */     } else {
/* 66 */       super.callAction(paramString);
/*    */     }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 31 */     if (PlatformUtil.isMac()) {
/* 32 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.B, "bold").meta());
/* 33 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.I, "italic").meta());
/* 34 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.U, "underline").meta());
/*    */     } else {
/* 36 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.B, "bold").ctrl());
/* 37 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.I, "italic").ctrl());
/* 38 */       HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.U, "underline").ctrl());
/*    */     }
/*    */ 
/* 41 */     HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.F12, "F12"));
/* 42 */     HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext").ctrl());
/* 43 */     HTMLEDITOR_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").ctrl().shift());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.behavior.HTMLEditorBehavior
 * JD-Core Version:    0.6.2
 */