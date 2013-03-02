/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.AccordionSkin;
/*     */ import com.sun.javafx.scene.control.skin.TitledPaneSkin;
/*     */ import com.sun.javafx.scene.traversal.Direction;
/*     */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.scene.control.TitledPane;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class TitledPaneBehavior extends BehaviorBase<TitledPane>
/*     */ {
/*     */   private TitledPane titledPane;
/*     */   private static final String PRESS_ACTION = "Press";
/*  59 */   protected static final List<KeyBinding> TITLEDPANE_BINDINGS = new ArrayList();
/*     */ 
/*     */   public TitledPaneBehavior(TitledPane paramTitledPane)
/*     */   {
/*  47 */     super(paramTitledPane);
/*  48 */     this.titledPane = paramTitledPane;
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/*  68 */     return TITLEDPANE_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/*     */     TitledPane localTitledPane;
/*  72 */     if ("Press".equals(paramString)) {
/*  73 */       localTitledPane = (TitledPane)getControl();
/*  74 */       if ((localTitledPane.isCollapsible()) && (localTitledPane.isFocused())) {
/*  75 */         localTitledPane.setExpanded(!localTitledPane.isExpanded());
/*  76 */         localTitledPane.requestFocus();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       TitledPaneSkin localTitledPaneSkin;
/*  78 */       if ("TraverseNext".equals(paramString)) {
/*  79 */         localTitledPane = (TitledPane)getControl();
/*  80 */         localTitledPaneSkin = (TitledPaneSkin)localTitledPane.getSkin();
/*  81 */         localTitledPaneSkin.getContentRegion().getImpl_traversalEngine().getTopLeftFocusableNode();
/*     */ 
/*  83 */         if ((!localTitledPane.isExpanded()) || (localTitledPaneSkin.getContentRegion().getImpl_traversalEngine().registeredNodes.isEmpty()))
/*     */         {
/*  86 */           if ((((TitledPane)getControl()).getParent() != null) && ((((TitledPane)getControl()).getParent() instanceof AccordionSkin)))
/*  87 */             localTitledPaneSkin.getContentRegion().getImpl_traversalEngine().trav(((TitledPane)getControl()).getParent(), Direction.NEXT);
/*     */           else
/*  89 */             super.callAction(paramString);
/*     */         }
/*     */       }
/*  92 */       else if ("TraversePrevious".equals(paramString)) {
/*  93 */         localTitledPane = (TitledPane)getControl();
/*  94 */         localTitledPaneSkin = (TitledPaneSkin)localTitledPane.getSkin();
/*     */ 
/*  97 */         if ((((TitledPane)getControl()).getParent() != null) && ((((TitledPane)getControl()).getParent() instanceof AccordionSkin)))
/*  98 */           localTitledPaneSkin.getContentRegion().getImpl_traversalEngine().trav(((TitledPane)getControl()).getParent(), Direction.PREVIOUS);
/*     */         else
/* 100 */           super.callAction(paramString);
/*     */       }
/*     */       else {
/* 103 */         super.callAction(paramString);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 114 */     super.mousePressed(paramMouseEvent);
/* 115 */     TitledPane localTitledPane = (TitledPane)getControl();
/* 116 */     localTitledPane.requestFocus();
/*     */   }
/*     */ 
/*     */   public void expand()
/*     */   {
/* 124 */     this.titledPane.setExpanded(true);
/*     */   }
/*     */ 
/*     */   public void collapse() {
/* 128 */     this.titledPane.setExpanded(false);
/*     */   }
/*     */ 
/*     */   public void toggle() {
/* 132 */     this.titledPane.setExpanded(!this.titledPane.isExpanded());
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  61 */     TITLEDPANE_BINDINGS.add(new KeyBinding(KeyCode.ENTER, "Press"));
/*  62 */     TITLEDPANE_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "Press"));
/*  63 */     TITLEDPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*  64 */     TITLEDPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TitledPaneBehavior
 * JD-Core Version:    0.6.2
 */