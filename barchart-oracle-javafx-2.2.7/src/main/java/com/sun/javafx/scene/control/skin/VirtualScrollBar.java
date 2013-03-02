/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.Utils;
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.Observable;
/*    */ import javafx.beans.property.DoubleProperty;
/*    */ import javafx.scene.control.IndexedCell;
/*    */ import javafx.scene.control.ScrollBar;
/*    */ 
/*    */ public class VirtualScrollBar extends ScrollBar
/*    */ {
/*    */   private final VirtualFlow flow;
/*    */   private boolean virtual;
/*    */   private boolean adjusting;
/*    */ 
/*    */   public VirtualScrollBar(final VirtualFlow paramVirtualFlow)
/*    */   {
/* 48 */     this.flow = paramVirtualFlow;
/*    */ 
/* 50 */     super.valueProperty().addListener(new InvalidationListener() {
/*    */       public void invalidated(Observable paramAnonymousObservable) {
/* 52 */         if ((VirtualScrollBar.this.isVirtual()) && 
/* 53 */           (!VirtualScrollBar.this.adjusting))
/*    */         {
/* 56 */           paramVirtualFlow.setPosition(VirtualScrollBar.this.getValue());
/*    */         }
/*    */       }
/*    */     });
/*    */   }
/*    */ 
/*    */   public void setVirtual(boolean paramBoolean)
/*    */   {
/* 64 */     this.virtual = paramBoolean;
/*    */   }
/*    */ 
/*    */   public boolean isVirtual() {
/* 68 */     return this.virtual;
/*    */   }
/*    */ 
/*    */   public void decrement() {
/* 72 */     if (isVirtual())
/* 73 */       this.flow.adjustPixels(-10.0D);
/*    */     else
/* 75 */       super.decrement();
/*    */   }
/*    */ 
/*    */   public void increment()
/*    */   {
/* 80 */     if (isVirtual())
/* 81 */       this.flow.adjustPixels(10.0D);
/*    */     else
/* 83 */       super.increment();
/*    */   }
/*    */ 
/*    */   public void adjustValue(double paramDouble)
/*    */   {
/* 93 */     if (isVirtual())
/*    */     {
/* 98 */       this.adjusting = true;
/* 99 */       double d1 = this.flow.getPosition();
/*    */ 
/* 101 */       double d2 = (getMax() - getMin()) * Utils.clamp(0.0D, paramDouble, 1.0D) + getMin();
/*    */       IndexedCell localIndexedCell;
/* 102 */       if (d2 < d1) {
/* 103 */         localIndexedCell = this.flow.getFirstVisibleCell();
/* 104 */         if (localIndexedCell == null) return;
/* 105 */         this.flow.showAsLast(localIndexedCell);
/* 106 */       } else if (d2 > d1) {
/* 107 */         localIndexedCell = this.flow.getLastVisibleCell();
/* 108 */         if (localIndexedCell == null) return;
/* 109 */         this.flow.showAsFirst(localIndexedCell);
/*    */       }
/*    */ 
/* 113 */       this.adjusting = false;
/*    */     } else {
/* 115 */       super.adjustValue(paramDouble);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.VirtualScrollBar
 * JD-Core Version:    0.6.2
 */