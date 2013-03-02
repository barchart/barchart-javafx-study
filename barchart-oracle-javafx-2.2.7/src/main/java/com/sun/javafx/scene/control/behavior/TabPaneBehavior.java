/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.TabPaneSkin;
/*     */ import com.sun.javafx.scene.traversal.TraversalEngine;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.Tab;
/*     */ import javafx.scene.control.TabPane;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class TabPaneBehavior extends BehaviorBase<TabPane>
/*     */ {
/*     */   private static final String HOME = "Home";
/*     */   private static final String END = "End";
/*     */   private static final String CTRL_PAGE_UP = "Ctrl_Page_Up";
/*     */   private static final String CTRL_PAGE_DOWN = "Ctrl_Page_Down";
/*     */   private static final String CTRL_TAB = "Ctrl_Tab";
/*     */   private static final String CTRL_SHIFT_TAB = "Ctrl_Shift_Tab";
/*  54 */   protected static final List<KeyBinding> TABPANE_BINDINGS = new ArrayList();
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/*  66 */     return TABPANE_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/*  70 */     if (("TraverseLeft".equals(paramString)) || ("TraverseUp".equals(paramString)))
/*     */     {
/*  72 */       if (((TabPane)getControl()).isFocused())
/*  73 */         selectPreviousTab();
/*     */     }
/*  75 */     else if (("TraverseRight".equals(paramString)) || ("TraverseDown".equals(paramString)))
/*     */     {
/*  77 */       if (((TabPane)getControl()).isFocused())
/*  78 */         selectNextTab();
/*     */     }
/*     */     else
/*     */     {
/*     */       TabPane localTabPane;
/*  80 */       if ("TraverseNext".equals(paramString)) {
/*  81 */         localTabPane = (TabPane)getControl();
/*  82 */         TabPaneSkin localTabPaneSkin = (TabPaneSkin)localTabPane.getSkin();
/*  83 */         if (localTabPaneSkin.getSelectedTabContentRegion() != null) {
/*  84 */           localTabPaneSkin.getSelectedTabContentRegion().getImpl_traversalEngine().getTopLeftFocusableNode();
/*  85 */           if (localTabPaneSkin.getSelectedTabContentRegion().getImpl_traversalEngine().registeredNodes.isEmpty())
/*     */           {
/*  87 */             Parent localParent = null;
/*  88 */             localParent = getFirstPopulatedInnerTraversalEngine(localTabPaneSkin.getSelectedTabContentRegion().getChildren());
/*  89 */             if (localParent != null) {
/*  90 */               int i = 0;
/*  91 */               for (Node localNode : localParent.getImpl_traversalEngine().registeredNodes) {
/*  92 */                 if ((!localNode.isFocused()) && (localNode.impl_isTreeVisible()) && (!localNode.isDisabled())) {
/*  93 */                   localNode.requestFocus();
/*  94 */                   i = 1;
/*  95 */                   break;
/*     */                 }
/*     */               }
/*  98 */               if (i == 0)
/*  99 */                 super.callAction(paramString);
/*     */             }
/*     */             else
/*     */             {
/* 103 */               super.callAction(paramString);
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 108 */           super.callAction(paramString);
/*     */         }
/* 110 */       } else if (("Ctrl_Tab".equals(paramString)) || ("Ctrl_Page_Down".equals(paramString))) {
/* 111 */         localTabPane = (TabPane)getControl();
/* 112 */         if (localTabPane.getSelectionModel().getSelectedIndex() == localTabPane.getTabs().size() - 1)
/* 113 */           localTabPane.getSelectionModel().selectFirst();
/*     */         else {
/* 115 */           selectNextTab();
/*     */         }
/* 117 */         localTabPane.requestFocus();
/* 118 */       } else if (("Ctrl_Shift_Tab".equals(paramString)) || ("Ctrl_Page_Up".equals(paramString))) {
/* 119 */         localTabPane = (TabPane)getControl();
/* 120 */         if (localTabPane.getSelectionModel().getSelectedIndex() == 0)
/* 121 */           localTabPane.getSelectionModel().selectLast();
/*     */         else {
/* 123 */           selectPreviousTab();
/*     */         }
/* 125 */         localTabPane.requestFocus();
/* 126 */       } else if ("Home".equals(paramString)) {
/* 127 */         if (((TabPane)getControl()).isFocused())
/* 128 */           ((TabPane)getControl()).getSelectionModel().selectFirst();
/*     */       }
/* 130 */       else if ("End".equals(paramString)) {
/* 131 */         if (((TabPane)getControl()).isFocused())
/* 132 */           ((TabPane)getControl()).getSelectionModel().selectLast();
/*     */       }
/*     */       else {
/* 135 */         super.callAction(paramString);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Parent getFirstPopulatedInnerTraversalEngine(ObservableList<Node> paramObservableList) {
/* 141 */     Parent localParent = null;
/* 142 */     for (Node localNode : paramObservableList) {
/* 143 */       if ((localNode instanceof Parent)) {
/* 144 */         if ((((Parent)localNode).getImpl_traversalEngine() != null) && (!((Parent)localNode).getImpl_traversalEngine().registeredNodes.isEmpty())) {
/* 145 */           localParent = (Parent)localNode;
/*     */         }
/*     */         else
/*     */         {
/* 149 */           localParent = getFirstPopulatedInnerTraversalEngine(((Parent)localNode).getChildrenUnmodifiable());
/* 150 */           if (localParent != null) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 156 */     return localParent;
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 167 */     super.mousePressed(paramMouseEvent);
/* 168 */     TabPane localTabPane = (TabPane)getControl();
/* 169 */     localTabPane.requestFocus();
/*     */   }
/*     */ 
/*     */   public TabPaneBehavior(TabPane paramTabPane)
/*     */   {
/* 177 */     super(paramTabPane);
/*     */   }
/*     */ 
/*     */   public void selectTab(Tab paramTab) {
/* 181 */     ((TabPane)getControl()).getSelectionModel().select(paramTab);
/*     */   }
/*     */ 
/*     */   public void closeTab(Tab paramTab) {
/* 185 */     TabPane localTabPane = (TabPane)getControl();
/*     */ 
/* 187 */     int i = localTabPane.getTabs().indexOf(paramTab);
/* 188 */     if (paramTab.isSelected()) {
/* 189 */       if (i == 0) {
/* 190 */         if (localTabPane.getTabs().size() > 0)
/* 191 */           localTabPane.getSelectionModel().selectFirst();
/*     */       }
/*     */       else {
/* 194 */         localTabPane.getSelectionModel().selectPrevious();
/*     */       }
/*     */     }
/* 197 */     if (i != -1) {
/* 198 */       localTabPane.getTabs().remove(i);
/*     */     }
/* 200 */     if (paramTab.getOnClosed() != null)
/* 201 */       Event.fireEvent(paramTab, new Event(Tab.CLOSED_EVENT));
/*     */   }
/*     */ 
/*     */   public void selectNextTab()
/*     */   {
/* 207 */     SingleSelectionModel localSingleSelectionModel = ((TabPane)getControl()).getSelectionModel();
/* 208 */     int i = localSingleSelectionModel.getSelectedIndex();
/* 209 */     int j = i;
/* 210 */     while (j < ((TabPane)getControl()).getTabs().size()) {
/* 211 */       localSingleSelectionModel.selectNext();
/* 212 */       j++;
/* 213 */       if (!((Tab)localSingleSelectionModel.getSelectedItem()).isDisable()) {
/* 214 */         return;
/*     */       }
/*     */     }
/* 217 */     localSingleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   public void selectPreviousTab()
/*     */   {
/* 222 */     SingleSelectionModel localSingleSelectionModel = ((TabPane)getControl()).getSelectionModel();
/* 223 */     int i = localSingleSelectionModel.getSelectedIndex();
/* 224 */     int j = i;
/* 225 */     while (j > 0) {
/* 226 */       localSingleSelectionModel.selectPrevious();
/* 227 */       j--;
/* 228 */       if (!((Tab)localSingleSelectionModel.getSelectedItem()).isDisable()) {
/* 229 */         return;
/*     */       }
/*     */     }
/* 232 */     localSingleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  56 */     TABPANE_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*  57 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.HOME, "Home"));
/*  58 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.END, "End"));
/*  59 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "Ctrl_Page_Up").ctrl());
/*  60 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "Ctrl_Page_Down").ctrl());
/*  61 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "Ctrl_Tab").ctrl());
/*  62 */     TABPANE_BINDINGS.add(new KeyBinding(KeyCode.TAB, "Ctrl_Shift_Tab").shift().ctrl());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TabPaneBehavior
 * JD-Core Version:    0.6.2
 */