/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.Accordion;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.TitledPane;
/*     */ import javafx.scene.input.KeyCode;
/*     */ 
/*     */ public class AccordionBehavior extends BehaviorBase<Accordion>
/*     */ {
/*     */   private AccordionFocusModel focusModel;
/*     */   private static final String HOME = "Home";
/*     */   private static final String END = "End";
/*     */   private static final String PAGE_UP = "Page_Up";
/*     */   private static final String PAGE_DOWN = "Page_Down";
/*     */   private static final String CTRL_PAGE_UP = "Ctrl_Page_Up";
/*     */   private static final String CTRL_PAGE_DOWN = "Ctrl_Page_Down";
/*     */   private static final String CTRL_TAB = "Ctrl_Tab";
/*     */   private static final String CTRL_SHIFT_TAB = "Ctrl_Shift_Tab";
/*  63 */   protected static final List<KeyBinding> ACCORDION_BINDINGS = new ArrayList();
/*     */ 
/*     */   public AccordionBehavior(Accordion paramAccordion)
/*     */   {
/*  44 */     super(paramAccordion);
/*  45 */     this.focusModel = new AccordionFocusModel(paramAccordion);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings()
/*     */   {
/*  77 */     return ACCORDION_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callAction(String paramString) {
/*  81 */     Accordion localAccordion = (Accordion)getControl();
/*     */     int i;
/*  82 */     if (("TraverseLeft".equals(paramString)) || ("TraverseUp".equals(paramString)) || ("Page_Up".equals(paramString))) {
/*  83 */       if ((this.focusModel.getFocusedIndex() != -1) && (((TitledPane)localAccordion.getPanes().get(this.focusModel.getFocusedIndex())).isFocused())) {
/*  84 */         this.focusModel.focusPrevious();
/*  85 */         i = this.focusModel.getFocusedIndex();
/*  86 */         ((TitledPane)localAccordion.getPanes().get(i)).requestFocus();
/*  87 */         if ("Page_Up".equals(paramString))
/*  88 */           ((TitledPane)localAccordion.getPanes().get(i)).setExpanded(true);
/*     */       }
/*     */     }
/*  91 */     else if (("TraverseRight".equals(paramString)) || ("TraverseDown".equals(paramString)) || ("Page_Down".equals(paramString))) {
/*  92 */       if ((this.focusModel.getFocusedIndex() != -1) && (((TitledPane)localAccordion.getPanes().get(this.focusModel.getFocusedIndex())).isFocused())) {
/*  93 */         this.focusModel.focusNext();
/*  94 */         i = this.focusModel.getFocusedIndex();
/*  95 */         ((TitledPane)localAccordion.getPanes().get(i)).requestFocus();
/*  96 */         if ("Page_Down".equals(paramString))
/*  97 */           ((TitledPane)localAccordion.getPanes().get(i)).setExpanded(true);
/*     */       }
/*     */     }
/* 100 */     else if (("Ctrl_Tab".equals(paramString)) || ("Ctrl_Page_Down".equals(paramString))) {
/* 101 */       this.focusModel.focusNext();
/* 102 */       if (this.focusModel.getFocusedIndex() != -1) {
/* 103 */         i = this.focusModel.getFocusedIndex();
/* 104 */         ((TitledPane)localAccordion.getPanes().get(i)).requestFocus();
/* 105 */         ((TitledPane)localAccordion.getPanes().get(i)).setExpanded(true);
/*     */       }
/* 107 */     } else if (("Ctrl_Shift_Tab".equals(paramString)) || ("Ctrl_Page_Up".equals(paramString))) {
/* 108 */       this.focusModel.focusPrevious();
/* 109 */       if (this.focusModel.getFocusedIndex() != -1) {
/* 110 */         i = this.focusModel.getFocusedIndex();
/* 111 */         ((TitledPane)localAccordion.getPanes().get(i)).requestFocus();
/* 112 */         ((TitledPane)localAccordion.getPanes().get(i)).setExpanded(true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       TitledPane localTitledPane;
/* 114 */       if ("Home".equals(paramString)) {
/* 115 */         if ((this.focusModel.getFocusedIndex() != -1) && (((TitledPane)localAccordion.getPanes().get(this.focusModel.getFocusedIndex())).isFocused())) {
/* 116 */           localTitledPane = (TitledPane)localAccordion.getPanes().get(0);
/* 117 */           localTitledPane.requestFocus();
/* 118 */           localTitledPane.setExpanded(!localTitledPane.isExpanded());
/*     */         }
/* 120 */       } else if ("End".equals(paramString)) {
/* 121 */         if ((this.focusModel.getFocusedIndex() != -1) && (((TitledPane)localAccordion.getPanes().get(this.focusModel.getFocusedIndex())).isFocused())) {
/* 122 */           localTitledPane = (TitledPane)localAccordion.getPanes().get(localAccordion.getPanes().size() - 1);
/* 123 */           localTitledPane.requestFocus();
/* 124 */           localTitledPane.setExpanded(!localTitledPane.isExpanded());
/*     */         }
/*     */       }
/* 127 */       else super.callAction(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  65 */     ACCORDION_BINDINGS.addAll(TRAVERSAL_BINDINGS);
/*  66 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.HOME, "Home"));
/*  67 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.END, "End"));
/*  68 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "Page_Up"));
/*  69 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "Page_Down"));
/*  70 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "Ctrl_Page_Up").ctrl());
/*  71 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "Ctrl_Page_Down").ctrl());
/*  72 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.TAB, "Ctrl_Tab").ctrl());
/*  73 */     ACCORDION_BINDINGS.add(new KeyBinding(KeyCode.TAB, "Ctrl_Shift_Tab").shift().ctrl());
/*     */   }
/*     */ 
/*     */   static class AccordionFocusModel extends FocusModel<TitledPane>
/*     */   {
/*     */     private final Accordion accordion;
/*     */ 
/*     */     public AccordionFocusModel(final Accordion paramAccordion)
/*     */     {
/* 136 */       if (paramAccordion == null) {
/* 137 */         throw new IllegalArgumentException("Accordion can not be null");
/*     */       }
/* 139 */       this.accordion = paramAccordion;
/* 140 */       this.accordion.focusedProperty().addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 143 */           if (paramAnonymousBoolean2.booleanValue())
/* 144 */             if (paramAccordion.getExpandedPane() != null) {
/* 145 */               paramAccordion.getExpandedPane().requestFocus();
/*     */             }
/*     */             else
/*     */             {
/* 150 */               ((TitledPane)paramAccordion.getPanes().get(0)).requestFocus();
/*     */             }
/*     */         }
/*     */       });
/* 155 */       this.accordion.getPanes().addListener(new ListChangeListener()
/*     */       {
/*     */         public void onChanged(ListChangeListener.Change<? extends TitledPane> paramAnonymousChange) {
/* 158 */           while (paramAnonymousChange.next())
/* 159 */             if (paramAnonymousChange.wasAdded())
/* 160 */               for (final TitledPane localTitledPane : paramAnonymousChange.getAddedSubList())
/* 161 */                 localTitledPane.focusedProperty().addListener(new ChangeListener()
/*     */                 {
/*     */                   public void changed(ObservableValue<? extends Boolean> paramAnonymous2ObservableValue, Boolean paramAnonymous2Boolean1, Boolean paramAnonymous2Boolean2) {
/* 164 */                     if (paramAnonymous2Boolean2.booleanValue())
/* 165 */                       AccordionBehavior.AccordionFocusModel.this.focus(AccordionBehavior.AccordionFocusModel.2.this.val$accordion.getPanes().indexOf(localTitledPane));
/*     */                   }
/*     */                 });
/*     */         }
/*     */       });
/* 175 */       for (final TitledPane localTitledPane : this.accordion.getPanes())
/* 176 */         localTitledPane.focusedProperty().addListener(new ChangeListener()
/*     */         {
/*     */           public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 179 */             if (paramAnonymousBoolean2.booleanValue())
/* 180 */               AccordionBehavior.AccordionFocusModel.this.focus(paramAccordion.getPanes().indexOf(localTitledPane));
/*     */           }
/*     */         });
/*     */     }
/*     */ 
/*     */     protected int getItemCount()
/*     */     {
/* 189 */       ObservableList localObservableList = this.accordion.getPanes();
/* 190 */       return localObservableList == null ? 0 : localObservableList.size();
/*     */     }
/*     */ 
/*     */     protected TitledPane getModelItem(int paramInt)
/*     */     {
/* 195 */       ObservableList localObservableList = this.accordion.getPanes();
/* 196 */       if (localObservableList == null) return null;
/* 197 */       if (paramInt < 0) return null;
/* 198 */       return (TitledPane)localObservableList.get(paramInt % localObservableList.size());
/*     */     }
/*     */ 
/*     */     public void focusPrevious() {
/* 202 */       if (getFocusedIndex() <= 0)
/* 203 */         focus(this.accordion.getPanes().size() - 1);
/*     */       else
/* 205 */         focus((getFocusedIndex() - 1) % this.accordion.getPanes().size());
/*     */     }
/*     */ 
/*     */     public void focusNext()
/*     */     {
/* 210 */       if (getFocusedIndex() == -1)
/* 211 */         focus(0);
/*     */       else
/* 213 */         focus((getFocusedIndex() + 1) % this.accordion.getPanes().size());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.AccordionBehavior
 * JD-Core Version:    0.6.2
 */