/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.AccordionBehavior;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Accordion;
/*     */ import javafx.scene.control.TitledPane;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public class AccordionSkin extends SkinBase<Accordion, AccordionBehavior>
/*     */ {
/*     */   private TitledPane firstTitledPane;
/*     */   private Rectangle clipRect;
/*  46 */   private boolean relocateAllPanes = false;
/*  47 */   private boolean resize = false;
/*  48 */   private double previousHeight = 0.0D;
/*     */ 
/* 202 */   private TitledPane expandedPane = null;
/* 203 */   private TitledPane previousPane = null;
/* 204 */   private Map<TitledPane, ChangeListener> listeners = new HashMap();
/*     */ 
/*     */   public AccordionSkin(final Accordion paramAccordion)
/*     */   {
/*  51 */     super(paramAccordion, new AccordionBehavior(paramAccordion));
/*  52 */     paramAccordion.getPanes().addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change<? extends TitledPane> paramAnonymousChange) {
/*  54 */         if (AccordionSkin.this.firstTitledPane != null) {
/*  55 */           AccordionSkin.this.firstTitledPane.getStyleClass().remove("first-titled-pane");
/*     */         }
/*  57 */         if (!paramAccordion.getPanes().isEmpty()) {
/*  58 */           AccordionSkin.this.firstTitledPane = ((TitledPane)paramAccordion.getPanes().get(0));
/*  59 */           AccordionSkin.this.firstTitledPane.getStyleClass().add("first-titled-pane");
/*     */         }
/*     */ 
/*  62 */         AccordionSkin.this.getChildren().setAll(paramAccordion.getPanes());
/*  63 */         while (paramAnonymousChange.next()) {
/*  64 */           AccordionSkin.this.removeTitledPaneListeners(paramAnonymousChange.getRemoved());
/*  65 */           AccordionSkin.this.initTitledPaneListeners(paramAnonymousChange.getAddedSubList());
/*     */         }
/*     */       }
/*     */     });
/*  70 */     if (!paramAccordion.getPanes().isEmpty()) {
/*  71 */       this.firstTitledPane = ((TitledPane)paramAccordion.getPanes().get(0));
/*  72 */       this.firstTitledPane.getStyleClass().add("first-titled-pane");
/*     */     }
/*     */ 
/*  75 */     this.clipRect = new Rectangle();
/*  76 */     setClip(this.clipRect);
/*     */ 
/*  78 */     initTitledPaneListeners(paramAccordion.getPanes());
/*  79 */     getChildren().setAll(paramAccordion.getPanes());
/*  80 */     requestLayout();
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/*  84 */     double d = 0.0D;
/*  85 */     for (Node localNode : getManagedChildren()) {
/*  86 */       d += snapSize(localNode.minHeight(paramDouble));
/*     */     }
/*  88 */     return d;
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/*  92 */     double d = 0.0D;
/*     */ 
/*  96 */     TitledPane localTitledPane1 = ((Accordion)getSkinnable()).getExpandedPane() != null ? ((Accordion)getSkinnable()).getExpandedPane() : this.previousPane;
/*  97 */     if (localTitledPane1 != null) {
/*  98 */       d = localTitledPane1.prefHeight(-1.0D);
/*     */     }
/* 100 */     for (Node localNode : getManagedChildren()) {
/* 101 */       TitledPane localTitledPane2 = (TitledPane)localNode;
/* 102 */       if (!localTitledPane2.equals(localTitledPane1))
/*     */       {
/* 106 */         d += snapSize(localTitledPane2.minHeight(paramDouble));
/*     */       }
/*     */     }
/* 109 */     return d + snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 113 */     double d1 = snapSize(getWidth()) - (snapSpace(getInsets().getLeft()) + snapSpace(getInsets().getRight()));
/* 114 */     double d2 = snapSize(getHeight()) - (snapSpace(getInsets().getTop()) + snapSpace(getInsets().getBottom()));
/* 115 */     double d3 = snapSpace(getInsets().getLeft());
/* 116 */     double d4 = snapSpace(getInsets().getTop());
/*     */ 
/* 119 */     double d5 = 0.0D;
/* 120 */     double d6 = 0.0D;
/*     */ 
/* 123 */     for (TitledPane localTitledPane1 : ((Accordion)getSkinnable()).getPanes()) {
/* 124 */       localObject = (TitledPane)localTitledPane1;
/* 125 */       if (!((TitledPane)localObject).isExpanded())
/*     */       {
/* 127 */         d6 += snapSize(((TitledPane)localObject).minHeight(-1.0D));
/*     */       }
/*     */     }
/*     */ 
/* 131 */     double d7 = 0.0D;
/* 132 */     if ((this.previousPane != null) && (this.previousPane.equals(this.expandedPane)) && (((Accordion)getSkinnable()).getExpandedPane() == null)) {
/* 133 */       if (((Accordion)getSkinnable()).getPanes().size() == 1)
/*     */       {
/* 135 */         d7 = d2;
/*     */       }
/*     */       else
/* 138 */         d7 = d2 - d6 + this.previousPane.minHeight(-1.0D);
/*     */     }
/*     */     else {
/* 141 */       d7 = d2 - d6;
/*     */     }
/*     */ 
/* 144 */     for (Object localObject = ((Accordion)getSkinnable()).getPanes().iterator(); ((Iterator)localObject).hasNext(); ) { TitledPane localTitledPane2 = (TitledPane)((Iterator)localObject).next();
/* 145 */       TitledPane localTitledPane3 = (TitledPane)localTitledPane2;
/* 146 */       TitledPaneSkin localTitledPaneSkin = (TitledPaneSkin)localTitledPane3.getSkin();
/* 147 */       localTitledPaneSkin.setMaxTitledPaneHeightForAccordion(d7);
/* 148 */       double d8 = snapSize(localTitledPaneSkin.getTitledPaneHeightForAccordion());
/* 149 */       localTitledPane3.resize(d1, d8);
/*     */ 
/* 151 */       if ((!this.resize) && (this.previousPane != null) && (this.expandedPane != null))
/*     */       {
/* 153 */         if (((Accordion)getSkinnable()).getPanes().indexOf(this.previousPane) < ((Accordion)getSkinnable()).getPanes().indexOf(this.expandedPane))
/*     */         {
/* 155 */           if ((this.relocateAllPanes) || (((Accordion)getSkinnable()).getPanes().indexOf(localTitledPane3) <= ((Accordion)getSkinnable()).getPanes().indexOf(this.expandedPane))) {
/* 156 */             localTitledPane3.relocate(d3, d4);
/* 157 */             d4 += d8 + d5;
/*     */           }
/*     */         }
/* 160 */         else if (((Accordion)getSkinnable()).getPanes().indexOf(this.previousPane) > ((Accordion)getSkinnable()).getPanes().indexOf(this.expandedPane))
/*     */         {
/* 162 */           if ((this.relocateAllPanes) || (((Accordion)getSkinnable()).getPanes().indexOf(localTitledPane3) <= ((Accordion)getSkinnable()).getPanes().indexOf(this.previousPane))) {
/* 163 */             localTitledPane3.relocate(d3, d4);
/* 164 */             d4 += d8 + d5;
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 170 */           this.relocateAllPanes = true;
/* 171 */           localTitledPane3.relocate(d3, d4);
/* 172 */           d4 += d8 + d5;
/*     */         }
/*     */       } else {
/* 175 */         localTitledPane3.relocate(d3, d4);
/* 176 */         d4 += d8 + d5;
/*     */       }
/*     */     }
/*     */ 
/* 180 */     if ((this.expandedPane != null) && (((TitledPaneSkin)this.expandedPane.getSkin()).getTitledPaneHeightForAccordion() == d7))
/*     */     {
/* 182 */       this.relocateAllPanes = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setWidth(double paramDouble) {
/* 187 */     super.setWidth(paramDouble);
/* 188 */     this.clipRect.setWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected void setHeight(double paramDouble) {
/* 192 */     super.setHeight(paramDouble);
/* 193 */     this.clipRect.setHeight(paramDouble);
/* 194 */     if (this.previousHeight != paramDouble) {
/* 195 */       this.previousHeight = paramDouble;
/* 196 */       this.resize = true;
/*     */     } else {
/* 198 */       this.resize = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void initTitledPaneListeners(List<? extends TitledPane> paramList)
/*     */   {
/* 207 */     for (TitledPane localTitledPane : paramList) {
/* 208 */       localTitledPane.setExpanded(localTitledPane == ((Accordion)getSkinnable()).getExpandedPane());
/* 209 */       if (localTitledPane.isExpanded()) {
/* 210 */         this.expandedPane = localTitledPane;
/*     */       }
/* 212 */       ChangeListener localChangeListener = expandedPropertyListener(localTitledPane);
/* 213 */       localTitledPane.expandedProperty().addListener(localChangeListener);
/* 214 */       this.listeners.put(localTitledPane, localChangeListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeTitledPaneListeners(List<? extends TitledPane> paramList) {
/* 219 */     for (TitledPane localTitledPane : paramList)
/* 220 */       if (this.listeners.containsKey(localTitledPane)) {
/* 221 */         localTitledPane.expandedProperty().removeListener((ChangeListener)this.listeners.get(localTitledPane));
/* 222 */         this.listeners.remove(localTitledPane);
/*     */       }
/*     */   }
/*     */ 
/*     */   private ChangeListener<Boolean> expandedPropertyListener(final TitledPane paramTitledPane)
/*     */   {
/* 228 */     return new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 230 */         AccordionSkin.this.previousPane = AccordionSkin.this.expandedPane;
/* 231 */         if (paramAnonymousBoolean2.booleanValue()) {
/* 232 */           if (((Accordion)AccordionSkin.this.getSkinnable()).getExpandedPane() != null) {
/* 233 */             ((Accordion)AccordionSkin.this.getSkinnable()).getExpandedPane().setExpanded(false);
/*     */           }
/* 235 */           if (paramTitledPane != null) {
/* 236 */             ((Accordion)AccordionSkin.this.getSkinnable()).setExpandedPane(paramTitledPane);
/*     */           }
/* 238 */           AccordionSkin.this.expandedPane = ((Accordion)AccordionSkin.this.getSkinnable()).getExpandedPane();
/*     */         } else {
/* 240 */           AccordionSkin.this.expandedPane = ((Accordion)AccordionSkin.this.getSkinnable()).getExpandedPane();
/* 241 */           ((Accordion)AccordionSkin.this.getSkinnable()).setExpandedPane(null);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.AccordionSkin
 * JD-Core Version:    0.6.2
 */